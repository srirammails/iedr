<%@ attribute name="titleText" description="Text to be displayed on header" %>
<%@ attribute name="cssIcon" description="Group icon css class" %>
<%@ attribute name="customErrorCondition" type="java.lang.Boolean" description="if provided, error would only be displayed, if the condition is true regardless of the hasActionErrors() value" %>
<%@ attribute name="displayActionErrors" type="java.lang.Boolean" description="if false, action errors would not be displayed (only the body). Default is true." %>

<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	String ua = request.getHeader("User-Agent");
	boolean isFF = false;
	boolean isIE = false;
	String browserVersionMajor = "";
	int browserNameIndex = 0;
	int browserVersionNumber = 0;

	if (displayActionErrors == null) {
	    displayActionErrors = true;
	}

	jspContext.setAttribute("displayActionErrors", displayActionErrors);
	Boolean displayError;
	if (customErrorCondition != null)
	    displayError = customErrorCondition;
	else
	    displayError = (Boolean) jspContext.getVariableResolver().resolveVariable("hasActionErrors()");


	if(ua != null) {
		// detect FF and its major version number
		browserNameIndex = ua.indexOf("Firefox/");
		isFF = (browserNameIndex != -1);
		if(isFF)
			browserVersionMajor = ua.substring(browserNameIndex + 8, browserNameIndex + 8 + 1);

		// detect IE and its major version number (refuse Netscape and Opera)
		browserNameIndex = ua.indexOf("MSIE");
		isIE = (browserNameIndex != -1) && (ua.indexOf("Netscape") == -1) && (ua.indexOf("Opera") == -1);
		if(isIE)
			browserVersionMajor = ua.substring(browserNameIndex + 5, browserNameIndex + 5 + 1);

		try
		{
			browserVersionNumber = Integer.parseInt(browserVersionMajor);
		}
		catch(NumberFormatException ex)
		{
			browserVersionNumber = 0;
		}
	}

	response.setHeader("Vary", "User-Agent");
%>

<%-- template
<%if (isFF && browserVersionNumber < 3) { %>
<% } else if (isFF && browserVersionNumber >= 3) { %>
<% } else if (isIE && browserVersionNumber < 7) { %>
<% } else if (isIE && browserVersionNumber >=7) { %>
<% } else { %>
<% } %>
--%>

<% if (displayError) { %>
<div class="group">

<%if (isFF) { %>

	<div class="EH">
		<div class="EHLC"></div>
		<div class="EHRC"></div>
		<div class="EHTB">
			<div class="EHTB-bar">
				<% if (cssIcon != null) { %>
					<div class="EHTB-icon">
						<div class="${cssIcon}">&nbsp;</div>
					</div>
				<% } %>
				<div class="EHTB-title">${titleText}</div>
			</div>
		</div>
	</div>

	<div class="EC">
		<div class="ECCA">
			<% if (displayActionErrors) { %>
				<s:actionerror/>
			<% } %>
			<jsp:doBody/>
		</div>
	</div>

	<div class="EF">
		<div class="EFLC"></div>
		<div class="EFRC"></div>
		<div class="EFFB"></div>
	</div>

<% } else if (isIE) { %>

	<div class="EH">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="EHLC">&nbsp;</td>
				<td class="EHTB">
					<% if (cssIcon != null) { %>
						<div class="EHTB-icon ${cssIcon}">&nbsp;</div>
					<% } %>
					<div class="EHTB-title">${titleText}</div>
				</td>
				<td class="EHRC">&nbsp;</td>
			</tr>
		</table>
	</div>

	<div class="EC">
		<div class="ECCA">
			<% if (displayActionErrors) { %>
				<s:actionerror/>
			<% } %>
			<jsp:doBody/>
		</div>
	</div>

	<div class="EF">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="EFLC">&nbsp;</td>
				<td class="EFFB">&nbsp;</td>
				<td class="EFRC">&nbsp;</td>
			</tr>
		</table>
	</div>

<% } else { %>
<%-- todo: pixel: temp version as same as for FF --%>

<div class="EH">
	<div class="EHLC"></div>
	<div class="EHRC"></div>
	<div class="EHTB">
		<div class="EHTB-bar">
			<% if (cssIcon != null) { %>
				<div class="EHTB-icon">
					<div class="${cssIcon}">&nbsp;</div>
				</div>
			<% } %>
			<div class="EHTB-title">${titleText}</div>
		</div>
	</div>
</div>

<div class="EC">
	<div class="ECCA">
		<% if (displayActionErrors) { %>
			<s:actionerror/>
		<% } %>
		<jsp:doBody/>
	</div>
</div>

<div class="EF">
	<div class="EFLC"></div>
	<div class="EFRC"></div>
	<div class="EFFB"></div>
</div>

<% } %>

</div>
<% } %>
