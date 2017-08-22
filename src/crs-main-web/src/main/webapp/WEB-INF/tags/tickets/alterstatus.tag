<%@ attribute name="ticket" required="true" type="pl.nask.crs.ticket.Ticket" %>
<%@ attribute name="adminStatuses" required="true" type="java.util.List" %>
<%@ attribute name="idPrefix" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
	if(idPrefix==null)
    	idPrefix = "";

	String dialogId = idPrefix + "alterStatusDialog" + ticket.getId();
	String openTriggerId = idPrefix + "openAlterStatusDialog" + ticket.getId();
%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#<%=dialogId%>').jqm({trigger: false})
				  .jqmAddTrigger('#<%=openTriggerId%>')
				  .jqmAddClose($('.closeDialog'));
	})
</script>
<div id="<%=dialogId%>" class="jqmWindow">
	<n:group2 titleText="Alter Status" cssIcon="group-icon-status">

		<form action="ticketbrowser-alterStatus.action" method="post">
			<center style="margin: 10px 0">
				Altering the admin status of the ticket ${ticket.id}
				for the domain ${ticket.operation.domainNameField.newValue}
			</center>
			<input type="hidden" name="id" value="${ticket.id}"/>
			<c:set var="adminStatusId" value="${ticket.adminStatus.id}"/>
			<center>
				<s:select list="adminStatuses" name="newAdminStatus" theme="simple" listKey="id" listValue="description"
							 value="#attr.adminStatusId" size="8"/>
			</center>
			<hr class="buttons"/>
			<center>
				<input type="Submit" value="Submit"/>
				<input type="button" value="Cancel" class="closeDialog"/>
			</center>
		</form>
	</n:group2>
</div>
