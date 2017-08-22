<%@attribute name="contact" required="true" description="contact nic handle, for which the domains"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<s:url var="listDomainsUrl" action="domains-list"
	includeParams="none">
	<s:param name="searchCriteria.nicHandle">${contact}</s:param>
	<s:param name="resetSearch" value="true" />
</s:url>
<!-- listDomainsUrl -->
<div>
  <s:a href="%{listDomainsUrl}">
	  <img src="images/skin-default/action-icons/details.png" title="Show" />
  </s:a>
</div>
