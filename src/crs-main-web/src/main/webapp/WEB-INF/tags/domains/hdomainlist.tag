<%@ attribute name="firstSearch" required="true" type="java.lang.Boolean" %>
<%@ attribute name="sortable" required="true" type="java.lang.Boolean" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<n:group2 titleText="History" cssIcon="group-icon-history">
    <s:set var="page" value="#attr.tableParams.page"/>  <%-- used in HistoricalDomainRowDecorator --%>
    <s:set var="selected" value="#attr.selected"/> <%-- used in HistoricalDomainRowDecorator --%>
    <s:set var="selectedPage" value="#attr.selectedPage"/> <%-- used in HistoricalDomainRowDecorator --%>
    <display:table name="paginatedResult" id="domainRow" class="result" requestURI=""
                   excludedParams="firstSearch resetSearch">
        <c:set var="cssClass"
               value="${((selected == (domainRow_rowNum-1)) && (page == selectedPage))? 'selected' : ''}"/>
        <display:column title="Date" class="${cssClass}" sortable="${sortable}" sortProperty="changeDate">
            <s:date name="#attr.domainRow.changeDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Domain Name" class="${cssClass}" property="object.name" sortable="${sortable}"
                        sortProperty="name"/>
        <display:column title="Changed By" property="changedBy" class="${cssClass}" sortable="${sortable}"
                        sortProperty="changedByNicHandle"/>
        <display:column title="Domain Holder" property="object.holder" class="${cssClass}" sortable="${sortable}"
                        sortProperty="holder" escapeXml="true"/>
        <display:column title="Remark" property="object.remark" class="${cssClass}" escapeXml="true"/>
        <display:column title="" class="${cssClass}">
            <s:set var="domainName" value="#attr.domainRow.object.name"/>
            <s:set var="prevAction" value="#attr.previousAction"/>
            <s:url var="historicalDomainView" action="historical-domain-view" includeParams="none">
                <s:param name="changeId" value="#attr.domainRow.changeId"/>
                <s:param name="searchCriteria.domainName" value="%{domainName}"/>
                <s:param name="previousAction" value="%{prevAction}"/>
                <s:param name="tableParams.page" value="#attr.tableParams.page"/>
                <s:param name="firstSearch">${firstSearch}</s:param>
            </s:url>
            <s:a href="%{historicalDomainView}">
                <img src="images/skin-default/action-icons/domain.png" alt="View" title="View"/>
            </s:a>
        </display:column>
    </display:table>

</n:group2>