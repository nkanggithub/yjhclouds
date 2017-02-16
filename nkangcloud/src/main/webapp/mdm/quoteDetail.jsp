<%@ page language="java" pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.baseobject.OnlineQuotation"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%@ page import="com.nkang.kxmoment.baseobject.ClientMeta"%>
<%	
String AccessKey = RestUtils.callGetValidAccessKey();
List<OnlineQuotation> ql=MongoDBBasic.getAllQuotations();
String uid = request.getParameter("UID");
WeChatUser wcu;
wcu = RestUtils.getWeChatUserInfo(AccessKey, uid);
%>  
<!Doctype html>
<html>
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<link rel="stylesheet" href="../nkang/jquery.mobile.min.css" />
<script type="text/javascript" src="../nkang/jquery-1.8.0.js"></script>
<script type="text/javascript" src="../nkang/jquery.mobile.min.js"></script>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles//CSS/animation-effects.css"/>
<script type="text/javascript" src="../MetroStyleFiles/sweetalert.min.js"></script>
<script>
$(function(){

$(".singleQuote").on("swiperight",function(){
$(this).css("overflow","hidden");
$(this).removeClass("editBtn");
$(this).remove(".edit");
});
$(".singleQuote").on("swipeleft",function(){
	$(this).css("overflow","visible");
$(this).addClass("editBtn");
$(this).append("<div class='edit'><p onclick='edit(this)'>编辑</p></div>");
$(this).siblings().removeClass("editBtn");
$(this).siblings().remove(".edit");
});
function edit(obj)
{
	var isUpdatePrice="0";
	var price=$(obj).parent(".edit").siblings(".firstLayer").children(".quotePrice").find("span").text();
	var inventory=$(obj).parent(".edit").siblings(".secondLayer").children(".leftPanel").find("#inventoryValue").text();
	var soldOutOfPay=$(obj).parent(".edit").siblings(".secondLayer").children(".leftPanel").find("#soldOutOfPayValue").text();
	var onDelivery=$(obj).parent(".edit").siblings(".secondLayer").children(".leftPanel").find("#onDeliveryValue").text();
	var category=$(obj).parent(".edit").siblings(".firstLayer").children(".quoteTitle").find("#category").text();
	var item=$(obj).parent(".edit").siblings(".firstLayer").children(".quoteTitle").find("#item").text();
	var categoryGrade=$(obj).parent(".edit").siblings(".firstLayer").children(".tagStyle").text();
/* 	if(categoryGrade==""){categoryGrade="&nbsp";}
	if(category==""){category="&nbsp";} */
	var formText="<p style='width:40%;float:left;height:40px;line-height:40px;'>牌号：</p><input id='newItem' style='margin-top:0px;width:50%;height:35px;display:block;float:left;' type='text' value="+item+" disabled='true'/>"
    +"<p style='width:40%;float:left;height:40px;line-height:40px;'>级别：</p><input id='newGrade' onfocus='textClear(this)' onblur='textReturn(this)' style='margin-top:0px;width:50%;height:35px;display:block;float:left;' type='text' value="+categoryGrade+" />"
    +"<p style='width:40%;float:left;height:40px;line-height:40px;'>类型：</p><input id='newCategory' onfocus='textClear(this)' onblur='textReturn(this)' style='margin-top:0px;width:50%;height:35px;display:block;float:left;' type='text' value="+category+" />"
    +"<p style='width:40%;float:left;height:40px;line-height:40px;'>价格：</p><input id='newPrice' style='margin-top:0px;width:50%;height:35px;display:block;float:left;' type='text' value="+price+" />"
    +"<p style='width:40%;float:left;height:40px;line-height:40px;'>可用库存：</p><input id='newInventory' style='margin-top:0px;width:50%;height:35px;display:block;float:left;' type='text' value="+inventory+" />"
    +"<p style='width:40%;float:left;height:40px;line-height:40px;'>已售未下账：</p><input id='newSoldOutOfPay' style='margin-top:0px;width:50%;height:35px;display:block;float:left;' type='text' value="+soldOutOfPay+" />"  
    +"<p style='width:40%;float:left;height:40px;line-height:40px;'>在途：</p><input id='newOnDelivery' style='margin-top:0px;width:50%;height:35px;display:block;float:left;' type='text' value="+onDelivery+" />";
	swal({  
        title:"编辑报价",  
        text:formText,
        html:"true",
        showConfirmButton:"true", 
		showCancelButton: true,   
		closeOnConfirm: false,  
        confirmButtonText:"OK",  
        animation:"slide-from-top"  
      }, 
		function(inputValue){
			if (inputValue === false){ return false; }
			else{
				if($("#newPrice").val().trim()!==price)
					{isUpdatePrice="1";}
				 $.ajax({
					 url:'../updateQuotationByItem',
					 type:"POST",
					 data:{
						 item:$("#newItem").val(),
						 categoryGrade:$("#newGrade").val(),
						 category:$("#newCategory").val(),
						 quotationPrice:$("#newPrice").val(),
						 avaliableInventory:$("#newInventory").val(),
						 soldOutOfPay:$("#newSoldOutOfPay").val(),
						 onDelivery:$("#newOnDelivery").val(),
						 isUpdatePrice:isUpdatePrice
					 },
					 success: function(data) {
						 $(obj).parent().parent().removeClass("editBtn");
						 $(obj).parent().parent().remove(".edit");
						 swal("Success", "报价更新成功", "success");
						 $.ajax({
							 url:'../getAllQuotations',
							 type:"POST",
							 success:function(data){
								 if(data)
									 {
									 var html="";
									 var category="";
									 var grade="";
									 var status="";
									 for(var i=0;i<data.length;i++){
										 if(data[i].category!=""){
											 category="[<span id='category'>"+data[i].category+"</span>]";
										 }
										 if(data[i].categoryGrade!=""){
											 grade="<p class='tag tagStyle'>"+data[i].categoryGrade+"</p>";
										 }
										 if(data[i].approveStatus="0"){
											 status="<img style='position:absolute;width:80px;height:auto;top:30px;right:-40px;opacity:0.8' src='../mdm/images/progress.png' alt=''/>";
										 }
										 if(data[i].approveStatus=="1"){
											 status="<img style='position:absolute;width:80px;height:auto;top:30px;right:-40px;opacity:0.8' src='../mdm/images/approved.png' alt=''/>";
										 }
										 html+="<li class='singleQuote'>"
											 +"<div class='firstLayer'><p class='quoteTitle'><span id='item'>"+data[i].item+"</span>"+category+"</p>"+grade+"<p class='quotePrice' style='color:red'>￥<span>"+data[i].quotationPrice+"</span></p></div>"
											 +"<div class='secondLayer'>"
											 +"<div class='leftPanel'>"
											 +"<div class='shape quoteInventory '><p>可用库存</p><p id='inventoryValue'>"+data[i].avaliableInventory+"</p></div>"
											 +"<div class='shape soldOutOfPay'><p>已售未下账</p><p id='soldOutOfPayValue'>"+data[i].soldOutOfPay+"</p></div>"
											 +"<div class='shape onDelivery'><p class='ui-li-desc'>在途</p><p id='onDeliveryValue'>"+data[i].onDelivery+"</p></div>"
											 +"</div>"
											 +"<div class='rightPanel'>"
											 +"<p>"+data[i].locationAmounts.split("|")[0]+"</p>"
											 +"<p>"+data[i].locationAmounts.split("|")[1]+"</p>"
											 +"</div></div>"
											 +"<div class='edit'><p onclick='edit(this)'>编辑</p><p class='cancelEdit' onclick='cancelEdit(this);'>取消</p></div>"+status+"</li>";
										 grade="";
										 category="";
										 status="";
									 }
									 $("#QuoteList").html(html);
									 }
								 }
							 });
					 },
					 error:function(data){
						 
					 }
					 });
			}});
	}
window.edit=edit;
function textClear(obj){
	if($(obj).val()=="/"){
	$(obj).val("");}
}
function textReturn(obj){
	if($(obj).val()==""){
	$(obj).val("/");}
}
window.textClear=textClear;
window.textReturn=textReturn;
});

</script>
<!--	<link href='css/horsey.css' rel='stylesheet' type='text/css' />
	<link href='css/example.css' rel='stylesheet' type='text/css' />-->
<style>
.edit
{width: 70px;
    height: 90px;
    color: white;
    text-align: center;
    position: absolute;
    top: 0px;
    right: -70px;
	font-size:14px;
    background: red;
    border-bottom: 1px solid black;}
	.edit p
	{width:100%;
	height:30px;
	line-height:90px;
	}
.editBtn
{
position: relative;
    left: -70px;
	}
*{margin:0;padding:0;}
.singleQuote
{
position:relative;
padding-left:0px!important;
height:90px;
width:100%;
border-bottom:1px solid gray;
overflow:hidden;
}
.firstLayer{
line-height:40px;
width:100%;
height:35%;
font-size:18px;
font-weight:bold;
}
.secondLayer
{
height:60%;
font-size:14px;
margin-top: 2px;
}
.quoteTitle
{
padding-left:4%;
width:45%;
height:100%;
float:left;
}
.quotePrice
{

width:27%;
margin-right:1%;
height:100%;
float:right;
}
.shape{
margin-top:2%;

padding:1%;
margin-left:2%;
float:left;
}
.quoteInventory
{
margin-left: 5%;
    width: 24%;
    border: 1px solid #2BD2CA;
    color: #2BD2CA;
}
.soldOutOfPay
{
width: 30%;
    border: 1px solid #CBCCCE;
    color: #CBCCCE;
}
.onDelivery
{
    width: 20%;
    border: 1px solid #FF896F;
    color: #FF896F;
}
.shape p
{
text-align:center;}
.leftPanel
{
width:73%;
height:100%;
float:left;
}
.rightPanel
{
margin-top:1%;
width:27%;
height:99%;
float:left;
}
.rightPanel p
{
padding-right:5%;
width:95%;
font-size:13px;
line-height:22px;}
.HpLogo {
    position: relative;
    top: 8px;
    left: 1%;
    width: 150px;
    height: 58px;
}
.searchBox
{    border: none;
    outline: none;
    background-color: #ecf0f1;
    padding-top:10px;
	padding-bottom:10px;
	padding-left:3%;
    color: #14204f;
    border: 0;
    margin: 5px 0;
    display: block;
    width: 97%;}
	.tag
	{    width: 16%;
    margin-left: 1%;
    margin-right: 5%;
    float: left;
    height: 25px;
    margin-top: 5px;
    text-align: center;
    line-height: 26px;
    font-size: 14px;
    font-weight: normal;
    color: black;}
	.tagStyle{
	 border: 1px solid #2BD2CA;
    background: #2BD2CA;
	}
</style>
</head>
<body>
<img style="width:90px;height:auto;position:fixed;top:85%;right:10px;z-index:1000" src="../mdm/images/quotation.gif" alt="" />
<div style="padding:10px;padding-top:5px;border-bottom:2px solid #0067B6;position:relative"> 
					<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM" alt="Logo" class="HpLogo" style="display:inline !important;height:35px !important;width:auto !important;float:none;padding:0px;vertical-align:bottom;padding-bottom:10px;">
					<span class="clientSubName" style="font-size:12px;padding-left:7px;color:#333;">市场如水 企业如舟</span>
					<h2 style="color:#333;font-size:18px;padding:0px;padding-left:5px;font-weight:bold;margin-top:5px;font-family:HP Simplified, Arial, Sans-Serif !important;" class="clientName">永佳和塑胶有限公司</h2>
					<p style="position: absolute;top: 10px;right: 10px;font-size: 15px;">欢迎您,<%=wcu.getNickname() %> </p><img style="border-radius:25px;height:35px;width:35px;position:absolute;top:36px;right:10px;" src="<%=wcu.getHeadimgurl() %>" alt=""/>
				
				</div>
<!--<input class="searchBox" id='hy' />-->
<div  style="position: absolute; top: 100px;overflow:hidden" data-role="page" style="padding-top:15px" data-theme="c">
 <ul id="QuoteList" data-role="listview" data-autodividers="false" data-filter="true" data-filter-placeholder="输入牌号" data-inset="true" style="margin-top:15px">
<%
for(int i=0;i<ql.size();i++){
%>
<li class="singleQuote">
<div class="firstLayer"><p class="quoteTitle"><span id="item"><%=ql.get(i).getItem() %></span><%if(null!=ql.get(i).getCategory()){  %>[<span id="category"><%=ql.get(i).getCategory() %></span>]<%} %></p><% if(null!=ql.get(i).getCategoryGrade()&&!"".equals(ql.get(i).getCategoryGrade())){ %>
	<p class="tag tagStyle"><%=ql.get(i).getCategoryGrade() %></p><% } %><p class="quotePrice"  style="color:red">￥<span><%=ql.get(i).getQuotationPrice() %></span></p></div>

<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p id="inventoryValue"><%=ql.get(i).getAvaliableInventory() %></p></div>
<div class="shape soldOutOfPay"><p>已售未下账</p><p id="soldOutOfPayValue"><%=ql.get(i).getSoldOutOfPay() %></p></div>
<div class="shape onDelivery"><p>在途</p><p id="onDeliveryValue"><%=ql.get(i).getOnDelivery() %></p></div>
</div>
<div class="rightPanel">
<p><%=ql.get(i).getLocationAmounts().split("\\|")[0] %></p>
<p><%=ql.get(i).getLocationAmounts().split("\\|")[1] %></p>
</div>
</div>
<% if(ql.get(i).getApproveStatus().equals("0")) {%>
<img style="position:absolute;width:80px;height:auto;top:30px;right:-40px;opacity:0.8" src="../mdm/images/progress.png" alt=""/>
<% } %>
<% if(ql.get(i).getApproveStatus().equals("1")) {%>
<img style="position:absolute;width:80px;height:auto;top:30px;right:-40px;opacity:0.8" src="../mdm/images/approved.png" alt=""/>
<% } %>
</li>
<%} %>
</ul>
</div>
<!--
	<script src='js/horsey.js'></script>
	<script src='js/example.js'></script>-->
</body>
</html>