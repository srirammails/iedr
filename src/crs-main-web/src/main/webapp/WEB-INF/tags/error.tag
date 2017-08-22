<%@ attribute name="errorClass" description="Text to display on section title" %>
<%@ attribute name="errorTitle" description="Text to display on section title" %>
<%@ attribute name="customErrorCondition" type="java.lang.Boolean" description="if provided, error would only be displayed, if the condition is true regardless of the hasActionErrors() value" %>
<%@ attribute name="displayActionErrors" type="java.lang.Boolean" description="if false, action errors would not be displayed (only the body). Default is true." %>

<%@taglib prefix="s" uri="/struts-tags" %>

<%
	if (displayActionErrors == null) {
	    displayActionErrors = true;
	}

	jspContext.setAttribute("displayActionErrors", displayActionErrors);
	Boolean displayError;
	if (customErrorCondition != null)
	    displayError = customErrorCondition;
	else
	    displayError = (Boolean) jspContext.getVariableResolver().resolveVariable("hasActionErrors()");		
%>


<% if (displayError) { %>
	<div class="group">
		<div class="group-header">

			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="lu-corner-error">&nbsp;</td>
					<td class="up-corner-error">
						<% if (errorClass != null) { %>
						<div class="${errorClass}">&nbsp;</div>
						<% } %>
						<p>${errorTitle}</p>
					</td>
					<td class="ru-corner-error">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div class="group-content-error">
			<% if (displayActionErrors) { %>
				<s:actionerror/>
			<% } %>
			<jsp:doBody/>
		</div>
		<div class="group-bottom-error">
			<div class="group-bottom-error-fix">
				<div class="ld-corner-round-error"></div>
				<div class="rd-corner-round-error"></div>
				<div class="dn-corner-round-error"></div>
			</div>
		</div>
	</div>
<% } %>