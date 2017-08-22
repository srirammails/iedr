<%@ attribute name="ticketHistory" required="true" type="java.util.List" %>
<%@ attribute name="selected" required="true" type="java.lang.Integer" %>
<%@ attribute name="previousAction" required="true" %>
<%@ attribute name="historyAction" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty historyAction}">
    <c:set var="historyAction" value="ticketview-history"/>
</c:if>
<display:table class="result" name="ticketHistory" id="histRow" excludedParams="resetSearch">
    <c:set var="cssClass" value="${selected == (histRow_rowNum-1) ? 'selected' : ''}"/>
    <display:column title="Date" class="${cssClass}">
        <s:date name="#attr.histRow.object.changeDate" format="dd/MM/yyyy"/>
    </display:column>
    <display:column title="Nic Handle" property="changedBy" class="${cssClass}"/>
    <display:column title="Remark" property="object.hostmastersRemark" class="${cssClass}" escapeXml="true"/>
    <display:column class="${cssClass}">
        <s:url var="showHistoryUrl" action="%{#attr.historyAction}" includeParams="none">
            <s:param name="id">${histRow.object.id}</s:param>
            <s:param name="historyIndex">${histRow_rowNum-1}</s:param>
            <s:param name="previousAction">${previousAction}</s:param>
        </s:url>
        <s:a href="%{showHistoryUrl}">
            <img src="images/skin-default/action-icons/ticket.png" alt="View Historical Record"
                 title="View Historical Record"/>
        </s:a>
    </display:column>
</display:table>
