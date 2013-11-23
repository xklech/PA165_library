<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<s:layout-render name="/layout.jsp" titlekey="customer.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.library.CustomersActionBean" var="actionBean"/>
        <a href="javascript:showHide('customer_find')" title="<f:message key="common.search" />"><h2><f:message key="common.search" /></h2></a>
        <div id="customer_find" <c:if test="${actionBean.validationError }">style="display:none"</c:if>>
            
            <s:form beanclass="cz.muni.fi.pa165.library.CustomersActionBean">
                <fieldset><legend><f:message key="customer.list.findid"/></legend>                    
                    <table>
                        <tr>
                            <th><s:label for="f1" name="findId"/></th>
                            <td><s:text id="f1" name="findId"/><s:errors field="findId" /></td>
                            <td><s:submit name="findById"><f:message key="common.search" /></s:submit></td>
                        </tr>                            
                    </table>
                </fieldset>
            </s:form>
            
            <s:form beanclass="cz.muni.fi.pa165.library.CustomersActionBean">
                <fieldset>
                    <legend><f:message key="customer.list.findName"/></legend>                 
                    <table>
                        <tr>
                            <th><s:label for="f2" name="customer.firstName"/></th>
                            <td><s:text id="f2" name="findFirstName"/><s:errors field="findFirstName" /></td>
                        </tr>                            
                        <tr>
                            <th><s:label for="f3" name="customer.lastName"/></th>
                            <td><s:text id="f3" name="findLastName"/><s:errors field="findLastName" /></td>
                        </tr>
                        <tr>
                            <td><s:submit name="findByName"><f:message key="common.search" /></s:submit></td>
                        </tr>
                    </table>
                </fieldset>
            </s:form>
        </div>
        
        <a href="javascript:showHide('customer_add')" title="<f:message key="customer.add" />"><h2><f:message key="customer.add" /></h2></a>
        <div id="customer_add">
            <s:form beanclass="cz.muni.fi.pa165.library.CustomersActionBean">
                <fieldset><legend><f:message key="customer.list.newcustomer"/></legend>

                    <%@include file="customer/form.jsp"%>
                    <s:submit name="add"><f:message key="customer.submit.add" /></s:submit>
                </fieldset>
            </s:form>
        </div>
    </s:layout-component>
</s:layout-render>
