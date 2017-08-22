<%@attribute name="ticketEdit" required="true" type="java.lang.Boolean"%>
<%@attribute name="agreementSigned" required="true" type="java.lang.Boolean"%>
<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>

<n:field2 label="Agreement Signed"><n:showBoolean value="${agreementSigned}" /></n:field2>
<n:field2 label="Edit Ticket"><n:showBoolean value="${ticketEdit}" /></n:field2>