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
    <title>Domain Edit</title>

    <sx:head/>

    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <script type="text/javascript" src="js/tooltip.js"></script>
    <script type="text/javascript">
        function selectNicHandle(nicHandle, cntrl) {
            var root = $(cntrl).parents('.jqmWindow');
            var fieldId = '#' + root.children('.fieldId')[0].value;
            $(fieldId).attr('value', nicHandle);
            root.jqmHide();
        }
    </script>
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

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:form name="domain_form" theme="simple">
<s:hidden name="id"/>
<s:hidden name="domainName" value="%{#attr.domainWrapper.name}"/>
<s:hidden name="previousAction" value="%{#attr.previousAction}"/>
<table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td width="50%">
<n:group2 titleText="Domain" cssIcon="group-icon-domain">

    <n:field label="Domain Name">
        <div class="showValue">${domainWrapper.name}</div>
    </n:field>
    <n:field label="Domain Holder" fielderror="domainWrapper.domainHolder">
        <input type="text" value="${domainWrapper.holder}" name="domainWrapper.domainHolder" />
    </n:field>
    <n:field label="Account">
        <div class="showValue">${domainWrapper.resellerAccount.name}</div>
    </n:field>

    <div class="ctrl-container" style="height:54px;">
        <div class="ctrl-label">
            <div class="ctrl-label-inner">Class</div>
            <div class="ctrl-tooltip">&nbsp;</div>
            <div class="ctrl-label-inner2">Category</div>
        </div>
        <div class="ctrl-field">
            <div class="ctrl-field-inner-no-FR-gap">
                <s:doubleselect
                        id="list1"
                        name="domainWrapper.holderClassId"
                        list="domainClasses"
                        listKey="id"
                        listValue="name"
                        label="Class"
                        cssClass="%{ticketWrapper.domainHolderClass.modification?'modification':''}"
                        doubleCssClass="%{ticketWrapper.domainHolderClass.modification?'modification':''}"
                        doubleCssStyle="margin-top:5px"
                        doubleId="list2"
                        doubleName="domainWrapper.holderCategoryId"
                        doubleList="categories"
                        doubleListKey="id"
                        doubleListValue="name"
                        disabled="false"
                        theme="simple"/>
            </div>
        </div>
    </div>
    <n:fielderror fields="domainWrapper.holderCategoryId" colspan="1"/>
    <n:field label="Clik Paid">
        <s:checkbox name="domainWrapper.clikPaid" label="Clik Paid" disabled="true" theme="simple"/>
    </n:field>
</n:group2>

<n:group2 titleText="Domain Dates" cssIcon="group-icon-status">
    <n:field label="Changed">
        <div class="showValue">
            <s:date name="domainWrapper.changeDate"/></div>
    </n:field>
    <n:field label="Reg Date">
        <div class="showValue">
            <s:date format="dd/MM/yyyy" name="domainWrapper.registrationDate"/></div>
    </n:field>
    <n:field label="Renew Date">
        <div class="showValue">
            <s:date format="dd/MM/yyyy" name="domainWrapper.renewDate"/></div>
    </n:field>
    <s:if test="domainWrapper.suspensionDate != null">
    	<n:field label="Suspension Date">
      	    <div class="showValue"><s:date format="dd/MM/yyyy"
                    name="domainWrapper.suspensionDate"/></div>
      	</n:field>
    </s:if>
    <s:if test="domainWrapper.deletionDate != null">
     	<n:field label="Deletion Date">
           	<div class="showValue"><s:date format="dd/MM/yyyy"
                   name="domainWrapper.deletionDate"/></div>
       	</n:field>
    </s:if>
</n:group2>

<n:group2 titleText="Domain Contacts" cssIcon="group-icon-contact">
    <s:set var="admin1Info"  value="makeContactInfo(domainWrapper.adminContacts[0])"/>
    <s:set var="admin2Info"  value="makeContactInfo(domainWrapper.adminContacts[1])"/>
    <s:set var="techInfo"  value="makeContactInfo(domainWrapper.techContacts[0])"/>
    <s:set var="billingInfo"  value="makeContactInfo(domainWrapper.billingContacts[0])"/>
    <n:field label="Admin Contact 1"
             fielderror="domainWrapper.adminContact1"
             tooltip="${admin1Info}"
             tooltipId="domainWrapper.adminContacts1">
        <input type="text" value="${domainWrapper.adminContacts[0].nicHandle}"
               name="domainWrapper.adminContact1" id="adminContact1"/>

        <div class="window-icon">
            <a href="#" id="openSearchDialogadminContact1">
                <img src="images/skin-default/action-icons/window.png" title="Select"/>
            </a>
        </div>
    </n:field>
    <n:field label="Admin Contact 2"
             fielderror="domainWrapper.adminContact2"
             tooltip="${admin2Info}"
             tooltipId="domainWrapper.adminContacts2">
        <input type="text" value="${domainWrapper.adminContacts[1].nicHandle}"
               name="domainWrapper.adminContact2" id="adminContact2"/>

        <div class="window-icon">
            <a href="#" id="openSearchDialogadminContact2">
                <img src="images/skin-default/action-icons/window.png" title="Select"/>
            </a>
        </div>
    </n:field>

    <n:field label="Tech Contact"
             fielderror="domainWrapper.techContact"
             tooltip="${techInfo}"
             tooltipId="domainWrapper.techContact">
        <input type="text" value="${domainWrapper.techContacts[0].nicHandle}"
               name="domainWrapper.techContact" id="techContact"/>

        <div class="window-icon">
            <a href="#" id="openSearchDialogtechContact">
                <img src="images/skin-default/action-icons/window.png" title="Select"/>
            </a>
        </div>
    </n:field>
    <n:field
            label="Billing Contact"
            tooltip="${billingInfo}"
            tooltipId="domainWrapper.billingContact">

        <div class="showValue">${domainWrapper.billingContacts[0].nicHandle}</div>
    </n:field>
</n:group2>

</td>
<td width="50%">
    <n:group2 titleText="Domain Status" cssIcon="group-icon-status">
        <n:field label="Documentation">
            <docs:showdocs domainName="${domainWrapper.name}" condition="${domainWrapper.documents}"></docs:showdocs>
        </n:field>
        <n:field label="Tickets">
            <s:checkbox name="domainWrapper.tickets" label="Tickets" disabled="true" theme="simple"/>
        </n:field>        
        <domain:dsmstatus dsmState="${domainWrapper.dsmState}" />
    </n:group2>
    <n:group2 titleText="DNS Name Servers" cssIcon="group-icon-dns">
        <domain:nameservers inputDisabled="false" wrapper="domainWrapper.dnsWrapper"/>
    </n:group2>
    <n:group2 titleText="Hostmaster Remarks" cssIcon="group-icon-remarks">
        <s:textarea name="domainWrapper.newRemark" theme="simple"></s:textarea>
        <n:fielderror fields="domainWrapper.remark, domainWrapper.newRemark" colspan="1"/>
    </n:group2>
</td>
</tr>
</table>
<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td colspan="2" align="center">
            <hr class="buttons"/>
            <center>
                <s:submit value="Save" method="save" theme="simple" cssClass="xSave"/>
            </center>
        </td>
    </tr>
</table>
</s:form>
<s:form>
    <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="2" align="center">
                <center>
                    <s:submit value="Cancel" action="domainview" theme="simple" cssClass="xCancel"/>
                </center>
            </td>
        </tr>
    </table>
</s:form>
<ticket:contacts-search fieldId="adminContact1" accountId="${domainWrapper.accountNo}"/>
<ticket:contacts-search fieldId="adminContact2" accountId="${domainWrapper.accountNo}"/>
<ticket:contacts-search fieldId="techContact" accountId="${domainWrapper.accountNo}"/>

</body>
</html>