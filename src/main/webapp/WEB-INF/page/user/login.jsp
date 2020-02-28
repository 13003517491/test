<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/res/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/res/layer-v3.1.1/layer/layer.js"></script>
<script src="<%=request.getContextPath()%>/res/js/jsencrypt/jsencrypt.min.js"></script>
<script type="text/javascript">

	//判断当前窗口路径与加载路径是否一致。
	if(window.top.document.URL != document.URL){
		//将窗口路径与加载路径同步
		window.top.location = document.URL;
	}

	//登录方法
	function login(){
		var index = layer.load(1,{shade:0.5});
		$.post(
				"<%=request.getContextPath()%>/user/login",
				$("#dj_form").serialize(),
				function(data){
					layer.close(index);
					if (data.code != -1) {
						layer.msg(data.msg, {icon: 6}, function(){
							window.location.href = "<%=request.getContextPath()%>/index/toIndex";
						});
						return;
					}
					layer.msg(data.msg, {icon: 5})
				}
		)
	}
	
	//去注册页面
	function register() {
		layer.open({
			  type: 2,
			  title: '注册页面',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['380px', '40%'],
			  content: '<%=request.getContextPath()%>/user/toAdd'
			}); 
	}

/*	$(function() {
		$('#dj_button').click(function() {
			var encrypt = new JSEncrypt();
			// 公钥
			encrypt.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjDTC4AN0gsImx05j4qbuajy1b+A8pGW9vybE6UNxAWWtdopN1ovlSQxwcZ0g6QDriCIgQFSgX17dkwc+bPGKF9rcypYfatqI+ZETpslqNcBBqHluN6Qy8h/MjCAJcjLVFUMfpbG79yYSBI/ovVDQYkM9uwNX5uezV4BK/BsI8pTcgK7KBHuVupcZTjAtsoYaGFI3pIHJM73Z4ckX2tsfuQ9LXVGN9b+J32qyMJKNzKhN5meEf3lEiPsNUZ/QL6uu9+NMiLZ3ImTPywx42HuwxkzwNkATO3X2sjMv8KoABC/UqEzoxjuD/vpzHdXMortgySbGVoJfaNUeu1WBxdW8mQIDAQAB");
			// 加密方式   == 数据格式     "name="+$("#name").val()+"&pwd="+$("#name").val()
			var encrypted = encrypt.encrypt($("#dj_form").serialize());
			/!* $("#age").val(encrypted); *!/
			$.post(
					"<%=request.getContextPath()%>/user/login.rsa",
					{"data":encrypted},
					function(data){
						if (data.code != -1) {
							layer.msg(data.msg, {icon: 6}, function(){
								window.location.href = "<%=request.getContextPath()%>/index/toIndex";
							});
							return;
						}
						layer.msg(data.msg, {icon: 5})
					});
		});
	});*/
</script>
</head>
<body>
	<form id="dj_form">
		用户名:<input type="text" name="userName" id="userName">
		密码:<input type="text" name="password" id="password">
		<input type="button" value="登录" id="dj_button" onclick="login()">
		<input type="button" value="注册" onclick="register()">
	</form>
</body>
</html>