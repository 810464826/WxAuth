<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--自适应  -->
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>微信登录Demo</title>
</head>
<body style="font-size:50px;text-align:center;">
	<div>登录成功！</div>
	<div>昵称：${info.nickname}</div> 
	<div>头像：<img style="vertical-align: top;" width="100" height="100" alt="" src="${info.headimgurl}"></div>
</body>
</html>