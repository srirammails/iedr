<%@ attribute name="to" required="true" type="java.util.List" %>
<%@ attribute name="cc" required="true" type="java.util.List" %>
<%@ attribute name="bcc" required="true" type="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="me" uri="usefulellib" %>

<c:if test="${ ! empty to}">
    to: ${me:join(to, ", ")}<br/>
</c:if>
<c:if test="${ ! empty cc}">
    cc: ${me:join(cc, ", ")}<br/>
</c:if>
<c:if test="${ ! empty bcc}">
    bcc: ${me:join(bcc, ", ")}
</c:if>