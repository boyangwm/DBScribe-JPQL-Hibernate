<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="header">
    <div id="logo"><a href="/home">Spring MVC Chat</a></div>
    <br />
    <%--<c:out value="${page}"/>--%>
    <%--<c:out value="${authenticated}"/>--%>
    <c:choose>
        <c:when test="${page == 'home'}">

            <c:if test="${authenticated == true}">
                <ul id="tools">
                    <li>
                        <a href="/home">Home</a>&nbsp;|&nbsp;
                        <a href="/profile/edit/${user.id}">Edit Profile</a>&nbsp;|&nbsp;
                        <a href="/login">Change User</a>&nbsp;|&nbsp;
                        <a href="<c:url value="j_spring_security_logout" />" >Logout</a>
                    </li>
                </ul>
                <div id="title"><h2>You're logged in as ${user.nick}</h2></div>
            </c:if>

            <c:if test="${authenticated == false}">
                <ul id="tools">
                    <li>
                        <a href="/home">Home</a>&nbsp;|&nbsp;
                        <a href="/login">Login</a>&nbsp;|&nbsp;
                        <a href="/register">Register</a>
                    </li>
                </ul>
                <div id="title"><h2>You're not logged in. Please <a href="/login">login</a> or <a href="/register">register</a></h2></div>
            </c:if>
            </ul>
        </c:when>

        <c:when test="${page == 'register'}">
            <ul id="tools">
                <li><a href="/home">Home</a></li>
            </ul>
        </c:when>

        <c:when test="${page == 'profile'}">
            <ul id="tools">
                <li>
                    <a href="/home">Home</a>&nbsp;|&nbsp;
                    <a href="/login">Change User</a>
                </li>
            </ul>
        </c:when>


        <c:otherwise>
            <ul id="tools">
                <li>
                    <a href="/home">Home</a>&nbsp;|&nbsp;
                    <a href="/login">Login</a>&nbsp;|&nbsp;
                    <a href="/register">Register</a>
                </li>
            </ul>
            <c:if test="${authenticated == true}">
                <div id="title"><h2>You're logged in as ${user.nick}</h2></div>
            </c:if>
            <c:if test="${authenticated == false}">
                <div id="title"><h2>You're not logged in. Please <a href="/login">login</a> or <a href="/register">register</a></h2></div>
            </c:if>
        </c:otherwise>
    </c:choose>
    <br />
</div>