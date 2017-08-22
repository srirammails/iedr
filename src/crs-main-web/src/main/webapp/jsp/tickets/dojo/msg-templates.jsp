<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>


<%
    request.setAttribute("decorator", "none");
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<s:url var="saveMsg" namespace="/dojo" action="template-save" />
<s:url var="saveMsgAs" namespace="/dojo" action="template-add" />
<s:url var="deleteMsg" namespace="/dojo" action="template-delete"/>

<s:actionerror/>
<s:actionmessage/>
<s:iterator value="responses" var="response">
    <input type="hidden" id="id${response.id}" value="${response.text}" disabled="disabled"/>
</s:iterator>
<s:select id="messageTemplates"
				  name="selectedResponseId"
                  onchange="responseSelected(this.options.selectedIndex);"
				  list="responses"
                  listKey="id"
				  listValue="title"
                  theme="simple"
                  /><br/>
<s:textarea id="hostmasterMessage" name="responseText" value="%{responseText}" theme="simple"></s:textarea><br/>

<div id="saveMessageDiv" <s:if test="%{selectedResponseId == -1}">style="display:none;"</s:if>>
    <sx:submit value="Delete" id="deleteMessageButton" href="%{deleteMsg}" targets="responsesListDiv" />
    <sx:submit value="Save" id="saveMessageButton" href="%{saveMsg}" targets="responsesListDiv" />
</div>
                    
<div id="newMessageDiv" <s:if test="%{selectedResponseId > -1}">style="display:none;"</s:if>>
		<sx:submit value="Save as..." id="saveMessageAsButton" href="%{saveMsgAs}" targets="responsesListDiv" />
		<s:textfield id="newResponseTitle" name="newResponseTitle" theme="simple"/>
</div>



