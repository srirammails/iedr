<%@attribute name="value" required="true" type="java.lang.Boolean"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<s:if test="#attr.value">
	<div class="style4true">YES</div>
</s:if>
<s:else>
	<div class="style4false">NO</div>
</s:else>

