<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ page import="java.util.*" %>
<%@ page import="com.nkang.kxmoment.baseobject.MdmDataQualityView" %>
<%@ page import="com.nkang.kxmoment.baseobject.GeoLocation" %>  
<%-- <%@ page import="com.nkang.kxmoment.util.DBUtils"%> --%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%@ page import="com.nkang.kxmoment.util.Constants"%>
<%	
String AccessKey =RestUtils.callGetValidAccessKey();
String uid = request.getParameter("UID"); 
WeChatUser wcu = RestUtils.getWeChatUserInfo(AccessKey, uid);
%>
<!DOCTYPE html>
<html class=" js csstransitions">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MDM Makes it Matter</title> 
	<meta content="" name="hpe" />
<!-- 	<meta name="viewport" content="width=device-width, user-scalable=no"> -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link rel="icon" type="image/x-icon" href="../webitem/hpe.ico">
	<link rel="short icon" type="image/x-icon" href="../webitem/hpe.ico">
	<link rel="stylesheet" href="../MetroStyleFiles/CSS/sonhlab-base.css" type="text/css">
	<link rel="stylesheet" href="../MetroStyleFiles/CSS/metrotab-v2.css" type="text/css">
	<link rel="stylesheet" href="../MetroStyleFiles/CSS/metro-bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="../MetroStyleFiles/CSS/font-awesome.min.css" type="text/css">
	<link rel="stylesheet" href="../nkang/c3.css"  type="text/css"/>
	<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css">
	<script type="text/javascript" src="../MetroStyleFiles/JS/jquery.min.2.1.1.js"></script>
	<script type="text/javascript" src="../MetroStyleFiles/JS/jquery.easing.min.13.js"></script>
	<script type="text/javascript" src="../MetroStyleFiles/JS/jquery.mousewheel.min.js"></script>
	<script type="text/javascript" src="../MetroStyleFiles/JS/jquery.jscrollpane.min.js"></script>
	<script type="text/javascript" src="../MetroStyleFiles/JS/jquery.masonry.min.js"></script>
	<script type="text/javascript" src="../MetroStyleFiles/JS/modernizr-transitions.js"></script>
	<script type="text/javascript" src="../MetroStyleFiles/JS/metrotab-v2.min.js"></script>
	<script src="../nkang/c3.min.js"></script>
    <script src="../nkang/d3.v3.min.js"></script>
    <script src="../nkang/liquidFillGauge.js"></script>
    <script src="../nkang/Donut3D.js"></script>
	<script src="../nkang/Chart.js"></script>
	<script src="../nkang/gauge.js"></script>
	<script src="../nkang/RadarChart.js"></script>
	<script src="../MetroStyleFiles/sweetalert.min.js"></script>
	
<!-- 	<script async="" src="http://www.google-analytics.com/analytics.js"></script><script type="text/javascript">
		if (top != self) {
			window.open(self.location.href, '_top');
		}
	</script> -->
	<script type="text/javascript">
	$(document).ready(function() {
		$('.metrotabs').metrotabs({
				outeffect : 'swing',
				ineffect :	'swing',
				moveout : 'BottomOrRight', // TopOrLeft | BottomOrRight
				movein : 'TopOrLeft', // TopOrLeft | BottomOrRight
				outduration : 400,
				induration : 400,
				minibartitle: 'Master Data Lake Quality Grade',
				mtEffect : 'vertical' // vertical | horizontal | fade
			});
	});
	
	function CommentMe(){
		swal(
				{
					title: "Dear <%= wcu.getNickname()%> Comment US!",   
					text: "Drop us a message and do things better together",   
					type: "input",   
					showCancelButton: true,   
					closeOnConfirm: false,   
					animation: "slide-from-top",   
					inputPlaceholder: "Write something" 
				}, 
				function(inputValue){
					inputVar = inputValue;
					if (inputValue === false) return false;      
					if (inputValue === "") {     
						swal.showInputError("You need to write something!");     
						return false;   
					}
					swal("Thank You!", "We will contact you soon", "success"); 
				}
			);
	}
	
	function OrganizationInformation(){
		var cnt = <%= RestUtils.CallgetFilterTotalOPSIFromMongo()%>
		swal("200M", cnt+" Organizations", "success");
	}
	</script>
<!--  	<script type="text/javascript" src="../MetroStyleFiles/JS/ga.js"></script> -->

</head>
<body>
<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=015900000053FQo&oid=00D90000000pkXM&lastMod=1438220916000" alt="HP Logo" class="HpLogo"/> 
<div id="userInfo">
	<p class="navbar-text pull-right">Welcome <a href="http://shenan.duapp.com/mdm/profile.jsp?UID=<%= uid%>" class="navbar-link"><%= wcu.getNickname()%></a><br />
		<a href="http://shenan.duapp.com/mdm/profile.jsp?UID=<%= uid%>"><img src="<%= wcu.getHeadimgurl()%>" alt="userImage" class="userImage pull-right"/></a>
	</p>
</div>
 <div id="topinfo">
		<svg id="fillgauge4" width="0%" height="0" onclick="gauge4.update(NewValue());"></svg>
		<svg id="fillgauge5" width="50%" height="220" onclick="Javascript:OrganizationInformation();"></svg>
		
		<script>
		var percent ="0.66";
		var percent2 = 100*percent;
		  var gauge4 = loadLiquidFillGauge("fillgauge4", 50);
		    var config4 = liquidFillGaugeDefaultSettings();
		    config4.circleThickness = 0.15;
		   config4.circleColor = "#FFFFFF";
		    config4.textColor = "#00b287";
		    config4.waveTextColor = "#00b287";
		    config4.waveColor = "#00b287";
		    config4.textVertPosition = 0.8;
		    config4.waveAnimateTime = 1000;
		    config4.waveHeight = 0.05;
		    config4.waveAnimate = true;
		    config4.waveRise = false;
		    config4.waveHeightScaling = false;
		    config4.waveOffset = 0.25;
		    config4.textSize = 0.75;
		    config4.waveCount = 3;
		  var gauge5 = loadLiquidFillGauge("fillgauge5", percent2 , config4);
		    var config5 = liquidFillGaugeDefaultSettings();
		    config5.circleThickness = 0.4;
		    config5.circleColor = "#FFFFFF";
		    config5.textColor = "#00b287";
		    config5.waveTextColor = "#00b287";
		    config5.waveColor = "#00b287";
		    config5.textVertPosition = 0.52;
		    config5.waveAnimateTime = 5000;
		    config5.waveHeight = 0;
		    config5.waveAnimate = false;
		    config4.waveRise = true;
		    config5.waveCount = 2;
		    config5.waveOffset = 0.25;
		    config5.textSize = 1.2;
		    config5.minValue = 30;
		    config5.maxValue = 150;
		    config5.displayPercent = false;
		    function NewValue(){
		        return 100*mqv.getPercents;
		    }
		</script>
 </div>
    
    
<section id="mainform">

	<!-- START METROTAB -->
    <div class="metrotabs">
        <div class="mt-blocksholder floatleft masonry" style="width: 100%; display: block; position: relative; height: 100%;">
            <div id="tileboxjs" class="tile-bt-long img-purtywood mt-tab mt-active mt-loadcontent masonry-brick" style="position: absolute; top: 0px; left: 0px;">
                <a href="javascript:void(0);">
	                <img src="../MetroStyleFiles/image/icon.png" alt="">
	                <span class="light-text"><strong>拿铁</strong></span>
                </a>
            </div>
			
			<div id="openmes" class="tile-bt-long solid-green-2 mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 0px; left: 100px;">
                <a href="javascript:void(0);">
	                <img src="../MetroStyleFiles/image/icon.png" alt="">
	                <span class="light-text">摩卡</span>
                </a>
            </div>
            
            <div id="capuqino" data-ext="html" class="tile-bt-long solid-orange-2 mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 200px; left: 0px;">
                <a href="javascript:void(0);">
	                <img src="../MetroStyleFiles/image/icon.png" alt="">
	                <span class="light-text">卡布奇诺</span>
                </a>
            </div>
            
			<div id="openfooter" class="tile-bt-long img-wildoliva mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 300px; left: 0px;">
                <a href="javascript:void(0);">
	                <img src="../MetroStyleFiles/image/icon.png" alt="">
	                <span class="light-text">马琪雅朵</span>
                </a>
            </div>
            
            <div id="conporlan" class="tile-bt solid-red mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 200px; left: 200px;">
                <a href="javascript:void(0);" target="_blank">
					<img src="../MetroStyleFiles/image/icon.png" alt="">
					<span class="light-text">康宝蓝</span>
                </a>
            </div>
			
			<div id="espresso" class="tile-bt solid-blue-2 mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 300px; left: 200px;">
                <a href="javascript:void(0);">
	                <img src="../MetroStyleFiles/image/icon.png" alt="">
	                <span class="light-text">Espresso</span>
                </a>
            </div>

			
			<div id="americano" class="tile-bt-long solid-red-2 mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 400px; left: 0px;">
                <a href="javascript:void(0);">
	                <img src="../MetroStyleFiles/image/icon.png" alt="">
	                <span class="light-text">Americano</span>
                </a>
            </div>
            
            <div id="joinus" class="tile-bt-long solid-red-2 mt-tab  masonry-brick" style="position: absolute; top: 400px; left: 0px;">
		        <div class="tile-bt-long solid-green hovershadow-green">
		            <a href="javascript:void(0);" onClick="Javascript:CommentMe();"  title="move together">
		                <img src="../MetroStyleFiles/image/documents.png" alt="MetroTab Docs">
		                <span class="light-text" style="margin-left:5px; font-size:20px;">Join US</span>
		            </a>
		        </div>
    		</div>

        <div class="clearspace"></div>
        
        </div>
        
        <!-- START CONTENT -->
        <div class="mt-contentblock mt-contentblockstyle floatleft" style="padding: 5px; height: 100%; width:100%; display: block;">
	        <div class="mt-content jspScrollable" style="height: 100%; top: 0px; overflow: hidden; padding: 0px; width: 100%;" tabindex="0">
				<div class="jspContainer" style="width: 100%; height: 100%;">
					<div class="jspPane" style="padding: 0px; top: 0px; width: 100%;">
						<div>
							<h3><a href="http://shenan.duapp.com/mdm/DQNavigate.jsp" target="_blank" class="dark-text">Master Data Lake</a></h3>
							<p>
								<a href="http://shenan.duapp.com/mdm/DQNavigate.jsp" target="_blank" class="resimg"><img class="imagesize" src="../MetroStyleFiles/image/datalakedashboard.jpg" alt="TileBox jQuery"></a>
							</p>
							<p>
								 If you think of a datamart as a store of bottled water – cleansed and packaged and structured for easy consumption – the data lake is a large body of water in a more natural state. The contents of the data lake stream in from a source to fill the lake, and various users of the lake can come to examine, dive in, or take samples
						  		 <br /> --Pentaho CTO James Dixon
						  	</p>    
							<div class="readmore">
								<a href="http://shenan.duapp.com/mdm/DQNavigate.jsp" target="_blank" class="demoitem solid-blue-2 light-text floatleft">READ MORE</a>
								<div class="clearspace"></div>
							</div>
						</div>
					</div>
					<div class="jspVerticalBar">
						<div class="jspCap jspCapTop"></div>
						<div class="jspTrack" style="height: 100%;">
							<div class="jspDrag" style="height: 100%;">
								<div class="jspDragTop"></div>
								<div class="jspDragBottom"></div>
							</div>
						</div>
						<div class="jspCap jspCapBottom"></div>
					</div>
					</div>
			</div>
		</div>
        <!-- END CONTENT -->
    
     
    </div><div class="clearspace"></div>
	<!-- END METROTAB -->
	
	
	





</section>


<div id="mt-station">


<!-- MetroTab Content 卡布奇诺-->
<div data-mtid="capuqino"> 
	<div class="">
        <h3 class="dark-text">
			<a href="http://docs.sonhlab.com/openmes-jquery-open-animation-messages/" target="_blank" class="dark-text">
			卡布奇诺
			</a>
		</h3>
        <div class="dark-text">
                <p>
                	<a href="http://docs.sonhlab.com/openmes-jquery-open-animation-messages/" target="_blank" class="resimg"><img class="imagesize" src="../MetroStyleFiles/image/datalakepure.jpg" alt="openmes-jquery-plugin"></a>
                </p>
            
            	<div class="readmore">
					<a href="http://docs.sonhlab.com/openmes-jquery-open-animation-messages/" target="_blank" class="demoitem solid-blue-2 light-text floatleft">
						READ MORE
					</a>
					<div class="clearspace"></div>
				</div>
        </div>
	</div>
</div>
<!-- End MetroTab Content  卡布奇诺-->

<!-- MetroTab Content 美式咖啡-->
<div data-mtid="americano"> 
	<div class="">
        <h3 class="dark-text">
			<a href="http://docs.sonhlab.com/openmes-jquery-open-animation-messages/" target="_blank" class="dark-text">
			americano
			</a>
		</h3>
        <div class="dark-text">
                <p>
                	<a href="http://docs.sonhlab.com/openmes-jquery-open-animation-messages/" target="_blank" class="resimg"><img class="imagesize" src="../MetroStyleFiles/image/datalakeflow.jpg" alt="openmes-jquery-plugin"></a>
                </p>
            
            	<div class="readmore">
					<a href="http://docs.sonhlab.com/openmes-jquery-open-animation-messages/" target="_blank" class="demoitem solid-blue-2 light-text floatleft">
						READ MORE
					</a>
					<div class="clearspace"></div>
				</div>
        </div>
	</div>
</div>
<!-- End MetroTab Content  美式咖啡-->


<!-- MetroTab Content espresso浓缩-->
<div data-mtid="espresso"> 
	<div class="">
        <h3 class="dark-text">
			<a href="http://docs.sonhlab.com/openmes-jquery-open-animation-messages/" target="_blank" class="dark-text">
			espresso
			</a>
		</h3>
        <div class="dark-text">
                <p>
                	<a href="http://docs.sonhlab.com/openmes-jquery-open-animation-messages/" target="_blank" class="resimg"><img class="imagesize" src="../MetroStyleFiles/image/datalakeflow.jpg" alt="openmes-jquery-plugin"></a>
                </p>
            
            	<div class="readmore">
					<a href="http://docs.sonhlab.com/openmes-jquery-open-animation-messages/" target="_blank" class="demoitem solid-blue-2 light-text floatleft">
						READ MORE
					</a>
					<div class="clearspace"></div>
				</div>
        </div>
	</div>
</div>
<!-- End MetroTab Content  espresso浓缩-->

<!-- Start MetroTab Content 康宝蓝-->
<div data-mtid="conporlan"> 
	<div class="jumbotron">
	  <h1>Hello, Data lake!</h1>
	  <p>...</p>
	  <p><a class="btn btn-primary btn-lg" role="button">Learn more</a></p>
	</div>
</div>
<!-- End MetroTab Content  康宝蓝-->

<!-- MetroTab Content 摩卡-->
<div data-mtid="openmes">
	<br />
			<span class="label label-primary">UPP</span>
			<span class="label label-primary">Salesforce</span>
			<span class="label label-primary">SiebelPRM</span>
			<span class="label label-primary">Ecplise</span>
			<span class="label label-primary">Big Machine</span>
			<span class="label label-primary">SBS</span>
			<span class="label label-primary">midaxo</span><br /><br />
			<span class="label label-primary">vista</span>
			<span class="label label-primary">pcs</span>
			<span class="label label-primary">workbench</span>
			<span class="label label-primary">chrs</span>
			<span class="label label-primary">columbus</span>
			<span class="label label-primary">autonomy</span>
			<span class="label label-primary">csifdirect</span><br /><br />
			<span class="label label-primary">cust-loyalty-center</span>
			<span class="label label-primary">ecomcat</span>
			<span class="label label-primary">egomui</span>
			<span class="label label-primary">globalswlicensing</span>
			<span class="label label-primary">isac</span><br /><br />
			<span class="label label-primary">ngc</span>
			<span class="label label-primary">onlinepricebk</span>
			<span class="label label-primary">ppmwfm</span>
			<span class="label label-primary">pricinganalytics</span>
			<span class="label label-primary">quicksilver</span>
			<span class="label label-primary">rem</span><br /><br />
			<span class="label label-primary">salesids</span>
			<span class="label label-primary">sems</span>
			<span class="label label-primary">smartquote</span>
			<span class="label label-primary">watson</span>
			<span class="label label-primary">target builder</span>
</div>
<!-- End MetroTab Content 摩卡-->


<!-- Start MetroTab Content 玛琪雅朵-->
<div data-mtid="openfooter">
	<div id="chart3">
		<script>
			var chart = c3
					.generate({
						data : {
							columns : [ 
							        [ 'Customer', 70 ],
									[ 'Partner', 20 ],
									[ 'Competitor', 10 ] ],
							type : 'pie'
						},
						pie : {
							label : {
								format : function(value, ratio, id) {
									return d3.format('#')(value);
								}
							}
						},
						bindto : '#chart3'
					});
		</script>
	</div> 
</div>
<!-- End MetroTab Content 玛琪雅朵-->


<!-- Start MetroTab Content 拿铁-->
<div data-mtid="tileboxjs">
	<div>
			<h3>
				<a
					href="http://shenan.duapp.com/mdm/DQNavigate.jsp"
					target="_blank" class="dark-text">What is data lakes</a>
			</h3>
			<p>
				<a href="http://shenan.duapp.com/mdm/DQNavigate.jsp"
					target="_blank" class="resimg"><img class="imagesize"
					src="../MetroStyleFiles/image/datalakedashboard.jpg"
					alt="TileBox jQuery"></a>
			</p>
			<p>
				If you think of a datamart as a store of bottled water – cleansed
				and packaged and structured for easy consumption – the data lake is
				a large body of water in a more natural state. The contents of the
				data lake stream in from a source to fill the lake, and various
				users of the lake can come to examine, dive in, or take samples <br />
				--Pentaho CTO James Dixon
			</p>
			<div class="readmore">
				<a href="http://shenan.duapp.com/mdm/DQNavigate.jsp"
					target="_blank" class="demoitem solid-blue-2 light-text floatleft">READ
					MORE</a>
				<div class="clearspace"></div>
			</div>

		</div>
</div>
<!-- End MetroTab Content 拿铁-->
</div>

<div class="panel-footer"><span>©</span> 2016 Hewlett-Packard Enterprise Development Company, L.P.</div>

</body></html>