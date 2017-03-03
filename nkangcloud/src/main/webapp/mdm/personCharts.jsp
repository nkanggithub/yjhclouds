<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
    <%//String num = request.getParameter("num");
    String uid = request.getParameter("openid");
    Random rand = new Random();
	int randNum = rand.nextInt(30);
    int num=randNum;%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<title>附近人员分布情况</title>
<style>
ul li.Work_Mates_div_list_div2 {
    list-style: none;
    min-height: 60px !important;
}
.Work_Mates_div2 .Work_Mates_div_list_div2 {
    border-top: #ccc 1px solid;
    border-bottom: 0px;
}
</style>
<link rel="stylesheet" type="text/css"
	href="../nkang/css_athena/profile.css" />
<script type="text/javascript" src="../nkang/jquery-1.8.0.js"></script>
<script type="text/javascript" src="../Jsp/JS/fusioncharts.js"></script>
<script type="text/javascript" src="../Jsp/JS/fusioncharts.powercharts.js"></script>
<script type="text/javascript" src="../Jsp/JS/fusioncharts.theme.fint.js"></script>
</head>
<body style="margin-left:auto;margin-right:auto;">
<div id="chart-container"></div>

<ul class="Work_Mates_div2" id="Work_Mates_div" data-role="listview" data-autodividers="false" data-filter="true" data-filter-placeholder="输入关键字" data-inset="true" style="margin-top:15px">
</ul>
<script>
$(function(){ 
	 getMDLUserLists();
});
function getMDLUserLists() {
	var UpStreamList=0;
	var DownStreamList= 0;
	var PartnerList= 0;
	var CompetitorList= 0;
	var InternalList=0;
	var NoRoleList=0;
jQuery.ajax({
		type : "GET",
		url : "../CallGetWeChatUserDistanceList?openid=<%=uid %>",
		cache : false,
		success : function(data) {
			var ul = "",regNumber=0;
			//for (var i = data.length-1; i >0 ; i--) {
			for (var i = 0; i < data.length ; i++) {
				var temp = data[i];
				var selfIntro="";
				var companyName=temp.companyName;
				var companyRole=temp.role;
				var workDay=temp.workDay;
				var tag=temp.tag;
				var tagHtml="";
				var congratulate="";
				var role= new Array();
				var infoPer=0;
				
				if(temp.openid==$('#uid').val()){
					LastToLikeDate=temp.like.lastLikeDate;
					lastLikeTo=temp.like.lastLikeTo;
				}
				if(companyName==null||companyName=='null'||companyName==''){
					companyName="";
				}else{
					infoPer+=10;
				}
				if(companyRole==null||companyRole=='null'||companyRole==''){
					companyRole="";
				}else{
					companyRole="【"+companyRole+"】";
				}
				companyName+=companyRole;
				
				workDay='<div style="float:right;background-color:#eee;color:#333;font-size:13px;padding:3px;margin-right:5px;position:relative;margin-top:-28px;opacity:0.85;">'+temp.distance+'km</div>';
				var lastUpdatedDate="暂无互动";
				if(temp.lastUpdatedDate!=null&&temp.lastUpdatedDate!='null'){
					lastUpdatedDate=temp.lastUpdatedDate.substring(0,10);
				}
				
				
				var li='	<li class="Work_Mates_div_list_div2">'
					+'                                           	 	<div class="Work_Mates_img_div2">'
					+'                                        			 <img src="'
					+ ((temp.headimgurl==null||temp.headimgurl=='')?'../MetroStyleFiles/image/user.jpg':temp.headimgurl)
					+ '" alt="userImage" class="matesUserImage" alt="no_username" /> '
					//+'<p style="margin: 0 0 10px;font-size: 12px;text-align: center;color: #375FA7;margin-top: -5px;">'+lastUpdatedDate+'</p>'
					+'                                         		</div>'
					+'                                         		<div class="Work_Mates_text_div" style="margin-left: 80px;">'
					+'                                        			 <h2><span class="openid" style="display:none;">'+ temp.openid + '</span><span class="name">'
					+ temp.nickname
					+ '</span>'
					//+'<span class="role">'
					//+companyName+'</span>'
					+'</h2>'
					+ '<div>'
					//+tagHtml
					+'</div>'+workDay+'<div style="margin-top:5px;">'
					//+'													<span class="selfIntro">'+selfIntro+'</span>'
					+'												</div>'
					+'                                        		</div>'
					+'                                                <div class="clear"></div>'
					+'                                          </li>';
				ul += li;
			}
			$("#Work_Mates_div").html(ul);
		}
	});
}
FusionCharts.ready(function () {
    var budgetChart = new FusionCharts({
        type: 'radar',
        renderAt: 'chart-container',
        width: '100%',
        height: '450',
        dataFormat: 'json',
        dataSource: {
    "chart": {
        "caption": "附近人员【<%=num%>/170】分布情况",
        "bgcolor": "FFFFFF",
        "radarfillcolor": "FFFFFF",
        "plotfillalpha": "5",
        "plotborderthickness": "2",
        "anchoralpha": "500",
        "numberprefix": "",
        "numdivlines": "2",
        "legendposition": "bottom",
        "showborder": "0"
    },
    "categories": [
        {
            "font": "Arial",
            "fontsize": "11",
            "category": [
                {
                    "label": "北",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "西北",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "西",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "西南",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "南",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "东南",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "东",
                    "font": "Arial",
                    "fontsize": "11"
                },
                {
                    "label": "东北",
                    "font": "Arial",
                    "fontsize": "11"
                }
               
            ]
        }
    ],
    "dataset": [
     {
            "seriesname": "10km以内",
            "color": "0099FF",
            "anchorsides": "10",
            "anchorbordercolor": "0099FF",
            "anchorgbalpha": "0",
            "anchorradius": "2",
            "data": [
                {
                    "value": "8"
                },
                {
                    "value": "23"
                },
                {
                    "value": "13"
                },
                {
                    "value": "16"
                },
                {
                    "value": "11"
                },
                {
                    "value": "14"
                },
                {
                    "value": "10"
                },
                {
                    "value": "45"
                },
                {
                    "value": "23"
                },
                {
                    "value": "45"
                },
                {
                    "value": "23"
                },
                {
                    "value": "43"
                }
            ]
        },
        {
            "seriesname": "50km以内",
            "color": "CD6AC0",
            "anchorsides": "6",
            "anchorradius": "2",
            "anchorbordercolor": "CD6AC0",
            "anchorgbalpha": "0",
            "data": [
                {
                    "value": "50"
                },
                {
                    "value": "60"
                },
                {
                    "value": "48"
                },
                {
                    "value": "47"
                },
                {
                    "value": "37"
                },
                {
                    "value": "45"
                },
                {
                    "value": "36"
                },
                {
                    "value": "75"
                },
                {
                    "value": "35"
                },
                {
                    "value": "65"
                },
                {
                    "value": "34"
                },
                {
                    "value": "35"
                }
            ]
        }
       
    ]
}
    }).render();
});
</script>
</body>
</html>