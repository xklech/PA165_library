<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:layout-definition>
<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
    <head>
	<title><f:message key="${titlekey}"/></title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css" />
	<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="${pageContext.request.contextPath}/js/js.js"></script>
	<script src="${pageContext.request.contextPath}/js/<f:message key="datepicker.localization.file" />"></script>
	<s:layout-component name="header"/>
    </head>
    <body>
	<div id="wrapper">
	    <div id="headerWrapper">
		<div id="header"> 
		    <h1>
			<a href="index.html" title="Home page"><f:message key="library.header" /></a>
		    </h1>
		</div>
	    </div>
	    <div id="contentWrapper">
		<nav>
		    <s:link href="/index.jsp" title="${navigation.customers}"><f:message key="navigation.customers"/></s:link>
		    <s:link href="/index.jsp" title="${navigation.customers}"><f:message key="navigation.customers"/></s:link>
		    <s:link beanclass="cz.muni.fi.pa165.library.BooksActionBean"><f:message key="navigation.books"/></s:link>
		    <s:link href="/loan.jsp" title="${navigation.impressions}"><f:message key="navigation.impressions"/></s:link>
		</nav>
		<div id="content">   
		    <h1>
			<f:message key="${titlekey}" />
		    </h1>
		    <s:messages/>
		    <s:messages key="error"/>
		    <s:layout-component name="body"/>
		</div>
	    </div>
	    <div id="footerWrapper">
		<div id="footer">
		    Copyright &copy 2013 fi.muni.cz
		</div>
	    </div>
	</div>
    </body>
</html>
</s:layout-definition>