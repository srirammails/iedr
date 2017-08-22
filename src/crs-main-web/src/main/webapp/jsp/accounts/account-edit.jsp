<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/accounts" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib prefix="nask" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Account Edit</title>

    <sx:head/>

    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <script type="text/javascript" src="js/tooltip.js"></script>
    <script type="text/javascript">
        function selectNicHandle(nicHandle, email, cntrl) {
            var root = $(cntrl).parents('.jqmWindow');
            var fieldId1 = '#' + root.children('.fieldId')[0].value;
            var fieldId2 = '#' + root.children('.fieldEmail')[0].value;
            $(fieldId1).attr('value', nicHandle);
            $(fieldId2).attr('value', email);
            root.jqmHide();
        }

        function changeTicketEditFlag(value, ticketEditCheckboxId) {            
			el = document.getElementById(ticketEditCheckboxId);
            if (value.checked) {
                el.disabled = false;
            } else {
            	el.disabled = true;
            	el.checked = false;
            }
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#status-dialog').jqm({trigger: false})
                    .jqmAddTrigger($('#open-status-dialog'))
                    .jqmAddClose($('#close-status-dialog'));
<%--
        <s:if test="%{isHistory()}">
            $('#details').fadeTo("fast", 0.5);
        </s:if>
--%>
        })
    </script>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="account_form" theme="simple">
    <s:hidden name="id"/>
    <table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td width="50%">
                <n:group2 titleText="Account" cssIcon="group-icon-account">
                    <n:field label="Account No"><div class="showValue">${account.id}</div></n:field>
                    <n:field label="Name" fielderror="account.name"><input type="text" name="account.name" value="${account.name}"/></n:field>
                    <n:field label="Web Address" fielderror="account.webAddress"><input type="text" name="account.webAddress" value="${account.webAddress}"/></n:field>
                    <s:if test="%{hasPermission('account.edit.editFlags')}">                    
                    	<n:field label="Signed Agreement">
        					<s:checkbox name="account.agreementSigned" label="Signed Agreement" theme="simple" onchange="changeTicketEditFlag(this, 'ticketEditChkbx')"/>
    					</n:field>
    					<n:field label="Edit Ticket">
        					<s:checkbox id="ticketEditChkbx" name="account.ticketEdit" label="Edit Ticket" disabled="%{!account.agreementSigned}" theme="simple"/>
    					</n:field>
    				</s:if>
    				<s:else>
    					<account:accountFlags ticketEdit="${account.ticketEdit}" agreementSigned="${account.agreementSigned}" />
    				</s:else>
                </n:group2>
                <n:group2 titleText="Billing Contact" cssIcon="group-icon-billing-contact">
                    <s:set var="billingInfo"  value="makeContactInfo(account.billingContact)"/>
                    <n:field label="Nic Handle" tooltip="${billingInfo}" tooltipId="account.billingContact">
                        <div class="showValue">${account.billingContact.nicHandle}</div>
                    </n:field>
                    <n:field label="E-mail"><div class="showValue">${account.billingContact.email}</div></n:field>
                    <%--<n:field label="Nic Handle" fielderror="wrapper.newBillingContactId">--%>
                        <!--<input type="text" value="${wrapper.newBillingContactId}" name="wrapper.newBillingContactId" id="billingContact"/>-->
                        <!--<div class="window-icon">-->
                            <!--<a href="#" id="openSearchExtendedDialogbillingContact"><img src="images/skin-default/action-icons/window.png" title="Select"/></a>-->
                        <!--</div>-->
                    <%--</n:field>--%>
                    <%--<n:field label="E-mail"><input type="text" name="wrapper.newBillingContactEmail" value="${wrapper.newBillingContactEmail}" readonly="true"  id="contactEmail"/> </n:field>--%>
                </n:group2>
            </td>
            <td width="50%">
					<n:group2 titleText="Status" cssIcon="group-icon-status">
						 <n:field label="Changed"><div class="showValue"><s:date name="account.changeDate"/></div></n:field>
						 <n:field label="Status"><div class="showValue">${account.status}</div></n:field>
						 <n:field label="Status Changed"><div class="showValue"><s:date name="account.statusChangeDate" format="dd/MM/yyyy"/></div></n:field>
						 <n:field label="Creation Date"><div class="showValue"><s:date name="account.creationDate" format="dd/MM/yyyy"/></div></n:field>
					</n:group2>
                <n:group2 titleText="Hostmaster Remarks" cssIcon="group-icon-remarks">
                    <s:textarea name="hostmastersRemark" theme="simple"></s:textarea>
                    <n:fielderror fields="hostmastersRemark" colspan="1"/>
                </n:group2>
            </td>

        </tr>
    </table>
    <table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="2" align="center">
                <hr class="buttons"/>
                <center>
                    <s:submit value="Save" action="account-edit-save" theme="simple" cssClass="xSave"/>
                </center>
            </td>
        </tr>
    </table>
</s:form>
<s:form theme="simple">
	<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td colspan="2" align="center">
                <center>
                    <s:submit value="Cancel" action="account-view-view" theme="simple" cssClass="xCancel"/>
                </center>
            </td>
        </tr>
    </table>
</s:form>
<%--<ticket:contacts-search-extended fieldId="billingContact" fieldEmail="contactEmail"/>--%>

</body>
</html>