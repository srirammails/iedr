<%@attribute name="label" required="true" %>
<%@attribute name="labelJustify" description="Informs tag how label text has to be justified.
				 Default is left" %>

<%@attribute name="tooltip" description="Tooltip to be displayed." %>
<%@attribute name="tooltipGapHidden" type="java.lang.Boolean"
				 description="if set to true, there will be no space between label and control.
				 Default is False." %>

<%@attribute name="field" required="true" rtexprvalue="false"
				 description="input field name - must be an instance of SimpleDomainFieldChangeWrapper" %>
<%@attribute name="fieldSuffix" rtexprvalue="false" %>
<%@attribute name="fieldEditable" type="java.lang.Boolean"
				 description="Informs tag that its jsp:doBody has no select or input.
				 Default is False." %>

<%@attribute name="selectorGapHidden" type="java.lang.Boolean"
				 description="if set to true, there will be no extra-space between control and fail reason.
				 Default is False." %>

<%@attribute name="reasonEditable" type="java.lang.Boolean"
				 description="Informs tag that its fail reason is a pure text (no select or input).
				 Default is False." %>
<%@attribute name="reasonHidden" type="java.lang.Boolean"
				 description="if set to true, there will be no fail reason displayed.
				 Default is False." %>
<%@attribute name="reasonGapHidden" type="java.lang.Boolean"
				 description="if set to true, there will be no extra-space between fail reason and right end of container.
				 Default is False." %>

<%@attribute name="cssContainerStyle" description="extra css style added to whole container" %>
<%@attribute name="cssLabelStyle" description="extra css style added to label" %>
<%@attribute name="cssCtrlStyle" description="extra css style added to control" %>
<%@attribute name="cssReasonStyle" description="extra css style added to control" %>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>

<%@tag import="pl.nask.crs.web.ticket.wrappers.SimpleDomainFieldChangeWrapper" %>
<%@tag import="java.util.List" %>

<%
	// classes
	String containerClass;	// whole field

	String labelClass;		// label with icon
	String labelBoxClass;	// label without icon
	String labelValueClass;	// inner label element

	String boxClass;			// ctrl with reason
	String boxBoxClass;		// ctrl without reason
	String boxReasonClass;		// failure reason

	String reasonValueClass;	// inner reason element

	String ctrlBoxClass;		// ctrl without icon
	String ctrlValueClass;	// inner ctrl element

	containerClass = "FC";

	labelClass = "FCL";
	labelBoxClass = "FCL-box";
	labelValueClass = "FCL-value textOnly";

	boxClass = "FCB";
	boxBoxClass = "FCB-box";
	boxReasonClass = "FCB-reason";

	reasonValueClass = "FCR-value";

	ctrlBoxClass = "FCC-box";
	ctrlValueClass = "FCC-value";

	// extra style
	String containerStyle	= "";	// whole field
	String labelStyle			= "";	//
	String boxStyle			= "";
	String ctrlStyle			= "";	// control which displays value select/input/text
	String reasonStyle	= "";	// control which displays fail reason (select)

	if (cssContainerStyle != null) containerStyle += " " + cssContainerStyle;
	if (cssLabelStyle != null) labelStyle += " " + cssLabelStyle;
	else labelClass += " default-label-width";
//	if (cssCtrlStyle != null) ctrlStyle += " " + cssCtrlStyle;
	if (cssCtrlStyle != null) boxStyle += " " + cssCtrlStyle;
	if (cssReasonStyle != null) reasonStyle += " " + cssReasonStyle;

	// label value
	if (labelJustify == null) labelJustify = "left";
	else labelJustify = labelJustify.toLowerCase();

	if (labelJustify.equals("left")) labelValueClass += " justify-left";
	else labelValueClass += " justify-right";

	// field
	String fieldID = null;
	String fieldElements[] = field.split("\\.");
	if (fieldElements.length == 2 && fieldElements[1] != null && !fieldElements[1].equals("")) fieldID = fieldElements[1];

	if (fieldEditable == null) fieldEditable = false;
	if (!fieldEditable) ctrlValueClass += " textOnly";

	SimpleDomainFieldChangeWrapper fieldChange = (SimpleDomainFieldChangeWrapper) jspContext.getVariableResolver().resolveVariable(field);

	if (fieldChange == null)
		 throw new IllegalArgumentException("cannot access field: " + field);

	if (fieldChange != null && fieldChange.isModification())
		 ctrlValueClass += " modification";

	if (fieldSuffix == null) fieldSuffix = "newValue";
	String fieldName = field + "." + fieldSuffix;
	Object fieldValue = jspContext.getVariableResolver().resolveVariable(fieldName);

	if (fieldValue == null) fieldValue = "";

	// tooltip
	if (tooltipGapHidden == null) tooltipGapHidden = false;

	String tooltipId = "id" + fieldName.replaceAll("[\\.\\[\\]]", "_");
	boolean hasTooltip = tooltip != null && tooltip.trim().length() > 0 && tooltipId != null;

	if (!tooltipGapHidden) labelBoxClass += " tooltip-gap";

	// box
	if (cssLabelStyle == null)
		boxClass += " default-label-gap";

	// field selector
	boolean selectorHidden = false;

	if (selectorGapHidden == null) selectorGapHidden = false;
	if (selectorGapHidden == true) selectorHidden = true;

	if (!selectorGapHidden) ctrlBoxClass += " selector-gap";

	// reason
	if (reasonHidden == null) reasonHidden = false;
	if (reasonEditable == null) reasonEditable = false;
	if (reasonGapHidden == null) reasonGapHidden = false;

	if (reasonGapHidden == true) {
		reasonHidden = true;
		reasonEditable = false;
	} else {
		boxBoxClass += " reason-gap";
	}

	if (reasonEditable == false) reasonValueClass += " textOnly";

	String reasonName = field + ".failureReason.description";
	List reasonsList = fieldChange.getFailureReasonList();

	String reasonID = field + ".failureReasonId";
	Integer n = (Integer) jspContext.getVariableResolver().resolveVariable(reasonID);

	jspContext.setAttribute("reasonsList", reasonsList);
	jspContext.setAttribute("frFieldName", reasonName);
	jspContext.setAttribute("frIdFieldName", reasonID);
	jspContext.setAttribute("frIdValue", n);
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

	<!-- ctrl + reason -->
	<div class="<%=boxClass%>" style="<%=boxStyle%>">

		<!-- reason -->
		<% if (!reasonGapHidden) { %>
		<div class="<%=boxReasonClass%>">
			<% if (!reasonHidden) { %>
			<div class="<%=reasonValueClass%>" style="<%=reasonStyle%>">
				<% if (reasonEditable) { %>
				<s:select list="#attr.reasonsList" headerKey="0" headerValue="(none)" listKey="id"
							 listValue="description" theme="simple">
					<s:param name="name">${frIdFieldName}</s:param>
					<s:param name="value">${frIdValue}</s:param>
				</s:select>
				<% } else { %>
					<% String s = (String) jspContext.getVariableResolver().resolveVariable(reasonName); %>
					<%= s != null ? s : "" %>
				<% } %>
			</div>
			<% } %>
		</div>
		<% } %>

		<!-- ctrl without reason -->
		<div class="<%=boxBoxClass%>">

			<!-- ctrl value -->
			<div class="<%=ctrlBoxClass%>">
				<div class="<%=ctrlValueClass%>">
					<% if (fieldEditable) { %>
						<%--<input id="<%= fieldID%>" class="<%= ctrlValueClass%>" type="text" value="<%= fieldValue%>" name="<%= fieldName %>"/>--%>
						<input id="<%= fieldID%>" type="text" value="<%= fieldValue%>" name="<%= fieldName %>"/>
					<% } else { %>
						<%= fieldValue%>
					<% } %>
				</div>
			</div>

			<!-- ctrl icon -->
			<% if (!selectorGapHidden) { %>
			<div class="FCC-icon">
				<jsp:doBody/>
			</div>
			<% } %>
		</div>
	</div>
</div>

<% String fnl = fieldName + "," + reasonID + "," + reasonName; %>
<n:fielderror colspan="4" fields="<%= fnl%>"/>