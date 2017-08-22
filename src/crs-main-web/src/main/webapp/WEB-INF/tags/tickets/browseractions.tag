<%@ attribute name="ticket" type="pl.nask.crs.ticket.Ticket"%>
<%@ attribute name="idPrefix" %>
<%
	//Object ticketRow = jspContext.getVariableResolver().resolveVariable(ticket);
	//jspContext.setAttribute("ticket", ticket);
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:div cssClass="ticket-row-images">
				<s:url var="reviseUrl" action="ticketrevise-input"
					includeParams="none">
					<s:param name="id">${ticket.id}</s:param>
				</s:url>
				<s:url var="viewUrl" action="ticketview" includeParams="none">
					<s:param name="id">${ticket.id}</s:param>
					<s:param name="previousAction">tickets-input</s:param>
				</s:url>
				<s:url var="checkInUrl" action="ticketbrowser-checkIn"
					includeParams="none">
					<s:param name="id">${ticket.id}</s:param>
				</s:url>
				<s:url var="checkOutUrl" action="ticketbrowser-checkOut"
					includeParams="none">
					<s:param name="id">${ticket.id}</s:param>
				</s:url>
				<s:url var="reassingUrl" action="ticketbrowser-reassing"
					includeParams="none">
					<s:param name="id">${ticket.id}</s:param>
				</s:url>
				<s:url var="alterStatusUrl" action="ticketbrowser-alterStatus"
					includeParams="none">
					<s:param name="id">${ticket.id}</s:param>
				</s:url>

				<s:if test="allowCheckOut(#attr.ticket) && hasPermission('ticket.checkOut')">
					<s:a href="%{checkOutUrl}">
						<img src="images/skin-default/action-icons/ticket.checkout.png" alt="Check Out"
							title="Check Out" />
					</s:a>
				</s:if>
				<s:elseif test="allowCheckIn(#attr.ticket) && hasPermission('ticket.checkIn')">
					<img src="images/skin-default/action-icons/ticket.checkin.png" alt="Check In"
						title="Check In" id="${idPrefix}openCheckInDialog${ticket.id}" />
				</s:elseif>
				<s:if test="allowAlterStatus(#attr.ticket) && hasPermission('ticket.alterStatus')">
					<img src="images/skin-default/action-icons/ticket.alterstatus.png" alt="Alter Status"
						title="Alter Status" id="${idPrefix}openAlterStatusDialog${ticket.id}" />
				</s:if>
				<s:if test="allowReassign(#attr.ticket)  && hasPermission('ticket.reassign')">
					<img src="images/skin-default/action-icons/ticket.reassing.png" alt="Reassign"
						title="Reassign" id="${idPrefix}openReassignDialog${ticket.id}" />
				</s:if>
				<s:if test="allowRevise(#attr.ticket)  && hasPermission('ticket.revise')">
					<s:a href="%{reviseUrl}">
						<img src="images/skin-default/action-icons/ticket.revise.png" alt="Revise and Edit"
							title="Revise and Edit" />
					</s:a>
				</s:if>
				<s:else>
					<s:if test="hasPermission('ticket.view')">
						<s:a href="%{viewUrl}">
							<img src="images/skin-default/action-icons/ticket.png" alt="View" title="View" />
						</s:a>
					</s:if>
				</s:else>
			</s:div>
