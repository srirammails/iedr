<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Email Group Create</title>

    <s:head/>

    <script type="text/javascript" src="js/jquery.js"></script>

</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="emailtemplate_form" theme="simple">
    <table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td width="100%">
                <n:group2 titleText="New Email Group">
                    <n:field label="Name" fielderror="group.name">
                        <s:textfield name="group.name"/>
                    </n:field>
                    <n:field label="Visible in Console">
                        <s:checkbox name="group.visible" label="Visible in Console" theme="simple"/>
                    </n:field>
                </n:group2>
            </td>
        </tr>
    </table>
    <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="2" align="center">
                <hr class="buttons"/>
                <center>
                    <s:if test="%{hasPermission('emailgroup.create.button')}">
                        <s:submit value="Create" action="emailgroup-create" theme="simple" cssClass="xSave"/>
                    </s:if>
                    <s:submit value="Cancel" action="emailgroups-search" theme="simple"/>
                </center>
            </td>
        </tr>
    </table>
</s:form>

</body>
</html>