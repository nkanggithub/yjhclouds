package com.nkang.kxmoment.util.OAuthUitl;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.nkang.kxmoment.util.Constants;

//import com.souvc.weixin.pojo.SNSUserInfo;
//import com.souvc.weixin.pojo.WeixinOauth2Token;
//import com.souvc.weixin.util.AdvancedUtil;

/**
* 类名: OAuthServlet </br>
* 描述: 授权后的回调请求处理 </br>
* 开发人员： souvc </br>
* 创建时间：  2015-11-27 </br>
* 发布版本：V1.0  </br>
 */
//public class OAuthServlet extends HttpServlet {
public class OAuthServlet implements Filter  {
    private Logger log = Logger.getLogger(this.getClass());
   
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
       // String getRequestURI =((HttpServletRequest) request).getServletPath();

       // String getRequestURL =((HttpServletRequest) request).getRequestURL().toString();

        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String state = request.getParameter("state");
    	HttpServletRequest req=(HttpServletRequest) request;
        String uri= req.getRequestURI(); //uri就是获取到的连接地址!
        //String accessToken=null;
        request.setAttribute("code", code);
        if(code!=null&&code!=""&&!"authdeny".equals(code)){
        	 // 用户同意授权
        	//log.info("Start call weixinOauth2Token .......");
            // 获取网页授权access_token
        	WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(Constants.APP_ID, Constants.APPSECRET, code);
             //log.info("end call weixinOauth2Token .......=="+weixinOauth2Token.getAccessToken());
            // 网页授权接口访问凭证
            if(weixinOauth2Token!=null){
            	 String accessToken = weixinOauth2Token.getAccessToken();
            	 // log.info("accessToken .--->"+accessToken);
            	 // 用户标识
                 String openId = weixinOauth2Token.getOpenId();
                 //log.info("openId .--->"+openId);
                 // 获取用户信息
                 SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
                // log.info("snsUserInfo .--->"+snsUserInfo.getOpenId());
                 // 设置要传递的参数
                 request.setAttribute("snsUserInfo", snsUserInfo);
                 request.setAttribute("state", state);
            }
        }
        SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo"); 
    	if(user==null){
    	 Enumeration  enum1=req.getParameterNames();
    	 String value="";
    	 while(enum1.hasMoreElements()){    
    		 String  paramName=(String)enum1.nextElement();    
    		 if(!"from".equals(paramName)&&!"isappinstalled".equals(paramName)&&!"code".equals(paramName)){
        		 if("".equals(value)){
        			 value+="?";
        		 }else{
        			 value+="&";
        		 }
                 value+=paramName;
                 value+="=";
                 value+=req.getParameter(paramName);
    		 }
    	 }  
    	 log.info("===============url:"+uri+value);
    	 uri=java.net.URLEncoder.encode(uri+value, "utf-8");
    	 log.info("===============encode(uri):"+uri);
    	((HttpServletResponse) response).sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constants.APP_ID+"&redirect_uri=http%3A%2F%2F"+Constants.baehost+uri+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
    	}
        
        log.info("end doFilter.......");
        chain.doFilter(request, response);
       
       
        // 跳转到index.jsp
//        request.getRequestDispatcher("index.jsp").forward(request, response);
		
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}