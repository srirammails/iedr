<?xml version="1.0"?>
<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"

	xmlns:tns="http://domainregistry.ie/"
	targetNamespace="http://domainregistry.ie/"
	>

	<xsl:param name="xsd_file"/>

	<xsl:output method="xml" indent="yes"/>

	<xsl:template match="/">
		<wsapi>
			<xsl:for-each select="//wsdl:portType">
				<xsl:sort select="@name"/>
				<xsl:apply-templates select="."/>
			</xsl:for-each>
		</wsapi>
	</xsl:template>

	<xsl:template match="wsdl:portType">
		<service name="{@name}">
			<xsl:for-each select="wsdl:operation">
				<xsl:sort select="@name"/>
				<xsl:apply-templates select="."/>
			</xsl:for-each>
		</service>
	</xsl:template>

	<xsl:template name="get_wsdl_oper_io_message_type">
		<xsl:param name="msg_type" value=""/>
		<!--xsl:comment> msg_type = <xsl:value-of select="$msg_type"/></xsl:comment>-->

		<xsl:variable name="msg_type_nons">
			<xsl:choose>
				<xsl:when test="contains($msg_type,':')"><xsl:value-of select="substring-after($msg_type,':')"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="$msg_type"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!--xsl:comment> msg_type_nons = <xsl:value-of select="$msg_type_nons"/></xsl:comment-->

		<xsl:variable name="msg_name" select="//wsdl:message[@name=$msg_type_nons]/wsdl:part/@element"/>
		<!--xsl:comment> msg_name = <xsl:value-of select="$msg_name"/></xsl:comment-->

		<xsl:variable name="msg_name_nons">
			<xsl:choose>
				<xsl:when test="contains($msg_name,':')"><xsl:value-of select="substring-after($msg_name,':')"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="$msg_name"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!--xsl:comment> msg_name_nons = <xsl:value-of select="$msg_name_nons"/></xsl:comment>-->

		<xsl:variable name="msg_elem_name">
			<xsl:choose>
				<xsl:when test="document($xsd_file)//xs:complexType[@name=$msg_name_nons]/@name">
					<xsl:value-of select="document($xsd_file)//xs:complexType[@name=$msg_name_nons]/@name"/>
				</xsl:when>
				<xsl:when test="document($xsd_file)//xs:complexType[@name=$msg_name_nons]/@type">
					<xsl:value-of select="document($xsd_file)//xs:complexType[@name=$msg_name_nons]/@type"/>
				</xsl:when>
				<xsl:when test="document($xsd_file)//xs:simpleType[@name=$msg_name_nons]/@name">
					<xsl:value-of select="document($xsd_file)//xs:simpleType[@name=$msg_name_nons]/@name"/>
				</xsl:when>
				<xsl:when test="document($xsd_file)//xs:simpleType[@name=$msg_name_nons]/@type">
					<xsl:value-of select="document($xsd_file)//xs:simpleType[@name=$msg_name_nons]/@type"/>
				</xsl:when>
				<xsl:when test="document($xsd_file)//xs:element[@name=$msg_name_nons]/@name">
					<xsl:value-of select="document($xsd_file)//xs:element[@name=$msg_name_nons]/@name"/>
				</xsl:when>
				<xsl:when test="document($xsd_file)//xs:element[@name=$msg_name_nons]/@type">
					<xsl:value-of select="document($xsd_file)//xs:element[@name=$msg_name_nons]/@type"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:comment>Name '<xsl:value-of select="$msg_name_nons"/>' Not found in XSD File <xsl:value-of select="$xsd_file"/></xsl:comment>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!--xsl:comment> msg_elem_name = '<xsl:value-of select="$msg_elem_name"/>'</xsl:comment>-->

		<xsl:choose>
			<xsl:when test="document($xsd_file)//xs:complexType[@name=$msg_elem_name]">
				<xsl:apply-templates select="document($xsd_file)//xs:complexType[@name=$msg_elem_name]"/>
			</xsl:when>
			<xsl:when test="document($xsd_file)//xs:simpleType[@name=$msg_elem_name]">
				<xsl:apply-templates select="document($xsd_file)//xs:simpleType[@name=$msg_elem_name]"/>
			</xsl:when>
			<xsl:when test="document($xsd_file)//xs:element[@name=$msg_elem_name]">
				<xsl:apply-templates select="document($xsd_file)//xs:element[@name=$msg_elem_name]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:comment> No XSD for '<xsl:value-of select="$msg_elem_name"/>'</xsl:comment>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="wsdl:operation">
		<operation name="{@name}">
			<msg_input  message="{wsdl:input/@message}">
				<xsl:call-template name="get_wsdl_oper_io_message_type">
					<xsl:with-param name="msg_type" select="wsdl:input/@message"/>
				</xsl:call-template>
			</msg_input>
			<msg_output message="{wsdl:output/@message}">
				<xsl:call-template name="get_wsdl_oper_io_message_type">
					<xsl:with-param name="msg_type" select="wsdl:output/@message"/>
				</xsl:call-template>
			</msg_output>
			<msg_exceptions>
				<xsl:for-each select='wsdl:fault'>
					<exception><xsl:value-of select='@name'/></exception>
				</xsl:for-each>
			</msg_exceptions>
		</operation>
	</xsl:template>

	<xsl:template match="xs:complexType">
		<xsl:copy select=".">
			<!-- copies complexType@name, which isn't valid XSD (but I don't care ..) -->
			<xsl:copy-of select="@*[not(.='')]"/>
			<xsl:apply-templates select="node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="xs:extension">
		<!-- get the base class name -->
		<xsl:variable name="baseClassName"><xsl:value-of select="substring-after(@base,':')"/></xsl:variable>
		<xsl:comment>inherited from base class '<xsl:value-of select="$baseClassName"/>' : </xsl:comment>
		<!-- find base class and copy members -->
		<xsl:apply-templates select="document($xsd_file)//xs:complexType[@name=$baseClassName]/node()" />
		<!-- continue .. -->
		<xsl:apply-templates select="node()"/>
	</xsl:template>

	<xsl:template match="xs:simpleType">
		<xsl:copy select=".">
			<xsl:copy-of select="@*[not(.='')]"/>
			<xsl:apply-templates select="node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="xs:sequence">
		<xsl:copy select=".">
			<xsl:copy-of select="@*[not(.='')]"/>
			<xsl:apply-templates select="node()"/>
			<xsl:if test="count(./node()) &lt; 1">
				<xsl:comment> NO NODES FOUND </xsl:comment>
			</xsl:if>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="xs:restriction">
		<xsl:copy select=".">
			<xsl:copy-of select="@*[not(.='')]"/>
			<xsl:apply-templates select="node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="xs:enumeration">
		<xsl:copy select=".">
			<xsl:copy-of select="@*[not(.='')]"/>
			<xsl:apply-templates select="node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="xs:element">
		<xsl:choose>

			<xsl:when test="starts-with(@type,'xs:')">
				<xsl:copy select=".">
					<xsl:copy-of select="@*[not(.='')]"/>
					<xsl:apply-templates select="node()"/>
				</xsl:copy>
			</xsl:when>

			<xsl:otherwise>
				<xsl:copy select=".">
					<xsl:copy-of select="@*[not(.='')]"/>
					<xsl:variable name="type_name">
						<xsl:choose>
							<xsl:when test="contains(@type,':')">
								<xsl:value-of select="substring-after(@type,':')"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="@type"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:choose>
						<xsl:when test="document($xsd_file)//xs:complexType[@name=$type_name]">
							<!--xsl:comment> lookup in $xsd_file : xs:complexType[@name='<xsl:value-of select="$type_name"/>']</xsl:comment-->
							<xsl:apply-templates select="document($xsd_file)//xs:complexType[@name=$type_name]"/>
						</xsl:when>
						<xsl:when test="document($xsd_file)//xs:simpleType[@name=$type_name]">
							<!--xsl:comment> lookup in $xsd_file : xs:simpleType[@name='<xsl:value-of select="$type_name"/>']</xsl:comment-->
							<xsl:apply-templates select="document($xsd_file)//xs:simpleType[@name=$type_name]"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:comment> NOT FOUND : <xsl:value-of select="$type_name"/> </xsl:comment>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:copy>
			</xsl:otherwise>

		</xsl:choose>
	</xsl:template>

	<xsl:template match="xs:annotation"/>
	<xsl:template match="xs:documentation"/>
	<xsl:template match="text()"/>

</xsl:stylesheet>

