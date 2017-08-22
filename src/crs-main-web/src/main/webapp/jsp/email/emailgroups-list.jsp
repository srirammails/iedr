<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Email Group Listing</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <s:head/>
</head>
<body>

<n:group2 titleText="Email Groups" cssIcon="group-icon-account">
    <display:table name="paginatedResult" id="emailGroupRow" requestURI="" class="result" sort="external" excludedParams="resetSearch">
        <display:column title="EG_ID" property="id"/>
        <display:column title="Name" property="name"/>
        <display:column title="Visible" property="visible" decorator="pl.nask.crs.web.displaytag.BooleanDecorator"/>
        <display:column title="View">
            <s:div cssClass="ticket-row-images">
                <s:url var="viewUrl" action="emailgroup-view" includeParams="none">
                    <s:param name="id">${emailGroupRow.id}</s:param>
                    <s:param name="previousAction">emailgroups-search</s:param>
                </s:url>
                <s:a href="%{viewUrl}">
                    <img src="images/skin-default/action-icons/details.png" alt="View" title="View"/>
                </s:a>
            </s:div>
        </display:column>
    </display:table>
</n:group2>

<s:if test="%{hasPermission('emailgroup.new.button')}">
    <s:form name="group" theme="simple">
        <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr>
                <td colspan="2" align="center">
                    <hr class="buttons"/>
                    <center>
                        <s:submit value="Create new" action="emailgroup-new" theme="simple" cssClass="xSave"/>
                    </center>
                </td>
            </tr>
        </table>
    </s:form>
</s:if>

</body>
</html>
