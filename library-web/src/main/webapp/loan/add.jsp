<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-render name="/layout.jsp" titlekey="loans.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.library.LoansActionBean" var="actionBean"/>
	<s:form beanclass="cz.muni.fi.pa165.library.LoansActionBean">
	    <fieldset>
		<legend><f:message key="loans.add" /></legend>
		<s:hidden name="customerId" value="${actionBean.customerId}"/>
		<s:hidden name="bookId" value="${actionBean.bookId}"/>
		<table>
		    <tr>
			<th><f:message key="loans.add.impression" /></th>
			<td>
			    <s:select name="impressionId">
				<c:forEach var="impression" items="${actionBean.impressions}">
				    <s:option value="${impression.id}">${impression.bookTo.author} - ${impression.bookTo.name} (#${impression.id})</s:option>
				</c:forEach>
			    </s:select>
			    <s:errors field="customerId"/>
			</td>
		    </tr>
		</table>
		<s:submit name="add"><f:message key="loans.add.addLoan"/></s:submit>
	    </fieldset>
	</s:form>
    </s:layout-component>
</s:layout-render>