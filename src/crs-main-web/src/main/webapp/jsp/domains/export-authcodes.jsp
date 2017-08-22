<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<s:set var="domains" value="paginatedResult.list"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Export Authcodes</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jqModal.js"></script>
    <s:head/>
    <sx:head/>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>
<s:actionmessage/>
<s:form action="domainExportAuthCodes-search" theme="simple">
<n:group2 titleText="Criteria" cssIcon="group-icon-search">

<div style="padding:5px 0; height:26px;">
    <div style="float:left; width:50%">
        <div>
            <n:field2 label="Domain name" labelJustify="right" tooltipGapHidden="true"
                         fieldEditable="true"
                         cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:70%;">
                <s:textfield name="searchCriteria.domainName" theme="simple" cssStyle="width:150px;text-indent:0;"/>
            </n:field2>
        </div>
    </div>
    <div style="float:left; width:50%">
        <div>
            <n:field2 label="Nic Handle" labelJustify="right" tooltipGapHidden="true"
                         fieldEditable="true"
                         cssLabelStyle="float:left;width:30%;" cssCtrlStyle="float:left;width:70%;">
                <s:textfield name="searchCriteria.nicHandle" theme="simple" cssStyle="width:150px;text-indent:0;"/>
            </n:field2>
        </div>
    </div>
</div>
<div style="clear:both;">
    <n:refreshsearch/>
</div>
</n:group2>
</s:form>

<s:form action="domainExportAuthCodes-export" theme="simple">
<n:group2 titleText="Selected domains" cssIcon="group-icon-search">
<ul id="selected_for_export"></ul>
<center>
    <input type="button" value="Clear" id="do_clear" />
    <s:submit id="do_export_authcodes" value="Export"/>
</center>
</n:group2>
</s:form>

<n:group2 titleText="Result">
    <display:table name="paginatedResult" id="domainRow" class="result"
                   requestURI="" decorator="pl.nask.crs.web.displaytag.TicketTableDecorator"
                   sort="external" excludedParams="resetSearch" export="true">
        <display:column title="Domain Name" property="name" paramId="domainName" sortable="true"/>
        <display:column title="BillC NH" property="billingContact.nicHandle" sortable="false"/>
        <display:column title="Authcode" property="authCode" sortable="false"/>
        <display:column title="">
            <input
                class="export_button"
                data-domain="${attr.domainRow.name}"
                type="button"
                value="Add to export"
                style="font-size:1em; height:2em; width: 100px">
        </display:column>
    </display:table>
</n:group2>

<style>
ul#selected_for_export {
    padding: 0px;
    list-style: none outside none;
}
ul#selected_for_export li {
    display: inline;
    margin: 5px;
    background: none repeat scroll 0px 0px rgba(0, 120, 0, 0.07);
    border-radius: 15px;
    padding: 3px 4px 3px 8px;
    border: 1px solid rgba(0, 120, 0, 0.4);
    list-style: none outside none;
}
ul#selected_for_export li a {
    color: rgba(200, 0, 0, 0.5);
    height: 15px;
    padding: 0 4px;
    width: 15px;
}
ul#selected_for_export li:hover a {
    background: none repeat scroll 0 0 rgba(200, 0, 0, 1);
    border-radius: 10px;
    color: white;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
    var setupButtons = function() {
        var selected_count = $('#selected_for_export li').size();
        if (selected_count > 0) {
            $("#do_export_authcodes").attr("disabled", false);
            $("#do_clear").attr("disabled", false);
        } else {
            $("#do_export_authcodes").attr("disabled", true);
            $("#do_clear").attr("disabled", true);
        }
    };

    var createSelectedDomain = function(domainName) {
        $('input[type=button][data-domain="'+domainName+'"]').attr("disabled", true);
        $('#selected_for_export').append(
            $("<li></li>").attr("data-domain", domainName).text(domainName).append(
                $('<a title="remove">X</a>').click(function() {
                    $.ajax({
                        url  : "domainExportAuthCodes-removeFromSelected.action",
                        data : {selectedDomain : domainName},
                        complete : function() {
                            removeSelectedDomain(domainName);
                        }
                    });
                })
            )
        );
        setupButtons();
    };

    var removeSelectedDomain = function(domainName) {
        $('input[type=button][data-domain="'+domainName+'"]').attr("disabled", false);
        $('#selected_for_export li[data-domain="'+domainName+'"]').remove();
        setupButtons();
    };

    var selectedDomains = $.grep("${allSelectedAsString}".split(","), function(e) { return e; } );
    $.each(selectedDomains, function(i, domain) {
        createSelectedDomain(domain);
    });
    setupButtons();

    $('input.export_button').click(function(event) {
        event.preventDefault();
        var domainName = $(this).attr('data-domain');
        $.ajax({
            url  : "domainExportAuthCodes-addToSelected.action",
            data : {selectedDomain : domainName},
            complete : function() {
                createSelectedDomain(domainName);
            }
        });
    });

    $('#do_clear').click(function(event) {
        event.preventDefault();
        $.ajax({
            url  : "domainExportAuthCodes-clearSelected.action",
            complete : function() {
                $('input.export_button[type=button]').attr("disabled", false);
                $('#selected_for_export li').remove();
                setupButtons();
            }
        });
    });
});
</script>
</body>
</html>