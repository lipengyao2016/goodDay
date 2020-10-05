<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>GoodDay系统</title>
<link type="image/x-icon" rel="shortcut icon" href="${ctx}/common/img/goodDay.png" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="GoodDay系统">
<meta name="author" content="小张张的老头儿">
<link type="text/css" rel="stylesheet" href="${ctx}/common/css/reset.css">
<link type="text/css" rel="stylesheet" href="${ctx}/common/css/supersized.css">
<link type="text/css" rel="stylesheet" href="${ctx}/common/css/style.css">
<link type="text/css" rel="stylesheet" href="${ctx}/jquery/jquery-confirm/css/jquery-confirm.css" />

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
	<script src="http://open.sojson.com/common/js/canvas-nest.min.js" count="200" zindex="-2" opacity="0.8" color="255,218,155" type="text/javascript"></script>
	<div class="page-container">
        <h1>GoodDay系统</h1>
        <form id="form">
            <input type="text" name="userLoginName" class="username" placeholder="用户名" id="userLoginName">
            <input type="password" name="userPassword" class="password" placeholder="密码" id="userPassword">
            <button type="button" id="submit1">登陆</button>
        </form>
    </div>

    <!-- Javascript -->
    <script type="text/javascript" src="${ctx}/jquery/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/common/js/supersized.3.2.7.min.js"></script>
    <script type="text/javascript" src="${ctx}/common/js/supersized-init.js"></script>
    <script type="text/javascript" src="${ctx}/common/js/scripts.js"></script>
    <script type="text/javascript" src="${ctx}/jquery/jquery.toObject.js"></script>
    <script type="text/javascript" src="${ctx}/common/js/form2js.js"></script>
    <script type="text/javascript" src="${ctx}/common/js/json2.js"></script>
</body>
<script type="text/javascript" charset="utf-8">
	var cfg = {
		type : 'POST',
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8'
	};
	
	$("#submit1").click(function(){
		var loginName = $("#userLoginName").val();
		if(null == loginName || loginName == ""){
			alert("用户名不能为空！");
			return;
		}
		var pwd = $("#userPassword").val();
		if(null == pwd || pwd == ""){
			alert("密码不能为空！");
			return;
		}
		var obj = $('#form').toObject({ mode : 'first' });
		cfg.data = JSON.stringify(obj);
		cfg.success = function ret(res) {
			if("success" == res.model.errCode){
				window.location.href="${ctx}/admin/home.do";
			}else{
				alert(res.model.errMsg);
			}
		};
		cfg.error=function(XMLHttpRequest, textStatus,errorThrown){
			alert('登陆异常，请联系管理员！');
		};
		cfg.url = '${ctx}/common/login.json';
		$.ajax(cfg);
	});
</script>
</html>