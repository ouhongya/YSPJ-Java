<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>后台登录</title>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />

	<link rel="stylesheet" href="static/login/bootstrap.min.css" />
	<link rel="stylesheet" href="static/login/css/camera.css" />
	<link rel="stylesheet" href="static/login/bootstrap-responsive.min.css" />
	<link rel="stylesheet" href="static/login/matrix-login.css" />
	<link href="static/login/font-awesome.css" rel="stylesheet" />
	<script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script>
	<style>
		#loginbox form {
			/* background:#cccccc5e; */
			background-color: rgba(0, 0, 0, .4);
		}

		.loginpart {
			width: 100%;
			text-align: center;
			margin: 0 auto;
			position: absolute;
		}

		h3 {
			letter-spacing: 8px;
		}

		h3 img {
			height: 30px;
			margin-right: 5px;
		}

		.remember {
			display: flex;
			justify-content: flex-end;
			align-items: center;

			padding: 0 60px;

		}

		.remember .password {
			color: #ffffff;
			font-size: 14px;
			display: flex;
			justify-content: flex-end;
			align-items: center;

		}

		.remember .password span {
			padding-right: 5px;
		}

		.remember .password input {
			margin: 0;
		}


		.form-actions {
			display: flex;
			justify-content: space-between;
			align-items: center;
			border-top: 1px solid #cccccc;
			padding: 10px 60px !important;
		}

		.form-actions:before,
		.form-actions:after {

			position: absolute;
		}

		.form-left {
			display: flex;
			justify-content: flex-start;
			align-items: center;

		}

		.form-left div {
			margin-right: 5px;
		}

		.form-right {
			display: flex;
			justify-content: flex-end;

		}

		.form-right span {
			margin-left: 10px;

		}

		.bottom {

			color: #f2f6fc;
			margin: 50px 0;
			text-align: center;
		}

		#logo,
		#loginbox {
			min-width: 560px;
		}

		#loginbox .main_input_box .add-on {
			padding: 10px 9px;
		}
	</style>

</head>

<body>

	<div class="loginpart">
		<div id="loginbox">
			<form action="" method="post" name="loginForm" id="loginForm">
				<div class="control-group normal_text">
					<h3>
						<!-- <img src="static/login/logo2.png" alt="Logo" /> -->
						 验收评价
					</h3>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
								<i><img height="37" src="static/login/user.png" /></i>
							</span><input type="text" name="loginname" id="loginname" value="" placeholder="请输入用户名" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
								<i><img height="37" src="static/login/suo.png" /></i>
							</span><input type="password" name="password" id="password" placeholder="请输入密码" value="" />
						</div>
					</div>
				</div>

				<div class="remember">
					<div class="password">
						<span>记住密码</span><input name="form-field-checkbox" id="saveid" type="checkbox"
							onclick="savePaw();" />
					</div>
				</div>
				<div class="form-actions">
					<div class="form-left">
						<div>
							<i><img src="static/login/yan.png" /></i>
						</div>
						<div class="codediv">
							<input type="text" name="code" id="code" class="login_code"
								style="padding:0px;margin: 0;width: 100%;" />
						</div>
						<div>
							<i><img style="height:22px;" id="codeImg" alt="点击更换" title="点击更换" src="" /></i>
						</div>
					</div>
					<div class="form-right">
						<span><a onclick="severCheck();" class="flip-link btn btn-success" id="to-recover">登录</a></span>
						<span><a href="javascript:quxiao();" class="btn ">取消</a></span>

					</div>
				</div>

			</form>


			<div class=" bottom">
				<div class="main_input_box">
					<span id="nameerr">Copyright © Cciet
					</span>
				</div>
			</div>
		</div>
	</div>
	<div id="templatemo_banner_slide" class="container_wapper">
		<div class="camera_wrap camera_emboss" id="camera_slide">
			<!-- <div data-src="static/login/images/banner_slide_01.jpg"></div>
			<div data-src="static/login/images/banner_slide_02.jpg"></div>
			<div data-src="static/login/images/banner_slide_03.jpg"></div> -->
			<!-- <div data-src="static/login/images/login_bg5.jpeg"></div>
			<div data-src="static/login/images/login_bg7.jpeg"></div>
			<div data-src="static/login/images/login_bg9.jpeg"></div> -->
			<div data-src="static/login/images/login1.png"></div>
			<div data-src="static/login/images/login2.png"></div>
			<div data-src="static/login/images/login3.png"></div>
		</div>
		<!-- #camera_wrap_3 -->
	</div>

	<script type="text/javascript">
		//服务器校验
		function severCheck() {
			if (check()) {

				var loginname = $("#loginname").val();
				var password = $("#password").val();
				var code = "qq313596790fh" + loginname + ",fh," + password + "QQ978336446fh" + ",fh," + $("#code").val();
				$.ajax({
					type: "POST",
					url: 'login_login',
					data: {
						KEYDATA: code,
						tm: new Date().getTime()
					},
					dataType: 'json',
					cache: false,
					success: function (data) {
						if ("success" == data.result) {
							saveCookie();
							window.location.href = "main/index";
						} else if ("usererror" == data.result) {
							$("#loginname").tips({
								side: 1,
								msg: "用户名或密码有误",
								bg: '#FF5080',
								time: 15
							});
							$("#loginname").focus();
						} else if ("codeerror" == data.result) {
							$("#code").tips({
								side: 1,
								msg: "验证码输入有误",
								bg: '#FF5080',
								time: 15
							});
							$("#code").focus();
						} else {
							$("#loginname").tips({
								side: 1,
								msg: "缺少参数",
								bg: '#FF5080',
								time: 15
							});
							$("#loginname").focus();
						}
					}
				});
			}
		}

		$(document).ready(function () {
			changeCode();
			$("#codeImg").bind("click", changeCode);
		});

		$(document).keyup(function (event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		function changeCode() {
			$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
		}

		//客户端校验
		function check() {

			if ($("#loginname").val() == "") {

				$("#loginname").tips({
					side: 2,
					msg: '用户名不得为空',
					bg: '#AE81FF',
					time: 3
				});

				$("#loginname").focus();
				return false;
			} else {
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}

			if ($("#password").val() == "") {

				$("#password").tips({
					side: 2,
					msg: '密码不得为空',
					bg: '#AE81FF',
					time: 3
				});

				$("#password").focus();
				return false;
			}
			if ($("#code").val() == "") {

				$("#code").tips({
					side: 1,
					msg: '验证码不得为空',
					bg: '#AE81FF',
					time: 3
				});

				$("#code").focus();
				return false;
			}

			$("#loginbox").tips({
				side: 1,
				msg: '正在登录 , 请稍后 ...',
				bg: '#68B500',
				time: 10
			});

			return true;
		}

		function savePaw() {
			if (!$("#saveid").attr("checked")) {
				$.cookie('loginname', '', {
					expires: -1
				});
				$.cookie('password', '', {
					expires: -1
				});
				$("#loginname").val('');
				$("#password").val('');
			}
		}

		function saveCookie() {
			if ($("#saveid").attr("checked")) {
				$.cookie('loginname', $("#loginname").val(), {
					expires: 7
				});
				$.cookie('password', $("#password").val(), {
					expires: 7
				});
			}
		}

		function quxiao() {
			$("#loginname").val('');
			$("#password").val('');
		}

		jQuery(function () {
			var loginname = $.cookie('loginname');
			var password = $.cookie('password');
			if (typeof (loginname) != "undefined" &&
				typeof (password) != "undefined") {
				$("#loginname").val(loginname);
				$("#password").val(password);
				$("#saveid").attr("checked", true);
				$("#code").focus();
			}
		});
	</script>
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
	</script>

	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/jquery-1.7.2.js"></script>
	<script src="static/login/js/jquery.easing.1.3.js"></script>
	<script src="static/login/js/jquery.mobile.customized.min.js"></script>
	<script src="static/login/js/camera.min.js"></script>
	<script src="static/login/js/templatemo_script.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
</body>

</html>