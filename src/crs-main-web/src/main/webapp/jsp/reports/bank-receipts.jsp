<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Receipts: Bank Reconciliation</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<s:form action="bankReceipts-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">

        <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Invoice Date From" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.invoiceDateFrom"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;"
                              cssCtrlStyle="float:left;width:42%;">
                        <n:datefield2 field="searchCriteria.invoiceDateTo"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:50%">
                <n:field2 label="Invoice No. from" labelJustify="right" tooltipGapHidden="true"
                          fieldEditable="true"
                          cssContainerStyle="padding-left:30%;"
                          cssLabelStyle="float:left;width:40%;"
                          cssCtrlStyle="float:left;width:60%;">
                    <s:textfield name="searchCriteria.invoiceNumberFrom" theme="simple" cssStyle="width:150px;text-indent:0;"/>
                </n:field2>
            </div>

            <div style="float:left; width:50%">
                <n:field2 label="Invoice No. to" labelJustify="right" tooltipGapHidden="true"
                          fieldEditable="true"
                          cssLabelStyle="float:left;width:25%;"
                          cssCtrlStyle="float:left;width:42%;">
                    <s:textfield name="searchCriteria.invoiceNumberTo" theme="simple" cssStyle="width:150px;text-indent:0;"/>
                </n:field2>
            </div>
        </div>

        <div style="clear:both;">
            <n:refreshsearch1/>
        </div>
    </n:group2>
</s:form>

<n:group2 titleText="Result">
    <display:table name="paginatedResult" id="invoiceRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external" excludedParams="resetSearch" export="true">
        <display:column title="Invoice Date" sortable="true" sortProperty="invoiceDate">
            <s:date name="#attr.invoiceRow.invoiceDate" format="%{getText(datePattern)}" />
        </display:column>
        <display:column title="Bill-C Nic" property="billingNicHandle" sortable="true"/>
        <display:column title="Bill-C Name" property="billingNicHandleName" sortable="true"/>
        <display:column title="Domain Name" property="domainName" sortable="true"/>
        <display:column title="Invoice Number" property="invoiceNumber" sortable="true"/>
        <display:column title="Order ID" property="orderId" sortable="true"/>
        <display:column title="Total Value" sortable="true" sortProperty="total" headerClass="force-text-right" class="force-text-right" property="total" decorator="pl.nask.crs.web.displaytag.MoneyDecorator" />
        <display:setProperty name="export.excel.filename" value="ex_invoices.xls"/>
        <display:setProperty name="export.csv.filename" value="ex_invoices.csv"/>
        <display:setProperty name="export.xml.filename" value="ex_invoices.xml"/>
    </display:table>
</n:group2>
</body>
</html>