<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<s:layout-render name="/layout.jsp" titlekey="books.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.library.BooksActionBean" var="actionBean"/>
        <a href="javascript:showHide('books_find')" title="<f:message key="common.search" />"><h2><f:message key="common.search" /></h2></a>
        <div id="books_find" <c:if test="${actionBean.validationError }">style="display:none"</c:if>>
            <div class="row">
		<div class="col-md-6">
		    <s:form beanclass="cz.muni.fi.pa165.library.BooksActionBean">
			<fieldset><legend><f:message key="books.list.findall"/></legend>
			    <s:submit name="findAll"><f:message key="books.submit.find" /></s:submit>
			</fieldset>
		    </s:form>
		</div>
		<div class="col-md-6">
		    <s:form beanclass="cz.muni.fi.pa165.library.BooksActionBean">
			<fieldset><legend><f:message key="books.list.findid"/></legend>

			    <table>
				<tr>
				    <th><s:label for="f1" name="findId"/></th>
				    <td><s:text id="f1" name="findId"/><s:errors field="findId" /></td>
				</tr>
			    </table>
			    <s:submit name="findById"><f:message key="books.submit.find" /></s:submit>
			</fieldset>
		    </s:form>
		</div>
	    </div>
	    <div class="row">
		<div class="col-md-6">
		    <s:form beanclass="cz.muni.fi.pa165.library.BooksActionBean">
			<fieldset><legend><f:message key="books.list.findname"/></legend>

			    <table>
				<tr>
				    <th><s:label for="f2" name="findName"/></th>
				    <td><s:text id="f2" name="findName"/><s:errors field="findName" /></td>
				</tr>
			    </table>
			    <s:submit name="findByName"><f:message key="books.submit.find" /></s:submit>
			</fieldset>
		    </s:form>
		</div>
		<div class="col-md-6">
		    <s:form beanclass="cz.muni.fi.pa165.library.BooksActionBean">
			<fieldset><legend><f:message key="books.list.findauthor"/></legend>

			    <table>
				<tr>
				    <th><s:label for="f3" name="findAuthor"/></th>
				    <td><s:text id="f3" name="findAuthor"/><s:errors field="findAuthor" /></td>
				</tr>
			    </table>
			    <s:submit name="findByAuthor"><f:message key="books.submit.find" /></s:submit>
			</fieldset>
		    </s:form>
		</div>
	    </div>
	    <div class="row">
		<div class="col-md-6">
		    <s:form beanclass="cz.muni.fi.pa165.library.BooksActionBean">
			<fieldset><legend><f:message key="books.list.finddepartment"/></legend>

			    <table>
				<tr>
				    <th><s:label for="f4" name="findDepartment"/></th>
				    <td><s:text id="f4" name="findDepartment"/><s:errors field="findDepartment" /></td>
				</tr>
			    </table>
			    <s:submit name="findByDepartment"><f:message key="books.submit.find" /></s:submit>
			</fieldset>
		    </s:form>
		</div>
		<div class="col-md-6">
		    <s:form beanclass="cz.muni.fi.pa165.library.BooksActionBean">
			<fieldset><legend><f:message key="books.list.finddate"/></legend>

			    <table>
				<tr>
				    <th><s:label for="findDateFromPicker" name="findDateFrom"/></th>
				    <td><s:text id="findDateFromPicker" name="findDateFrom"/><s:errors field="findDateFrom" /></td>
				</tr>
			    </table>

			    <table>
				<tr>
				    <th><s:label for="findDateToPicker" name="findDateTo"/></th>
				    <td><s:text id="findDateToPicker" name="findDateTo"/> <s:errors field="findDateTo" /></td>
				</tr>
			    </table>
			    <s:submit name="findByDate"><f:message key="books.submit.find" /></s:submit>
			</fieldset>
		    </s:form>
		</div>
	    </div>
	</div>
	<a href="javascript:showHide('books_add')" title="<f:message key="books.add" />"><h2><f:message key="books.add" /></h2></a>
	<div id="books_add">
	    <s:form beanclass="cz.muni.fi.pa165.library.BooksActionBean">
		<fieldset><legend><f:message key="books.list.newbook"/></legend>
		    <%@include file="book/form.jsp"%>
		    <s:submit name="add"><f:message key="books.submit.add" /></s:submit>
		</fieldset>
	    </s:form>
	</div>
	<script>
	    $(function(){
		$( "#f2" ).autocomplete({
		  source: "${pageContext.request.contextPath}/autoComplete/booksByName",
		  minLength: 1,
		  maxLength:15,
		  select: function( event, ui ) {
		    $( "#f2" ).val(ui.item.value);
		  }
		});
		$( "#f3" ).autocomplete({
		  source: "${pageContext.request.contextPath}/autoComplete/booksByAuthor",
		  minLength: 1,
		  maxLength:15,
		  select: function( event, ui ) {
		    $( "#f3" ).val(ui.item.value);
		  }
		});
		$( "#f4" ).autocomplete({
		  source: "${pageContext.request.contextPath}/autoComplete/booksByDepartment",
		  minLength: 1,
		  maxLength:15,
		  select: function( event, ui ) {
		    $( "#f4" ).val(ui.item.value);
		  }
		});
	      });   
	    $(function(){
		$("#findDateFromPicker").datepicker();
		$("#findDateToPicker").datepicker();
	    });
	</script>
    </s:layout-component>
</s:layout-render>
