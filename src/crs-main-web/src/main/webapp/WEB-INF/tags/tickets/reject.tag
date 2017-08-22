<%@ attribute name="ticket" required="true" type="pl.nask.crs.ticket.Ticket" %>
<%@ attribute name="adminStatuses" required="true" type="java.util.List" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
	String dialogId = "rejectDialog" + ticket.getId();
	String openTriggerId = "openRejectDialog";
%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#<%=dialogId%>').jqm({trigger: false})
				  .jqmAddTrigger('#<%=openTriggerId%>')
				  .jqmAddClose($('.closeDialog'));
	})
</script>
<div id="<%=dialogId%>" class="jqmWindow">
	<n:group2 titleText="Reject" cssIcon="group-icon-reject">
		<center style="margin: 10px 0">
			Rejecting the ticket ${ticket.id} for the domain ${ticket.operation.domainNameField.newValue}
		</center>
		<center>
			<s:select list="rejectionStatuses" name="newAdminStatus" theme="simple" listKey="id" listValue="description"
						 value="#attr.adminStatusId" size="8"/>
		</center>
		<hr class="buttons"/>
		<center>
			<s:submit value="Reject" method="reject" theme="simple"/>
			<input type="button" value="Cancel" class="closeDialog"/>
		</center>
	</n:group2>
</div>
