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
        Force DSM State
    </title>
</head>
<body>

<s:set var="stateList" value="stateIds"/>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>

<n:group2 titleText="Force DSM Event" cssIcon="group-icon-document">
    <s:form theme="simple">
        <center>
            <n:field label="Domain name" hideTooltipGap="true"
                     cssContainer="force-margin-left30 force-width30" fielderror="wrapper.domainNames">
                <s:textarea name="wrapper.domainNames" value="%{#attr.wrapper.domainNames}" label="Domain name"
                             disabled="false" theme="simple" cssStyle="width:100%;"/>
            </n:field>
            <n:field label="States" hideTooltipGap="true"
                     cssContainer="force-margin-left30 force-width30">
                <s:select name="wrapper.state"  list="stateList" theme="simple"
                          headerKey="-1" cssStyle="width:100%;"/>
            </n:field>
            <n:field label="Remark" hideTooltipGap="true"
                     cssContainer="force-margin-left30 force-width30" fielderror="wrapper.remark">
                <s:textarea name="wrapper.remark" value="%{#attr.wrapper.remark}" label="remark"
                             disabled="false" theme="simple" cssStyle="width:100%;"/>
            </n:field>
        </center>
        <hr class="buttons"/>
        <center>
            <input type="button" value="Back" onClick="history.back()">
            <s:submit value="Force" theme="simple" action="forceDSMstate-force"/>
        </center>
    </s:form>
</n:group2>
</body>
</html>