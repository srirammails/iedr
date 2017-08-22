<%@attribute name="hasPermission" required="true"
				 description="Required permission name to check"
%>
<%@taglib prefix="s" uri="/struts-tags" %>

<%
	String permissionName;

	if (hasPermission != null){
		permissionName = "";
	}else{
		permissionName = hasPermission;
	}

	String testLiteral	= "";
	testLiteral += "#userkey!=null && #permissionkey!=null && #permissionkey.hasPermission(#userkey,'";
	testLiteral += permissionName;
	testLiteral += "')";
%>

<s:set name="userkey" value="#session.get('user-key')"/>
<s:set name="permissionkey" value="#session.get('permission-key')"/>

<s:if test="<%= testLiteral %>">
	<jsp:doBody/>
</s:if>


<%--<s:if test="#session.get('user-key')!=null && #session.get('permission-key')!=null && #session.get('permission-key').hasPermission(#session.get('user-key'), 'nichandle-edit')">--%>
<%--<s:if test="#userkey!=null && #permissionkey!=null && #permissionkey.hasPermission(#userkey, 'nichandle-edit')">--%>
