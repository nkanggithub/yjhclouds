<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%
String uid = request.getParameter("UID");
String hardcodeUID = "oij7nt5GgpKftiaoMSKD68MTLXpc";
String hardcodeUID2 = "oij7ntwDnybi-9PLvGjuRR_EcJYg";
if(MongoDBBasic.checkUserAuth(uid, "isITOperations")||hardcodeUID.equalsIgnoreCase(uid)||hardcodeUID2.equalsIgnoreCase(uid)){
}else{
	out.print("你没有查看该页面的权限！");
	return;
}


%>
<!DOCTYPE HTML>
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
<link rel="stylesheet" type="text/css" href="../nkang/admin/admin.css">
<link rel="stylesheet" type="text/css"
	href="../nkang/autocomplete/jquery-ui.css">

<script type="text/javascript" src="../nkang/easyui/jquery.min.js"></script>
<script type="text/javascript">
	var $113 = $;
</script>
<script type="text/javascript"
	src="../nkang/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../nkang/jquery-1.8.0.js"></script>
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
<style>
   #return-top{position:fixed;bottom:40px;right:10px; text-align:center; display:none;} 
.edit
{
	width: 60px;
    height: 100%;
    color: #fff;
    font-family:微软雅黑;
    text-align: center;
    position: absolute;
    top: 0px;
    right: -60px;
	font-size:14px;
    background: #438CD0;
    border-bottom: 1px solid #ccc;
}
.edit img {
    width:25px;height:auto;position:absolute;top:25px;margin-left: 2px;
}
.edit p
{
	width:50%;
	height:100%;
	line-height:145px;
	margin-right:auto;
	margin-left:auto;
}
.editBtn
{
	position: relative;
    left: -60px;
}
</style>
<script src="../Jsp/JS/fusioncharts.js" type="text/javascript"></script>

<link rel="stylesheet" href="../nkang/jquery.mobile.min.css" />
<script type="text/javascript" src="../nkang/jquery.mobile.min.js"></script>
<script>
$(function(){  
    $('#return-top').hide();  
    $(function(){  
        $(window).scroll(function(){  
            if($(window).scrollTop()>200){  
                $('#return-top').fadeIn(200);  
                }  
                else{$('#return-top').fadeOut(200);}  
            });  
            $('#return-top').click(function(){  
                $('body,html').animate({scrollTop:0},200);  
                return false;  
                });  
        });  
    });

var clientThemeColor,HpLogoSrc,LogoData;
$(window).load(function() {
	getLogoLists();
	getMDLUserLists();
	$(".Work_Mates_div_list_div2").live("swiperight",function(){
		$(this).css("overflow","hidden");
		$(this).removeClass("editBtn");
		$(this).remove(".edit");
	}); 
	$(".Work_Mates_div_list_div2").live("swipeleft",function(){
		$(this).css("overflow","visible");
		$(this).addClass("editBtn");
		var openid=$(this).find("span.openid").text();
		$(this).append("<div class='edit'><p onclick='showUpdateUserPanel(\""+openid+"\")'><img src='../mdm/images/edit.png' slt='' />编辑</p></div>");
		$(this).siblings().removeClass("editBtn");
		$(this).siblings().remove(".edit");
	});
	
});

function showUpdateUserPanel(openid){
	showCommonPanel();
	$(".Work_Mates_div_list_div2").removeClass("editBtn");
	$(".Work_Mates_div_list_div2").remove(".edit");
	$("body").append('<div id="UpdateUserPart" class="bouncePart" style="position:fixed;z-index:999;top:100px;width:80%;margin-left:10%;"><legend>编辑人员信息</legend><div id="UpdateUserPartDiv" style="margin-top:0px;margin-bottom: -20px;background-color:#fff;">'
			+'<center>正在加载中...</center>'		
	+'						</div>');
	$('#UpdateUserPart').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass("bounceInDown animated");
	 });
	jQuery.ajax({
		type : "GET",
		url : "../userProfile/getMDLUserLists",
		data : {
			UID : openid
		},
		cache : false,
		success : function(data) {
			data = data.replace(/:null/g, ':""');
			data = '{"results":' + data + '}';
			var jsons = eval('(' + data + ')');
			if (jsons.results.length > 0) {
				var isExternalUpStream=jsons.results[0].roleObj.externalUpStream;
				var isExternalCustomer=jsons.results[0].roleObj.externalCustomer;
				var isExternalPartner=jsons.results[0].roleObj.externalPartner;
				var isExternalCompetitor=jsons.results[0].roleObj.externalCompetitor;
				var isInternalSeniorMgt=jsons.results[0].roleObj.internalSeniorMgt;
				var isInternalImtMgt=jsons.results[0].roleObj.internalImtMgt;
				var isInternalQuoter=jsons.results[0].roleObj.internalQuoter;
				var isITOperations=jsons.results[0].roleObj.itoperations;
				var IsRegistered=jsons.results[0].IsRegistered;
				var registerDate=jsons.results[0].registerDate.replace(/\//g,"-");
				var realName=jsons.results[0].realName;
				var phone=jsons.results[0].phone;
				var email=jsons.results[0].email;
				var role=jsons.results[0].role;
				var companyName=jsons.results[0].companyName;
				var selfIntro=jsons.results[0].selfIntro;
				$("#UpdateUserPartDiv").html('<form id="atest">'
			            +'												<input type="hidden" name="uid" id="atest_uid" value="'+openid+'"/>'
			            +'												<table id="tableForm" style="margin-top:-20px;">'
			            +'													<tr>'
			            +'														<td><nobr>真实姓名:</nobr></td>'
			            +'														<td><input type="text" name="realName" value="'+realName+'"/></td>'
			            +'													</tr>'
			            +'													<tr>'
			            +'														<td>手机号码:</td>'
			            +'														<td><input type="text" name="phone" value="'+phone+'"/></td>'
			            +'													</tr>'
			            +'													<tr>'
			            +'														<td>电子邮箱:</td>'
			            +'														<td><input type="text" name="email" value="'+email+'"/></td>'
			            +'													</tr>'
			            +'													<tr>'
			            +'														<td>公司名称:</td>'
			            +'														<td><input type="text" name="companyName" value="'+companyName+'"/></td>'
			            +'													</tr>'
			            +'													<tr>'
			            +'														<td>销售代表:</td>'
			            +'														<td><input type="text"  placeholder="永佳和销售代表"  name="selfIntro" value="'+selfIntro+'"/></td>'
			            +'													</tr>'
			            +'													<tr>'
			            +'														<td>用户职位:</td>'
			            +'														<td><input type="text" name="companyRole" value="'+role+'"/></td>'
			            +'													</tr>'
			            +'													<tr>'
			            +'														<td>用户角色:</td>'
			            +'														<td align="left">'
			            +'															<nobr>'
			            +'															<input type="checkbox"  name="role" value="isExternalUpStream" '+(isExternalUpStream==true?'checked':'')+'/>上游客户'
			            +'															<input type="checkbox"  name="role" value="isExternalPartner"  '+(isExternalPartner==true?'checked':'')+'/>贸易商'
			            +'															</nobr><br/><nobr>'
			            +'															<input type="checkbox"  name="role" value="isExternalCustomer"  '+(isExternalCustomer==true?'checked':'')+'/>下游工厂'
			            +'															<input type="checkbox"  name="role" value="isExternalCompetitor"  '+(isExternalCompetitor==true?'checked':'')+'/>代理商'
			            +'															</nobr><br/><nobr>'
			            +'															<input type="checkbox"  name="role" value="isInternalImtMgt"  '+(isInternalImtMgt==true?'checked':'')+'/>信息发布'
			            +'															<input type="checkbox"  name="role" value="isInternalQuoter"  '+(isInternalQuoter==true?'checked':'')+'/>报价修改'
			            +'															</nobr><br/><nobr>'
			            +'															<input type="checkbox"  name="role" value="isInternalSeniorMgt"  '+(isInternalSeniorMgt==true?'checked':'')+'/>报价审核'
			            +'															<input type="checkbox"  name="role" value="isITOperations"  '+(isITOperations==true?'checked':'')+'/>后台管理'
			            +'															</nobr>'
			            +'														</td>'
			            +'													</tr>'
			            +'												    <tr>'
			            +'													    <td>注册时间:</td>'
			            +'													    <td align="left" class="tdText" >'
			            +'													    	<input name="registerDate" type="date" id="registerDate" required style="text-align: -webkit-center; width: 130px;"  value="'+registerDate+'">'
			            +'													    </td>'
			            +'												    </tr>'
			            +'												    <tr>'
			            +'												        <td>确认注册:</td>'
			            +'												        <td  align="left" class="tdText">'
			            +'												        	<input type="radio" name="isRegistered" value="true"  '+(IsRegistered=="true"?'checked="checked"':'')+' />是&nbsp;&nbsp;&nbsp;<input type="radio" name="isRegistered" '+(IsRegistered!="true"?'checked="checked"':'')+' value="false"/>否'
			            +'												        </td>'
			            +'												    </tr> '
			            +'												 </table>'
			            +'												 </form>'
			            +'												 <button class="btnAthena EbtnLess" style="background-color:#005CA1;margin-left: 90px;margin-top:15px;" id="updateUserInfoBtn">确定</button>');
				$("#updateUserInfoBtn").click(function(){
					var isRegistered = $("input[name='isRegistered']:checked").val();
					var registerDate = $("#registerDate").val();
					if(isRegistered==null || registerDate==null){
						swal("修改信息失败", "请输入正确的信息", "error");
					}
					$.ajax({
						url:"../updateUserInfo",
						data:$("#atest").serialize(),
						type:"POST",
						dataType:"json",
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						cache:false,
						async:false,
						success:function(result) {
							if(result){
								swal("更改成功!", "恭喜!", "success"); 
								hideBouncePanel();
								getMDLUserLists();
							} else {
								swal("更改失败!", "请填写正确的信息.", "error");
							}
						}
					});
				});
			}
		}
	});
}
function showLogoPanel(index){
	showCommonPanel();
	var thisLogo=LogoData[index];
	var Slide1="",Slide2="",Slide3="";
	if(thisLogo.slide!=null){
		Slide1=thisLogo.slide[0];
		Slide2=thisLogo.slide[1];
		Slide3=thisLogo.slide[2];
	}
	$("body").append('<div id="LogoEditPart" class="bouncePart" style="position:fixed;z-index:999;top:100px;width:80%;margin-left:10%;"><legend>LOGO编辑</legend><div style="margin-top:0px;margin-bottom: -20px;background-color:#fff;">'
			+'<form id="callUpdateClientMeta" >'
			+'<table style="margin-left:auto;margin-right:auto; id="logoEdit">'
			+'	<tr>'
			+'		<td>Logo的url：<input name="ClientCode" type="hidden" value="'+thisLogo.clientStockCode+'"/></td>'
			+'		<td><input name="ClientLogo" type="text" style="width:150px;"  value="'+thisLogo.clientLogo+'"/></td>'
			+'	</tr>'
			+'	<tr>'
			+'		<td>公司名称：</td>'
			+'		<td><input name="ClientName" type="text" style="width:150px;"  value="'+thisLogo.clientName+'"/></td>'
			+'	</tr>'
			+'	<tr>'
			+'		<td>部门名称：</td>'
			+'		<td><input name="ClientSubName" type="text" style="width:150px;" value="'+thisLogo.clientSubName+'"/></td>'
			+'	</tr>'
			+'	<tr>'
			+'		<td>版权归属：</td>'
			+'		<td><input name="ClientCopyRight" type="text" style="width:150px;" value="'+thisLogo.clientCopyRight+'"/></td>'
			+'	</tr>'
			+'	<tr>'
			+'		<td>主题颜色：</td>'
			+'		<td><input name="ClientThemeColor" type="text" style="width:150px;" value="'+thisLogo.clientThemeColor+'"/></td>'
			+'	</tr>'
			+'	<tr>'
			+'		<td>幻灯片url1：</td>'
			+'		<td><input name="Slide1" type="text" style="width:150px;" value="'+Slide1+'"/></td>'
			+'	</tr>'
			+'	<tr>'
			+'		<td>幻灯片url2：</td>'
			+'		<td><input name="Slide2" type="text" style="width:150px;" value="'+Slide2+'"/></td>'
			+'	</tr>'
			+'	<tr>'
			+'		<td>幻灯片url3：</td>'
			+'		<td><input name="Slide3" type="text" style="width:150px;" value="'+Slide3+'"/></td>'
			+'	</tr>'
			+'	<tr>'
			+'		<td colspan="2" style="text-align: center; padding: 0px;padding-top:10px;">	'
			+'			<button class="btnAthena EbtnLess" style="padding: 0px;background-color:'+clientThemeColor+';" id="submit_button" onclick="">保存</button>												'
			+'		</td>'
			+'	</tr>'
			+'</table>'
			+'</form>'
			+'							</div>');
	$('#submit_button').click(function(){
		jQuery.ajax({
			type : "GET",
			url : "../callUpdateClientMeta",
			data : $('#callUpdateClientMeta').serialize(),
			cache : false,
			success : function(data) {
				if(data=="true"){
					swal("恭喜！", "LOGO信息修改成功!", "success"); 
					hideBouncePanel();
					getLogoLists();
				}
				else{
					swal("LOGO信息修改失败!", "服务器出异常了", "error"); 	
				}
			}
		});
	});
	$('#LogoEditPart').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass("bounceInDown animated");
	    });
}
function showCommonPanel()
{
	$("body").append("<div  id='data_model_div' style='z-index:999;'  class='dataModelPanel'><img onclick='hideBouncePanel()' src='../MetroStyleFiles/EXIT1.png' style='width: 30px; height: 30px;position:absolute;top:20px;left:20px;' />	<img style='position:absolute;top:8px;right:20px;' class='HpLogo' src='"+HpLogoSrc+"' alt='Logo' class='HpLogo'><div style='width:100%;height:4px;background:"+clientThemeColor+";position:absolute;top:70px;'></div></div>");
	$('#data_model_div').removeClass().addClass('panelShowAnmitation').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass();
	    }); }
function hideBouncePanel()
{
	$("body").find(".bouncePart").remove();
	$("body").find("#data_model_div").remove();
	}
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
					LogoData=jsons;
					for (var i = 0; i < jsons.length; i++) {
					var temp=jsons[i];
					var buttonText;
					if(temp.clientActive=='Y'){
						buttonText='							<div style="float: right; margin-top: -80px; background-color: #777; color: #fff; font-weight:bold; font-size: 13px; padding: 3px;width:50px;text-align:center;border-radius:6px;">'
							+'								应用中'
							+'							</div>';
						clientThemeColor=temp.clientThemeColor;
						HpLogoSrc=temp.clientLogo;
						$('#logo_now').html('<img'
								+'				src="'+temp.clientLogo+'"'
								+'				alt="Logo" class="HpLogo"'
								+'				style="display: inline !important; height: 30px; float: none; padding: 0px; vertical-align: bottom;"><span'
								+'				class="clientSubName"'
								+'				style="font-size: 12px; padding-left: 7px; color: #333;">'+temp.clientSubName+'</span>'
								+'				<h1 style="color: #333; font-size: 18px;" class="clientName">'+temp.clientName+'</h1>');
						$('#logo_now_color').css('border-color',temp.clientThemeColor);
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
	//	+'<hr style="border:2px solid '+temp.clientThemeColor+';width:75%;padding:0px;margin-top:0px;">'
		+'							<p style="font-size:10px;margin-bottom:3px;margin-top:-3px;">©'+temp.clientCopyRight+'</p>'
		+buttonText
		+'							<div style="float: right; margin-top: -50px; background-color: #0197D6; color: #fff; font-weight:bold; font-size: 13px; padding: 3px;width:50px;text-align:center;border-radius:6px;" onclick="showLogoPanel('+i+')">'
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
	jQuery.ajax({
		type : "GET",
		url : "../ActivaeClientMeta",
		data : {clientCode:id},
		cache : false,
		success : function(data) {
			if(data=="true"){
				swal("请手动刷新该页面", "网站LOGO替换成功!", "success"); 
			}
			else{
				swal("网站LOGO替换失败!", "服务器出异常了", "error"); 	
			}
		}
	});
}
function getMDLUserLists() {
	var UpStreamList=0;
	var DownStreamList= 0;
	var PartnerList= 0;
	var CompetitorList= 0;
	var InternalList=0;
	var NoRoleList=0;
jQuery.ajax({
		type : "GET",
		url : "../userProfile/getMDLUserLists",
		data : {},
		cache : false,
		success : function(data) {
			data = '{"results":' + data + '}';
			var jsons = eval('(' + data + ')');
			var ul = "",regNumber=0;
			for (var i = jsons.results.length-1; i >0 ; i--) {
				var temp = jsons.results[i];
				var selfIntro="";
				var companyName=temp.companyName;
				var companyRole=temp.role;
				var workDay=temp.workDay;
				var tag=temp.tag;
				var tagHtml="";
				var congratulate="";
				var role= new Array();
				var infoPer=0;
				//************************
				var temp_B=true;
				if(temp.roleObj.externalUpStream){
					UpStreamList++;
					role.push("上游客户");
					temp_B=false;
				}
				if(temp.roleObj.externalCustomer){
					DownStreamList++;
					role.push("下游工厂");
					temp_B=false;
				}
				if(temp.roleObj.externalPartner){
					PartnerList++;
					role.push("贸易商");
					temp_B=false;
				}
				if(temp.roleObj.externalCompetitor){
					CompetitorList++;
					role.push("代理商");
					temp_B=false;
				}
				if(temp.roleObj.internalSeniorMgt){
					InternalList++;
					role.push("报价审核");
					temp_B=false;
				}
				if(temp.roleObj.internalImtMgt){
					InternalList++;
					role.push("信息发布");
					temp_B=false;
				}
//				if(temp.roleObj.internalBizEmp|| temp.roleObj.internalNonBizEmp){
				if(temp.roleObj.internalQuoter){
					InternalList++;
					role.push("报价修改");
					temp_B=false;
				}
				if(temp.roleObj.itoperations){
					InternalList++;
					role.push("后台管理");
					temp_B=false;
				}
				
				if(temp_B){
					NoRoleList++;
					role.push("未分类");
				}
				//************************
				
				if(temp.openid==$('#uid').val()){
					LastToLikeDate=temp.like.lastLikeDate;
					lastLikeTo=temp.like.lastLikeTo;
				}
				if(temp.realName!=null&&temp.realName!='null'){
					temp.nickname=temp.realName;
					infoPer+=40;
				}
				
				
				if(temp.phone!=null&&temp.phone!='null'&&temp.phone!=''){
					selfIntro="电话:"+temp.phone;
					infoPer+=40;
				}else{
					selfIntro="&nbsp;";
				}
				
				if(companyName==null||companyName=='null'||companyName==''){
					companyName="";
				}else{
					infoPer+=10;
				}
				if(companyRole==null||companyRole=='null'||companyRole==''){
					companyRole="";
				}else{
					companyRole="【"+companyRole+"】";
				}
				companyName+=companyRole;
				if(role!=null&&role!='null'){
					for(var j=0;j<role.length&&j<4;j++){
							tagHtml+='													<div class="tag" '
							+(role[j]=='未分类'?'id="tag" ':'')
							+'>'
							+role[j]
							+'													</div>';
					}
				}
				if(workDay==null||workDay=='null'||workDay==0){
					workDay="";
				}else{
					regNumber++;
					workDay='<div style="float:right;background-color:#eee;color:#333;font-size:13px;padding:3px;margin-right:5px;position:relative;margin-top:-28px;opacity:0.85;">'+workDay+'天</div>';
					if(temp.IsRegistered!="true"){
					workDay='<div style="float:right;background-color:#eee;color:red;font-size:13px;padding:3px;margin-right:5px;position:relative;margin-top:-28px;opacity:0.85;">待审核</div>';
					}
				}
				if(temp.selfIntro!=null&&temp.selfIntro!='null'&&temp.selfIntro!=''){
					selfIntro+='<div style="float:right;margin-right:5px;">销售代表:'+temp.selfIntro+'</div>';
				}else{
					
				}
				if(temp.congratulateNum==null||temp.congratulateNum=='null'||temp.congratulateNum==undefined||temp.congratulateNum==0){
					
				}else{
					congratulate='<div style="float:right;"><img src="../MetroStyleFiles/reward.png" style="height:25px;"/>'
						+ '<span style="font-size:12px;color:#07090B;font-weight:normal;">'+temp.congratulateNum+'</span><div>';
				}
				var li='	<li class="Work_Mates_div_list_div2">'
					+'                                           	 	<div class="Work_Mates_img_div2">'
					+'                                        			 <img src="'
					+ ((temp.headimgurl==null||temp.headimgurl=='')?'../MetroStyleFiles/image/user.jpg':temp.headimgurl)
					+ '" alt="userImage" class="matesUserImage" alt="no_username" /> '
					+'                                         		</div>'
					+'                                         		<div class="Work_Mates_text_div">'
					+'                                        			 <h2><span class="openid" style="display:none;">'+ temp.openid + '</span><span>'
					+ temp.nickname
					+ '</span><span class="role">'
					+companyName+'</span>'
					+'<span style="font-size:12px;color:green;float:right;">完善度'+infoPer+'%</span>'
					+'</h2>'
					+ '<div>'
					+tagHtml
					+'</div>'+workDay+'<div>'
					+'													<span class="selfIntro">'+selfIntro+'</span>'
					+'												</div>'
					+'                                        		</div>'
					+'                                                <div class="clear"></div>'
					+'                                          </li>';
				ul += li;
			}
			$("#Work_Mates_div").html(ul);
			$("#syncUser").click(function(){
				syncUser();
			});
			$("#refreshUser").click(function(){
				getMDLUserLists();
			});
			
			
			FusionCharts.ready(function () {
			    var revenueChart = new FusionCharts({
			        type: 'doughnut2d',
			        renderAt: 'chart-container',
			        width: '350',
			        height: '300',
			        dataFormat: 'json',
			        dataSource: {
			            "chart": {
			                "caption": "",
			                "subCaption": "",
			                "numberSuffix": "人",
			                "paletteColors": "#8e0000,#8e7080,#0075c2,#1aaf5d,#f2c500,#f45b00",
			                "bgColor": "#ffffff",
			                "showBorder": "0",
			                "use3DLighting": "0",
			                "showShadow": "0",
			                "enableSmartLabels": "0",
			                "startingAngle": "310",
			                "showLabels": "0",
			                "showPercentValues": "1",
			                "showLegend": "1",
			                "legendShadow": "0",
			                "legendBorderAlpha": "0",
			                "defaultCenterLabel": "总人数: "+jsons.results.length+"人",
			                "centerLabel": " $label",
			                "centerLabelBold": "1",
			                "showTooltip": "0",
			                "decimals": "0",
			                "captionFontSize": "14",
			                "subcaptionFontSize": "14",
			                "subcaptionFontBold": "0"
			            },
			            "data": [
			                {
			                    "label": "上游客户:"+UpStreamList+"人",
			                    "value": UpStreamList
			                }, 
			                {
			                    "label": "下游工厂:"+DownStreamList+"人",
			                    "value": DownStreamList
			                }, 
			                {
			                    "label": "贸易商:"+PartnerList+"人",
			                    "value": PartnerList
			                }, 
			                {
			                    "label": "代理商:"+CompetitorList+"人",
			                    "value": CompetitorList
			                }, 
			                {
			                    "label": "内部员工:"+InternalList+"人",
			                    "value": InternalList
			                }, 
			                {
			                    "label": "未分类:"+NoRoleList+"人",
			                    "value": NoRoleList
			                }
			            ],
			            "events": { 
			                "beforeLinkedItemOpen": function(eventObj, dataObj) { 
			                    console.log(eventObj);
			                    console.log(dataObj);
			                }
			            }
			        }
			    }).render();
			    
			    
			});
		}
	});
}
function syncUser(){
	$("#syncUser").attr("src","../MetroStyleFiles/loading.gif");
	jQuery.ajax({
		type : "GET",
		url : "../userProfile/getWechatUserLists",
		data : {},
		cache : false,
		success : function(data) {
			swal("同步成功！", data, "success"); 
			$("#syncUser").attr("src","../MetroStyleFiles/sync.png");
			getMDLUserLists();
		}
	});
}
function updateUserInfo(openId){
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
				//*  update data start */
				data = data.replace(/:"未编辑"/g, ':"未注册"');
				jsons = eval('(' + data + ')');
				if(jsons.results[0].roleObj.externalUpStream){
					$("#atest input[name='role'][value='isExternalUpStream']").attr("checked","true");
				}else{
					$("#atest input[name='role'][value='isExternalUpStream']").removeAttr("checked");
				}
				if(jsons.results[0].roleObj.externalCustomer){
					$("#atest input[name='role'][value='isExternalCustomer']").attr("checked","true");
				}else{
					$("#atest input[name='role'][value='isExternalCustomer']").removeAttr("checked");
				}
				if(jsons.results[0].roleObj.externalPartner){
					$("#atest input[name='role'][value='isExternalPartner']").attr("checked","true");
				}else{
					$("#atest input[name='role'][value='isExternalPartner']").removeAttr("checked");
				}
				if(jsons.results[0].roleObj.externalCompetitor){
					$("#atest input[name='role'][value='isExternalCompetitor']").attr("checked","true");
				}else{
					$("#atest input[name='role'][value='isExternalCompetitor']").removeAttr("checked");
				}
				if(jsons.results[0].roleObj.internalSeniorMgt){
					$("#atest input[name='role'][value='isInternalSeniorMgt']").attr("checked","true");
				}else{
					$("#atest input[name='role'][value='isInternalSeniorMgt']").removeAttr("checked");
				}
				if(jsons.results[0].roleObj.internalImtMgt){
					$("#atest input[name='role'][value='isInternalImtMgt']").attr("checked","true");
				}else{
					$("#atest input[name='role'][value='isInternalImtMgt']").removeAttr("checked");
				}
				if(jsons.results[0].roleObj.internalBizEmp){
					$("#atest input[name='role'][value='isInternalBizEmp']").attr("checked","true");
				}else{
					$("#atest input[name='role'][value='isInternalBizEmp']").removeAttr("checked");
				}
				if(jsons.results[0].roleObj.internalNonBizEmp){
					$("#atest input[name='role'][value='isInternalNonBizEmp']").attr("checked","true");
				}else{
					$("#atest input[name='role'][value='isInternalNonBizEmp']").removeAttr("checked");
				}
				if(jsons.results[0].roleObj.internalQuoter){
					$("#atest input[name='role'][value='isInternalQuoter']").attr("checked","true");
				}else{
					$("#atest input[name='role'][value='isInternalQuoter']").removeAttr("checked");
				}
				if(jsons.results[0].roleObj.itoperations){
					$("#atest input[name='role'][value='isITOperations']").attr("checked","true");
				}else{
					$("#atest input[name='role'][value='isITOperations']").removeAttr("checked");
				}
				$("#atest input[name='companyName']").val(jsons.results[0].companyName);
				
				if(jsons.results[0].IsActive !="未注册"){
					 jsons.results[0].IsActive=="true"?$("input[name='isActived']").eq(0).attr("checked","true"):$("input[name='isActived']").eq(1).attr("checked","true");
				}
				if(jsons.results[0].IsAuthenticated !="未注册"){
					jsons.results[0].IsAuthenticated=="true" ? $("input[name='isAuthenticated']").eq(0).attr("checked","true"):$("input[name='isAuthenticated']").eq(1).attr("checked","true");
				}
				if(jsons.results[0].IsRegistered !="未注册"){
					jsons.results[0].IsRegistered=="true"?$("input[name='isRegistered']").eq(0).attr("checked","true"):$("input[name='isRegistered']").eq(1).attr("checked","true");
				}
			    if(jsons.results[0].registerDate !="未注册"){
			    	$("#registerDate").val(jsons.results[0].registerDate.replace(/\//g,"-"));
			    } 
			    if(jsons.results[0].realName !="未注册"){
			    	$("#atest input[name='realName']").val(jsons.results[0].realName);
			    }else{
			    	$("#atest input[name='realName']").val("");
			    }
			    if(jsons.results[0].phone !="未注册"){
			    	$("#atest input[name='phone']").val(jsons.results[0].phone);
			    }else{
			    	$("#atest input[name='phone']").val("");
			    }
			    if(jsons.results[0].email !="未注册"){
			    	$("#atest input[name='email']").val(jsons.results[0].email);
			    }else{
			    	$("#atest input[name='email']").val("");
			    }
			    if(jsons.results[0].companyName !="未注册"){
			    	$("#atest input[name='companyName']").val(jsons.results[0].companyName);
			    }else{
			    	$("#atest input[name='companyName']").val("");
			    }
				//*  update data  end */
			}
		}
	});
	
	$('#UserInfo').modal('hide');
	$('#updateUserInfoForm').modal('show');
	$("#atest_uid").val(openId);
	$("#updateUserInfoBtn").click(function(){
		var isActived = $("input[name='isActived']:checked").val();
		var isAuthenticated = $("input[name='isAuthenticated']:checked").val();
		var isRegistered = $("input[name='isRegistered']:checked").val();
		var registerDate = $("#registerDate").val();
		if(isActived==null || isAuthenticated==null ||  isRegistered==null || registerDate==null){
			swal("修改信息失败", "请输入正确的信息", "error");
		}
		
		$.ajax({
			url:"../updateUserInfo",
			data:$("#atest").serialize(),
			type:"POST",
			dataType:"json",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			cache:false,
			async:false,
			success:function(result) {
				if(result){
					$('#updateUserInfoForm').modal('hide');
					swal("更改成功!", "恭喜!", "success"); 
				} else {
					swal("更改失败!", "请填写正确的信息.", "error");
				}
			}
		});
	});
	
}
</script>
<title>胖和微管理</title>
</head>
<body style="padding: 0px !important; margin: 0px;">
	<div class="navbar-inner" style="background-color: #fff !important;">
		<div class="container-fluid">
			<a 
				style="float: left; padding-top: 10px;"  id="logo_now"> 
			</a>
			<div class="clear"></div>
			<ul class="nav pull-right top-menu" style="margin-top: -70px;display:none;">
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
		<div id="logo_now_color" style="border-top: 4px solid #fff; padding: 5px;">
			<ul class="nav nav-tabs" id="myTabs"
				style="border-color: rgb(0, 179, 136);">
				<li class="active"><a href="#WorkMates" data-toggle="tab"
					style="border-right-color: rgb(0, 179, 136); border-top-color: rgb(0, 179, 136); border-left-color: rgb(0, 179, 136);">人员管理</a></li>
				<li><a href="#logoElements" data-toggle="tab"
					style="border-right-color: rgb(0, 179, 136); border-top-color: rgb(0, 179, 136); border-left-color: rgb(0, 179, 136);">版权管理</a></li>
			</ul>
			<div class="tab-content" id="dvTabContent"
				style="border: 0px; padding-top: 0px;margin-top:0px;">
				<div class="tab-pane" id="logoElements">
					<!-- start logoElements-->
					<div class="Work_Mates_div2" id="Logo_div">
					</div>
					<!-- end logoElements-->
				</div>
								<div id="updateUserInfoForm" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel1"
									aria-hidden="true" data-backdrop="static">
									<div class="modal-body"
										style="white-space: pre-line; padding: 0px;">
										<img src="../MetroStyleFiles/Close2.png" data-dismiss="modal"
											aria-hidden="true"
											style="float: right; height: 27px; cursor: pointer; margin-top: -15px; margin-right: 5px;" />
											<!-- 
												<form id="atest">
												<input type="hidden" name="uid" id="atest_uid"/>
												<table id="tableForm" style="margin-top:-20px;">
													<tr>
														<td><nobr>真实姓名:</nobr></td>
														<td><input type="text" name="realName"/></td>
													</tr>
													<tr>
														<td>手机号码:</td>
														<td><input type="text" name="phone"/></td>
													</tr>
													<tr>
														<td>电子邮箱:</td>
														<td><input type="text" name="email"/></td>
													</tr>
													<tr>
														<td>公司名称:</td>
														<td><input type="text" name="companyName"/></td>
													</tr>
													<tr>
														<td>用户角色:</td>
														<td align="left">
															<nobr>
															<input type="checkbox"  name="role" value="isExternalUpStream" />上游客户
															<input type="checkbox"  name="role" value="isExternalPartner" />贸易商
															</nobr><br/><br/><nobr>
															<input type="checkbox"  name="role" value="isExternalCustomer" />下游工厂
															<input type="checkbox"  name="role" value="isExternalCompetitor" />代理商
															</nobr><br/><br/><nobr>
															<input type="checkbox"  name="role" value="isInternalImtMgt" />信息发布
															<input type="checkbox"  name="role" value="isInternalQuoter" />报价修改
															</nobr><br/><br/><nobr>
															<input type="checkbox"  name="role" value="isInternalSeniorMgt" />报价审核
															<input type="checkbox"  name="role" value="isITOperations" />后台管理
															<!-- </nobr><br/><br/><nobr>
															<input type="checkbox"  name="role" value="isInternalNonBizEmp" />内部非业务员
															<input type="checkbox"  name="role" value="isInternalBizEmp" />内部业务员 --><!--
															</nobr>
														</td>
													</tr>
													<tr>
												        <td>是否激活:</td>
												        <td  align="left" class="tdText">
												        	<input type="radio" name="isActived" value="true"/>是&nbsp;&nbsp;&nbsp;<input type="radio" name="isActived" checked="checked" value="false"/>否
												        </td>
												    </tr>
												    <tr>
												        <td>是否验证:</td>
												        <td align="left"  class="tdText">
												        	<input type="radio" name="isAuthenticated" value="true"/>是&nbsp;&nbsp;&nbsp;<input type="radio" name="isAuthenticated" checked="checked" value="false"/>否
												        </td>
												    </tr>
												    <tr>
												        <td>是否注册:</td>
												        <td  align="left" class="tdText">
												        	<input type="radio" name="isRegistered" value="true"/>是&nbsp;&nbsp;&nbsp;<input type="radio" name="isRegistered" checked="checked" value="false"/>否
												        </td>
												    </tr> 
												    <tr>
													    <td>注册时间:</td>
													    <td align="left" class="tdText" >
													    	<input name="registerDate" type="date" id="registerDate" required style="text-align: -webkit-center; width: 130px;">
													    </td>
												    </tr>
												 </table>
												 </form>
												 <button class="btnAthena EbtnLess" style="background-color:#00B287;margin-top: -50px;" id="updateUserInfoBtn">确定</button>
												  -->
									</div>
								</div>
				
				
				<div class="tab-pane active" id="WorkMates">
					<img id="refreshUser"  src="../MetroStyleFiles/refresh2.png" style="height:25px;float:right;margin-top:8px;margin-left:15px;"/>
					<img id="syncUser"  src="../MetroStyleFiles/sync.png" style="height:30px;float:right;margin-top:7px;"/>
					<div id="chart-container" style="margin-left:auto;margin-right:auto;text-align:center;"></div>
					<div  style="position: absolute; top: 440px;overflow:hidden" data-role="page" style="padding-top:15px" data-theme="c">
 <ul class="Work_Mates_div2" id="Work_Mates_div" data-role="listview" data-autodividers="false" data-filter="true" data-filter-placeholder="输入关键字" data-inset="true" style="margin-top:15px">
					</ul></div>
					<div id="return-top" style="display: block;"><img class="scroll-top" src="../mdm/images/quotation2.gif" alt="" width="100px"></div>
				</div>
				
				
				
			</div>
		</div>
	</div>
</body>
</html>