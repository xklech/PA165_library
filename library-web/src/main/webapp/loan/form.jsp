<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<table>
    <tr>
	<th>Customer</th>
	<td>
	    <s:select name="customer">
		<c:forEach var="customer" items="${actionBean.customers}">
		    <s:option value="${customer.id}">${customer.firstName} ${customer.lastName}</s:option>
		</c:forEach>
	    </s:select>
	</td>
    </tr>
    <tr>
	<th>Book</th>
	<td>
	    <s:select name="book">
		<c:forEach var="book" items="${actionBean.books}">
		    <s:option value="${book.id}">${book.author} - ${book.name}</s:option>
		</c:forEach>
	    </s:select>
	</td>
    </tr>
</table>


