<%@taglib prefix="s" uri="/struts-tags" %>

<%
	String ua = request.getHeader("User-Agent");
	boolean isFirefox = (ua != null && ua.indexOf("Firefox/") != -1);
	boolean isMSIE = (ua != null && ua.indexOf("MSIE") != -1);
//	boolean isNetscape= ( ua != null && ua.indexOf( "netscape6" ) != -1 );
//	boolean isMozilla = ( ua != null && ua.indexOf( "mozilla" ) != -1 );

	response.setHeader("Vary", "User-Agent");
%>

<%if (isFirefox) { %>

<link href="<s:url value='/styles/browser/FF.css'/>" rel="stylesheet" type="text/css" media="all"/>

<% } else if (isMSIE) { %>

<link href="<s:url value='/styles/browser/IE.css'/>" rel="stylesheet" type="text/css" media="all"/>

<% } else { %>

<link href="<s:url value='/styles/browser/FF.css'/>" rel="stylesheet" type="text/css" media="all"/>

<% } %>



