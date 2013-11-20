<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" titlekey="impression.edit.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.library.ImpressionActionBean" var="actionBean"/>

        <s:form beanclass="cz.muni.fi.pa165.library.ImpressionActionBean">
            <fieldset><legend><f:message key="impression.edit.edit"/></legend>
                <%@include file="form.jsp"%>
                <s:hidden name="book.id" value="${actionBean.book.id}" />
                <s:hidden name="impression.id" value="${actionBean.impression.id}" />
                <s:submit name="save"><f:message key="impression.edit.save"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>