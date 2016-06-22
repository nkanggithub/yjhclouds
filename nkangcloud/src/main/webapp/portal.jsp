<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ page import="com.nkang.kxmoment.util.DBUtils" %>
<%-- <%@ page import="com.it.shancloud.util.*" %> --%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
	<meta name="baidu-site-verification" content="VEOBGv7rlp" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>重庆神安化工</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="DS MY UI Team">
    <meta name="keywords" content="神安">
    <meta name="title" content="">
    <meta name="description" content="">
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
    <link rel="stylesheet" id="prettyphoto-style-css" href="SLP/prettyPhoto(1).css" type="text/css" media="all">
	<script src="SLP/jquery-migrate.min.js"></script>
	<script src="SLP/jquery.js"></script>
	<script src="SLP/superfish.js"></script>
	<script src="SLP/jquery.prettyPhoto(1).js"></script>
	<script src="SLP/custom.js"></script>
	<script src="SLP/comment-reply.min.js"></script>

    <link rel="shortcut icon" href="webitem/sico.ico">
    <link rel="stylesheet" type="text/css" href="SLP/select2.css">
    <link href="SLP/bootstrap.css" 				media="all" rel="stylesheet" type="text/css">
    <link href="SLP/dark-blue-theme.css" 		media="all" id="color-settings-body-color" rel="stylesheet" type="text/css">
    <link href="SLP/theme-colors.css" 			media="all" rel="stylesheet" type="text/css">
    <link href="SLP/jquery_ui.css" 				media="all" rel="stylesheet" type="text/css">
    <link href="SLP/styles.css" 				media="all" rel="stylesheet" type="text/css">
    <link href="SLP/elegent_font.css" 			media="all" rel="stylesheet" type="text/css">
    <link href="HPStyles/css/HpstandIcon.css" 		media="all" rel="stylesheet" type="text/css">
	
	<!--  <script type="text/javascript" src="SLP/jquery.min.js"></script>-->
    <script type="text/javascript" src="SLP/jquery-ui.min.js"></script>
    <script type="text/javascript" src="SLP/bootstrap.js"></script>
    <script type="text/javascript" src="SLP/jquery.ui.touch-punch.min.js"></script>
    <script type="text/javascript" src="SLP/select2.js"></script>
    <script type="text/javascript" src="SLP/jquery.scrollTo-1.4.3.1-min.js"></script>
    <script type="text/javascript" src="SLP/jquery.localscroll-1.2.7-min.js"></script>
    <script type="text/javascript" src="SLP/jquery.stellar.min.js"></script>
    <script type="text/javascript" src="SLP/modernizr-2.6.1.min.js"></script>
    <script type="text/javascript" src="SLP/jquery.validate.min.js"></script>
    <script type="text/javascript" src="SLP/additional-methods.js"></script>


 <%!
	 String userID;
	 String userPwd;

%>
 
 <%
   // Get session creation time.
   Date createTime = new Date(session.getCreationTime());
   Date lastAccessTime = new Date(session.getLastAccessedTime());	
   String title = "Welcome Back";
   String userIDKey = "userIDKey";
   userID = request.getParameter("name1");
   String userPWDKey = "userPWDKey";
   userPwd = request.getParameter("pwd1");
   if (session.isNew()){
      title = "Welcome";
      session.setAttribute(userIDKey, userID);
      session.setAttribute(userPWDKey, userPwd);
   }
   session.setAttribute(userIDKey, userID);
   session.setAttribute(userPWDKey, userPwd);
%>   

</head>

<style>

#login-container{
	height:368px;
}
/* body{
	background-color:#1a759c;
} */
/* .contrast-blue header .navbar{
	background-color:#1a759c;
} */
/* #footer{
	background-color:#1a759c;
}  */
#login-container .social {
    background: url("SLP/blackbg.png") 0 center repeat transparent;
    padding: 15px 0 35px;
    margin: 120px 0;
    position: absolute;
    left: 10%;
    width: 550px;
}
#login-container .login {
    background: url("SLP/signup_bg.png") 0 center repeat transparent;
    padding: 60px 35px 60px;
    border-radius: 0 0 15px 15px;
}
#features .details {
    background: url("SLP/blackbg.png") 0 0 repeat transparent;
    padding: 30px 15px 40px;
    border-radius: 0 0 15px 15px;
    height:90%;

}
.paralaxSlice2 {
    background-image: url("SLP/img2.jpg");
}
#aboutUs .testimonial {
    background: url("SLP/blackbg.png") 0 center repeat transparent;
    min-height: 306px;
    padding: 20px;
}

.FeatureImg{
	height:300px;
}
.HansFont{
	font-family:"SimSun",Georgia,Serif;
}
.HansFontkaiTi{
	font-family:"KaiTi",Georgia,Serif;
}
.container {
	max-width: 1700px;
}
。containerC{
}
.col-lg-4 {
	width: 20%;
}  
.col-lg-44 {
	width: 33.33333%;
}
.pageTitle{
	background: url("SLP/chemicaltitle.png") 0 center repeat transparent; 
}


</style>



<body class="contrast-blue">

    <div id="globalWrapper" class="localscroll">
    	
        <header class="navbar-fixed-top pageTitle">
            <nav class="navbar navbar-default">
                <div class="container ">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="row">
                                <div class="col-lg-7 col-md-7 ">
                                    <span class="navbar-brand" >
                                        <img class="logo-web" alt="HP Logo" src="SLP/SLogo.png" style="width:66px;height:60px;">
                                        <img class="logo-mobile" alt="HP Logo" src="SLP/SLogo.png">
                                        <div class="title" >
                                            <h1>神安化工建材有限公司</h1>
                                            <h4>CHONGQING, CHINA</h4>
                                        </div>
                                    </span>
                                    <a class="toggle-nav btn pull-left lg-hide" href="http://eskm.hp.com/SLPApp/index.html?RelayState=https://hp--plmesit--c.cs10.visual.force.com/apex/AthenaUserProfile?id%3D005J0000001ccetIAA#">
                                        <i class="icon-reorder"></i>
                                    </a>
                                </div>
                                <div class="col-lg-5 col-md-5 social">
                                    <div class="row">
                                        <div class="col-lg-offset-9 col-lg-3 col-md-4 fb">
                                            <div class="label">
                                                <p class="title">点赞!</p>
                                                <p class="likes"></p>
                                            </div>
                                            <div class="icon-box">
                                                <a style="text-decoration:none" href="javascript:void(null);" onclick="TriggerLike()">
                                                    <i class="icon-thumbs-up like-part"></i>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
            <div class="page-header-container">
                <div class="container">
                    <div class="row" id="content-wrapper">
                        <div class="col-xs-12">
                            <div class="page-header">
                                <div class="tabbable menu">
                                    <ul class="nav nav-tabs nav-tabs-simple">
                                        <li class="home active"><a href="#login-container" class="blue-border"><i class="icon-home"></i></a></li>
                                        <!-- <li><a href="#rlbAndNews">Insights</a></li> -->
                                        <li class=""><a href="#features" class="">关注神安</a></li>
                                        <li><a href="#aboutUs">关于神安</a></li>
                                        <li><a href="#footer">联系神安</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        
        <section id="login-container" class="">
            <div class="box-content">
                <div class="container">
                    <div class="row">
						<div class="col-lg-12">
							<div class="row ">
								<div class="signup col-lg-offset-8 col-lg-4 col-sm-offset-8 col-sm-4 col-md-offset-8 col-md-4 login">
									<h2 class="title">Login Now!</h2>
									<form action="http://eskm.hp.com/SLPApp/siteminderLogin" class="" method="get" novalidate="novalidate">
<!-- 										<a href="#" id="aDesktopUrl" class="btn btn-web">访客登陆</a>
										<a href="#" id="aMobileUrl" class="btn btn-mobile">访客登陆</a>
                                        <a href="#" id="aTabletUrl" class="btn btn-tablet">访客登陆</a> -->
                                        <a href="#modal-login" class="btn btn-web"    role="button" data-toggle="modal">访客登陆</a>
                                        <a href="#modal-login" class="btn btn-mobile" role="button" data-toggle="modal">访客登陆</a>
                                        <a href="#modal-login" class="btn btn-tablet" role="button" data-toggle="modal">访客登陆</a>
                                        
									</form>
									<h3>Or Request Access…</h3>
									<a class="btn" data-toggle="modal" href="#modal-signup" role="button">申请访问</a>
								</div>
							</div>
						</div>
                    </div>
                </div>
                <div class="carousel slide" id="myCarousel" style="margin-bottom:0;">
                    <ol class="carousel-indicators">
                        <li data-slide-to="0" data-target="#myCarousel"></li>
                        <li data-slide-to="1" data-target="#myCarousel"></li>
                        <li class="active" data-slide-to="2" data-target="#myCarousel"></li>
                    </ol>
                    <div class="carousel-inner" style="">
                        <div class="active item">
                            <div class="social">
                                <h1 class="firstlayer" style="font-size:30px;"><span class="thin">重庆市文明单位 </span></h1>
                                <h1 class="firstlayer" style="font-size:25px;"><span class="thin">优秀民营企业</span></h1>
                                <h1 class="secondlayer">技术创新先进企业</h1>
                                <h2 class="thirdlayer">重合同守信用企业</h2>
                            </div>
                            <img src="SLP/marquee01.jpg">
                        </div>
                        <div class="item">
                            <div class="social">
                                <h1 class="firstlayer" style="font-size:30px;"><span class="thin">改革开放30年忠县10大工业品牌 </span></h1>
                                <h1 class="firstlayer" style="font-size:25px;"><span class="thin">先进党组织</span></h1>
                                <h1 class="secondlayer">关爱员工先进企业</h1>
                                <h2 class="thirdlayer">安全生产先进企业</h2>
                            </div>
                            <img src="SLP/marquee02.jpg">
                        </div>
                        <div class="item">
                            <div class="social">
                                <h1 class="firstlayer" style="font-size:30px;"><span class="thin">重庆市文明单位 </span></h1>
                                <h1 class="firstlayer" style="font-size:25px;"><span class="thin">优秀民营企业</span></h1>
                                <h1 class="secondlayer">质量效益型企业</h1>
                                <h2 class="thirdlayer">重合同守信用企业</h2>
                            </div>
                            <img src="SLP/marquee03.jpg">
                        </div>
                    </div>
                    <a class="left carousel-control" data-slide="prev" href="#myCarousel">
                        <span class="icon-angle-left icon-prev"></span>
                    </a>
                    <a class="right carousel-control" data-slide="next" href="#myCarousel">
                        <span class="icon-angle-right icon-next"></span>
                    </a>
                </div>
            </div>
        </section>
		<section id="features">
            <div class="container">
                <h1>Focusing</h1>
                <div class="row">
                    <div class="col-lg-4 col-md-4">
                        <div class="details">
							<a href="SLP/SPortal.jpg" data-rel="prettyPhoto" rel="prettyPhoto" sl-processed="1" style="border: 0px none;">
								<img src="SLP/SPortal.jpg" alt="
								振兴神安为己任，不用扬鞭自奋踢。“神安人”发扬“自强不息，拼搏创新”的精神，秉承“追求卓越，铸百年神安”的发展理念，坚持“忠诚、务实、创新、敏捷”的经营思想，确立了以气体产业为龙头的产业发展方向，
								立足“2+4”产业格局，竭力打造气体产品产业链，努力扩张气体产业的深度和广度，实现气体产业“多元化”，“以气体产业拓展带动商贸业，以商贸业的发展促进企业实力的提升，实现工贸并举的产业格局”。力求把产品做优、把市场做大、把效益做好、把企业做强。 
								今天，“神安人”在董事长马培英女士的团结带领下，坚持“内强素质，外塑形象”的方针，向着“更强、更大”的目标昂首阔步，竭力打造实力神安、魅力神安、和谐神安，通过不懈努力，把神安打造成为三峡库区气体产业的“航空母舰”，为全面构建社会主义和谐社会作出新的更大贡献。
								" 
								class="FeatureImg">
							</a>
							
                            <h3>服务宗旨</h3>
                            <p>神安人发扬“自强不息，拼搏创新”的精神，秉承“追求卓越，铸百年神安”的发展理念，坚持“忠诚、务实、创新、敏捷”的经营思想<br /><br /><br /></p>
                            <!-- <button class="btn">More</button> -->
                            <a class='btn' data-toggle='modal' href='#modal-signup' role='button'>Get Started Now</a>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="details">
							<a href="SLP/FrontGate.jpg" data-rel="prettyPhoto" rel="prettyPhoto" sl-processed="1" style="border: 0px none;">
								<img src="SLP/FrontGate.jpg" alt="
								重庆市神安化工建材有限公司，位于重庆市忠县东溪镇，2002年被重庆市委、重庆市人民政府命名为“文明单位”。
								公司始建于1984年，公司总占地面积60亩，现有员工60余人，拥有专业技术人员20人。公司内设办公室（人力资源部）、财务部、销售部、安全环保部、生产质量技术部。公司注册资本金201万元，总资产2800多万元。 
								公司主要生产经营溶解乙炔、工业氧、工业氮、氩气、二氧化碳、医用氧等产品，广泛适用于化工、冶金、机械、建筑、医疗等行业。《神安》牌溶解乙炔、工业氧以其先进生产工艺和优良的产品质量及良好的市场信誉，
								畅销渝东及三峡库区。　一分耕耘，一分收获。神安风雨兼程20余年，企业由单一的建材产品，发展成为了集化工、商贸、气瓶定期检验于一体的综合经济实体，为县域经济社会发展做出了重要贡献。" 
								class="FeatureImg">
							</a>
							
                            <h3>神安简介</h3>
                            <p>重庆市神安化工建材有限公司，位于重庆市忠县东溪镇。公司始建于1984年，公司总占地面积60亩，现有员工60余人，拥有专业技术人员20人。公司注册资本金201万元，总资产2800多万元 </p>
                            <!-- <button class="btn">More</button> -->
                            <a class='btn' data-toggle='modal' href='#modal-signup' role='button'>Get Started Now</a>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="details">
							<a href="SLP/ecosystem.jpg" data-rel="prettyPhoto" rel="prettyPhoto" sl-processed="1" style="border: 0px none;">
								<img src="SLP/ecosystem.jpg" alt="
								神安从建立起,在依靠科技力量,坚持环境保护工作从细微入手,本着“忠诚、务实、创新、敏捷”的理念走出了一条创新发展的经营之路。乙炔生产的主要原料为电石,在生产过程中产生的主要废弃物是电石渣。
								随着生产的延续,积少成多,如何处理生产中产生的电石渣的问题也成为困扰重庆市神安化工建材有限公司领导难题。经过多方考察和论证科研人员认为电石渣是生产水泥建材的理想原料。
								企业将电石渣经脱水处理后,用于水泥生产,不仅解决了电石渣堆积影响环境的问题,还真正实现了变废为宝	
								" class="FeatureImg">
							</a>
                            <h3 style="color:white;"><a href="http://xuewen.cnki.net/CJFD-HJJI200811028.html" target="_blank" style="color:white;">生态神安</a></h3>
                            <p>神安在化工产品生产过程中，从工艺源头上就运用环保的理念，推行源消减、进行生产过程的优化集成，废物再利用与资源化，从而降低了成本与消耗，减少废弃物的排放和毒性，减少产品全生命周期对环境的不良影响</p>
                            <!-- <button class="btn">More</button>               -->
                            <a class='btn' data-toggle='modal' href='#modal-signup' role='button'>Get Started Now</a>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="details">
							<a href="SLP/biztalk.jpg" data-rel="prettyPhoto" rel="prettyPhoto" sl-processed="1" style="border: 0px none;">
								<img src="SLP/biztalk.jpg" alt="" class="FeatureImg">
							</a>
                            <h3 style="color:white;"><a href="http://xuewen.cnki.net/CJFD-HJJI200811028.html" target="_blank" style="color:white;">精英团队</a></h3>
                            <p>神安精英团队<br /><br /><br /><br /><br /></p>
                            <!-- <button class="btn">More</button>            -->
                            <a class='btn' data-toggle='modal' href='#modal-signup' role='button'>Get Started Now</a>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4">
                        <div class="details">
							<a href="SLP/factorypark.jpg" data-rel="prettyPhoto" rel="prettyPhoto" sl-processed="1" style="border: 0px none;">
								<img src="SLP/factorypark.jpg" alt="" class="FeatureImg">
							</a>
                            <h3 style="color:white;"><a href="http://xuewen.cnki.net/CJFD-HJJI200811028.html" target="_blank" style="color:white;">绿色工厂</a></h3>
                            <p>神安绿色工厂一角<br /><br /><br /><br /><br /></p>
                            <!-- <button class="btn">More</button>           -->
                            <a class='btn' data-toggle='modal' href='#modal-signup' role='button'>Get Started Now</a>
                        </div>
                    </div>

                </div>
            </div>
        </section>
        <section id="aboutUs" class="paralaxSlice2" data-stellar-background-ratio="0.5" style="background-position: 50% -621.5px;">
            <div class="container">
                <div class="row" id="Div1">
                    <div class="col-lg-44 col-md-4">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="box-quick-link">
                                    <div class="header">
                                        <span class=" white icon-technology-services"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 ">
                                <div class="col-lg-12">
									<div class="testimonial">
                                    <h2>Commitment</h2>
                                    <p>今天，“神安人”在董事长马培英女士的团结带领下，坚持“内强素质，外塑形象”的方针，向着“更强、更大”的目标昂首阔步，竭力打造实力神安、魅力神安、和谐神安，通过不懈努力，把神安打造成为三峡库区气体产业的“航空母舰”，为全面构建社会主义和谐社会作出新的更大贡献。</p>
                                    <p><b>马培英</b><br /><span class="thin">董事长</span><br />神安化工 </p>
									</div>
								</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-44 col-md-4">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="box-quick-link">
                                    <div class="header">
                                        <span class=" white icon-partnership2"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="col-lg-12">
                                    <div class="testimonial">
                                    <h2>ABOUT US</h2>
                                        <p> 联系人：马培英</p>
                                        <p> 公司电话：862354783666 或  862354812666</p>
                                        <p> 公司地址：重庆市忠县东溪镇翠屏村</p>
                                        <p> 邮编：404300</p>
									</div>
								
								</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-44 col-md-4">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="box-quick-link">
                                    <div class="header">
                                        <span class="white icon-on-call-service"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="col-lg-12">
									<div class="testimonial">
                                    <h2>Social Connection</h2>
                                        <p>
				                            <a href="SLP/SWeixin.jpg" data-rel="prettyPhoto" rel="prettyPhoto" sl-processed="1" style="border: 0px none;">
												<img src="SLP/SWeixin.jpg" alt="" class="FeatureImg" style="width:108px;height:108px;">
											</a>
										</p>
	                                        <div class="pullLeft eBrainFooter"> Lets Also Connect <br />
											<a href="https://www.facebook.com/HP" target="_blank"><img src="SLP/facebook.png" style="width:38px;height:38px;"/></a> 
											<a href="https://www.facebook.com/HP" target="_blank"><img src="SLP/twitter2.png" style="width:38px;height:38px;"/></a>
											<a href="https://www.facebook.com/HP" target="_blank"><img src="SLP/tumblr.png" style="width:38px;height:38px;"/></a> 
											<a href="https://www.facebook.com/HP" target="_blank"><img src="SLP/linkedin.png" style="width:38px;height:38px;"/></a>
										</div>
                                    </div>
								</div>
                            </div>
                        </div>
                    </div>
                    

                </div>
            </div>
        </section>
        <footer id="footer">
            <div class="container web">
                <div class="row">
                    <div class="col-lg-6 col-md-6 text text-center">
                        <ul class="sitemap">
                            <li><a href="#login-container">主页</a> | </li>
                            <li><a href="#features">神安文化</a> | </li>
                            <li><a href="#aboutUs">关于我们</a> | </li>
                            <li><a data-toggle='modal' href='#modal-industryNews' role='button'>行业动态</a> | </li>
                            <li><a data-toggle='modal' href='#modal-privacy' role='button'>法律声明</a> | </li>
                            <li><a data-toggle='modal' href='#modal-terms' role='button'>Terms of Use</a></li>
                        </ul>
                        <p>@ 2014 神安化工建材有限公司 | Shenan Restricted</p>
                    </div>
                    <div class="col-lg-offset-2 col-lg-2 col-md-offset-2 col-md-2 contact">
                        <h5>CONTACT US</h5>
                        <p><a href="mailto:camning_amanda@hotmail.com">camning_amanda@hotmail.com</a></p>
                    </div>
                </div>
            </div>
            <div class="container mobile">
                <div class="row">
                    <div class="col-lg-12 col-md-12">
                        <p>@ 2014 神安化工建材有限公司</p>
                    </div>
                    <div class="col-lg-12 col-md-12">
                        <h5>联系我们</h5>
                        <p><a href="mailto:camning_amanda@hotmail.com">camning_amanda@hotmail.com</a></p>
                    </div>
                </div>
            </div>
        </footer>
        <div class='modal fade' id='modal-signup' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='myModalLabel'>请提供以下信息</h4>
                    </div>
                    <form class="form validate-form" style="margin-bottom: 0;" action="FileUpload.jsp" method="post" enctype="multipart/form-data" novalidate="novalidate" >
                        <div class='modal-body'>
                            <div class='form-group controls'>
                                <label for='inputText0'>姓名</label>
                                <input value="" placeholder="姓名" class="form-control"   data-rule-required="true" 	id="rname1"   name="rname1"  type="text" />
                                <label for='inputText1'>邮箱</label>
                                <input value="" placeholder="邮箱" class="form-control"   data-rule-email="true" 		id="remail1"  name="remail1" type="email" />
                                <label for='inputText2'>手机</label>
                                <input value="" placeholder="电话" class="form-control"  data-rule-required="true" 	id="rphone1"  name="rphone1" type="text" />
								<label for='inputText3'>头像</label>
                                <input type="file" name="upfile" size="50" /><br />
								<!-- <input type="submit" value="上传" /> -->
                            </div>
                        </div>
                        <div class='modal-footer'>
                            <button class='btn btn-default'    data-dismiss='modal' type='button'>取消</button>
                            <!-- <button class='btn btn-join-modal' data-dismiss='modal' type='submit'>提交</button> -->
                            <input class='btn' type="submit" value="提交">
                        </div>
                    </form>

                </div>
            </div>
        </div>
        <div class='modal fade' id='modal-login' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='myModalLabel'>请提供登陆信息 【访客请使用 guest/guest登陆】</h4>
                    </div>
                    <form class="form validate-form" style="margin-bottom: 0;" method="post" action="main.jsp" novalidate="novalidate">
                        <div class='modal-body'>
                            <div class='form-group controls'>
                                <label for='inputText4'>用户名</label>
                                <input value="" placeholder="手机或邮箱地址" class="form-control" id="name1" name="name1"  type="text" />
                                <label for='inputText5'>密码</label>
                                <input value="" placeholder="密码" class="form-control"   data-rule-required="true"	id="pwd1"  name="pwd1"  type="password" />
                            	
                            </div>
                        </div>
                        <div class='modal-footer'>
                            <button class='btn btn-default' data-dismiss='modal' type='button'>取消</button>
                            <input class='btn'  type="submit" value="登陆" onClick="javascript:return credentialCheck();"/>
                            
                            <script type="text/javascript">
                            function credentialCheck(){
                             	userID = document.getElementById("name1").value;
                            	userPwd = document.getElementById("pwd1").value;
								var indd;
                            	if(userID != null && userPwd != null){
                            		indd = 'Y';
                            		alert(indd+"---"+userID+"---"+userPwd);
                        			if(indd != "guest"){
                        				alert("welcome" + ind);
                        				return false;	
                        			}
                        			else{
                        				alert("验证失败，请重试, "+ ind);
                        				return false;
                        			}
                        			return false;
                            	}
                            	else{
                            		return false;
                            	}
                            }
                            </script>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        

        <div class='modal fade' id='modal-maintance' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='myModalLabel2'>系统升级维护中.....</h4>
                    </div>
                </div>
            </div>
        </div>
        
        <div class='modal fade' id='modal-thanks' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='H1'>&nbsp;</h4>
                    </div>
                    <div class='modal-body'>
						<h4>恭喜您，您已经成功提交申请。神安会尽快回复您....</h4>
                    </div>
                    <div class='modal-footer'>
                        <button class='btn btn-default' data-dismiss='modal' type='button'>Ok</button>
                    </div>
                </div>
            </div>
        </div>

        <div class='modal fade' id='modal-like' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='H2'>&nbsp;</h4>
                    </div>
                    <div class='modal-body'>
                        <h4>谢谢亲对我们的支持与鼓励.</h4>
                    </div>
                    <div class='modal-footer'>
                        <button class='btn btn-default' data-dismiss='modal' type='button'>确定</button>
                    </div>
                </div>
            </div>
        </div>

        <div class='modal fade' id='modal-hpemail' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='H3'>&nbsp;</h4>
                    </div>
                    <div class='modal-body'>
                        <h4>请输入合法的邮箱地址</h4>
                    </div>
                    <div class='modal-footer'>
                        <button class='btn btn-default' data-dismiss='modal' type='button'>确定</button>
                    </div>
                </div>
            </div>
        </div>

        <div class='modal fade' id='modal-error' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='H4'>&nbsp;</h4>
                    </div>
                    <div class='modal-body'>
                        <!--content goes here -->
                    </div>
                    <div class='modal-footer'>
                        <button class='btn btn-default' data-dismiss='modal' type='button'>Ok</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class='modal fade' id='modal-industryNews' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='H5'>行业动态</h4>
                    </div>
                    <div class='modal-body' style='height:500px; overflow-y:scroll'>
                        <!--content goes here -->
                    </div>
                    <div class='modal-footer'>
                        <button class='btn btn-default' data-dismiss='modal' type='button'>Ok</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class='modal fade' id='modal-privacy' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='H5'>Privacy Policy</h4>
                    </div>
                    <div class='modal-body' style='height:500px; overflow-y:scroll'>
                        <!--content goes here -->
                    </div>
                    <div class='modal-footer'>
                        <button class='btn btn-default' data-dismiss='modal' type='button'>Ok</button>
                    </div>
                </div>
            </div>
        </div>
		
        <div class='modal fade' id='modal-terms' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='H6'>Terms of Use</h4>
                    </div>
                    <div class='modal-body' style='height:500px; overflow-y:scroll'>
                        <!--content goes here -->
                    </div>
                    <div class='modal-footer'>
                        <button class='btn btn-default' data-dismiss='modal' type='button'>Ok</button>
                    </div>
                </div>
            </div>
        </div>

        <div class='modal fade' id='modal-ie' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='H7'>Non-recommended browser detected</h4>
                    </div>
                    <div class='modal-body'>
                        <p>Please note that Web UI is not optimized for Internet Explorer, please use Chrome</p>
                        <p>We recommend <a href="https://www.google.com/chrome/browser/">Google Chrome</a></p>
                    </div>
                    <div class='modal-footer'>
                        <button class='btn btn-default' data-dismiss='modal' type='button'>Ok</button>
                    </div>
                </div>
            </div>
        </div>

        <div class='modal fade' id='modal-mob' tabindex='-1'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button aria-hidden='true' class='close' data-dismiss='modal' type='button'>×</button>
                        <h4 class='modal-title' id='H8'>Mobile coming soon</h4>
                    </div>
                    <div class='modal-body'>
                        <p>Please note that Web UI is not optimized for your device, Mobile Experience coming soon</p>
                    </div>
                    <div class='modal-footer'>
                        <button class='btn btn-default' data-dismiss='modal' type='button'>Ok</button>
                    </div>
                </div>
            </div>
        </div>


    </div>
    


    <script>!function (d, s, id) { var js, fjs = d.getElementsByTagName(s)[0], p = /^http:/.test(d.location) ? 'http' : 'https'; if (!d.getElementById(id)) { js = d.createElement(s); js.id = id; js.src = p + "://platform.twitter.com/widgets.js"; fjs.parentNode.insertBefore(js, fjs); } }(document, "script", "twitter-wjs");</script>
    <script type="text/javascript">

        var isMobile = {
            Android: function () {
                return navigator.userAgent.match(/Android/i);
            },
            BlackBerry: function () {
                return navigator.userAgent.match(/BlackBerry/i);
            },
            iOS: function () {
                return navigator.userAgent.match(/iPhone|iPod/i);
            },
            ipad: function () {
                return navigator.userAgent.match(/iPad/i);
            },
            Opera: function () {
                return navigator.userAgent.match(/Opera Mini/i);
            },
            Windows: function () {
                return navigator.userAgent.match(/IEMobile/i);
            },
            any: function () {
                return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows() || isMobile.ipad());
            }
        };

        var isTablet = false;

        if (isMobile.Android()) {
            //alert(navigator.userAgent)
            //Mozilla/5.0 (Linux; U; Android 2.2; en-us; SCH-I800 Build/FROYO) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1
            //check resolution range
            console.log('width ' + $(window).width() + ' height ' + $(window).height());
            w = $(window).width();
            h = $(window).height();
            var orientation;
            //check current orientation
            if (w > h) {
                // Landscape
                console.log("LANDSCAPE");
                orientation = 'landscape';
            } else {
                // Portrait
                console.log("PORTRAIT");
                orientation = 'potrait';
            }
            //check if current device is in supported range
            switch (orientation) {
                case 'landscape':
                    if (w >= 1024)
                        isTablet = true;
                    break;
                case 'potrait':
                    if (w >= 768)
                        isTablet = true;
                    break;
                default:
                    'error:device not supported, please use mobile or desktop to view'
            }

            //if all done, load the css
            if (isTablet) {
                var $ = document; // shortcut            
                var cssId = 'myCss';  // you could encode the css path itself to generate id..
                if (!$.getElementById(cssId)) {
                    var head = $.getElementsByTagName('head')[0];
                    var link = $.createElement('link');
                    link.id = cssId;
                    link.rel = 'stylesheet';
                    link.type = 'text/css';
                    link.href = 'css/tabletstyles.css';
                    link.media = 'all';
                    head.appendChild(link);                    
                } else {
                    alert('Unable to load style for mobile...')
                }
                //alert('Mobile experience reconstruction...');             
            }
        } else if (isMobile.ipad()) {
            var $ = document; // shortcut
            var cssId = 'myCss';  // you could encode the css path itself to generate id..
            if (!$.getElementById(cssId)) {
                var head = $.getElementsByTagName('head')[0];
                var link = $.createElement('link');
                link.id = cssId;
                link.rel = 'stylesheet';
                link.type = 'text/css';
                link.href = 'css/ipadstyles.css';
                link.media = 'all';
                head.appendChild(link);
            } else {
                alert('Unable to load style for mobile...')
            }
            //alert('Mobile experience reconstruction...');         
        } else if (isMobile.any()) {
            var $ = document; // shortcut
            var cssId = 'myCss';  // you could encode the css path itself to generate id..
            if (!$.getElementById(cssId)) {
                var head = $.getElementsByTagName('head')[0];
                var link = $.createElement('link');
                link.id = cssId;
                link.rel = 'stylesheet';
                link.type = 'text/css';
                link.href = 'css/Mstyles.css';
                link.media = 'all';
                head.appendChild(link);
            } else {
                alert('Unable to load style for mobile...')
            }
            //alert('Mobile experience reconstruction...'); 
        };

        console.log('this is a tablet? = ' + isTablet)

        function msieversion() {

            var ua = window.navigator.userAgent;
            var msie = ua.indexOf("MSIE ");

            if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer, return version number
            {
                var iever = parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)));
                jQuery('#modal-ie').modal("show");
            }

            else                 // If another browser, return 0
                //alert('otherbrowser');

                return false;
        }

        function detectmob() {
            if (window.innerWidth <= 800) {
                return true;
            } else {
                return false;
            }
        }

        console.log(isTablet)
        if (isTablet)
            jQuery('#footer').addClass('tablet');

        jQuery(document).ready(function () {
            msieversion();
            if (detectmob() == true) {
                //jQuery('#modal-mob').modal("show");                
            }

            //Bhaskar added below code
            var menuTabClick = false;
            var offset = 0;
            var timeout = 0;
            jQuery.get("likeCounter", { likeStatus: 'FetchCount' },
              function (data) {
                  //if (data=="SUCCESS"){
                  jQuery('.likes').html(data);
                  //}
              });


            if (jQuery('.localscroll').length) {
                jQuery('.localscroll').localScroll({
                    lazy: true,
                    offset: {
                        top: -(170)
                    }
                });
                var isMobile = false;
                if (Modernizr.mq('only all and (max-width: 1024px)')) {
                    isMobile = true;
                }
                if (isMobile === false && jQuery('[class^="paralaxSlice"]').length && !jQuery('.ie8').length) {
                    jQuery(window).stellar({
                        horizontalScrolling: false,
                        responsive: true,
                        scrollProperty: 'scroll',
                        parallaxElements: false,
                        horizontalScrolling: false,
                        horizontalOffset: 0,
                        verticalOffset: 0
                    });
                }
            }
            jQuery(".select2").select2({
                placeholder: "Choose..",
                allowClear: true
            });


            jQuery('.page-header .nav a').click(function (e) {
                if (timeout > 0) {
                    clearTimeout(timeout);
                    offset = 0;
                }
                menuTabClick = true;
                e.preventDefault();
                var _this = $(this);
                attr = $(this).attr('href');
                offset = $(attr).offset().top;
                var isSelected = _this.parent().hasClass('active');
                if (!isSelected) {
                    jQuery('.nav li.active').removeClass('active');
                    jQuery('.nav a.blue-border').removeClass('blue-border');

                    _this.addClass('blue-border');
                    _this.parent().addClass('active');

                }
                timeout = window.setTimeout(function () {
                    offset = 0;
                }, 1500)
            });
            jQuery(document).scroll(function (e) {
                var pos = $(this).scrollTop();
                var sections = {},
                  _height = $(window).height(),
                  _headerHeight = jQuery('header').height();
                i = 0;

                // Look in the sections object and see if any section is viewable on the screen.
                // If two are viewable, the lower one will be the active one.
                jQuery('section').each(function () {
                    sections[this.id] = $(this).offset().top - 170;
                });
                if (offset > 0) {
                    return;
                }
                for (i in sections) {
                    if (pos == 0) {
                        _section = jQuery('.page-header .nav li a[href="#login-container"]')
                        jQuery('.nav li.active').removeClass('active');
                        jQuery('.nav a.blue-border').removeClass('blue-border');

                        _section.addClass('blue-border');
                        _section.parent().addClass('active');
                    }
                    else {
                        if (pos >= sections[i]) {
                            _section = jQuery('.page-header .nav li a[href="#' + i + '"]')
                            if (!_section.parent().hasClass('active')) {
                                jQuery('.nav li.active').removeClass('active');
                                jQuery('.nav a.blue-border').removeClass('blue-border');

                                _section.addClass('blue-border');
                                _section.parent().addClass('active');
                            }
                        }
                    }
                }
            })

            jQuery('#modal-privacy .modal-body').load('privacy.html');
            jQuery('#modal-terms .modal-body').load('terms.html');
        })

        // Edited By Syafiq on 3 June 2014
/*         jQuery('.like-part').on("click", function (e) {
        	jQuery('#modal-like').modal("show");
            if ($(this).hasClass('disabled')) {
                //$(this).removeClass('disabled');
                jQuery('#modal-like').modal("show");
            } else {
                $(this).addClass('disabled');
                $.post("likeCounter", { likeStatus: 'InsertCount' },
                function (data) {
                    //if (data=="SUCCESS"){
                    jQuery('.likes').html(parseInt(jQuery('.likes').html(), 10) + 1);
                    //}
                });
            }
        }); */


        jQuery('.btn-join-modal').on("click", function (e) {
            e.preventDefault();
            if (document.getElementById("email1").value.indexOf(".com") > -1) {
                jQuery('#modal-thanks').modal("show");
                // $.get("sendNotification", { empNumber: document.getElementById("empId").value, empEmailId: document.getElementById("email").value },
                $.get("sendNotification", { empEmailId: document.getElementById("email1").value },
                function (data) {

                }); document.getElementById("email1").value = "";
            } else {
                jQuery('#modal-hpemail').modal("show");
            }
        });

        (function () {

            jQuery(document).ready(function () {
                //ESDS-IDC Modified 20-Jun-2014 Start
                FetchRelayUrl();
                //ESDS-IDC Modified 20-Jun-2014 End
                var touch;
                setCharCounter();
                setMaxLength();
                setValidateForm();

                touch = false;

                if (window.Modernizr) {
                    if (!Modernizr.input.placeholder) {
                        jQuery("[placeholder]").focus(function () {

                            var input;
                            input = $(this);
                            if (input.val() === input.attr("placeholder")) {
                                input.val("");
                                return input.removeClass("placeholder");
                            }
                        }).blur(function () {
                          
                            var input;
                            input = $(this);
                            if (input.val() === "" || input.val() === input.attr("placeholder")) {
                                input.addClass("placeholder");
                                return input.val(input.attr("placeholder"));
                            }
                        }).blur();
                        return jQuery("[placeholder]").parents("form").submit(function () {
                            return $(this).find("[placeholder]").each(function () {
                                var input;
                                input = $(this);
                                if (input.val() === input.attr("placeholder")) {
                                    return input.val("");
                                }
                            });
                        });
                    }
                }
            });

            this.setMaxLength = function (selector) {
                if (selector == null) {
                    selector = jQuery(".char-max-length");
                }
                if (jQuery().maxlength) {
                    return selector.maxlength();
                }
            };

            this.setCharCounter = function (selector) {
                if (selector == null) {
                    selector = jQuery(".char-counter");
                }
                if (jQuery().charCount) {
                    return selector.charCount({
                        allowed: selector.data("char-allowed"),
                        warning: selector.data("char-warning"),
                        cssWarning: "text-warning",
                        cssExceeded: "text-error"
                    });
                }
            };

            this.setValidateForm = function (selector) {
                if (selector == null) {
                    selector = jQuery(".validate-form");
                }
                if (jQuery().validate) {
                    return selector.each(function (i, elem) {
                        return jQuery(elem).validate({
                            errorElement: "span",
                            errorClass: "help-block has-error",
                            errorPlacement: function (e, t) {
                                return t.parents(".controls").first().append(e);
                            },
                            highlight: function (e) {
                                return jQuery(e).closest('.form-group').removeClass("has-error has-success").addClass('has-error');
                            },
                            success: function (e) {
                                return e.closest(".form-group").removeClass("has-error");
                            }
                        });
                    });
                }
            };

        }).call(this);



        //ESDS-IDC Modified 20-Jun-2014 Start
		function TriggerLike() {
			jQuery('#modal-like').modal("show");
			var hc = "33";
		    if ($(this).hasClass('disabled')) {
		        //$(this).removeClass('disabled');
		        jQuery('#modal-like').modal("show");
		    } else {
		        $(this).addClass('disabled');
		        $.post("likeCounter", { likeStatus: 'InsertCount' },
		        function (data) {
		            //if (data=="SUCCESS"){
		            jQuery('.likes').html(parseInt(jQuery('.likes').html(), 10) + 1);
		            //}
		        });
		    }
        }

        
        function FetchRelayUrl() {
            //var MobileUrl = 'https://athpitg-idp-it.houston.hp.com/affwebservices/public/saml2sso?SPID=https://hp--PLMESIT.cs10.my.salesforce.com&RelayState=';
            //var DesktopUrl = 'https://athpitg-idp-it.houston.hp.com/affwebservices/public/saml2sso?SPID=https://hp--PLMESIT.cs10.my.salesforce.com&RelayState=';
            //var TabletUrl = 'https://athpitg-idp-it.houston.hp.com/affwebservices/public/saml2sso?SPID=https://hp--PLMESIT.cs10.my.salesforce.com&RelayState=';
            var MobileUrl = 'http://shenan.duapp.com/main.jsp';
            var DesktopUrl = 'http://shenan.duapp.com/main.jsp';
            var TabletUrl = 'http://shenan.duapp.com/main.jsp';
            var GenRelayState = '';
            var GenRelayTab='';
            //var DefaultMobileUrl = 'https://hp--plmesit--c.cs10.visual.force.com/apex/mSLPLanding0';
            //var DefaultDeskTopUrl = 'https://hp--plmesit--c.cs10.visual.force.com/apex/SLP_Activity';
            //var DefaultTabletUrl = 'https://hp--plmesit--c.cs10.visual.force.com/apex/tabletActivityPage';
            
            var DefaultMobileUrl = '';
            var DefaultDeskTopUrl = '';
            var DefaultTabletUrl = '';
            //var sPageURL = "http://eskm.hp.com/SLPApp/index.html?RelayState=https://hp--plmesit--c.cs10.visual.force.com/apex/SLP_ViewCommunity?id%3Da8lJ00000004Hg4IAE";
            var sPageURL = window.location.href;
            if (sPageURL.indexOf('RelayState') > 0) {
                var sURLVariables = sPageURL.split('RelayState=')[1];
                if (sURLVariables.indexOf('SLP_ViewCommunity') > 0) {
                    GenRelayState = sURLVariables.replace('SLP_ViewCommunity', 'mobileCommunityMockup');
                    GenRelayTab = sURLVariables.replace('SLP_ViewCommunity', 'tabletViewCommunity');
                    MobileUrl = MobileUrl + GenRelayState;
                    DesktopUrl = DesktopUrl + sURLVariables;
                    TabletUrl = TabletUrl + GenRelayTab;

                    
                }
                else if (sURLVariables.indexOf('AthenaLearningBoardDtls') > 0) {
                    GenRelayState = sURLVariables.replace('AthenaLearningBoardDtls', 'mobileLearningBoardMockup');
                    GenRelayTab= sURLVariables.replace('AthenaLearningBoardDtls', 'tabletViewBoard');
                    MobileUrl = MobileUrl + GenRelayState;
                    DesktopUrl = DesktopUrl + sURLVariables;
                    TabletUrl = TabletUrl + GenRelayTab;
                }
                else if (sURLVariables.indexOf('AthenaUserProfile') > 0) {
                    GenRelayState = sURLVariables.replace('AthenaUserProfile', 'mobileUserProfuleMockup');
                    GenRelayTab = sURLVariables.replace('AthenaUserProfile', 'mobileProfileNew');
                    MobileUrl = MobileUrl + GenRelayState;
                    DesktopUrl = DesktopUrl + sURLVariables;
                    TabletUrl = TabletUrl + GenRelayTab;
                }
                else {
                    MobileUrl = MobileUrl + sURLVariables;
                    DesktopUrl = DesktopUrl + sURLVariables;
                    TabletUrl = TabletUrl + sURLVariables;

                }
            }
            else {
                MobileUrl = MobileUrl + DefaultMobileUrl;

                DesktopUrl = DesktopUrl + DefaultDeskTopUrl;
                TabletUrl = TabletUrl + DefaultTabletUrl;
            }
            jQuery('#aDesktopUrl').attr('href', DesktopUrl);
            jQuery('#aMobileUrl').attr('href', MobileUrl);
            jQuery('#aTabletUrl').attr('href', TabletUrl);
        }
        //ESDS-IDC Modified 20-Jun-2014 End

    </script>

</body></html>