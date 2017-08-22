<%@ attribute name="fieldId" required="true" %>
<%@ attribute name="fieldEmail" required="true" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<%
    String dialogId = "contactSearchExtendedDialog" + fieldId;
    String openTriggerId = "openSearchExtendedDialog" + fieldId;
%>
<script type="text/javascript">
    $(document).ready(function() {
        $('#<%=dialogId%>').jqm({trigger: false})
                .jqmAddTrigger('#<%=openTriggerId%>')
                .jqmAddClose($('.closeDialog'));
    })
</script>
<c:set var="divResultId" value="div${fieldId}"/>
<c:set var="formName" value="formName${fieldId}"/>
<div id="<%=dialogId%>" class="jqmWindow">
    <input type="hidden" name="id" class="fieldId" value="${fieldId}"/>
    <input type="hidden" name="email" class="fieldEmail" value="${fieldEmail}"/>
    <n:group2 titleText="Search Contacts" cssIcon="group-icon-ticketreassign">
        <s:form name="%{#attr.formName}" action="contacts-search-extended" namespace="/dojo" theme="simple">
            <center>
                Name:
                <s:textfield name="searchCriteria.name" theme="simple"/>
            </center>
            <hr class="buttons"/>
            <center>
                <sx:submit targets="%{#attr.divResultId}"/>
                <input type="button" value="Cancel" class="closeDialog"/>
            </center>
        </s:form>
    </n:group2>

    <n:group2 titleText="Results" cssIcon="group-icon-contact">
        <div id="${divResultId}">
            <!-- loaded via AJAX call -->
        </div>


    </n:group2>
</div>
