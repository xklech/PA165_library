<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-render name="/layout.jsp" titlekey="books.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.library.BooksActionBean" var="actionBean"/>
        <h2><f:message key="common.search" /></h2>
        <br /><br /><br /><br />
        <h2><f:message key="books.add" /></h2>

        <s:form beanclass="cz.muni.fi.pa165.library.BooksActionBean">
            <fieldset><legend><f:message key="books.list.newbook"/></legend>
                <%@include file="books/form.jsp"%>
                <s:submit name="add"><f:message key="books.submit.add" /></s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>
