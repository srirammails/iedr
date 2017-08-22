<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/accounts" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Account View
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
<%--
        <s:if test="%{isHistory()}">
            $('#details').fadeTo("fast", 0.5);
        </s:if>
--%>
        })
    </script>

	<s:if test="%{isHistory()}">
		 <link href="<s:url value='/styles/skin-iedr-history/main.css'/>" rel="stylesheet" type="text/css" media="all"/>
	</s:if>
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
                    <n:field label="Name"><div class="showValue"><c:out value="${account.name}"/></div></n:field>
                    <n:field label="Web Address"><div class="showValue"><c:out value="${account.webAddress}"/></div></n:field>
                    <account:accountFlags ticketEdit="${account.ticketEdit}" agreementSigned="${account.agreementSigned}" />
                </n:group2>
                <n:group2 titleText="Billing Contact" cssIcon="group-icon-billing-contact">
                    <s:set var="billingInfo"  value="makeContactInfo(account.billingContact)"/>
                    <n:field label="Nic Handle" tooltip="${billingInfo}" tooltipId="account.billingContact">
                        <div class="showValue">${account.billingContact.nicHandle}</div>
                        <n:showcontact actionName="account-view-view" nicHandle="${account.billingContact.nicHandle}"/>
                    </n:field>
                    <n:field label="E-mail"><div class="showValue">${account.billingContact.email}</div></n:field>
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
                    <n:field label="Remark"><div class="showValue" style="white-space:pre-wrap;"><c:out value="${account.remark}"/></div></n:field>
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

								<s:if test="%{hasPermission('account.current.button')}">
									<s:submit value="Current Account" action="account-view-view" theme="simple"/>
								</s:if>

							</s:if>
							<s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
						</s:if>
						<s:else>
							<s:if test="%{hasPermission('account.alterstatus.button')}">
								<input type="button" value="Alter Status" id="openAlterStatusDialog"/>
							</s:if>
							<s:if test="%{hasPermission('account.edit.button')}">
								<s:submit value="Edit" action="account-edit-input" theme="simple"/>
							</s:if>
                            <s:if test="%{hasPermission('account.deposit.button')}">
                                <s:submit value="Deposit" action="deposit-view-input" theme="simple"/>
                            </s:if>
							<s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
						</s:else>
					</center>
				</td>
        </tr>

        <tr>
            <td colspan="2">
                <account:haccountlist/>
            </td>
        </tr>
    </table>

</s:form>

<account:alterstatus account="${account}" statuses="${statuses}"/>

</body>
</html>