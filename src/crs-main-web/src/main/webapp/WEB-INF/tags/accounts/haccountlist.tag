<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<n:group2 titleText="History" cssIcon="group-icon-history">
    <s:set var="histselected" value="#attr.historicalSelected"/>
    <display:table name="accountHist" id="result" class="result" requestURI="" excludedParams="resetSearch">
        <c:set var="cssClass"
               value="${(histselected == (result_rowNum-1))? 'selected' : ''}"/>
        <display:column title="Date" class="${cssClass}">
            <s:date name="#attr.result.object.changeDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Remark" property="object.remark" class="${cssClass}" escapeXml="true"/>
        <display:column title="" class="${cssClass}">
            <s:set var="id" value="#attr.result.object.id"/>
            <s:set var="prevAction" value="#attr.previousAction"/>
            <s:url var="historicalAccountView" action="account-view-view" includeParams="none">
                <s:param name="changeId" value="#attr.result.changeId"/>
                <s:param name="id" value="%{id}"/>
                <s:param name="previousAction" value="%{prevAction}"/>
            </s:url>
            <s:a href="%{historicalAccountView}">
                <img src="images/skin-default/action-icons/ticket.png" alt="View" title="View"/>
            </s:a>
        </display:column>
    </display:table>

</n:group2>