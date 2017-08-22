<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib prefix="docs" tagdir="/WEB-INF/tags/documents" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Ticket Edit</title>

	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jqModal.js"></script>
	<script type="text/javascript" src="js/tooltip.js"></script>
	<sx:head/>
	<s:head/>
	<script type="text/javascript">
		function selectNicHandle(nicHandle, cntrl) {
			var root = $(cntrl).parents('.jqmWindow');
			var fieldId = '#' + root.children('.fieldId')[0].value;
			$(fieldId).attr('value', nicHandle);
			root.jqmHide();
		}
	</script>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error" displayActionErrors="false">
	<div style="max-height: 150px; overflow:auto">
		<s:actionerror escape="false"/>
	</div>
</n:error2>
<s:actionmessage />

<s:form name="ticket_form" theme="simple">
<s:hidden name="id"/>
<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td width="50%">

<n:group2 titleText="Ticket Status" cssIcon="group-icon-status">
	<n:field2 label="Id">${ticket.id}</n:field2>
	<n:field2 label="Type">${ticket.operation.type}</n:field2>
	<s:if test="ticketWrapper.numberOfYears!=null">
		<n:field2 label="Number of years">${ticketWrapper.numberOfYears}</n:field2>
	</s:if>
	<s:if test="ticketWrapper.domainPeriodInYears!=null">
		<n:field2 label="Registration period (years)">${ticketWrapper.domainPeriodInYears}</n:field2>
	</s:if>
	<s:if test="info.paymentMethod!=null">
		<n:field2 label="Payment method">${info.paymentMethod}</n:field2>
	</s:if>
	<s:if test="ticketWrapper.charityCode!=null">
		<n:field2 label="Charity code">${ticketWrapper.charityCode}</n:field2>
	</s:if>
	<n:field2 label="Documentation">
			<docs:showdocs domainName="${ticketWrapper.domainName.value}" condition="${info.documents}"></docs:showdocs>
		</n:field2>
	<n:field2 label="Created"><s:date name="ticket.creationDate"/></n:field2>
	<n:field2 label="Changed"><s:date name="ticket.changeDate"/></n:field2>
	<n:field2 label="Admin Status">${ticket.adminStatus.description}</n:field2>
	<n:field2 label="Tech Status">${ticket.techStatus.description}</n:field2>
	<n:field2 label="Financial Status">${ticket.financialStatus.description}</n:field2>
	<n:field2 label="Customer Status">${ticket.customerStatus.description}</n:field2>
</n:group2>

<n:group2 titleText="Domain" cssIcon="group-icon-domain">

	<ticket:tickettextfield2 label="Domain Name"
	field="ticketWrapper.domainName"
	reasonEditable="true"
	/>

	<ticket:tickettextfield2 label="Account Name"
									field="ticketWrapper.resellerAccount" fieldSuffix="name"
									reasonEditable="true"
			  />
	<ticket:accountFlags ticketEdit="${ticketWrapper.resellerAccount.ticketEdit}" agreementSigned="${ticketWrapper.resellerAccount.agreementSigned}" />			  

	<s:set var="relatedDomains" value="info.relatedDomainNamesAsString"/>
	<ticket:tickettextfield2 label="Domain Holder"
									field="ticketWrapper.domainHolder" fieldSuffix="value"
									tooltip="${relatedDomains}"
									fieldEditable="true"
									reasonEditable="true"
			  />


	<n:field2 label="Previous Holder">
		<c:out value="${info.previousHolder}"/>
	</n:field2>

	<div class="ctrl-container force-height-double">
		<div class="ctrl-label">
			<div class="ctrl-label-inner" style="padding-left:8px">Class</div>
			<div class="ctrl-tooltip">&nbsp;</div>
			<div class="ctrl-label-inner2" style="padding-left:8px">Category</div>
		</div>
		<div class="ctrl-field">
			<div class="ctrl-field-inner">
				<s:doubleselect
						  id="list1"
						  name="ticketWrapper.domainHolderClassId"
						  list="ticketClasses"
						  listKey="id"
						  listValue="name"
						  label="Class"
						  cssClass="%{ticketWrapper.domainHolderClass.modification?'modification':''}"
						  doubleCssClass="%{ticketWrapper.domainHolderClass.modification?'modification':''}"
						  doubleCssStyle="margin-top:5px"
						  doubleId="list2"
						  doubleName="ticketWrapper.domainHolderCategoryId"
						  doubleList="categories"
						  doubleListKey="id"
						  doubleListValue="name"
						  disabled="false"
						  theme="simple"/>
			</div>
			<div class="ctrl-failure-reason">
				<div class="ctrl-failure-reason-double">
					<s:select list="ticketWrapper.domainHolderClass.failureReasonList" headerKey="0" headerValue="(none)"
								 listKey="id" listValue="description"
								 value="ticketWrapper.domainHolderClass.failureReasonId"
								 name="ticketWrapper.domainHolderClass.failureReasonId" theme="simple"/>
				</div>
				<div class="ctrl-failure-reason-double">
					<s:select list="ticketWrapper.domainHolderCategory.failureReasonList" headerKey="0" headerValue="(none)"
								 listKey="id" listValue="description"
								 name="ticketWrapper.domainHolderCategory.failureReasonId"
								 value="ticketWrapper.domainHolderCategory.failureReasonId" theme="simple"
								 cssStyle="margin-top:5px"/>
				</div>
			</div>
		</div>
	</div>
    <n:fielderror fields="ticketWrapper.domainHolderCategoryId" colspan="1"/>
    <n:field2 label="Clik Paid">
		<s:checkbox name="ticket.clikPaid" label="Clik Paid" disabled="true" theme="simple"/>
	</n:field2>
</n:group2>
<n:group2 titleText="Contacts" cssIcon="group-icon-contact">

<s:set var="admin1Info"  value="makeContactInfo(ticketWrapper.adminContact1.newValue)"/>
<s:set var="admin2Info"  value="makeContactInfo(ticketWrapper.adminContact2.newValue)"/>
<s:set var="techInfo"  value="makeContactInfo(ticketWrapper.techContact.newValue)"/>
<s:set var="billingInfo"  value="makeContactInfo(ticketWrapper.billingContact.newValue)"/>
<ticket:tickettextfield2 label="Admin Contact"
								 field="ticketWrapper.adminContact1" fieldSuffix="nicHandle"
								 fieldEditable="true"
								 tooltip="${admin1Info}"
								 reasonEditable="true"
		  >
	<a href="#" id="openSearchDialogadminContact1">
		<img src="images/skin-default/action-icons/window.png" title="Select"/>
	</a>
</ticket:tickettextfield2>

<ticket:tickettextfield2 label="Admin Contact"
								 field="ticketWrapper.adminContact2" fieldSuffix="nicHandle"
								 fieldEditable="true"
								 tooltip="${admin2Info}"
								 reasonEditable="true"
		  >
	<a href="#" id="openSearchDialogadminContact2">
		<img src="images/skin-default/action-icons/window.png" title="Select"/>
	</a>
</ticket:tickettextfield2>

<ticket:tickettextfield2 label="Tech Contact"
								 field="ticketWrapper.techContact" fieldSuffix="nicHandle"
								 fieldEditable="true"
								 tooltip="${techInfo}"
								 reasonEditable="true"
		  >
	<a href="#" id="openSearchDialogtechContact">
		<img src="images/skin-default/action-icons/window.png" title="Select"/>
	</a>
</ticket:tickettextfield2>

<ticket:tickettextfield2 label="Billing Contact"
								 field="ticketWrapper.billingContact" fieldSuffix="nicHandle"
								 fieldEditable="false"
								 tooltip="${billingInfo}"
								 reasonEditable="false"/>
</n:group2>
</td>
<td width="50%">
	<n:group2 titleText="DNS Name Servers" cssIcon="group-icon-dns">
		<s:set var="ns" value="ticketWrapper.currentNameserverWrappers"/>
		<s:set var="modTicket" value="modification"/>
		<s:set var="nameFrs" value="nameserverNameFailureReasons"/>
		<s:set var="ipFrs" value="nameserverIpFailureReasons"/>
		<ticket:nameservers
		    nameservers="${ns}"
		    modifyingTicket="${modTicket}"
		    inputDisabled="false"
		    frDisabled="false"
		    nameFailureReasons="${nameFrs}"
		    ipFailureReasons="${ipFrs}"/>
		<s:submit value="DNS Check" method="dnsCheck" theme="simple"/>
	</n:group2>
	<n:group2 titleText="Requester's Remark" cssIcon="group-icon-remarks">
		<s:if test="empty ticket.requestersRemark">
			(none)
		</s:if>
		<s:else>
			<div style="white-space:pre-wrap;"><c:out value="${ticket.requestersRemark}"/></div>
		</s:else>
	</n:group2>
	<n:group2 titleText="Hostmaster Remarks" cssIcon="group-icon-remarks">
		<ticket:hostmasterRemark />
		<n:fielderror fields="responseText" colspan="1"></n:fielderror>
	</n:group2>
</td>
</tr>
<tr>
	<td colspan="2" align="center">
		<hr class="buttons"/>
		<center>
			<s:submit value="Save" method="forceSave" theme="simple"/>
			<s:submit value="Cancel" method="cancel" theme="simple"/>
		</center>
	</td>
</tr>
<tr>
	<td colspan="2">
		<n:group2 titleText="History" cssIcon="group-icon-history">
			<s:set var="ticketHist" value="ticketHistory"/>
			<ticket:history ticketHistory="${ticketHist}" previousAction="ticketedit-input" selected="-1"/>
		</n:group2>
	</td>
</tr>
</table>
</s:form>
<ticket:contacts-search fieldId="adminContact1" accountId="${ticketWrapper.resellerAccount.accountId}"/>
<ticket:contacts-search fieldId="adminContact2" accountId="${ticketWrapper.resellerAccount.accountId}"/>
<ticket:contacts-search fieldId="techContact" accountId="${ticketWrapper.resellerAccount.accountId}"/>
</body>
</html>