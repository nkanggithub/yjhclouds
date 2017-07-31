<%@ page language="java"    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.nkang.kxmoment.util.*"%>
<%
	String ticket=RestUtils.getTicket();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta http-equiv="Access-Control-Allow-Methods" content="POST, GET, OPTIONS, DELETE">
<meta http-equiv="Access-Control-Max-Age" content="3600">
<meta http-equiv="Access-Control-Allow-Headers" content="x-requested-with">
<title>test share</title>
<script type="text/javascript" src="../Jsp/JS/jquery-1.8.0.js"></script>
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
     var shareTitle="分享的标题";
     var shareDesc="分享的描述";
     var shareImgUrl="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000EUmgT&oid=00D90000000pkXM";
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
             alert("用户成功分享了该网页");
         },
         cancel: function () { 
             // 用户取消分享后执行的回调函数
             alert("用户取消了分享");
         },
         fail: function (res) {
             alert(JSON.stringify(res));
         }
     });
     //------------"分享到朋友圈"
     wx.onMenuShareTimeline({
         title: shareTitle, // 分享标题
         link:url, // 分享链接
         imgUrl: shareImgUrl, // 分享图标
         success: function () { 
             // 用户确认分享后执行的回调函数
             alert("用户成功分享了该网页");
         },
         cancel: function () { 
             // 用户取消分享后执行的回调函数
             alert("用户取消了分享");
         },
         fail: function (res) {
             alert(JSON.stringify(res));
         }
     });
     wx.error(function(res){
         // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
         alert("errorMSG:"+res);
     });
 });
 
//分享到朋友圈 
 function weixinShareTimeline(title,desc,link,imgUrl){
		WeixinJSBridge.invoke('shareTimeline',{
			"img_url":imgUrl,
			//"img_width":"640",
			//"img_height":"640",
			"link":link,
			"desc": desc,
			"title":title
		});	
	}
</script>
</head>
<body>
测试分享功能
<div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#" class="bds_qzone" data-cmd="qzone"></a><a href="#" class="bds_tsina" data-cmd="tsina"></a><a href="#" class="bds_tqq" data-cmd="tqq"></a><a href="#" class="bds_renren" data-cmd="renren"></a><a href="#" class="bds_weixin" data-cmd="weixin"></a></div>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdPic":"","bdStyle":"0","bdSize":"16"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
<script type="text/javascript">
(function(){var a=document.getElementsByTagName("html")[0];window.Session={appDomain:a.getAttribute("data-app-domain")||"",staticDomain:a.getAttribute("data-static-domain")||""}})();window.registNS=function(fullNS,isIgnorSelf){var reg=/^[_$a-z]+[_$a-z0-9]*/i;var nsArray=fullNS.split(".");var sEval="";var sNS="";var n=isIgnorSelf?nsArray.length-1:nsArray.length;for(var i=0;i<n;i++){if(!reg.test(nsArray[i])){throw new Error("Invalid namespace:"+nsArray[i]+"");return}if(i!=0){sNS+="."}sNS+=nsArray[i];sEval+="if(typeof("+sNS+")=='undefined') "+sNS+"=new Object();else "+sNS+";"}if(sEval!=""){return eval(sEval)}return{}};window.addEventMap=function(c,d,b,a){$.each(d,function(e,f){$(c).bind(f,function(h){var g=h.target||h.srcElement;if(!g){return false}for(var j in b[f]){var i=b[f][j];if((g.className&&$(g).hasClass(j))){i.call(g,h);break}else{if(ancestor=$(g).parents("."+j)[0]){i.call(ancestor,h);break}}}if(typeof a==="function"){a.call(h)}})})};var fixedTheElementOnScroll=function(c,a){if(!c){return false}a=parseInt(a,10)||0;var b=function(g){var f=$(c).offset();if($(c).attr("data-fixed")!="1"){$(c).attr({"data-original-top":f.top})}var d=$(window).scrollTop()+a;if($(c).attr("data-original-top")<=d){if($.browser.ie==6){$(c).attr({"data-fixed":1});$(c).css({position:"absolute",top:d-$(c).attr("data-original-top")+c.offsetHeight,left:0})}else{if($(c).attr("data-fixed")!="1"){$(c).attr({"data-fixed":1});$(c).css({position:"fixed",top:a,left:f.left})}}}else{$(c).attr("data-fixed",0);$(c).css({position:"static"})}};b();$(window).scroll(b)};String.prototype.trim=function(){return this.replace(/^\s*|\s*$/g,"")};String.format=function(c,a){c=String(c);var b=Array.prototype.slice.call(arguments,1),d=Object.prototype.toString;if(b.length){b=b.length==1?(a!==null&&(/\[object Array\]|\[object Object\]/.test(d.call(a)))?a:b):b;return c.replace(/#\{(.+?)\}/g,function(e,g){var f=b[g];if("[object Function]"==d.call(f)){f=f(g)}return("undefined"==typeof f?"":f)})}return c};(function(){var b=("abbr,article,aside,audio,canvas,datalist,details,dialog,eventsource,figure,footer,header,hgroup,mark,menu,meter,nav,output,progress,section,time,video").split(","),a=b.length;while(a--){document.createElement(b[a])}})();
</script>
<br/><br/><br/>
<button onclick="javascript:weixinShareTimeline('测试标题','desc','link','https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000EUmgT&oid=00D90000000pkXM')">分享到朋友圈</button>
</body>
</html>