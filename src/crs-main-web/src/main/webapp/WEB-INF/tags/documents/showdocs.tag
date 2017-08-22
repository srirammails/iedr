<%@attribute name="condition" required="true" type="java.lang.Boolean" description="if true, 'YES' is displayed and a link to documents is shown. If false, 'NO' is displayed"%>
<%@attribute name="domainName" required="true" description="domain name for which the link to the documents should be generated"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<s:url var="listDocumentsUrl" action="documents-list"
	includeParams="none">
	<s:param name="documentSearchCriteria.domainName">${domainName}</s:param>
</s:url>

<div class="showDocs">
	<s:if test="#attr.condition">YES</s:if>
	<s:else>NO</s:else>
</div>

<div class="showDocs-icon">
<s:if test="#attr.condition">
	<s:a href="%{listDocumentsUrl}">
		<img src="images/skin-default/action-icons/details.png" title="Show" />
	</s:a>
</s:if>
</div>
