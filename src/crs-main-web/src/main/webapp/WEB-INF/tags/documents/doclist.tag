<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ attribute name="disableFromColumn" type="java.lang.Boolean" %>
<%@ attribute name="disablePurposeColumn" type="java.lang.Boolean" %>
<%@ attribute name="allowSkippingPagination" type="java.lang.Boolean" %>
<%@ attribute name="showFilename" type="java.lang.Boolean" description="Show filename instead of domain names. Default is false."%>
<n:group2 titleText="Results" cssIcon="group-icon-document">
    <s:if test="%{#attr.allowSkippingPagination}">
        <s:if test="%{showAll}">
            <s:url var="viewUrl" action="documents-incoming" includeParams="none">
                <s:param name="showAll">false</s:param>
            </s:url>
            <s:a href="%{viewUrl}" cssStyle="float:right">
                Show paginated
            </s:a>
        </s:if>
        <s:else>
            <s:url var="viewUrl" action="documents-incoming" includeParams="none">
                <s:param name="showAll">true</s:param>
            </s:url>
            <s:a href="%{viewUrl}" cssStyle="float:right">
                Show all
            </s:a>
        </s:else>
    </s:if>
    <display:table name="paginatedResult" id="documentRow" class="result" requestURI="" sort="external" excludedParams="resetSearch">
        <display:column title="Type" class="documents-type" sortable="true" sortProperty="docType">
            <s:set name="fType" value="#attr.documentRow.documentFile.fileType"/>
            <s:if test="#fType.isFaxType()">
                <img src="images/skin-default/action-icons/document.fax.png" alt="Fax" title="Fax"/>
            </s:if>
            <s:elseif test="#fType.isAttachmentType()">
                <img src="images/skin-default/action-icons/document.attachment.png" alt="Attachment" title="Attachment"/>
            </s:elseif>
            <s:else>
                <img src="images/skin-default/action-icons/document.paper.png" alt="Paper" title="Paper"/>
            </s:else>
        </display:column>
        
        <s:if test="%{#attr.showFilename==null || !#attr.showFilename}">
        	<display:column title="Domain names" class="documents-name" property="domainsAsString"  sortable="true" sortProperty="domain_name"/>
        </s:if>		        	                    
        <s:else>
			<s:set name="fName" value="#attr.documentRow.documentFile.fileName" />
			<s:if test="%{#fName == null || #fName == ''}">
				<display:column title="Name" class="documents-name"
					value="Unknown file name" />
			</s:if>
			<s:else>
				<display:column title="Name" class="documents-name"
					property="documentFile.fileName" />
			</s:else>
		</s:else>
        <display:column>
            <s:set name="fName" value="#attr.documentRow.documentFile.fileName"/>
            <s:set name="fType" value="#attr.documentRow.documentFile.fileType"/>
            <s:set name="fId" value="#attr.documentRow.id"/>
            <s:if test="%{#fId != null}">
                <s:url var="documentEdit" action="documents-view-update">
                    <s:param name="document.id" value="%{#fId}"/>
                    <s:param name="document.fileName" value="%{#fName}"/>
                    <s:param name="document.fileType" value="%{#fType}"/>
                </s:url>
                <s:a href="%{documentEdit}">
                    <img src="images/skin-default/action-icons/ticket.revise.png" alt="Edit domain names" title="Edit domain names"/>
                </s:a>
            </s:if>
        </display:column>
        <s:if test="%{#attr.disableFromColumn==null || !#attr.disableFromColumn}">
        <display:column title="From" property="docSource" sortable="true" sortProperty="docSource"/>
        </s:if>
        <s:if test="%{#attr.disablePurposeColumn==null || !#attr.disablePurposeColumn}">
        <display:column title="Purpose" property="docPurpose.value" sortable="true" sortProperty="docPurpose"/>
        </s:if>
        <display:column title="Date" sortable="true" sortProperty="date">
            <s:date name="#attr.documentRow.date"/>
        </display:column>
        <display:column>
            <s:set name="fName" value="#attr.documentRow.documentFile.fileName"/>
            <s:set name="fType" value="#attr.documentRow.documentFile.fileType"/>
            <s:set name="fId" value="#attr.documentRow.id"/>
            <s:if test="%{#fId == null}">
                <s:url var="documentView" action="documents-view-new">
                    <s:param name="document.id" value="%{#fId}"/>
                    <s:param name="document.fileName" value="%{#fName}"/>
                    <s:param name="document.fileType" value="%{#fType}"/>
                </s:url>
            </s:if>
            <s:else>
                <s:url var="documentView" action="documents-view-assigned">
                    <s:param name="document.id" value="%{#fId}"/>
                    <s:param name="document.fileName" value="%{#fName}"/>
                    <s:param name="document.fileType" value="%{#fType}"/>
                </s:url>
            </s:else>
            <s:a href="%{documentView}">
				<img src="images/skin-default/action-icons/document.paper.png" alt="View" title="View"/>
			</s:a>
        </display:column>
    </display:table>

</n:group2>