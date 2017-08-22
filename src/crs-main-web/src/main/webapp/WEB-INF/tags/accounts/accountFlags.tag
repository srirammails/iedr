<%@attribute name="ticketEdit" required="true" type="java.lang.Boolean"%>
<%@attribute name="agreementSigned" required="true" type="java.lang.Boolean"%>
<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>

<n:field label="Agreement Signed"><n:showBoolean value="${agreementSigned}" /></n:field>
<n:field label="Edit Ticket"><n:showBoolean value="${ticketEdit}" /></n:field>