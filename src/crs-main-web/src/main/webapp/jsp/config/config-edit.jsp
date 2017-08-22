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
        Edit config value
    </title>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<n:group2 titleText="Config entry" cssIcon="group-icon-document">
    <s:form theme="simple">
        <center>
            <n:field label="Key" cssContainer="force-margin-left30 force-width30">
                <s:textfield name="key" disabled="true" />
                <s:hidden name="key" />
            </n:field>
            <n:field label="Type" cssContainer="force-margin-left30 force-width30" >
                <s:textfield name="entry.type" disabled="true"/>
            </n:field>
            <n:field label="Current value" cssContainer="force-margin-left30 force-width30" >
                <s:textfield name="entry.value" disabled="true"/>
            </n:field>
            <n:field label="New value" cssContainer="force-margin-left30 force-width30" fielderror="newValue">
                <s:textfield name="newValue" />
            </n:field>
        </center>
        <hr class="buttons"/>
        <center>
            <input type="button" value="Back" onClick="history.back()">
            <s:submit value="Save" theme="simple" action="configEdit-save"/>
        </center>
    </s:form>
</n:group2>
</body>
</html>