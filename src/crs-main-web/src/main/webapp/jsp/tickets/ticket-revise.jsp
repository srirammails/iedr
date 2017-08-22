<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
			pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="docs" tagdir="/WEB-INF/tags/documents" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Ticket Revise</title>

	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jqModal.js"></script>
	<script type="text/javascript" src="js/tooltip.js"></script>
	<sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error" displayActionErrors="false">
    <div style="max-height: 150px; overflow:auto">
        <s:actionerror escape="false"/>
    </div>
</n:error2>

<s:actionmessage />

<s:form name="ticket_form" theme="simple">
<n:error2 cssIcon="group-icon-error" titleText="Warning" displayActionErrors="false"
          customErrorCondition="${forceEdit}">
    <p>This ticket has been restricted from editing as the Registrar has not signed Exhibit 3 of the Registrar Agreement.</p>

    <p>Are you sure you want to edit this ticket? <s:submit method="edit" theme="simple" value="YES"/> <s:submit method="cancelEdit" theme="simple" value="NO"/></p>
</n:error2>
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
        <s:if test="%{!isHistory()}">
            <n:field2 label="Expiry date"><s:date format="dd/MM/yyyy" name="ticketDeletionDate"/></n:field2>
        </s:if>        
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
											field="ticketWrapper.resellerAccount"
											fieldSuffix="name"
											reasonEditable="true"
					  />
			<ticket:accountFlags ticketEdit="${ticketWrapper.resellerAccount.ticketEdit}" agreementSigned="${ticketWrapper.resellerAccount.agreementSigned}" />					  
			<s:set var="relatedDomains" value="info.relatedDomainNamesAsString"/>
			<ticket:tickettextfield2 label="Domain Holder"
											field="ticketWrapper.domainHolder"
											tooltip="${relatedDomains}"
											reasonEditable="true"
					  />
			<n:field2 label="Previous Holder">
				<c:out value="${info.previousHolder}"/>
			</n:field2>
			<ticket:tickettextfield2 label="Class"
											field="ticketWrapper.domainHolderClass"
											reasonEditable="true"
					  />
			<ticket:tickettextfield2 label="Category"
											field="ticketWrapper.domainHolderCategory"
											reasonEditable="true"
					  />
			<n:field2 label="Clik Paid">
				<s:checkbox name="ticket.clikPaid" label="Clik Paid" disabled="true"
								theme="simple"/>
				&nbsp;
			</n:field2>
		</n:group2>
		<n:group2 titleText="Contacts" cssIcon="group-icon-contact">
            <s:set var="admin1Info"  value="makeContactInfo(ticket.operation.adminContactsField[0].newValue)"/>
            <s:set var="admin2Info"  value="makeContactInfo(ticket.operation.adminContactsField[1].newValue)"/>
            <s:set var="techInfo"    value="makeContactInfo(ticket.operation.techContactsField[0].newValue)"/>
            <s:set var="billingInfo" value="makeContactInfo(ticket.operation.billingContactsField[0].newValue)"/>
            <ticket:tickettextfield2 label="Admin Contact"
											field="ticketWrapper.adminContact1" fieldSuffix="nicHandle"
											fieldEditable="false"
											reasonEditable="true"
											tooltip="${admin1Info}">
				<n:showcontact actionName="ticketrevise-execute" nicHandle="${ticket.operation.adminContactsField[0].newValue.nicHandle}" />
			</ticket:tickettextfield2>
			<ticket:tickettextfield2 label="Admin Contact"
											field="ticketWrapper.adminContact2" fieldSuffix="nicHandle"
											fieldEditable="false"
											reasonEditable="true"
											tooltip="${admin2Info}">
				<n:showcontact actionName="ticketrevise-execute" nicHandle="${ticket.operation.adminContactsField[1].newValue.nicHandle}" />
			</ticket:tickettextfield2>
			<ticket:tickettextfield2 label="Tech Contact"
											field="ticketWrapper.techContact" fieldSuffix="nicHandle"
											fieldEditable="false"
											reasonEditable="true"
											tooltip="${techInfo}">
				<n:showcontact actionName="ticketrevise-execute" nicHandle="${ticket.operation.techContactsField[0].newValue.nicHandle} " />
			</ticket:tickettextfield2>
			<ticket:tickettextfield2 label="Billing Contact"
											field="ticketWrapper.billingContact" fieldSuffix="nicHandle"
											fieldEditable="false"
											reasonEditable="true"
											tooltip="${billingInfo}">
				<n:showcontact actionName="ticketrevise-execute" nicHandle="${ticket.operation.billingContactsField[0].newValue.nicHandle}" />
			</ticket:tickettextfield2>
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
                inputDisabled="true"
                frDisabled="false"
                nameFailureReasons="${nameFrs}"
                ipFailureReasons="${ipFrs}"/>
			<s:submit value="DNS Check" method="dnsCheck" theme="simple"/>
		</n:group2>
		<n:group2 titleText="Requester's Remark" cssIcon="group-icon-remarks">
			<s:if test="isEmpty(ticket.requestersRemark)">
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
            <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
                <tr>
                    <td>
                        <center>
                        <s:if test="%{hasPermission('ticketrevise.accept.button')}">
                        <s:submit value="Accept" method="accept" theme="simple"/>
                        </s:if>
                        </center>
                    </td>
                    <td width="76%">
                        <center>
                        <s:if test="%{hasPermission('ticketrevise.save.button')}">
                            <s:submit value="Save" method="save" theme="simple"/>
                        </s:if>
                        <s:if test="%{hasPermission('ticketrevise.reject.button')}">
                            <img src="images/skin-default/web-parts/spacer.png" alt="" width="5%" height="1"/>
                            <input type="button" value="Reject" id="openRejectDialog"/>
                        </s:if>
                        <s:if test="%{ticketWrapper.resellerAccount.ticketEdit && hasPermission('ticketrevise.edit.button')}">
                            <img src="images/skin-default/web-parts/spacer.png" alt="" width="5%" height="1"/>                             
                            <s:submit value="Edit" method="edit" theme="simple"/>
                        </s:if>
                        <s:if test="%{!ticketWrapper.resellerAccount.ticketEdit && hasPermission('ticketrevise.edit.button')}">
                                <img src="images/skin-default/web-parts/spacer.png"alt=""width="5%"height="1"/>
                                <s:checkbox name="forceEdit" theme="simple" onchange="submit();">
                                <label>Bypass edit flags</label>
                                </s:checkbox>
                        </s:if>
                        </center>
                    </td>
                    <td>
                        <center>
                        <s:submit value="Cancel" method="search" theme="simple"/>
                        </center>
                    </td>
                </tr>
            </table>
	</td>
</tr>
<tr>
	<td colspan="2">
		<n:group2 titleText="History" cssIcon="group-icon-history">
			<s:set var="ticketHist" value="ticketHistory"/>
			<ticket:history ticketHistory="${ticketHist}" previousAction="ticketrevise-input" selected="-1"/>
		</n:group2>
	</td>
</tr>
</table>
<s:set var="adminStatusList" value="rejectionStatuses"/>
<ticket:reject ticket="${ticket}" adminStatuses="${adminStatusList}"/>
</s:form>

</body>
</html>