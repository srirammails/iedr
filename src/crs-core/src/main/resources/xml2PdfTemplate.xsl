<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

<xsl:template match="/">
	<fo:root>

		<fo:layout-master-set>
			<fo:simple-page-master master-name="A4"
					page-width="210mm" page-height="297mm" margin-top="1cm"
					margin-bottom="1.5cm" margin-left="1cm" margin-right="1cm">
				<fo:region-body margin-top="5.2cm"/>
				<fo:region-before extent="0cm"/>
				<fo:region-after extent="0cm"/>
				<fo:region-start extent="0cm"/>
				<fo:region-end extent="0cm"/>
			</fo:simple-page-master>
		</fo:layout-master-set>

		<fo:page-sequence master-reference="A4" id="seq1">

				<fo:static-content flow-name="xsl-region-before" height="auto">
					<fo:table table-layout="auto" border-width="0mm" border-style="solid" font-size="7pt">
						<fo:table-column column-width="45%"/>
						<fo:table-column column-width="27%"/>
						<fo:table-column column-width="28%"/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block>
										<fo:external-graphic src="url('iedrLogo.tif')" content-height="50%"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block linefeed-treatment="preserve"><xsl:value-of select="invoice/iedr/address1"/></fo:block>
									<fo:block><xsl:value-of select="invoice/iedr/county"/></fo:block>
									<fo:block><xsl:value-of select="invoice/iedr/country"/></fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block><fo:inline font-weight="bold">VAT No</fo:inline> IE 6335315V</fo:block>
									<fo:block><fo:inline font-weight="bold">Tel</fo:inline> 00353 (0)1 236 5422</fo:block>
									<fo:block><fo:inline font-weight="bold">Fax</fo:inline> 00353 (0)1 230 0365</fo:block>
									<fo:block><fo:inline font-weight="bold">Email</fo:inline> accounts@iedr.ie</fo:block>
									<fo:block><fo:inline font-weight="bold">Web</fo:inline> www.iedr.ie</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
					<fo:table margin-top="0.5cm" table-layout="auto" border-width="0mm" border-style="solid" font-size="7pt">
						<fo:table-column column-width="75%"/>
						<fo:table-column column-width="25%"/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell font-size="8pt">
                                    <fo:block><xsl:value-of select="invoice/account/name"/></fo:block>
                                    <fo:block linefeed-treatment="preserve"><xsl:value-of select="invoice/account/address1"/></fo:block>
                                    <fo:block linefeed-treatment="preserve"><xsl:value-of select="invoice/account/address2"/></fo:block>
                                    <fo:block linefeed-treatment="preserve"><xsl:value-of select="invoice/account/address3"/></fo:block>
                                    <fo:block><xsl:value-of select="invoice/account/county"/></fo:block>
                                    <fo:block><xsl:value-of select="invoice/account/country"/></fo:block>
                                </fo:table-cell>
								<fo:table-cell>
                                    <fo:table>
                                        <fo:table-column column-width="50%"/>
                                        <fo:table-column column-width="50%"/>
                                        <fo:table-body>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block>Invoice Number:</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell text-align="right">
                                                    <fo:block><xsl:value-of select="invoice/number"/></fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block>Account Number:</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell text-align="right">
                                                    <fo:block><xsl:value-of select="invoice/account/billC"/></fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block>Invoice Date:</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell text-align="right">
                                                    <fo:block><xsl:value-of select="invoice/date"/></fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </fo:table-body>
                                    </fo:table>
                                </fo:table-cell>
                            </fo:table-row>
						</fo:table-body>
					</fo:table>
					<fo:block font-size="7pt" text-align="right">Page <fo:page-number/> of <fo:page-number-citation-last ref-id="seq1"/></fo:block>										
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
				<fo:table table-layout="auto" border-width="0pt" border-style="solid" font-size="7pt">
					<fo:table-column column-width="26%"/>	<!-- domain name -->
					<fo:table-column column-width="14%"/>	<!-- payment ref -->
					<fo:table-column column-width="8%"/>	<!-- reg dt -->
					<fo:table-column column-width="8%"/>	<!-- ren dt -->
					<fo:table-column column-width="8%"/>	<!-- xref dt -->
					<fo:table-column column-width="5%"/>	<!-- type -->
					<fo:table-column column-width="4%"/>	<!-- years -->
					<fo:table-column column-width="9%"/>	<!-- unit price -->
					<fo:table-column column-width="7%"/>	<!-- amount -->
					<fo:table-column column-width="5%"/>	<!-- vat -->
					<fo:table-column column-width="6%"/>	<!-- total -->
					<fo:table-header text-align="center" display-align="center">                        
                        <fo:table-row>
                            <fo:table-cell number-columns-spanned="11" text-align="center">
                                <fo:block font-size="13pt" font-weight="bold">INVOICE</fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row font-weight="bold" background-color="#04917d" color="#ffffff">
							<xsl:call-template name="headerCellWithAlign">
        						<xsl:with-param name="title">Domain Name</xsl:with-param>
        						<xsl:with-param name="textAlign">left</xsl:with-param>
        					</xsl:call-template>
							<xsl:call-template name="headerCell">
        						<xsl:with-param name="title">Payment Ref</xsl:with-param>
        					</xsl:call-template>
							<xsl:call-template name="headerCell">
        						<xsl:with-param name="title">Reg Date</xsl:with-param>
        					</xsl:call-template>
							<xsl:call-template name="headerCell">
        						<xsl:with-param name="title">Ren Date</xsl:with-param>
        					</xsl:call-template>
							<xsl:call-template name="headerCell">
        						<xsl:with-param name="title">Xfer Date</xsl:with-param>
        					</xsl:call-template>
							<xsl:call-template name="headerCell">
        						<xsl:with-param name="title">Type</xsl:with-param>
        					</xsl:call-template>
							<xsl:call-template name="headerCell">
        						<xsl:with-param name="title">Years</xsl:with-param>
        					</xsl:call-template>
							<xsl:call-template name="headerCell">
        						<xsl:with-param name="title">Unit Price* €</xsl:with-param>
        					</xsl:call-template>
							<xsl:call-template name="headerCell">
        						<xsl:with-param name="title">Amount €</xsl:with-param>
        					</xsl:call-template>
							<xsl:call-template name="headerCell">
        						<xsl:with-param name="title">VAT €</xsl:with-param>
        					</xsl:call-template>
							<xsl:call-template name="headerCell">
        						<xsl:with-param name="title">Total €</xsl:with-param>
        					</xsl:call-template>
						</fo:table-row>
					</fo:table-header>

					<fo:table-body border-width="0pt" border-style="solid" border-color="#ffffff" background-color="#e3f3ec" width="100%">
						<xsl:for-each select="invoice/transactions/transaction">
							<xsl:for-each select="item">
								<fo:table-row>
									<fo:table-cell keep-together.within-column="always" padding-left="2pt" border-width="1pt" border-top-width="0pt" border-bottom-width="0pt" border-style="solid" border-color="#ffffff">
										<fo:block>
											<xsl:call-template name="intersperse-with-zero-spaces">
	        									<xsl:with-param name="str" select="domain/name"/>
	    									</xsl:call-template>
										</fo:block>
										<fo:block>&#160;&#160;<xsl:value-of select="holder/name"/></fo:block>
									</fo:table-cell>
									<xsl:call-template name="mainCell">
        								<xsl:with-param name="value"><xsl:value-of select="../orderID"/></xsl:with-param>
        							</xsl:call-template>
									<xsl:call-template name="mainCell">
        								<xsl:with-param name="value"><xsl:value-of select="domain/registrationDate"/></xsl:with-param>
        							</xsl:call-template>
									<xsl:call-template name="mainCell">
        								<xsl:with-param name="value"><xsl:value-of select="domain/renewalDate"/></xsl:with-param>
        							</xsl:call-template>
									<xsl:call-template name="mainCell">
        								<xsl:with-param name="value"><xsl:value-of select="domain/transferDate"/></xsl:with-param>
        							</xsl:call-template>
									<xsl:call-template name="typeCell">
        								<xsl:with-param name="value"><xsl:value-of select="../type"/></xsl:with-param>
        							</xsl:call-template>
									<xsl:call-template name="mainCell">
        								<xsl:with-param name="value"><xsl:value-of select="product/years"/></xsl:with-param>
        							</xsl:call-template>
									<xsl:call-template name="mainCellNumber">
        								<xsl:with-param name="value"><xsl:value-of select="unit"/></xsl:with-param>
        							</xsl:call-template>
									<xsl:call-template name="mainCellNumber">
        								<xsl:with-param name="value"><xsl:value-of select="cost"/></xsl:with-param>
        							</xsl:call-template>
									<xsl:call-template name="mainCellNumber">
        								<xsl:with-param name="value"><xsl:value-of select="VATAmount"/></xsl:with-param>
        							</xsl:call-template>
									<xsl:call-template name="mainCellNumber">
        								<xsl:with-param name="value"><xsl:value-of select="total"/></xsl:with-param>
        							</xsl:call-template>
								</fo:table-row>
							</xsl:for-each>
						</xsl:for-each>
					</fo:table-body>
				</fo:table>
				<fo:table id="total" font-size="7pt" margin-top="5mm" font-weight="bold" color="#ffffff">
					<fo:table-column column-width="75%"/>
					<fo:table-column column-width="15%"/>
					<fo:table-column column-width="2%"/>
					<fo:table-column column-width="8%"/>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell number-columns-spanned="4">
								<fo:block color="#000000" font-style="italic" font-size="6pt" font-weight="normal">Fees in respect of domain registrations, renewals and transfers.</fo:block>
                                <fo:block color="#000000" font-style="italic" font-size="6pt" font-weight="normal">* Multiyear unit price is subject to rounding.</fo:block>
                            </fo:table-cell>
						</fo:table-row>
						<xsl:call-template name="totalRow">
        					<xsl:with-param name="title">Total Value:</xsl:with-param>
        					<xsl:with-param name="curr">€</xsl:with-param>
        					<xsl:with-param name="value" select="invoice/totalExVAT"/>
    					</xsl:call-template>
						<xsl:call-template name="totalRow">
        					<xsl:with-param name="title">Total VAT @ 23.00%:</xsl:with-param>
        					<xsl:with-param name="curr">€</xsl:with-param>
        					<xsl:with-param name="value" select="invoice/totalVAT/total"/>
    					</xsl:call-template>
						<xsl:call-template name="totalRow">
        					<xsl:with-param name="title">Total including VAT:</xsl:with-param>
        					<xsl:with-param name="curr">€</xsl:with-param>
        					<xsl:with-param name="value" select="invoice/totalIncVAT"/>
    					</xsl:call-template>
						<fo:table-row>
							<fo:table-cell number-columns-spanned="4"><fo:block/></fo:table-cell>
						</fo:table-row>
						<xsl:call-template name="totalRow1">
        					<xsl:with-param name="title1" >Paid by: </xsl:with-param>
                            <xsl:with-param name="title2" select="invoice/paidBy"/>
                            <xsl:with-param name="curr">€</xsl:with-param>
        					<xsl:with-param name="value" select="invoice/totalIncVAT"/>
    					</xsl:call-template>
					</fo:table-body>
				</fo:table>			
			</fo:flow>
		</fo:page-sequence>

	</fo:root>
</xsl:template>

<xsl:template name="headerCell">
	<xsl:param name="title"/>
	<xsl:call-template name="headerCellWithAlign">
		<xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
		<xsl:with-param name="textAlign">center</xsl:with-param>
	</xsl:call-template>
</xsl:template>



<xsl:template name="headerCellWithAlign">
	<xsl:param name="title"/>
	<xsl:param name="textAlign"/>
	<fo:table-cell padding="2pt" text-align="{$textAlign}" border-width="1pt" border-top-width="0pt" border-bottom-width="0pt" border-style="solid" border-color="#ffffff">
		<fo:block><xsl:value-of select="$title"/></fo:block>
	</fo:table-cell>
</xsl:template>

<xsl:template name="mainCell">
	<xsl:param name="value"/>
	<xsl:call-template name="mainCellWithAlign">
		<xsl:with-param name="value"><xsl:value-of select="$value"/></xsl:with-param>
		<xsl:with-param name="textAlign">center</xsl:with-param>
	</xsl:call-template>
</xsl:template>

<xsl:template name="mainCellNumber">
    <xsl:param name="value"/>
    <xsl:call-template name="mainCellWithAlign">
        <xsl:with-param name="value"><xsl:value-of select="format-number($value, '###,##0.00')"/></xsl:with-param>
        <xsl:with-param name="textAlign">right</xsl:with-param>
    </xsl:call-template>
</xsl:template>

<xsl:template name="typeCell">
	<xsl:param name="value"/>
	<xsl:choose>
		<xsl:when test="$value = 'renewal'">
			<xsl:call-template name="mainCell">
				<xsl:with-param name="value">Ren</xsl:with-param>
			</xsl:call-template>
		</xsl:when>
		<xsl:when test="$value = 'transfer'">
			<xsl:call-template name="mainCell">
				<xsl:with-param name="value">Xfer</xsl:with-param>
			</xsl:call-template>
		</xsl:when>
		<xsl:when test="$value = 'registration'">
			<xsl:call-template name="mainCell">
				<xsl:with-param name="value">NReg</xsl:with-param>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
        	<xsl:call-template name="mainCell">
				<xsl:with-param name="value">unknown</xsl:with-param>
			</xsl:call-template>
        </xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="mainCellWithAlign">
	<xsl:param name="value"/>
	<xsl:param name="textAlign"/>
	<fo:table-cell padding="1pt" border-width="1pt" border-top-width="0pt" border-bottom-width="0pt" border-style="solid" border-color="#ffffff">
		<fo:block text-align="{$textAlign}"><xsl:value-of select="$value"/></fo:block>
	</fo:table-cell>
</xsl:template>

<xsl:template name="totalRow">
	<xsl:param name="title"/>
	<xsl:param name="curr"/>
	<xsl:param name="value"/>
	<fo:table-row>
		<fo:table-cell><fo:block/></fo:table-cell>
		<xsl:call-template name="totalCell">
	  		<xsl:with-param name="str"><xsl:value-of select="$title"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="totalCell">
	  		<xsl:with-param name="str"><xsl:value-of select="$curr"/></xsl:with-param>
		</xsl:call-template>
		<fo:table-cell padding="2pt" text-align="right" background-color="#e3f3ec" color="#000000" border-width="1pt" border-left-width="0pt" border-right-width="0pt" border-style="solid" border-color="#ffffff">
            <fo:block><xsl:value-of select="format-number($value, '###,##0.00')"/></fo:block>
        </fo:table-cell>
	</fo:table-row>
</xsl:template>

<xsl:template name="totalRow1">
    <xsl:param name="title1"/>
    <xsl:param name="title2"/>
    <xsl:param name="curr"/>
    <xsl:param name="value"/>
    <fo:table-row>
        <fo:table-cell><fo:block/></fo:table-cell>
        <xsl:call-template name="totalCell">
            <xsl:with-param name="str"><xsl:value-of select="$title1"/><xsl:value-of select="$title2"/></xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="totalCell">
            <xsl:with-param name="str"><xsl:value-of select="$curr"/></xsl:with-param>
        </xsl:call-template>
        <fo:table-cell padding="2pt" text-align="right" background-color="#e3f3ec" color="#000000" border-width="1pt" border-left-width="0pt" border-right-width="0pt" border-style="solid" border-color="#ffffff">
            <fo:block><xsl:value-of select="format-number($value, '###,##0.00')"/></fo:block>
        </fo:table-cell>
    </fo:table-row>
</xsl:template>


<xsl:template name="totalCell">
	<xsl:param name="str"/>
	<fo:table-cell padding="2pt" background-color="#04917d" border-width="1pt" border-left-width="0pt" border-right-width="0pt" border-style="solid" border-color="#ffffff">
		<fo:block><xsl:value-of select="$str"/></fo:block>
	</fo:table-cell>
</xsl:template>

<xsl:template name="intersperse-with-zero-spaces">
    <xsl:param name="str"/>
    <xsl:variable name="spacechars">
        &#x9;&#xA;
        &#x2000;&#x2001;&#x2002;&#x2003;&#x2004;&#x2005;
        &#x2006;&#x2007;&#x2008;&#x2009;&#x200A;&#x200B;
    </xsl:variable>

    <xsl:if test="string-length($str) &gt; 0">
        <xsl:variable name="c1" select="substring($str, 1, 1)"/>
        <xsl:variable name="c2" select="substring($str, 2, 1)"/>
        <xsl:value-of select="$c1"/>
        <xsl:if test="$c2 != '' and
            not(contains($spacechars, $c1) or
            contains($spacechars, $c2))">
            <xsl:text>&#x200B;</xsl:text>
        </xsl:if>
        <xsl:call-template name="intersperse-with-zero-spaces">
            <xsl:with-param name="str" select="substring($str, 2)"/>
        </xsl:call-template>
    </xsl:if>
</xsl:template>

</xsl:stylesheet>