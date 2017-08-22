<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="nichandle" tagdir="/WEB-INF/tags/nichandles" %>
<%@ taglib prefix="domain" tagdir="/WEB-INF/tags/domains" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="account" tagdir="/WEB-INF/tags/accounts" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Nic Handle View
        <s:if test="%{isHistory()} ">(History)</s:if>
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

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

    <table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td width="50%">
                <n:group2 titleText="Nic Handle" cssIcon="group-icon-nichandle">
                    <n:field label="Nic Handle Id"><div class="showValue">${nicHandle.nicHandleId}</div></n:field>
                    <n:field label="Nic Handle Name"><div class="showValue"><c:out value="${nicHandle.name}"/></div></n:field>
                    <n:field label="Company Name"><div class="showValue"><c:out value="${nicHandle.companyName}"/></div></n:field>
                    <n:field label="Account">
                        <s:select name="nicHandle.account.id" list="accounts" theme="simple" listKey="id"
                                  listValue="name"
                                  headerValue="[UNKNOWN]" disabled="true"/>
                    </n:field>
                    <account:accountFlags ticketEdit="${nicHandle.account.ticketEdit}" agreementSigned="${nicHandle.account.agreementSigned}" />
                    <n:field label="Domains">
                    	<domain:showdomains contact="${nicHandle.nicHandleId}" />
                    </n:field>
                </n:group2>
					<n:group2 titleText="VAT" cssIcon="group-icon-cash">
						 <n:field label="VAT No"><div class="showValue">${nicHandle.vat.vatNo}</div></n:field>
						 <n:field label="VAT Category"><div class="showValue">${nicHandle.vatCategory}</div></n:field>
					</n:group2>
            </td>
            <td width="50%">
					<n:group2 titleText="Status" cssIcon="group-icon-status">
						 <n:field label="Changed"><div class="showValue"><s:date name="nicHandle.changeDate"/></div></n:field>
						 <n:field label="Status"><div class="showValue">${nicHandle.status}</div></n:field>
					</n:group2>
                <n:group2 titleText="Contact" cssIcon="group-icon-contact">
                    <n:field label="E-mail"><div class="showValue"><c:out value="${nicHandle.email}"/></div></n:field>
                    <n:field label="Address"><div class="showValue" style="white-space:pre-wrap;"><c:out value="${nicHandle.address}"/></div></n:field>
                    <n:field label="County"><div class="showValue">${nicHandle.county}</div></n:field>
                    <n:field label="Country"><div class="showValue">${nicHandle.country}</div></n:field>
                    <nichandle:telecomlist label="Phone" inputDisabled="true" wrapper="wrapper.phonesWrapper"/>
                    <nichandle:telecomlist label="Fax" inputDisabled="true" wrapper="wrapper.faxesWrapper"/>
                </n:group2>
                <n:group2 titleText="Hostmaster Remark" cssIcon="group-icon-remarks">
                    <s:if test="isEmpty(nicHandle.nicHandleRemark)">
                        (none)
                    </s:if>
                    <s:else>
                        <div style="white-space:pre-wrap;"><c:out value="${nicHandle.nicHandleRemark}"/></div>
                    </s:else>
                </n:group2>
            </td>
        </tr>
    </table>
	<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" align="center">
				<hr class="buttons"/>
				<div style="width: 60%; margin: 0 auto;">
					<s:if test="%{isHistory()}">
					<s:form name="nic_handle_form_1" theme="simple">
						<s:if test="%{hasCurrent()}">
							<s:hidden name="previousAction"/>
							<s:if test="%{hasPermission('nichandle.current.button')}">
								<s:submit value="Current NicHandle" action="nic-handle-view" theme="simple"/>
							</s:if>
						</s:if>

						<s:submit value="Cancel" method="cancel" theme="simple"/>
					</s:form>
					</s:if>
					<s:else>
						<div style="float:left;padding:0 5px 0 10%;">
							<s:form name="nic_handle_form_2" theme="simple">
								<s:hidden name="nicHandleId"/>
								<s:hidden name="previousAction" value="nic-handle-view"/>
								<s:if test="%{hasPermission('nichandle.accesslevel.button')}">
									<s:submit value="Access Level" action="nic-handle-access-level" method="input" theme="simple"/>
								</s:if>
								<s:if test="%{hasPermission('nichandle.resetpassword.button')}">
									<s:submit value="Reset Password" action="nic-handle-reset-password" method="input" theme="simple"/>
								</s:if>
								<s:if test="%{hasPermission('nichandle.tfa.button')}">
									<s:submit value="T.F.A" action="nic-handle-tfa" disabled="%{!hasAccessRecord}" method="input" theme="simple"/>
								</s:if>
								<s:if test="%{hasPermission('nichandle.alterstatus.button')}">
									<input type="button" value="Alter Status" id="openAlterStatusDialog"/>
								</s:if>
								<s:if test="%{hasPermission('nichandle.edit.button')}">
									<s:submit value="Edit" action="nic-handle-edit" method="input" theme="simple"/>
								</s:if>
								<s:if test="%{hasPermission('nichandle.create.button')}">
									<s:submit value="Create..." action="nic-handle-createFrom" method="input" theme="simple"/>
								</s:if>
							</s:form>
						</div>
						<div style="float:left;">
							<s:form name="nic_handle_form_3" theme="simple">
								<s:hidden name="nicHandleId"/>
								<s:submit value="Cancel" action="%{previousAction}" theme="simple"/>
							</s:form>
						</div>
					</s:else>
				</div>
			</td>
		</tr>

		<tr>
			<td colspan="2">
				<nichandle:hnichandlelist/>
			</td>
		</tr>
	</table>


<nichandle:alterstatus nicHandle="${nicHandle}" statuses="${statuses}"/>

</body>
</html>