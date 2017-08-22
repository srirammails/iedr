<%@ attribute name="domain" required="true" type="pl.nask.crs.app.domains.wrappers.DomainWrapper" %>
<%@ attribute name="holderTypes" required="true" type="java.util.List" %>
<%@ attribute name="formAction" required="true" type="java.lang.String" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    String dialogId = "changeHolderTypeDialog" + domain.getName();
    dialogId = dialogId.replaceAll("\\.", "");
    String openTriggerId = "openChangeHolderTypeDialog";
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
        valid = validateEmpty(theForm.holderType_hostmastersRemark, document.getElementById("holderType_hostmastersRemark_fielderror")) && valid;
        valid = validateSelected(theForm.holderType_type, document.getElementById("holderType_type_fielderror")) && valid;
        return valid;
    }
</script>
    <div id="<%=dialogId%>" class="jqmWindow">
        <n:group2 titleText="Holder Type" cssIcon="group-icon-status">

            <form action="${formAction}" method="post"
                  onsubmit="return validateFormOnSubmit(this)">

                <center style="margin: 10px 0">
                    Changing Holder Type of ${domain.name}
                </center>

                <input type="hidden" name="domainName" value="${domain.name}"/>
                <c:set var="newHolderType" />

                <!--<center>-->
                <s:if test="#attr.holderTypes.size() != 0">
                    <div class="ctrl-container" style="width:70%;height:5em;margin-left:10%;">
                        <div class="ctrl-label force-">New holder type:</div>
                        <div class="ctrl-field" style="width:50%;">
                            <s:select id="holderType_type" list="#attr.holderTypes" name="newHolderType" theme="simple"
                                      value="#attr.currentHolderType" size="3"/>
                        </div>
                    </div>
                    <div class="invalid-field" id="holderType_type_fielderror" style="display:none;">
                        <div class="invalid-field-icon"></div>
                        <div class="invalid-field-description"> You must select a new type</div>
                    </div>
                    <div style="clear:both; height:5px;">&nbsp;</div>

                    <div class="ctrl-container" style="width:70%;height:8em;margin-left:10%">
                        <div class="ctrl-label">Hostmaster's remark:</div>
                        <div class="ctrl-field" style="width:50%;">
                            <textarea id="holderType_hostmastersRemark" name="hostmastersRemark" rows="2"
                                      cols="20">Change Holder Type</textarea>
                        </div>
                    </div>
                    <div class="invalid-field" id="holderType_hostmastersRemark_fielderror" style="display:none;">
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
                        Holder type cannot be changed.
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

