<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<security:authorize
        access="isAuthenticated()">

<div>

    <sf:form modelAttribute="user">
        <fieldset>
            <sf:errors cssClass="error" />
            <table cellspacing="0">
                <tr>
                    <th><sf:label path="nick">Nick:</sf:label></th>
                    <td><sf:input path="nick" size="15" readonly="true" disabled="true" /></td>
                </tr>

                <tr>
                    <th><sf:label path="firstName">First Name:</sf:label></th>
                    <td><sf:input path="firstName" size="15" readonly="true" disabled="true" /></td>
                </tr>

                <tr>
                    <th><sf:label path="middleName">Middle Name:</sf:label></th>
                    <td><sf:input path="middleName" size="15" readonly="true" disabled="true" /></td>
                </tr>

                <tr>
                    <th><sf:label path="lastName">Last Name:</sf:label></th>
                    <td><sf:input path="lastName" size="15" readonly="true" disabled="true" /></td>
                </tr>

                <tr>
                    <th><sf:label path="password">Password:</sf:label></th>
                    <td><sf:password path="password" size="20"
                                     showPassword="true" readonly="true" disabled="true"/>
                    </td>
                </tr>

                <tr>
                    <th><sf:label path="email">Email Address:</sf:label></th>
                    <td><sf:input path="email" size="30" readonly="true" disabled="true"/></td>
                </tr>
            </table>
        </fieldset>
    </sf:form>
</div>

</security:authorize>