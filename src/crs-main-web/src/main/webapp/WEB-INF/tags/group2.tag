<%@ attribute name="titleText" description="Text to be displayed on header" %>
<%@ attribute name="cssIcon" description="Group icon css class" %>

<%
	if (titleText == null) titleText = "";
	if (cssIcon == null) cssIcon = "";
	
	String ua = request.getHeader("User-Agent");
	boolean isFF = false;
	boolean isIE = false;
	String browserVersionMajor = "";
	int browserNameIndex = 0;
	int browserVersionNumber = 0;

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

<div class="group">

<%if (isFF) { %>

	<div class="GH">
		<div class="GHLC"></div>
		<div class="GHRC"></div>
		<div class="GHTB">
			<div class="GHTB-bar">
				<% if (cssIcon != null) { %>
					<div class="GHTB-icon">
						<div class="${cssIcon}">&nbsp;</div>
					</div>
				<% } %>
				<div class="GHTB-title">${titleText}</div>
			</div>
		</div>
	</div>

	<div class="GC">
		<div class="GCCA"><jsp:doBody/></div>
	</div>

	<div class="GF">
		<div class="GFLC"></div>
		<div class="GFRC"></div>
		<div class="GFFB"></div>
	</div>

<% } else if (isIE) { %>

	<div class="GH">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="GHLC">&nbsp;</td>
				<td class="GHTB">
					<% if (cssIcon != null) { %>
						<div class="GHTB-icon ${cssIcon}">&nbsp;</div>
					<% } %>
					<div class="GHTB-title">${titleText}</div>
				</td>
				<td class="GHRC">&nbsp;</td>
			</tr>
		</table>
	</div>

	<div class="GC">
		<div class="GCCA"><jsp:doBody/></div>
	</div>

	<div class="GF">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="GFLC">&nbsp;</td>
				<td class="GFFB">&nbsp;</td>
				<td class="GFRC">&nbsp;</td>
			</tr>
		</table>
	</div>

<% } else { %>
	<%-- todo: pixel: temp version as same as for FF --%>

	<div class="GH">
		<div class="GHLC"></div>
		<div class="GHRC"></div>
		<div class="GHTB">
			<div class="GHTB-bar">
				<% if (cssIcon != null) { %>
					<div class="GHTB-icon">
						<div class="${cssIcon}">&nbsp;</div>
					</div>
				<% } %>
				<div class="GHTB-title">${titleText}</div>
			</div>
		</div>
	</div>

	<div class="GC">
		<div class="GCCA"><jsp:doBody/></div>
	</div>

	<div class="GF">
		<div class="GFLC"></div>
		<div class="GFRC"></div>
		<div class="GFFB"></div>
	</div>

<% } %>

</div>
