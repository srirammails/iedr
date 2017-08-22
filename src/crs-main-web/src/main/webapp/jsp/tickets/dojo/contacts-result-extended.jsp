<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<%
    request.setAttribute("decorator", "none");
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<n:group2 titleText="Results" headerClass="lu-corner-h2">
    <display:table name="paginatedResult" class="result" id="contactRow"  excludedParams="resetSearch" >
    <display:setProperty name="paging.banner.first" value=""/>
    <display:setProperty name="paging.banner.full" value=""/>
    <display:setProperty name="paging.banner.last" value=""/>
    <display:setProperty name="paging.banner.onepage" value=""/>
        <display:column title="Nic Handle" property="nicHandle"/>
        <display:column title="Name" property="name"/>
        <display:column title="Email" property="email"/>
        <display:column><a href="#" class="closeDialog"
                           onclick="selectNicHandle('${contactRow.nicHandle}', '${contactRow.email}',this)">Select</a></display:column>
    </display:table>
</n:group2>