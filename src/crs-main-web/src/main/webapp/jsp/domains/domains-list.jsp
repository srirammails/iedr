<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="domain" tagdir="/WEB-INF/tags/domains" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Domains Search</title>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jqModal.js"></script>
	<s:head/>
	<sx:head/>
</head>
<body>

<s:set var="hostmasterList" value="hostmasters"/>
<s:set var="domains" value="paginatedResult.list"/>
<n:error2 cssIcon="group-icon-error" titleText="Error">
	<display:table name="ProcessedDomainsList" id="domainRow" class="error-result" requestURI="">
		<display:setProperty name="paging.banner.placement" value="none"/>
		<display:setProperty name="basic.msg.empty_list" value=" "/>
		<display:column title="Registration Date">
			<s:date name="#attr.domainRow.registrationDate" format="dd/MM/yyyy"/>
		</display:column>
		<display:column title="Renew Date">
			<s:date name="#attr.domainRow.renewDate" format="dd/MM/yyyy"/>
		</display:column>
		<display:column title="Account Name" property="resellerAccount.name"/>
		<display:column title="Domain Name" property="name"/>
		<display:column title="Domain Holder" property="holder"/>
		<display:column title="">
			<s:div cssClass="ticket-row-images">
				<s:url var="viewUrl" action="domainview" includeParams="none">
					<s:param name="domainName">${domainRow.name}</s:param>
					<s:param name="previousAction">domains-input</s:param>
				</s:url>
				<s:a href="%{viewUrl}">
					<img src="images/skin-default/action-icons/domain.png" alt="View" title="View"/>
				</s:a>
			</s:div>
		</display:column>
	</display:table>
</n:error2>

<domain:domainlist />
<hr class="buttons"/>
<center><input type="button" value="Back" onClick="history.back()"/></center>
</body>
</html>