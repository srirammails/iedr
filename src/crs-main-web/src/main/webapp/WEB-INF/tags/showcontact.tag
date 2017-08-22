<%@ attribute name="nicHandle" required="true" %>
<%@ attribute name="actionName" required="true" %>
<%@ attribute name="returnTo" required="false"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<% if(nicHandle!=null && nicHandle.trim().length()!=0) { %>
<s:url var="aaa" action="nic-handle-view" includeParams="none">
	<s:param name="nicHandleId">${nicHandle}</s:param>
	<s:param name="previousAction">${actionName}</s:param>					  
</s:url>
<%--<s:a href="%{aaa}">...</s:a>--%>
<div class="showValue-icon">
	<s:a href="%{aaa}">
		<img src="images/skin-default/action-icons/details.png" title="Select"/>
	</s:a>
</div>
<% } %>
