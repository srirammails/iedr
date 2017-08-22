<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>View deposit</title>

    <sx:head/>
</head>
<body>
	<n:error2 cssIcon="group-icon-error" titleText="Error"/>    
    
    <n:group2 titleText="Current deposit status">
    	<n:field2 label="Deposit id">${deposit.nicHandleId}</n:field2>
    	<n:field2 label="Current balance">
            <s:property value="getText('struts.money.format',{deposit.closeBal})"/>
        </n:field2>
    	<n:field2 label="Available funds">
            <s:property value="getText('struts.money.format',{deposit.closeBalIncReservaions})"/>
        </n:field2>
    </n:group2>
    
    <n:group2 titleText="Last transaction">
    	<n:field2 label="ID">${deposit.orderId}</n:field2>
    	<n:field2 label="Type">${deposit.transactionType}</n:field2>
    	<n:field2 label="Date"><s:date name="deposit.transactionDate"></s:date></n:field2>
		<n:field2 label="Amount">
            <s:property value="getText('struts.money.format',{deposit.transactionAmount})"/>
        </n:field2>
    </n:group2>
    
    <s:form name="account_form" theme="simple">
    	<s:hidden name="id"/>   
    	<center>
    		<s:if test="%{hasPermission('accessLevelPerms.edit.button')}">
	    		<s:submit value="Correct" action="deposit-correct-input" theme="simple"/>
	    	</s:if>
            <s:submit value="TopUp" action="deposit-topup-input" theme="simple"/>
            <s:submit value="Back" action="account-view-view" theme="simple" />
    	</center> 	
    </s:form>

</body>
