<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>每10秒重整一次</title>
<script>

window.setInterval(sendRequest, 10000);

function sendRequest(){
 	location.reload();
}
</script>
</head>
<body>
<h2>每10秒重整一次....</h2>
</body>
</html>