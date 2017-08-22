<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
			pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Ticket View</title>
</head>
<body>
<!--<div class="contentheader">-->
<!--Ticket Reject-->
<!--</div>-->

<div class="group">
	<div class="group-header">
		<table cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td class="group-icon group-icon-status">&nbsp;</td>
				<td class="up-corner-h2"><p>Rejection Status</p></td>
				<td class="ru-corner-h2">&nbsp;</td>
			</tr>
		</table>
	</div>
	<div class="group-content">
		<s:form name="ticket_form" theme="simple">
			<table class="form" width="100%">
				<tr>
					<td>
						<center>
							<select>
								<option>New</option>
								<option>Passed</option>
								<option>Hold Update</option>
								<option>Hold Paperwork</option>
								<option>Stalled</option>
								<option>Renew</option>
								<option>Finance Holdup</option>
								<option>Cancelled</option>
							</select>
						</center>
					</td>
				</tr>
				<tr>
					<td>
						<center>
							<s:submit value="Reject" action="tickets-input" theme="simple"/>
							<s:submit value="Cancel" method="revise" theme="simple"/>
						</center>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</div>

</body>
</html>