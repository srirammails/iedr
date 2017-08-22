<!DOCTYPE html PUBLIC
"-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<n:skin>
		<link rel="icon"
				type="image/png"
				href="images/web-parts/favicon.png">
	</n:skin>

	<title><decorator:title default="Struts Starter"/></title>

	<n:detectbrowser/>
	<%--<n:detectspecial/>--%>

	<script type="text/javascript" src="js/calendar.js"></script>
	<script type="text/javascript" src="js/calendar-en.js"></script>
	<script type="text/javascript" src="js/calendar-setup.js"></script>

	<!-- IE hover -->
	<!--[if lte IE 7]>
				<script type="text/javascript" src="js/hoverIE.js"></script>
		  <![endif]-->

	<decorator:head/>
</head>
<body id="page-home">
<div id="page">

<div id="header">
	<%--
			<table width="100%" cellpadding="0" cellspacing="0">
				 <tr>
					  <td id="header-image-upper">&nbsp;</td>
					  <td id="header-main">
							<div id="header-title">
								 <table width="100%" cellpadding="0" cellspacing="0">
									  <tr>
											<td class="lu-corner-h1">&nbsp;</td>
											<td class="up-corner-h1"><p><decorator:title/></p></td>
											<td class="ru-corner-h1">&nbsp;</td>
									  </tr>
								 </table>
							</div>
					  </td>
				 </tr>
			</table>
	 --%>

	<%-- temp changes for iedr skin --%>
	<div class="HM">
		<div class="HL">
			<div class="HLL">
				<span style="position:absolute;left:80px;top:12px;">CORE</span>
				<span style="position:absolute;left:80px;top:24px;">REGISTRY</span>
				<span style="position:absolute;left:80px;top:36px;">SYSTEM</span>
			</div>
			<div class="HLS">
			</div>
		</div>
		<div class="HR">
			<div class="HRL">

				<div class="loginInfo">
					<s:if test="#session.get('user-key').username!=null">
						<s:label name="'Logged in as: '" theme="simple"/>
						<s:url var="viewNicHandle" action="nic-handle-view" includeParams="none">
							<s:param name="nicHandleId" value="#session.get('user-key').username" />
							<s:param name="previousAction">home</s:param>
						</s:url>

						<s:set var="userName" value="#session.get('user-key').username"/>
						<s:a href="%{viewNicHandle}" cssStyle="color:white;font-weight:bold;" theme="simple">${userName}</s:a>
						<s:label name="' | '" theme="simple"/>
						<s:a href="log-out.action" cssStyle="color:white;" theme="simple">Logout</s:a>
					</s:if>
				</div>

			</div>
			<div class="HRT">
				<p><decorator:title/></p>
			</div>
		</div>
	</div>
</div>

<div id="content">
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td id="nav-panel">
				<div id="nav">
					<!--<div id="header-image-lower">&nbsp;</div>-->

					<n:group2 titleText="Menu">
						<n:skin>
							<menu:useMenuDisplayer name="Simple" permissions="menu-key">
								<menu:displayMenu name="main"/>
							</menu:useMenuDisplayer>
						</n:skin>
					</n:group2>

					<%--
					  <div class="group">
							<div class="group-content nav-group-content">
								 <menu:useMenuDisplayer name="Simple" permissions="menu-key">
									 <menu:displayMenu name="main-simple"/>
								 </menu:useMenuDisplayer>
							</div>
					  </div>
				  --%>
			</td>
			<td id="main-panel">
				<div id="main">
					<decorator:body/>
				</div>
			</td>
		</tr>
	</table>
</div>

<n:footer/>

</div>
</body>
</html>
