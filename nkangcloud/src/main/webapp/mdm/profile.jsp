<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ page import="java.util.*" %>
<%@ page import="com.nkang.kxmoment.baseobject.GeoLocation" %> 
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%					
String AccessKey =RestUtils.callGetValidAccessKey();
String uid = request.getParameter("UID"); 
String curLoc ;
WeChatUser wcu ;
if(session.getAttribute("location")==null){
	GeoLocation loc= RestUtils.callGetDBUserGeoInfo(uid);
	wcu = RestUtils.getWeChatUserInfo(AccessKey, uid);
	curLoc = RestUtils.getUserCurLocWithLatLng(loc.getLAT() , loc.getLNG()); 
	session.setAttribute("location",curLoc);
	session.setAttribute("wcu",wcu);
}else{
	wcu=(WeChatUser)session.getAttribute("wcu");
	curLoc=(String)session.getAttribute("location");
}
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
	<meta charset="utf-8" />
	<title>HPE - Master Data Management</title>
	<!-- <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	 -->
	 
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="hpe" />

	<link href="../nkang/assets_athena/bootstrap/css/bootstrap.min.css" 				rel="stylesheet" type="text/css"/>
	<link href="../nkang/assets_athena/bootstrap/css/bootstrap-responsive.min.css" 	rel="stylesheet" type="text/css"/>
	<link href="../nkang/assets_athena/font-awesome/css/font-awesome.css" 			rel="stylesheet" type="text/css"/>
	<link href="../nkang/css_athena/style.css" 										rel="stylesheet" type="text/css"/>
	<link href="../nkang/assets_athena/icomoon/iconMoon.css" 						rel="stylesheet" type="text/css"/>
	<link href="../nkang/css_athena/style-responsive.css" 							rel="stylesheet" type="text/css"/>
	<link href="../nkang/css_athena/style-default.css" 								rel="stylesheet" type="text/css"/>
	<link href="../nkang/assets_athena/data-tables/DT_bootstrap.css" 				rel="stylesheet" type="text/css"/>
	<script src="../nkang/js_athena/jquery-1.8.2.min.js"></script>
	<script src="../nkang/assets_athena/bootstrap/js/bootstrap.js"></script>
	<script src="../nkang/assets_athena/jquery-ui/jQuery_UI_1_10_3.js"></script>
	<script src="../nkang/js_athena/typeahead.js"></script>
	<script src="../nkang/js_athena/jquery.feedback_me.js"></script>
	<script src="../nkang/assets_athena/raty/ratyViewJs.js"></script>
	<script src="../nkang/assets_athena/data-tables/jquery.dataTables.js"></script>
	<script src="../nkang/assets_athena/data-tables/DT_bootstrap.js"></script>
	<script src="../nkang/js_athena/common-scripts.js"></script>

<style>

	#weather{
		margin-bottom:20px;
	}
	#weather tr td{
		height:50px;
	}
	#weather_suggest{
		margin-top:20px;
	}
	#weather_suggest tr td{
		line-height:35px;
	}
	#weather_div_loading{
		text-align:center;
	}
	#main-content {
		margin-bottom: 0px !important;
	}
	.HpLogo{
	width:200px;
	height:60px;
	}

	#navlist li
	{
	display: inline;
	list-style-type: none;
	padding-right: 30px;
	white-space: nowrap;
	}
	.mainTitle{
	font-size:15px;
	}
a:hover,a:link{
	text-decoration:none;
	}
	.loading {
	display: none;
	opacity: 0.7;
	filter:alpha(opacity=70); 
	position: absolute;
	left: 0;
	top: 0;
	width: 1000px;
	height: 100%;
	background-color: #FFFFFF;
	z-index: 1000;
	}
.matesUserImage {
	border-radius: 30px;
	height: 60px;
	width: 60px;
}
.Work_Mates_div{
	width:100%;
	padding:0px;
}
.Work_Mates_div .Work_Mates_div_li{
	float:left;
	margin:20px;
}
	.myButtons{
	background: none  !important;
	border: 0px  !important;
	background-color: #007DBA  !important;
	color: #fff !important;
	font-size: 13px  !important;
	border-radius: 0px  !important;
	padding: 6px 6px 6px 6px  !important;
	border-bottom-left-radius: 6px  !important;
	border-top-right-radius: 6px  !important;

	}
	.myButtons:hover{
	background-color: #D7410B !important;
	}
	.myDisabledButtons{
	background: none  !important;
	border: 0px  !important;
	background-color: gray !important;
	color: #fff !important;
	font-size: 13px  !important;
	border-radius: 0px  !important;
	padding: 6px 6px 6px 6px  !important;
	border-bottom-left-radius: 6px  !important;
	border-top-right-radius: 6px  !important;
	}
	.parentDisable{
	overflow:hidden;
	width:100%;
	//height:550px;
	clear: both;
	}



	/* upload button */
	::-webkit-file-upload-button {
	background: none;
	border: 0px;
	background-color: #007DBA;
	color: #fff !important;
	font-size: 14px;
	border-radius: 0px;
	padding: 8px 8px 8px 8px;
	border-bottom-left-radius: 6px;
	border-top-right-radius: 6px;
	}

	.linkBtn {
	background: none !important;
	border: 0px !important;
	background-color: #007DBA !important;
	color: #fff !important;
	font-size: 14px !important;
	border-radius: 0px !important;
	padding: 8px 8px 8px 8px !important;
	border-bottom-left-radius: 6px !important;
	border-top-right-radius: 6px !important;
	}
	.clear{clear:both}
	#positionToggle{position:absolute; top:9px; left:9px; }
	#showHideList{display:none; position:absolute; top:10px; left:10px; border-radius: 5px; border:1px solid #cccccc; background-color:#eeeeee; }
	#ListInner{padding:10px 10px 10px 10px; }

	.clearfix {
	*zoom: 1;
	}

	.clearfix:before, .clearfix:after {
	display: table;
	line-height: 0;
	content: "";
	}

	.clearfix:after {
	clear: both;
	}

	/*label,select,.ui-select-menu { float: left; margin-right: 10px; }
	.wrap ul.ui-selectmenu-menu-popup li a { font-weight: bold; }*/
	.ui-selectmenu{
	height: 1.5em;
	}
	.ui-selectmenu-status {
	line-height: 1.0em;
	}
</style>
<script>
var $j = jQuery.noConflict();
$j(window).load(function() {
	getWeather();
});
function getWeather(){
	jQuery.ajax({
		type : "GET",
		url : "../userProfile/getWeather",
		data : {},
		cache : false,
		success : function(data) {
			var jsons = eval('(' + data + ')');
			if(jsons.status='success'){
				var tbody;
				for(var i=0;i<jsons.results[0].weather_data.length;i++){
					var temp=jsons.results[0].weather_data[i];
					var tr="<tr>";
					if(i==0){
						var dateT=temp.date;
						var start=dateT.lastIndexOf("：");
						var end=dateT.lastIndexOf(")");
						dateT=dateT.substring(start+1,end);
						tr+='<td colspan="3" align="center"><div  style="float:left;padding-bottom:10px;margin-bottom:-50px;"><img src="../MetroStyleFiles/refresh.png" style="height:25px;cursor:pointer;" onclick="getWeather();"/></div><b> <img src="../MetroStyleFiles/temperature.png" style="height:25px;"/>'+dateT+'</b> <div  style="float:right;padding-bottom:10px;margin-bottom:-50px;"><a class="" data-toggle="modal" href="#WeatherDetails"><img src="../MetroStyleFiles/details.png" style="height:25px;cursor:pointer;"/> </a></div></td></tr><tr>';
						//tr+='<td colspan="3" align="center"><b>'+jsons.results[0].currentCity+'&nbsp;&nbsp;'+temp.date+'</b> <img src="../MetroStyleFiles/refresh.png" style="height:30px;cursor:pointer;" onclick="getWeather();"/></td></tr><tr>';
						tr+='<td width="20%" align="left">今天</td>';
					}else{
						tr+='<td width="20%" align="left">'+temp.date+'</td>';
					}
					tr+='<td width="55%" align="left"><img src="'+temp.dayPictureUrl +'"/><img src="'+temp.nightPictureUrl +'"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+temp.weather +'</td>';
					tr+='<td width="25%" align="right">'+temp.temperature+' </td>';
					 tr+="</tr>";
					 tbody+=tr;
				}
				tbody+='<tr><td colspan="3" align="center">'+getNowFormatDate()+'</td></tr>';
				$j('#weather').html(tbody);
			//	$j('weather_div_loading').css({'display':'none'});
			//	$j('weather_div').css({'display':'block'});
				tbody="";
				for(var i=0;i<jsons.results[0].index.length;i++){
					var temp=jsons.results[0].index[i];
					var tr="<tr>";
					var tipt;
					if(temp.tipt.length > 4){
						tipt="紫外线";
					}else{
						tipt=temp.tipt;
					}
					tr+='<td width="15%" align="right" valign="top" ><nobr><b>'+tipt+'：</b></nobr></td>';
					tr+='<td width="85%" align="left">'+temp.des+'</td>';
					 tr+="</tr>";
					 tbody+=tr;
				}
				$j('#weather_suggest').html(tbody);
			}
		}
	});
}
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes();
          //  + seperator2 + date.getSeconds();
    return currentdate;
}
    //  $j = jQuery.noConflict();
      function myAlter(err) {
          $('#myAlterMessage').html(err);
          $('#DivMyAlter').modal('show');
      }
      function myConfirm(msg,actionId) {
          $('#myConfirmMessage').html(msg);
          $('#DivMyConfirm').modal('show');
          $('#myConfirmSubmit').unbind();
          $('#myConfirmSubmit').click(function(){
              $('#DivMyConfirm').hide();
              $('[id$='+actionId+']').click();
          });
      }
      function myConfirmWithParm(msg,actionId,parm){
          $('#myConfirmMessage').html(msg);
          $('#DivMyConfirm').modal('show');
          $('#myConfirmSubmit').unbind();
          $('#myConfirmSubmit').click(function(){
              $('#DivMyConfirm').hide();
              $('[id$='+actionId+']').each(function(k){
                  if($(this).text() == parm){
                      $(this).click();
                      return;
                  }
              });
          });
      }
      </script>
  </head>
  <body>

    <div class="navbar">
      <div class="navbar-inner">
        <div class="container-fluid">
			  <img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=015900000053FQo&oid=00D90000000pkXM&lastMod=1438220916000" alt="HP Logo" class="HpLogo"/> 
              <ul class="nav pull-right top-menu">
                <li class="dropdown">
                	<a href="#" class="dropdown-toggle" data-toggle="dropdown"> Welcome <span class="username colorBlue">  <%= wcu.getNickname()%>  </span> 	</a>
						<span><a style="float:right;" href="baidu.com"> 
						<img src="<%= wcu.getHeadimgurl()%>" alt="userImage" class="userImage" alt="no_username"/>
                        <!-- <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="userImage" alt="no_username"/> -->
                        </a></span> 
                  
                </li>
              </ul>
         </div>
        </div>
    </div>

  <!-- BEGIN CONTAINER -->
  <div id="container" class="row-fluid"> 
    <!-- BEGIN PAGE -->
    <div id="main-content">
      <div class="BDbg">
        <div class="BDheading">
          <div class="span12"> 
            <div id="divBoardName"  style="dispaly:none" title='LBName'></div>
            <h2><nobr> <span class="colorDarkBlue"><%= curLoc%></span> <span style="float:right;margin-right:10px;" class="colorDarkBlue" id="location"> 
            <img src="../MetroStyleFiles/setuplocation.png" style="height:25px;"/>
            </span></nobr></h2>
          </div>
        </div>
        <div class="container-fluid" style="margin-top:0px;">
          <div class="row-fluid mtop10">
            <div class="span12">
              <div class="PositionR">
                  <img src="../MetroStyleFiles/image/socialHPE.png" class="BoardDetailImage"/>
               </div>
            </div>
          </div>
        </div>
      </div>
      <div class="container-fluid">
        <div class="row-fluid mtop20">
          <div class="span12">
            <div class="TABclass">
              <ul class="nav nav-tabs" id="myTabs">
                <li id="liSocialElements" class="active"><a href="#SocialElements" data-toggle="tab">Social Elements</a></li>
                <li id="liBoardContent" ><a href="#BoardContent" data-toggle="tab">Recognition</a></li>
                <li id="liWorkMates"><a href="#WorkMates" data-toggle="tab">Work Mates</a></li>
              </ul>
              <div class="tab-content" id="dvTabContent">
                <div class="tab-pane" id="BoardContent">
                  <div>
                    <div class="panel-group" id="accordion">
                      <div id="DivLearnings">
                      		<div  style="float:right;padding-bottom:10px;"><a class="" data-toggle="modal" href="#LBreadmore">Manage your Profile </a></div>
                            
                            <div id="LBreadmore" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true" data-backdrop="static">
                <div class="modal-header">
                 <b> Manage your profile</b>
                </div>
                <div class="modal-body readmoreHpop" style="white-space: pre-line;">
                   <table style="width:100%;">
                   	<tr>
                    	<td width="30%" align="right">Email:&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td><input type="text"/></td>
                    </tr><tr>
                    	<td width="30%" align="right">Email:&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td><input type="text"/></td>
                    </tr><tr>
                    	<td width="30%" align="right">Email:&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td><input type="text"/></td>
                    </tr><tr>
                    	<td width="30%" align="right">Email:&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td><input type="text"/></td>
                    </tr><tr>
                    	<td width="30%" align="right">Email:&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td><input type="text"/></td>
                    </tr>
                   </table>
                </div>
                <div class="modal-footer">               
						<button class="btnAthena" data-dismiss="modal" aria-hidden="true">Cancel</button>
                         <button class="btnAthenaSave" data-dismiss="modal" aria-hidden="true">Save</button>
                </div>
              </div>
                             <!--  
                              <form>
                                <input type = "button" value ="Save" class="myButtons" style="width:50%;float:right;"/>
                                </form>
                                -->
                                        <table class="table">
                                            <tbody>
                                             <tr >
                                                     <td class="text-right">
                                                        <span style="text-align:right;"><strong>Profile Photo:</strong></span>
                                                     </td>
                                                    <td>
														NKANG
													</td>
                                                  </tr>
                                                  <tr>
                                                     <td class="text-right">
                                                        <span style="text-align:right;"><strong>Nick Name:</strong></span>
                                                     </td>
                                                    <td>
                                                        NKANG
                                                    </td> 
                                                  </tr>
                                                  <tr>
                                                     <td class="text-right">
                                                        <span style="text-align:right;"><strong>Legal Name:</strong></span>
                                                     </td>
                                                    <td>
                                                        NKANG
                                                    </td> 
                                                  </tr>
                                                  <tr>
                                                     <td class="text-right">
                                                        <span style="text-align:right;" ><strong>Email:</strong></span>
                                                     </td>
                                                    <td>
                                                        NKANG
                                                    </td> 
                                                  </tr>
                                                   <tr>
                                                     <td class="text-right">
                                                        <span style="text-align:right;" ><strong>Phone:</strong></span>
                                                     </td>
                                                    <td>
                                                        NKANG
                                                    </td> 
                                                  </tr>
                                                  <tr>
                                                     <td class="text-right">
                                                         <span style="text-align:right;"><strong>Project Manager:</strong></span>
                                                     </td>
                                                    <td>
                                                        NKANG
                                                    </td>
                                                  </tr>
                                                  <tr>
                                                    <td class="text-right">
                                                         <span style="text-align:right;"><strong>Project Lead:</strong></span>
                                                     </td>
                                                    <td>
                                                        NKANG
                                                    </td>  
                                                  </tr>
                                                 
                                             </tbody>
                                        </table>
										
                      </div>
                    </div>
                  </div>
                </div>
                  <div class="tab-pane active" id="SocialElements">
                   <!-- start -->
                  <div id="WeatherDetails" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true" data-backdrop="static">
                <div class="modal-header" style="text-align:center;">
                 <img src="../MetroStyleFiles/index.png" style="height:55px;"/>
                </div>
                <div class="modal-body readmoreHpop" style="white-space: pre-line;padding:0px;">
                	<table width="95%" align="center" id="weather_suggest" style="margin-top:-15px;">
		                     </table>
		        </div>
                <div class="modal-footer">               
						<button class="btnAthena" data-dismiss="modal" aria-hidden="true">Cancel</button>
                </div>
              </div>
              <!-- end -->
                  <!-- 
               		   <div id="weather_div_loading">
               		   <img alt="内容加载中,请稍后..." src="../MetroStyleFiles/loading.gif" height="55" width="55">
               		   		<b>内容加载中,请稍后...</b>
               		   </div> style="display:none"
               		    -->
                  		<div id="weather_div">
		                       <table width="100%" id="weather" style="margin-bottom:-20px;">
		                       </table>
	                  </div>
                  </div>
                <div class="tab-pane" id="WorkMates">
                  <div>
                      <div > <!-- class="span4" -->
                          <div class="row-fluid">
                              <div class="hpit_athena_rightDiv span12" style="margin:0px;">
                                  <div style="overflow: auto; width: auto; max-height:442px;margin:0px;padding:0px;">
                                      <ul class="Work_Mates_div">
                                      	<li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li>
                                      <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li>
                                        <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li>
                                         <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li> <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li> <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li> <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li> <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li> <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li> <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li> <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li> <li class="Work_Mates_div_li">
                                        	<div class="Work_Mates_div_list_div">
                                           	 	<div class="Work_Mates_img_div">
                                        			 <img src="../MetroStyleFiles/gallery.jpg" alt="userImage" class="matesUserImage" alt="no_username"/> 
                                         		</div>
                                         		<div class="Work_Mates_img_div">
                                        			 <span>username</span>
                                        		 </div>
                                          </div>
                                        </li>
                                      </ul>
                                     
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
            
            

            
            
        </div>
      </div>
    </div>
    <!-- END PAGE -->
      </div>
      
<!-- Modal PAGE Start-->   
      <div id="DivMyAlter" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
          <div class="modal-header">
            <h3>Error Message</h3>
          </div>
          <div class="modal-body">
            <div class="span5 ml0" id="myAlterMessage">
              
            </div>
          </div>
          <div class="modal-footer">
              <button class="btnAthena EbtnLess" data-dismiss="modal" aria-hidden="true">OK</button>
          </div>
    </div>
    <div id="DivMyConfirm" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
          <div class="modal-header">
            <h3>Confirm Message</h3>
          </div>
          <div class="modal-body">
            <div class="span5 ml0" id="myConfirmMessage">
              
            </div>
          </div>
          <div class="modal-footer">
              <button class="btnAthena EbtnLess" data-dismiss="modal" aria-hidden="true">Cancel</button>
              <button class="btnAthena EbtnLess" id="myConfirmSubmit">Submit</button>
          </div>
    </div>
<!-- Modal PAGE End-->   

  <!-- BEGIN FOOTER -->
  <div id="footer"> <span><nobr>© Hewlett-Packard Enterprise Development Company, L.P.   |   HP Restricted </nobr></span></div>
  <!-- END FOOTER --> 
  </body>
  </html>