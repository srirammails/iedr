<%@ attribute name="nicHandle" required="true" type="pl.nask.crs.nichandle.NicHandle" %>
<%@ attribute name="statuses" required="true" type="java.util.List" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>

<%
    String dialogId = "alterStatusDialog" + nicHandle.getNicHandleId();
	String openTriggerId = "openAlterStatusDialog";
%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#<%=dialogId%>').jqm({trigger: false})
				  .jqmAddTrigger('#<%=openTriggerId%>')
				  .jqmAddClose($('.closeDialog'));
	})
	

	function showError(flderr, show) {
		if (show) {
			flderr.style.display="";
			return false;
		} else {
			flderr.style.display="none";
			return true;
		}	
	}
	
	function validateEmpty(fld, flderr) {	
		return showError(flderr, fld.value.length == 0); 		
	}

	function validateSelected(fld, flderr) {
		return showError(flderr, fld.selectedIndex==-1);
			
	}

	function validateFormOnSubmit(theForm) {
		var valid = true;

		valid =  validateEmpty(theForm.alterStatus_hostmastersRemark, document.getElementById("alterStatus_hostmastersRemark_fielderror")) && valid;
		valid =  validateSelected(theForm.alterStatus_status, document.getElementById("alterStatus_status_fielderror")) && valid ;	
		return valid;
	}
</script>
<div id="<%=dialogId%>" class="jqmWindow">
	<n:group2 titleText="Alter Status" cssIcon="group-icon-status">

        <!--todo NicHandleBrowserAction is deprecated-->
        <form action="nic-handle-browser-alterStatus.action" method="post" onsubmit="return validateFormOnSubmit(this)">

			<center style="margin: 10px 0">
				Altering the status of the nic handle ${nicHandle.nicHandleId}.
			</center>

			<input type="hidden" name="nicHandleId" value="${nicHandle.nicHandleId}"/>
			<c:set var="status" value="${nicHandle.status}"/>

			  <div class="ctrl-container" style="width:70%;height:5em;margin-left:10%;">
					<div class="ctrl-label force-">New status:</div>
					<div class="ctrl-field" style="width:50%; overflow: hidden;">
						<s:select id="alterStatus_status" list="statuses" name="newStatus" theme="simple" size="3" value="#attr.nicHandle.status" cssStyle="width: 110%;border: 0px;"/>
					</div>
				</div>
				<div class="invalid-field" id="alterStatus_status_fielderror" style="display:none;">
					<div class="invalid-field-icon"> </div>
					<div class="invalid-field-description"> You must select an new status</div>
				</div>
				<div style="clear:both; height:5px;">&nbsp;</div>
				
				<div class="ctrl-container" style="width:70%;height:8em;margin-left:10%">
					<div class="ctrl-label">Hostmaster's remark:</div>
					<div class="ctrl-field" style="width:50%;">
						<textarea id="alterStatus_hostmastersRemark" name="hostmastersRemark" rows="2" cols="20" ></textarea>
					</div>
				</div>
				<div class="invalid-field" id="alterStatus_hostmastersRemark_fielderror" style="display:none;">
					<div class="invalid-field-icon"> </div>
					<div class="invalid-field-description"> You must enter a value for Hostmasters's Remark </div>
				</div>

			<div style="clear:both; height:0;">&nbsp;</div>

			<hr class="buttons"/>

			<center>
				<input type="Submit" value="Submit"/>
				<input type="button" value="Cancel" class="closeDialog"/>
			</center>
		</form>
	</n:group2>
</div>


<%--<div id="<%=dialogId%>" class="jqmWindow">--%>
	<%--<n:group2 titleText="Alter Status" cssIcon="group-icon-status">--%>

		<%--<form action="nic-handle-browser-alterStatus.action" method="post">--%>
			<%--<center style="margin: 10px 0">--%>
				<%--Altering the status of the nic handle ${nicHandle.nicHandleId}.--%>
			<%--</center>--%>
			<%--<input type="hidden" name="nicHandleId" value="${nicHandle.nicHandleId}"/>--%>
			<%--<c:set var="status" value="${nicHandle.status}"/>--%>
			<%--<center>--%>
				<%--New status: <s:select list="statuses" name="newStatus" theme="simple" size="3" value="statuses.get(0)"/>--%>
            <%--</center>--%>
            <%--<center>--%>
                <%--Hostmaster's remark: <textarea name="hostmastersRemark" rows="2" cols="20">Alter status.</textarea>--%>
            <%--</center>--%>
            <%--<hr class="buttons"/>--%>
			<%--<center>--%>
				<%--<input type="Submit" value="Submit"/>--%>
				<%--<input type="button" value="Cancel" class="closeDialog"/>--%>
			<%--</center>--%>
		<%--</form>--%>
	<%--</n:group2>--%>
<%--</div>--%>
