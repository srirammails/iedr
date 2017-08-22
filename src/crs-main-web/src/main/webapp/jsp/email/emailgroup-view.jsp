<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Email Group View</title>

    <s:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="group" theme="simple">
    <s:hidden name="id"/>

    <n:group2 titleText="Email Group" cssIcon="group-icon-account">
        <n:field label="EG_ID"><div class="showValue">${group.id}</div></n:field>
        <n:field label="Name"><div class="showValue">${group.name}</div></n:field>
        <n:field label="Visible in Console"><n:showBoolean value="${group.visible}" /></n:field>
    </n:group2>

    <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="2" align="center">
                <hr class="buttons"/>
                <center>
                    <s:if test="%{hasPermission('emailgroup.delete.button')}">
                        <s:submit value="Delete" action="emailgroup-delete" theme="simple" cssClass="xSave"/>
                    </s:if>
                    <s:if test="%{hasPermission('emailgroup.save.button')}">
                        <s:submit value="Edit" action="emailgroup-edit" theme="simple"/>
                    </s:if>
                    <s:submit value="Cancel" action="emailgroups-search" theme="simple"/>
                </center>
            </td>
        </tr>
    </table>

</s:form>

</body>
</html>
