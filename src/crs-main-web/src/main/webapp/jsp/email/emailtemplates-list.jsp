<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Emails Listing</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <s:head/>
</head>
<body>

<n:group2 titleText="Emails" cssIcon="group-icon-account">
    <s:if test="%{showAll}">
        <s:url var="viewUrl" action="emailtemplates-search" includeParams="none">
            <s:param name="showAll">false</s:param>
        </s:url>
        <s:a href="%{viewUrl}" cssStyle="float:right">
            Show paginated
        </s:a>
    </s:if>
    <s:else>
        <s:url var="viewUrl" action="emailtemplates-search" includeParams="none">
            <s:param name="showAll">true</s:param>
        </s:url>
        <s:a href="%{viewUrl}" cssStyle="float:right">
            Show all
        </s:a>
    </s:else>
    <display:table name="paginatedResult" id="emailTemplateRow" requestURI="" class="result" sort="external" excludedParams="resetSearch" decorator="pl.nask.crs.web.displaytag.EmailTemplateTableDecorator">
        <display:column title="E_ID" property="id" sortable="true" sortProperty="id"/>
        <display:column title="Subject" property="subject" sortable="true" sortProperty="subject"/>
        <display:column title="Group" property="group.name" sortable="true" sortProperty="group_id"/>
        <display:column title="Internal Recipients" property="internalRecipients" escapeXml="false"/>
        <display:column title="External Recipients" property="recipients" escapeXml="false"/>
        <display:column title="Reason for sending" property="sendReason" sortable="true" sortProperty="sendReason"/>
        <display:column title="Allow suppression" property="suppressible" decorator="pl.nask.crs.web.displaytag.BooleanDecorator" sortable="true" sortProperty="suppressible"/>
        <display:column title="View">
            <s:div cssClass="ticket-row-images">
                <s:url var="viewUrl" action="emailtemplate-view" includeParams="none">
                    <s:param name="id">${emailTemplateRow.id}</s:param>
                    <s:param name="previousAction">emailtemplates-search</s:param>
                </s:url>
                <s:a href="%{viewUrl}">
                    <img src="images/skin-default/action-icons/details.png" alt="View" title="View"/>
                </s:a>
            </s:div>
        </display:column>
    </display:table>
</n:group2>

</body>
</html>
