<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib prefix="domain" tagdir="/WEB-INF/tags/domains" %>
<%@ taglib prefix="docs" tagdir="/WEB-INF/tags/documents" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Bulk transfer view</title>

    <sx:head/>

    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <script type="text/javascript" src="js/tooltip.js"></script>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>
		<n:group2 titleText="Transfer between">
			<n:field2 label="Losing Account" labelJustify="right">
				${transferRequest.losingAccountName}
			</n:field2>

			<n:field2 label="Gaining Account" labelJustify="right">
				${transferRequest.gainingAccountName}
			</n:field2>
			<n:field2 label="Remarks" labelJustify="right">
				${transferRequest.remarks}
			</n:field2>	
			<s:if test="%{transferRequest.closed}">
				<n:field2 label="Completion date" labelJustify="right">
					${transferRequest.completionDate}
				</n:field2>
				<n:field2 label="Completed by" labelJustify="right">
					${transferRequest.hostmasterNh}
				</n:field2>								
			</s:if>
		</n:group2>

	<s:if test="%{!transferRequest.closed}">
		<s:form name="bulk_transfer_form" theme="simple" method="POST">

			<n:group2 titleText="Add domains" cssIcon="group-icon-remarks">
			Comma-separated list of domains
				<s:textarea name="newDomains" theme="simple"></s:textarea>
				<n:fielderror fields="newDomains" />
			</n:group2>
			<s:hidden name="id" />
			<center>
				<s:submit value="Save changes" theme="simple" cssClass="xSave"
					action="bulkTransferView-addDomains" />
			</center>
		</s:form>
	</s:if>
	<s:set var="domains" value="transferRequest.requestedDomains"/>
		<n:group2 titleText="Requested domains">
			<display:table name="domains" id="row" class="result">
				<display:column title="Domain name" property="domainName" />
				<display:column title="Transfer date" property="transferDate" />
				<display:column title="Transferred by" property="hostmasterNh" />
			<s:if test="%{!transferRequest.closed}">
				<display:column title="">
					<s:div cssClass="ticket-row-images">
						<s:if test="%{#attr.row.transferDate == null}">
							<s:url var="viewUrl" action="bulkTransferView-removeDomain"
								includeParams="none">
								<s:param name="id">${id}</s:param>
								<s:param name="domainName">${row.domainName}</s:param>
							</s:url>
							<s:a href="%{viewUrl}">
								<img src="images/skin-default/action-icons/delete.png"
									alt="View" title="View" />
							</s:a>
						</s:if>
						<s:else>
							<img src="images/skin-default/web-parts/spacer.png" width="16"
								height="16" />
						</s:else>
					</s:div>
				</display:column>
			</s:if>
		</display:table>
		</n:group2>
		
	<s:form name="bulk_transfer_form_2" theme="simple" method="POST">	
		<s:hidden name="id"/>
		<center>
		<s:if test="%{!transferRequest.closed}">
           <s:submit value="Transfer all" theme="simple" cssClass="xSave" action="bulkTransferView-transferAll"/>
           <s:submit value="Transfer valid" theme="simple" cssClass="xSave" action="bulkTransferView-transferValid"/>
           <s:if test="%{transferRequest.fullyCompleted}">
           		<s:submit value="Close request" theme="simple" cssClass="xSave" action="bulkTransferView-closeRequest"/>
           </s:if>
        </s:if>
           <input type="button" value="Back" onClick="history.back()">
        </center>
	</s:form>
</body>
</html>