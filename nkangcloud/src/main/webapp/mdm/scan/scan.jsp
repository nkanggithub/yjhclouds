<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.baseobject.GeoLocation"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%@ page import="com.nkang.kxmoment.baseobject.ClientMeta"%>
<%	


String AccessKey = RestUtils.callGetValidAccessKey();
String uid = request.getParameter("UID");
WeChatUser wcu;
session.setAttribute("UID", uid);
if (session.getAttribute("location") == null) {
	GeoLocation loc = RestUtils.callGetDBUserGeoInfo(uid);
	wcu = RestUtils.getWeChatUserInfo(AccessKey, uid);
	session.setAttribute("wcu", wcu);
} else {
	wcu = (WeChatUser) session.getAttribute("wcu");
}
%>
<html lang="zh-CN"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <title>扫描附近永佳和客户</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="ming.css">
    <script src="jquery.min(1).js"></script>
</head>
<body>
    <div class="container">
        <div id="load">
            <div class="border1">
                <div class="pointer"></div>
                <div class="user">
                    <span class="userOne">
                        <img src="1.jpg" width="100%">
                    </span>
                    <span class="userTwo">
                        <img src="2.jpg" width="100%">
                    </span>
                    <span class="userThree">
                        <img src="3.jpg" width="100%">
                    </span>
                    <span class="userFour">
                        <img src="4.jpg" width="100%">
                    </span>
                    <span class="userFive">
                        <img src="5.jpg" width="100%">
                    </span>
                    <span class="userSix">
                        <img src="6.jpg" width="100%">
                    </span>
                    <span class="userSeven">
                        <img src="7.jpg" width="100%">
                    </span>
                    <span class="userEight">
                        <img src="8.jpg" width="100%">
                    </span>
                    <span class="userNine">
                        <img src="9.jpg" width="100%">
                    </span>
                </div>
                <div class="border2">
                    <div class="border3">
                        <div class="radar">
                            <span class="avatar">
                                <img src="<%=wcu.getHeadimgurl() %>" width="100%">
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <button class="btn">开始扫描</button>
        </div>
    </div>
    <script>
    
    
    
    jQuery.ajax({
		type : "GET",
		url : "../../CallGetWeChatUserDistanceList?openid=<%=uid %>",
		cache : false,
		success : function(data) {
			for (var i = 0; i < data.length ; i++) {
				var temp = data[i];
				if(i==0){
					$("span.userOne img").attr("src",temp.headimgurl);
				}else if(i==1){
					$("span.userTwo img").attr("src",temp.headimgurl);
				}else if(i==2){
					$("span.userThree img").attr("src",temp.headimgurl);
				}else if(i==3){
					$("span.userFour img").attr("src",temp.headimgurl);
				}else if(i==4){
					$("span.userFive img").attr("src",temp.headimgurl);
				}else if(i==5){
					$("span.userSix img").attr("src",temp.headimgurl);
				}else if(i==6){
					$("span.userSeven img").attr("src",temp.headimgurl);
				}else if(i==7){
					$("span.userEight img").attr("src",temp.headimgurl);
				}else if(i==8){
					$("span.userNine img").attr("src",temp.headimgurl);
				}
			}
		}
	});
    
    
    
    
    $(".btn").html("停止扫描");
	$(".pointer").addClass("pointerAnim");  //添加雷达旋转动画
	var timeout = 1000;  //每隔1000ms
	var index = 0;
	var addClass;
	addClass = setInterval(function(){
		if(index >= $(".user span").length){
			index = 0;
			addClass = clearInterval(addClass);  //结束循环
			$(".user span").removeClass("flip").addClass("shadow")
		}else {
			$(".user span").eq(index++).fadeIn(timeout).addClass("flip")
		}
	},timeout);
	
	
       $(".btn").click(function () {
			window.location.href="../personCharts.jsp?openid=<%=uid %>";
       });
    </script>

</body></html>