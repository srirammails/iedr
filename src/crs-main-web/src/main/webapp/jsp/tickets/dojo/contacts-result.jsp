<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    request.setAttribute("decorator", "none");
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    
    // check for IE
    String ua = request.getHeader("User-Agent");
	boolean isMSIE = (ua != null && ua.indexOf("MSIE") != -1);
%>


    <display:table name="paginatedResult" class="result" id="contactRow">
    	<% if (isMSIE) { %> 		
 		<display:setProperty name="paging.banner.first" value=""/>
        <display:setProperty name="paging.banner.full" value=""/>
        <display:setProperty name="paging.banner.last" value=""/>
        <display:setProperty name="paging.banner.onepage" value=""/>
        <% } %>
        <display:column title="Nic Handle" property="nicHandle"/>
        <display:column title="Name" property="name"/>
        <display:column title="Company Name" property="companyName"/>
        <display:column title="Email" property="email"/>
        <display:column><a href="#" class="closeDialog"
                           onclick="selectNicHandle('${contactRow.nicHandle}', this)">Select</a></display:column>
    </display:table>
