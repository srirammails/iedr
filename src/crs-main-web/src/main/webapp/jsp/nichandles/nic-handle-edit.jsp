<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="nichandle" tagdir="/WEB-INF/tags/nichandles" %>
<%@ taglib prefix="nask" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="permission" tagdir="/WEB-INF/tags/permission" %>
<%@ taglib prefix="domain" tagdir="/WEB-INF/tags/domains" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Nic Handle Edit </title>

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
        <link href="<s:url value='/styles/skin-iedr-history/main.css'/>" rel="stylesheet" type="text/css"
              media="all"/>
    </s:if>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="nic_handle_form" theme="simple">
<s:hidden name="nicHandleId"/>
<table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td width="50%">
            <n:group2 titleText="Nic Handle" cssIcon="group-icon-nichandle">
                <n:field label="Nic Handle Id">
                    <div class="showValue">${nicHandle.nicHandleId}</div>
                </n:field>
                <s:if test="#session.get('user-key')!=null && #session.get('permission-key')!=null && #session.get('permission-key').hasPermission(#session.get('user-key'), 'nichandle.edit.main')">
                    <n:field label="Nic Handle Name" fielderror="nicHandle.name"><input type="text"
                                                                                        name="nicHandle.name"
                                                                                        value="${nicHandle.name}"/></n:field>
                    <n:field label="Company Name" fielderror="nicHandle.companyName"><input type="text" name="nicHandle.companyName"
                                                         value="${nicHandle.companyName}"/></n:field>
                    <n:field label="Account" fielderror="accountNumberWrapper.accountNumber">
                        <s:select name="accountNumberWrapper.accountNumber" list="accounts" theme="simple" listKey="id"
                                  listValue="name"
                                  headerValue="[UNKNOWN]"/>
                    </n:field>
					<n:field label="Domains">
                    	<domain:showdomains contact="${nicHandle.nicHandleId}" />
                    </n:field>
                </s:if>
                <s:else>
                    <n:field label="Nic Handle Name" fielderror="nicHandle.name">
                        <div class="showValue">${nicHandle.name}</div>
                    </n:field>
                    <n:field label="Company Name">
                        <div class="showValue">${nicHandle.companyName}</div>
                    </n:field>
                    <n:field label="Account" fielderror="accountNumberWrapper.accountNumber">
                        <s:select name="nicHandle.account.id" list="accounts" theme="simple" listKey="id"
                                  listValue="name"
                                  headerValue="[UNKNOWN]" disabled="true"/>
                    </n:field>
					<n:field label="Domains">
                    	<domain:showdomains contact="${nicHandle.nicHandleId}" />
                    </n:field>
                </s:else>
            </n:group2>

            <n:group2 titleText="VAT" cssIcon="group-icon-cash">
                <s:if test="#session.get('user-key')!=null && #session.get('permission-key')!=null && #session.get('permission-key').hasPermission(#session.get('user-key'), 'nichandle.edit.vat')">
                    <n:field label="VAT No">
                        <input type="text" maxlength="64" name="nicHandle.vat.vatNo" value="${nicHandle.vat.vatNo}"/>
                    </n:field>
                    <n:field label="VAT Category" >
                        <s:select list="categories" name="nicHandle.vatCategory" headerKey="" headerValue="(unset)" theme="simple" />
                    </n:field>
                </s:if>
                <s:else>
                    <n:field label="VAT No">
                        <div class="showValue">${nicHandle.vat.vatNo}</div>
                    </n:field>
                    <n:field label="VAT Category">
                        <div class="showValue">${nicHandle.vatCategory}</div>
                    </n:field>
                </s:else>
            </n:group2>
        </td>
        <td width="50%">
            <n:group2 titleText="Status" cssIcon="group-icon-status">
                <n:field label="Changed">
                    <div class="showValue"><s:date name="nicHandle.changeDate"/></div>
                </n:field>
                <n:field label="Status">
                    <div class="showValue">${nicHandle.status}</div>
                </n:field>
            </n:group2>
            <n:group2 titleText="Contact" cssIcon="group-icon-contact">
                <s:if test="#session.get('user-key')!=null && #session.get('permission-key')!=null && #session.get('permission-key').hasPermission(#session.get('user-key'), 'nichandle.edit.address')">
                    <n:field label="E-mail" fielderror="nicHandle.email">
                        <input type="text" name="nicHandle.email" value="${nicHandle.email}"/>
                    </n:field>
                    <div style="height:108px;">
							  <n:field label="Address" fielderror="nicHandle.address">
									<s:textarea name="nicHandle.address" theme="simple"/>
							  </n:field>
						  </div>
                    <nask:country countryList="countries"
                                  countryField="nicHandle.country"
                                  countyField="nicHandle.county"/>
                    <nichandle:telecomlist label="Phone" inputDisabled="false" wrapper="wrapper.phonesWrapper"/>
                    <nichandle:telecomlist label="Fax" inputDisabled="false" wrapper="wrapper.faxesWrapper"/>
                </s:if>
                <s:else>
                    <n:field label="E-mail">
                        <div class="showValue">${nicHandle.email}</div>
                    </n:field>
                    <n:field label="Address">
                        <div class="showValue">${nicHandle.address}</div>
                    </n:field>
                    <n:field label="County">
                        <div class="showValue">${nicHandle.county}</div>
                    </n:field>
                    <n:field label="Country">
                        <div class="showValue">${nicHandle.country}</div>
                    </n:field>
                    <nichandle:telecomlist label="Phone" inputDisabled="true" wrapper="wrapper.phonesWrapper"/>
                    <nichandle:telecomlist label="Fax" inputDisabled="true" wrapper="wrapper.faxesWrapper"/>
                </s:else>
            </n:group2>
            <n:group2 titleText="Hostmaster Remarks" cssIcon="group-icon-remarks">
                <s:textarea name="hostmastersRemark" theme="simple"></s:textarea>
                <n:fielderror fields="hostmastersRemark"/>
            </n:group2>
        </td>
    </tr>
</table>
<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td colspan="2" align="center">
            <hr class="buttons"/>
            <center>
                <s:submit value="Save" action="nic-handle-edit" method="save" theme="simple" cssClass="xSave"/>
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
            	<s:hidden name="nicHandleId"/>
                <s:submit value="Cancel" action="nic-handle-view" method="view" theme="simple" cssClass="xCancel"/>
            </center>
        </td>
    </tr>
</table>
</s:form>


</body>
</html>