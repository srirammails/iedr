<%@ attribute name="dsmState" required="true" rtexprvalue="true" type="pl.nask.crs.domains.DsmState"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%@ tag import="pl.nask.crs.app.domains.wrappers.DnsWrapper" %>


<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<n:field label="Holder Type">
	<div class="showValue">${dsmState.domainHolderType.description}</div>
</n:field>
<n:field label="Renewal Mode">
	<div class="showValue">${dsmState.renewalMode.description}</div>
</n:field>
<n:field label="NRP Status">
	<div class="showValue">${dsmState.NRPStatus.description}</div>
</n:field>
<n:field label="Customer Type">
	<div class="showValue">${dsmState.customerType.description}</div>
</n:field>
<n:field label="WIPO Dispute">
	<div class="showValue">${dsmState.wipoDispute}</div>
</n:field>
<n:field label="Published">
	<div class="showValue">${dsmState.published}</div>
</n:field>