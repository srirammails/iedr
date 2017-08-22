<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<n:group2 titleText="Results" cssIcon="group-icon-domain">
	<display:table name="paginatedResult" id="domainRow" class="result" requestURI="" sort="external"
						excludedParams="resetSearch">
		<display:column title="Registration Date" sortable="true" sortProperty="registrationDate">
			<s:date name="#attr.domainRow.registrationDate" format="dd/MM/yyyy"/>
		</display:column>
		<display:column title="Renew Date" sortable="true" sortProperty="renewDate">
			<s:date name="#attr.domainRow.renewDate" format="dd/MM/yyyy"/>
		</display:column>
		<display:column title="Account Name" property="resellerAccount.name" sortable="true"
							 sortProperty="resellerAccountName" escapeXml="true"/>
		<display:column title="Domain Name" property="name" sortable="true" sortProperty="name"/>
		<display:column title="Domain Holder" property="holder" sortable="true" sortProperty="holder" escapeXml="true"/>
		<%--<display:column title="Bill Status" property="billingStatus.detailedDescription"/>--%>
		<%--<display:column title="Domain Status" property="domainStatus.statusName" />--%>
		<display:column title="">
			<s:div cssClass="ticket-row-images">
				<s:url var="viewUrl" action="domainview" includeParams="none">
					<s:param name="domainName">${domainRow.name}</s:param>
					<s:param name="previousAction">domains-input</s:param>
				</s:url>
				<s:a href="%{viewUrl}">
					<img src="images/skin-default/action-icons/domain.png" alt="View" title="View"/>
				</s:a>
			</s:div>
		</display:column>
	</display:table>
</n:group2>