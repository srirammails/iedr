<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="e" tagdir="/WEB-INF/tags/emailtemplates" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Email Edit</title>

    <s:head/>

    <script type="text/javascript" src="js/jquery.js"></script>

</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="emailtemplate_form" theme="simple">
    <s:hidden name="id"/>
    <table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td width="100%">
                <n:group2 titleText="Email Template">
                    <s:hidden name="template.id"/>
                    <n:field label="E_ID"><div class="showValue">${template.id}</div></n:field>
                    <n:field label="Group" fielderror="template.group.id">
                        <select name="template.group.id">
                           <option value="-1" <s:if test="template.group.id == -1">selected="selected"</s:if> data-visible="false">Default</option>
                           <s:iterator value="groups">
                              <option
                                value="<s:property value="id" />"
                                data-visible="<s:property value="visible"/>"
                                <s:if test="id == template.group.id">selected="selected"</s:if> ><s:property value="name"/></option>
                           </s:iterator>
                        </select>
                    </n:field>
                    <div class="ctrl-container">
                        <div class="ctrl-label"></div>
                        <div id="invisibleGroupWarning" class="ctrl-field alert alert-warning">
                            <p>Selected group is invisible.</p>
                            <p>Assigning this template to an invisible group will cause all disabling of this template to be removed.</p>
                            <p>Number of users that have blocked this email:
                            <strong><s:property value="affectedUsersCount"/></strong>
                            </p>
                        </div>
                    </div>
                    <n:field label="Subject"><div class="showValue"><c:out value="${template.subject}"/></div></n:field>
                    <n:field label="Text"><div class="showValue"><c:out value="${template.text}"/></div></n:field>
                    <n:field label="Internal Recipients"><div class="showValue"><e:recipientsList to="${template.internalToList}" cc="${template.internalCcList}" bcc="${template.internalBccList}"/></div></n:field>
                    <n:field label="External Recipients"><div class="showValue"><e:recipientsList to="${template.toList}" cc="${template.ccList}" bcc="${template.bccList}"/></div></n:field>
                    <n:field label="Active"><n:showBoolean value="${template.active}" /></n:field>
                    <n:field label="Reason for sending">
                        <s:textfield name="template.sendReason"/>
                    </n:field>
                    <n:field label="Allow suppression">
                        <s:checkbox name="template.suppressible" label="Allow suppression" theme="simple"/>
                    </n:field>
                    <n:field label="Why cannot be disabled (tooltip)">
                        <s:textfield name="template.nonSuppressibleReason"/>
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
                    <s:if test="%{hasPermission('emailtemplate.save.button')}">
                        <s:submit value="Save" action="emailtemplate-save" theme="simple" cssClass="xSave"/>
                    </s:if>
                    <s:submit value="Cancel" action="emailtemplate-view" theme="simple"/>
                </center>
            </td>
        </tr>
    </table>
</s:form>

<script type="text/javascript">
$(document).ready(function() {
    var warning = $("#invisibleGroupWarning").parent(".ctrl-container");
    warning.css({height: "auto", overflow: "auto"});
    warning.hide();
    $("select[name=template.group.id]").change(function() {
        var group_id = $(this).val();
        if ($("option[value="+group_id+"]", $(this)).attr("data-visible") == "true") {
            warning.hide();
        } else {
            warning.show();
        }
    });
});
</script>

</body>
</html>