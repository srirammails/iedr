<%@ attribute name="domain" required="true" type="pl.nask.crs.app.domains.wrappers.DomainWrapper" %>
<%@ attribute name="wipo" required="true" type="java.lang.Boolean" %>
<%@ attribute name="formAction" required="true" type="java.lang.String" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    String dialogId = "changeWipoDialog" + domain.getName();
    dialogId = dialogId.replaceAll("\\.", "");
    String openTriggerId = "openChangeWipoDialog";
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

    function validateFormOnSubmit(theForm) {
        var valid = validateEmpty(theForm.holderType_hostmastersRemark, document.getElementById("wipo_hostmastersRemark_fielderror"));        
        return valid;
    }
</script>
    <div id="<%=dialogId%>" class="jqmWindow">
        <n:group2 titleText="Holder Type" cssIcon="group-icon-status">

            <form action="${formAction}" method="post"
                  onsubmit="return validateFormOnSubmit(this)">

                <center style="margin: 10px 0">
                	<s:if test="#attr.wipo">                		
                    	Exiting WIPO, ${domain.name}
                    </s:if>
                    <s:else>
                    	Entering WIPO, ${domain.name}
                    </s:else>
                </center>

                <input type="hidden" name="domainName" value="${domain.name}"/>

                <!--<center>-->
                
                    <div class="ctrl-container" style="width:70%;height:8em;margin-left:10%">
                        <div class="ctrl-label">Hostmaster's remark:</div>
                        <div class="ctrl-field" style="width:50%;">
                            <textarea id="wipo_hostmastersRemark" name="hostmastersRemark" rows="2"
                                      cols="20">Changing WIPO status</textarea>
                        </div>
                    </div>
                    <div class="invalid-field" id="wipo_hostmastersRemark_fielderror" style="display:none;">
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
            </form>
        </n:group2>
    </div>

