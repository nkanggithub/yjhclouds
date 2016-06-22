package com.nkang.kxmoment.service;


import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;


/**
 * @author yi.liu9@hp.com
 */
public class SecurityUtil {
	private static final String certificationPath = WebServiceLoaderUtils.getWebServiceProperty(Constants.SECURITY_CERTIFICATIONPATH).toString();
	private static final String username = WebServiceLoaderUtils.getWebServiceProperty(Constants.SECURITY_USERNAME).toString();
	private static final String password = WebServiceLoaderUtils.getWebServiceProperty(Constants.SECURITY_PASSWORD).toString();
	
	@SuppressWarnings("rawtypes")
	public static <T> T setSecurityHeader(T port){
		System.setProperty("javax.net.ssl.trustStore", certificationPath);
		// start add soap header
        Binding binding = ((BindingProvider) port).getBinding();
        List<Handler> handlerList = binding.getHandlerChain();
        if (handlerList == null) {
            handlerList = new ArrayList<Handler>();
        }
        handlerList.add(new SecurityHandler(username, password));
        binding.setHandlerChain(handlerList);
        // end add soap header
		return port;
	}
}