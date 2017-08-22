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
        Product Pricing View
    </title>
</head>
<body>

<%--<s:set var="prices" value="priceList"/>--%>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<n:group2 titleText="Product Prices">
    <display:table name="paginatedResult" id="priceRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external"
                   export="true">
        <display:column title="id" property="id" sortable="true"/>
        <display:column title="description" property="description" sortable="true"/>
        <display:column title="price" sortable="true" sortProperty="price" class="force-text-right">
            <%--<s:text name="struts.money.format">--%>
                <%--<s:property value="#attr.priceRow.price"/>--%>
            <%--</s:text>--%>
            <s:property value="getText('struts.money.format',{#attr.priceRow.price})"/>
        </display:column>
        <display:column title="duration" property="duration" sortable="true"/>
        <display:column title="validFrom" sortable="true" sortProperty="validFrom">
            <s:date name="#attr.priceRow.validFrom" format="%{getText(datePattern)}"/>
        </display:column>
        <display:column title="validTo" sortable="true" sortProperty="validTo">
            <s:date name="#attr.priceRow.validTo" format="%{getText(datePattern)}"/>
        </display:column>
        <display:column title="forRegistration" property="forRegistration" sortable="true"/>
        <display:column title="forRenewal" property="forRenewal" sortable="true"/>
        <display:column title="direct" property="direct" sortable="true"/>
        <display:column title="" media="html">
            <s:div cssClass="ticket-row-images">
                <s:url var="editUrl" action="priceEdit-input" includeParams="none">
                    <s:param name="id">${priceRow.id}</s:param>
                </s:url>
                <s:a href="%{editUrl}">
                    <img src="images/skin-default/action-icons/ticket.revise.png" alt="Edit" title="Edit"/>
                </s:a>
            </s:div>
        </display:column>
        <display:setProperty name="export.excel.filename" value="product_prices.xls"/>
        <display:setProperty name="export.csv.filename" value="product_prices.csv"/>
        <display:setProperty name="export.xml.filename" value="product_prices.xml"/>
    </display:table>
</n:group2>

<hr class="buttons"/>
<center>
    <input type="button" value="Back" onClick="history.back()">
</center>
</body>
</html>