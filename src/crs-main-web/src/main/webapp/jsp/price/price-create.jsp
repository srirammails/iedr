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
        Create Product Price
    </title>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<n:group2 titleText="Product Price" cssIcon="group-icon-document">
    <s:form theme="simple">
        <center>
            <n:field label="Id" cssContainer="force-margin-left30 force-width30" fielderror="wrapper.id">
                <s:textfield name="wrapper.id"/>
            </n:field>
            <n:field label="Description" cssContainer="force-margin-left30 force-width30" fielderror="wrapper.description">
                <s:textfield name="wrapper.description"/>
            </n:field>
            <n:field label="Price" cssContainer="force-margin-left30 force-width30" fielderror="wrapper.price">
                <s:textfield name="wrapper.price"/>
            </n:field>
            <n:field label="Duration" cssContainer="force-margin-left30 force-width30" fielderror="wrapper.duration">
                <s:textfield name="wrapper.duration"/>
            </n:field>
            <n:field label="Valid From" cssContainer="force-margin-left30 force-width30" fielderror="wrapper.validFrom">
                <n:datefield field="wrapper.validFrom"/>
            </n:field>
            <n:field label="Valid To" cssContainer="force-margin-left30 force-width30" fielderror="wrapper.validTo">
                <n:datefield field="wrapper.validTo"/>
            </n:field>
            <n:field label="For Registration" cssContainer="force-margin-left30 force-width30">
                <s:checkbox name="wrapper.forRegistration"/>
            </n:field>
            <n:field label="For Renewal" cssContainer="force-margin-left30 force-width30">
                <s:checkbox name="wrapper.forRenewal"/>
            </n:field>
            <n:field label="For Direct" cssContainer="force-margin-left30 force-width30">
                <s:checkbox name="wrapper.direct"/>
            </n:field>
        </center>
        <hr class="buttons"/>
        <center>
            <input type="button" value="Back" onClick="history.back()">
            <s:submit value="Create" theme="simple" action="priceCreate-create"/>
        </center>
    </s:form>
</n:group2>
</body>
</html>