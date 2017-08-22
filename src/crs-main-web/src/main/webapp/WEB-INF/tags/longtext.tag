<%@ tag import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="text" required="true" %>
<%@ attribute name="length" required="false" type="java.lang.Integer"%>

<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    if (length == null) length = 50;
    if (text == null) {
        text = "";
    } else {
        text = StringEscapeUtils.escapeHtml(text);
    }
    String displayedText = text;
    boolean tooltip = false;
    if (displayedText.length() > length) {
        tooltip = true;
        displayedText = text.substring(0, length) + "...";
        jspContext.setAttribute("textInTooltip", text);
    }
%>
<%=displayedText%>
<% if (tooltip) { %>
<n:tooltip id="${id}" text="${textInTooltip}" />
<% } %>