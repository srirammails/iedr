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
    <title>Nic Handle Access Level</title>
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
    
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

                <s:form action="nic-handle-access-editGroups" theme="simple">                

    <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="1" align="center">
                <input type="hidden" name="nicHandleId" value="${nicHandle.nicHandleId}"/>
                <n:group2 titleText="Nichandle Info" cssIcon="group-icon-info">
                    <n:field label="Nic Handle Id" hideTooltipGap="true" cssContainer="force-width330px force-gap-left30">
                        <div class="showValue">${nicHandle.nicHandleId}</div>
                    </n:field>
                    <n:field label="Name" hideTooltipGap="true" cssContainer="force-width330px force-gap-left30">
                        <div class="showValue">${nicHandle.name}</div>
                    </n:field>
                </n:group2>
            </td>
        </tr>
        <tr>
            <td colspan="1" align="center">
                <n:group2 titleText="User groups" cssIcon="group-icon-info">
                	<s:if test="!fullAccess">
                		<s:iterator value="allGroups" var="group">                			
                			<nichandle:accesslevel label="${group.label}" wrapper="wrapper.permissionGroupsWrapper" name="${group.name}"/>                		
						</s:iterator>
					</s:if>            
					<s:if test="fullAccess">			
						<s:iterator value="allGroups" var="group">			
                			<n:field label="${group.label}">
                				<s:checkbox name="wrapper.permissionGroupsWrapper.%{#attr.group.name}" label="Guest" disabled="false" theme="simple"/>                				
                			</n:field>                		
						</s:iterator>			                    
                    </s:if>
                </n:group2>
            </td>
        </tr>
        <tr>
            <td colspan="1" align="center">
                <hr class="buttons"/>
                <center>
						 <s:if test="fullAccess">
						 <s:submit value="Save" method="save" theme="simple"/>
						 </s:if>
                    <input type="button" value="Cancel" onClick="history.back()">
                </center>
            </td>
        </tr>
    </table>
                </s:form>



</body>
</html>