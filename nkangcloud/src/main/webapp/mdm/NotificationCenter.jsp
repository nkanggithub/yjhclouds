<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.nkang.kxmoment.baseobject.ArticleMessage"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="java.util.List"%>
<%	
String uid = request.getParameter("uid");
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
 
 
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>
  <meta charset="utf-8">
  <title>消息推送</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/style.css"/>
<script type="text/javascript" src="../Jsp/JS/jquery-1.8.0.js"></script>

</head>
<body style="padding:0px;margin:0px;">
<div id="recognitionCenter" style="position:absolute;width:100%;height:auto;"> 
<div style="height:70px;font-family: HP Simplified, Arial, Sans-Serif;border-bottom:5px solid #0067B6"><img style='position:absolute;top:20px;left:10px;width:90px;height:auto' src='' alt='Logo' class='HpLogo'></div>
<div style="position:absolute;top:100px;width:80%;left:10%;height:30px;">
<p style="text-align:center;"><%=n.getTitle() %></p>
</div>
<div style="position:absolute;top:140px;width:80%;left:10%;height:30px;">
<p style="text-align:left;line-height:25px;font-size:14px;"><%=n.getContent() %></p>
</div>
<div style="position:absolute;top:500px;width:80%;left:10%;height:30px;">
<p style="text-align:right;line-height:25px;font-size:14px;"><%=n.getTime() %></p>
</div>
</div>

	<div id="footer">
		<span class="clientCopyRight"><nobr></nobr></span>
	</div>
	<script>
         jQuery.ajax({
     		type : "GET",
     		url : "../QueryClientMeta",
     		data : {},
     		cache : false,
     		success : function(data) {
     			var jsons = eval(data);
     			$('img.HpLogo').attr('src',jsons.clientLogo);
     			$('span.clientCopyRight').text('©'+jsons.clientCopyRight);
     		}
     	});
         </script>
</body>


</html>
