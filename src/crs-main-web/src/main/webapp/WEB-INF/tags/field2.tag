<%@attribute name="label" required="true" %>
<%@attribute name="labelJustify" description="Informs tag how label text has to be justified.
				 Default is left" %>

<%@attribute name="tooltip" description="Tooltip to be displayed." %>
<%@attribute name="tooltipGapHidden" type="java.lang.Boolean"
				 description="if set to true, there will be no space between label and control.
				 Default is False." %>

<%@attribute name="fieldEditable" type="java.lang.Boolean"
				 description="Informs tag that its jsp:doBody has no select or input.
				 Default is False." %>

<%@attribute name="fielderror" rtexprvalue="true" description="name (path) of the value, fielderror should be rendered for" %>

<%@attribute name="cssContainerStyle" description="extra css style added to whole container" %>
<%@attribute name="cssLabelStyle" description="extra css style added to label" %>
<%@attribute name="cssCtrlStyle" description="extra css style added to control" %>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
	// classes
	String containerClass;	// whole field

	String labelClass;		// label with icon
	String labelBoxClass;	// label without icon
	String labelValueClass;	// inner label element

	String boxClass;			//
	String boxBoxClass;		//

	String ctrlBoxClass;		// ctrl without icon
	String ctrlValueClass;	// inner ctrl element

	containerClass = "FC";

	labelClass = "FCL";
	labelBoxClass = "FCL-box";
	labelValueClass = "FCL-value textOnly";

	boxClass = "FCB";
	boxBoxClass = "FCB-box";

	ctrlBoxClass = "FCC-box";
	ctrlValueClass = "FCC-value";

	// extra style
	String containerStyle	= "";	// whole field
	String labelStyle			= "";	//
	String boxStyle			= "";
	String ctrlStyle			= "";	// control which displays value select/input/text

	if (cssContainerStyle != null) containerStyle += " " + cssContainerStyle;
	if (cssLabelStyle != null) labelStyle += " " + cssLabelStyle;
	else labelClass += " default-label-width";
//	if (cssCtrlStyle != null) ctrlStyle += " " + cssCtrlStyle;
	if (cssCtrlStyle != null) boxStyle += " " + cssCtrlStyle;

	// label value
	if (labelJustify == null) labelJustify = "left";
	else labelJustify = labelJustify.toLowerCase();

	if (labelJustify.equals("left")) labelValueClass += " justify-left";
	else labelValueClass += " justify-right";

	// tooltip
	if (tooltipGapHidden == null) tooltipGapHidden = false;

	String tooltipId = tooltip;
	boolean hasTooltip = tooltip != null && tooltip.trim().length() > 0 && tooltipId != null;

	if (!tooltipGapHidden) labelBoxClass += " tooltip-gap";

	// box
	if (cssLabelStyle == null)
		boxClass += " default-label-gap";

	if (fieldEditable == null) fieldEditable = false;
	if (!fieldEditable) ctrlValueClass += " textOnly";

	jspContext.setAttribute("tooltipId", tooltipId);
%>

<div class="<%=containerClass%>" style="<%=containerStyle%>">

	<!-- label -->
	<div class="<%=labelClass%>" style="<%=labelStyle%>">

		<!-- label (tooltip) icon -->
		<% if (!tooltipGapHidden) { %>
		<div class="FCL-icon">
			<% if (hasTooltip) { %> <n:tooltip id="<%= tooltipId%>" text="${tooltip}"/> <% } %>
		</div>
		<% } %>

		<%-- label text --%>
		<div class="<%=labelBoxClass%>">
			<div class="<%=labelValueClass%>">
				${label}
			</div>
		</div>
	</div>

	<!-- ctrl -->
	<div class="<%=boxClass%>" style="<%=boxStyle%>">

		<!-- ctrl -->
		<div class="<%=boxBoxClass%>">

			<!-- ctrl value -->
			<div class="<%=ctrlBoxClass%>">
				<div class="<%=ctrlValueClass%>">
					<jsp:doBody/>
				</div>
			</div>
		</div>
	</div>
</div>

<% if (!((fielderror==null) || ("".equals(fielderror)))) { %>
	<n:fielderror fields="${fielderror}" />
<% } %>