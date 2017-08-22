<%@ attribute name="ticket" required="true" type="pl.nask.crs.ticket.Ticket" %>
<%@ attribute name="hostmasters" required="true" type="java.util.List" %>
<%@ attribute name="idPrefix" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
	if(idPrefix==null)
    	idPrefix = "";
    String dialogId = idPrefix + "reassignDialog" + ticket.getId();
    String openTriggerId = idPrefix + "openReassignDialog" + ticket.getId();
%>
<script type="text/javascript">
    $(document).ready(function() {
        $('#<%=dialogId%>').jqm({trigger: false})
                .jqmAddTrigger('#<%=openTriggerId%>')
                .jqmAddClose($('.closeDialog'));
    })
</script>
<div id="<%=dialogId%>" class="jqmWindow">
    <form action="ticketbrowser-reassign.action" method="post">
        <n:group2 titleText="Reassign" cssIcon="group-icon-ticketreassign">
            <center style="margin: 10px 0">
                Reassigning the ticket ${ticket.id} for the domain ${ticket.operation.domainNameField.newValue}
            </center>
            <input type="hidden" name="id" value="${ticket.id}"/>
            <c:set var="checkedOutTo" value="${ticket.checkedOutTo.name}"/>
            <center>
                <s:select list="hostmasters" name="newHostmaster" theme="simple" listKey="username" listValue="name"
                          value="#attr.checkedOutTo"/>
            </center>
            <hr class="buttons"/>
            <center>
                <input type="Submit" value="Submit"/>
                <input type="button" value="Cancel" class="closeDialog"/>
            </center>
        </n:group2>
    </form>
</div>
