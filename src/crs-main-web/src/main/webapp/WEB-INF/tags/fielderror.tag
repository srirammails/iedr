<%@ attribute name="fields" required="true" description="comma separated list of field names" %>
<%@ attribute name="colspan" required="false" type="java.lang.Integer"
				  description="comma separated list of field names" %>

<%
	if (colspan == null)
		colspan = 3;
	String[] afields = fields.split(",");

%>

<%
	Map<String, List<String>> m = (Map<String, List<String>>) jspContext.getVariableResolver().resolveVariable("fieldErrors");

	boolean render = false;
	if (m != null && !m.isEmpty()) {
		int i = 0;
		while (!render && i < afields.length) {
			render |= m.containsKey(afields[i].trim());
			i++;
		}
	}

	if (render) {
%>

<%@tag import="java.util.Map" %>
<%@tag import="java.util.List" %>

<div class="invalid-field">
	<div class="invalid-field-icon">&nbsp;</div>
	<div class="invalid-field-description">
		<%
			boolean first = true;
			for (String fn : afields) {
				List<String> ve = m.get(fn.trim());

				if (ve != null) {
					for (String vem : ve) {
		%>
		<%= (!first) ? "<br />" : "" %>
		<%= vem%>
		<%
						first = false;
					}
				}
			}

		%>
	</div>
</div>

<% } %> <!--if (render)-->