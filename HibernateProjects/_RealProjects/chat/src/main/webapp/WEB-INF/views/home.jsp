<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="chat">
    ${chatHistory}
</div>
<br />
<sf:form id="chatMessageSubmitForm" >
    <br />
    <span id="flash"></span>
    <br />
    <div id="pageSettings">Messages per page:
        <input type="radio" id="messagesPerPage50" name="messagesPerPage" value="50" checked="true" />50
        <input type="radio" id="messagesPerPage100" name="messagesPerPage" value="100" />100
        <input type="radio" id="messagesPerPage200" name="messagesPerPage" value="200" />200
        <input type="radio" id="messagesPerPageAll" name="messagesPerPage" value="0" />All
    </div>
    <br />
    <textarea id="chatMessage" name="chatMessage" rows="3" cols="50"></textarea>
    <br />
    <input type="button" name="postMessage" id="postMessage" value="Post Message">
</sf:form>
