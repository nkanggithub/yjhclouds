<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.baseobject.ShortNews"%>
<%
ArrayList<ShortNews> shortNews=MongoDBBasic.queryShortNews();
int size=5;
String uid = request.getParameter("UID");
if(shortNews.size()<5){size=shortNews.size();}
boolean isInternalSeniorMgt=MongoDBBasic.checkUserAuth(uid, "isInternalSeniorMgt");
boolean isInternalImtMgt=MongoDBBasic.checkUserAuth(uid, "isInternalImtMgt");
%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <title>永佳和行情实况</title>
			 <script src="../mdm/uploadfile_js/jquery-1.11.2.min.js"></script>
			 <script src="../Jsp/JS/iscroll.js"></script>
			  <script src="../Jsp/JS/avgrund.js"></script>
<link rel="stylesheet" href="../Jsp/CSS/about.css">

<script	src="../MetroStyleFiles/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
</head>
<body style="margin:0px">
<%if(isInternalSeniorMgt==true||isInternalImtMgt==true) { %>
<button style="position: absolute;top: 40px;right: 20px;padding: 4px 8px;background: white;border-style: none;border: 1px solid black;border-radius: 5px;" onClick="javascript:publishNews();">发布新闻</button><% } %>
		<aside style="margin-top:50px;height:400px;position:absolute;width:80%;left:5%;top:80px;" id="default-popup" class="avgrund-popup">

			<h2 id="title" style="margin-bottom:10px;"></h2>

			<p style="margin-top:20px;" id="content">
			
			</p>

			<button style="position:absolute;bottom:20px;right:20px;padding:4px 8px;" onClick="javascript:closeDialog();">关闭</button>

		</aside>

<div style="padding-left: 10px;height: 70px;border-bottom: 4px solid #0066FF;padding-top: 10px;">
<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM" alt="" style="width:200px;height:85%;">
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
	    				var html="";
	    				$.ajax({
	    	    			url : "../QueryShortNewsList",
	    					type:'post',
	    					success:function(data){
	    						for (var i = 0; i < data.length; i++) {
	    							html+="<li><span>"+data[i].date+"</span><p><span onClick='javascript:openDialog(this);'>"+data[i].content+" </span></p></li>";
	    						
	    						}
	    						$('.scroller ul').html(html);
	    					},
	    					error:function(){
	    						console.log('error');
	    					}
	    				});
	    			}
	    			});
	    	  $.ajax({
	    			type : "post",
	    			url : "../userProfile/sendNewsToAll",
	    			data:{
	    				content:$("#news").val()
	    			},
	    			cache : false,
	    			success : function(data) {
	    				swal("恭喜!", data+"个人已收到您的新闻推送~", "success");
	    			
	    			}
	    			});	    	
	    			    	 		
	      });

	}
	window.openDialog=openDialog;
	window.closeDialog=closeDialog;
	window.publishNews=publishNews;
});

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
					$('.pull_icon').addClass('loading');
					$('.more span').text('加载中...');
					pullUpAction();
				}
				
			},
			onRefresh:function(){
				$('.more').removeClass('flip');
				$('.more span').text('上拉加载...');
			}
			
		});
		
		function pullUpAction(){
			setTimeout(function(){
		/* 		$.ajax({
	    			url : "../QueryShortNewsList",
					type:'post',
					success:function(data){
						for (var i = size; i < data.length; i++) {
							$('.scroller ul').append("<li><span>"+data[i].date+"</span><p><span onClick='javascript:openDialog(this);'>"+data[i].content+" </span></p></li>");
						}
						myscroll.refresh();
					},
					error:function(){
						console.log('error');
					},
				}); */
			
				<%for(int i=size ;i<shortNews.size();i++){%>
				$('.scroller ul').append("<li><span>"+"<%=shortNews.get(i).getDate()%>"+"</span><p><span onClick='javascript:openDialog(this);'>"+"<%=shortNews.get(i).getContent()%>"+" </span></p></li>");
				<%}%>
				
				myscroll.refresh();
			}, 1000)
		}
		if ($('.scroller').height()<$('#wrapper').height()) {
			$('.more').hide();
			myscroll.destroy();
		}

	</script>
</body></html>