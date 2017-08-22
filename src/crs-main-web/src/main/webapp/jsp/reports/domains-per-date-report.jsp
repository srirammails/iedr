<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ticket" tagdir="/WEB-INF/tags/tickets" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registrations Per Month/Year for Registrar Report</title>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="totalDomainsPerDateReport-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">
        <div style="padding: 0 0 5px 0; height:26px;">
            <div style="float:left; width:25%">
                <div>
                    <n:field2 label="Account" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:30%;"
                              cssCtrlStyle="float:left;width:70%;">
                        <s:select name="searchCriteria.accountId" list="accounts"
                                  listKey="id" listValue="name" headerKey="-1" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:25%">
                <div>
                    <n:field2 label="Class" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:30%;"
                              cssCtrlStyle="float:left;width:70%;">
                        <s:select list="classList" listKey="name" listValue="name"
                                  headerKey="" headerValue="[All]"
                                  name="searchCriteria.holderClass" value="searchCriteria.holderClass"
                                  theme="simple" cssStyle="width: 150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:25%">
                <div>
                    <n:field2 label="Category" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:30%;"
                              cssCtrlStyle="float:left;width:70%;">
                        <s:select list="categoryList" listKey="name" listValue="name"
                                  headerKey="" headerValue="[All]"
                                  name="searchCriteria.holderCategory" value="searchCriteria.holderCategory"
                                  theme="simple" cssStyle="width: 150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:25%">
                <div>
                    <n:field2 label="Customer Type" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:30%;"
                              cssCtrlStyle="float:left;width:70%;">
                        <s:select list="customerTypes" name="searchCriteria.customerType"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
        </div>
        <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Registration From" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:65%;"
                              cssCtrlStyle="float:left;width:35%;">
                        <n:datefield2 field="searchCriteria.registrationFrom"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:5%;"
                              cssCtrlStyle="float:left;width:95%;">
                        <n:datefield2 field="searchCriteria.registrationTo"/>
                    </n:field2>
                </div>
            </div>
        </div>
        <div style="height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Report Time Granulation" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <s:select list="reportTimeGranulation" name="searchCriteria.reportTimeGranulation"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Report Type" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;"
                              cssCtrlStyle="float:left;width:75%;">
                        <s:select list="reportTypeGranulation" name="searchCriteria.reportTypeGranulation"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
        </div>
        <div style="clear:both;">
            <n:refreshsearch1/>
        </div>
    </n:group2>
</s:form>


<n:group2 titleText="Result">
    <display:table name="paginatedResult" id="domainRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external" excludedParams="resetSearch" export="true">
        <s:if test="isPerRegistrarSearch()">
            <display:column title="Registrar" property="billingNH" sortable="true"/>
            <display:column title="Name" property="accountName" sortable="true"/>            
        </s:if>
		<display:column title="Class" property="domainClass" sortable="true"/>
        <display:column title="Category" property="domainCategory" sortable="true"/>
        <display:column title="Domains Count" sortProperty="domainCount" sortable="true" headerClass="force-text-right" class="force-text-right" property="domainCount" decorator="pl.nask.crs.web.displaytag.NumberDecorator" />
        <display:column media="html">
            <s:url var="viewDomainsUrl" action="domains-list" includeParams="none">
                <s:param name="searchCriteria.nicHandle">${domainRow.billingNH}</s:param>
                <s:param name="searchCriteria.holderClass">${domainRow.domainClass}</s:param>
                <s:param name="searchCriteria.holderCategory">${domainRow.domainCategory}</s:param>
                <s:param name="registrationFrom" value="getRegDateFrom(#attr.domainRow.date)"/>
                <s:param name="registrationTo" value="getRegDateTo(#attr.domainRow.date)"/>
                <s:param name="resetSearch" value="true" />
            </s:url>
            <s:a href="%{viewDomainsUrl}">
                <img src="images/skin-default/action-icons/ticket.revise.png" alt="View Domains" title="View Domains"/>
            </s:a>       	
        </display:column>
        <display:column title="Date" sortable="true" sortProperty="date">
            <s:date name="#attr.domainRow.date" format="%{getText(datePattern)}"/>
        </display:column>
        <display:setProperty name="export.excel.filename" value="registrations.xls"/>
        <display:setProperty name="export.csv.filename" value="registrations.csv"/>
        <display:setProperty name="export.xml.filename" value="registrations.xml"/>
    </display:table>
</n:group2>

</body>
</html>