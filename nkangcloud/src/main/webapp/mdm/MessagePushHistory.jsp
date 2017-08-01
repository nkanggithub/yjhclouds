<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.nkang.kxmoment.util.*"%>
<%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.baseobject.ArticleMessage"%>
<%
List<ArticleMessage> ams=MongoDBBasic.getArticleMessageByNum("");
String uid=request.getParameter("UID");
int size=5;
int realSize=ams.size();
if(ams.size()<=5){size=ams.size();}

boolean isInternalSeniorMgt=MongoDBBasic.checkUserAuth(uid, "isInternalSeniorMgt");
boolean isInternalImtMgt=MongoDBBasic.checkUserAuth(uid, "isInternalImtMgt");

%>
<!DOCTYPE html>
<html lang="en" class="csstransforms csstransforms3d csstransitions">
<head>
<title>图文统计</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
			 <script src="../mdm/uploadfile_js/jquery-1.11.2.min.js"></script>
			 <script src="../Jsp/JS/iscroll.js"></script>
			 <link rel="stylesheet" href="../Jsp/CSS/about.css">
<style>
*{
margin:0;}
.topPic
{
position:relative;
height:180px;
background:blue;
width:100%;
}
.topPic img
{
width:100%;
height:100%;
}
.topPic_title
{
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
    height: 1.4em;
    font-size: 16px;
    padding: 20px 50px 12px 13px;
    background-image: -webkit-linear-gradient(top,rgba(0,0,0,0) 0,rgba(0,0,0,.7) 100%);
    background-image: linear-gradient(to bottom,rgba(0,0,0,0) 0,rgba(0,0,0,.7) 100%);
    color: #fff;
    text-shadow: 0 1px 0 rgba(0,0,0,.5);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
}
.navi{
height:45px;
width:100%;
background:#f2f2f2;
}
.navi p{
line-height:45px;
text-align:center;
width:50%;
float:left;
font-size:15px;
}
.singleMes{
height: 70px;
margin: 3%;
margin-top:16px;
margin-right:0px;
width: 94%;
border-bottom: 1px solid #f2f2f2;
}
.mesImg
{
    float: left;
    margin-right: 10px;
}
.mesImg img
{
    display: block;
    width: 80px;
    height: 60px;
}
.mesContent{
overflow:hidden;
}
.mesTitle{
font-size: 16px;
    color: #000;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
}
.mesIntro{
margin-top:10px;
    font-size: 13px;
    color: #999;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    line-height: 1.3;
}
a{ text-decoration:none;
}
</style>
<script>
function forward(num,type){
	alert("正在发送中，请耐心等待！");
 $.ajax({
		url : "../getForwardMessage",
		type:'post',
		data:{
			num :num,
			type:type
		},
		success:function(data){
			alert("发送成功！");
		}
 });
}
</script>
</head>
<body>
<div style="padding-left: 10px;height: 60px;padding-top: 10px;">
<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&oid=00D90000000pkXM" alt="" style="width:30%;">
</div>
<div class="topPic"><img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490602667276&di=5ff160cb3a889645ffaf2ba17b4f2071&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F65%2F94%2F64B58PICiVp_1024.jpg" />
<div class="topPic_title">科技生活，从大数据开始</div></div>
<div class="navi"><p>图文消息</p><p>XXXX</p></div>
	<div id="wrapper" style="top:290px;">
		<div class="scroller">
<div id="mesPushPanel">
<% for(int i=0;i<size;i++){ %>
<%-- <a href="http://shenan.duapp.com/mdm/NotificationCenter.jsp?num=<%=ams.get(i).getNum()%>">
 --%><a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=<%=Constants.APP_ID%>&redirect_uri=http%3A%2F%2F<%=Constants.baehost%>%2Fmdm%2FNotificationCenter.jsp?num=<%=ams.get(i).getNum()%>&response_type=code&scope=snsapi_userinfo&state=<%=uid %>#wechat_redirect">
<div class="singleMes">
<div class="mesImg">
<%if(ams.get(i).getPicture()!=null&&ams.get(i).getPicture()!=""){ %>
<img src="<%=ams.get(i).getPicture() %>" />
<% }else{%>
<img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490602667276&di=5ff160cb3a889645ffaf2ba17b4f2071&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F65%2F94%2F64B58PICiVp_1024.jpg" />
<%} %></div>
<div class="mesContent">
<h2 class="mesTitle">
<%if(isInternalSeniorMgt==true||isInternalImtMgt==true){ %>
<img
 <% if("0".equals(ams.get(i).isForward)){ %>
 src='images/forward.png'
 <%}else{ %>
 src='images/forward2.png'
 <%} %>
  onclick="javascript:forward('<%=ams.get(i).getNum() %>','mes');return false;" style='height:20px;vertical-align:bottom;padding-bottom:3px;'/>
<%} %>
  <%=ams.get(i).getTitle() %></h2>
<p class="mesIntro"><%=ams.get(i).getContent() %></p>
</div>
</div></a>
<%} %>
</div>
<div class="more"><i class="pull_icon"></i><span>上拉加载...</span></div>
		</div>
	</div>
	<script type="text/javascript">

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
				var img="";
				setTimeout(function(){
			 		$.ajax({
		    			url : "../QueryArticleMessage",
						type:'post',
						data:{
							startNumber:size,
							pageSize:5
						},
						success:function(data){
							for (var i = 0; i < data.length; i++) {
								if(data[i].picture!=null&&data[i].picture!=""){
									img="<img src='"+data[i].picture+"'/>";
								}
								else{
									img="<img src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490602667276&di=5ff160cb3a889645ffaf2ba17b4f2071&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F65%2F94%2F64B58PICiVp_1024.jpg' />";
								}
								var isForward="2";
								if("0"==data[i].isForward){
									isForward="";
								}
//								$('#mesPushPanel').append("<a href='http://shenan.duapp.com/mdm/NotificationCenter.jsp?num="+data[i].num+"'><div class='singleMes'><div class='mesImg'>"+img+"</div><div class='mesContent'><h2 class='mesTitle'>"+data[i].title+"</h2><p class='mesIntro'>"+data[i].content+"</p></div></div></a>");
								
								<%if(isInternalSeniorMgt==true||isInternalImtMgt==true){ %>
									$('#mesPushPanel').append("<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=<%=Constants.APP_ID%>&redirect_uri=http%3A%2F%2F<%=Constants.baehost%>%2Fmdm%2FNotificationCenter.jsp?num="+data[i].num+"&response_type=code&scope=snsapi_userinfo&state=<%=uid%>#wechat_redirect'><div class='singleMes'><div class='mesImg'>"+img+"</div><div class='mesContent'><h2 class='mesTitle'><img src='images/forward"+isForward+".png' onclick='javascript:forward(\""+data[i].num+"\",\"mes\");return false;'  style='height:20px;vertical-align:bottom;padding-bottom:3px;'/>"+data[i].title+"</h2><p class='mesIntro'>"+data[i].content+"</p></div></div></a>");
								<%}%>
								$('#mesPushPanel').append("<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=<%=Constants.APP_ID%>&redirect_uri=http%3A%2F%2F<%=Constants.baehost%>%2Fmdm%2FNotificationCenter.jsp?num="+data[i].num+"&response_type=code&scope=snsapi_userinfo&state=<%=uid%>#wechat_redirect'><div class='singleMes'><div class='mesImg'>"+img+"</div><div class='mesContent'><h2 class='mesTitle'>"+data[i].title+"</h2><p class='mesIntro'>"+data[i].content+"</p></div></div></a>");
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

		</script>
</body>
</html>
