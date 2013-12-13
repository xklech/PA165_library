<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:layout-definition>
<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
    <head>
	<title><f:message key="${titlekey}"/></title>
	<script src="${pageContext.request.contextPath}/plugins/jquery/jquery-2.0.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js"></script>	
	<script src="${pageContext.request.contextPath}/plugins/jquery/<f:message key="datepicker.localization.file" />"></script>
	<script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.min.js"></script>	
	<script src="${pageContext.request.contextPath}/plugins/javascript/main.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap-theme.min.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css" />
	<s:layout-component name="header"/>
    </head>
    <body>
	<div id="wrapper">
	    <div id="headerWrapper">
		<div id="header"> 
		    <h1>
			<a href="${pageContext.request.contextPath}" title="Home page"><f:message key="library.header" /></a>
		    </h1>
		</div>
	    </div>
	    <div id="contentWrapper">
		<nav>
		    <s:link beanclass="cz.muni.fi.pa165.library.CustomersActionBean"><f:message key="navigation.customers"/></s:link>
		    <s:link beanclass="cz.muni.fi.pa165.library.LoansActionBean"><f:message key="navigation.loans"/></s:link>
		    <s:link beanclass="cz.muni.fi.pa165.library.BooksActionBean"><f:message key="navigation.books"/></s:link>
		</nav>
		<div id="content" class="container">   
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
		    Copyright &copy; 2013 fi.muni.cz
		</div>
	    </div>
	</div>
    </body>
</html>
</s:layout-definition>