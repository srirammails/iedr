<jsp:doBody var="theBody"/>

<%
	String skinName = "skin-iedr";
	String body = (String) jspContext.getAttribute("theBody");
	StringBuilder skinnedBody = new StringBuilder();
	String[] bodyParts;

	bodyParts = body.split("images/");

	int index = 0;
	int length = bodyParts.length;

	if (length == 0)
		return;

	while (index < length - 1) {
		skinnedBody.append(bodyParts[index]);
		skinnedBody.append("images/");

//		if (!bodyParts[index].endsWith(skinName + "/")) {
			skinnedBody.append(skinName);
			skinnedBody.append("/");
//		}
		index++;
	}
	skinnedBody.append(bodyParts[index]);
%>

<%= skinnedBody%>

