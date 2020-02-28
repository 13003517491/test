<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/res/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/res/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/res/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript">

	 $(function(){
			$("#fm").validate({
				//效验规则
				rules: {
					userName:{
						required:true,
						minlength:2,
						<%--remote: {--%>
		                <%--    type: 'POST',--%>
		                <%--    url: "<%=request.getContextPath()%>/user/findByName", --%>
		                <%--    data:{--%>
		                <%--    	userName:function() {--%>
		                <%--    		return $("#userName").val();--%>
			            <%--    	},--%>
			            <%--        dataType:"json",--%>
			            <%--        dataFilter:function(data,type){--%>
				        <%--            if (data == 'true'){--%>
				        <%--            	return true;	--%>
				        <%--            }else {--%>
				        <%--            	return false	;--%>
				        <%--            }--%>
			            <%--       	}--%>
		               	<%--	}--%>
						<%--}--%>
					},
					password:{
						required:true,
						minlength:1,
					},
					password2:{
						required:true,
						minlength:1,
						equalTo:"#pwd"
					},

				},
				messages:{
					userName:{
						required:"名字必填",
						minlength:"最少2个字"
					},
					password:{
						required:"密码必填",
						minlength:"最少1个字",
					},
					password2:{
						required:"请重新确认密码",
						minlength:"最少1个字",
						equalTo:"两次输入不同"
					},
				},
			})
		})
	 
	$.validator.setDefaults({
	    submitHandler: function() {
	    	var index = layer.load(1,{shade:0.5});
		      $.post("<%=request.getContextPath()%>/user/add",
		    		  $("#fm").serialize(),
		    		  function(data){
						if(data.code == -1){
			    	  		layer.close(index);
							layer.msg(data.msg, {icon: 5}); 
							return
						}
						layer.msg(data.msg, {
							  icon: 6,
							  time: 2000 
							}, function(){
				    	  		layer.close(index);
								parent.window.location.href = "<%=request.getContextPath()%>/user/toLogin";
							});   
				}
		)
	    }
	});

</script>
<!-- 错误时提示颜色 -->
<style>
	.error{
		color:red;
	}
</style>
</head>
<body>
	<form id="fm">
		<input type="hidden" name="token" value="${token}">
		用户名:<input type="text" name="userName" id="userName"><br />
		密码:<input type="text" name="password" id="pwd"><br />
		确认密码:<input type="text" name="password2"><br />
		年龄:<input type="text" name="age"><br />
		手机号:<input type="text" name="phone" ><br />
		邮箱:<input type="text" name="email" ><br />
		<input type="hidden" name="isDel" value="1" ><br />
		<input type="submit" value="添加提交">
	</form>
</body>
</html>