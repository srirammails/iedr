	<@s.url var="getResponses" namespace="/dojo" action="template-input"/>
	<@sx.div id="responsesListDiv"
			 href="%{getResponses}" listenTopics="/refresh" 
			 loadingText="Loading..." 			 
			 executeScripts="true"
			 separateScripts="true"
			 />
<script type="text/javascript">

    function responseSelected(index) {
        var messageTemplates = document.getElementById("messageTemplates");
        var hostmasterMessage = document.getElementById("hostmasterMessage");
        var newMessageTitle = document.getElementById("newResponseTitle");
        var id = messageTemplates.options[index].value;
        changeSaveButton(id == -1);
        var contentHidden = document.getElementById("id"+id);
        if (contentHidden) {
            hostmasterMessage.value = contentHidden.value;
        }
        newMessageTitle.value = messageTemplates.options[index].text;
    }

    function changeSaveButton(isNewMessage) {
        var saveMessageDiv = document.getElementById("saveMessageDiv")
        var newMessageDiv = document.getElementById("newMessageDiv")

        if (isNewMessage) {
            saveMessageDiv.style.display="none"
            newMessageDiv.style.display="block"
        } else {
            saveMessageDiv.style.display="block"
            newMessageDiv.style.display="none"
        }
    }
</script>


