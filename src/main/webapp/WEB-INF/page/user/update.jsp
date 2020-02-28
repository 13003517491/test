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
<script type="text/javascript">

	function update (){
		var index = layer.load(1,{shade:0.5});
		$.post(
				"<%=request.getContextPath()%>/user/update",
				$("#fm").serialize(),
				function(data){
					layer.close(index);
					if (data.code != -1) {
						layer.msg(data.msg, {icon: 6}, function(){
							window.location.href = "<%=request.getContextPath()%>/user/toShow";
						});
						return;
					}
					layer.msg(data.msg, {icon: 5})

				}
	)
	}

</script>
</head>
<body>

	<form id = "fm">
		<input type="hidden" name="id" value="${user.id}">
		用户名:<input type="text" name="userName" value="${user.userName}">
		密码:<input type="text" name="password" value="${user.password}">
		年龄:<input type="text" name="age" value="${user.age}">
		手机号:<input type="text" name="phone" value="${user.phone}">
		邮箱:<input type="text" name="email" value="${user.email}">
		<input type="button" value="修改提交" onclick="update()">
	</form>
</body>
</html>