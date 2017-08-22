<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="nd" tagdir="/WEB-INF/tags/domains" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Domains History</title>
	<s:head/>
	<sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="/historical-domain-search.action" name="domainsSearch" theme="simple" method="POST">
	<s:hidden name="page" value="1"/>
	<n:group2 titleText="Criteria" cssIcon="group-icon-search">
		<div style="padding:5px 0 0 0; height:26px;">
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Domain Name" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
								 cssCtrlStyle="float:left;width:60%;">
						<s:textfield name="searchCriteria.domainName" theme="simple" cssStyle="width: 150px;text-indent:0;"/>
					</n:field2>
				</div>
			</div>
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Contact" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssLabelStyle="float:left;width:25%;" cssCtrlStyle="float:left;width:75%;">
						<s:textfield name="searchCriteria.nicHandle" theme="simple" cssStyle="width: 150px;text-indent:0;"/>
					</n:field2>
				</div>
			</div>
		</div>
		<div style="height:26px;">
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Account" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssContainerStyle="padding-left:30%;" cssLabelStyle="float:left;width:40%;"
								 cssCtrlStyle="float:left;width:60%;">
						<s:select name="searchCriteria.accountId" list="accounts" theme="simple" listKey="id"
									 cssStyle="width:150px;"
									 listValue="name"
									 headerKey="-1" headerValue="[ALL]"/>
					</n:field2>
				</div>
			</div>
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Domain Holder" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssLabelStyle="float:left;width:25%;" cssCtrlStyle="float:left;width:75%;">
						<s:textfield name="searchCriteria.domainHolder" theme="simple" cssStyle="width: 150px;text-indent:0;"/>
					</n:field2>
				</div>
			</div>
		</div>

		<div style="height:26px;">
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Class" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssContainerStyle="padding-left:30%;"
								 cssLabelStyle="float:left;width:40%;"
								 cssCtrlStyle="float:left;width:60%;">
						<s:select list="classList" listKey="name" listValue="name"
									 headerKey="" headerValue="[All]"
									 name="searchCriteria.holderClass" value="searchCriteria.holderClass"
									 theme="simple" cssStyle="width: 150px;"/>
					</n:field2>
				</div>
			</div>
			<div style="float:left; width:50%">
				<div>
					<n:field2 label="Category" labelJustify="right" tooltipGapHidden="true"
								 fieldEditable="true"
								 cssLabelStyle="float:left;width:25%;" cssCtrlStyle="float:left;width:75%;">
						<s:select list="categoryList" listKey="name" listValue="name"
									 headerKey="" headerValue="[All]"
									 name="searchCriteria.holderCategory" value="searchCriteria.holderCategory"
									 theme="simple" cssStyle="width: 150px;"/>
					</n:field2>
				</div>
			</div>
		</div>

		<div style="clear:both;">
			<n:refreshsearch/>
		</div>
	</n:group2>
</s:form>

<nd:hdomainlist firstSearch="true" sortable="true"/>

</body>
</html>