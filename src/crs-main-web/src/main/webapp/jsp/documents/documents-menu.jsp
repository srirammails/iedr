<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Documents</title>
	<s:head/>
	<sx:head/>
</head>
<body>
<!--<div class="contentheader">Documents Menu</div>-->
<br/><br/>
<s:a href="documents-incoming.action">
	New
</s:a>
<br/><br/>
<s:a href="documents-assigned.action">
	Assigned
</s:a>
</body>
</html>