<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.nkang.kxmoment.baseobject.ArticleMessage"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%	
SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd"); 
Date date=new Date();
String currentDate = format.format(date);
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
<body style="margin:0;">


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
	        	$("#sendCode").attr("disable","true");
	        	$("#sendCode").css("background-color","#ccc");
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
