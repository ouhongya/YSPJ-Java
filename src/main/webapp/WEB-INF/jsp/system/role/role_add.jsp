<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
		<link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />

		<style>
			input::-webkit-outer-spin-button,            input::-webkit-inner-spin-button{                -webkit-appearance: none !important;            }
			.main{
				padding:30px 20px;
			}
			.title{
				height: 100px;
				line-height: 100px;
				text-align: center;
				font-size:28px;
				font-weight: 600;
				color: #333130;
			}
			.top{
				display: flex;
				justify-content: space-between;
				align-items: center;
				height: 100px;
				padding: 20px 0;
				border-bottom: 1px solid #dedfe6;
				margin-top: 30px;
			}
			.content{
				display: flex;
				justify-content: flex-start;

			}
			.company{
				padding: 0 20px;
			}
			.choose{

				display: block;
			}
			.action{
				display: flex;
				justify-content: space-around;
				padding: 20px 0;
			}
			.save{
				width: 80px;
				height: 30px;
				line-height: 30px;
				border-radius: 5px;
				color: #ffffff;
				background: #19be6b;
				text-align: center;
				font-size: 16px;
				margin: 0 10px;
				cursor:pointer;
			}
			.cancel{
				width: 80px;
				height: 30px;
				line-height: 30px;
				border-radius: 5px;
				color: #ffffff;
				background: #909399;
				text-align: center;
				font-size: 16px;
				margin: 0 10px;
				cursor:pointer;
			}
			.bottom{
				padding: 30px;
			}
			.part{
				border-bottom: 1px solid #ecf5ff;
				line-height:30px;
			}
			.name{
				height: 30px;
				line-height: 30px;
				font-size:16px;
				color: #666666;
				text-align: left;
			}
			p{
				margin: 10px 0;
			}
			span{
				margin-right:10px;
				color: #999999;
				font-size: 14px;
			}
			#uploader .placeholder{
				height: auto;
				min-height:100px;
			}
			#uploader .filelist li{
				width: 20vw;
				height: 16vw;
				line-height: 16vw;
			}
			#uploader .filelist li p.imgWrap{
				width: 20vw;
				height: 16vw;
				line-height: 16vw;
			}
			#uploader .filelist{
				display: flex;
				justify-content: center;

			}
			.center{
				position: fixed;
				top: 10vh;
				z-index: 999;
				margin-left: 45%;
				display: none;
			}






		</style>
	</head>
<body>
		<div class="main">
			<div class="title">
				??????????????????
			</div>
			<div class="choose">
				<div id="uploader">
					<div class="queueList">
						<div id="dndArea" class="placeholder">
							<div id="filePicker"  ></div>
						</div>
					</div>
					<div class="statusBar" style="display:none;">
						<div class="progress">
							<span class="text">0%</span>
							<span class="percentage"></span>
						</div><div class="info"></div>
						<div class="btns">
							<div></div><div class="uploadBtn">????????????</div>
						</div>
					</div>
				</div>
			</div>


			<form action="role/edit.do" name="form1" id="form1"  method="post">

			<div class="top">
				<div class="content">
					<div class="company">???????????????<input  name="company" id="company"  type="text"  placeholder="?????????????????????" title="????????????"/></div>
					<div class="company">???????????????<input name="username" id="username"  type="text" placeholder="?????????????????????" title="????????????"/></div>
					<div class="company">???????????????<input  name="phone" id="phone"  type="tel"  onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="11" placeholder="?????????????????????" title="????????????"/></div>
					<div class="company">???????????????<input  name="usernumber" id="usernumber"  type="number"    placeholder="???????????????????????????" title="????????????"/></div>
				</div>

				<div class="action">
					<div class="save" onclick="save()" >??????</div>

				</div>
			</div>

			<div class="bottom">
				<div class="part">
					<div class="name">?????????????????????</div>
					<p><span id = "name">???????????????</span><span id = "nub">????????????</span></p>
				</div>
			</div>
			</form>
		</div>
		<div id="zhongxin" class="center" ><br/><br/><br/><img src="static/images/jzx.gif" style="width: 100px;" /><br/><h4 class="lighter block ">?????????...</h4></div>
		<input type="text" id = "pictureid" style="display:none" />
		<!-- ?????? -->
		<script src="static/1.9.1/jquery.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script type="text/javascript" src="static/js/md5.js"></script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
        <script>
            var relfile = null;


			function save(){
				var pho = $("#phone").val();
				var passwd =  pho.slice(5);
				// var passwd = $.md5($("#username").val() +  $("#phone").val());
				$("#name").html("???????????????");
				$("#nub").html("????????????");
				$("#zhongxin").show();
				var company =  $("#company").val();
				var phone = $("#phone").val();
				var usernumber = $("#usernumber").val();
				if(usernumber<3){
					alert("????????????????????????3???")
					return
				}
				var username = $("#username").val();
				var pictureid =  $("#pictureid").val();
				var url = "<%=basePath%>role/edit.do";
				var postData;
				postData = {"company":company,"phone":phone,"passwd":passwd,"pictureid":pictureid,"username":username,"usernumber":usernumber};
				$.post(url,postData,function(data){
                    if(data.msg == "1"){
						$("#zhongxin").hide();
                    	alert("?????????????????????")
					}else if(data.msg == "0"){
						$("#zhongxin").hide();
						alert("?????????????????????")
						$("#name").html(data.name);
						$("#nub").html(data.phone);
						uploader.removeFile(relfile);


					}
				});

			}



			$(function() {
				//??????
				$('#tp').ace_file_input({
					no_file:'??????????????? ...',
					btn_choose:'??????',
					btn_change:'??????',
					droppable:false,
					onchange:null,
					thumbnail:false, //| true | large
					whitelist:'gif|png|jpg|jpeg',
				}).trigger("myClick");

			});


		</script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>

		<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
		<script type="text/javascript" src="plugins/webuploader/upload.js"></script>
</body>
</html>
