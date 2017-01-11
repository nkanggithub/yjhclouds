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
<script src="../nkang/js_athena/jquery.circliful.min.js"></script>
<script src="../nkang/assets_athena/bootstrap/js/bootstrap.js"></script>
<script src="../MetroStyleFiles/sweetalert.min.js"></script>
<script type="text/javascript"
	src="../MetroStyleFiles//JS/openmes.min.js"></script>
<script src="../Jsp/JS/modernizr.js"></script>
<script src="../Jsp/JS/jSignature.min.noconflict.js"></script>
<script type="text/javascript" src="../nkang/autocomplete/jquery-ui.js"></script>
<script type="text/javascript">
$(window).load(function() {
		getLogoLists();
		getMDLUserLists();
});
function getLogoLists() {
	jQuery.ajax({
				type : "GET",
				url : "../QueryClientMetaList",
				data : {},
				cache : false,
				success : function(data) {
					var jsons = data;
					var ul = "",regNumber=0;
					ul='<div class="Work_Mates_div_list_div2">'
						+'							<span class="total_num">总数：'+jsons.length+'</span>'
						+'							<div class="clear"></div>'
						+'						</div>';
					for (var i = 0; i < jsons.length; i++) {
					var temp=jsons[i];
					var buttonText;
					if(temp.clientActive=='Y'){
						buttonText='							<div style="float: right; margin-top: -80px; background-color: #777; color: #fff; font-weight:bold; font-size: 13px; padding: 3px;width:50px;text-align:center;border-radius:6px;">'
							+'								应用中'
							+'							</div>';
					}else{
						buttonText='<div style="float: right; margin-top: -80px; background-color: #0197D6; color: #fff; font-weight:bold; font-size: 13px; padding: 3px;width:50px;text-align:center;border-radius:6px;" onclick="updateLogo(\''+temp.clientStockCode+'\')">'
							+'								应用'
							+'							</div>';
					}
						var li='<div class="Work_Mates_div_list_div2" style="padding-bottom:0px !important;">'
		+'							<img'
		+'								src="'+temp.clientLogo+'"'
		+'								alt="Logo" class="HpLogo"'
		+'								style="display: inline !important; height: 30px;padding-left:0px !important;margin-left:0px !important;vertical-align: bottom;"><span'
		+'								class="clientSubName"'
		+'								style="font-size: 12px; padding-left: 7px; color: #333;">'+temp.clientSubName+'</span>'
		+'							<h1 style="color: #333; font-size: 18px;padding-left:0px;" class="clientName">'+temp.clientName+'</h1>'
		+'							<p style="font-size:10px;margin-bottom:3px;margin-top:-3px;">'+temp.clientCopyRight+'</p>'
		+buttonText
		+'							<div style="float: right; margin-top: -50px; background-color: #0197D6; color: #fff; font-weight:bold; font-size: 13px; padding: 3px;width:50px;text-align:center;border-radius:6px;">'
		+'								编辑'
		+'							</div>'
		+'							<div class="clear"></div>'
		+'						</div>';
		ul+=li;
					}
					$("#Logo_div").html(ul);
				}
			});
}
function updateLogo(id){
	alert(id);
}
function getMDLUserLists() {
	$.ajax({
				type : "GET",
				url : "../userProfile/getMDLUserLists",
				data : {},
				cache : false,
				success : function(data) {
					data = '{"results":' + data + '}';
					var jsons = eval('(' + data + ')');
					var ul = "",regNumber=0;
					ul='<div class="Work_Mates_div_list_div2" style="border-bottom:0px;">'
					for (var i = 0; i < jsons.results.length; i++) {
						var temp = jsons.results[i];
						var selfIntro=temp.selfIntro;
						var role=temp.role;
						var workDay=temp.workDay;
						var tag=temp.tag;
						var tagHtml="";
						var congratulate="";
						
						if(temp.openid==$('#uid').val()){
							LastToLikeDate=temp.like.lastLikeDate;
							lastLikeTo=temp.like.lastLikeTo;
						}

						
						
						if(selfIntro==null||selfIntro=='null'){
							selfIntro="nothing";
						}else{
							if(selfIntro.length>10){
								selfIntro=(selfIntro.substr(0,12)+'...');
							}
						}
						if(role==null||role=='null'){
							role="";
						}
						if(tag!=null&&tag!='null'){
							for(var j=0;j<tag.length&&j<4;j++){
								for (var key in tag[j]) { 
									tagHtml+='													<div class="tag">'
									+key
									+'													</div>';
								}
							}
						}
						if(workDay==null||workDay=='null'||workDay==0){
							workDay="";
						}else{
							regNumber++;
							workDay='<div style="float:right;margin-top:-45px;background-color:#eee;color:#333;font-size:13px;padding:3px;">'+workDay+' Days</div>';
						}
						if(temp.congratulateNum==null||temp.congratulateNum=='null'||temp.congratulateNum==undefined||temp.congratulateNum==0){
							
						}else{
							congratulate='<div style="float:right;"><img src="../MetroStyleFiles/reward.png" style="height:25px;"/>'
								+ '<span style="font-size:12px;color:#07090B;font-weight:normal;">'+temp.congratulateNum+'</span><div>';
						}
						var li='	<div class="Work_Mates_div_list_div2">'
							+'                                           	 	<div class="Work_Mates_img_div2">'
							+'                                        			 <img src="'
							+ temp.headimgurl
							+ '" alt="userImage" class="matesUserImage" alt="no_username" onclick="updateUserInfo()"/> '
							+'                                         		</div>'
							+'                                         		<div class="Work_Mates_text_div">'
							+'                                        			 <h2><span  onclick="updateUserInfo()">'
							+ temp.nickname
							+ '</span><span class="role">'
							+role+'</span>'
							+congratulate
							+'</h2>'
							+ '<div>'
							+tagHtml
							+'<br/>'
							+'													<span class="selfIntro">'+selfIntro+'</span>'
							+'												</div>'
							+'                                        		</div>'
							+workDay
							+'                                                <div class="clear"></div>'
							+'                                          </div>';
						ul += li;
					}
					ul='<div class="Work_Mates_div_list_div2">'
					+'<span class="total_num"><img src="../MetroStyleFiles/role.png"/>总人数：'+ jsons.results.length
					+'&nbsp;&nbsp;&nbsp;已注册人数：'+regNumber
					+'</span><div class="clear"></div></div>'+ul;
					$("#Work_Mates_div").html(ul);
				}
			});
}

function updateUserInfo() {
	jQuery
	.ajax({
		type : "GET",
		url : "../userProfile/getMDLUserLists",
		data : {
			UID : $('#uid').val()
		},
		cache : false,
		success : function(data) {
			data = data.replace(/:null/g, ':"未注册"');
			data = '{"results":' + data + '}';
			var jsons = eval('(' + data + ')');
			if (jsons.results.length > 0) {
				$("#info_tag tr").html("");
				if(jsons.results[0].role!="未注册"){
					$("#info_interact").css("display","none");
					$("#info_interact2").css("display","none");
					$("#info_imgurl").attr("src",$('#userImage').attr('src'));
					if(jsons.results[0].tag!="未注册"){
						for(var j=0;j<jsons.results[0].tag.length;j++){
							var tag=jsons.results[0].tag[j];
							for (var key in tag) { 
								var td='<td>'
									+'				<div id="'+key+'" data-dimension="70" data-text="'
									+tag[key]
									+'%" data-info="" data-width="8" data-fontsize="18" data-percent="'
									+tag[key]
									+'" data-fgcolor="#FFF" data-bgcolor="#aaa" data-fill=""></div>'
									+'				<span style="font-size:12px;">'
									+key
									+'</span>'
									+'														</td>';

								$("#info_tag tr").append(td);
								$('#'+key).circliful();
							}
						}
					}
					data = data.replace(/:"未注册"/g, ':"未编辑"');
					jsons = eval('(' + data + ')');
					$("#info_username span").html(jsons.results[0].realName+'<span style="font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;['+jsons.results[0].role+']&nbsp;</span>'+'<img onclick="showUpdateUserInfo()" src="../MetroStyleFiles/edit.png" style="height: 20px; cursor: pointer;padding-left:5px;"/>');
					$("#info_all").css('display','table');
					$("#info_phone").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].phone);
					$("#info_group").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].groupid);
					$("#info_email").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].email);
					$("#info_selfIntro").text(jsons.results[0].selfIntro);
					$('#UserInfo').modal('show');
				}else{
					$("#info_all").css('display','none');
					$("#info_selfIntro").text('');
					showRegister();
				}
			}else{
				$("#info_all").css('display','none');
				$("#info_selfIntro").text('');
				showRegister();
			}
		}
	});
	}
	
function showUpdateUserInfo(){
	$('#UserInfo').modal('hide');
	$('#registerform').modal('show');
	$.ajax({
		type : "GET",
		url : "../userProfile/getMDLUserLists",
		data : {
			UID : $('#uid').val()
		},
		cache : false,
		success : function(data) {
			data = data.replace(/:null/g, ':"未注册"');
			data = '{"results":' + data + '}';
			var jsons = eval('(' + data + ')');
			if (jsons.results.length > 0) {
				if(jsons.results[0].realName !="未注册"){
					$("#realname").val(jsons.results[0].realName);
				}
				if(jsons.results[0].phone !="未注册"){
					$("#phone").val(jsons.results[0].phone);
				}
				if(jsons.results[0].email !="未注册"){
					$("#email").val(jsons.results[0].email);
				}
				$("#roleSelect option[value='"+jsons.results[0].role+"']").attr("selected",true);
			    var count=$("#roleSelect option").length;
			    for(var i=0;i<count;i++)  
			       {           if($("#roleSelect").get(0).options[i].text == jsons.results[0].role)  
			          {  
			              $("#roleSelect").get(0).options[i].selected = true;  
			            
			              break;  
			          }  
			      }
			    count=$("#groupSelect option").length;
			    for(var i=0;i<count;i++)  
			       {           if($("#groupSelect").get(0).options[i].text == jsons.results[0].groupid)  
			          {  
			              $("#groupSelect").get(0).options[i].selected = true;  
			            
			              break;  
			          }  
			      }
			    if(jsons.results[0].tag!="未注册"){
					for(var j=0;j<jsons.results[0].tag.length;j++){
						var tag=jsons.results[0].tag[j];
						for (var key in tag) { 
							if(key=="java"){
								$113("#javatag").slider("setValue",tag[key]);
							} 
							
							if(key =="html"){
								$113("#htmltag").slider("setValue",tag[key]);
							} 
							
							if(key =="webservice"){
								$113("#webservicetag").slider("setValue",tag[key]);
							} 
							
							if(key =="etl"){
								$113("#etltag").slider("setValue",tag[key]);
							}
						}
					}
				}
			    
			    if(jsons.results[0].selfIntro !="未注册"){
					$("#selfIntro").val(jsons.results[0].selfIntro);
			    }
			}
		}
	});
	$("#registerBtn").click(function(){
		var uid = $("#uid").val();
		var name = $("#realname").val();
		var phone = $("#phone").val();
		var email = $("#email").val();
		//var suppovisor = $("#suppovisor").val();
		var role = $("#roleSelect option:selected").val();
		var group = $("#groupSelect option:selected").val();
		var javatag = $("#javatag").val();
		var htmltag = $("#htmltag").val();
		var webservicetag = $("#webservicetag").val();
		var etltag = $("#etltag").val();
		var selfIntro = $("#selfIntro").val();
		
		 var emailFilter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		 var phoneFilter = /^1[0-9]{10}/;
		 if (name.replace(/(^ *)|( *$)/g,'')==''){
			 swal("Registered fail!", "Pls input your correct name information.", "error");
		 }else if (!phoneFilter.test(phone)){
			 swal("Registered fail!", "Pls input your correct phone information.", "error");
		 }else if (!emailFilter.test(email)){
			 swal("Registered fail!", "Pls input your correct E-mail information.", "error");
		 }else if (selfIntro==''){
			 swal("Registered fail!", "Pls input your correct self-introduction information.", "error");
		 }else{
			$.ajax({
				url:"../regist",
				data:{uid:uid,name:name,telephone:phone,email:email,
					role:role,group:group,javatag:javatag,htmltag:htmltag,
					webservicetag:webservicetag,etltag:etltag,selfIntro:selfIntro},
				type:"POST",
				dataType:"json",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				cache:false,
				async:false,
				success:function(result) {
					if(result){
						$('#registerform').modal('hide');
						swal("Registered successfully!", "Congratulations!", "success"); 
						$("#realName").val(name);
					} else {
						swal("Registered fail!", "Pls input your correct information.", "error");
					}
				}
			});
		}
	});
	
}

function getUserInfo(username, headimgurl, openId) {
	$("#info_interact").css("display","block");
	$("#info_interact2").css("display","block");
	$("#info_imgurl").attr("src",headimgurl);
	//$("#info_username span").html(username+'<img src="../MetroStyleFiles/edit.png" style="height: 20px; cursor: pointer;padding-left:5px;"/>');
jQuery
	.ajax({
		type : "GET",
		url : "../userProfile/getMDLUserLists",
		data : {
			UID : openId
			
		},
		cache : false,
		success : function(data) {
			data = data.replace(/:null/g, ':"未注册"');
			data = '{"results":' + data + '}';
			var jsons = eval('(' + data + ')');
			if (jsons.results.length > 0) {
				$("#info_tag tr").html("");
				$("#info_interact img.like").attr("onclick","toLike('"+username+"','"+jsons.results[0].openid+"')");
				$("#info_interact2 span.like").text(jsons.results[0].like.number==""?0:jsons.results[0].like.number);
				if(jsons.results[0].role!="未注册"){
					$("#info_username span").html(jsons.results[0].realName);
					$("#info_interact img.zan").attr("onclick","recognizationPanelByPerson('"+jsons.results[0].realName+"')");
					$("#info_interact2 span.zan").text(jsons.results[0].CongratulateNum);
					if(jsons.results[0].tag!="未注册"){
						for(var j=0;j<jsons.results[0].tag.length;j++){
							var tag=jsons.results[0].tag[j];
							for (var key in tag) { 
								var td='<td>'
									+'				<div id="'+key+'" data-dimension="70" data-text="'
									+tag[key]
									+'%" data-info="" data-width="8" data-fontsize="18" data-percent="'
									+tag[key]
									+'" data-fgcolor="#FFF" data-bgcolor="#aaa" data-fill=""></div>'
									//+'" data-fgcolor="#61a9dc" data-bgcolor="#eee" data-fill="#ddd"></div>'
									+'				<span style="font-size:12px;">'
									+key
									+'</span>'
									+'														</td>';

								$("#info_tag tr").append(td);
								$('#'+key).circliful();
							}
						}
					}
					data = data.replace(/:"未注册"/g, ':"未编辑"');
					jsons = eval('(' + data + ')');
					$("#info_all").css('display','table');
					$("img.zan").css('display','block');
					$("span.zan").css('display','block');
					$("#info_username span").html(username+'<span style="font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;['+jsons.results[0].role+']</span>');
					$("#info_phone").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].phone);
					$("#info_group").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].groupid);
					$("#info_email").html("&nbsp;&nbsp;&nbsp;&nbsp;<a style='color:#fff;' href='mailto:"+jsons.results[0].email+"'>"+jsons.results[0].email+"</a>");
					$("#info_selfIntro").text(jsons.results[0].selfIntro);
				}else{
					$("#info_username span").html('未注册');
					$("img.zan").css('display','none');
					$("span.zan").css('display','none');
					$("#info_all").css('display','none');
					$("#info_selfIntro").text('');
				}
				$('#UserInfo').modal('show');
			}
		}
	});
}

</script>
<title>admin</title>
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
					<div class="Work_Mates_div2" id="Logo_div">
					</div>
					<!-- end logoElements-->

				</div>
				<div id="UserInfo" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel1"
									aria-hidden="true" data-backdrop="static">
									<div class="modal-body readmoreHpop"
										style="white-space: pre-line; padding: 0px;">
										<img src="../MetroStyleFiles/Close2.png" data-dismiss="modal"
											aria-hidden="true"
											style="float: right; height: 27px; cursor: pointer; margin-top: -15px; margin-right: 5px;" />
										<div id="userInfoDiv">
											<div id="info_interact"  style="position: absolute;width:100%;">
												<img class="like" src="../MetroStyleFiles/like.png"/>
												<img class="zan"  data-dismiss="modal" aria-hidden="true" onclick="recognizationPanel()" src="../MetroStyleFiles/zan.png"/>
											</div>
											<div id="info_interact2"
												style="position: absolute; width: 100%; display: block; margin-top: 45px;">
												<span class="like"
													style="float: left; margin-left: 25px; width: 40px; text-align: center;"></span>
												<span class="zan"
													style="float: right; margin-right: 30px; margin-top: -20px; width: 40px; text-align: center;"></span>
											</div>
											<img id="info_imgurl"
												src="http://wx.qlogo.cn/mmopen/soSX1MtHexV6ibXOvfzOoeEwjLFW3dyR80Mic1pzmg5b1qV0EFD4aegic9hic5iawRIDgJIImrY0XybC57j16ka4SabDCqy3TTtd2/0"
												alt="userImage" class="matesUserImage2" style="position: relative;">
											<div id="info_username" style="margin-top:-20px;">
												<span></span>
											</div>
											<table id="info_all">
												<tr>
													<td><img src="../MetroStyleFiles/group2.png"/></td>
													<td><div id="info_group"></div></td>
												</tr>
												<tr>
													<td><img src="../MetroStyleFiles/telephone2.png"/></td>
													<td><div id="info_phone"></div></td>
												</tr>
												<tr>
													<td><img src="../MetroStyleFiles/email2.png"/></td>
													<td><div id="info_email"></div></td>
												</tr>
											</table>
											<div id="info_selfIntro" style="margin-top:-10px;width:100%;text-align:center;"></div>
											<div style="width:100%; padding:0px;margin-top:-35px;margin-bottom:-40px;overflow-x: auto;">
												<table id="info_tag" style="margin-left:auto;margin-right:auto;">
													<tr>
													</tr>
												</table>											
											</div>
										</div>
									</div>
								</div>


							
								<div id="registerform" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel1"
									aria-hidden="true" data-backdrop="static">
									<div class="modal-body readmoreHpop"
										style="white-space: pre-line; padding: 0px;">
										<img src="../MetroStyleFiles/Close2.png" data-dismiss="modal"
											aria-hidden="true"
											style="float: right; height: 27px; cursor: pointer; margin-top: -15px; margin-right: 5px;" />
												<!-- <form id="registerFormSubmit" autocomplete="on"> -->
												    <table id="tableForm" style="margin-top:20px;">
												    <tr>
												        <td class="tdText"><img class='imgclass' src='../MetroStyleFiles/username2.png'/></td>
												        <td class="tdInput">
												          <input type="text" placeholder="请输入真实姓名" id="realname"  pattern="^[\u4E00-\u9FA0\s]+$|^[a-zA-Z\s]+$" required/>
												        </td>
												      </tr>
												      <tr>
												        <td class="tdText"><img class='imgclass' src='../MetroStyleFiles/telephone2.png'/></td>
												        <td class="tdInput">
												          <input type="text" placeholder="请输入电话号码" id="phone" pattern="^1[34578]\d{9}$" required/>
												        </td>
												      </tr>
												      <tr>
												        <td class="tdText"><img class='imgclass' src='../MetroStyleFiles/email2.png'/></td>
												        <td>
												          <input class="inputClass" placeholder="请输入邮箱地址" type="email" id="email" required/>
												        </td>
												      </tr>
												      <tr>
												        <td class="tdText"><img class='imgclass' src='../MetroStyleFiles/role2.png'/></td>
												        <td>
												          <select id="roleSelect">
															<option selected="selected">Individual Contributor</option> 
															<option>Team Lead</option>
															<option>Technical Lead</option>
															<option>Bussiness Analysis</option>
															<option>Other</option>
														</select>
												        </td>
												      </tr>
												      <tr>
												        <td class="tdText"><img class="imgclass" src="../MetroStyleFiles/group2.png"/></td>
												        <td>
												         <select id='groupSelect'>
															<option selected="selected">Chen, Hua-Quan</option>
															<option>Kang, Ning</option>
															<option>Zeng, Qiang</option>
															<option>Li, Jian-Jun</option>
															<option>Wu, Sha</option>
															<option>Other</option>
														</select>
												        </td>
												      </tr>
												      <tr>
												        <td class="tdText"><img class="imgclass" src="../MetroStyleFiles/selfIntro2.png"/></td>
												        <td>
												          <input class="inputClass" type="text" placeholder="请输入个人简介" id="selfIntro" required/>
												        </td>
												      </tr>
												      <tr class="sliderclass">
												        <td style="width:50px" >Java</td>
												        <td>
															<input id="javatag" class="easyui-slider" style="width:220px" data-options="
																		showTip:true
																	"/>
												        </td>
												      </tr>
												      <tr>
												        <td style="width:50px">H5</td>
												        <td>
															<input id="htmltag" class="easyui-slider" style="width:220px" data-options="
																		showTip:true
																	"/>
												        </td>
												      </tr>
												      <tr>
												        <td style="width:50px">WS</td>
												        <td>
															<input id="webservicetag" class="easyui-slider" style="width:220px" data-options="
																		showTip:true
																	"/>
												        </td>
												      </tr>
												      <tr>
												        <td style="width:50px">ETL</td>
												        <td>
															<input id="etltag" class="easyui-slider" style="width:220px" data-options="
																		showTip:true
																	"/>
												        </td>
												      </tr>
												      
												 </table>
											    <button class="btnAthena EbtnLess" style="background-color:#00B287;margin-bottom: -35px;" id="registerBtn">在一起吧</button>
										<!-- 	</form>  -->
									</div>
								</div>
				
				
				<div class="tab-pane" id="WorkMates">
					<div class="Work_Mates_div2" id="Work_Mates_div">
						<!-- <div class="Work_Mates_div_list_div2">
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
						</div> -->

					</div>
				</div>
				
				
				
			</div>
		</div>
	</div>
</body>
</html>