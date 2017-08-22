<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>List bulk transfers</title>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jqModal.js"></script>
	<s:head/>
	<sx:head/>
</head>
<body>
<s:set var="results" value="transfers" />
<n:group2 titleText="Bulk transfers" cssIcon="group-icon-domain">
	<display:table name="results" id="row" class="result">
		<display:column title="ID" property="id" />
		<display:column title="Losing Account" property="losingAccountName" />
		<display:column title="Gaining Account" property="gainingAccountName" />
		
		<display:column title="Completion Date" property="completionDate" />
		<display:column title="">
			<s:div cssClass="ticket-row-images">
				<s:url var="viewUrl" action="bulkTransferView" includeParams="none">
					<s:param name="id">${row.id}</s:param>
				</s:url>
				<s:a href="%{viewUrl}">
					<img src="images/skin-default/action-icons/details.png" alt="View" title="View"/>
				</s:a>
			</s:div>
		</display:column>
	</display:table>
</n:group2>

</body></html>