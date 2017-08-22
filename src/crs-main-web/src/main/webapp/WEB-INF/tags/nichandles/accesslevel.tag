<%@ attribute name="label" %>
<%@ attribute name="name" %>
<%@ attribute name="wrapper" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags"%>

<%@tag import="java.util.List"%>
<%@tag import="java.util.ArrayList"%>

<%
	Boolean condition = (Boolean) jspContext.getVariableResolver().resolveVariable(wrapper+"."+name);

	if (condition==null) 
	    condition = false; 
	    //throw new IllegalArgumentException("Cannot resolve "+wrapper+"."+name);
	jspContext.setAttribute("condition", condition);
%>

<s:if test="#attr.condition">
<%	
	List list = (List) jspContext.getVariableResolver().resolveVariable("changeableFrom(\""+name+"\")");
	if (list==null) list = new ArrayList();
	jspContext.setAttribute("list", list);
%>	

	<n:field label="${label}" cssContainer="force-width330px force-gap-left30">
		<s:form>
			<s:if test="#attr.list.size()>0">
				<div class="access-combo">
					<s:select list="#attr.list" listKey="name" listValue="label" headerKey="" headerValue="(select)" name="changeTo" theme="simple"/>
				</div>
				<input type="hidden" name="changeFrom" value="${name}" />
				<div class="access-submit">
					<s:submit method="changePermission" label="Change" theme="simple"/>
				</div>
			</s:if>
		</s:form>
	</n:field>
</s:if>
