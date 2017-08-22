<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC
"-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>Core Registry System Authentication</title>
	<s:head/>	
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:form action="log-in" theme="simple">
	<n:group2 titleText="Login">
		<div style="height:26px;">
			<n:field2 label="User Name" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssContainerStyle="padding-left:10%;" cssLabelStyle="float:left;width:40%;" cssCtrlStyle="float:left;width:60%;">
				<s:textfield label="User Name" name="username" theme="simple"/>
			</n:field2>
		</div>
		<div style="height:26px;">
			<n:field2 label="Password" labelJustify="right" tooltipGapHidden="true"
						 fieldEditable="true"
						 cssContainerStyle="padding-left:10%;" cssLabelStyle="float:left;width:40%;" cssCtrlStyle="float:left;width:60%;">
				<s:password label="Password" name="password" theme="simple"/>
			</n:field2>
		</div>
		<div style="clear:both;">
			<hr class="buttons"/>
			<center><s:submit/></center>
		</div>

	</n:group2>
</s:form>

</body>
</html>
