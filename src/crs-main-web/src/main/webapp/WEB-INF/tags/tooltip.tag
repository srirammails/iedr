<%@ tag import="java.util.List" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="text" required="true" %>
<%@ attribute name="width" required="false" %>
<%@ attribute name="height" required="false" %>

<img src="images/skin-default/action-icons/info.png" alt="" class="tooltipped" id="${id}"/>
<%
    text = text.replaceAll("\r\n", "\n");
    text = text.replaceAll("\n", "<br/>");
    String style = "display: none; ";
    if (width != null) style += "width: " + width + "; ";
    if (height != null) style += "height: " + height + "; ";
%>
<div id="tooltip-for-${id}" class="tooltip" style="<%=style%>">
    <%=text%>
</div>
