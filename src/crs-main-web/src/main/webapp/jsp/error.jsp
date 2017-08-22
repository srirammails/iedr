<!DOCTYPE html PUBLIC
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page isErrorPage="true" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Error</title>
    <s:head/>
</head>
<body>

<div class="group">
	<div class="group-header">

		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="lu-corner-error">&nbsp;</td>
				<td class="up-corner-error">
					<div class="group-icon group-icon-error">&nbsp;</div>
					<p>&nbsp;</p>
				</td>
				<td class="ru-corner-error">&nbsp;</td>
			</tr>
		</table>
	</div>
	<div class="group-content-error">
	<%= new java.util.Date() %>		
		<h2>An error has occurred</h2>

		<p>
			 Please report this error to your system administrator
			 or appropriate technical support personnel.			
		</p>
		<h3><s:property value="%{#session.EXCEPTION_OBJECT}"/></h3>
		<pre>
			<s:property value="%{#session.EXCEPTION_TRACE}"/>			
		</pre>
		<hr class="buttons"/>
		<center style="height:20px;">
			<input type="button" value="Back" onClick="history.back()">
		</center>

	</div>
	<div class="group-bottom-error">
		<div class="group-bottom-error-fix">
			<div class="ld-corner-round-error"></div>
			<div class="rd-corner-round-error"></div>
			<div class="dn-corner-round-error"></div>
		</div>
	</div>
</div>



</body>
</html>
