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
            <td colspan="2" align="center">
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
            <td colspan="2" align="center">
                <n:group2 titleText="User Groups" cssIcon="group-icon-info">
					<s:iterator value="userGroups" var="group">
                		<n:field label="${group.label}" cssContainer="force-width330px force-gap-left30"/>
					</s:iterator>
                </n:group2>
            </td>
            </tr>
            <tr>
            <td colspan="1" align="center">
                <n:group2 titleText="Group permissions" cssIcon="group-icon-info">
					<s:iterator value="groupPermissions" var="perm">
                		<n:field label="${perm.id}" cssContainer="force-width330px force-gap-left30"/>
					</s:iterator>
                </n:group2>
            </td>
            <td colspan="1" align="center">
                <n:group2 titleText="User permissions" cssIcon="group-icon-info">
					<s:iterator value="userPermissions" var="perm">
                		<n:field label="${perm}" cssContainer="force-width330px force-gap-left30"/>
					</s:iterator>
                </n:group2>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <hr class="buttons"/>
                <center>
                	<s:if test="%{isHistory()}">                    
                        <s:hidden name="previousAction"/>
						<s:if test="%{hasPermission('domain.current.button')}">
							<s:submit value="Current" action="nic-handle-access-level-input" theme="simple"/>
						</s:if>                    
                    	<s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
                	</s:if>
                	<s:else>
    	            	<s:if test="%{hasPermission('accessLevel.edit.button')}">
	                    	<s:submit label="Edit groups" value="Edit groups" action="nic-handle-access-editGroups-edit"/>
                    	</s:if>
                    	<s:if test="%{hasPermission('accessLevelPerms.edit.button')}">
	                    	<s:submit label="Edit user permissions" value="Edit user permissions" action="nic-handle-access-editPerms-edit" cssStyle="width: 150px;"/>
                    	</s:if>
                    	<s:submit value="Cancel" action="nic-handle-view" method="view" theme="simple"/>
                    </s:else>
                </center>
            </td>
        </tr>
    </table>
</s:form>

<n:group2 titleText="History" cssIcon="group-icon-history">
<s:set var="page" value="#attr.tableParams.page"/>
    <s:set var="selected" value="#attr.historicalSelected"/> 
    <s:set var="selectedPage" value="#attr.selectedPage"/> 
    <display:table name="paginatedHistory" id="tableRow" class="result" requestURI="" excludedParams="firstSearch resetSearch">
        <c:set var="cssClass" value="${((selected == (tableRow_rowNum-1)) && (page == selectedPage))? 'selected' : ''}"/>
        <display:column title="Date" class="${cssClass}">
            <s:date name="#attr.tableRow.changeDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Nic Handle" property="changedBy" class="${cssClass}"/>
        <display:column title="Groups" class="${cssClass}">
        <s:iterator value="getSortedGroups(#attr.tableRow.object.permissionGroups)" var="group" status="rowStatus">
            ${group.label}<s:if test="!#rowStatus.last">, </s:if>
        </s:iterator>
        </display:column>
        <display:column title="" class="${cssClass}">
            <s:set var="nicHandleId" value="#attr.result.object.id"/>
            <s:set var="prevAction" value="#attr.previousAction"/>
            <s:url var="historicalUserView" action="nic-handle-access-level-input" includeParams="none">
                <s:param name="changeId" value="#attr.result.changeId"/>
                <s:param name="id" value="%{id}"/>
                <s:param name="previousAction" value="%{prevAction}"/>
            </s:url>
            <s:a href="%{historicalUserView}">
                <img src="images/skin-default-history/action-icons/nichandle.png" alt="View" title="View"/>
            </s:a>
        </display:column>
    </display:table>
</n:group2>
</body>
</html>