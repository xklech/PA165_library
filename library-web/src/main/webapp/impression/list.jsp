<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="impression.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.library.ImpressionActionBean" var="actionBean"/>

        <h2><f:message key="impression.list.header" var="actionBean.book.name"/></h2>
        <s:errors globalErrorsOnly="true"/>
        <table class="table_display w100">
            <tr>
                <td class="c b w5">id</td>
                <td class="c b w20"><f:message key="impression.initialDamage"/></td>
                <td class="c b w25"><f:message key="impression.damage"/></td>
                <td class="c b w15"><f:message key="impression.status"/></td>
                <td class="c b w20" colspan="2"><f:message key="common.action"/></td>
            </tr>
            <c:forEach items="${actionBean.impressions}" var="impression">
                <tr>
                    <td class="w10">${impression.id}</td>
                    <td class="w20"><f:message key="DamageType.${impression.initialDamage}"/></td>
                    <td class="w25"><f:message key="DamageType.${impression.damage}"/></td>
                    <td class="w15"><f:message key="StatusType.${impression.status}"/></td>
                    <td class="w10">
                        <s:link beanclass="cz.muni.fi.pa165.library.ImpressionActionBean" event="edit"><s:param name="book.id" value="${actionBean.book.id}"/><s:param name="impression.id" value="${impression.id}"/><f:message key="common.edit" /></s:link>
                    </td>
                    <td class="w10">
                        <s:form beanclass="cz.muni.fi.pa165.library.ImpressionActionBean">
                            <s:hidden name="book.id" value="${actionBean.book.id}"/>
                            <s:hidden name="impression.id" value="${impression.id}"/>
                            <s:submit name="delete"><f:message key="common.delete"/></s:submit>
                        </s:form>
                    </td>
                </tr>
            </c:forEach>
        </table>
            
        <a href="javascript:showHide('impression_add')" title="<f:message key="impression.list.newbook" />"><h2><f:message key="impression.list.newbook" /></h2></a>
        <div id="impression_add" style="display:none">
            <s:form beanclass="cz.muni.fi.pa165.library.ImpressionActionBean">
                <fieldset><legend><f:message key="impression.list.newbook"/></legend>
                    <%@include file="form.jsp"%>
                    <s:hidden name="book.id" value="${actionBean.book.id}" />
                    <s:submit name="add"><f:message key="impression.submit.add"/></s:submit>
                </fieldset>
            </s:form>
        </div>
        
    </s:layout-component>
</s:layout-render>