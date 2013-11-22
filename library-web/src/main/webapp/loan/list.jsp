<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-render name="/layout.jsp" titlekey="loans.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.library.LoansActionBean" var="actionBean"/>
	<table>
	    <tr>
		<th>Loan</th>
		<th>Customer</th>
		<th>Impression</th>
		<th>Start date</th>
		<th>End date</th>
		<th>Actions</th>
	    </tr>
	    <c:forEach var="loan" items="${actionBean.loans}">
		<tr>
		    <td>${loan.id}</td>
		    <td>${loan.customerTo.firstName} ${loan.customerTo.lastName}</td>
		    <td>${loan.impressionTo.bookTo.author} - ${loan.impressionTo.bookTo.name}</td>
		    <td>${loan.fromDate}</td>
		    <td>${loan.toDate}</td>
		    <td></td>
		    <td>
			<s:form beanclass="cz.muni.fi.pa165.library.LoansActionBean">
			    <s:hidden name="loanId" value="${loan.id}"/>
			    <s:submit name="restore"><f:message key="loans.list.restore"/></s:submit>
			    <s:submit name="delete"><f:message key="loans.list.delete"/></s:submit>
			</s:form>
		    </td>
		</tr>
	    </c:forEach>
	</table>
    </s:layout-component>
</s:layout-render>
