<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.nkang.kxmoment.util.OAuthUitl.SNSUserInfo,java.lang.*"%>
 <%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.baseobject.OnlineQuotation"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%@ page import="com.nkang.kxmoment.baseobject.ClientMeta"%>
<%	

//获取由OAuthServlet中传入的参数
SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo"); 
String state=(String)request.getAttribute("state");
String name = "";
String headImgUrl ="";
String uid="";
String CompanyTelPhone="重庆永佳和塑胶有限公司【副总经理】邓立铭(电话：<a href='tel:13320204222'>13320204222</a>)";
if(null != user) {
	HashMap<String, String> res=MongoDBBasic.getWeChatUserFromOpenID(user.getOpenId());
	if(res!=null){
		if(res.get("HeadUrl")!=null){
			uid = user.getOpenId();
			headImgUrl=res.get("HeadUrl");
		}else{
			headImgUrl = user.getHeadImgUrl(); 
		}
		if(res.get("NickName")!=null){
			uid = user.getOpenId();
			name=res.get("NickName");
		}else{
			name = user.getNickname();
			headImgUrl = user.getHeadImgUrl(); 
			uid="oij7nt5GgpKftiaoMSKD68MTLXpc";
		}
		if(res.get("market0")!=null){
			CompanyTelPhone="重庆永佳和塑胶有限公司【"+res.get("market0")+"】"+res.get("market1") +"(电话：<a href='tel:"+res.get("market2") +"'>"+res.get("market2")+"</a>)";
		}
	}else{
		uid="oij7nt5GgpKftiaoMSKD68MTLXpc";
		name = user.getNickname();
		headImgUrl = user.getHeadImgUrl(); 
	}

	out.print("UID:");
	out.print("<br/>name:");
	out.print("<br/>headImgUrl:"+headImgUrl);
	
}else{
	out.print("用户不同意授权,未获取到用户信息！");
}
%>