<%@ tag import="java.util.Calendar" %>
<%@ tag import="pl.nask.crs.config.VersionInfo" %>

<%
    Calendar myCal = Calendar.getInstance();
    int year = myCal.get(Calendar.YEAR);
%>

<div id="footer">
    <!-- note! version inside span is changed by ant task -->
    <!-- it can be moved but it needs to be such span -->
    <div class="version"><span id="antversion">version: <%=VersionInfo.getCrsVersion()%>-<%=VersionInfo.getBuildProfile()%></span> <span id="svn">Revision: <%=VersionInfo.getRevision() %></span></div>
    <p>(c) IE Domain Registry Ltd <%= year%>
    </p>
</div>




