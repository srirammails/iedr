<?xml version="1.0"?>
<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:date="http://exslt.org/dates-and-times"
	extension-element-prefixes="date"
	>

<!--
	TEST
		echo "select D_Name, Bill_NH from Ticket;" | mysql | grep -v "^D_Name"  | tr '\t' '@' | sed "s|\(.*\)@\(.*\)|php -r \"include('wsapi.php'); \\\\\$e=null; \\\\\$r=null; \\\\\$u=new authenticatedUserVO(); \\\\\$u->username='\2'; \\\\\$u->authenticationToken=\\\\\$u->username; CRSTicketAppService::viewUserTicketForDomain(\\\\\$r,\\\\\$e,\\\\\$u, '\1'); print_r(\\\\\$r);\"|"  | sh -
-->

	<xsl:output method="xml" indent="yes"/>

	<xsl:param name="namespace"/>

	<!-- ======= root ========= -->

	<xsl:template match="/">
		<xsl:processing-instruction name="php">
			<xsl:call-template name="generation"/>
			<xsl:apply-templates/>
		</xsl:processing-instruction>
	</xsl:template>

		<xsl:template name="generation">
			<xsl:text>&#xa;&#xa;</xsl:text>
			<xsl:text>// AUTO-GENERATED BY </xsl:text>
			<xsl:value-of select="system-property('xsl:vendor')"/>
			<xsl:text> AT </xsl:text>
			<xsl:value-of select="date:date-time()"/>
		</xsl:template>

	<!-- ======= api ========= -->

	<xsl:template match="wsapi">
		<!--xsl:text>&#xa;&#xa;namespace </xsl:text><xsl:value-of select="$namespace"/>
		<xsl:text>;&#xa;</xsl:text-->
		<xsl:call-template name="input_type_defs"/>
		<xsl:apply-templates select="service"/>
		<xsl:text>&#xa;</xsl:text>
	</xsl:template>

		<xsl:key name="elemtypekeys" match="//xs:element[not(starts-with(@type,'xs:'))]/@type" use="."/>

		<xsl:template name="input_type_defs">
			<xsl:variable name="num" select="count(//msg_input//xs:element[not(starts-with(@type,'xs:'))])"/>

			<xsl:for-each select="//xs:element[not(starts-with(@type,'xs:'))]">
				<xsl:sort select="@type"/>
				<!-- meunchian grouping : http://www.dpawson.co.uk/xsl/sect2/N2696.html#d4278e208 -->
				<xsl:if test="generate-id(@type) = generate-id(key('elemtypekeys', @type)[1])">
					<xsl:variable name="elemtypename" select="@type"/>
					<xsl:variable name="firstelemtype" select="(//xs:element[@type=$elemtypename])[1]"/>
					<xsl:call-template name="write_type_def">
						<xsl:with-param name="typename" select="$elemtypename"/>
						<xsl:with-param name="typeelem" select="$firstelemtype"/>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</xsl:template>

		<xsl:template name="write_type_def">
			<xsl:param name="typename"/>
			<xsl:param name="typeelem"/>
			<xsl:variable name="classname">
				<xsl:choose>
					<xsl:when test="contains($typename,':')">
						<xsl:value-of select="concat($namespace,'_',substring-after($typename,':'))"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="concat($namespace,'_',$typename)"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:text>&#xa;&#xa;class </xsl:text>
			<xsl:value-of select="$classname"/>
			<xsl:if test="$typeelem/xs:complexType/xs:sequence/xs:element">
				<xsl:text>&#xa;	extends crs_wsapi_vo_base</xsl:text>
			</xsl:if>
			<xsl:text>&#xa;	{</xsl:text>
			<xsl:choose>
				<xsl:when test="$typeelem/xs:complexType/xs:sequence">
					<!-- a compound type -->
					<xsl:for-each select="$typeelem/xs:complexType/xs:sequence/xs:element">
						<xsl:text>&#xa;	public $</xsl:text>
						<xsl:value-of select="@name"/>
						<xsl:text>;</xsl:text>
						<xsl:call-template name="param_type_comment">
							<xsl:with-param name="elem" select="."/>
						</xsl:call-template>
					</xsl:for-each>
				</xsl:when>
				<xsl:when test="$typeelem/xs:simpleType/xs:restriction">
					<!-- an enum -->
					<xsl:for-each select="$typeelem/xs:simpleType/xs:restriction/xs:enumeration">
						<xsl:text>&#xa;	const _</xsl:text>
						<xsl:value-of select="@value"/>
						<xsl:text> = '</xsl:text>
						<xsl:value-of select="@value"/>
						<xsl:text>';</xsl:text>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>/* UNHANDLED */</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:text>&#xa;	}</xsl:text>
		</xsl:template>

		<xsl:template name="param_type_comment">
			<xsl:param name="elem"/>
			<xsl:for-each select="$elem">
				<xsl:text>// type=</xsl:text>
				<xsl:value-of select="@type"/>
				<xsl:choose>
					<xsl:when test="@nillable and (@nillable='true')">
						<xsl:text>, (null)</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>, (NOT NULL)</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:choose>
					<xsl:when test="@minOccurs">
						<xsl:text>, min=</xsl:text>
						<xsl:value-of select="@minOccurs"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>, min=1</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@maxOccurs">
					<xsl:text>, max=</xsl:text>
					<xsl:value-of select="@maxOccurs"/>
				</xsl:if>
			</xsl:for-each>
		</xsl:template>

	<!-- ======= service endpoints ========= -->

	<xsl:template match="service">
		<xsl:text>&#xa;&#xa;class </xsl:text><xsl:value-of select="concat($namespace,'_service')"/>
		<xsl:text>&#xa;	extends crs_wsapi_service_base&#xa;	{</xsl:text>
		<xsl:apply-templates select="operation"/>
		<xsl:text>&#xa;	}</xsl:text>
	</xsl:template>

	<!-- ======= service functions ========= -->

	<xsl:template match="operation">
		<xsl:variable name="php_fn_name" select="@name"/>
		<xsl:variable name="service_name" select="ancestor::service/@name"/>
		<xsl:variable name="operation_name" select="@name"/>
		<xsl:variable name="return_element_name" select="msg_output/xs:complexType/xs:sequence/xs:element/@name"/>
		<xsl:variable name="return_type_name" select="msg_output/xs:complexType/xs:sequence/xs:element/xs:complexType/@name"/>

		<xsl:variable name="str_inout_params">
			<xsl:choose>
				<xsl:when test="$return_element_name!=''">&amp;$result,&amp;$errs</xsl:when>
				<xsl:otherwise>&amp;$errs</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:variable name="expected_exception_names">
			<xsl:for-each select="msg_exceptions/exception">
				<xsl:text>'</xsl:text><xsl:value-of select="."/><xsl:text>',
				</xsl:text>
			</xsl:for-each>
		</xsl:variable>

		<xsl:variable name="str_return_val">
			<xsl:choose>
				<xsl:when test="$return_element_name!=''">
					<xsl:text>&#xa;			if(is_object($response) and property_exists($response,'</xsl:text><xsl:value-of select="$return_element_name"/><xsl:text>'))</xsl:text>
					<xsl:text>&#xa;				$result = /*(</xsl:text>
					<xsl:value-of select="$return_type_name"/>
					<xsl:text>)*/ $response-&gt;</xsl:text>
					<xsl:value-of select="$return_element_name"/>
					<xsl:text>;</xsl:text>
				</xsl:when>
				<xsl:otherwise><xsl:text/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:text>
	static public function </xsl:text>
			<xsl:value-of select="$php_fn_name"/>
			<xsl:text>(</xsl:text>
				<xsl:value-of select="$str_inout_params"/>
				<xsl:apply-templates select="msg_input" mode="paramlist"/>
			<xsl:text>)
		{
		$response = null;
		$errs = 'SOAP Request Error';
		self::log("</xsl:text>
				<xsl:value-of select="concat($service_name,'::',$php_fn_name)"/>
				<xsl:text>(</xsl:text>
				<!--xsl:apply-templates select="msg_input" mode="paramlist"/ BUG -->
				<xsl:text>)",__FILE__,__LINE__);
		$client = null;
		try
			{
			$client = new SoapClient(self::getSoapUrl().'</xsl:text><xsl:value-of select="$service_name"/><xsl:text>?wsdl', self::$crs_soap_options);
			$response = $client-></xsl:text><xsl:value-of select="$operation_name"/><xsl:text>
					(
					array(</xsl:text><xsl:apply-templates select="msg_input" mode="paramarray"/><xsl:text>
					)
				);</xsl:text>
			<xsl:value-of select="$str_return_val"/><xsl:text>
			$errs = null;
			}
		catch (Exception $e)
			{
			$expected_exceptions = array
				(
				</xsl:text><xsl:value-of select="$expected_exception_names"/><xsl:text>
				);
			$errs = self::getMessageFromException($e, $expected_exceptions);
			}
		self::logrequest($client);
		return $response==null ? false : true;
		}
		</xsl:text>
	</xsl:template>

	<!-- ======= service function input params ========= -->

	<xsl:template match="msg_input" mode="paramlist">
		<xsl:param name="msginput"/>
		<xsl:for-each select="xs:complexType/xs:sequence/xs:element">
			<xsl:text>, $</xsl:text>
			<xsl:value-of select="@name"/>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="msg_input" mode="paramarray">
		<xsl:param name="msginput"/>
		<xsl:for-each select="xs:complexType/xs:sequence/xs:element">
			<xsl:text>&#xa;					'</xsl:text>
			<xsl:value-of select="@name"/>
			<xsl:text>' =&gt; $</xsl:text>
			<xsl:value-of select="@name"/>
			<xsl:text>, </xsl:text>
			<xsl:call-template name="param_type_comment">
				<xsl:with-param name="elem" select="."/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
