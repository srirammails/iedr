<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Tickets History Search</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <script type="text/javascript" src="js/tooltip.js"></script>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="ticketshistory-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">

        <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Domain" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;" cssCtrlStyle="float:left;width:65%;">
                        <s:textfield name="searchCriteria.domainName" theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Account name" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;" cssCtrlStyle="float:left;width:65%;">
                        <s:select list="accounts" name="searchCriteria.accountId"
                                  listKey="id" listValue="name"
                                  headerKey="-1" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Domain Holder" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;" cssCtrlStyle="float:left;width:65%;">
                        <s:textfield name="searchCriteria.domainHolder" theme="simple"
                                     cssStyle="width:150px;text-indent:0;"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="padding:5px 0; height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Class" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <s:select list="classList" listKey="name" listValue="name"
                                  headerKey="" headerValue="[All]"
                                  name="searchCriteria.clazz" value="searchCriteria.clazz"
                                  theme="simple" cssStyle="width: 150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Category" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;"
                              cssCtrlStyle="float:left;width:75%;">
                        <s:select list="categoryList" listKey="name" listValue="name"
                                  headerKey="" headerValue="[All]"
                                  name="searchCriteria.category" value="searchCriteria.category"
                                  theme="simple" cssStyle="width: 150px;"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <n:refreshsearch/>
    </n:group2>
</s:form>

<n:group2 titleText="Results" cssIcon="group-icon-history">
    <display:table name="paginatedResult" id="histRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   excludedParams="resetSearch">


        <display:column title="Date" sortable="true" sortProperty="histChangeDate">
            <s:date name="#attr.histRow.object.changeDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Domain Name" property="object.operation.domainNameField.newValue" sortable="true"
                        sortProperty="domainName"/>
        <display:column title="Domain Holder" property="object.operation.domainHolderField.newValue" sortable="true"
                        sortProperty="domainHolder" escapeXml="true"/>
        <display:column title="Req" property="object.operation.type" sortable="true" sortProperty="type"/>
        <display:column title="Account Name" property="object.operation.resellerAccountField.newValue.name" escapeXml="true"/>
        <display:column title="Changed By" property="changedBy" sortable="true" sortProperty="changedByNicHandle"/>
        <display:column title="Remark">
            <n:longtext id="remark${histRow_rowNum-1}" text="${histRow.object.hostmastersRemark}"/>
        </display:column>
        <display:column>
            <s:div cssClass="ticket-row-images">
                <s:url var="showHistoryUrl" action="ticketview-history" includeParams="none">
                    <s:param name="id" value="#attr.histRow.object.id"/>
                    <s:param name="changeId" value="#attr.histRow.changeId"/>
                    <s:param name="previousAction">ticketshistory-input</s:param>
                </s:url>
                <s:a href="%{showHistoryUrl}">
                    <img src="images/skin-default/action-icons/ticket.png" alt="View Historical Record"
                         title="View Historical Record"/>
                </s:a>
            </s:div>
        </display:column>
    </display:table>
</n:group2>


</body>
</html>