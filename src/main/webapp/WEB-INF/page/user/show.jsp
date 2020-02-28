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
	$(function(){
		search();
	})

	function search() {
		$.post(
				"<%=request.getContextPath()%>/user/show",
				$("#fm").serialize(),
				function(data){
					var html = "";
					for (var i = 0; i < data.data.userList.length; i++) {
						var u = data.data.userList[i];
						html += "<tr>"
						html += "<td>"+u.id+"</td>"
						html += "<td>"+u.user_name+"</td>"
						html += "<td>"+u.password+"</td>"
						html += "<td>"+u.age+"</td>"
						html += "<td>"+u.phone+"</td>"
						html += "<td>"+u.email+"</td>"
						html += "<td><input type='button' value='修改' onclick='updateById("+u.id+")'>"
						html += "<input type='button' value='删除' onclick='delById("+u.id+")'></td>"
						html += "</tr>"
					}
					$("#tbd").html(html);
					var pageNo = $("#pageNo").val();
					var pageHtml = "<input type='button' value='上一页' onclick='page("+(parseInt(pageNo) - 1)+","+data.data.totalNum+")'>";
					pageHtml += "<input type='button' value='下一页' onclick='page("+(parseInt(pageNo) + 1)+","+data.data.totalNum+")'>";
					$("#pageInfo").html(pageHtml);
				}
		)
	}
	function page(page, totalNum) {
		if (page < 1) {
			layer.msg('已经到首页啦!', {icon:0});
			return;
		}
		if (page > totalNum) {
			layer.msg('已经到尾页啦!!', {icon:0});
			return;
		}
		$("#pageNo").val(page);
		search();

	}
	
	function delById(id){
		$.post(
				"<%=request.getContextPath()%>/user/delById",
				{"id":id},
				function(data){
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
	
	function updateById(id){
		window.location.href = "<%=request.getContextPath()%>/user/toUpdate?id="+id+"";
	}

	function updateUserImg(id){
		window.location.href = "<%=request.getContextPath()%>/qiniu/toUpdateImg?id="+id+"";
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
</script>
</head>
<body align="center">
<form id="fm">
	用户名:<input type="text" name="userName" value=""><br />
	手机号手机号:<input type="text" name="phone" value=""><br />
	邮箱:<input type="text" name="email" value=""><br />
	最小年龄:<input type="text" name="minAge" value="" >~最大年龄:<input type="text" name="maxAge" value="">
	<input type="hidden" name="pageNo" id="pageNo" value="1">
	<input type="button" value="查询" onclick="search()">
</form>
	<form>
		<table border="1px" align="center">
			<tr>
				<th>用户id</th>
				<th>用户名</th>
				<th>密码</th>
				<th>年龄</th>
				<th>手机号</th>
				<th>邮箱</th>
			</tr>
			<tbody id="tbd">
			
			</tbody>
		</table>
		<div id="pageInfo">

		</div>
	</form>
</body>
</html>