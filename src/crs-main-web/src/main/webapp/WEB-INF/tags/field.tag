<%@attribute name="label" required="true" %>
<%@attribute name="hideTooltipGap"
				 description="if set to true, there will be no space between label and control. False is a default"
				 type="java.lang.Boolean" %>

<%@attribute name="cssContainer" description="extra css class added to whole container" %>
<%@attribute name="cssLabel" description="extra css class added to label" %>
<%@attribute name="cssCtrl" description="extra css class added to control" %>

<%@attribute name="fielderror" rtexprvalue="true" description="name (path) of the value, fielderror should be rendered for" %>

<%@attribute name="tooltip" description="Tooltip to be displayed." %>
<%@attribute name="tooltipId" description="Tooltip Id to be used - MUST be an unique identifier. MUST be set, if tooltip is to be shown" %>

<%@attribute name="overrideElastic"
				 description="Forces tag to be fixed (auto) rather than elastic. False is a default"
				 type="java.lang.Boolean" %>

<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%
	if (hideTooltipGap == null) hideTooltipGap = false;
	if (overrideElastic == null) overrideElastic = false;

	String cssContainerClass;
	String cssLabelClass;
	String cssLabelInnerClass;
	String cssCtrlClass;

	if (overrideElastic) {
		cssContainerClass = "ctrl-fixed-container";
		cssLabelClass = "ctrl-fixed-label";
		cssLabelInnerClass = "ctrl-fixed-label-inner";
		cssCtrlClass = "ctrl-fixed-field";
	} else {
		cssContainerClass = "ctrl-container";
		cssLabelClass = "ctrl-label";
		cssLabelInnerClass = "ctrl-label-inner";
		cssCtrlClass = "ctrl-field";
	}

	if (cssContainer != null) cssContainerClass += " " + cssContainer;
	if (cssLabel != null) cssLabelInnerClass += " " + cssLabel;
	if (cssCtrl != null) cssCtrlClass += " " + cssCtrl;

	cssContainerClass = "class=\"" + cssContainerClass + "\"";
	cssLabelClass = "class=\"" + cssLabelClass + "\"";
	cssLabelInnerClass = "class=\"" + cssLabelInnerClass + "\"";
	cssCtrlClass = "class=\"" + cssCtrlClass + "\"";

	boolean hasTooltip = tooltip != null && tooltip.trim().length() > 0 && tooltipId !=null;
	if(hasTooltip) {
		tooltipId = "id" + tooltipId.replaceAll("[\\.\\[\\]]", "_");
	}
%>

<div <%= cssContainerClass %> >

	<div <%= cssLabelClass %> >
		<div <%= cssLabelInnerClass %> >
			${label}
		</div>

		<% if (!hideTooltipGap) { %>
			<div class="ctrl-tooltip">
			<% if (hasTooltip) { %>
				<n:tooltip id="<%= tooltipId%>" text="${tooltip}"/>
			<% } %>
			</div>
		<% } %>
	</div>

	<div <%= cssCtrlClass %> >
		<div class="ctrl-field-inner-no-FR-gap">
			<jsp:doBody/>
		</div>
	</div>
</div>

<% if(!((fielderror==null) || ("".equals(fielderror)))) {%>
	<n:fielderror fields="${fielderror}" />
<%}%>