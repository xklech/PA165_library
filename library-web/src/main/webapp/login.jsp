<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<s:layout-render name="/layout.jsp" titlekey="admin">       
    <s:layout-component name="body"> 

        <form name="f" action="<c:url value="j_spring_security_check" />" method="POST">

            <div class="post">
                <table>
                    <tr>
                        <td> <s:label for="j_username" name="username"/></td>
                        <td><input id="j_username" name="j_username" type="text" value="" autofocus /></td>
		    </tr>
                    <tr>
			<td><s:label for="j_password" name="password"/></td>
                        <td> <input id="j_password" name="j_password" type="password" /></td>
		    </tr>
                </table>
            </div>
            <button name="submit" type="submit" value="1" class="button"><f:message key="login"/></button>
        </form>

    </s:layout-component>
</s:layout-render>