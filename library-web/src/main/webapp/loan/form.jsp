<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table>
    <tr>
	<th><f:message key="loans.form.customer"/></th>
	<td>
	    <s:select name="customerId">
		<c:forEach var="customer" items="${actionBean.customers}">
		    <s:option value="${customer.id}">${customer.firstName} ${customer.lastName}</s:option>
		</c:forEach>
	    </s:select>
	    <s:errors field="customerId"/>
	</td>
    </tr>
    <tr>
	<th><f:message key="loans.form.book"/></th>
	<td>
	    <s:select name="bookId">
		<c:forEach var="book" items="${actionBean.books}">
		    <s:option value="${book.id}">${book.author} - ${book.name}</s:option>
		</c:forEach>
	    </s:select>
	    <s:errors field="bookId"/>
	</td>
    </tr>
</table>
