<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.nkang.kxmoment.util.OAuthUitl.SNSUserInfo,java.lang.*"%>
<%@ page import="com.nkang.kxmoment.baseobject.ArticleMessage"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="java.util.*,com.nkang.kxmoment.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%	
//获取由OAuthServlet中传入的参数
SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo"); 
String originalUid=request.getParameter("UID");
if(request.getParameter("UID")==null||request.getParameter("UID")==""){
	originalUid=request.getParameter("state"); 
}
String name = "";
String phone = "";
String headImgUrl ="";
boolean isFollow=false;
if(null != user) {
	//String uid = request.getParameter("UID");
	String uid = user.getOpenId();
	name = user.getNickname();
	headImgUrl = user.getHeadImgUrl();
	HashMap<String, String> res=MongoDBBasic.getWeChatUserFromOpenID(uid);
	if(res!=null){
		isFollow=true;
		if(res.get("HeadUrl")!=null){
			headImgUrl=res.get("HeadUrl");
		}
		if(res.get("NickName")!=null){
			name=res.get("NickName");
		}
		if(res.get("phone")!=null){
			phone=res.get("phone");
		}
	}
	
	SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd"); 
	Date date=new Date();
	String currentDate = format.format(date);
	if(uid.equals(originalUid)){
		MongoDBBasic.updateVisited(user.getOpenId(),currentDate,"NotificationCenter",user.getHeadImgUrl(),name);
	}
	else
	{
		MongoDBBasic.updateVisited(user.getOpenId(),currentDate,"NotificationCenter",user.getHeadImgUrl(),name);
		if(!"STATE".equals(originalUid)){
		HashMap<String, String> resOriginal=MongoDBBasic.getWeChatUserFromOpenID(originalUid);
		System.out.println("originalUid:"+originalUid);
		System.out.println("user.getHeadImgUrl():"+user.getHeadImgUrl());
		System.out.println("name:"+name);
		System.out.println("resOriginal.get('HeadUrl')----"+resOriginal.get("HeadUrl"));
		System.out.println("resOriginal.get('NickName')----"+resOriginal.get("NickName"));
		MongoDBBasic.updateShared(originalUid,currentDate,"NotificationCenter",user.getHeadImgUrl(),name,resOriginal.get("HeadUrl"),resOriginal.get("NickName"));
		}
	}
}


//SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd"); 
//Date date=new Date();
//String currentDate = format.format(date);
String num = request.getParameter("num");
List<ArticleMessage> nList=MongoDBBasic.getArticleMessageByNum(num);
MongoDBBasic.updateVisitedNumber(num);
ArticleMessage n=new ArticleMessage();
/* n.setContent("此部分功能正在开发中，请等待。。");
n.setTitle("Notification!");
n.setTime("2017/2/10 16:42"); */
  if(!nList.isEmpty()){
	n=nList.get(0);
}
 

String ticket=RestUtils.getTicket();
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>
  <meta charset="utf-8">
  <title>消息推送</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/style.css"/>
<script type="text/javascript" src="../Jsp/JS/jquery-1.8.0.js"></script>
<script	src="../MetroStyleFiles/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
<script type="text/javascript" src="../Jsp/JS/jquery.sha1.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript">
var url = window.location.href;
if(url.indexOf('#')!=-1){
	url=url.substr(0,(url.indexOf('#')-1));
}
var string1='jsapi_ticket=<%=ticket%>'
	+'&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url='+url;
var signature=$.sha1(string1);
wx.config({
        debug: false,
        appId: '<%=Constants.APP_ID%>'+'',
        timestamp: 1414587457,
        nonceStr: 'Wm3WZYTPz0wzccnW'+'',
        signature: signature+'',
        jsApiList: [
            // 所有要调用的 API 都要加到这个列表中
            'checkJsApi',
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo'
          ]
    });
 wx.ready(function () {
	/*  wx.checkJsApi({
            jsApiList: [
                'getLocation',
                'onMenuShareTimeline',
                'onMenuShareAppMessage'
            ],
            success: function (res) {
                alert(JSON.stringify(res));
            }
     }); */
     var shareTitle='<%=n.getTitle().replaceAll("\'", "\"")%>';
     var shareDesc='<%= n.getContent().replaceAll("(\r\n|\r|\n|\n\r|<br/>|<br />|<br>|<b>|</b>)", "").replaceAll("\'", "\"") %>';
     var shareImgUrl='<%=n.getPicture().replaceAll("\'", "\"")%>';
	//----------“分享给朋友”
     wx.onMenuShareAppMessage({
         title: shareTitle, // 分享标题
         desc: shareDesc, // 分享描述
         link: url, // 分享链接
         imgUrl: shareImgUrl, // 分享图标
         type: '', // 分享类型,music、video或link，不填默认为link
         dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
         success: function () { 
             // 用户确认分享后执行的回调函数、
            // alert("用户成功分享了该网页");
         },
         cancel: function () { 
             // 用户取消分享后执行的回调函数
           //  alert("用户取消了分享");
         },
         fail: function (res) {
          //   alert(JSON.stringify(res));
         }
     });
     //------------"分享到朋友圈"
     wx.onMenuShareTimeline({
         title: shareTitle, // 分享标题
         link:url, // 分享链接
         imgUrl: shareImgUrl, // 分享图标
         success: function () { 
             // 用户确认分享后执行的回调函数
          //   alert("用户成功分享了该网页");
         },
         cancel: function () { 
             // 用户取消分享后执行的回调函数
         //    alert("用户取消了分享");
         },
         fail: function (res) {
          //   alert(JSON.stringify(res));
         }
     });
     wx.error(function(res){
         // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
         alert("errorMSG:"+res);
     });
 });
</script>
</head>
<body style="margin:0;">
<div style="width:100%;text-align:right;right:0px;position: absolute;margin-top:-10px;"><b>
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
				</b></div>

<%if(n.getTitle().indexOf("邀请函")>0){%>
				<img id="signUp" style="width: 70px;cursor:pointer;position: fixed;bottom: 50px;right: 0px;z-index: 1002;" src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000EUjnM&oid=00D90000000pkXM">
				<%} %>
				
				
            <table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;border-collapse:collapse;border-spacing:0;display:table;">
             <tbody>
              <tr>
               <td width="270" valign="top" style="width:202.5pt;padding:0in 7.5pt 0in 15.0pt;padding-left:0px;">
                <table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;border-collapse:collapse;border-spacing:0">
                 <tbody>
                  <tr>
                   <td valign="top" style="width:60%"><p class="MsoNormal" style="/* line-height:14.0pt */"><span style="font-size:11.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black"><b><img id="_x0000_i1025" src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&oid=00D90000000pkXM" style="
    width: 60%;
"></b>
                      <o:p></o:p></span></p></td>
                  </tr>
                 </tbody>
                </table></td>
               <td width="270" valign="bottom" style="width:202.5pt;padding:0in 15.0pt 0in 7.5pt;">
                <table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;border-collapse:collapse;border-spacing:0;">
                 <tbody>
                  <tr>
                   <td valign="top" style="padding:0in 0in 0in 0in"><h1 align="right" style="margin:0in;margin-bottom:.0001pt;text-align:right;margin-bottom:0!important;color:inherit;margin-bottom: 24px!important;word-wrap:normal;"><span style="font-size: 12px;font-family:&quot;Arial&quot;,sans-serif;color:black;">
                      <o:p></o:p></span></h1></td>
                  </tr>
                 </tbody>
                </table></td>
              </tr>
             </tbody>
            </table>
            <table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;/* border-collapse:collapse; *//* display:table; *//* border-spacing: 0; */">
             <tbody>
              <tr>
               <td width="588" valign="top" style="width:441.0pt;/* padding:0in 0in 0in 0in */">
                <table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;/* border-collapse:collapse; */padding-bottom:0!important;">
                 <tbody>
                  <tr>
                   <td valign="top"><p class="MsoNormal" style="margin-top:0px;"><span style="font-size:11.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black"><img id="_x0000_i1026" src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000EUjzD&oid=00D90000000pkXM" style="
    width: 100%;
">
                      <o:p></o:p></span></p></td>
                   <td width="0" valign="top" style="width:.3pt;padding:0in 0in 0in 0in"><p class="MsoNormal" style="line-height:14.0pt"><span style="font-size:11.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black">&nbsp;
                      <o:p></o:p></span></p></td>
                  </tr>
                 </tbody>
                </table></td>
              </tr>
             </tbody>
            </table>
    
            <p style="margin: 20px;line-height:14.0pt;"><strong><span style="font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black"><%=n.getTitle() %></span></strong><span style="font-size:11.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black">
              <o:p></o:p></span></p>
            <table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;border-collapse:collapse;display:table;border-spacing: 0;">
             <tbody>
              <tr>
               <td width="588" valign="top" style="width: 100%;/* padding:0in 0in 15.0pt 0in; */-moz-hyphens: auto;/* border-collapse:collapse!important; *//* word-wrap: break-word; */">
                <table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;/* border-collapse:collapse; *//* border-spacing: 0; */">
                 <tbody>
                  <tr>
                   <td valign="top" style="padding:0in 0in 0in 0in;width: 100%;">
                    <table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;border-collapse:collapse;border-spacing: 0">
                     <tbody>
                      <tr>
                       <td width="100%" valign="top" style="width:100.0%;border:none;border-top:solid #A6A6A6 1.0pt;padding:0in 0in 0in 0in"></td>
                      </tr>
                     </tbody>
                    </table></td>
                   <td width="0" style="width:10%;/* padding:0in 0in 0in 0in */"><p class="MsoNormal" style="line-height:14.0pt"><span style="font-size:11.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black"><o:p></o:p></span></p></td>
                  </tr>
                 </tbody>
                </table></td>
              </tr>
             </tbody>
            </table>
<p style="margin: 10px;margin-left:20px;margin-bottom: 20px;line-height:14.0pt;"><span style="font-size: 14px;font-family:&quot;Arial&quot;,sans-serif;color:black;"><%=n.getContent() %><o:p></o:p></span></p>

	<div id="footer">
		<span class="clientCopyRight"><nobr></nobr></span>
	</div>
	<script>
	var code="";
	$(function(){
		$("#signUp").click(function(){
			var formText="<p style='width:30%;float:left;height:40px;line-height:40px;'>姓名：</p><input id='name' style='margin-top:0px;width:50%;height:35px;display:block;float:left;' type='text' value=''/>"
		    +"<p style='width:30%;float:left;height:40px;line-height:40px;'>电话：</p><input id='phone' style='margin-top:0px;width:50%;height:35px;display:block;float:left;' type='text' value='' />"
		    +"<p style='width:30%;float:left;height:40px;line-height:40px;'></p><input id='sendCode' onclick='sendValidateCode()' style='margin-top:0px;width:50%;height:35px;display:block;float:left;background-color:#8CD4F5;color:#fff;' type='button' value='获取验证码'/>"
		    +"<p style='width:30%;float:left;height:40px;line-height:40px;'>验证码：</p><input id='code' style='margin-top:0px;width:50%;height:35px;display:block;float:left;' type='text'/>";
			swal({  
		        title:"我要报名",  
		        text:formText,
		        html:"true",
		        showConfirmButton:"true", 
				showCancelButton: true,   
				closeOnConfirm: false,  
		        confirmButtonText:"提交",  
		        cancelButtonText:"取消",
		        animation:"slide-from-top"  
		      }, 
				function(inputValue){
		    	  if (inputValue === false){ return false; }
		    	  if(code!=$("#code").val()){
		    		  alert("你输入的验证码不正确！");
		    		  return false;
		    	  }
		    	  $.ajax({
		  	        cache: false,
		  	        type: "POST",
		  	        url:"../saveArticleMessageSignUp",
		  	        data:{
		  	        	phone:$("#phone").val(),
		  	        	name:$("#name").val(),
		  	        	num:<%=num %> 
		  	        },
		  	        async: true,
		  	        error: function(request) {
		  	            alert("Connection error");
		  	        },
		  	        success: function(data) {
		  	        	if(data){
		  		    	  swal("恭喜!", "报名成功！", "success");
		  	        	}
		  	        }
		  	    });
		      }
		     );
		});
	});
	
	function MathRand() 
	{ 
		var Num=""; 
		for(var i=0;i<6;i++) 
		{ 
		Num+=Math.floor(Math.random()*10); 
		} 
		return Num;
	} 
	function sendValidateCode(){
		var phone = $("#phone").val();
		var phoneFilter = /^1[0-9]{10}/;
		if(""==phone||!phoneFilter.test(phone)){
			 alert("发送失败!请输入正确的号码信息");
			 return;
		}else{
       	$("#sendCode").attr("disable","true");
       	$("#sendCode").css("background-color","#ccc");
		code=MathRand();
		$.ajax({
	        cache: false,
	        type: "POST",
	        url:"../sendValidateCode",
	        data:{
	        	phone:phone,
	        	code:code	
	        },
	        async: true,
	        error: function(request) {
	            alert("Connection error");
	        },
	        success: function(data) {
	        	if(data=="OK"){
	        	alert("验证码已发送至"+phone+",请耐心等候");
	        	}
	        }
	    });}
		}
         jQuery.ajax({
     		type : "GET",
     		url : "../QueryClientMeta",
     		data : {},
     		cache : false,
     		success : function(data) {
     			var jsons = eval(data);
     			$('span.clientCopyRight').text('©'+jsons.clientCopyRight);
     		}
     	});
         </script>
</body>


</html>
