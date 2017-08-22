<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nichandle" tagdir="/WEB-INF/tags/nichandles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Nic Handle Search</title>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jqModal.js"></script>
	<s:head/>
	<sx:head/>
</head>
<body>

<s:set var="nicHandles" value="paginatedResult.list"/>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="nic-handles-search" theme="simple">
	<n:group2 titleText="Criteria" cssIcon="group-icon-search">

		<div style="padding:5px 0 0 0; height:26px;">
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Nic Handle Id" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
								 cssCtrlStyle="float:left;width:60%;">
						<s:textfield name="searchCriteria.nicHandleId" theme="simple" cssStyle="width:150px;text-indent:0;"/>
					</n:field2>
				</div>
			</div>
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Email" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:65%;">
						<s:textfield name="searchCriteria.email" theme="simple" cssStyle="width:150px;text-indent:0;"/>
					</n:field2>
				</div>
			</div>
		</div>

		<div style="height:26px;">
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Name" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
								 cssCtrlStyle="float:left;width:60%;">
						<s:textfield name="searchCriteria.name" theme="simple" cssStyle="width:150px;text-indent:0;"/>
					</n:field2>
				</div>
			</div>
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Account" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:65%;">
						<s:select name="searchCriteria.accountNumber"
									 list="accounts" listKey="id" listValue="name"
									 headerKey="-1" headerValue="[ALL]"
									 theme="simple"  cssStyle="width:150px;"/>
					</n:field2>
				</div>
			</div>
		</div>

		<div style="height:26px;">
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Company Name" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
								 cssCtrlStyle="float:left;width:60%;">
						<s:textfield name="searchCriteria.companyName" theme="simple" cssStyle="width:150px;text-indent:0;"/>
					</n:field2>
				</div>
			</div>
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Domain Name" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:65%;">
						<s:textfield name="searchCriteria.domainName" theme="simple" cssStyle="width:150px;text-indent:0;"/>
					</n:field2>
				</div>
			</div>
		</div>

		<div style="clear:both;">
			<n:refreshsearch/>
		</div>
	</n:group2>
</s:form>

<n:group2 titleText="Results" cssIcon="group-icon-nichandle">
	<display:table name="paginatedResult" id="nicHandleRow" class="result" requestURI=""
						decorator="pl.nask.crs.web.displaytag.TicketTableDecorator" sort="external" excludedParams="resetSearch">
		<display:column title="Nic Handle Id" property="nicHandleId" sortable="true" sortProperty="nicHandleId"/>
		<display:column title="Name" property="name" sortable="true" sortProperty="name" escapeXml="true"/>
		<display:column title="Company Name" property="companyName" sortable="true" sortProperty="companyName" escapeXml="true"/>
		<display:column title="Account" property="account.name" sortable="true" sortProperty="accountName" escapeXml="true"/>
		<display:column title="E-mail" property="email" sortable="true" sortProperty="email" escapeXml="true"/>
		<display:column title="Status" property="status" sortable="true" sortProperty="status"/>

		<display:column title="">
			<s:div cssClass="ticket-row-images">
				<s:url var="viewUrl" action="nic-handle-view" includeParams="none">
					<s:param name="nicHandleId">${nicHandleRow.nicHandleId}</s:param>
					<s:param name="previousAction">nic-handles-search</s:param>
				</s:url>
				<%--<s:url var="editUrl" action="nic-handle-edit" includeParams="none">--%>
				<%--<s:param name="id">${nicHandleRow.nicHandleId}</s:param>--%>
				<%--</s:url>--%>
				<s:a href="%{viewUrl}">
					<img src="images/skin-default/action-icons/nichandle.png" alt="View" title="View"/>
				</s:a>
				<%--<s:a href="%{editUrl}">--%>
				<!--<img src="images/ticket.revise.16.png" alt="Edit" title="Edit"/>-->
				<%--</s:a>--%>

				<!--<img src="images/nichandle.alterstatus.16.png" alt="Alter Status"-->
				<!--title="Alter Status" id="openAlterStatusDialog${nicHandleRow.nicHandleId}"/>-->
			</s:div>
		</display:column>

	</display:table>

	<%--<c:forEach items="${nicHandles}" var="nicHandleRow">--%>
	<%--<nichandle:alterstatus nicHandle="${nicHandleRow}" statuses="${adminStatusList}"/>--%>
	<%--</c:forEach>--%>
</n:group2>
</body>
</html>