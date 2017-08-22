<%@attribute name="field" required="true" rtexprvalue="false"
				 description="path of the field which holds the dte value" type="java.lang.String" %>
<%@attribute name="dateFormat" required="false" rtexprvalue="false" type="java.lang.String"
				 description="javascript date format (note, that this tag edits ONLY date, not the time).
			 	default is %d/%m/%Y which corresponds to dd-MM-yyyy 
			 	%Y - 4-digit year, %m - 2 digit month, $d - day of the month 
			 	See http://www.dynarch.com/demos/jscalendar/doc/html/reference.html#node_toc_node_sec_5.3.5 for more formating options			 	
			 	" %>
<%@tag import="java.util.Date" %>
<%@tag import="java.text.SimpleDateFormat" %>

<%
	if (dateFormat == null || "".equals(dateFormat))
		dateFormat = "%d/%m/%Y";

// field value
	Object date = jspContext.getVariableResolver().resolveVariable(field);
	if ((!(date instanceof Date)) && (date != null))
		throw new IllegalArgumentException("'field' must lead to instance of java.util.Date class (" + date + " spotted)");

// String representation of the date
	String inputFieldString = "";  //holds date in user format
	String inputHiddenString = ""; //holds date in rfc format

	if (date != null) {
		SimpleDateFormat rfc = new SimpleDateFormat("yyyy-MM-dd");
		inputHiddenString = rfc.format(date);

//    SimpleDateFormat user = new SimpleDateFormat("dd/MM/yyyy");
//    inputFieldString = user.format(date);                
	}

	String safeField = field.replaceAll("\\.", "_");

// input fields ids
	String inputFieldId = "jscal_input_" + safeField;
	String hiddenFieldId = "jscal_hidden_" + safeField;
// button (image) id
	String buttonId = "jscal_trigger_" + safeField;
// function names
	String updateInputFieldFunction = "jscal_update_input_" + safeField;
	String updateHiddenFieldFunction = "jscal_update_hidden_" + safeField;
%>

<div class="datefield-field">
	<div class="datefield-inner">
		<input type="hidden" name="${field}" id="<%= hiddenFieldId %>" value="<%= inputHiddenString %>"/>
		<input type="text" id="<%= inputFieldId %>" onchange="<%= updateHiddenFieldFunction %> ();"/>
	</div>
	<div class="datefield-icon">
		<img src="images/skin-default/action-icons/calendar.png" id="<%= buttonId %>" style="cursor: default; " title="Date selector"/>
	</div>
	<script type="text/javascript">

		function <%= updateHiddenFieldFunction %> () {
			var inputField = document.getElementById("<%= inputFieldId %>");
			var hiddenField = document.getElementById("<%= hiddenFieldId %>");

			//var calendar = new Calendar(1, null, "", "");
			//calendar.setDateFormat("%d/%m/%Y");
			//var date = calendar.parseDate(inputField.Value);
			var newDate = inputField.value;
			hiddenField.value = "";
			inputField.value = "";
			if (newDate != "") {
				var date = Date.parseDate(newDate, "<%= dateFormat %>");

				hiddenField.value = date.print("%Y-%m-%dT00:00:00");
				inputField.value = date.print("<%= dateFormat %>");
			}
		}

		Calendar.setup({
			inputField : "<%= inputFieldId %>", // id of the input field.
			ifFormat : "<%= dateFormat %>}", // format of the input field
			button : "<%= buttonId %>", // trigger for the calendar (button ID)
			align : "Bl", // alignment (defaults to "Bl")
			singleClick : true,
			weekNumbers : false,
			firstDay : 1
		});

		if (document.getElementById("<%= hiddenFieldId %>").value != "") {
			document.getElementById("<%= inputFieldId %>").value = Date.parseDate(
					  document.getElementById("<%= hiddenFieldId %>").value,
					  "%Y-%m-%dT00:00:00")
					  .print("<%= dateFormat %>");
		}

	</script>
</div>