<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<s:layout-render name="/layout.jsp" titlekey="books.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.library.BooksActionBean" var="actionBean"/>

        <h2><f:message key="books.list.find"/></h2>
        <s:errors globalErrorsOnly="true"/>
        <table class="table_display w100">
            <tr>
                <td class="c b w5">id</td>
                <td class="c b w20"><f:message key="book.author"/></td>
                <td class="c b w25"><f:message key="book.name"/></td>
                <td class="c b w15"><f:message key="book.publishDate"/></td>
                <td class="c b w15"><f:message key="book.department"/></td>
		<sec:authorize access="isAuthenticated()">
		    <td class="c b w20" colspan="2"><f:message key="common.action"/></td>
		</sec:authorize>
            </tr>
            <c:forEach items="${actionBean.books}" var="book">
                <tr>
                    <td class="w10">${book.id}</td>
                    <td class="w20"><c:out value="${book.author}"/></td>
                    <td class="w25"><c:out value="${book.name}"/></td>
                    <td class="w15"><c:out value="${book.publishDate}"/></td>
                    <td class="w15"><c:out value="${book.department}"/></td>
		    <sec:authorize access="isAuthenticated()">
			<td class="w10">
			    <s:link beanclass="cz.muni.fi.pa165.library.BooksActionBean" event="edit"><s:param name="book.id" value="${book.id}"/><f:message key="common.edit" /></s:link>
			    <s:link beanclass="cz.muni.fi.pa165.library.ImpressionActionBean" event="list"><s:param name="book.id" value="${book.id}"/><f:message key="book.impressions" /></s:link>
			</td>
			<td class="w10">
			    <s:form beanclass="cz.muni.fi.pa165.library.BooksActionBean">
				<s:hidden name="book.id" value="${book.id}"/>
				<s:submit name="delete"><f:message key="common.delete"/></s:submit>
			    </s:form>
			</td>
		    </sec:authorize>
                </tr>
            </c:forEach>
        </table>
            
        
    </s:layout-component>
</s:layout-render>