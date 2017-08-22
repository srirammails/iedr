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
    <title>Two Factor Authentication Setup</title>

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
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form name="nic_handle_form" theme="simple">
<s:hidden name="nicHandleId"/>
<table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td>
            <n:group2 titleText="TFA Status" cssIcon="group-icon-nichandle">
                <n:field label="TFA Status">
                	<s:if test="tfaUsed">
                		Enabled
                	</s:if>
                	<s:else>
                		Disabled
                	</s:else>
                </n:field>
            </n:group2>

        </td>
   </tr>
</table>
<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td colspan="2" align="center">
            <hr class="buttons"/>
            <center>
            	<s:if test="tfaUsed">
                	<s:submit value="Disable" action="nic-handle-tfa" method="disable" theme="simple"/>
				</s:if>
				<s:else>
                	<s:submit value="Enable" action="nic-handle-tfa" method="enable" theme="simple"/>
                </s:else>
                <s:submit value="Back" action="nic-handle-view" method="view" theme="simple"/>
            </center>
        </td>
    </tr>
</table>

<table class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
    <tr>
        <td colspan="2" align="center">
            <center>
            </center>
        </td>
    </tr>
</table>
</s:form>


</body>
</html>