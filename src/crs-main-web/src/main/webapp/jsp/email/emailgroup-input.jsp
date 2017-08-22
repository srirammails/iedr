<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Email Group Edit</title>

    <s:head/>

    <script type="text/javascript" src="js/jquery.js"></script>

</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="emailgroup_form" theme="simple">
    <s:hidden name="id"/>
    <table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td width="100%">
                <n:group2 titleText="Email Group">
                    <s:hidden name="group.id"/>
                    <n:field label="EG_ID"><div class="showValue">${group.id}</div></n:field>
                    <n:field label="Name" fielderror="group.name">
                        <s:textfield name="group.name"/>
                    </n:field>
                    <n:field label="Visible in Console">
                        <s:checkbox name="group.visible" label="Visible in Console" theme="simple"/>
                    </n:field>
                    <s:if test="%{actionWillBeDestructive()}">
                        <div class="ctrl-container">
                            <div class="ctrl-label"></div>
                            <div id="invisibleGroupWarning" class="ctrl-field alert alert-warning">
                                <p>Setting this group to invisible will reset all email disabler settings
                                for all templates in this group.</p>
                                <p>Number of users that have blocked at least one email belonging to this group:
                                <strong><s:property value="affectedUsersCount"/></strong>
                                </p>
                                <p>Total number of disabled email templates from this group for all users:
                                <strong><s:property value="disablingsCount"/></strong></p>
                            </div>
                        </div>
                    </s:if>
                    <s:else>
                        <div class="ctrl-container">
                            <div class="ctrl-label"></div>
                            <div id="invisibleGroupWarning" class="ctrl-field alert alert-info">
                                <p>Setting this group to invisible is safe, no user has blocked any of its templates</p>
                            </div>
                        </div>
                    </s:else>
                </n:group2>
            </td>
        </tr>
    </table>
    <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="2" align="center">
                <hr class="buttons"/>
                <center>
                    <s:if test="%{hasPermission('emailgroup.save.button')}">
                        <s:submit value="Save" action="emailgroup-update" theme="simple" cssClass="xSave"/>
                    </s:if>
                    <s:submit value="Cancel" action="emailgroup-view" theme="simple"/>
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
    $("#emailgroup-edit_group_visible").change(function() {
        if ($(this).is(":checked")) {
            warning.hide();
        } else {
            warning.show();
        }
    });
});
</script>

</body>
</html>