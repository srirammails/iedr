<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="nd" tagdir="/WEB-INF/tags/documents" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Assigned Documents</title>
	<s:head/>
	<sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="/documents-search.action" theme="simple" method="GET">
	<n:group2 titleText="Criteria" cssIcon="group-icon-search">

		<div style="height:26px;">
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Date From" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
								 cssCtrlStyle="float:left;width:60%;">
						<n:datefield2 field="documentSearchCriteria.from"/>
					</n:field2>
				</div>
			</div>
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:42%;">
						<n:datefield2 field="documentSearchCriteria.to"/>
					</n:field2>
				</div>
			</div>
		</div>

		<div style="height:26px;">
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Domain Name" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
								 cssCtrlStyle="float:left;width:60%;">
						<s:textfield name="documentSearchCriteria.domainName" theme="simple" cssStyle="width:150px;text-indent:0;"/>
					</n:field2>
				</div>
			</div>
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="From (source)" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:65%;">
						<s:textfield name="documentSearchCriteria.docSource" theme="simple" cssStyle="width:150px;text-indent:0;"/>
					</n:field2>
				</div>
			</div>
		</div>

		<div style="clear:both;">
			<n:refreshsearch/>
		</div>
	</n:group2>
</s:form>


<nd:doclist />
</body>
</html>