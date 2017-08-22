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
        Config view
    </title>
</head>
<body>

<s:set var="configEntries" value="entries"/>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<n:group2 titleText="Config entries">
    <display:table name="configEntries" id="configEntry" class="result">
        <display:column title="key" property="key"/>
        <display:column title="value" property="value"/>
        <display:column title="type" property="type"/>
        <display:column title="">
            <s:div cssClass="ticket-row-images">
                <s:url var="editUrl" action="configEdit-input" includeParams="none">
                    <s:param name="key">${configEntry.key}</s:param>
                </s:url>
                <s:a href="%{editUrl}">
                    <img src="images/skin-default/action-icons/ticket.revise.png" alt="Edit" title="Edit"/>
                </s:a>
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