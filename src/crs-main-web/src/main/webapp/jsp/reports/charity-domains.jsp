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
    <title>Charity Domains</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <script type="text/javascript">
        function selectNicHandle(nicHandle, cntrl) {
            var root = $(cntrl).parents('.jqmWindow');
            var fieldId = '#' + root.children('.fieldId')[0].value;
            $(fieldId).attr('value', nicHandle);
            root.jqmHide();
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#status-dialog').jqm({trigger:false})
                    .jqmAddTrigger($('#open-status-dialog'))
                    .jqmAddClose($('#close-status-dialog'));
        })
    </script>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="charityDomains-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">
        <div style="height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Domain Name" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <s:textfield name="searchCriteria.domainName" theme="simple"
                                     cssStyle="width:150px;text-indent:0;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="NRP Status" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;"
                              cssCtrlStyle="float:left;width:75%;">
                        <s:select list="inNrpStatuses" listKey="code" listValue="description"
                                  headerKey="" headerValue="[ALL]"
                                  name="searchCriteria.nrpStatus"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
        </div>
        <div style="height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Customer Type" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <s:select list="customerTypes" name="searchCriteria.type"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:25%">
                <div>
                    <n:field2 label="Registrar Name" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:10%;"
                              cssLabelStyle="float:left;width:45%;"
                              cssCtrlStyle="float:left;width:55%;">
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
                              cssLabelStyle="float:left;width:10%;"
                              cssCtrlStyle="float:left;width:90%;">
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
        <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Renewal From" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:65%;"
                              cssCtrlStyle="float:left;width:35%;">
                        <n:datefield2 field="searchCriteria.renewFrom"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:5%;"
                              cssCtrlStyle="float:left;width:95%;">
                        <n:datefield2 field="searchCriteria.renewTo"/>
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
        <display:column title="Domain Name" property="name" sortable="true"/>
        <display:column title="BillC NH" property="billingContact.nicHandle" sortable="false"/>
        <display:column title="BillC Name" property="billingContact.name" sortable="false"/>
        <display:column title="NRP Status" property="dsmState.NRPStatus" sortable="false"/>
        <display:column title="Registration Date" sortable="true" sortProperty="registrationDate">
            <s:date name="#attr.domainRow.registrationDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Renewal Date" sortable="true" sortProperty="renewDate">
            <s:date name="#attr.domainRow.renewDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Suspension Date" sortable="true" sortProperty="suspensionDate">
            <s:date name="#attr.domainRow.suspensionDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:column title="Deletion Date" sortable="true" sortProperty="deletionDate">
            <s:date name="#attr.domainRow.deletionDate" format="dd/MM/yyyy"/>
        </display:column>
        <display:setProperty name="export.excel.filename" value="charity_domains.xls"/>
        <display:setProperty name="export.csv.filename" value="charity_domains.csv"/>
        <display:setProperty name="export.xml.filename" value="charity_domains.xml"/>
    </display:table>
</n:group2>

<ticket:contacts-search fieldId="billingNH"/>
</body>
</html>