<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Create bulk transfer request</title>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jqModal.js"></script>
	<s:head/>
	<sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

	<s:form action="bulkTransferCreate-create" theme="simple">
		<n:group2 titleText="Transfer between" cssIcon="group-icon-search">
			<n:field2 label="Losing Account" labelJustify="right"
				tooltipGapHidden="true" fieldEditable="true"
				cssLabelStyle="float:left;width:20%;"
				cssCtrlStyle="float:left;width:65%;" fielderror="losingAccountId">
				<s:select name="losingAccountId" list="accounts" listKey="id"
					listValue="name" theme="simple" cssStyle="width:150px;" />
			</n:field2>

			<n:field2 label="Gaining Account" labelJustify="right"
				tooltipGapHidden="true" fieldEditable="true"
				cssLabelStyle="float:left;width:20%;"
				cssCtrlStyle="float:left;width:65%;" fielderror="gainingAccountId">
				<s:select name="gainingAccountId" list="accounts" listKey="id"
					listValue="name" theme="simple" cssStyle="width:150px;" />
			</n:field2>
		</n:group2>
		
		<n:group2 titleText="Hostmaster Remarks" cssIcon="group-icon-remarks">
			<s:textarea name="remarks" theme="simple"></s:textarea>
			<n:fielderror fields="remarks" colspan="1" />
		</n:group2>
	
		<center>
           <s:submit value="Save" theme="simple" cssClass="xSave"/>
           <input type="button" value="Back" onClick="history.back()">
        </center>
	</s:form>

	

</body>
</html>