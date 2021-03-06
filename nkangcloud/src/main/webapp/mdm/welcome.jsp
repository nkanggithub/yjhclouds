<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.nkang.kxmoment.util.Constants"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%	
String baehosthost = Constants.baehost;
String UID = request.getParameter("UID");
MongoDBBasic.updateUser(UID);
%> 
<!DOCTYPE html>
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>永佳和塑胶有限公司</title>
    <link href="../Jsp/CSS/common.css" type="text/css" rel="stylesheet">
    <link href="../Jsp/CSS/style_360_2.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="../Jsp/CSS/layer.css" id="layui_layer_skinlayercss">
   <script>
    function appointment(){
    	window.location.href="http://"+baehosthost+"/mdm/quoteDetail.jsp";
    }
    $(function(){
    	//分享到朋友圈
    	$("#shareF").click(function(){
    		var index = layer.open({
    		    type: 1,
    		    title: false,
    		    closeBtn: false,
    		    shadeClose: true,
    		    skin: 'share-box',
    		    scrollbar: false,
    		    content:'<div class="share-inner">'+
		    				'<span class="arrow"></span>'+
		    				'<div class="con">点击右上角菜单<br>分享给好友</div>'+
		    			'</div>'
    		});
    		$(".share-inner").click(function(){
    			layer.close(index)
    		})
    	})
    	
    	//点击箭头页面向上滑动
    	$("#slideDown").click(function(){
    		$(this).addClass("arrow-affter");
    		$("html,body").animate({scrollTop: $("#pageD1").offset().top}, 500);
    	})
    	
    	//滑动页面市箭头消失
    	$(window).scroll( function(){ 
    		$("#slideDown").addClass("arrow-affter");
    	});
    	
    	//获取屏幕高度，并赋值给第一屏
    	
    	var winHeight = $(window).height()-55;
    	$("#pageD0").height(winHeight);
    	
    	//展开更多清洗服务
    	$("#slideMore").click(function(){
    		$("#otherService").slideDown();
    		$(this).hide();
    	})
    	
    	//判断浏览器是否为微信浏览器，根据浏览器不同，二维码不同
   		if(isWeiXin()){ 
   			$("#bottomWx").show();
   		}else{
   			$("#bottomBrowser").show();
   		}
    })
    </script>
</head>
<body>
<div style="padding:10px;padding-top:5px;border-bottom:2px solid #0067B6;position:relative"> 
	<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM" alt="Logo" class="HpLogo" style="display:inline !important;height:35px !important;width:auto !important;float:none;padding:0px;vertical-align:bottom;padding-bottom:10px;">
	<span class="clientSubName" style="font-size:12px;padding-left:7px;color:#333;">市场如水 企业如舟</span>
	<h2 style="color:#333;font-size:18px;padding:0px;padding-left:5px;font-weight:bold;margin-top:5px;font-family:HP Simplified, Arial, Sans-Serif !important;" class="clientName">永佳和塑胶有限公司</h2>
	<p style="position: absolute;top: 1px;right: 10px;font-size: 15px;">欢迎您</p>			
</div>
<div class="wrap">
    <section class="main-content-descirption">
        <div class="brand">
        	<div class="page page1" id="pageD0" style="height: 870px;">
        		<p class="tip-t">您刚好需要<br>我正好专业</p>
        		<p class="tip-b">中国塑胶领跑者</p>
        		<div class="img-wrap">
        			<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmM82&oid=00D90000000pkXM" width="100%" height="330px">
        		</div>
        		<div class="arrow-box">
        			<span class="arrow-icon arrow-affter" id="slideDown"><img src="../Jsp/PIC/icon-goldarrow.png"></span>
        		</div>
        	</div>
        	<div class="page" id="pageD1">
        		<p class="tip-t">匠心沉淀</p>
        		<p class="tip-b">
        			永佳和塑胶有限公司成立近13年来，不断开拓创新，发展壮大，业务规模不断攀升。近三年来，公司及旗下子公司业务量年均增长率为30%，到2017年突破20亿元人民币。良好的经营业绩得益于上下游客户的鼎力支持，也离不开每一位员工的不懈努力和辛勤付出，在此我谨代表公司管理层表示最衷心的感谢。
        		</p>
        		<div class="step-box">

        		</div>
        		<div class="img-wrap" style="position: static; margin-top:30px;">
        			<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmM45&oid=00D90000000pkXM" width="100%">
        		</div>
        	</div>
        	 <script>
        	 var weixinSignMap = null;
        	   var signUrl = encodeURIComponent(window.location.href);
        		$.ajax({
        			type:'POST',
        			url:window.location.protocol + "//" + window.location.host + "/weixin/api_invoke!createWeiXinJsSign.action",
        			data:{
        				'signUrl':signUrl
        			},
        			async:false,
        			success:function(data){
        				weixinSignMap = data;
        			}
        		});
    	//微信设置
    	wx.config({
    	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    	    appId: weixinSignMap.appId, // 必填，公众号的唯一标识
    	    timestamp:weixinSignMap.timestamp,  // 必填，生成签名的时间戳
    	    nonceStr: weixinSignMap.nonceStr,  // 必填，生成签名的随机串
    	    signature: weixinSignMap.signature, // 必填，签名，见附录1
    	    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','hideMenuItems'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    	});
    	
    	var options = {
    			imgUrl : window.location.protocol + "//" + window.location.host + "/weixin/clearorder/images/zmn_log.png",
    			link : window.location.protocol + "//" + window.location.host + "/staticpage/brand-description.html",
    			desc : "关注啄木鸟家电微信公众号，微信下单预约上门家电清洗和维修，省心又省钱！",
    			title :  "啄木鸟家电•品牌介绍",
    			appId :  weixinSignMap.appId
    		};
    	wx.ready(function () {
    		wx.hideMenuItems({
    		    menuList: ['menuItem:copyUrl'] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
    		});
    	    wx.checkJsApi({
    	        jsApiList: [
    	            'onMenuShareTimeline',
    	        ]
    	    });
    		wx.onMenuShareTimeline({title: options.title,link:  options.link,imgUrl:options.imgUrl});
    		wx.onMenuShareAppMessage({title: options.title,desc: options.desc,link: options.link,imgUrl: options.imgUrl,type: 'link',dataUrl: ''});
    		wx.onMenuShareQQ({title: options.title,desc: options.desc,link: options.link,imgUrl: options.imgUrl});
    		wx.onMenuShareWeibo({title: options.title,desc: options.desc,link: options.link,imgUrl: options.imgUrl});
    	});
        </script>
        	<div class="page page3">
        		<p class="tip-t">董事长致词</p>
        		<p class="tip-b">
        			在这个充满了机遇与挑战的信息时代，我们更要转变观念，勤于思考，善于洞察，秉持诚信，大胆创新。2010年以来，公司通过引进了OA办公系统和U8进销存及客户管理系统，有效推动了制度与流程的规范化，提升了运营效率；通过激励手段的探索和创新，公司设立了合作基金制度，使管理人员和资深员工得以分享公司的经营成果；通过对全国销售网络的重新规划，整合了上海、广州等地的销售团队，制定了武汉、郑州、江西、成都等地的发展计划，明确了以厦门带动华南、重庆覆盖西南、武汉辐射华东及华中的中短期战略布局。这些举措为公司的厚积薄发奠定了重要的基础
        		</p>
        		<div class="img-wrap">
        			<img src="" width="100%">
        		</div>
        	</div>
        	<div class="page" style="height:580px;">
        		<p class="tip-t">联系我们</p>
        		<p class="tip-b">
        			公        司：  重庆永佳和塑胶有限公司<br/>
联 系  人：  王素萍 (总经理)<br/>
电        话：  023-68698689<br/>
传        真：  023-68698676<br/>
地        址：  重庆市九龙坡区渝州路156号6-8(歇台子党校旁边百科大厦6-8)<br/>
        		</p>
        		<div class="img-wrap" style="bottom:20px;">
        			<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DmM7n&oid=00D90000000pkXM" width="100%">
        		</div>
        	</div>

        </div>
        <div class="common-bottom" id="bottomWx" style="display:none;">
        	<div class="clean-tips">
	            <div class="tit">更多永佳和的故事</div>
	            <div class="con">
	            	永佳和塑胶有限公司成立于2000年1月，专业经营进口及国产塑胶原料（硬胶、软胶、工程塑胶），应用范围覆盖航空、医疗设备、汽车、卫浴、日用消费品、电子产品、电器、服饰配件、运动用品、其他工业品等众多领域，行业前景广阔。
	            </div>
	            <div class="btn-box">
	                <button class="btn" onclick="appointment()">马上询价</button>
	                <button class="btn share-btn" id="shareF">分享到朋友圈</button>
	            </div>
	            <div class="attention-box" style="opacity:.9">
	                <img src="./啄木鸟家电_files/code-all.png" width="244" height="114">
	            </div>
	        </div>
	        <div class="tel-box">
	            <a href="tel:4000238888" style="color:#a0a0a0;">400-023-8888</a>
	            <a href="tel:4000238888" class="telphone-box" style="color:#a0a0a0;">
	               	 服务咨询
	                <span class="tel-icon"></span>
	            </a>
	        </div>
	     </div>
	     <div class="common-bottom" id="bottomBrowser" style="">
        	<div class="clean-tips">
	            <div class="tit">>更多永佳和的故事</div>
	            <div class="con">
	            	永佳和塑胶有限公司成立于2000年1月，专业经营进口及国产塑胶原料（硬胶、软胶、工程塑胶），应用范围覆盖航空、医疗设备、汽车、卫浴、日用消费品、电子产品、电器、服饰配件、运动用品、其他工业品等众多领域，行业前景广阔。
	            
	            </div>
	            <div class="btn-box">
	                <a href="http://wonderfulcq.bceapp.com/mdm/quoteDetailExternal.jsp?UID=<%= UID%>" ><button class="btn">马上询价</button></a>     
	            </div>
	            <div class="attention-box" style="opacity:.9">
	                <img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000EA48E&oid=00D90000000pkXM" width="244" height="114">
	            </div>
	        </div>
	        <div class="tel-box">
	            <a href="tel:023-68698689" style="color:#a0a0a0;">023-68698689</a>
	        </div>
	     </div>
    </section>
</div>

</body></html>