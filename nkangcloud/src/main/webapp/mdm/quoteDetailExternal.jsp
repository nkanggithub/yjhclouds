<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.nkang.kxmoment.util.OAuthUitl.SNSUserInfo,java.lang.*"%>
 <%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.baseobject.OnlineQuotation"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%@ page import="com.nkang.kxmoment.baseobject.ClientMeta"%>
<%	

//获取由OAuthServlet中传入的参数
SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo"); 
String state=(String)request.getAttribute("state");
String name = "";
String headImgUrl ="";
String uid="";
HashMap<String, String> res=new HashMap<String, String>();
String CompanyTelPhone="重庆永佳和塑胶有限公司【副总经理】邓立铭(电话：<a href='tel:13320204222'>13320204222</a>)";
if(null != user) {
	//String uid = request.getParameter("UID");
	uid = user.getOpenId();
	name = user.getNickname();
	headImgUrl = user.getHeadImgUrl();
	//MongoDBBasic.updateUser(uid);
	res=MongoDBBasic.getWeChatUserFromOpenID(uid);
	if(res!=null){
		if(res.get("HeadUrl")!=null){
			headImgUrl=res.get("HeadUrl");
		}
		if(res.get("NickName")!=null){
			name=res.get("NickName");
		}
		if(res.get("market0")!=null){
			CompanyTelPhone="重庆永佳和塑胶有限公司【"+res.get("market0")+"】"+res.get("market1") +"(电话：<a href='tel:"+res.get("market2") +"'>"+res.get("market2")+"</a>)";
		}
		if(res.get("market0")==null){
			res=new HashMap<String, String>();
			res.put("IsAuthenticated","false");
		}
	}else{
		uid="oij7nt5GgpKftiaoMSKD68MTLXpc";
		res=new HashMap<String, String>();
		res.put("IsAuthenticated","false");
	}
}else{
	out.print("用户不同意授权,未获取到用户信息！");
	return;
}
%>
<!Doctype html>
<html>
<head>
<title>重庆永佳和实时报价</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<link rel="stylesheet" href="../nkang/jquery.mobile.min.css" />
<script type="text/javascript" src="../nkang/jquery-1.8.0.js"></script>
<script type="text/javascript" src="../nkang/jquery.mobile.min.js"></script>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles//CSS/animation-effects.css"/>
<script type="text/javascript" src="../MetroStyleFiles/sweetalert.min.js"></script>

<script type="text/javascript" src="../Jsp/JS/fusioncharts.js"></script>
    	<script type="text/javascript" src="../Jsp/JS/fusioncharts.powercharts.js"></script>
	<script type="text/javascript" src="../Jsp/JS/fusioncharts.theme.fint.js"></script>
<style>
#echarts{height: 350px;width:750px;}
span.chartButton.now {
    background-color: #005CA1;
}
span.chartButton {
    cursor: pointer;
    padding: 8px 20px;
    background-color: #ccc;
    font-weight: bold;
    color: #fff;
}
#data_model_div
{
    position: fixed;
    top: 0;
    height: 130%;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 90;
    background-color: white;
    transition: opacity .15s linear;
}
 #return-top{position:fixed;bottom:40px;right:10px; text-align:center; display:none;z-index:9999;} 
.HpLogo {
    position: relative;
    top: 8px;
    left: 1%;
    width: 150px;
    height: 58px;
}
ul li.singleQuote{
	font-size:18px;
	border-bottom:1px solid #ccc;
	padding:20px;
	color:#333;
}
ul li.singleQuote .firstLayer.attention{
	color:#0761A5;
}
ul li.singleQuote .firstLayer .quoteTitle{
	float:left;
	cursor:pointer;
}
ul li.singleQuote .firstLayer .quoteTitle .tag{
	font-size:11px;
	background-color:orange;
	color:#fff;
	padding:1px 5px;
	font-weight:normol;
	font-family:微软雅黑;
	margin-left:8px;
}
ul li.singleQuote .firstLayer .quoteTitle .tag.approve{
	background-color:#0067B5;
}
ul li.singleQuote .firstLayer .quotePrice{
	float:right;
	color:#333;
}
ul li.singleQuote .firstLayer .quotePrice.high,ul li.singleQuote .firstLayer  .change.high{
	color:red;
}
ul li.singleQuote .firstLayer .quotePrice.normal,ul li.singleQuote .firstLayer  .change.normal{
	color:#333;
}
ul li.singleQuote .firstLayer.attention .quotePrice.high,ul li.singleQuote .firstLayer.attention  .change.high{
	color:red;
}
ul li.singleQuote .firstLayer .quotePrice.low,ul li.singleQuote .firstLayer  .change.low{
	color:green;
}
ul li.singleQuote .firstLayer .quotePrice.lose,ul li.singleQuote .firstLayer  .change.lose{
	color:#bbb;
}
ul li.singleQuote .firstLayer  .change{
	font-size:10px;
	float:right;
	margin-top:-35px;
	position:relative;
	clear:both;
}
.clear{
	clear:both;
}
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
    background: orange;
    border-bottom: 1px solid #ccc;
}
.edit img {
    width:25px;height:auto;position:absolute;top:8px;margin-left: 2px;
}
.edit p
{
	width:50%;
	height:100%;
	line-height:20px;
	margin-right:auto;
	margin-left:auto;
	font-weight:bold;
}
.edit.no{
background: #999;
}
.picClose
{
cursor:pointer;
}
.edit.no p
{
	line-height:20px;
}
.editBtn
{
	position: relative;
    left: -60px;
}
</style>

<script>
<%-- $(document).ready(function (){ 
	jQuery.ajax({
		type : "POST",
		url : "../../insertVisited",
		data : {openid:"<%=uid %>",
			pageName:"quoteDetailExternal",
			imgUrl:"<%=headImgUrl %>",
			nickName:"<%=name %>"},
		cache : false,
		success : function(resData) {
		}
    });
}); --%>
$(function(){
	   $(function(){  
	      	 $(window).scroll(function(){  
	           	  if($(window).scrollTop()>200){  
	                 $('#return-top').fadeIn(200);  
	               }  
	               else{
	            	 //  $('#return-top').fadeOut(200);
	            	 }  
	           });  
	           $('#return-top').click(function(){  
	               $('body,html').animate({scrollTop:0},200);  
	               return false;  
	            });  
	   });
	    
	/*    $(".quoteTitle").live("click",function(){
			$("#pic").css("display","block");
		});
		$(".picClose").live("click",function(){
			$("#pic").css("display","none");
		});
	    */
	   
	getAllDatas();
	$(".singleQuote").live("swiperight",function(){
		$(this).css("overflow","hidden");
		$(this).removeClass("editBtn");
		$(this).remove(".edit");
	}); 
	$(".singleQuote").live("swipeleft",function(){
		$(this).css("overflow","visible");
		$(this).addClass("editBtn");
		var tagNum=$(this).find('span.tag').length;
		var item=$(this).find('span.item').text();
		if(tagNum==0){
			$(this).append('<div class="edit"><p onclick="UpdateTag(\''+item+'\',\'add\',this)">申请<br/>报价</p></div>');
		}else{
			var tagName=$(this).find('span.tag').text();
			if(tagName=='已关注'){
				$(this).append('<div class="edit no"><p onclick="UpdateTag(\''+item+'\',\'del\',this)">取消<br/>关注</p></div>');
			}else{
				$(this).append('<div class="edit no"><p onclick="UpdateTag(\''+item+'\',\'del\',this)">取消<br/>申请</p></div>');
			}
		}
		$(this).siblings().removeClass("editBtn");
		$(this).siblings().remove(".edit");
	});
});
function ToCharPage(item){
	//location.href="priceCharts.jsp?itemNo="+item;
	showKMPanel(item);
}
function showKMPanel(item){
	showCommonPanel();
	$("body").append('<div id="UpdateUserKmPart" class="bouncePart" style="position:fixed;z-index:9999;top:100px;width:100%">'
	//+'<legend>编辑关注的牌号</legend><div id="UpdateUserPartDiv" style="margin-top:0px;margin-bottom: -20px;background-color:#fff;">'
			/* +'<div class="title">'+item+'的价格趋势分析</div>'
			+'	<div class="subtext">2016-12-30 - 2017-02-23</div>'
			+'	<div class="chart-box">'
			+'		<div id="echarts" _echarts_instance_="ec_1487834284265" style="width: 371px; -webkit-tap-highlight-color: transparent; -webkit-user-select: none; position: relative; background: transparent;"><div style="position: relative; overflow: hidden; width: 371px; height: 350px; cursor: default;"><canvas width="371" height="350" data-zr-dom-id="zr_0" style="position: absolute; left: 0px; top: 0px; width: 371px; height: 350px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas></div><div style="position: absolute; display: none; border: 0px solid rgb(51, 51, 51); white-space: nowrap; z-index: 9999999; transition: left 0.4s cubic-bezier(0.23, 1, 0.32, 1), top 0.4s cubic-bezier(0.23, 1, 0.32, 1); border-radius: 4px; color: rgb(255, 255, 255); font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 14px; font-family: &#39;Microsoft YaHei&#39;; line-height: 21px; padding: 5px; left: 211.95px; top: 122px; background-color: rgba(50, 50, 50, 0.701961);">02-13<br><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#7DB5E8"></span>价格 : 9,840</div></div>'
			+'	</div>'
			+'				</div>'); */
			+'<div class="title"><center>'+item+'的价格趋势分析</center></div>'
			+'	<div class="chart-box">'
			+'		<div id="chart-container" style="text-align: center;overflow-x: auto;"></div>'
			+'	</div>'
			+'<div style="text-align:center;margin-top:20px;"><span class="chartButton now">周</span><span class="chartButton">月</span></div>');
	$('#UpdateUserKmPart').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass("bounceInDown animated");
	 });
	 FusionCharts.ready(function () { 
	        var testProcChart = new FusionCharts({
	            type: 'errorline',
	            renderAt: 'chart-container',
	            width: '380',
	            height: '350',
	            dataFormat: 'jsonurl',
	            dataSource: '../PlasticItem/priceList?page=1&type=W&count=999&itemNo='+item
	        }).render();
	    });
	$('span.chartButton').click(function(){
		var text=$(this).text();
		$(this).addClass("now");
		$(this).siblings().removeClass("now");
		var type="W";
		if(text=="月"){
			type="M";
		}
	    FusionCharts.ready(function () { 
	        var testProcChart = new FusionCharts({
	            type: 'errorline',
	            renderAt: 'chart-container',
	            width: '380',
	            height: '350',
	            dataFormat: 'jsonurl',
	            dataSource: '../PlasticItem/priceList?page=1&type='+type+'&count=999&itemNo='+item
	        }).render();
	    });
	});
}
function showCommonPanel()
{
	$("body").append("<div  id='data_model_div' style='z-index:9999;'  class='dataModelPanel'><img onclick='hideBouncePanel()' src='../MetroStyleFiles/EXIT1.png' style='width: 30px; height: 30px;position:absolute;top:20px;left:20px;' />	<img style='position:relative;height: 50px;float: right;top:8px;margin-right:20px;width:auto;' class='HpLogo' src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM' alt='Logo' class='HpLogo'><div style='width:100%;height:4px;background:#005CA1;position:absolute;top:70px;'></div></div>");
	$('#data_model_div').removeClass().addClass('panelShowAnmitation').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass();
	    }); }
function hideBouncePanel()
{
	$("body").find(".bouncePart").remove();
	$("body").find("#data_model_div").remove();
}
function UpdateTag(item,flag,obj){
	$(".singleQuote").removeClass("editBtn");
	$(".singleQuote").remove(".edit");
	if(flag=='del'){
		$.ajax({
			 url:'../saveUserKM',
			 type:"POST",
			 data : {
				 openid : $("#openid").val(),
				 kmItem : item,
				 flag : flag
			 },
			 success:function(result){
				 if(result==true){
					 if(flag=='add'){
						 swal("关注成功 ", "恭喜你成功关注该牌号", "success");
						 $(obj).parent().parent(".singleQuote").find(".firstLayer").addClass("attention");
						 $(obj).parent().parent(".singleQuote").find(".firstLayer").find(".quoteTitle").append('<span class="tag">已关注</span>');
					 }else  if(flag=='del'){
						 var tagName=$(obj).parent().parent(".singleQuote").find(".firstLayer").find(".quoteTitle").find(".tag").text();
						 if(tagName=='已关注'){
							 swal("取消成功", "你取消了对该牌号的关注", "success");
						 }else{
							 swal("取消成功", "你取消了对该牌号的申请", "success");
						 }
						 $(obj).parent().parent(".singleQuote").find(".firstLayer").removeClass("attention");
						 $(obj).parent().parent(".singleQuote").find(".firstLayer").find(".quoteTitle").find(".tag").remove();
					 }
				 }else{
					 swal("操作失败", "请刷新页面后重试", "error");
				 }
			 }
		});	
	}else{
		$.ajax({
			 url:'../saveUserApproveKM',
			 type:"POST",
			 data : {
				 openid : $("#openid").val(),
				 kmItem : item,
				 flag : flag
			 },
			 success:function(result){
				 if(result==true){
					 if(flag=='add'){
						 swal("申请成功 ", "恭喜你成功申请该牌号，请耐心等待管理员审核", "success");
						 $(obj).parent().parent(".singleQuote").find(".firstLayer").addClass("attention");
						 $(obj).parent().parent(".singleQuote").find(".firstLayer").find(".quoteTitle").append('<span class="tag approve">申请中</span>');
					 }else  if(flag=='del'){
						 swal("取消成功", "你取消了对该牌号的关注", "success");
						 $(obj).parent().parent(".singleQuote").find(".firstLayer").removeClass("attention");
						 $(obj).parent().parent(".singleQuote").find(".firstLayer").find(".quoteTitle").find(".tag").remove();
					 }
				 }else{
					 swal("操作失败", "请刷新页面后重试", "error");
				 }
			 }
		});	
	}
}
function getAllDatas(){
	$.ajax({
		 url:'../queryUserKM',
		 type:"POST",
		 data : {
			 openid : $("#openid").val()
		 },
		 success:function(user){
			var KMLikeArr=user.kmLists;
			var KMLikeApproveArr=user.kmApproveLists;
		 	$.ajax({
			 url:'../PlasticItem/findList?page=1&count=999',
			 type:"POST",
			 success:function(res){
			 var resData=res.data;
			 if(resData.length)
			{
				var NoLikeArr=new Array();
				var LikeArr=new Array();
				var ApproveArr=new Array();
				if((KMLikeArr!=null&&KMLikeArr.length>0)||(KMLikeApproveArr!=null&&KMLikeApproveArr.length>0)){
					if(KMLikeArr==null){
						KMLikeArr=new Array();
					}
					if(KMLikeApproveArr==null){
						KMLikeApproveArr=new Array();
					}
						 for(var i=0;i<resData.length;i++){
						 		var itemTemp=$.trim(resData[i].itemNo);
						 		var index=$.inArray(itemTemp,KMLikeArr);
						 		var index2=$.inArray(itemTemp,KMLikeApproveArr);
						 		if(index>-1){
						 			resData[i]["like"]=true;
						 			LikeArr.push(resData[i]);
						 			KMLikeArr.splice(index,1);
						 		}else if(index2>-1){
						 			resData[i]["approve"]=true;
						 			ApproveArr.push(resData[i]);
						 			KMLikeApproveArr.splice(index2,1);
						 		}else{
						 			NoLikeArr.push(resData[i]);
						 		}
						}
		 		}else{
		 			NoLikeArr=resData;
		 		}
				var data=$.merge($.merge(LikeArr,ApproveArr), NoLikeArr);   
				 var html="";
				 var totalNum=0;
				 for(var i=0;i<data.length;i++){
					 if(data[i].itemNo!=""){
						 totalNum++;
						 var priceColor="lose";
						 var change='';
						 if(data[i].price=="暂停报价"||data[i].price==0){
							 priceColor="lose";
						 }else if(data[i].diffPrice==0){
							 priceColor="normal";
						 }else if(data[i].diffPrice<0){
							 priceColor="low";
							 change='		<span class="change low">'+data[i].diffPrice+'&nbsp;<b>↓</b></span>';
						 }else if(data[i].diffPrice>0){
							 priceColor="high";
							 change='		<span class="change high">'+data[i].diffPrice+'&nbsp;<b>↑</b></span>';
						 }

						 var tag='';
						 var attention='';
						 var priceStyle='';
						 var unit='<span class="unit">元/吨</span>';
						 
						 var myDate = new Date();
						 var nowHour=myDate.getHours();       //获取当前小时数(0-23)
						 var nowMinu=myDate.getMinutes();     //获取当前分钟数(0-59)
						 if(nowHour<10||nowHour>=18||data[i].priceStatus==1){
							 priceStyle=' style="text-decoration:line-through;color:#aaa;" ';
							 change='';
						 }
						 
						 if(data[i].priceStatus==0){
							 data[i].price="暂停报价";
							 unit='';
						 }
						 var openChart=' onclick="ToCharPage(\''+data[i].itemNo+'\')" ';
						 if(data[i]["like"]==true){
							 tag='<span class="tag">已关注</span>';
							 attention='attention';
						 }else{
							 if(data[i]["approve"]==true){
								 tag='<span class="tag approve">申请中</span>';
							 }
							 /* data[i].price="暂无报价";
							 unit='';
							 openChart='';
							 priceColor="lose";
							 change='';
							 priceStyle=''; */
						 }
						 
						 
						 html+='<li class="singleQuote">'
							 +'	<div class="firstLayer '+attention+'">'
							 +'		<div class="quoteTitle"><span class="item">'+data[i].itemNo+'</span>'+tag+'</div>'
							 +'		<div class="quotePrice '+priceColor+'" '+openChart+' '+priceStyle+'><span class="price">'+data[i].price+'</span>'+unit+'</div>'
			  				 + change
							 /*  +'		<span class="change high">+10</span>' */
							 +'		<div class="clear"></div>'
							 +'	</div>'
							 +'</li>'; 
					 }
				 }
				 $("#QuoteList").html(html);
				 
				 $("input.ui-input-text.ui-body-c").attr("placeholder","输入牌号【"+totalNum+"个牌号供您查询】");
				 }
			 }
		 });
		 		
		}
	});
}
</script>
</head>
<body>
<div id="pic" style="width:90%;border-radius:10px;background:rgba(0,0,0,0.7);height:80%;position:fixed;left:5%;top:10%;display:none;z-index:9999" >
<img style="position:absolute;left:5%;top:5%;width:90%;height:90%;" src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000EAc05&oid=00D90000000pkXM" alt=""/>
 <img class="picClose" style="position:absolute;top:10px;right:10px;width:15px;height:auto;" src="../MetroStyleFiles/Close2.png" alt=""/> 

</div>
<div id="return-top" style="display: block;"><img class="scroll-top" src="../mdm/images/quotation.gif" alt="" width="100px"></div>
<div style="padding:10px;padding-top:5px;border-bottom:2px solid #0067B6;position:relative;padding-bottom:0px;"> 
					<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM" alt="Logo" class="HpLogo" style="display:inline !important;height:35px !important;width:auto !important;float:none;padding:0px;vertical-align:bottom;padding-bottom:10px;">
					<span class="clientSubName" style="font-size:12px;padding-left:7px;color:#333;">市场如水 企业如舟</span>
					<h2 style="color:#333;font-size:18px;padding:0px;padding-left:5px;font-weight:bold;margin-top:5px;font-family:HP Simplified, Arial, Sans-Serif !important;" class="clientName">永佳和塑胶有限公司</h2>
					<p style="position: absolute;right: 10px;top: 0px;font-size: 15px;">欢迎您,<% if("true".equals(res.get("IsAuthenticated"))){ %><img style="height:20px;vertical-align: sub;padding-left: 5px;padding-right: 3px;" src="images/VIP.png"/><%} %><%=name %></p><img style="border-radius:25px;height:35px;width:35px;position:absolute;top:36px;right:10px;" src="<%=headImgUrl%>" alt=""/>
				<input id="openid" type="hidden" value="<%=uid%>"/>
				<marquee direction="left" scrollamount="6" onmouseover="this.stop()" onmouseout="this.start()" style="margin-top:-10px;">
<nobr><span style="font-size:12px;"><%=CompanyTelPhone %></span></nobr></marquee>

				</div>
<!--<input class="searchBox" id='hy' />-->
<div  style="position: absolute; top: 120px;overflow:hidden" data-role="page" style="padding-top:15px" data-theme="c">
 <ul id="QuoteList" data-role="listview" data-autodividers="false" data-filter="true" data-filter-placeholder="输入牌号" data-inset="true" style="margin-top:15px">
<!-- <li class="singleQuote">
	<div class="firstLayer  attention">
	
		<div class="quoteTitle"><span class="id">GE150</span><span class="tag">已关注</span></div>
		<div class="quotePrice high">￥<span class="price">11670</span></div>
		<span class="change high">+10</span>
		<div class="clear"></div>
	</div>
</li>
<li class="singleQuote">
	<div class="firstLayer">
	
		<div class="quoteTitle"><span >GE150</span></div>
		<div class="quotePrice low">￥<span>11670</span></div>
		<div class="clear"></div>
	</div>
</li>
 -->
</ul>
</div>
</body>
</html>