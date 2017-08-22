<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<s:set var="fName" value="#attr.document.fileName"/>

<%@page import="java.net.URL"%>
<%@page import="java.net.URI"%><html>
<%
    //String fullPath = "" + request.getAttribute("path") + "/" +  request.getAttribute("fName");
	URI uri = new URI(null, null, request.getAttribute("path") + "/" + request.getAttribute("fName"), null);
	String fullPath = uri.toASCIIString();
	
%>
<s:set ></s:set>


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>
        <s:if test="%{document.assigned}">
            Assigned Document
        </s:if>
        <s:else>
            New Document
        </s:else>
    </title>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<center>
    <s:if test="%{document.assigned}">
        <s:set name="assigned" value="true"/>
    </s:if>
    <s:else>
        <br><br>
        <s:set name="assigned" value="false"/>
        <s:url var="documentDelete" action="documents-delete">
            <s:param name="document.fileName" value="%{#attr.document.fileName}"/>
            <s:param name="document.fileType" value="%{#attr.document.fileType}"/>
        </s:url>
        <s:a href="%{documentDelete}"
             onclick="return confirm('Are you sure you want to delete this document?');">Delete Document</s:a><br><br>
    </s:else>	
    <s:if test="documentFileExists()">
    <s:if test="#attr.document.isTiff()">       
    <embed width=600 height=500
           src="<%= fullPath%>" type="image/tiff">
        </s:if>
        <s:elseif test="#attr.document.isPicture()">
        <a href="<%= fullPath%>">View in original size</a> <br><br>
        <img src="<%= fullPath%>" width="600" height="500" alt="Picture"/>
        </s:elseif>
        <s:else>
        <a href="<%= fullPath%>" >Unknown document format. Click to download</a><br><br>
        </s:else>
        </s:if>
        <s:else>
    <p>File does not exist.</p>
    </s:else>
</center>
<n:group2 titleText="Log new document:" cssIcon="group-icon-document">
    <s:form action="documents-update" theme="simple">
        <s:hidden name="document.id" value="%{#attr.document.id}" theme="simple"/>
        <s:hidden name="document.fileName" value="%{#attr.document.fileName}" theme="simple"/>
        <s:hidden name="document.fileType" value="%{#attr.document.fileType}" theme="simple"/>
        <center>
            <n:field label="Domain name" hideTooltipGap="true"
                     cssContainer="force-margin-left30 force-width30" fielderror="document.domainNames">
                <s:textarea name="document.domainNames" value="%{#attr.document.domainNames}" label="Domain name"
                             disabled="%{#assigned}" theme="simple" cssStyle="width:100%;"/>
            </n:field>
            <n:field label="From" hideTooltipGap="true"
                     cssContainer="force-margin-left30 force-width30" fielderror="document.docSource">
                <s:textfield name="document.docSource" value="%{#attr.document.docSource}" label="From"
                             disabled="%{#assigned}" theme="simple" cssStyle="width:100%;"/>
            </n:field>
            <n:field label="Email" hideTooltipGap="true"
                     cssContainer="force-margin-left30 force-width30">
                <s:if test="%{document.assigned}">
                    <s:select name="document.account" list="docAccount" theme="simple" listKey="id" listValue="name"
                              headerKey="-1" disabled="%{#assigned}" cssStyle="width:100%;"/>
                </s:if>
                <s:else>
                    <s:select name="document.account" list="accounts" theme="simple" listKey="id" listValue="name"
                              headerKey="-1" headerValue="[NONE]" disabled="%{#assigned}" cssStyle="width:100%;"/>
                </s:else>
            </n:field>
            <n:field label="Type" hideTooltipGap="true"
                     cssContainer="force-margin-left30 force-width30">
                <s:if test="%{document.assigned}">
                    <s:set var="purpose" value="%{#attr.document.docPurpose}"/>
                </s:if>
                <s:else>
                    <s:set var="purpose" value="%{#attr.purposes}"/>
                </s:else>

                <s:select name="document.docPurpose" list="%{purpose}" label="Log to" disabled="%{#assigned}"
                          theme="simple" cssStyle="width:100%;"/>
            </n:field>
            <s:if test="%{document.assigned}">
                <n:field label="Date" hideTooltipGap="true"
                         cssContainer="text-align-left force-width30">
                    <s:date name="document.crDate"/>
                </n:field>
            </s:if>


                <%--<td>Domain name:</td>--%>
                <%--<td><s:textfield name="document.domainNames" value="%{#attr.document.domainNames}" label="Domain name"--%>
                <%--disabled="%{#assigned}" theme="simple" />--%>
                <%--</td>--%>
            <!--</tr>-->
            <!--<tr>-->
                <%--<td>From:</td>--%>
                <%--<td><s:textfield name="document.docSource" value="%{#attr.document.docSource}" label="From"--%>
                <%--disabled="%{#assigned}" theme="simple"/>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                <%--<td>Email:</td>--%>
                <%--<td>--%>
                <%--<s:if test="%{document.assigned}">--%>
                <%--<s:select name="document.account" list="docAccount" theme="simple" listKey="id" listValue="name"--%>
                <%--headerKey="-1" disabled="%{#assigned}"/>--%>
                <%--</s:if>--%>
                <%--<s:else>--%>
                <%--<s:select name="document.account" list="accounts" theme="simple" listKey="id" listValue="name"--%>
                <%--headerKey="-1" headerValue="[NONE]" disabled="%{#assigned}"/>--%>
                <%--</s:else>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                <%--<td>Log to:</td>--%>
                <%--<td>--%>
                <%--<s:if test="%{document.assigned}">--%>
                <%--<s:set var="purpose" value="%{#attr.document.docPurpose}"/>--%>
                <%--</s:if>--%>
                <%--<s:else>--%>
                <%--<s:set var="purpose" value="%{#attr.purposes}"/>--%>
                <%--</s:else>--%>

                <%--<s:select name="document.docPurpose" list="%{purpose}" label="Log to" disabled="%{#assigned}" theme="simple"/>--%>

                <%--</td>--%>
        </center>
        <hr class="buttons"/>
        <center>
            <input type="button" value="Back" onClick="history.back()">
            <s:if test="%{!document.assigned}">
                <s:submit value="Assign" theme="simple"/>
            </s:if>
        </center>
    </s:form>
</n:group2>
</body>
</html>