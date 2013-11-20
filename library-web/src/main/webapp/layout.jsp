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
  <s:layout-component name="header"/>
</head>
<body>
    <div id="page">
        <div id="header"> 
            <div id="header_title"> <a href="index.html" title="Home page"> <f:message key="library.header" /> </a> </div> 
        </div>
   
        <div id="menu">
            <s:link href="/index.jsp" title="${navigation.customers}"><div class="odkaz_prvni"><f:message key="navigation.customers"/></div></s:link>
            
            <s:link beanclass="cz.muni.fi.pa165.library.BooksActionBean"><div class="odkaz_druhy"><f:message key="navigation.books"/></div></s:link>
            <s:link href="/loan.jsp" title="${navigation.impressions}"><div class="odkaz_treti"><f:message key="navigation.impressions"/></div></s:link>
        </div>
        
        <div id="content">   
            <h1><f:message key="${titlekey}" /></h1>
            <s:messages/>
            <s:layout-component name="body"/>
        </div>
    </div>
</body>
</html>
</s:layout-definition>