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
    <title>Hostmaster Usage</title>
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

<s:form action="reports-search" theme="simple">
    <n:group2 titleText="Criteria" cssIcon="group-icon-search">

        <div style="padding:5px 0 0 0; height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Date From" labelJustify="right" tooltipGapHidden="true"
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

        <div style="height:26px;">
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Report Type" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssContainerStyle="padding-left:30%;"
                              cssLabelStyle="float:left;width:40%;"
                              cssCtrlStyle="float:left;width:60%;">
                        <s:select list="reportTypes" name="searchCriteria.reportType"
                                  theme="simple" cssStyle="width:150px;" listValue="name" />
                    </n:field2>
                </div>
            </div>
            <div style="float:left; width:50%">
                <div>
                    <n:field2 label="Report Granulation" labelJustify="right" tooltipGapHidden="true"
                              fieldEditable="true"
                              cssLabelStyle="float:left;width:25%;"
                              cssCtrlStyle="float:left;width:75%;">
                        <s:select list="reportTimeGranulation" name="searchCriteria.reportTimeGranulation"
                                  theme="simple" cssStyle="width:150px;"/>
                    </n:field2>
                </div>
            </div>
        </div>

        <div style="height:26px;">
            <div style="float:left; width:100%">
                <div>
                    <n:field label="Hostmaster"
                             hideTooltipGap="true"
                             cssContainer="field-class-container"
                             cssLabel="field-class-label"
                             cssCtrl="field-class-ctrl">
                        <input type="text" name="searchCriteria.hostmasterName" id="hostmasterName" style="width: 133px;"/>
                        <div class="window-icon">
                            <a href="#" id="openSearchDialoghostmasterName">
                                <img src="images/skin-default/action-icons/window.png" title="Select"/>
                            </a>
                        </div>
                    </n:field>
                </div>
            </div>
        </div>

        <div style="clear:both;">
            <n:refreshsearch/>
        </div>
    </n:group2>
</s:form>

<n:group2 titleText="Results">
    <display:table name="paginatedResult" id="reportsRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external" excludedParams="resetSearch" export="true">
        <display:column title="Hostmaster" property="hostmasterName" sortable="true"/>
        <s:if test="isNotDocumentsLoggedSearch()">
            <display:column title="Ticket Revisions" property="ticketRevisionsCount" sortable="true"/>
            <display:column title="Ticket Type" property="ticketType" sortable="true"/>
        </s:if>
        <s:if test="isNotTicketRevisionsSearch()">
            <display:column title="Documents" property="documentsCount" sortable="true"/>
        </s:if>
        <display:column title="Date" sortable="true" sortProperty="reportForDate">
            <s:date name="#attr.reportsRow.reportForDate" format="%{getText(datePattern)}" />
        </display:column>
        <display:setProperty name="export.excel.filename" value="hostmaster_usage.xls"/>
        <display:setProperty name="export.csv.filename" value="hostmaster_usage.csv"/>
        <display:setProperty name="export.xml.filename" value="hostmaster_usage.xml"/>
    </display:table>
</n:group2>

<ticket:contacts-search fieldId="hostmasterName"/>
</body>
</html>