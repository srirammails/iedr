<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Job View</title>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<n:group2 titleText="Jobs">
    <display:table name="paginatedResult" id="jobRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external" excludedParams="resetSearch">
        <display:column title="Name" property="name" sortable="true"/>
        <display:column title="Start Date" sortProperty="start" sortable="true" >
            <s:date name="#attr.jobRow.start" format="yyyy-MM-dd HH:mm:ss"/>
        </display:column>
        <display:column title="End Date" sortProperty="end" sortable="true" >
            <s:date name="#attr.jobRow.end" format="yyyy-MM-dd HH:mm:ss"/>
        </display:column>
        <display:column title="Status" property="statusName" sortProperty="status" sortable="true"/>
        <display:column title="Failure Reason" property="failure" sortable="false"/>
    </display:table>
</n:group2>

</body>
</html>