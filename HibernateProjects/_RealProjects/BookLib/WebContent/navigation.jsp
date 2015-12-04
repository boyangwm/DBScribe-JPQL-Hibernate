<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Accordion Menu Using jQuery</title>
<script type="text/javascript" language="javascript" src="jquery.js"></script>
<script type="text/javascript">
$(document).ready(function()
{
	//slides the element with class "menu_body" when paragraph with class "menu_head" is clicked 
	$("#firstpane p.menu_head").click(function()
    {
		$(this).css({backgroundImage:"url(down.png)"}).next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
       	$(this).siblings().css({backgroundImage:"url(left.png)"});
	});
	//slides the element with class "menu_body" when mouse is over the paragraph
	$("#secondpane p.menu_head").mouseover(function()
    {
	     $(this).css({backgroundImage:"url(down.png)"}).next("div.menu_body").slideDown(500).siblings("div.menu_body").slideUp("slow");
         $(this).siblings().css();
	});
});
</script>
<style type="text/css">
body {  auto; font: 100%/150% Verdana, Arial, Helvetica, sans-serif; }
.menu_list { width: 150px; }
.menu_head { padding: 5px 10px; cursor: pointer; position: relative; margin:1px;font-size:large; font-weight:bold; background: #efefef  center right no-repeat; }
.menu_body { display:none; }
.menu_body a { display:block; color:#6C6C6C; background-color:#FFFFFF; padding-left:10px; font-weight:bold; text-decoration:none; }
.menu_body a:hover { color: #000000; text-decoration:underline; }
</style>
</head>
<body>
<div style="float:left; margin-right:5px;">
  <!--This is the second division of right-->
  <div class="menu_list" id="secondpane">
    <!--Code for menu starts here-->
    <p class="menu_head">系统管理</p>
    <div class="menu_body"> <a href="usermanager.jsp" target="mainFrame">用户管理</a> <a href="readertypemanager.jsp" target="mainFrame">读者类型管理</a> <a href="readermanager.jsp" target="mainFrame">读者管理</a><a href="publishermanager.jsp" target="mainFrame">出版社管理</a> <a href="bookmanager.jsp" target="mainFrame">图书管理</a> </div>
    <p class="menu_head">借阅管理</p>
    <div class="menu_body"> <a href="lendmanager.jsp" target="mainFrame">借书</a> <a href="returnmanager.jsp" target="mainFrame">还书</a></div>
    <p class="menu_head">查询统计</p>
    <div class="menu_body"> <a href="bookcondition.jsp" target="mainFrame">图书借阅情况查询</a> <a href="readercondition.jsp" target="mainFrame">读者借阅情况查询</a> <a href="bookstatistics.jsp" target="mainFrame">图书借阅情况统计</a> <a href="readerstatistics.jsp" target="mainFrame">读者借阅情况统计</a></div>
  </div>
  <!--Code for menu ends here-->
</div>
</body>
</html>