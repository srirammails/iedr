<%@ attribute name="fieldId" required="true" %>
<%@ attribute name="accountId" required="false" description="nichandle account id search criterion" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<%
    String dialogId = "contactSearchDialog" + fieldId;
    String openTriggerId = "openSearchDialog" + fieldId;
%>
<script type="text/javascript">
    $(document).ready(function() {
        $('#<%=dialogId%>').jqm({trigger: false})
                .jqmAddTrigger('#<%=openTriggerId%>')
                .jqmAddClose($('.closeDialog'));
    })
</script>
<script type="text/javascript">

    function ${fieldId}_replaceLinks() {
        var sortLinks = $('thead > tr > th > a');
        ${fieldId}_ajaxifyLinks(sortLinks);
        if (dojo.byId("<%=dialogId%>").getElementsByClassName('pagelinks').length > 0) {
            var pagelinks =$('.pagelinks > a', dojo.byId("<%=dialogId%>"));
            ${fieldId}_ajaxifyLinks(pagelinks);
        }
    }
    function ${fieldId}_ajaxifyLinks(links) {
        for (var i=0; i < links.length; i++) {
            links[i].onclick = function(){
            dojo.io.bind ({
                url: "dojo/contacts-search.action"+this.search,
                load: function(type, data, evt){
                    dojo.byId(${fieldId}_getDiv()).innerHTML = data;
                    ${fieldId}_replaceLinks();
                }
            });

            return false;
            }
        }
    }
</script>
<c:set var="divResultId" value="div${fieldId}" />
<c:set var="formName" value="formName${fieldId}"/>
<% if (accountId == null) { %>
<c:set var="accountIdDisabled" value='disabled="disabled"'/>
<% } %>

<div id="<%=dialogId%>" class="jqmWindow">
    <input type="hidden" name="id" class="fieldId" value="${fieldId}"/>
    <n:group2 titleText="Search Contacts" cssIcon="group-icon-ticketreassign">
        <s:form name="%{#attr.formName}" action="contacts-search" namespace="/dojo" theme="simple" id="testform1">
            <center>
                Name:
                <s:textfield name="searchCriteria.name" theme="simple"/>
                Company Name:
                <s:textfield name="searchCriteria.companyName" theme="simple"/>
                <input type="hidden" name="searchCriteria.account"
                       value="${accountId}"
                       id="cs_${fieldId}_accountId"
                    ${accountIdDisabled} />
            </center>
            <hr class="buttons"/>
            <center>
                <sx:submit targets="%{#attr.divResultId}" type="button" value="%{'Submit'}" label="Submit" onblur="%{#attr.fieldId}_replaceLinks();"/>
                <input type="button" value="Cancel" class="closeDialog"/>
            </center>

        </s:form>
    </n:group2>

<%--<s:url id="test" value="dojo/contacts-search.action?tableParams.page=3&searchCriteria.account=122"/>--%>
<%--<sx:submit targets="%{#attr.divResultId}" href="%{test}" value="inna strona"/>--%>

    <n:group2 titleText="Results" cssIcon="group-icon-contact">
        <div id="${divResultId}">
            <!-- loaded via AJAX call -->
        </div>
    </n:group2>
</div>
<script>
    function ${fieldId}_getDiv(){
        return "<c:out value="${divResultId}"/>";
    }
</script>

