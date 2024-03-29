#!/bin/bash

# wget http://downloads.sourceforge.net/cyclops-group/jmxterm-1.0-alpha-4-uber.jar

JARFILE="jmxterm-1.0-alpha-4-uber.jar"
JMXHOST=127.0.0.1
JMXPORT=9003

function die() { echo $1 ; exit 1 ; }
function checkexe() { [ -x `which $1` ] || die "cant find executable program $1" ; }

PROGS="java grep cat sed tr xsltproc xmllint"
for P in $PROGS ; do checkexe $P ; done
[ -f $JARFILE ] || die "can't see jar file $JARFILE"

## jmxterm commands to get thread stack dump
cat >/tmp/myscript.jmx<<EOF
domain java.lang
bean java.lang:type=Threading
run dumpAllThreads 1 1 
quit
EOF

## get the stack traces
java -jar $JARFILE -l $JMXHOST:$JMXPORT -i /tmp/myscript.jmx > /tmp/allthreads.txt

grep "threadId" /tmp/allthreads.txt || die "stack trace get seemed to fail ?!"

## turn them into xml
cat /tmp/allthreads.txt | \
	sed \
		-e "s|\[ |<array>|g" -e "s| \]|</array>|g" \
		-e "s|{|<obj>|g" -e "s|}|</obj>|g" \
		-e "s|\([^ <>]*\) = \([^ <>]*\);|<\1>\2</\1>|g" \
		-e "s|\([^ ]*\) = \([^;]*\) *;|<\1>\2</\1>|g" | \
	tr '\r\n\t' ' ' | tr -s ' ' | \
	sed \
		-e "s|\([^ =]*\) = \([^;=]*\);|<\1>\2</\1>|g" \
		-e "s|\([^ ]*\) = \([^;]*\);|<\1>\2</\1>|g"\
		-e "s| *, *||g"  > /tmp/allthreads.xml

## xsl to convert xml-ified stack traces to simpler format
cat >/tmp/jmxterm_threads.xslt<<EOF
<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<jmx>
			<xsl:for-each select="/array/obj">
				<xsl:sort select="threadId" data-type="number"/>
				<xsl:apply-templates select="." mode="thread"/>
			</xsl:for-each>
		</jmx>
	</xsl:template>
	<xsl:template match="obj" mode="thread">
		<thread id="{threadId/text()}">
			<xsl:copy-of select="threadName"/>
			<xsl:copy-of select="threadState"/>
			<xsl:copy-of select="suspended"/>
			<xsl:copy-of select="inNative"/>
			<xsl:copy-of select="waitedCount"/>
			<xsl:copy-of select="blockedCount"/>
			<xsl:apply-templates select="stackTrace"/>
		</thread>
	</xsl:template>
	<xsl:template match="stackTrace">
		<stackTrace>
			<xsl:for-each select="./array/obj">
				<stackfn class="{className}" method="{methodName}" file="{fileName}" line="{lineNumber}"/>
			</xsl:for-each>
		</stackTrace>
	</xsl:template>
</xsl:stylesheet>
EOF

## simplify xml
xsltproc /tmp/jmxterm_threads.xslt /tmp/allthreads.xml | xmllint --format - > /tmp/allthreads_simple.xml

cat /tmp/allthreads_simple.xml
