<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib prefix="docs" tagdir="/WEB-INF/tags/documents" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/accounts" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Ticket View <s:if test="%{isHistory()}">(History)</s:if></title>

    <sx:head/>

    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <script type="text/javascript" src="js/tooltip.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#status-dialog').jqm({trigger: false})
                    .jqmAddTrigger($('#open-status-dialog'))
                    .jqmAddClose($('#close-status-dialog'));
        })
    </script>

    <s:if test="%{isHistory()}">
        <link href="<s:url value='/styles/skin-iedr-history/main.css'/>" rel="stylesheet" type="text/css" media="all"/>
    </s:if>
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
<table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
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
    <s:if test="%{isHistory()}">
        <n:field2 label="Documentation">
            <docs:showdocs domainName="${ticketWrapper.domainName.value}" condition="${ticket.hasDocuments}"></docs:showdocs>
        </n:field2>
    </s:if>
    <s:else>
        <n:field2 label="Documentation">
            <docs:showdocs domainName="${ticketWrapper.domainName.value}" condition="${info.documents}"></docs:showdocs>
        </n:field2>
    </s:else>
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
									fieldEditable="false"
									reasonEditable="false"
			  />
	<ticket:tickettextfield2 label="Account Name"
									field="ticketWrapper.resellerAccount"
									fieldSuffix="name"
									fieldEditable="false"
									reasonEditable="false"
			  />
	<ticket:accountFlags ticketEdit="${ticketWrapper.resellerAccount.ticketEdit}" agreementSigned="${ticketWrapper.resellerAccount.agreementSigned}" />
	<s:set var="relatedDomains" value="info.relatedDomainNamesAsString"/>
	<ticket:tickettextfield2 label="Domain Holder"
									field="ticketWrapper.domainHolder"
									fieldEditable="false"
									tooltip="${relatedDomains}"
									reasonEditable="false"
			  />
	<s:if test="%{!isHistory()}">
		<n:field2 label="Previous Holder" fieldEditable="false">
			<c:out value="${info.previousHolder}"/>
		</n:field2>
	</s:if>
	<ticket:tickettextfield2 label="Class"
									field="ticketWrapper.domainHolderClass"
									fieldEditable="false"
									reasonEditable="false"
			  />
	<ticket:tickettextfield2 label="Category"
									field="ticketWrapper.domainHolderCategory"
									fieldEditable="false"
									reasonEditable="false"
			  />
	<n:field2 label="Clik Paid" fieldEditable="false">
		<s:checkbox name="ticket.clikPaid" label="Clik Paid"
						disabled="true"
						theme="simple"/>
	</n:field2>

</n:group2>

<n:group2 titleText="Contacts" cssIcon="group-icon-contact">
    <s:set var="admin1Info"  value="makeContactInfo(ticket.operation.adminContactsField[0].newValue)"/>
    <s:set var="admin2Info"  value="makeContactInfo(ticket.operation.adminContactsField[1].newValue)"/>
    <s:set var="techInfo"    value="makeContactInfo(ticket.operation.techContactsField[0].newValue)"/>
    <s:set var="billingInfo" value="makeContactInfo(ticket.operation.billingContactsField[0].newValue)"/>    
    <ticket:tickettextfield2 label="Admin Contact"
									 field="ticketWrapper.adminContact1" fieldSuffix="nicHandle"
									 tooltip="${admin1Info}"
									 fieldEditable="false"
									 reasonEditable="false">
		<s:if test="%{isHistory()}">
			<n:showcontact actionName="ticketview-history" nicHandle="${ticket.operation.adminContactsField[0].newValue.nicHandle}" />
		</s:if>
		<s:else>
			<n:showcontact actionName="ticketview-execute" nicHandle="${ticket.operation.adminContactsField[0].newValue.nicHandle}" />
		</s:else>
	</ticket:tickettextfield2>
	<ticket:tickettextfield2 label="Admin Contact"
									 field="ticketWrapper.adminContact2" fieldSuffix="nicHandle"
									 tooltip="${admin2Info}"
									 fieldEditable="false"
									 reasonEditable="false">
		<s:if test="%{isHistory()}">
			<n:showcontact actionName="ticketview-history" nicHandle="${ticket.operation.adminContactsField[1].newValue.nicHandle}" />
		</s:if>
		<s:else>
			<n:showcontact actionName="ticketview-execute" nicHandle="${ticket.operation.adminContactsField[1].newValue.nicHandle}" />
		</s:else>
	</ticket:tickettextfield2>
	<ticket:tickettextfield2 label="Tech Contact"
									 field="ticketWrapper.techContact" fieldSuffix="nicHandle"
									 tooltip="${techInfo}"
									 fieldEditable="false"
									 reasonEditable="false">
		<s:if test="%{isHistory()}">
			<n:showcontact actionName="ticketview-history" nicHandle="${ticket.operation.techContactsField[0].newValue.nicHandle}" />
		</s:if>
		<s:else>
			<n:showcontact actionName="ticketview-execute" nicHandle="${ticket.operation.techContactsField[0].newValue.nicHandle}" />
		</s:else>
	</ticket:tickettextfield2>
	<ticket:tickettextfield2 label="Billing Contact"
									 field="ticketWrapper.billingContact" fieldSuffix="nicHandle"
									 tooltip="${billingInfo}"
									 fieldEditable="false"
									 reasonEditable="false">
		<s:if test="%{isHistory()}">
			<n:showcontact actionName="ticketview-history" nicHandle="${ticket.operation.billingContactsField[0].newValue.nicHandle}" />
		</s:if>
		<s:else>
			<n:showcontact actionName="ticketview-execute" nicHandle="${ticket.operation.billingContactsField[0].newValue.nicHandle}" />
		</s:else>
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
            frDisabled="true"
            nameFailureReasons="${nameFrs}"
            ipFailureReasons="${ipFrs}"/>
        <s:if test="%{!isHistory()}">
            <s:submit value="DNS Check" method="dnsCheck" theme="simple"/>
        </s:if>
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
        <s:if test="isEmpty(ticket.hostmastersRemark)">
            (none)
        </s:if>
        <s:else>
        	<div style="white-space:pre-wrap;"><c:out value="${ticket.hostmastersRemark}"/></div>
        </s:else>
    </n:group2>
</td>
</tr>
</table>
<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td colspan="2" align="center">
            <hr class="buttons"/>
            <center>
                <s:if test="%{isHistory() && hasCurrent()}">
						 <s:if test="%{hasPermission('ticketview.current.button')}">
                             <%--fix for bug #1315-->
                             <%--<s:submit value="Current Ticket" method="current" theme="simple"/>--%>
                             <s:submit value="Current Ticket" action="%{getMethodForCurrentButton()}" theme="simple"/>
                         </s:if>
                </s:if>
                <s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
                <s:if test="%{!isHistory()}">
                    <s:submit value="Tech check" method="techCheck" theme="simple"/>
                </s:if>
            </center>
        </td>
    </tr>

    <tr>
        <td colspan="2">
            <n:group2 titleText="History" cssIcon="group-icon-history">
                <s:set var="ticketHist" value="ticketHistory"/>
                <s:set var="prevAction" value="previousAction"/>
                <s:set var="selected" value="historyIndex"/>
                <ticket:history ticketHistory="${ticketHist}" previousAction="${prevAction}" selected="${selected}"
                                historyAction="ticketview-change"/>
            </n:group2>
        </td>
    </tr>
</table>
</s:form>

</body>
</html>