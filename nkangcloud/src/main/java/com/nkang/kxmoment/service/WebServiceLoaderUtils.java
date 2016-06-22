package com.nkang.kxmoment.service;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class WebServiceLoaderUtils extends PropertyPlaceholderConfigurer{
	
	private static Logger log = Logger.getLogger(WebServiceLoaderUtils.class);
	private static Map<String,Object> wsPropertiesMap;
	private static String fileName = Constants.APP_PROPERTY_WEBSERVICE_INFO;
	private static String keyName = Constants.WEBSERVICE_INFO_ENVIRONMENT_HOST_URL;
	private static String forwordKeyName = Constants.HTTP_HOST_URL;
	private static String keyNameForMDAS = Constants.WEBSERVICE_INFO_ENVIRONMENT_HOST_URL_MDAS;
	
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		wsPropertiesMap = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			log.info("web service url :" + value);
			wsPropertiesMap.put(keyStr, value);
		}
	}

	public static Object getWebServiceProperty(String name) {
		Object obj = wsPropertiesMap.get(name);
        String result = null != obj ? obj.toString() : "";
		try {
			result = result.replace("{"+keyName+"}", PropertyFetcher.getKeyFromPropertyFile(fileName, keyName).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("web service url called: "+result);
		return result;
	}
	
	public static Object getForwordProperty(String name) {
		Object obj = wsPropertiesMap.get(name);
		String result = obj.toString();
		try {
			result = result.replace("{"+forwordKeyName+"}", PropertyFetcher.getKeyFromPropertyFile(fileName, forwordKeyName).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("forword url has be set: "+result);
		return result;
	}
	
	public static Object getWebServicePropertyForMDAS(String name) {
		Object obj = wsPropertiesMap.get(name);
		String result = obj.toString();
		try {
			result = result.replace("{"+keyNameForMDAS+"}", PropertyFetcher.getKeyFromPropertyFile(fileName, keyNameForMDAS).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("MDAS web service url called: "+result);
		return result;
	}

}