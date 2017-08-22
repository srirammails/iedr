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
		<display:column title="Account Name" property="resellerAccount.name" escapeXml="true"/>
		<display:column title="Domain Name" property="name"/>
		<display:column title="Domain Holder" property="holder" escapeXml="true"/>
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

<s:form action="domains-search" theme="simple" name="domainsSearch">
<n:group2 titleText="Criteria" cssIcon="group-icon-search">

<div style="padding:5px 0; height:26px;">
	<div style="float:left; width:33%">
		<div>
			<n:field2 label="Domain" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:70%;">
				<s:textfield name="searchCriteria.domainName" theme="simple" cssStyle="width:150px;text-indent:0;"/>
			</n:field2>
		</div>
	</div>
	<div style="float:left; width:33%">
		<div>
			<n:field2 label="Holder" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:70%;">
				<s:textfield name="searchCriteria.domainHolder" theme="simple" cssStyle="width:150px;text-indent:0;"/>
			</n:field2>
		</div>
	</div>
	<div style="float:left; width:33%">
		<div>
			<n:field2 label="Contact" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:70%;">
				<s:textfield name="searchCriteria.nicHandle" theme="simple" cssStyle="width:150px;text-indent:0;"/>
			</n:field2>
		</div>
	</div>
</div>

<div style="padding: 0 0 5px 0; height:26px;">
	<div style="float:left; width:25%">
		<div>
			<n:field2 label="Account" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssLabelStyle="float:left;width:30%;"
						 cssCtrlStyle="float:left;width:70%;">
				<s:select name="searchCriteria.accountId" list="accounts"
							 listKey="id" listValue="name" headerKey="-1" headerValue="[ALL]"
							 theme="simple" cssStyle="width:150px;"/>
			</n:field2>
		</div>
	</div>
    <div style="float:left; width:25%">
        <div style="float:left; width:50%">
            <div>
                <n:field2 label="Class" labelJustify="right" tooltipGapHidden="true"
                          fieldEditable="true"
                          cssLabelStyle="float:left;width:10%;"
                          cssCtrlStyle="float:left;width:50%;">
                    <s:select list="classList" listKey="name" listValue="name"
                                 headerKey="" headerValue="[All]"
                                 name="searchCriteria.holderClass" value="searchCriteria.holderClass"
                                 theme="simple" cssStyle="width: 150px;"/>
                </n:field2>
            </div>
        </div>
        <div style="float:left; width:50%">
            <div>
                <n:field2 label="Category" labelJustify="right" tooltipGapHidden="true"
                          fieldEditable="true"
                          cssLabelStyle="float:left;width:10%;"
                          cssCtrlStyle="float:left;width:50%;">
                    <s:select list="categoryList" listKey="name" listValue="name"
                              headerKey="" headerValue="[All]"
                              name="searchCriteria.holderCategory" value="searchCriteria.holderCategory"
                              theme="simple" cssStyle="width: 150px;"/>
                </n:field2>
            </div>
        </div>
    </div>
    <div style="float:left; width:25%">
        <div>
            <n:field2 label="Status" labelJustify="right" tooltipGapHidden="true"
                      fieldEditable="true"
                      cssLabelStyle="float:left;width:30%;"
                      cssCtrlStyle="float:left;width:70%;">
                <s:select list="nrpStatuses" listKey="code" listValue="description"
                          headerKey="" headerValue="[ALL]"
                          name="searchCriteria.nrpStatus"
                          theme="simple" cssStyle="width:150px;"/>
            </n:field2>
        </div>
    </div>
    <div style="float:left; width:25%">
        <div>
            <n:field2 label="Holder Type" labelJustify="right" tooltipGapHidden="true"
                      fieldEditable="true"
                      cssLabelStyle="float:left;width:30%;"
                      cssCtrlStyle="float:left;width:70%;">
                <s:select list="holderTypes" listKey="code" listValue="description"
                          headerKey="" headerValue="[ALL]"
                          name="searchCriteria.holderType"
                          theme="simple" cssStyle="width:150px;"/>
            </n:field2>
        </div>
    </div>
</div>
<%--
<div style="padding: 0 0 5px 0; height:26px;">
    <div style="float:left; width:33%">
        <div>
            <n:field2 label="Locked" labelJustify="right" tooltipGapHidden="true"
                         fieldEditable="true"
                         cssLabelStyle="float:left;width:20%;" cssCtrlStyle="float:left;width:65%;">
                <s:select list="#{'true':'Locked', 'false':'Unlocked'}" name="searchCriteria.lockedStatusString"
                             theme="simple" cssStyle="width:150px;"
                             headerKey="" headerValue="[ALL]"/>
            </n:field2>
        </div>
    </div>
    <div style="float:left; width:33%">
		<div>
			<n:field2 label="Domain Status" labelJustify="right" tooltipGapHidden="true"
                      fieldEditable="false"
                      cssLabelStyle="float:left;width:20%;"
                      cssCtrlStyle="float:left;width:65%;">
				<s:select list="domainStatuses" headerKey="" headerValue="[All]"
							 name="searchCriteria.domainStatus" listValue="statusName"
							 theme="simple" cssStyle="width: 150px;"/>
			</n:field2>
		</div>
	</div>
</div>
--%>
<div style="padding:5px 0 0 0; height:26px;">
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="Renew Date" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
						 cssCtrlStyle="float:left;width:60%;">
				<n:datefield2 field="searchCriteria.renewFrom"/>
			</n:field2>
		</div>
	</div>
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssLabelStyle="float:left;width:20%;" cssCtrlStyle="float:left;width:42%;">
				<n:datefield2 field="searchCriteria.renewTo"/>
			</n:field2>
		</div>
	</div>
</div>

<div style="height:26px;">
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="Registration Date" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
						 cssCtrlStyle="float:left;width:60%;">
				<n:datefield2 field="searchCriteria.registrationFrom"/>
			</n:field2>
		</div>
	</div>
	<div style="float:left; width:50%">
		<div>
			<n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssLabelStyle="float:left;width:20%;" cssCtrlStyle="float:left;width:42%;">
				<n:datefield2 field="searchCriteria.registrationTo"/>
			</n:field2>
		</div>
	</div>
</div>

<div style="clear:both;">
	<n:refreshsearch/>
</div>
</n:group2>
</s:form>
<domain:domainlist />
</body>
</html>