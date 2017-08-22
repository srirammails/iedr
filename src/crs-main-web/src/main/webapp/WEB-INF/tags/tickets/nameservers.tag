<%@ attribute name="nameservers" required="true" type="java.util.List" %>
<%@ attribute name="modifyingTicket" required="true" type="java.lang.Boolean" %>
<%@ attribute name="inputDisabled" required="true" type="java.lang.Boolean" %>
<%@ attribute name="frDisabled" required="true" type="java.lang.Boolean" %>
<%@ attribute name="nameFailureReasons" required="true" type="java.util.List" %>
<%@ attribute name="ipFailureReasons" required="true" type="java.util.List" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%@tag import="pl.nask.crs.web.ticket.wrappers.NameserverWrapper" %>
<%@tag import="pl.nask.crs.ticket.operation.FailureReason" %>

<style>
#ticket-nameservers {
    padding: 0;
    list-style: none;
}
.ticket-nameserver {
    margin-bottom: 10px;
}
#add-nameserver {
    margin-top: 20px;
    margin-bottom: 20px;
}
#add-nameserver,
.ticket-nameserver .button {
    border: 1px solid #ABAEAB;
    display: block;
    margin-left: 70px;
    padding: 5px;
    text-align: center;
    width: 65px;
}
#add-nameserver:hover,
.ticket-nameserver .button:hover {
    border: 1px solid black;
    background: none;
}
.ticket-nameserver .field-label {
    float:left;
    width:70px;
    line-height: 1.8em;
}
.ticket-nameserver .ctrl-field       { width: 200px; }
.ticket-nameserver .ctrl-field input { width: 200px; }
.ticket-nameserver .failure-reason {
    float:right;
    width:120px;
}
.ticket-nameserver .failure-reason.read-only {
    padding-top: 6px;
}

</style>

<ol id="ticket-nameservers">

<c:forEach items="${nameservers}" var="ns" varStatus="stat">
<li class="ticket-nameserver">
    <c:set var="num" value="${stat.count-1}"/>

    <%
    Long num = (Long) jspContext.getAttribute("num");
    NameserverWrapper w = (NameserverWrapper) jspContext.getAttribute("ns");
    if (modifyingTicket && w.isNameModification())
        jspContext.setAttribute("nsNameClass", "modification");
    else
        jspContext.setAttribute("nsNameClass", "");

    if (modifyingTicket && w.isIpModification())
        jspContext.setAttribute("nsIpClass", "modification");
    else
        jspContext.setAttribute("nsIpClass", "");
    %>

    <div class="ctrl-container">
        <div class="field-label">Name:</div>
        <div class="ctrl-field">
            <input class="${nsNameClass}" type="text" value="${ns.name}"
                     name="ticketWrapper.newNameserverWrappers[${num}].name" ${inputDisabled ? "disabled" : ""}/>
        </div>
        <% if (!frDisabled) {%>
        <div class="failure-reason">
            <s:select list="#attr.nameFailureReasons" headerKey="0" headerValue="(none)" listKey="id" listValue="description"
                         cssStyle="width:120px;" theme="simple" disabled="#attr.frDisabled">
                <s:param name="name">ticketWrapper.newNameserverWrappers[${num}].nameFr</s:param>
                <s:param name="value">${ns.nameFr}</s:param>
            </s:select>
        </div>
        <% } else { %>
        <div class="failure-reason read-only">
            <%
                String nameFRDescription = "";
                for (Object o : nameFailureReasons) {
                    FailureReason reason = (FailureReason) o;
                    if (reason.getId() == w.getNameFr()) {
                        nameFRDescription = reason.getDescription();
                        break;
                    }
                }
            %>
            <%=nameFRDescription %>
        </div>
        <% } %>
    </div>

    <% String nnames = "ticketWrapper.nameserverWrappers[" + num + "].name,ticketWrapper.nameserverWrappers[" + num + "].nameFr"; %>
    <n:fielderror fields="<%=nnames %>"/>

    <div class="ctrl-container">
        <div class="field-label">IPv4:</div>
        <div class="ctrl-field">
            <input class="${nsIpClass}" type="text" value="${ns.ipv4}"
                     name="ticketWrapper.newNameserverWrappers[${num}].ipv4" ${inputDisabled ? "disabled" : ""}/>
        </div>
        <% if (!frDisabled) {%>
        <div class="failure-reason">
            <s:select list="#attr.ipFailureReasons" headerKey="0" headerValue="(none)" listKey="id" listValue="description"
                         cssStyle="width:120px;" theme="simple" disabled="#attr.frDisabled">
                <s:param name="name">ticketWrapper.newNameserverWrappers[${num}].ipv4Fr</s:param>
                <s:param name="value">${ns.ipv4Fr}</s:param>
            </s:select>
        </div>
        <% } else { %>
        <div class="failure-reason read-only">
            <%
                String ipv4FRDescription = "";
                for (Object o : ipFailureReasons) {
                    FailureReason reason = (FailureReason) o;
                    if (reason.getId() == w.getIpv4Fr()) {
                        ipv4FRDescription = reason.getDescription();
                        break;
                    }
                }
            %>
            <%=ipv4FRDescription %>
        </div>
        <% } %>
    </div>

    <% String ipnames = "ticketWrapper.nameserverWrappers[" + num + "].ipv4,ticketWrapper.nameserverWrappers[" + num + "].ipv4Fr"; %>
    <n:fielderror fields="<%=ipnames %>"/>
    <% if (!inputDisabled) {%>
        <a class="button">Remove</a>
    <% } %>
</li>
</c:forEach>
</ol>

<n:fielderror fields="ticketWrapper.nameservers"/>

<% if (!inputDisabled) {%>

<a id="add-nameserver" class="button">Add</a>

<script id="ticket-nameserver-template" type="text/template">
    <li class="ticket-nameserver">
        <div class="ctrl-container">
            <div class="field-label">Name:</div>
            <div class="ctrl-field">
                <input type="text" name="ticketWrapper.newNameserverWrappers[%ID%].name"/>
            </div>
            <div class="failure-reason">
                <s:select list="#attr.nameFailureReasons" headerKey="0" headerValue="(none)" listKey="id" listValue="description"
                             cssStyle="width:120px;" theme="simple">
                    <s:param name="name">ticketWrapper.newNameserverWrappers[%ID%].nameFr</s:param>
                </s:select>
            </div>
        </div>

        <div class="ctrl-container">
            <div class="field-label">IPv4:</div>
            <div class="ctrl-field">
                <input type="text" name="ticketWrapper.newNameserverWrappers[%ID%].ipv4"/>
            </div>
            <div class="failure-reason">
                <s:select list="#attr.ipFailureReasons" headerKey="0" headerValue="(none)" listKey="id" listValue="description"
                             cssStyle="width:120px;" theme="simple" disabled="#attr.frDisabled">
                    <s:param name="name">ticketWrapper.newNameserverWrappers[%ID%].ipv4Fr</s:param>
                </s:select>
            </div>
        </div>
        <a class="button" href="#">Remove</a>
    </li>
</script>

<script type="text/javascript">
$(function() {
    var next_ns_idx = $(".ticket-nameserver").size(),
        nameserverTpl = $("#ticket-nameserver-template").text();
    var removeCallback = function() {
        var liElem = $(this).parents(".ticket-nameserver"),
            mappings = {
                '%NAME%' : $("input[name~=name]", liElem).val() || 'blank',
                '%IPV4%'   : $("input[name~=ipv4]", liElem).val() || 'blank'} ,
            msg   = "Name: %NAME%\nIPv4: %IPV4%\nAre you sure you want to delete this entry?".replace(/%[\w]+%/g, function(match) {
                return mappings[match] || match;
            });
        if (confirm(msg))
            $(this).parents(".ticket-nameserver").remove();
        return false;
    };
    $(".ticket-nameserver .button").bind("click", removeCallback);
    $("#add-nameserver").bind("click", function() {
        var newElem = $(nameserverTpl.replace(/%ID%/g, next_ns_idx++));
        $(".button", newElem).bind("click", removeCallback);
        $("#ticket-nameservers").append(newElem);
        return false;
    });
});
</script>
<% } %>
