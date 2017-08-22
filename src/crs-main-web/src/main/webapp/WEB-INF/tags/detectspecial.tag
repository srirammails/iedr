<%@ tag import="java.util.Calendar" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<%
	Calendar myCal = Calendar.getInstance();
	int m = myCal.get(Calendar.MONTH) + 1;
	int d = myCal.get(Calendar.DAY_OF_MONTH);

	boolean isXmas		= (m == 12 && d >= 18);
	boolean isEaster	= (m == 3 && d > 20) || (m == 4 && d < 26);
	boolean isPatrick	= (m == 3 && d == 17);
%>

<%if (isXmas) { %>

<link href="<s:url value='/styles/skin-xmas/main.css'/>" rel="stylesheet" type="text/css" media="all"/>

<% } else if (isEaster) { %>

<link href="<s:url value='/styles/skin-easter/main.css'/>" rel="stylesheet" type="text/css" media="all"/>

<% } else if (isPatrick) { %>

<link href="<s:url value='/styles/skin-patrick/main.css'/>" rel="stylesheet" type="text/css" media="all"/>

<% } else { %>

<%--<link href="<s:url value='/styles/main.css'/>" rel="stylesheet" type="text/css" media="all"/>--%>

<% } %>



