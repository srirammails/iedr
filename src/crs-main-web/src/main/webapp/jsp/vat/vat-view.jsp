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
        Vat View
    </title>
</head>
<body>

<s:set var="vats" value="vatList"/>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<n:group2 titleText="Valid Vat Rates">
    <display:table name="vats" id="vatRow" class="result">
        <display:column title="id" property="id"/>
        <display:column title="category" property="category"/>
        <display:column title="from">
            <s:date name="#attr.vatRow.fromDate" format="%{getText(datePattern)}"/>
        </display:column>
        <display:column title="rate" class="force-text-right">
            <s:property value="getText('struts.money.format',{#attr.vatRow.vatRate})"/>
        </display:column>
        <display:column title="">
            <s:div cssClass="ticket-row-images">
                <s:url var="invalidateUrl" action="vatView-invalidate" includeParams="none">
                    <s:param name="id">${vatRow.id}</s:param>
                </s:url>
                <s:if test="%{hasPermission('manageVat')}">
                	<s:a href="%{invalidateUrl}">
                    	<img src="images/skin-default/action-icons/delete.png" alt="Invalidate" title="Invalidate"/>
                	</s:a>
                </s:if>
            </s:div>
        </display:column>
    </display:table>
</n:group2>

<hr class="buttons"/>
<center>
    <input type="button" value="Back" onClick="history.back()">
</center>
</body>
</html>