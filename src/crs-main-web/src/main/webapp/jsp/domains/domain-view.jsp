<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib prefix="domain" tagdir="/WEB-INF/tags/domains" %>
<%@ taglib prefix="docs" tagdir="/WEB-INF/tags/documents" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Domain View
        <s:if test="%{isHistory()}">(History)</s:if>
    </title>

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
<s:actionmessage/>

<s:form name="domain_form" theme="simple" method="POST">
<s:hidden name="id"/>
<s:hidden name="domainName" value="%{#attr.domainWrapper.name}"/>
<%--<s:hidden name="previousAction" value="%{#attr.previousAction}"/>--%>
<s:hidden name="page" value="1"/>
<table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td width="50%">
    <n:group2 titleText="Domain" cssIcon="group-icon-domain">
        <n:field label="Domain Name">
            <div class="showValue">${domainWrapper.name}</div>
        </n:field>
        <s:set var="relatedDomains" value="domainWrapper.relatedDomainNamesAsString"/>
        <n:field label="Domain Holder" tooltip="${relatedDomains}" tooltipId="relatedDomainsTip">
            <div class="showValue">${domainWrapper.domainHolder}</div>
        </n:field>
        <n:field label="Account">
            <div class="showValue">${domainWrapper.resellerAccount.name}</div>
        </n:field>
        <n:field label="Class">
            <div class="showValue">${domainWrapper.holderClass}</div>
        </n:field>
        <n:field label="Category">
            <div class="showValue">${domainWrapper.holderCategory}</div>
        </n:field>
        <n:field label="Clik Paid">
            <s:checkbox name="domainWrapper.clikPaid" label="Clik Paid" disabled="true" theme="simple"/></n:field>
    </n:group2>
    <n:group2 titleText="Domain Dates">
        <n:field label="Changed">
            <div class="showValue"><s:date
                    name="domainWrapper.changeDate"/></div>
        </n:field>
        <n:field label="Reg Date">
            <div class="showValue"><s:date format="dd/MM/yyyy" name="domainWrapper.registrationDate"/></div>
        </n:field>
        <n:field label="Renew Date">
            <div class="showValue"><s:date format="dd/MM/yyyy" name="domainWrapper.renewDate"/></div>
        </n:field>
        <s:if test="domainWrapper.nrp && domainWrapper.suspensionDate != null">
       		<n:field label="Suspension Date">
        	    <div class="showValue"><s:date format="dd/MM/yyyy" name="domainWrapper.suspensionDate"/></div>
         	</n:field>
        </s:if>
        <s:if test="domainWrapper.nrp && domainWrapper.deletionDate != null">
        	<n:field label="Deletion Date">
            	<div class="showValue"><s:date format="dd/MM/yyyy" name="domainWrapper.deletionDate"/></div>
        	</n:field>
        </s:if>

    </n:group2>
    <n:group2 titleText="Domain Contacts" cssIcon="group-icon-contact">
        <s:set var="admin1Info"  value="makeContactInfo(domainWrapper.adminContacts[0])"/>
        <s:set var="admin2Info"  value="makeContactInfo(domainWrapper.adminContacts[1])"/>
        <s:set var="techInfo"  value="makeContactInfo(domainWrapper.techContacts[0])"/>
        <s:set var="billingInfo"  value="makeContactInfo(domainWrapper.billingContacts[0])"/>
        <n:field label="Admin Contact 1"
                 tooltip="${admin1Info}"
                 tooltipId="domainWrapper.adminContacts[0]">
            <div class="showValue" id="testId1">
                    ${domainWrapper.adminContacts[0].nicHandle}
            </div>
            <s:if test="%{isHistory()}">
                <n:showcontact actionName="historical-domain-view"
                               nicHandle="${domainWrapper.adminContacts[0].nicHandle}"/>
            </s:if>
            <s:else>
                <n:showcontact actionName="domainview" nicHandle="${domainWrapper.adminContacts[0].nicHandle}"/>
            </s:else>
        </n:field>
        <n:field label="Admin Contact 2"
                 tooltip="${admin2Info}"
                 tooltipId="domainWrapper.adminContacts[1]">
            <div class="showValue" id="testId2">
                    ${domainWrapper.adminContacts[1].nicHandle}
            </div>
            <s:if test="%{isHistory()}">
                <n:showcontact actionName="historical-domain-view"
                               nicHandle="${domainWrapper.adminContacts[1].nicHandle}"/>
            </s:if>
            <s:else>
                <n:showcontact actionName="domainview" nicHandle="${domainWrapper.adminContacts[1].nicHandle}"/>
            </s:else>
        </n:field>
        <n:field label="Tech Contact"
                 tooltip="${techInfo}"
                 tooltipId="domainWrapper.techContacts[0]">
            <div class="showValue">
                    ${domainWrapper.techContacts[0].nicHandle}
            </div>
            <s:if test="%{isHistory()}">
                <n:showcontact actionName="historical-domain-view"
                               nicHandle="${domainWrapper.techContacts[0].nicHandle}"/>
            </s:if>
            <s:else>
                <n:showcontact actionName="domainview" nicHandle="${domainWrapper.techContacts[0].nicHandle}"/>
            </s:else>
        </n:field>
        <n:field label="Billing Contact"
                 tooltip="${billingInfo}"
                 tooltipId="domainWrapper.billingContacts[0]">
            <div class="showValue">
                    ${domainWrapper.billingContacts[0].nicHandle}
            </div>
            <s:if test="%{isHistory()}">
                <n:showcontact actionName="historical-domain-view"
                               nicHandle="${domainWrapper.billingContacts[0].nicHandle}"/>
            </s:if>
            <s:else>
                <n:showcontact actionName="domainview" nicHandle="${domainWrapper.billingContacts[0].nicHandle}"/>
            </s:else>
        </n:field>
    </n:group2>

</td>
<td width="50%">
        <%--<n:group2 titleText="Domain Dates" cssIcon="group-icon-status">--%>

    <n:group2 titleText="Domain Status" cssIcon="group-icon-status">
        <s:if test="%{isHistory() == false}">
            <n:field label="Documentation">
                <docs:showdocs domainName="${domainWrapper.name}"
                               condition="${domainWrapper.documents}"></docs:showdocs>
            </n:field>
            <n:field label="Tickets">
                <s:checkbox name="domainWrapper.tickets" label="Tickets" disabled="true" theme="simple"/>
            </n:field>
        </s:if>
        <s:if test="%{domainWrapper.dsmState.valid}">
        	<domain:dsmstatus dsmState="${domainWrapper.dsmState}" />
        </s:if>
        <s:else>
        	<n:field label="Invalid DSM State">${domainWrapper.dsmState.id}</n:field>
        </s:else>
    </n:group2>
    <n:group2 titleText="DNS Name Servers" cssIcon="group-icon-dns">
        <domain:nameservers inputDisabled="true" wrapper="domainWrapper.dnsWrapper"/>
    </n:group2>
    <n:group2 titleText="Hostmaster Remarks" cssIcon="group-icon-remarks">
        <div style="white-space:pre-wrap;"><s:text name="domainWrapper.remark"/></div>
    </n:group2>
</td>
</tr>
</table>
<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td colspan="2" align="center">
            <hr class="buttons"/>
            <center>
                <s:if test="%{isHistory()}">
                    <s:if test="%{hasCurrent()}">
                        <s:hidden name="previousAction"/>
                        <s:if test="%{hasPermission('domain.current.button')}">
                            <s:submit value="Current Domain" action="domainview" theme="simple"/>
                        </s:if>
                    </s:if>
                    <s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
                </s:if>
                <s:else>
                    <s:if test="%{hasPermission('domain.alterstatus.button')}">
                        <input type="button" value="Alter Status" id="openAlterStatusDialog"/>
                    </s:if>
                    <s:if test="%{hasPermission('domain.changeHolderType.button')}">
                        <input type="button" value="Change Holder Type" id="openChangeHolderTypeDialog"/>
                    </s:if>
                    <s:if test="%{hasPermission('domain.changeWipo.button')}">
                        <input type="button" value="Change WIPO" id="openChangeWipoDialog"/>
                    </s:if>
                    <s:if test="%{hasPermission('domain.edit.button') && !isWipo()}">
                        <s:submit value="Edit" action="domainedit" method="input" theme="simple"/>
                    </s:if>
                    <s:submit value="DNS Check" action="domainview-dnsCheck" theme="simple"/>
                    <s:if test="%{hasPermission('domain.sendAuthCode.button') && !isWipo()}">
                        <s:submit value="Send Authcode" action="domainview-sendAuthCode" theme="simple"/>
                    </s:if>
                    <s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
                </s:else>
            </center>
        </td>
    </tr>

    <tr>
        <td colspan="2">
            <domain:hdomainlist firstSearch="false" sortable="false"/>
        </td>
    </tr>
</table>
</s:form>

<s:if test="%{!isHistory()}">
	<s:if test="%{isGIBOWaitingForApproval()}">
    	<domain:alterstatus domain="${domainWrapper}" domainStatuses="${giboStatuses}" formAction="domainalterstatus-alterStatusForAutoCreatedDomain.action"/>
    </s:if>
    <s:else>
    	<domain:alterstatus domain="${domainWrapper}" domainStatuses="${domainStatuses}" formAction="domainalterstatus-alterStatus.action"/>
    </s:else>
    <domain:changeholdertype formAction="domainChangeHolderType-updateHolderType.action" holderTypes="${holderTypes}" domain="${domainWrapper}" />
    <s:if test="%{isWipo()}" >
        <domain:changewipo formAction="domainWipo-exitWipo.action" domain="${domainWrapper}" wipo="true"/>
    </s:if>
    <s:else>
        <domain:changewipo formAction="domainWipo-enterWipo.action" domain="${domainWrapper}" wipo="false"/>
    </s:else>
</s:if>

</body>
</html>