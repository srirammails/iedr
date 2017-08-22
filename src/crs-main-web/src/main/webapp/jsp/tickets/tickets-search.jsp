<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Tickets Search</title>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jqModal.js"></script>
	<s:head/>
	<sx:head/>
</head>
<body>

<s:set var="adminStatusList" value="adminStatuses"/>
<s:set var="hostmasterList" value="hostmasters"/>
<s:set var="tickets" value="paginatedResult.list"/>

<n:error2 cssIcon="group-icon-error" titleText="Error">
	<display:table name="processedTicketsList" id="ticketRow" class="error-result"
						requestURI=""
						decorator="pl.nask.crs.web.displaytag.TicketTableDecorator" excludedParams="resetSearch">
		<display:setProperty name="paging.banner.placement">none</display:setProperty>
		<display:setProperty name="basic.msg.empty_list" value=" " />
		<display:column title="Date" sortProperty="creationDate">
			<s:date name="#attr.ticketRow.creationDate" />
		</display:column>
		<display:column title="Account Name"
							 property="operation.resellerAccountField.newValue.name" escapeXml="true"/>
		<display:column title="Domain Name"
							 property="operation.domainNameField.newValue" />
		<display:column title="Req" property="operation.type" />
		<display:column title="Admin Status"
							 property="adminStatus.description" />
		<display:column title="Tech Status" property="techStatus.description" />
		<display:column title="Financial Status" property="financialStatus.description"/>		
		<display:column title="Chk to" property="checkedOutTo.nicHandle" />
		<display:column title="Chk" property="checkedOut" />
		<display:column title="Docs" property="hasDocuments"/>
		<display:column title="Holder" escapeXml="true">
			${ticketRow.operation.domainHolderField.newValue}
        </display:column>
        <display:column title="">
			<ticket:browseractions ticket="${ticketRow}" idPrefix="err"/>
		</display:column>

	</display:table>
	<c:forEach items="${processedTicketsList}" var="ticketRow">
		<s:if test="allowCheckIn(#attr.ticketRow)">
			<ticket:checkin ticket="${ticketRow}"
								 adminStatuses="${adminStatusList}"
								 idPrefix="err"/>
		</s:if>
		<s:if test="allowAlterStatus(#attr.ticketRow)">
			<ticket:alterstatus ticket="${ticketRow}"
									  adminStatuses="${adminStatusList}"
									  idPrefix="err"/>
		</s:if>
		<s:if test="allowReassign(#attr.ticketRow)">
			<ticket:reassign ticket="${ticketRow}"
								  hostmasters="${hostmasterList}"
								  idPrefix="err"/>
		</s:if>
	</c:forEach>
</n:error2>

<s:form action="tickets-search" theme="simple">
<n:group2 titleText="Criteria" cssIcon="group-icon-search">
<div style="padding:5px 0 0 0; height:26px;">
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="Date From" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;" cssCtrlStyle="float:left;width:60%;">
				<n:datefield2 field="searchCriteria.from"/>
			</n:field2>
		</div>
	</div>
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssLabelStyle="float:left;width:25%;" cssCtrlStyle="float:left;width:42%;">
				<n:datefield2 field="searchCriteria.to"/>
			</n:field2>
		</div>
	</div>
</div>

<div style="height:26px;">
	<div style="float:left; width:33%">
		<div>
			<n:field2 label="Admin Status" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssContainerStyle="padding-left:30%;"
						 cssLabelStyle="float:left;width:40%;"
						 cssCtrlStyle="float:left;width:60%;">
				<s:select list="adminStatusList" name="searchCriteria.adminStatus"
							 theme="simple" cssStyle="width:150px;"
							 listKey="id" listValue="description" headerKey="-1"
							 headerValue="[ALL]"/>
			</n:field2>
		</div>
	</div>
	<div style="float:left; width:33%">
		<div>
			<n:field2 label="Tech Status" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
                         cssContainerStyle="padding-left:20%;"
                         cssLabelStyle="float:left;width:25%;"
						 cssCtrlStyle="float:left;width:75%;">
				<s:select list="techStatuses" name="searchCriteria.techStatus"
							 theme="simple" cssStyle="width:150px;" 
							 listKey="id" listValue="description" headerKey="-1"
							 headerValue="[ALL]"/>
			</n:field2>
		</div>
	</div>
    <div style="float:left; width:33%">
        <div>
            <n:field2 label="Financial Status" labelJustify="right" tooltipGapHidden="true"
                      fieldEditable="true"
                      cssLabelStyle="float:left;width:25%;"
                      cssCtrlStyle="float:left;width:75%;">
                <s:select list="financialStatuses" name="searchCriteria.financialStatus"
                          theme="simple" cssStyle="width:150px;"
                          listValue="description"
                          headerKey="" headerValue="[ALL]"/>
            </n:field2>
        </div>
    </div>
</div>

<div style="height:26px;">
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="Checked Out To" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssContainerStyle="padding-left:30%;"
						 cssLabelStyle="float:left;width:40%;"
						 cssCtrlStyle="float:left;width:60%;">
				<s:select list="hostmasters" name="searchCriteria.nicHandle"
							 theme="simple"  cssStyle="width:150px;"
							 listKey="username" listValue="username" headerKey=""
							 headerValue="[ALL]"/>
			</n:field2>
		</div>
	</div>
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="Account Name" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssLabelStyle="float:left;width:25%;"
						 cssCtrlStyle="float:left;width:75%;">
				<s:select list="accounts" name="searchCriteria.accountId"
							 theme="simple" cssStyle="width:150px;" 
							 listKey="id" listValue="name" headerKey="-1"
							 headerValue="[ALL]"/>
			</n:field2>
		</div>
	</div>
</div>

<div style="height:26px;">
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="Domain" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssContainerStyle="padding-left:30%;"
						 cssLabelStyle="float:left;width:40%;"
						 cssCtrlStyle="float:left;width:60%;">
				<s:textfield name="searchCriteria.domainName"
								 theme="simple" cssStyle="width:150px;text-indent:0"/>
			</n:field2>
		</div>
	</div>
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="Domain Holder" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssLabelStyle="float:left;width:25%;"
						 cssCtrlStyle="float:left;width:75%;">
				<s:textfield name="searchCriteria.domainHolder"
								 theme="simple" cssStyle="width:150px;text-indent:0"/>
			</n:field2>
		</div>
	</div>
</div>

<div style="height:26px;">
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="Class" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssContainerStyle="padding-left:30%;"
						 cssLabelStyle="float:left;width:40%;"
						 cssCtrlStyle="float:left;width:60%;">
				<s:select list="classList" listKey="name" listValue="name"
							 headerKey="" headerValue="[All]"
							 name="searchCriteria.clazz" value="searchCriteria.clazz"
							 theme="simple" cssStyle="width: 150px;"/>
			</n:field2>
		</div>
	</div>
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="Category" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssLabelStyle="float:left;width:25%;"
						 cssCtrlStyle="float:left;width:75%;">
				<s:select list="categoryList" listKey="name" listValue="name"
							 headerKey="" headerValue="[All]"
							 name="searchCriteria.category" value="searchCriteria.category"
							 theme="simple" cssStyle="width: 150px;"/>
			</n:field2>
		</div>
	</div>
</div>

<div style="clear:both;">
	<n:refreshsearch/>
</div>
</n:group2>
</s:form>

<n:group2 titleText="Results" cssIcon="group-icon-ticket">
	<display:table name="paginatedResult" id="ticketRow" class="result"
						requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
						sort="external" excludedParams="resetSearch">
		<display:column title="Date" sortable="true" sortProperty="creationDate">
			<s:date name="#attr.ticketRow.creationDate"/>
		</display:column>
		<display:column title="Account Name" property="operation.resellerAccountField.newValue.name" sortable="true"
							 sortProperty="resellerAccountName" escapeXml="true"/>
		<display:column title="Domain Name" property="operation.domainNameField.newValue" sortable="true"
							 sortProperty="domainName"/>
		<display:column title="Req" property="operation.type" sortable="true" sortProperty="type"/>
		<display:column title="Admin Status" property="adminStatus.description"/>
		<display:column title="Tech Status" property="techStatus.description"/>
		<display:column title="Financial Status" property="financialStatus.description"/>
		<display:column title="Chk to" property="checkedOutTo.nicHandle" sortable="true" sortProperty="CheckedOutTo"/>
		<display:column title="Chk" property="checkedOut" sortable="true" sortProperty="checkedOut"/>
		<display:column title="Docs" property="hasDocuments"/>
		<display:column title="Holder" sortable="true" sortProperty="domainHolder" escapeXml="true">
			${ticketRow.operation.domainHolderField.newValue}
        </display:column>
        <display:column title="">
            <ticket:browseractions ticket="${ticketRow}"/>
		</display:column>

	</display:table>
	<c:forEach items="${tickets}" var="ticketRow">
		<s:if test="allowCheckIn(#attr.ticketRow)">
			<ticket:checkin ticket="${ticketRow}" adminStatuses="${adminStatusList}"/>
		</s:if>
		<s:if test="allowAlterStatus(#attr.ticketRow)">
			<ticket:alterstatus ticket="${ticketRow}" adminStatuses="${adminStatusList}"/>
		</s:if>
		<s:if test="allowReassign(#attr.ticketRow)">
			<ticket:reassign ticket="${ticketRow}" hostmasters="${hostmasterList}"/>
		</s:if>
	</c:forEach>
</n:group2>


</body>
</html>