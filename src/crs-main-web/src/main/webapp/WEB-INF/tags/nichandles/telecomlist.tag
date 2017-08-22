<%@ attribute name="wrapper" required="true" type="java.lang.String" rtexprvalue="false"
				  description="path to the phone wrapper" %>
<%@ attribute name="inputDisabled" required="true" type="java.lang.Boolean" %>
<%@ attribute name="label" required="true" description="label to be used" %>


<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%@tag import="pl.nask.crs.app.nichandles.wrappers.PhoneWrapper" %>

<%

	PhoneWrapper o = (PhoneWrapper) jspContext.getVariableResolver().resolveVariable(wrapper);
	jspContext.setAttribute("list", o.getCurrentList());
	jspContext.setAttribute("field", wrapper + ".phone");
%>

<c:if test="${!inputDisabled}">
	<script type="text/javascript" language="javascript">
		function addRow_${label}() {
			// todo: @pixel: change nsTable as it's for nameservers
			var ni = document.getElementById('nsTable_${label}');
			var numi = document.getElementById('sequence_${label}');
			var num = (document.getElementById("sequence_${label}").value - 1) + 2;
			numi.value = num;
			var divIdName = "my_${label}" + num + "Div";
			var newdiv = document.createElement('div');
			newdiv.setAttribute("id", divIdName)

		<%--var link = "<a href=\"javascript:;\" onclick=\"removeElement_${label}(\'" + divIdName + "\')\">&nbsp;&nbsp;X&nbsp;&nbsp;</a>"--%>
			var input_field = "<input type=\"text\" name=\"${field}\">"

			//fields
			var div_close = "</div>";

			var ctrl_container = "<div class=\"ctrl-container\">";
			var ctrl_label = "<div class=\"ctrl-label\">";
			var ctrl_tooltip = "<div class=\"ctrl-tooltip\">";
			var ctrl_field = "<div class=\"ctrl-field force-width-no-max\">";
			var ctrl_inner = "<div class=\"ctrl-field-inner-no-FR-gap\">";
			var ctrl_field_icon = "<div class=\"ctrl-field-icon\">" +
										 "<a href=\"javascript:;\" onclick=\"removeElement_${label}(\'" + divIdName + "\')\">" +
										 "<img src=\"images/skin-default/action-icons/delete.png\" alt=\"X\"/>" +
										 "</a>";

			var my_inner = ctrl_container;
			my_inner += ctrl_label + "${label}" + div_close;
			my_inner += ctrl_tooltip + div_close;
			my_inner += ctrl_field;
			my_inner += ctrl_inner;
			my_inner += input_field;
			my_inner += ctrl_field_icon + div_close;
			my_inner += div_close;
			my_inner += div_close;
			my_inner += div_close;

			newdiv.innerHTML = my_inner;

			//Name field
		<%--var field_open1 = "<div class=\"ctrl-container\">" +--%>
		<%--"<div class=\"ctrl-label\">";--%>
		<%--//	"Name"--%>
		<%--var field_open2 = "</div>" +--%>
		<%--"<div class=\"ctrl-tooltip\"></div>" +--%>
		<%--"<div class=\"ctrl-field force-width-no-max\">" +--%>
		<%--"<div class=\"ctrl-field-inner\">";--%>
		<%--var field_close = "</div>" +--%>
		<%--"</div>" +--%>
		<%--"</div>";--%>
		<%--newdiv.innerHTML = link + field_open1 + "${label}" + field_open2 + input_field + field_close + "<BR/>"--%>
			ni.appendChild(newdiv);
		}

		function removeElement_${label}(divNum) {
			var d = document.getElementById('nsTable_${label}');
			var olddiv = document.getElementById(divNum);
			d.removeChild(olddiv);
		}
	</script>

	<input type="hidden" value="0" id="sequence_${label}"/>
</c:if>

<div class="phonefax_header">
	${label}
</div>
<div id="telecomEmpty" style="display: none;">
	<%-- defend against empty lists --%>
	<input type="" value="" name="${field}" ${inputDisabled ? "disabled" : ""}/>	
</div>
<div id="nsTable_${label}">
	<c:forEach items="${list}" var="ns" varStatus="stat">
		<c:set var="num" value="${stat.count-1}"/>
		<%
			Long num = (Long) jspContext.getAttribute("num");
			jspContext.setAttribute("fieldId", "cns_" + label + num + "div");
		%>
		<div id="${fieldId}">
			<n:field label="${label}" cssCtrl="force-width-no-max">
				<input class="${nsNameClass} force-width-very-long" type="text" value="${ns}"
						 name="${field}" ${inputDisabled ? "disabled" : ""}/>

				<c:if test="${!inputDisabled}">
					<div class="ctrl-field-icon">
						<a href="javascript:;" onclick="removeElement_${label}('${fieldId}')">
							<img src="images/skin-default/action-icons/delete.png" alt="X"/>
						</a>
					</div>
				</c:if>

			</n:field>
			<n:fielderror fields="${wrapper}[${num}]"/>
		</div>
	</c:forEach>
</div>
<n:fielderror fields="${wrapper}"/>
<c:if test="${!inputDisabled }">
	<hr class="buttons"/>
	<center>
		<input type="button" name="add" value="Add entry" onclick="addRow_${label}()"/>
	</center>
</c:if>


<%--<div id="nsTable_${label}">--%>
<%--<c:forEach items="${list}" var="ns" varStatus="stat">--%>
<%--<c:set var="num" value="${stat.count-1}"/>--%>
<%--<%--%>
<%--Long num = (Long) jspContext.getAttribute("num");--%>
<%--jspContext.setAttribute("fieldId", "cns_" + label + num + "div");--%>
<%--%>--%>
<%--<c:if test="${!inputDisabled}">--%>
<%--<div id="${fieldId}">--%>
<%--<a href="javascript:;" onclick="removeElement_${label}('${fieldId}')">&nbsp;&nbsp;X&nbsp;&nbsp;</a>--%>
<%--</c:if>--%>
<%--<n:field label="${label}" cssCtrl="force-width-no-max">--%>
<%--<input class="${nsNameClass}" type="text" value="${ns}"--%>
<%--name="${field}" ${inputDisabled ? "disabled" : ""}/>--%>
<%--</n:field>--%>
<%--<n:fielderror fields="${field}"/>--%>
<%--<c:if test="${!inputDisabled }">--%>
<%--<br/>--%>
<%--<br/>--%>
<%--</div>--%>
<%--</c:if>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--<c:if test="${!inputDisabled }">--%>
<%--<hr class="buttons"/>--%>
<%--<center>--%>
<%--<input type="button" name="add" value="Add entry" onclick="addRow_${label}()"/>--%>
<%--</center>--%>
<%--</c:if>--%>
