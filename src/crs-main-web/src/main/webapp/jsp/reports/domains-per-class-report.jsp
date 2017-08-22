<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Domains per Class Category Report</title>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="domainsPerClassReport-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">
        <div style="padding: 0 0 5px 0; height:26px;">
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
                    <n:field2 label="Registrar Name" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:10%;"
                              cssLabelStyle="float:left;width:30%;"
                              cssCtrlStyle="float:left;width:70%;">
                        <s:select name="searchCriteria.accountId" list="accountsByName"
                                  listKey="id" listValue="name"
                                  headerKey="-1" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:25%">
                <div>
                    <n:field2 label="Nic" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:30%;"
                              cssCtrlStyle="float:left;width:70%;">
                        <s:select name="searchCriteria.billingNH" list="accountsByNic"
                                  listKey="billingContact.nicHandle" listValue="billingContact.nicHandle"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>

        </div>

        <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Registration Date From" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <n:datefield2 field="searchCriteria.from"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;"
                              cssCtrlStyle="float:left;width:42%;">
                        <n:datefield2 field="searchCriteria.to"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="clear:both;">
            <n:refreshsearch/>
        </div>
    </n:group2>
</s:form>

<n:group2 titleText="Result">
    <display:table name="paginatedResult" id="domainRow" class="result" requestURI=""
                   sort="external" excludedParams="resetSearch" export="true">
        <display:column title="Class" property="domainClass" sortable="true"/>
        <display:column title="Category" property="domainCategory" sortable="true"/>
        <display:column title="Domains Count" sortProperty="domainCount" sortable="true" headerClass="force-text-right" class="force-text-right" property="domainCount" decorator="pl.nask.crs.web.displaytag.NumberDecorator"/>
        <display:column title="Account Name" property="accountName" sortable="true"/>
        <display:column title="Bill-C Nic" property="billNHId" sortable="true"/>
        <display:setProperty name="export.excel.filename" value="domain_per_category.xls"/>
        <display:setProperty name="export.csv.filename" value="domain_per_category.csv"/>
        <display:setProperty name="export.xml.filename" value="domain_per_category.xml"/>
    </display:table>
</n:group2>

</body>
</html>