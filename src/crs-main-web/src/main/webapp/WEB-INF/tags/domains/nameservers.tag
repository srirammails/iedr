<%@ attribute name="wrapper" required="true" type="java.lang.String" rtexprvalue="false"
              description="path to the dns wrapper" %>
<%@ attribute name="inputDisabled" required="true" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%@tag import="pl.nask.crs.app.domains.wrappers.DnsWrapper" %>

<%
    jspContext.setAttribute("nsNameClass", "");
    jspContext.setAttribute("nsIpClass", "");

    DnsWrapper o = (DnsWrapper) jspContext.getVariableResolver().resolveVariable(wrapper);
    jspContext.setAttribute("nslist", o.getCurrentNameservers());
    
    String nameField = wrapper + ".newNames";
    String ipField = wrapper + ".newIps";
    jspContext.setAttribute("nameField", nameField);
    jspContext.setAttribute("ipField", ipField);
%>

<div id="nsEmptyNS" style="display: none;">
	<%-- defend against empty lists --%>
	<input type="" value="" name="${ipField}" ${inputDisabled ? "disabled" : ""}/>
	<input value="" name="${nameField}" ${inputDisabled ? "disabled" : ""}/>
</div>

<c:if test="${!inputDisabled}">
	<script type="text/javascript" language="javascript">
		function addRow() {
			var ni = document.getElementById('nsTable');
			var numi = document.getElementById('sequence');
			var num = (document.getElementById("sequence").value - 1) + 2;
			numi.value = num;
			var divIdName = "my" + num + "Div";
			var newdiv = document.createElement('div');
			newdiv.setAttribute("id", divIdName)

			var wrapper = '<%= wrapper%>'
			var ognl_ip = wrapper + ".newIps"
			var ognl_ns = wrapper + ".newNames"

			var input_ns = "<input type=\"text\" name=\"" + ognl_ns + "\">"
			var input_ip = "<input type=\"text\" name=\"" + ognl_ip + "\">"

			//fields
			var div_close = "</div>";

			var ctrl_container = "<div class=\"ctrl-container\">";
			var ctrl_label = "<div class=\"ctrl-label\">";
			var ctrl_tooltip = "<div class=\"ctrl-tooltip\">";
			var ctrl_field = "<div class=\"ctrl-field force-width-no-max\">";
			var ctrl_inner = "<div class=\"ctrl-field-inner-no-FR-gap\">";
			var ctrl_field_icon = "<div class=\"ctrl-field-icon\">" +
										 "<a href=\"javascript:;\" onclick=\"removeElement(\'" + divIdName + "\')\">" +
										 "<img src=\"images/skin-default/action-icons/delete.png\" alt=\"X\"/>" +
										 "</a>";

			var my_inner = ctrl_container;
			my_inner += ctrl_label + "Name" + div_close;
			my_inner += ctrl_tooltip + div_close;
			my_inner += ctrl_field;
			my_inner += ctrl_inner;
			my_inner += input_ns;
			my_inner += ctrl_field_icon + div_close;
			my_inner += div_close;
			my_inner += div_close;
			my_inner += div_close;

			my_inner += ctrl_container;
			my_inner += ctrl_label + "IP" + div_close;
			my_inner += ctrl_tooltip + div_close;
			my_inner += ctrl_field;
			my_inner += ctrl_inner;
			my_inner += input_ip;
			my_inner += div_close;
			my_inner += div_close;
			my_inner += div_close;
			my_inner += "<br/>";

			newdiv.innerHTML	= my_inner;

			ni.appendChild(newdiv);


			//            var link = "<a href=\"javascript:;\" onclick=\"removeElement(\'" + divIdName + "\')\">&nbsp;&nbsp;X&nbsp;&nbsp;</a>"
			//            var input_ns = "<input type=\"text\" name=\"" + ognl_ns + "\">"
			//            var input_ip = "<input type=\"text\" name=\"" + ognl_ip + "\">"
			//            //Name field
			//            var field_open1 = "<div class=\"ctrl-container\">" +
			//                              "<div class=\"ctrl-label\">";
			//            //	"Name"
			//            var field_open2 = "</div>" +
			//                              "<div class=\"ctrl-tooltip\"></div>" +
			//                              "<div class=\"ctrl-field force-width-no-max\">" +
			//                              "<div class=\"ctrl-field-inner\">";
			//            var field_close = "</div>" +
			//                              "</div>" +
			//                              "</div>";
			//            newdiv.innerHTML = link + field_open1 + "Name" + field_open2 + input_ns + field_close
			//            newdiv.innerHTML = newdiv.innerHTML +
			//                               field_open1 + "Ip" + field_open2 + input_ip + field_close + "<BR/>"
			//            ni.appendChild(newdiv);

		}

		function removeElement(divNum) {
			var d = document.getElementById('nsTable');
			var olddiv = document.getElementById(divNum);
			var iname = document.getElementById(divNum+'_name');
			var name = iname != null ? iname.value : '';			
			var iip = document.getElementById(divNum+'_ip');
			var ip = iip != null ? iip.value : '';			
			
			var answer = confirm('Name: '+name+'\nIP: '+ip+'\nAre you sure you want to delete this entry? ');
			if (answer){				
				d.removeChild(olddiv);
			}
			
		}
	</script>

	<input type="hidden" value="0" id="sequence"/>
	<%--<input type="button" name="add" value="Add entry" onclick="addRow()"/>--%>
</c:if>

<div id="nsTable">
    <c:forEach items="${nslist}" var="ns" varStatus="stat">
        <c:set var="num" value="${stat.count-1}"/>
        <%
            Long num = (Long) jspContext.getAttribute("num");
        	String fieldId = "cns" + num + "div";
            String nameFieldFe = wrapper + ".currentNameservers["+num+"].name";
            
            String ipFieldFe = wrapper + ".currentNameservers["+num+"].ip";
            
            jspContext.setAttribute("fieldId", fieldId);
            jspContext.setAttribute("ipFieldFe", ipFieldFe);
            jspContext.setAttribute("nameFieldFe", nameFieldFe);
        %>
		 <div id="${fieldId}">
			 <n:field label="Name" cssCtrl="force-width-no-max">
				 <input id="${fieldId}_name" class="${nsNameClass} force-width-very-long" type="text" value="${ns.name}"
						  name="${nameField}" ${inputDisabled ? "disabled" : ""}/>

				 <c:if test="${!inputDisabled}">
					 <div class="ctrl-field-icon">
						 <a href="javascript:;" onclick="removeElement('${fieldId}')">
							 <img src="images/skin-default/action-icons/delete.png" alt="X"/>
						 </a>
					 </div>
				 </c:if>
			 </n:field>
			 <n:fielderror fields="${nameFieldFe}"/>
			 <n:field label="IP" cssCtrl="force-width-no-max">
				 <input id="${fieldId}_ip" class="${nsIpClass} force-width-very-long" type="text" value="${ns.ip}"
						  name="${ipField}" ${inputDisabled ? "disabled" : ""}/>
			 </n:field>
			 <n:fielderror fields="${ipFieldFe}"/>

			 <br/>

		 </div>
	 </c:forEach>
</div>
<n:fielderror fields="${wrapper}" />
<c:if test="${!inputDisabled}">
	<hr class="buttons"/>
	<center>
		<input type="button" name="add" value="Add entry" onclick="addRow()"/>
	</center>
</c:if>
