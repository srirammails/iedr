<%@ attribute name="ticket" required="true" type="pl.nask.crs.ticket.Ticket" %>
<%@ attribute name="adminStatuses" required="true" type="java.util.List" %>
<%@ attribute name="idPrefix" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
	if(idPrefix==null)
	    idPrefix = "";

	String dialogId = idPrefix + "checkInDialog" + ticket.getId();
	String openTriggerId = idPrefix + "openCheckInDialog" + ticket.getId();
%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#<%=dialogId%>').jqm({trigger: false})
				  .jqmAddTrigger('#<%=openTriggerId%>')
				  .jqmAddClose($('.closeDialog'));
	})
</script>
<div id="<%=dialogId%>" class="jqmWindow">
	<form action="ticketbrowser-checkIn.action" method="post">
		<n:group2 titleText="Check In" cssIcon="group-icon-ticketcheckin">
			<center style="margin: 10px 0">
				Checking in the ticket ${ticket.id} for the domain ${ticket.operation.domainNameField.newValue}
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
		</n:group2>
	</form>
</div>
