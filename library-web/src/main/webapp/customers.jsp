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
                <fieldset><legend><f:message key="customer.list.findFirstName"/></legend>                 
                    <table>
                        <tr>
                            <th><s:label for="f2" name="customer.firstName"/></th>
                            <td><s:text id="f2" name="customer.firstName"/><s:errors field="customer.firstName" /></td>
                            <td><s:submit name="findByFirstName"><f:message key="common.search" /></s:submit></td>
                        </tr>                            
                    </table>
                </fieldset>
            </s:form>
            
            <s:form beanclass="cz.muni.fi.pa165.library.CustomersActionBean">
                <fieldset><legend><f:message key="customer.list.findLastName"/></legend>
                    <table>
                        <tr>
                            <th><s:label for="f3" name="customer.lastName"/></th>
                            <td><s:text id="f3" name="customer.lastName"/><s:errors field="customer.lastName" /></td>
                            <td><s:submit name="findByLastName"><f:message key="common.search" /></s:submit></td>
                        </tr>                            
                    </table>
                </fieldset>
            </s:form>
            
            <s:form beanclass="cz.muni.fi.pa165.library.CustomersActionBean">
                <fieldset><legend><f:message key="customer.list.findAddress"/></legend>
                    <table>
                        <tr>
                            <th><s:label for="f4" name="customer.address"/></th>
                            <td><s:text id="f4" name="customer.address"/><s:errors field="customer.address" /></td>
                            <td><s:submit name="findByAddress"><f:message key="common.search" /></s:submit></td>
                        </tr>                            
                    </table>
                </fieldset>
            </s:form>
            
            <script>
                $(function(){
                    $("#findDatePicker").datepicker();
                });
                
            </script>
            <s:form beanclass="cz.muni.fi.pa165.library.CustomersActionBean">
                <fieldset><legend><f:message key="customer.list.findDateOfBirth"/></legend>
                    <table>
                        <tr>
                            <th><s:label for="findDatePicker" name="customer.dateOfBirth"/></th>
                            <td><s:text id="findDatePicker" name="customer.dateOfBirth"/><s:errors field="customer.dateOfBirth" /></td>
                            <td><s:submit name="findByDateOfBirth"><f:message key="common.search" /></s:submit></td>
                        </tr>                            
                    </table>
                </fieldset>
            </s:form>
            
            <s:form beanclass="cz.muni.fi.pa165.library.CustomersActionBean">
                <fieldset><legend><f:message key="customer.list.findPid"/></legend>
                    <table>
                        <tr>
                            <th><s:label for="f6" name="customer.pid"/></th>
                            <td><s:text id="f6" name="customer.pid"/><s:errors field="customer.pid" /></td>
                            <td><s:submit name="findByPid"><f:message key="common.search" /></s:submit></td>
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
