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




MongoDBBasic.addSkimNum();
ArrayList<ShortNews> shortNews=MongoDBBasic.queryShortNews();
int size=5;
int realSize=shortNews.size();
if(shortNews.size()<=5){size=shortNews.size();}
SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd"); 
Date date=new Date();
String currentDate = format.format(date);
MongoDBBasic.updateVisited(openid,currentDate,"DailyNewsToShare",headImgUrl,name);
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
</head>
<body style="margin:0px">
<img src = "https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000EAc05&oid=00D90000000pkXM" hidden="hidden">
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
		<li><span><%=shortNews.get(i).getDate() %></span><p><span onClick="javascript:openDialog(this);"><%=shortNews.get(i).getContent() %> </span></p></li>
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
					$('.more span').text('上拉加载...')
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