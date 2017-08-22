<%@ taglib prefix="s" uri="/struts-tags" %>

<hr class="buttons"/>
<s:hidden name="tableParams.page" value="1"/>
<s:hidden name="resetSearch" value="true"/>
<center>
    <s:submit label="Search">
        <s:checkbox name="showAll"><label>Skip Pagination</label></s:checkbox>
    </s:submit>
</center>