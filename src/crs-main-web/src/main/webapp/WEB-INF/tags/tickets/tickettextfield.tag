<%@ attribute name="label" required="true" %>
<%@ attribute name="field" required="true" rtexprvalue="false"
              description="input field name - must be an instance of SimpleDomainFieldChangeWrapper" %>
<%@ attribute name="editable" description="if set to true, field will be editable. False is a default"
              type="java.lang.Boolean" %>
<%@ attribute name="renderInput" type="java.lang.Boolean"
              description="if set to false, input field will not be rendered in read-only mode (true is default)" %>
<%@ attribute name="frDisabled" type="java.lang.Boolean" %>
<%@ attribute name="fieldSuffix" rtexprvalue="false" %>
<%@ attribute name="tooltip" %>
<%@ attribute name="id" %>
<%@ attribute name="cssClass" required="false" %>


<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>

<%@tag import="pl.nask.crs.web.ticket.wrappers.SimpleDomainFieldChangeWrapper" %>
<%@tag import="java.util.List" %>

<%
    String cssClassEx;

    if (editable == null)
        editable = false;

    if (fieldSuffix == null)
        fieldSuffix = "newValue";

    if (frDisabled == null)
        frDisabled = false;

    if (renderInput == null)
        renderInput = true;

    if (id == null)
        id = "";
    else
        id = "id=\"" + id + "\" ";

    if (cssClass == null)
        cssClassEx = "";
    else
        cssClassEx = cssClass;

    SimpleDomainFieldChangeWrapper fieldChange = (SimpleDomainFieldChangeWrapper) jspContext.getVariableResolver().resolveVariable(field);

    if (fieldChange == null)
        throw new IllegalArgumentException("cannot access field: " + field);

    if (fieldChange != null && fieldChange.isModification())
        cssClassEx = cssClassEx + " modification";

    String cssClassEx2 = "";
    if (cssClassEx.length() > 0)
        cssClassEx2 = "class=\"" + cssClassEx + "\"";
	
    String inputFieldName = field + "." + fieldSuffix;
    Object inputFieldValue = jspContext.getVariableResolver().resolveVariable(inputFieldName);
    if (inputFieldValue == null)
        inputFieldValue = "";

    String frFieldName = field + ".failureReason.description";

    boolean hasTooltip = tooltip != null && tooltip.trim().length() > 0;
    String tooltipId = "id" + inputFieldName.replaceAll("[\\.\\[\\]]", "_");

    boolean showFailureReasonsList = true;
    List failureReasonsList = fieldChange.getFailureReasonList();

    String frIdFieldName = field + ".failureReasonId";
    Integer n = (Integer) jspContext.getVariableResolver().resolveVariable(frIdFieldName);

    jspContext.setAttribute("failureReasonsList", failureReasonsList);
    jspContext.setAttribute("frFieldName", frFieldName);
    jspContext.setAttribute("frIdFieldName", frIdFieldName);
    jspContext.setAttribute("frIdValue", n);
    jspContext.setAttribute("tooltipId", tooltipId);
%>

<div class="ctrl-container">

	<div class="ctrl-label">
		<div class="ctrl-label-inner">
			${label}
		</div>
		<div class="ctrl-tooltip">
			<% if (hasTooltip) { %>
				<n:tooltip id="${tooltipId}" text="${tooltip}"/>
			<% } %>
		</div>
	</div>

	<div class="ctrl-field">
        <% if (editable) { %>
		 <div class="ctrl-field-inner">
	        <input <%=id%> <%=cssClassEx2%> type="text" value="<%= inputFieldValue%>" name="<%= inputFieldName %>"/>
			  <jsp:doBody/>
		  </div>
        <% } else {
			String newClass = "ctrl-field-inner " + cssClassEx; %>
        <div class="<%=newClass%>">
        	<div class="showValue">
            <%= inputFieldValue%>
            </div>
            <jsp:doBody/>
		  </div>
        <% } %>

		<div class="ctrl-failure-reason">
			 <%
				  if (!frDisabled) {%>
			 <s:select list="#attr.failureReasonsList" headerKey="0" headerValue="(none)" listKey="id"
						  listValue="description"
						  theme="simple">
				  <s:param name="name">${frIdFieldName}</s:param>
				  <s:param name="value">${frIdValue}</s:param>
			 </s:select>
			 <%
			 } else {
				  String s = (String) jspContext.getVariableResolver().resolveVariable(frFieldName);
			 %>
			 <div class="ctrl-failure-reason-text">
				  <%= s != null ? s : "" %>
			 </div>
			 <% } %>
		</div>
    </div>

</div>


<%--<div class="ctrl-container">--%>
    <%--<div class="ctrl-label">--%>
        <%--${label}--%>
    <%--</div>--%>
    <%--<div class="ctrl-tooltip">--%>
        <%--<% if (hasTooltip) { %>--%>
        <%--<n:tooltip id="${tooltipId}" text="${tooltip}"/>--%>
        <%--<% } %>--%>
    <%--</div>--%>
    <%--<div class="ctrl-field">--%>
        <%--<% if (editable) { %>--%>
		 <%--<div class="ctrl-field-inner">--%>
	        <%--<input <%=id%> <%=cssClassEx%> type="text" value="<%= inputFieldValue%>" name="<%= inputFieldName %>"/>--%>
			  <%--<jsp:doBody/>--%>
		  <%--</div>--%>
        <%--<% } else { %>--%>
        <%--<div <%=cssClassEx%> >--%>
			  <%--<div class="ctrl-field-inner">--%>
            <%--<%= inputFieldValue%>--%>
			  <%--</div>--%>
		  <%--</div>--%>
        <%--<% } %>--%>
    <%--</div>--%>
    <%--<div class="ctrl-failure-reason">--%>
        <%--<%--%>
            <%--if (!frDisabled) {%>--%>
        <%--<s:select list="#attr.failureReasonsList" headerKey="0" headerValue="(none)" listKey="id"--%>
                  <%--listValue="description"--%>
                  <%--theme="simple">--%>
            <%--<s:param name="name">${frIdFieldName}</s:param>--%>
            <%--<s:param name="value">${frIdValue}</s:param>--%>
        <%--</s:select>--%>
        <%--<%--%>
        <%--} else {--%>
            <%--String s = (String) jspContext.getVariableResolver().resolveVariable(frFieldName);--%>
        <%--%>--%>
        <%--<div class="ctrl-failure-reason-text">--%>
            <%--<%= s != null ? s : "" %>--%>
        <%--</div>--%>
        <%--<% } %>--%>
    <%--</div>--%>
<%--</div>--%>

<!--todo: wear me :)-->
<% String fnl = inputFieldName + "," + frIdFieldName + "," + frFieldName; %>
<n:fielderror colspan="4" fields="<%= fnl%>"/>