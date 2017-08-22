#!/bin/bash

# make dirs

for Y in 2011 2010 2009 2008 ; do for M in 01 02 03 04 05 06 07 08 09 10 11 12 ; do mkdir -p log/$Y/$M ; done ; done

# get svn log

svn log > log/svn.log

ls -l log/svn.log

# build command list

grep "^r[0-9]" log/svn.log | while read L ; do 
	R=`echo $L | cut -d' ' -f 1 | tr -d 'r'` ;  
	U=`echo $L | cut -d' ' -f 3` ; 
	D=`echo $L | cut -d' ' -f 5` ; 
	H=`echo $L | cut -d' ' -f 6` ; 
	DF=`echo log/${D}_${H}_${PR}_${PU}.diff | sed -e "s|log/\(20[0-9][0-9]\)-\([0-9][0-9]\)-|log/\1/\2/\1-\2-|g"`
	[ "_$PR" = "_" ] || echo "[ -f ${DF} ] || { svn diff -r$R:$PR > ${DF} ; ls -l ${DF} ; }"
	PR=$R ; 
	PU=$U ; 
done | sh -x -

find log -depth -type d -empty -exec rmdir {} \;

find log -type f -name \*\.diff | while read F ; do T=`echo $F | cut -d\/ -f 4 | cut -c 1-19 | tr '_' ' '` ; touch -d "$T" $F ; done
