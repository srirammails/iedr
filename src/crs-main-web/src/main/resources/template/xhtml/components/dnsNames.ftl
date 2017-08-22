<script type="text/javascript" language="javascript">

function addRow() {
	 var ni = document.getElementById('nsTable');
	 var numi = document.getElementById('sequence');
	 var num = (document.getElementById("sequence").value -1)+ 2;
	 numi.value = num;
	 var divIdName = "my"+num+"Div";
	 var newdiv = document.createElement('div');
	 newdiv.setAttribute("id",divIdName)
	 
	 var nameServers = 'nameServers'
	 var ognl_ip = nameServers+".server['"+divIdName+"_ip']"
	 var ognl_ns = nameServers+".server['"+divIdName+"_ns']"

	 var link = "<a href=\"javascript:;\" onclick=\"removeElement(\'"+divIdName+"\')\">&nbsp;&nbsp;&nbsp;&nbsp;</a>"
	 var input_ns = "<input type=\"text\" name=\""+ognl_ns+"\">"
	 var input_ip = "<input type=\"text\" name=\""+ognl_ip+"\">"
	 newdiv.innerHTML = link+input_ns+input_ip
	 var selectFr = document.createElement('select')
	 selectFr.setAttribute("id", "select_"+divIdName)
	 selectFr.setAttribute("name", "${parameters.failureReason}['"+divIdName+"']")
	 
	 var dfr = document.getElementById('dnsFailureReasonsHidden')
	 
	 for (i = 0; i < dfr.length; i++) {
		 selectFr.options[i] = new Option(dfr[i].text, dfr[i].value);
	 }
	 
	 newdiv.appendChild(selectFr)
	 ni.appendChild(newdiv);
	}



function removeElement(divNum) {
 var d = document.getElementById('nsTable');
 var olddiv = document.getElementById(divNum);
 d.removeChild(olddiv);
}
</script>


<#include "/${parameters.templateDir}/${parameters.theme}/controlheader.ftl" />	
<input type="hidden" value="0" id="sequence" /> 
<div id="aaaaa" style="display:none;">
<@s.select name="" list="${parameters.failureReasonsDict}" id="dnsFailureReasonsHidden" theme="simple" />
</div>
<input type="button" name="add" value="Add entry" onclick="addRow()"/>
<div id="nsTable">
<@s.iterator value="${parameters.name}.servers" var="ns">
<#assign fns=stack.findValue('#ns')>
<div id="${fns.id}">
	<a href="javascript:;" onclick="removeElement('${fns.id}')">&nbsp;&nbsp;&nbsp;&nbsp;</a>
	<@s.textfield name="${parameters.name}.server['${fns.id}_ns']" value="${fns.ns}" theme="simple"/>
	<@s.textfield name="${parameters.name}.server['${fns.id}_ip']" value="${fns.ip}" theme="simple"/>
	<@s.select list="${parameters.failureReasonsDict}" name="${parameters.failureReason}['${fns.id}']" theme="simple"/>
</div>
</@s.iterator>
</div>
<#include "/${parameters.templateDir}/${parameters.theme}/controlfooter.ftl" />

