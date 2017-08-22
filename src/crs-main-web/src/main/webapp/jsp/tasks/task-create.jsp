<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>
        Create Task
    </title>
</head>
<body>

<n:error2 cssIcon="group-icon-error" titleText="Error"/>

<n:group2 titleText="Create task" cssIcon="group-icon-document">
    <s:form theme="simple">
        <center>
            <n:field label="Name" cssContainer="force-margin-left30 force-width30">
                <s:select list="validTaskNames" name="taskWrapper.name" />        
            </n:field>
            <n:fielderror fields="taskWrapper.name" colspan="1"/>
            <n:field label="Schedule" cssContainer="force-margin-left30 force-width30">
                <s:textfield name="taskWrapper.schedule"/>
            </n:field>
            <n:fielderror fields="taskWrapper.schedule" colspan="1"/>
        </center>
        <hr class="buttons"/>
        <center>
            <input type="button" value="Back" onClick="history.back()">
            <s:submit value="Create" theme="simple" action="task-create"/>
        </center>
    </s:form>
</n:group2>
</body>
</html>
