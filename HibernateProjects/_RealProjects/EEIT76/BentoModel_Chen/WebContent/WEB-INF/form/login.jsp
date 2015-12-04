<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/Css/eeit76-design.css" rel="stylesheet">
<style type="text/css">
	body{background: url(/BentoDelivery/Image/errorSun.gif) no-repeat bottom right fixed ;
		-moz-background-size: 40%; 
		background-size: 40%;  /*背景圖片延展   應該*/
		}
</style>
</head>
<body>
	<CENTER>
		<form action="j_security_check" method="post" >
			<table>
				<TR>
					<TH width="180">&nbsp;</TH>
					<TH width="180">&nbsp;</TH>
				</TR>
				<TR>
					<TD colspan='2' align="CENTER"
						style="font-size: 0.6cm; font-weight: 300;"><I><h1>管理員登入</h1></I></TD>
				</TR>
				<TR height='10'>
					<del align="CENTER" colspan='2'>
						&nbsp;
						</TD>
						<TR height='10'>
							<TD align="CENTER" colspan='2'>&nbsp;</TD>
						</TR>
						<tr>
							<TD width="180" align=center><h4>帳號：</h4></TD>
							<td><input class="form-control" type="text" name="j_username"  placeholder="請輸入帳號"></td>
							<td></td>
						</tr>
						<tr>
							<TD width="180" align=center><B><h4>密碼：</h4></B></TD>
							<td><input class="form-control" type="password" name="j_password"  placeholder="請輸入密碼"></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td align="center"><input class="btn btn-warning" type="submit" value="登入"></td>
						</tr>
			</table>
		</form>
	</CENTER>
</body>
</html>