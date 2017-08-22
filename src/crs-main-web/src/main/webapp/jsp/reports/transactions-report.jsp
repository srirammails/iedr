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
        Deposit Available Balance
    </title>
</head>
<body>

<%--<s:set var="prices" value="priceList"/>--%>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<n:group2 titleText="Result">
    <display:table name="reports" id="reportRow" class="result" export="true" requestURI="">
        <display:column title="Order Id" property="orderId"/>
        <display:column title="Operation Type" property="operationType"/>
        <display:column title="Total Cost" sortable="false" headerClass="force-text-right" class="force-text-right" property="totalCost" decorator="pl.nask.crs.web.displaytag.MoneyDecorator" />
        <display:column title="Available Balance" sortable="false" headerClass="force-text-right" class="force-text-right" property="availableDepositBalance" decorator="pl.nask.crs.web.displaytag.MoneyDecorator"/>
        <display:setProperty name="export.excel.filename" value="deposit_available_balance.xls"/>
        <display:setProperty name="export.csv.filename" value="deposit_available_balance.csv"/>
        <display:setProperty name="export.xml.filename" value="deposit_available_balance.xml"/>
    </display:table>
</n:group2>

<hr class="buttons"/>
<center>
    <input type="button" value="Back" onClick="history.back()">
</center>
</body>
</html>