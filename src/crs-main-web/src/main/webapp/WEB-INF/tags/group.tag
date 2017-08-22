<%@ attribute name="headerName" description="Name of the group to be displayed in the header" %>
<%@ attribute name="headerClass" description="Name of the header left corner css class" %>
<%@ attribute name="skipTable" type="java.lang.Boolean" description="If true, skips nested table (false by default)" %>
<% if (skipTable == null) skipTable = Boolean.FALSE; %>

<div class="group">
	<div class="group-header">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="lu-corner-h2">&nbsp;</td>
				<td class="up-corner-h2">
					<% if (headerClass != null) { %>
					<div class="${headerClass}">&nbsp;</div>
					<% } %>
					<p>${headerName}</p>
				</td>
				<td class="ru-corner-h2">&nbsp;</td>
			</tr>
		</table>
	</div>

	<div class="group-content">
		<% if (!skipTable) { %>
		<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
			<% } %>
			<jsp:doBody/>
			<% if (!skipTable) { %>
		</table>
		<% } %>
	</div>

	<div class="group-bottom">
		<div class="group-bottom-fix">
			<div class="ld-corner-round-h2">&nbsp;</div>
			<div class="rd-corner-round-h2">&nbsp;</div>
			<div class="dn-corner-round-h2">&nbsp;</div>
		</div>
	</div>
</div>
