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
        Transaction Info
    </title>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<n:group2 titleText="Result">
    <display:table name="transactionInfos" id="transactionRow" class="result" export="true" requestURI="">
        <display:column title="Domain Name" property="domainName" sortable="false"/>
        <display:column title="Net Amount" sortable="false" headerClass="force-text-right" class="force-text-right" property="netAmount" decorator="pl.nask.crs.web.displaytag.MoneyDecorator"/>
        <display:column title="Vat Amount" sortable="false" headerClass="force-text-right" class="force-text-right" property="vatAmount" decorator="pl.nask.crs.web.displaytag.MoneyDecorator" />
        <display:setProperty name="export.excel.filename" value="transaction_info.xls"/>
        <display:setProperty name="export.csv.filename" value="transaction_info.csv"/>
        <display:setProperty name="export.xml.filename" value="transaction_info.xml"/>
    </display:table>
</n:group2>

<hr class="buttons"/>
<center>
    <input type="button" value="Back" onClick="history.back()">
</center>
</body>
</html>