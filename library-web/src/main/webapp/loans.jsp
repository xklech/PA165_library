<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<s:layout-render name="/layout.jsp" titlekey="loans.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.library.LoansActionBean" var="actionBean"/>
	
	<!-- Find by ID ---------------------------------------------------- -->
	<s:form beanclass="cz.muni.fi.pa165.library.LoansActionBean">
	    <fieldset>
		<legend><f:message key="loans.findById"/></legend>
		<table>
		    <tr>
			<th><s:label for="fId" name="findId"><f:message key="loans.findById.id"/></s:label></th>
			<td><s:text id="fId" name="findId"/><s:errors field="findId" /></td>
		    </tr>
		</table>
		<s:submit name="findById">
		    <f:message key="loans.search"/>
		</s:submit>
	    </fieldset>
	</s:form>
	
	<!-- Find all active ----------------------------------------------- -->
	<s:form beanclass="cz.muni.fi.pa165.library.LoansActionBean">
	    <fieldset>
		<legend><f:message key="loans.findAllActive"/></legend>
		<s:submit name="findByAllActive">
		    <f:message key="loans.search"/>
		</s:submit>
	    </fieldset>
	</s:form>
	
	<!-- Find by customer ---------------------------------------------- -->
	<s:form beanclass="cz.muni.fi.pa165.library.LoansActionBean">
	    <fieldset>
		<legend><f:message key="loans.findByCustomer"/></legend>
		<table>
		    <tr>
			<th><s:label for="cId" name="findCustomer"><f:message key="loans.findByCustomer.customer"/></s:label></th>
			<td>
			    <s:select id="cId" name="findCustomer">
				<c:forEach var="fCustomer" items="${actionBean.customers}">
				    <s:option value="${fCustomer.id}">${fCustomer.firstName} ${fCustomer.lastName}</s:option>
				</c:forEach>
			    </s:select>
			    <s:errors field="findCustomer"/>
			</td>
		    </tr>
		</table>
		<s:submit name="findByCustomer">
		    <f:message key="loans.search"/>
		</s:submit>
	    </fieldset>
	</s:form>
	
	<!-- Find by from<->to --------------------------------------------- -->
	<s:form beanclass="cz.muni.fi.pa165.library.LoansActionBean">
	    <fieldset>
		<legend><f:message key="loans.findByFromTo"/></legend>
		<table>
		    <tr>
			<th><s:label for="fFrom" name="findFrom"><f:message key="loans.findByFromTo.from"/></s:label></th>
			<td><s:text id="fFrom" name="findFrom"/><s:errors field="findFrom"/></td>
		    </tr>
		    <tr>
			<th><s:label for="fTo" name="findTo"><f:message key="loans.findByFromTo.to"/></s:label></th>
			<td><s:text id="fTo" name="findTo"/><s:errors field="findTo"/></td>
		    </tr>
		</table>
		<s:submit name="findByFromTo">
		    <f:message key="loans.search"/>
		</s:submit>
	    </fieldset>
	</s:form>
	
	<!-- Prepare/add loan form ----------------------------------------- -->
	<s:form beanclass="cz.muni.fi.pa165.library.LoansActionBean">
	    <fieldset><legend><f:message key="loans.prepare"/></legend>
	    <%@include file="/loan/form.jsp"%>
	    <s:submit name="prepare"><f:message key="loans.prepare.prepareLoan"/></s:submit>
	</s:form>
	
	<script type="text/javascript">
	    $(document).ready(function () {
		$('#findByFrom, #findByTo').datepicker($.datepicker.regional['cs']);
	    });
	</script>
    </s:layout-component>
</s:layout-render>
