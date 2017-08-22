<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Correct deposit</title>
    <sx:head/>
</head>
<body>
	<n:error2 cssIcon="group-icon-error" titleText="Error"/>
	        
    <s:form name="account_form" theme="simple">
	    <n:group2 titleText="Correct deposit">
    		<n:field2 label="Amount" fielderror="correctionAmount"><input type="text" name="correctionAmount" value="${correctionAmount}"/></n:field2>
    		<n:field2 label="Remark" fielderror="remark"><input type="text" name="remark" size="50" value="${remark}"></n:field2>
	    </n:group2>
    
    	<s:hidden name="id"/>   
    	<center>
	    	<s:submit value="Submit" action="deposit-correct-correct" theme="simple"/>
    	    <input type="button" value="Back" onClick="history.back()">
    	</center> 	
    </s:form>

</body>
