<%@ attribute name="countryList" required="true" type="java.lang.String" rtexprvalue="false"
				  description="list of countries" %>
<%@ attribute name="countryField" required="true" type="java.lang.String" rtexprvalue="false"
				  description="name (path) of the field, where selected country is to be saved" %>
<%@ attribute name="countyField" required="true" type="java.lang.String" rtexprvalue="false"
				  description="name (path) of the field, where selected county is to be saved" %>


<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %> 
<%@tag import="java.util.List"%>
<%@tag import="pl.nask.crs.country.Country"%>

<%
	List<Country> countries = (List<Country>) jspContext.getVariableResolver().resolveVariable(countryList);
	jspContext.setAttribute("countries", countries);

	String county = (String) jspContext.getVariableResolver().resolveVariable(countyField);
	jspContext.setAttribute("countyValue", county);
	
	String country = (String) jspContext.getVariableResolver().resolveVariable(countryField);
	jspContext.setAttribute("countryValue", country);
	
	//find selected country		
	if(country != null) {
		for(Country c: countries) {
			if(country.equalsIgnoreCase(c.getName())) {			    
			    jspContext.setAttribute("countryCode", c.getCode());
			    jspContext.setAttribute("inputStyle", c.getCounties().size()>0?"display:none;" : "");
			    break;
			}
		}
	}
	//generate linked selects for supported countries
	
	
	
%>

<script type="text/javascript" language="javascript">
function updateCountyEdit(options, index) {
	var select_div = document.getElementById("countySelect_${countyField}_"+options[index].value);
	var select = null;
	if (select_div!=null) {		
		select = select_div.getElementsByTagName('select')[0];
	}
	var input_div = document.getElementById("countyValue_${countryField}");
	var input= document.getElementById("countyValue_${countryField}_input");
	var input_t= document.getElementById("countyValue_${countryField}_input_text");
	//ensure all selects are hidden!
	for(i=0; i<options.length; i++) {
		var sid = "countySelect_${countyField}_"+options[i].value;
		var _select = document.getElementById(sid);
		if(_select!=null) {
			_select.style.display = "none"
		}
	}
	
	
	input_div.style.display="none";
//	input.value="";
//	input_t.innerHTML = "";
	if (select_div != null) {
		select_div.style.display="";
		input_div.style.display="none";
		updateCountyValue(select);
	} else {
		input_div.style.display="";
		if(index!=0) { //header
			input.value="N/A";
			input_t.innerHTML = "N/A";
		}
	}
}

function updateCountryValue (select) {
	var i = select.selectedIndex;
	var option = select.options[i];
	var value = option.text;
	if(i==0)
		value="";
	document.getElementById("countryValue_${countryField}").value = value;
	//update county field
	updateCountyEdit(select.options, i);	
}

function updateCountyValue (select) {
	var i = select.selectedIndex;
	if (i==null) i=0;
	var option = select.options[i];
	var value = option.text;
	if(i==0)
		value="";
	document.getElementById("countyValue_${countryField}_input").value = value;
}
function updateCountyOnLoad(){
    var input= document.getElementById("countyValue_${countryField}_input");
    var input_t= document.getElementById("countyValue_${countryField}_input_text");
    var country = document.getElementById("countryValue_${countryField}");
    if((country.value == null || country.value == "") && (input.value == null || input.value == "")){
        input.value="N/A";
        input_t.innerHTML = "N/A";        
    }
}

</script>


<n:field label="County" fielderror="${countyField}">
<div id="countyValue_${countryField}" style="${inputStyle}">
<p id="countyValue_${countryField}_input_text">${countyValue}</p>
</div>
<s:iterator value="#attr.countries" var="country">
<s:if test="#attr.country.counties.size > 0">
<div id="countySelect_${countyField}_${country.code}" style="${country.code==countryCode ? "" : "display:none;" }">
<s:select cssClass="select_county" list="#country.counties" headerKey="" headerValue="(select county)" onchange="updateCountyValue(this)">
	<s:param name="value">${countyValue}</s:param>	
</s:select>	
</div>
</s:if>
</s:iterator>
<input id="countyValue_${countryField}_input" type="hidden" name="${countyField}" value="${countyValue}" />
</n:field>
<input type="hidden" id="countryValue_${countryField}" name="${countryField}" value="${countryValue}"/>
<n:field label="Country" fielderror="${countryField}">
<s:select  list="#attr.countries" listKey="code" listValue="name" headerKey="" headerValue="(select country)" onchange="updateCountryValue(this)">
	<s:param name="value">${countryCode}</s:param>
</s:select>
</n:field>
<script type="text/javascript">
    updateCountyOnLoad();
</script>