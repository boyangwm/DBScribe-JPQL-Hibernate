<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
    <h2>User: ${user.nick}</h2>

    <table cellspacing="15">
        <s:url value="/admin/users/${user.id}" var="user_url" />
        <tr>
            <td>
                <c:out value="${user.id}"/>
            </td>

            <td>
                <c:out value="${user.firstName}"/>
            </td>

            <td>
                <c:out value="${user.middleName}"/>
            </td>

            <td>
                <c:out value="${user.lastName}"/>
            </td>

            <td>
                <c:out value="${user.password}"/>
            </td>

            <td>
                <c:out value="${user.email}"/>
            </td>

            <td>
                <a href="${user_url}">${user.nick}</a>
                <sf:form method="delete" action="/admin/users/delete/${user.id}" name="deleteUser_${user.id}" cssClass="deleteForm">
                    <input type="submit" value="Delete"/>
                </sf:form>
            </td>
        </tr>
    </table>

</div>