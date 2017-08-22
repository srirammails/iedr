<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nichandle" tagdir="/WEB-INF/tags/nichandles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Nic Handle Access Level
    	<s:if test="%{isHistory()} ">(History)</s:if>
    </title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <script type="text/javascript" src="js/tooltip.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#status-dialog').jqm({trigger: false})
                    .jqmAddTrigger($('#open-status-dialog'))
                    .jqmAddClose($('#close-status-dialog'));
        })
    </script>

    <s:if test="%{isHistory()}">
        <link href="<s:url value='/styles/skin-iedr-history/main.css'/>" rel="stylesheet" type="text/css" media="all"/>
    </s:if>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="nic-handle-access-level" theme="simple">
    <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="1" align="center">
                <input type="hidden" name="nicHandleId" value="${nicHandle.nicHandleId}"/>
                <n:group2 titleText="Nichandle Info" cssIcon="group-icon-info">
                    <n:field label="Nic Handle Id" hideTooltipGap="true" cssContainer="force-width330px force-gap-left30">
                        <div class="showValue">${nicHandle.nicHandleId}</div>
                    </n:field>
                    <s:if test="%{!isHistory()} "> 
                    <n:field label="Name" hideTooltipGap="true" cssContainer="force-width330px force-gap-left30">
                        <div class="showValue">${nicHandle.name}</div>
                    </n:field>
                    </s:if>
                </n:group2>
            </td>
        </tr>
        <tr>            
            <td colspan="1" align="center">
                <n:group2 titleText="User permissions" cssIcon="group-icon-info">
					<display:table name="allPermissions" id="row">
						<display:column title="Permission name" value="${row}"/>
						<display:column title="">
							<s:if test="%{wrapper.hasUserPermission(#attr.row)}">
								<s:url var="viewUrl" action="nic-handle-access-editPerms-removePermission"	includeParams="none">
									<s:param name="nicHandleId">${nicHandle.nicHandleId}</s:param>
									<s:param name="permissionName">${row}</s:param>
								</s:url>
								<s:a href="%{viewUrl}">
									<img src="images/skin-default/action-icons/delete.png" alt="Remove" title="Remove" />
								</s:a>
							</s:if>
							<s:else>
								<s:url var="viewUrl" action="nic-handle-access-editPerms-addPermission"	includeParams="none">
									<s:param name="nicHandleId">${nicHandle.nicHandleId}</s:param>
									<s:param name="permissionName">${row}</s:param>
								</s:url>
								<s:a href="%{viewUrl}">
									Add
								</s:a>
							</s:else>
						</display:column>
					</display:table>
                </n:group2>
            </td>
        </tr>
        <tr>
            <td colspan="1" align="center">
                <hr class="buttons"/>
                <center>
                    <s:submit value="Back" action="nic-handle-access-level" method="input" theme="simple"/>
                </center>
            </td>
        </tr>
    </table>
</s:form>
</body>
</html>