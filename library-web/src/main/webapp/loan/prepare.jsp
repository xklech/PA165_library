<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:form beanclass="cz.muni.fi.pa165.library.LoansActionBean">
    <fieldset><legend><f:message key="loans.add"/></legend>
    <%@include file="form.jsp"%>
    <s:submit name="prepare"><f:message key="loans.submit"/></s:submit>
</s:form>