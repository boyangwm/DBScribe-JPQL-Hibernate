<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<html>
  <head>
    <title>Chat - Spring MVC + Hibernate + JQuery</title>
      <link href="<s:url value="/resources" />/css/main.css"
            rel="stylesheet"
            type="text/css" />
      <script type="text/javascript" src="/resources/js/jquery-1.8.3.min.js"></script>
      <script type="text/javascript" src="/resources/js/script.js"></script>
  </head>

  <body>
    <div id="container">
      <div id="top">
        <t:insertAttribute name="top" />
      </div>

      <div id="main">
        <t:insertAttribute name="content" />
      </div>
    </div>
  </body>
</html>
