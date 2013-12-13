<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="customer.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.library.CustomersActionBean" var="actionBean"/>

        <h2><f:message key="customer.list.find"/></h2>
        <s:errors globalErrorsOnly="true"/>
        <table class="table_display w100">
            <tr>
                <td class="c b w5">id</td>
                <td class="c b w15"><f:message key="customer.firstName"/></td>
                <td class="c b w15"><f:message key="customer.lastName"/></td>
                <td class="c b w20"><f:message key="customer.address"/></td>
                <td class="c b w20"><f:message key="customer.dateOfBirth"/></td>
                <td class="c b w20"><f:message key="customer.pid"/></td>
                <td class="c b w5"><f:message key="common.action"/></td>
            </tr>
            <c:forEach items="${actionBean.customers}" var="customer">
                <tr>
                    <td class="w5">${customer.id}</td>
                    <td class="w15"><c:out value="${customer.firstName}"/></td>
                    <td class="w15"><c:out value="${customer.lastName}"/></td>
                    <td class="w20"><c:out value="${customer.address}"/></td>
                    <td class="w20"><c:out value="${customer.dateOfBirth}"/></td>
                    <td class="w20"><c:out value="${customer.pid}"/></td>
                    <td class="w5">
                        <s:link beanclass="cz.muni.fi.pa165.library.CustomersActionBean" event="edit"><s:param name="customer.id" value="${customer.id}"/><f:message key="common.edit" /></s:link>
                        <s:link beanclass="cz.muni.fi.pa165.library.CustomersActionBean" event="delete"><s:param name="customer.id" value="${customer.id}"/><f:message key="common.delete" /></s:link>
                    </td>
                </tr>
            </c:forEach>
        </table>
            
        
    </s:layout-component>
</s:layout-render>