<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<n:group2 titleText="History" cssIcon="group-icon-history">
    <s:set var="histselected" value="#attr.historicalSelected"/>
    <display:table name="paginatedResult" id="result" class="result" requestURI="" excludedParams="resetSearch">
        <c:set var="cssClass"
               value="${(histselected == (result_rowNum-1))? 'selected' : ''}"/>
        <display:column title="Date" class="${cssClass}">
            <s:date name="#attr.result.object.changeDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Nic Handle" class="${cssClass}" property="changedBy"/>
        <display:column title="Remark" property="object.nicHandleRemark" class="${cssClass}" escapeXml="true"/>
        <display:column title="" class="${cssClass}">
            <s:set var="nicHandleId" value="#attr.result.object.nicHandleId"/>
            <s:set var="prevAction" value="#attr.previousAction"/>
            <s:url var="historicalNicHandleView" action="nic-handle-view" includeParams="none">
                <s:param name="changeId" value="#attr.result.changeId"/>
                <s:param name="nicHandleId" value="%{nicHandleId}"/>
                <s:param name="previousAction" value="%{prevAction}"/>
                <s:param name="tableParams.page" value="%{tableParams.page}"/>
            </s:url>
            <s:a href="%{historicalNicHandleView}">
                <img src="images/skin-default/action-icons/nichandle.png" alt="View" title="View"/>
            </s:a>
        </display:column>
    </display:table>

</n:group2>