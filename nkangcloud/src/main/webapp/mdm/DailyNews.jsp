<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.nkang.kxmoment.util.OAuthUitl.SNSUserInfo,java.lang.*"%>
<%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.baseobject.ShortNews"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
//获取由OAuthServlet中传入的参数
SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo"); 
String state=(String)request.getAttribute("state");
String name = "";
String headImgUrl ="";
String uid="";
String openid="";
if(null != user) {
	openid=user.getOpenId();
	HashMap<String, String> res=MongoDBBasic.getWeChatUserFromOpenID(user.getOpenId());
	if(res!=null){
		if(res.get("HeadUrl")!=null){
			uid = user.getOpenId();
			headImgUrl=res.get("HeadUrl");
		}else{
			headImgUrl = user.getHeadImgUrl(); 
		}
		if(res.get("NickName")!=null){
			uid = user.getOpenId();
			name=res.get("NickName");
		}else{
			name = user.getNickname();
			headImgUrl = user.getHeadImgUrl(); 
			uid="oij7nt5GgpKftiaoMSKD68MTLXpc";
		}
	}else{
		name = user.getNickname();
		headImgUrl = user.getHeadImgUrl(); 
		uid="oij7nt5GgpKftiaoMSKD68MTLXpc";
	}
}





ArrayList<ShortNews> shortNews=MongoDBBasic.queryShortNews();
int size=5;
int realSize=shortNews.size();
if(shortNews.size()<=5){size=shortNews.size();}
boolean isInternalSeniorMgt=false;
boolean isInternalImtMgt=false;
if(uid.equals(openid)){
	isInternalSeniorMgt=MongoDBBasic.checkUserAuth(openid, "isInternalSeniorMgt");
	isInternalImtMgt=MongoDBBasic.checkUserAuth(openid, "isInternalImtMgt");
	MongoDBBasic.updateUser(openid);
}
SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd"); 
Date date=new Date();
String currentDate = format.format(date);
MongoDBBasic.updateVisited(openid,currentDate,"DailyNews",headImgUrl,name);

%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <title>永佳和行情实况直播-<%= shortNews.get(0).getContent()%></title>
 <script src="../mdm/uploadfile_js/jquery-1.11.2.min.js"></script>
 <script src="../Jsp/JS/iscroll.js"></script>
  <script src="../Jsp/JS/avgrund.js"></script>
<link rel="stylesheet" href="../Jsp/CSS/about.css">
<script	src="../MetroStyleFiles/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
<style>
/*ajax*/

.sk-circle {
  margin: 40px auto;
  width: 40px;
  height: 40px;
      position: absolute;
    top: 38%;
    left: 45%;
    display:none;
    z-index:100000;
     }
  .sk-circle .sk-child {
    width: 100%;
    height: 100%;
    position: absolute;
    left: 0;
    top: 0; }
  .sk-circle .sk-child:before {
    content: '';
    display: block;
    margin: 0 auto;
    width: 15%;
    height: 15%;
    background-color: white;
    border-radius: 100%;
    -webkit-animation: sk-circleBounceDelay 1.2s infinite ease-in-out both;
            animation: sk-circleBounceDelay 1.2s infinite ease-in-out both; }
  .sk-circle .sk-circle2 {
    -webkit-transform: rotate(30deg);
        -ms-transform: rotate(30deg);
            transform: rotate(30deg); }
  .sk-circle .sk-circle3 {
    -webkit-transform: rotate(60deg);
        -ms-transform: rotate(60deg);
            transform: rotate(60deg); }
  .sk-circle .sk-circle4 {
    -webkit-transform: rotate(90deg);
        -ms-transform: rotate(90deg);
            transform: rotate(90deg); }
  .sk-circle .sk-circle5 {
    -webkit-transform: rotate(120deg);
        -ms-transform: rotate(120deg);
            transform: rotate(120deg); }
  .sk-circle .sk-circle6 {
    -webkit-transform: rotate(150deg);
        -ms-transform: rotate(150deg);
            transform: rotate(150deg); }
  .sk-circle .sk-circle7 {
    -webkit-transform: rotate(180deg);
        -ms-transform: rotate(180deg);
            transform: rotate(180deg); }
  .sk-circle .sk-circle8 {
    -webkit-transform: rotate(210deg);
        -ms-transform: rotate(210deg);
            transform: rotate(210deg); }
  .sk-circle .sk-circle9 {
    -webkit-transform: rotate(240deg);
        -ms-transform: rotate(240deg);
            transform: rotate(240deg); }
  .sk-circle .sk-circle10 {
    -webkit-transform: rotate(270deg);
        -ms-transform: rotate(270deg);
            transform: rotate(270deg); }
  .sk-circle .sk-circle11 {
    -webkit-transform: rotate(300deg);
        -ms-transform: rotate(300deg);
            transform: rotate(300deg); }
  .sk-circle .sk-circle12 {
    -webkit-transform: rotate(330deg);
        -ms-transform: rotate(330deg);
            transform: rotate(330deg); }
  .sk-circle .sk-circle2:before {
    -webkit-animation-delay: -1.1s;
            animation-delay: -1.1s; }
  .sk-circle .sk-circle3:before {
    -webkit-animation-delay: -1s;
            animation-delay: -1s; }
  .sk-circle .sk-circle4:before {
    -webkit-animation-delay: -0.9s;
            animation-delay: -0.9s; }
  .sk-circle .sk-circle5:before {
    -webkit-animation-delay: -0.8s;
            animation-delay: -0.8s; }
  .sk-circle .sk-circle6:before {
    -webkit-animation-delay: -0.7s;
            animation-delay: -0.7s; }
  .sk-circle .sk-circle7:before {
    -webkit-animation-delay: -0.6s;
            animation-delay: -0.6s; }
  .sk-circle .sk-circle8:before {
    -webkit-animation-delay: -0.5s;
            animation-delay: -0.5s; }
  .sk-circle .sk-circle9:before {
    -webkit-animation-delay: -0.4s;
            animation-delay: -0.4s; }
  .sk-circle .sk-circle10:before {
    -webkit-animation-delay: -0.3s;
            animation-delay: -0.3s; }
  .sk-circle .sk-circle11:before {
    -webkit-animation-delay: -0.2s;
            animation-delay: -0.2s; }
  .sk-circle .sk-circle12:before {
    -webkit-animation-delay: -0.1s;
            animation-delay: -0.1s; }

@-webkit-keyframes sk-circleBounceDelay {
  0%, 80%, 100% {
    -webkit-transform: scale(0);
            transform: scale(0); }
  40% {
    -webkit-transform: scale(1);
            transform: scale(1); } }

@keyframes sk-circleBounceDelay {
  0%, 80%, 100% {
    -webkit-transform: scale(0);
            transform: scale(0); }
  40% {
    -webkit-transform: scale(1);
            transform: scale(1); } }
#footer {
    background: #DCD9D9;
    bottom: 0;
    color: #757575;
    font-size: 12px;
    padding: 10px 1%;
    position: fixed;
    text-align: center;
    width: 100%;
    z-index: 1002;
    left: 0;
}
</style>
<script type="text/javascript">
$(document).ajaxStart(function () {
	$(".sk-circle").show();
	$("#shadow").show();
    }).ajaxStop(function () {
    	$(".sk-circle").hide();
    	$("#shadow").hide();
    });
  
</script>
</head>
<body style="margin:0px">
<img src = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000EAc05&oid=00D90000000pkXM" hidden="hidden">
<div id="shadow" style="display:none;width:100%;height:100%;position:absolute;z-index:99999;top:0px;left:0px;opacity:0.4;background:black;"></div>
 <div class="sk-circle">
      <div class="sk-circle1 sk-child"></div>
      <div class="sk-circle2 sk-child"></div>
      <div class="sk-circle3 sk-child"></div>
      <div class="sk-circle4 sk-child"></div>
      <div class="sk-circle5 sk-child"></div>
      <div class="sk-circle6 sk-child"></div>
      <div class="sk-circle7 sk-child"></div>
      <div class="sk-circle8 sk-child"></div>
      <div class="sk-circle9 sk-child"></div>
      <div class="sk-circle10 sk-child"></div>
      <div class="sk-circle11 sk-child"></div>
      <div class="sk-circle12 sk-child"></div>
    </div>
<%if(isInternalSeniorMgt==true||isInternalImtMgt==true) { %>
<img onClick="javascript:publishNews();" style='width:100px;cursor:pointer;position: fixed;bottom: 40px;' src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000EUPTk&oid=00D90000000pkXM'>
<!-- 
<button style="position: absolute;top: 40px;right: 20px;padding: 4px 8px;background: white;border-style: none;border: 1px solid black;border-radius: 5px;" onClick="javascript:publishNews();">发布新闻</button>
 -->
 <% } %>
		<aside style="margin-top:50px;height:400px;position:absolute;width:80%;left:5%;top:80px;" id="default-popup" class="avgrund-popup">

			<h2 id="title" style="margin-bottom:10px;"></h2>

			<p style="margin-top:20px;" id="content">
			
			</p>

			<button style="position:absolute;bottom:20px;right:20px;padding:4px 8px;" onClick="javascript:closeDialog();">关闭</button>

		</aside>

<div style="padding-left: 10px;height: 70px;border-bottom: 4px solid #0066FF;padding-top: 10px;">
<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM" alt="" style="width:160px;height:85%;">
<div style="width:100%;text-align:right;margin-top:-80px;">
<ul class="nav pull-right top-menu" style="list-style: none;">
					<li class="dropdown"><a href="#" class="dropdown-toggle ui-link" data-toggle="dropdown" style="padding:5px;
    text-decoration: none;
    text-shadow: 0 1px 0 #fff;display: block;color:#777;font-weight:700;">
					欢迎您：<span class="username colorBlue" id="username" style="color:#2489ce;"><%=name %></span>
					</a> <span><a style="float: right;" class="ui-link"> <img id="userImage" src="<%=headImgUrl %>" alt="userImage" class="userImage" style="
    border-radius: 25px;
    height: 35px;
    width: 35px;">
						</a></span></li>
				</ul>
</div>
</div>
<div id="wrapper">
<div class="box scroller">

	<ul class="event_list">

<%for(int i=0;i<size;i++){ %>
		<li><span><%=shortNews.get(i).getDate() %><br></span><%if(isInternalSeniorMgt==true||isInternalImtMgt==true) { %><button style="position:absolute;top:65px;left:70px;background: white;border-style: none;border: 1px solid black;border-radius: 5px;" onclick="javascript:deleteNews('<%=shortNews.get(i).getMongoID() %>');">删除新闻</button><% } %><p><span onClick="javascript:openDialog(this);"><%=shortNews.get(i).getContent() %> </span></p></li>
<%} %>
	</ul>
	<div class="more"><i class="pull_icon"></i><span>上拉加载...</span></div>
	</div>
	</div>
<script>
$(function(){
	function openDialog(obj) {
		var content=$(obj).text();
		var title=$(obj).parent().siblings("span").text();
		$("#content").text(content);
		$("#title").text(title);
		Avgrund.show( "#default-popup" );

	}

	function closeDialog() {

		Avgrund.hide();

	}
	function deleteNews(obj) {
  	  $.ajax({
			type : "post",
			url : "../deleteShortNews",
			data:{
				id:obj
			},
			cache : false,
			success : function(data) {
				swal("恭喜!", "删除成功", "success");
				var html="";
				$.ajax({
	    			url : "../QueryShortNewsList",
					type:'post',
					success:function(data){
						var temp=5;
						if(data.length<=5){
							temp=data.length;
						}
						for (var i = 0; i < temp; i++) {
							html+="<li><span>"+data[i].date+"</span><button style='position:absolute;top:65px;left:70px;background: white;border-style: none;border: 1px solid black;border-radius: 5px;' onclick=\"javascript:deleteNews(\'"+data[i].mongoID+"\');\">删除新闻</button><p><span onClick='javascript:openDialog(this);'>"+data[i].content+" </span></p></li>";
						
						}
						realSize=data.length;
						size=temp;
						$('.scroller ul').html(html);
					},
					error:function(){
						console.log('error');
					}
				});
			}
			});

	}
	function publishNews() {
		var formText="<textarea style='height: 200px;font-size:14px;line-height:25px;width: 80%;margin-left: 10px;' id='news'></textarea>";
		swal({  
	        title:"发布新闻",  
	        text:formText,
	        html:"true",
	        showConfirmButton:true, 
			showCancelButton: true,   
			closeOnConfirm: false,  
	        cancelButtonText:"关闭",
	        confirmButtonText:"确定", 
	        animation:"slide-from-top"  
	      }, 
			function(inputValue){
	    	  if (inputValue === false){ return false; }
	    	  $.ajax({
	    			type : "post",
	    			url : "../CallCreateShortNews",
	    			data:{
	    				content:$("#news").val()
	    			},
	    			cache : false,
	    			success : function(data) {
	    				swal("恭喜!", data+"个人已收到您的新闻推送~", "success");
	    				var html="";
	    				$.ajax({
	    	    			url : "../QueryShortNewsList",
	    					type:'post',
	    					success:function(data){
	    						var temp=5;
	    						if(data.length<=5){
	    							temp=data.length;
	    						}
	    						for (var i = 0; i < temp; i++) {
	    							html+="<li><span>"+data[i].date+"</span><p><span onClick='javascript:openDialog(this);'>"+data[i].content+" </span></p></li>";
	    						
	    						}
	    						realSize=data.length;
	    						size=temp;
	    						$('.scroller ul').html(html);
	    					},
	    					error:function(){
	    						console.log('error');
	    					}
	    				});
	    			}
	    			});
	      });

	}
	window.openDialog=openDialog;
	window.closeDialog=closeDialog;
	window.publishNews=publishNews;
	window.deleteNews=deleteNews;
});

var realSize=<%=realSize %>;
var size=<%=size %>;
		var myscroll = new iScroll("wrapper",{
			onScrollMove:function(){
				if (this.y<(this.maxScrollY)) {
					$('.pull_icon').addClass('flip');
					$('.pull_icon').removeClass('loading');
					$('.more span').text('释放加载...');
				}else{
					$('.pull_icon').removeClass('flip loading');
					$('.more span').text('上拉加载...');
				}
			},
			onScrollEnd:function(){
				if ($('.pull_icon').hasClass('flip')) {
					if(size<realSize){
					$('.pull_icon').addClass('loading');
					$('.more span').text('加载中...');
					pullUpAction();}
					else
						{
						$('.more span').text('我是有底线的...');
						}
				}
				
			},
			onRefresh:function(){
				$('.more').removeClass('flip');
				$('.more span').text('上拉加载...');
			}
			
		});
		
		function pullUpAction(){
			setTimeout(function(){
		 		$.ajax({
	    			url : "../QueryShortNewsList2",
					type:'post',
					data:{
						startNumber:size,
						pageSize:5
					},
					success:function(data){
						for (var i = 0; i < data.length; i++) {
							$('.scroller ul').append("<li><span>"+data[i].date+"</span><p><span onClick='javascript:openDialog(this);'>"+data[i].content+" </span></p></li>");
						}
						size=size+data.length;
						myscroll.refresh();
					},
					error:function(){
						console.log('error');
					},
				});
				myscroll.refresh();
			}, 1000)
		}
		if ($('.scroller').height()<$('#wrapper').height()) {
			$('.more').hide();
			myscroll.destroy();
		}

		
		jQuery.ajax({
			type : "GET",
			url : "../QueryClientMeta",
			data : {},
			cache : false,
			success : function(data) {
				var jsons = eval(data);
				$('img.HpLogo').attr('src',jsons.clientLogo);
				$('span.clientCopyRight').text('©'+jsons.clientCopyRight);
				$('span.clientSubName').text(jsons.clientSubName);
				$('h2.clientName').text(jsons.clientName);
			}
		});

	</script>
	<div id="footer"><span class="clientCopyRight">©</span></div>
</body></html>