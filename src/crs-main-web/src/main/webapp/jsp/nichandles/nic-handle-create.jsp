<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="nichandle" tagdir="/WEB-INF/tags/nichandles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Nic Handle Create</title>

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

<s:form name="nic_handle_form" theme="simple">
    <table id="details" class="form" width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr>
            <td width="50%">
                <n:group2 titleText="Nic Handle" cssIcon="group-icon-nichandle">
                    <n:field label="Nic Handle Name"><input type="text" name="nicHandleCreateWrapper.name"
                                                            value="${nicHandleCreateWrapper.name}"/></n:field>
                    <n:fielderror fields="nicHandleCreateWrapper.name" colspan="1"/>
                    <n:field label="Company Name"><input type="text" name="nicHandleCreateWrapper.companyName"
                                                         value="${nicHandleCreateWrapper.companyName}"/></n:field>
                    <n:field label="Account">
                        <s:select name="nicHandleCreateWrapper.accountNumber" list="accounts" theme="simple"
                                  listKey="id" listValue="name"/>
                    </n:field>
                    <n:fielderror fields="nicHandleCreateWrapper.accountNumber" colspan="1"/>
                </n:group2>
                <n:group2 titleText="VAT" cssIcon="group-icon-cash">
                    <n:field label="VAT No"><input type="text" maxlength="64" name="nicHandleCreateWrapper.vatNo"
                                                   value="${nicHandleCreateWrapper.vatNo}"/></n:field>
                </n:group2>
            </td>
            <td width="50%">
                <n:group2 titleText="Contact" cssIcon="group-icon-contact">
                    <n:field label="E-mail"><input type="text" name="nicHandleCreateWrapper.email"
                                                   value="${nicHandleCreateWrapper.email}"/></n:field>
						 <n:fielderror fields="nicHandleCreateWrapper.email" colspan="1"/>
						 <div style="height:108px;">
							 <n:field label="Address">
								 <s:textarea name="nicHandleCreateWrapper.address" theme="simple"/>
							 </n:field>
						 </div>
						 <n:fielderror fields="nicHandleCreateWrapper.address" colspan="1"/>
           
 					<n:country countryList="countries" 
                    					countryField="nicHandleCreateWrapper.country"
                    					countyField="nicHandleCreateWrapper.county"/>       
                    <nichandle:telecomlist label="Phone" inputDisabled="false" wrapper="nicHandleCreateWrapper.phonesWrapper" />
                    <nichandle:telecomlist label="Fax" inputDisabled="false" wrapper="nicHandleCreateWrapper.faxesWrapper" />

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
                    <s:submit value="Save" action="nic-handle-create" method="create" theme="simple" cssClass="xSave"/>
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
                    <s:submit value="Cancel" action="%{previousAction!=null?previousAction:'nic-handles-search'}" theme="simple" cssClass="xCancel"/>
                </center>
            </td>
        </tr>
    </table>                    
</s:form>


</body>
</html>