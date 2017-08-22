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
    <title>DOA</title>
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
        $(document).ready(function() {
            $('#status-dialog').jqm({trigger: false})
                    .jqmAddTrigger($('#open-status-dialog'))
                    .jqmAddClose($('#close-status-dialog'));
        })
    </script>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<s:form action="viewDOA-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">
        <div style="height:26px;">
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="DOA by external user: Registrar Name" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:50%;"
                              cssCtrlStyle="float:left;width:50%;">
                        <s:select name="searchCriteria.accountBillNH" list="accountsByName"
                                  listKey="billingContact.nicHandle" listValue="name"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="Nic" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:30%"
                              cssCtrlStyle="float:left;width:70%;">
                        <s:select name="searchCriteria.nicHandleId" list="accountsByNic"
                                  listKey="billingContact.nicHandle" listValue="billingContact.nicHandle"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:33%">
                <div>
                    <n:field2 label="DOA by internal user: IEDR Staff Nick" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:50%;"
                              cssCtrlStyle="float:left;width:50%;">
                        <s:select name="searchCriteria.correctorNH" list="internalUsers"
                                  listKey="nicHandleId" listValue="nicHandleId"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="padding:7px 0 0 0; height:26px;">
                <div style="float:left; width:50%">
                    <div>
                        <n:field2 label="Transaction From" labelJustify="right" tooltipGapHidden="true"
                                  fieldEditable="true"
                                  cssContainerStyle="padding-left:30%;"
                                  cssLabelStyle="float:left;width:40%;"
                                  cssCtrlStyle="float:left;width:60%;">
                            <n:datefield2 field="searchCriteria.transactionDateFrom"/>
                        </n:field2>
                    </div>
                </div>
                <div style="float:left; width:50%">
                    <div>
                        <n:field2 label="To" labelJustify="right" tooltipGapHidden="true"
                                  fieldEditable="true"
                                  cssLabelStyle="float:left;width:25%;"
                                  cssCtrlStyle="float:left;width:42%;">
                            <n:datefield2 field="searchCriteria.transactionDateTo"/>
                        </n:field2>
                    </div>
                </div>
            </div>
        </div>
        <div style="clear:both;">
            <n:refreshsearch1/>
        </div>
    </n:group2>
</s:form>

<n:group2 titleText="Results">
    <display:table name="paginatedResult" id="depositsRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external" excludedParams="resetSearch" export="true">
        <display:column title="Lodgment Date" sortProperty="transactionDate" sortable="true">
            <s:date name="#attr.depositsRow.transactionDate"/>
        </display:column>
        <display:column title="Bill-C Nic" property="nicHandleId" sortable="true"/>
        <display:column title="IEDR Staff Nic" property="correctorNH" sortable="true"/>
        <display:column title="DOA Amount Lodged" sortProperty="transactionAmount" sortable="true" headerClass="force-text-right" class="force-text-right" property="transactionAmount" decorator="pl.nask.crs.web.displaytag.MoneyDecorator"/>
        <display:column title="Order Id" property="orderId" sortable="true"/>
        <display:column title="Remark" property="remark" sortable="true"/>
        <display:setProperty name="export.excel.filename" value="doa.xls"/>
        <display:setProperty name="export.csv.filename" value="doa.csv"/>
        <display:setProperty name="export.xml.filename" value="doa.xml"/>
    </display:table>
</n:group2>

<ticket:contacts-search fieldId="nicHandleId"/>
</body>
</html>