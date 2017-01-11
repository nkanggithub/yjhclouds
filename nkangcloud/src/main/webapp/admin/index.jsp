<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="hpe" />

<link rel="stylesheet" type="text/css"
	href="../nkang/assets_athena/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="../nkang/assets_athena/bootstrap/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css"
	href="../nkang/assets_athena/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" type="text/css"
	href="../nkang/css_athena/style.css" />
<link rel="stylesheet" type="text/css"
	href="../nkang/css_athena/profile.css" />
<link rel="stylesheet" type="text/css"
	href="../nkang/assets_athena/icomoon/iconMoon.css" />
<link rel="stylesheet" type="text/css"
	href="../nkang/css_athena/style-responsive.css" />
<link rel="stylesheet" type="text/css"
	href="../nkang/css_athena/style-default.css" />
<link rel="stylesheet" type="text/css"
	href="../MetroStyleFiles/sweetalert.css" />
<link rel="stylesheet" type="text/css"
	href="../MetroStyleFiles/CSS/sonhlab-base.css" />
<link rel="stylesheet" type="text/css"
	href="../MetroStyleFiles/CSS/openmes.css" />
<link rel="stylesheet" type="text/css"
	href="../nkang/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../nkang/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../nkang/demo.css">
<link rel="stylesheet" type="text/css" href="../nkang/animate.min.css">
<link rel="stylesheet" type="text/css"
	href="../nkang/autocomplete/jquery-ui.css">
<script type="text/javascript" src="../nkang/easyui/jquery.min.js"></script>
<script type="text/javascript">
	var $113 = $;
</script>
<script type="text/javascript"
	src="../nkang/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../Jsp/JS/jquery-1.8.0.js"></script>
<link rel="stylesheet" type="text/css"
	href="../MetroStyleFiles//CSS/animation-effects.css" />
<link rel="stylesheet" type="text/css" href="../Jsp/CSS/common.css">
<script type="text/javascript" src="../Jsp/JS/slides.js"></script>
<script src="../nkang/js_athena/jquery.circliful.min.js"></script>
<script src="../nkang/assets_athena/bootstrap/js/bootstrap.js"></script>
<script src="../MetroStyleFiles/sweetalert.min.js"></script>
<script type="text/javascript"
	src="../MetroStyleFiles//JS/openmes.min.js"></script>
<script src="../Jsp/JS/modernizr.js"></script>
<script src="../Jsp/JS/jSignature.min.noconflict.js"></script>
<script type="text/javascript" src="../nkang/autocomplete/jquery-ui.js"></script>
<title>admin</title>
<style>
</style>
</head>
<body style="padding: 0px !important; margin: 0px;">
	<div class="navbar-inner" style="background-color: #fff !important;">
		<div class="container-fluid">
			<a href="../DQMenu?UID=oqPI_xACjXB7pVPGi5KH9Nzqonj4"
				style="float: left; padding-top: 10px;"> <img
				src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=015900000053FQo&amp;oid=00D90000000pkXM"
				alt="Logo" class="HpLogo"
				style="display: inline !important; height: 30px; float: none; padding: 0px; vertical-align: bottom;"><span
				class="clientSubName"
				style="font-size: 12px; padding-left: 7px; color: #333;">Master
					Data Management</span>
				<h1 style="color: #333; font-size: 18px;" class="clientName">Hewlett-Packard
					Enterprise</h1>
			</a>
			<div class="clear"></div>
			<ul class="nav pull-right top-menu" style="margin-top: -70px;">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"
					style="font-size: 15px; margin: 0px; padding: 5px 0px;">
						Welcome <span class="username colorBlue" id="username">
							青青～笑 </span>
				</a> <span><a style="float: right;"> <img id="userImage"
							src="http://wx.qlogo.cn/mmopen/3ial0wgAS7u1sBkjFnq8CKfTlENtrYZvREwEhPMmu5FvHbDrYITooGLlmXszwNTVppJTc1ZCeyibZAqpviasUOmYqg4cfLr7lX8/0"
							alt="userImage" class="userImage" onclick="register()">
					</a></span></li>
			</ul>
		</div>
	</div>
	<div class="TABclass">
		<div style="border-top: 4px solid #00B388; padding: 5px;">
			<ul class="nav nav-tabs" id="myTabs"
				style="border-color: rgb(0, 179, 136);">
				<li class="active"><a href="#logoElements" data-toggle="tab"
					style="border-right-color: rgb(0, 179, 136); border-top-color: rgb(0, 179, 136); border-left-color: rgb(0, 179, 136);">LOGO管理</a></li>
				<li><a href="#WorkMates" data-toggle="tab"
					style="border-right-color: rgb(0, 179, 136); border-top-color: rgb(0, 179, 136); border-left-color: rgb(0, 179, 136);">人员管理</a></li>
			</ul>
			<div class="tab-content" id="dvTabContent"
				style="border: 0px; padding-top: 0px;">
				<div class="tab-pane active" id="logoElements">
					<!-- start logoElements-->
					<div class="Work_Mates_div2" id="Work_Mates_div">
						<div class="Work_Mates_div_list_div2">
							<span class="total_num">总数：4</span>
							<div class="clear"></div>
						</div>
						<div class="Work_Mates_div_list_div2" style="padding-bottom:0px !important;">
							<img
								src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=015900000053FQo&amp;oid=00D90000000pkXM"
								alt="Logo" class="HpLogo"
								style="display: inline !important; height: 30px;padding-left:0px !important;margin-left:0px !important;vertical-align: bottom;"><span
								class="clientSubName"
								style="font-size: 12px; padding-left: 7px; color: #333;">Master
								Data Management</span>
							<h1 style="color: #333; font-size: 18px;padding-left:0px;" class="clientName">Hewlett-Packard
								Enterprise</h1>
							<p style="font-size:10px;margin-bottom:3px;margin-top:-3px;">2017 Hewlett-Packard Enterprise Development Company, L.P.</p>
							<div style="float: right; margin-top: -80px; background-color: #777; color: #fff; font-weight:bold; font-size: 13px; padding: 3px;width:50px;text-align:center;border-radius:6px;">
								应用中
							</div>
							<div style="float: right; margin-top: -50px; background-color: #0197D6; color: #fff; font-weight:bold; font-size: 13px; padding: 3px;width:50px;text-align:center;border-radius:6px;">
								编辑
							</div>
							<div class="clear"></div>
						</div>
						<div class="Work_Mates_div_list_div2" style="padding-bottom:0px !important;">
							<img
								src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=015900000053FQo&amp;oid=00D90000000pkXM"
								alt="Logo" class="HpLogo"
								style="display: inline !important; height: 30px;padding-left:0px !important;margin-left:0px !important;vertical-align: bottom;"><span
								class="clientSubName"
								style="font-size: 12px; padding-left: 7px; color: #333;">Master
								Data Management</span>
							<h1 style="color: #333; font-size: 18px;padding-left:0px;" class="clientName">Hewlett-Packard
								Enterprise</h1>
							<p style="font-size:10px;margin-bottom:3px;margin-top:-3px;">2017 Hewlett-Packard Enterprise Development Company, L.P.</p>
							<div style="float: right; margin-top: -80px; background-color: #0197D6; color: #fff; font-weight:bold; font-size: 13px; padding: 3px;width:50px;text-align:center;border-radius:6px;">
								应用
							</div>
							<div style="float: right; margin-top: -50px; background-color: #0197D6; color: #fff; font-weight:bold; font-size: 13px; padding: 3px;width:50px;text-align:center;border-radius:6px;">
								编辑
							</div>
							<div class="clear"></div>
						</div>

					</div>
					<!-- end logoElements-->

				</div>
				<div class="tab-pane" id="WorkMates">
					<!-- start WorkMates-->
					<div class="Work_Mates_div2" id="Work_Mates_div">
						<div class="Work_Mates_div_list_div2">
							<span class="total_num"><img
								src="../MetroStyleFiles/role.png">总人数：44&nbsp;&nbsp;&nbsp;已注册人数：12</span>
							<div class="clear"></div>
						</div>
						<div class="Work_Mates_div_list_div2">
							<div class="Work_Mates_img_div2">
								<img
									src="http://wx.qlogo.cn/mmopen/3ial0wgAS7u3B8Oc7LqK5ZJb3ViaMo87OiaoNqLjWrKk7TLtITLqbMXWjWEZDGxZbn6ZpuiaKU1PiasiaiaULdSSW3scW9S1b9uWzt9/0"
									alt="userImage" class="matesUserImage"
									onclick="getUserInfo('康宁','http://wx.qlogo.cn/mmopen/3ial0wgAS7u3B8Oc7LqK5ZJb3ViaMo87OiaoNqLjWrKk7TLtITLqbMXWjWEZDGxZbn6ZpuiaKU1PiasiaiaULdSSW3scW9S1b9uWzt9/0','oqPI_xLq1YEJOczHi4DS2-1U0zqc');">
							</div>
							<div class="Work_Mates_text_div">
								<h2>
									<span
										onclick="getUserInfo('康宁','http://wx.qlogo.cn/mmopen/3ial0wgAS7u3B8Oc7LqK5ZJb3ViaMo87OiaoNqLjWrKk7TLtITLqbMXWjWEZDGxZbn6ZpuiaKU1PiasiaiaULdSSW3scW9S1b9uWzt9/0','oqPI_xLq1YEJOczHi4DS2-1U0zqc');">康宁</span><span
										class="role">Team Lead</span>
									<div style="float: right;">
										<img src="../MetroStyleFiles/reward.png" style="height: 25px;"><span
											style="font-size: 12px; color: #07090B; font-weight: normal;">11</span>
										<div></div>
									</div>
								</h2>
								<div>
									<div class="tag">java</div>
									<div class="tag">html</div>
									<div class="tag">webservice</div>
									<div class="tag">etl</div>
									<br> <span class="selfIntro">写点什么吧又不知道写什么...</span>
								</div>
							</div>
							<div
								style="float: right; margin-top: -45px; background-color: #eee; color: #333; font-size: 13px; padding: 3px;">907
								Days</div>
							<div class="clear"></div>
						</div>

					</div>
					<!-- end WorkMates-->
				</div>
			</div>
		</div>
	</div>
</body>
</html>