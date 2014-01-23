<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">
        <br />
        <p>
            <sec:authorize access="!isAuthenticated()">
                <f:message key="index.welcome" />
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <f:message key="index.logedInAs" /> <sec:authentication property="principal.username" />
            </sec:authorize>
        </p>
        <br />
    </s:layout-component>
</s:layout-render>
