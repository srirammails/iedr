<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>
        View Transactions History
    </title>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<s:form action="transactionsHistReport-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">
        <div style="height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Transaction Type" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <s:select list="depositTransactionTypes"
                                  headerKey="" headerValue="[ALL]"
                                  name="searchCriteria.transactionType"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Remark" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:25%;"
                              cssCtrlStyle="float:left;width:75%;">
                        <s:textfield name="searchCriteria.remark" theme="simple"
                                     cssStyle="width:150px;text-indent:0;"/>
                    </n:field2>
                </div>
            </div>
        </div>
        <div style="height:26px;">
            <div style="padding:7px 0 0 0; height:26px;">
                <div style="float:left; width:50%">
                    <div>
                        <n:field2 label="Transaction From" labelJustify="right" tooltipGapHidden="true"
                                  fieldEditable="true"
                                  cssContainerStyle="padding-left:30%;"
                                  cssLabelStyle="float:left;width:65%;"
                                  cssCtrlStyle="float:left;width:35%;">
                            <n:datefield2 field="searchCriteria.transactionDateFrom"/>
                        </n:field2>
                    </div>
                </div>
                <div style="float:left; width:50%">
                    <div>
                        <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                                  fieldEditable="true"
                                  cssLabelStyle="float:left;width:5%;"
                                  cssCtrlStyle="float:left;width:95%;">
                            <n:datefield2 field="searchCriteria.transactionDateTo"/>
                        </n:field2>
                    </div>
                </div>
            </div>
        </div>
        <div style="clear:both;">
            <n:refreshsearch1/>
        </div>
    </n:group2>
</s:form>

<n:group2 titleText="Result">
    <display:table name="paginatedResult" id="depositsRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external" excludedParams="resetSearch" export="true">
        <display:column title="Date" sortProperty="id" sortable="true">
            <s:date name="#attr.depositsRow.transactionDate"/>
        </display:column>
        <display:column title="Order Id" sortProperty="orderId" sortable="true" value="${depositsRow.orderId}" href="viewTransactionInfo-input.action" paramId="orderId"/>                    
        <display:column title="Remark" property="remark" sortable="true"/>
        <display:column title="Transaction Type" property="transactionType" sortable="true"/>
        <display:column title="Transaction Amount" sortable="true" sortProperty="transactionAmount" headerClass="force-text-right" class="force-text-right" property="transactionAmount" decorator="pl.nask.crs.web.displaytag.MoneyDecorator"/>
        <display:column title="Actual Balance" sortable="true" sortProperty="closeBal" headerClass="force-text-right" class="force-text-right" property="closeBal" decorator="pl.nask.crs.web.displaytag.MoneyDecorator"/>
        <display:setProperty name="export.excel.filename" value="transactions_history.xls"/>
        <display:setProperty name="export.csv.filename" value="transactions_history.csv"/>
        <display:setProperty name="export.xml.filename" value="transactions_history.xml"/>
    </display:table>
</n:group2>

<s:form name="back_form" theme="simple">
    <center>
        <s:submit value="Back" action="depositAccount" method="input" theme="simple" />
    </center>
</s:form>
</body>
</html>