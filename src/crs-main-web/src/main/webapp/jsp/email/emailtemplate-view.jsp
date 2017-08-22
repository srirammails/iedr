<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="e" tagdir="/WEB-INF/tags/emailtemplates" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Email View</title>

    <s:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="template" theme="simple">
    <s:hidden name="id"/>

    <n:group2 titleText="Email Template" cssIcon="group-icon-account">
        <n:field label="E_ID"><div class="showValue">${template.id}</div></n:field>
        <n:field label="Group"><div class="showValue">${template.group.name}</div></n:field>
        <n:field label="Subject"><div class="showValue"><c:out value="${template.subject}"/></div></n:field>
        <n:field label="Text"><div class="showValue"><c:out value="${template.text}"/></div></n:field>
        <n:field label="Internal Recipients"><div class="showValue"><e:recipientsList to="${template.internalToList}" cc="${template.internalCcList}" bcc="${template.internalBccList}"/></div></n:field>
        <n:field label="External Recipients"><div class="showValue"><e:recipientsList to="${template.toList}" cc="${template.ccList}" bcc="${template.bccList}"/></div></n:field>
        <n:field label="Active"><n:showBoolean value="${template.active}" /></n:field>
        <n:field label="Reason for sending"><div class="showValue"><c:out value="${template.sendReason}"/></div></n:field>
        <n:field label="Allow suppression"><n:showBoolean value="${template.suppressible}" /></n:field>
        <n:field label="Why cannot be disabled (tooltip)"><div class="showValue"><c:out value="${template.nonSuppressibleReason}"/></div></n:field>
    </n:group2>

    <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="2" align="center">
                <hr class="buttons"/>
                <center>
                    <s:if test="%{hasPermission('emailtemplate.save.button')}">
                        <s:submit value="Edit" action="emailtemplate-input" theme="simple"/>
                    </s:if>
                    <s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
                </center>
            </td>
        </tr>
    </table>

</s:form>

</body>
</html>
