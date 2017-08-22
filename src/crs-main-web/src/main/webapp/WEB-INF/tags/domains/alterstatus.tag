<%@ attribute name="domain" required="true" type="pl.nask.crs.app.domains.wrappers.DomainWrapper" %>
<%@ attribute name="domainStatuses" required="true" type="java.util.List" %>
<%@ attribute name="formAction" required="true" type="java.lang.String" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    String dialogId = "alterStatusDialog" + domain.getName();
    dialogId = dialogId.replaceAll("\\.", "");
    String openTriggerId = "openAlterStatusDialog";
    openTriggerId = openTriggerId.replaceAll("\\.", "");
%>
<script type="text/javascript">
    $(document).ready(function() {
        $('#<%=dialogId%>').jqm({trigger: false})
                .jqmAddTrigger('#<%=openTriggerId%>')
                .jqmAddClose($('.closeDialog'));
    })


    function showError(flderr, show) {
        if (show) {
            flderr.style.display = "";
            return false;
        } else {
            flderr.style.display = "none";
            return true;
        }
    }

    function validateEmpty(fld, flderr) {
        return showError(flderr, fld.value.length == 0);
    }

    function validateSelected(fld, flderr) {
        return showError(flderr, fld.selectedIndex == -1);
    }

    function validateFormOnSubmit(theForm) {
        var valid = true;
        valid = validateEmpty(theForm.alterStatus_hostmastersRemark, document.getElementById("alterStatus_hostmastersRemark_fielderror")) && valid;
        valid = validateSelected(theForm.alterStatus_status, document.getElementById("alterStatus_status_fielderror")) && valid;
        return valid;
    }
</script>
    <div id="<%=dialogId%>" class="jqmWindow">
        <n:group2 titleText="Alter Status" cssIcon="group-icon-status">

            <form action="${formAction}" method="post"
                  onsubmit="return validateFormOnSubmit(this)">

                <center style="margin: 10px 0">
                    Altering the status of the domain ${domain.name}
                </center>

                <input type="hidden" name="domainName" value="${domain.name}"/>
                <c:set var="domainStatus" />

                <!--<center>-->
                <s:if test="#attr.domainStatuses.size() != 0">
                    <div class="ctrl-container" style="width:70%;height:5em;margin-left:10%;">
                        <div class="ctrl-label force-">New status:</div>
                        <div class="ctrl-field" style="width:50%;">
                            <s:select id="alterStatus_status" list="#attr.domainStatuses" name="newStatus" theme="simple"
                                      value="#attr.domainStatus" size="3"/>
                        </div>
                    </div>
                    <div class="invalid-field" id="alterStatus_status_fielderror" style="display:none;">
                        <div class="invalid-field-icon"></div>
                        <div class="invalid-field-description"> You must select a new status</div>
                    </div>
                    <div style="clear:both; height:5px;">&nbsp;</div>

                    <div class="ctrl-container" style="width:70%;height:8em;margin-left:10%">
                        <div class="ctrl-label">Hostmaster's remark:</div>
                        <div class="ctrl-field" style="width:50%;">
                            <textarea id="alterStatus_hostmastersRemark" name="hostmastersRemark" rows="2"
                                      cols="20">Alter status.</textarea>
                        </div>
                    </div>
                    <div class="invalid-field" id="alterStatus_hostmastersRemark_fielderror" style="display:none;">
                        <div class="invalid-field-icon"></div>
                        <div class="invalid-field-description"> You must enter a value for Hostmasters's Remark</div>
                    </div>
                    <!--</center>-->

                    <div style="clear:both; height:0;">&nbsp;</div>
                    <hr class="buttons"/>

                    <center>
                        <input type="Submit" value="Submit"/>
                        <input type="button" value="Cancel" class="closeDialog"/>
                    </center>
                </s:if>
                <s:else>
                    <center style="margin: 10px 0">
                        Status cannot be changed.
                    </center>
                    <div style="clear:both; height:0;">&nbsp;</div>
                    <hr class="buttons"/>
                    <center>
                        <input type="button" value="Cancel" class="closeDialog"/>
                    </center>
                </s:else>
            </form>
        </n:group2>
    </div>

