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
    <title>Total Domains per Registrar Report</title>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<n:group2 titleText="Result">
    <display:table name="paginatedResult" id="domainRow" class="result" requestURI=""
                   sort="external" excludedParams="resetSearch" export="true">
        <display:column title="Registrar" property="billingNH" sortable="true"/>
        <display:column title="Name" property="accountName" sortable="true"/>
        <display:column title="Domains Count" sortProperty="domainCount" sortable="true" headerClass="force-text-right" class="force-text-right" property="domainCount" decorator="pl.nask.crs.web.displaytag.NumberDecorator" />
        <display:setProperty name="export.excel.filename" value="total_domains.xls"/>
        <display:setProperty name="export.csv.filename" value="total_domains.csv"/>
        <display:setProperty name="export.xml.filename" value="total_domains.xml"/>
    </display:table>
</n:group2>

</body>
</html>