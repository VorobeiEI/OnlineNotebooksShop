<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ctg" uri="mytag"%>
<fmt:setLocale value="${locale}"  scope="session"/>
<fmt:setBundle basename="pagesCont.pagecontent" var="rb" />

<header class="navbar navbar-static-top bs-docs-nav" id="top">
    <div class="container" >

        <div class="pull-right">
            <form name="headerForm" method="GET" action="controller">
                <input type="hidden" name="command" value="language" />
                <button type="submit" class="btn btn-mini" name="ru">ru</button>
                <button type="submit" class="btn btn-mini" name="en">en</button>
            </form>
        </div>
        <br>

        <form name="headerForm" method="GET" action="controller">
            <input type="hidden" name="command" value="header" />
            <div class="navbar-header">
                <a href="index.jsp"><img border="0" title="" alt="" src="image/logo.png"></a>
            </div>
            <nav class="navbar-collapse bs-navbar-collapse">
                <ul class="nav navbar-nav">
                    <li>
                        <fmt:message key="button.main" bundle="${ rb }" var="buttonMain" />
                        <button type="submit" class="btn btn-default" name="main">${buttonMain}</button>
                    </li>
                    <li class="active">
                        <fmt:message key="button.goods" bundle="${ rb }" var="buttonGoods" />
                        <button type="submit" class="btn btn-default" name="goods">${buttonGoods}</button>
                    </li>
                    <li>
                        <fmt:message key="button.cabinet" bundle="${ rb }" var="buttonCabinet" />
                        <button type="submit" class="btn btn-default" name="cabinet">${buttonCabinet}</button>
                    </li>
                    <li>
                        <fmt:message key="button.cart" bundle="${ rb }" var="buttonCart" />
                        <button type="submit" class="btn btn-default" name="cart">${buttonCart}</button>
                    </li>
                    <li>
                        <fmt:message key="button.contacts" bundle="${ rb }" var="buttonContacts" />
                        <button type="submit" class="btn btn-default" name="contacts">${buttonContacts}</button>
                    </li>
                    <li>
                        <fmt:message key="button.callback" bundle="${ rb }" var="buttonCallback" />
                        <button type="submit" class="btn btn-default" name="callback">${buttonCallback}</button>
                    </li>
                    <c:if test = "${role == 'ADMINISTRATOR'}">
                        <li>
                            <fmt:message key="button.admin" bundle="${ rb }" var="buttonAdmin" />
                            <button type="submit" class="btn btn-default" name="admin">${buttonAdmin}</button>
                        </li>
                    </c:if>
                </ul>
                <br/>
                <div class="pull-right">
                    <c:if test = "${user == null}">
                        <fmt:message key="button.login" bundle="${ rb }" var="buttonLogIn" />
                        <button type="submit" name="logIn" value="Log in" class="btn btn-primary btn-xs">${buttonLogIn}</button>
                        <fmt:message key="button.registration" bundle="${ rb }" var="buttonRegistration" />
                        <button type="submit" name="registration" value="Registration" class="btn btn-primary btn-xs">${buttonRegistration}</button>
                    </c:if>
                    <br/>${ctg:notnull(user)}, hello!
                    <c:if test = "${user != null}">
                        <fmt:message key="button.logout" bundle="${ rb }" var="buttonLogout" />
                        <a href="controller?command=logout">${buttonLogout}</a>
                    </c:if>
                </div>
            </nav>
        </form>
    </div>
</header>