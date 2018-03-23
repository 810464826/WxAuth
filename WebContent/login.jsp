<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--自适应  -->
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>微信登录Demo-登录页面</title>
</head>
<body>
	<form action="/WxAuth/wxCallBack" method="post">
		<input type="text" name="account"/>
		<input type="password" name="password"/>
		<input type="hidden" name="openid" value="${openid}"/>
		<input type="submit" value="登录并绑定"/>
	</form>
</body>
</html>