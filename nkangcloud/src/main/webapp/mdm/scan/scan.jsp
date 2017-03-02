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
                <div class="pointer pointerAnim"></div>
                <div class="user">
                    <span class="userOne shadow" style="display: inline;">
                        <img src="1.jpg" width="100%">
                    </span>
                    <span class="userTwo shadow" style="display: inline;">
                        <img src="2.jpg" width="100%">
                    </span>
                    <span class="userThree shadow" style="display: inline;">
                        <img src="3.jpg" width="100%">
                    </span>
                    <span class="userFour shadow" style="display: inline;">
                        <img src="4.jpg" width="100%">
                    </span>
                    <span class="userFive shadow" style="display: inline;">
                        <img src="5.jpg" width="100%">
                    </span>
                    <span class="userSix shadow" style="display: inline;">
                        <img src="6.jpg" width="100%">
                    </span>
                    <span class="userSeven shadow" style="display: inline;">
                        <img src="7.jpg" width="100%">
                    </span>
                    <span class="userEight shadow" style="display: inline;">
                        <img src="8.jpg" width="100%">
                    </span>
                    <span class="userNine shadow" style="display: inline;">
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
            <a href="../personCharts.jsp"><button class="btn">停止扫描</button></a>
        </div>
    </div>
    <script>
        $(".btn").click(function () {
            $(".pointer").addClass("pointerAnim"); 
            var timeout = 1000; 
            var index = 0;
            var addClass;
            addClass = setInterval(function(){
                if(index >= $(".user span").length){
                    index = 0;
                    addClass = clearInterval(addClass);  
                    $(".user span").removeClass("flip").addClass("shadow")
                }else {
                    $(".user span").eq(index++).fadeIn(timeout).addClass("flip")
                }
            },timeout);
        })
    </script>

</body></html>