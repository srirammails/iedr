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
    <title>Nic Handle Reset Password</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <s:head/>
    <sx:head/>
</head>
<body>

<s:set var="nicHandles" value="paginatedResult.list"/>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="nic-handle-reset-password" theme="simple">
	<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" align="center">
				<input type="hidden" name="nicHandleId" value="${nicHandle.nicHandleId}"/>
				<n:group2 titleText="Nichandle Info" cssIcon="group-icon-info">
					<n:field label="Nic Handle" hideTooltipGap="true" cssLabel="force-gap-left30 force-width-long">
						<div class="showValue">${nicHandle.nicHandleId}</div>
					</n:field>
					<n:field label="E-mail" hideTooltipGap="true" cssLabel="force-gap-left30 force-width-long">
						<div class="showValue">${nicHandle.email}</div>
					</n:field>
				</n:group2>
			</td>
		</tr>
		<tr>
			<td width="50%">
				<n:group2 titleText="Reset Password" cssIcon="group-icon-settings">
					<div style="height:52px;"></div>
					<hr class="buttons"/>
					<center><s:submit label="Reset" value="Reset" method="resetPassword"/></center>
				</n:group2>
			</td>
			<td width="50%">
				<n:group2 titleText="Set New Password" cssIcon="group-icon-password">
					<n:field label="New Password" hideTooltipGap="true" cssLabel="force-width-long">
						<s:password name="newPassword1" theme="simple"/>
					</n:field>
					<n:field label="Retype New&nbsp;Password" hideTooltipGap="true"
								cssLabel="force-width-long">
						<s:password name="newPassword2" theme="simple"/>
					</n:field>

					<hr class="buttons"/>
					<center><s:submit label="Save" value="Save" method="saveNewPassword"/></center>
				</n:group2>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<hr class="buttons"/>
				<center><s:submit value="Cancel" action="nic-handle-view" method="view" theme="simple"/></center>
			</td>
		</tr>
	</table>
</s:form>


</body>
</html>