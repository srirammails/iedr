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
    <title>Deposit Actual Balance</title>
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

<s:form action="depositAccount-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">
        <div style="height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Registrar Name" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:45%;"
                              cssLabelStyle="float:left;width:45%;"
                              cssCtrlStyle="float:left;width:55%;">
                        <s:select name="searchCriteria.accountBillNH" list="accountsByName"
                                  listKey="billingContact.nicHandle" listValue="name"
                                  headerKey="" headerValue="[ALL]"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Nic" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:1%;"
                              cssCtrlStyle="float:left;width:99%;">
                        <s:select name="searchCriteria.nicHandleId" list="accountsByNic"
                                  listKey="billingContact.nicHandle" listValue="billingContact.nicHandle"
                                  headerKey="" headerValue="[ALL]"
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

<n:group2 titleText="Results">
    <display:table name="paginatedResult" id="depositsRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external" excludedParams="resetSearch" export="true">
        <display:column title="Nic Handle Id" property="nicHandleId" sortable="true"/>
        <display:column title="Name" property="nicHandleName" sortable="true"/>
        <display:column title="Actual Balance" sortable="false" headerClass="force-text-right" class="force-text-right" property="closeBal" decorator="pl.nask.crs.web.displaytag.MoneyDecorator" />
        <display:column title="View Tx History" media="html">
            <s:div style="float: left;">
                <s:url var="editUrl" action="transactionsHistReport-input?resetSearch=true" includeParams="none">
                    <s:param name="nicHandleId">${depositsRow.nicHandleId}</s:param>
                </s:url>
                <s:a href="%{editUrl}">
                    <img src="images/skin-default/action-icons/ticket.revise.png" alt="View Transactions History" title="View Transactions History"/>
                </s:a>
            </s:div>
        </display:column>
        <display:column title="Reserved Funds" sortable="false" headerClass="force-text-right" class="force-text-right" property="reservedFunds" decorator="pl.nask.crs.web.displaytag.MoneyDecorator" media="csv xml excel"/>
        <display:column title="Reserved Funds" sortable="false" headerClass="force-text-right" class="force-text-right" media="html">
            <s:if test="%{#attr.depositsRow.reservedFunds neq 0}">
                <s:div class="force-text-right">
                    <s:url var="editUrl" action="transactionsReport-input" includeParams="none">
                        <s:param name="nicHandleId">${depositsRow.nicHandleId}</s:param>
                    </s:url>
                    <s:a cssStyle="text-decoration: underline;" href="%{editUrl}">
                        <s:property value="getText('struts.money.format',{#attr.depositsRow.reservedFunds})"/>
                    </s:a>
                </s:div>
            </s:if>
            <s:else>0.00</s:else>
        </display:column>        
        <display:column title="Available Balance" sortable="false" headerClass="force-text-right" class="force-text-right" property="closeBalIncReservaions" decorator="pl.nask.crs.web.displaytag.MoneyDecorator" />
        <display:setProperty name="export.excel.filename" value="deposit_actual_balance.xls"/>
        <display:setProperty name="export.csv.filename" value="deposit_actual_balance.csv"/>
        <display:setProperty name="export.xml.filename" value="deposit_actual_balance.xml"/>
    </display:table>
</n:group2>

<ticket:contacts-search fieldId="nicHandleId"/>
</body>
</html>