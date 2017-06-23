package com.nkang.kxmoment.util.OAuthUitl;
import java.io.IOException;

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

        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        //String accessToken=null;
        request.setAttribute("code", code);
        if(code!=null&&code!=""){
        	 // 用户同意授权
            if (!"authdeny".equals(code)) {
            	log.info("Start call weixinOauth2Token .......");
                // 获取网页授权access_token
                WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wxaa0a9bfa29863d87", "7b2fbfc81353fed1f7fc046129dc671d", code);
                log.info("end call weixinOauth2Token .......=="+weixinOauth2Token.getAccessToken());
                // 网页授权接口访问凭证
                if(weixinOauth2Token!=null){
                	 String accessToken = weixinOauth2Token.getAccessToken();
                	  log.info("accessToken .--->"+accessToken);
                	 // 用户标识
                     String openId = weixinOauth2Token.getOpenId();
                     log.info("openId .--->"+openId);
                     // 获取用户信息
                     SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
                     log.info("snsUserInfo .--->"+snsUserInfo.getOpenId());
                     // 设置要传递的参数
                     request.setAttribute("snsUserInfo", snsUserInfo);
                     request.setAttribute("state", state);
                }
            }
            log.info("end doFilter.......");
        }
       
       
        // 跳转到index.jsp
//        request.getRequestDispatcher("index.jsp").forward(request, response);
        chain.doFilter(request, response);
		
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}