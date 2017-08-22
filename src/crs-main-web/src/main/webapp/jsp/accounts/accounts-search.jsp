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
	<title>Account Search</title>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jqModal.js"></script>
	<s:head/>
	<sx:head/>
</head>
<body>

<s:set var="accounts" value="paginatedResult.list"/>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="accounts-search" theme="simple">
	<n:group2 titleText="Criteria" cssIcon="group-icon-search">
		<div style="height:26px;">
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Account No" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
								 cssCtrlStyle="float:left;width:60%;">
						<s:textfield name="searchCriteria.id" theme="simple" cssStyle="width:150px;"/>
					</n:field2>
				</div>
			</div>
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Account Name" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:65%;">
						<s:textfield name="searchCriteria.name" theme="simple" cssStyle="width:150px;"/>
					</n:field2>
				</div>
			</div>
		</div>

		<div style="height:26px;">
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Billing Contact" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
								 cssCtrlStyle="float:left;width:60%;">
						<s:textfield name="searchCriteria.nicHandle" theme="simple" cssStyle="width:150px;"/>
					</n:field2>
				</div>
			</div>
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Domain Name" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:65%;">
						<s:textfield name="searchCriteria.domainName" theme="simple" cssStyle="width:150px;"/>
					</n:field2>
				</div>
			</div>
		</div>

		<div style="clear:both;">
			<n:refreshsearch/>
		</div>
	</n:group2>
</s:form>

<n:group2 titleText="Results" cssIcon="group-icon-account">
	<display:table name="paginatedResult" id="accountRow" class="result" requestURI=""
						decorator="pl.nask.crs.web.displaytag.TicketTableDecorator" sort="external" excludedParams="resetSearch">
		<display:column title="Account No" property="id" sortable="true" sortProperty="id"/>
		<display:column title="Name" property="name" sortable="true" sortProperty="name" escapeXml="true"/>
		<display:column title="Billing Contact" property="billingContact.nicHandle" sortable="true" sortProperty="billingContactId"/>
		<display:column title="Status" property="status" sortable="true" sortProperty="status"/>

		<display:column title="">
			<s:div cssClass="ticket-row-images">
				<s:url var="viewUrl" action="account-view-view" includeParams="none">
					<s:param name="id">${accountRow.id}</s:param>
					<s:param name="previousAction">accounts-search</s:param>
				</s:url>
				<s:a href="%{viewUrl}">
					<img src="images/skin-default/action-icons/account.png" alt="View" title="View"/>
				</s:a>
			</s:div>
		</display:column>

	</display:table>

</n:group2>


</body>
</html>