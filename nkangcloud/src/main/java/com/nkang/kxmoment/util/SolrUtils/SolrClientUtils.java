package com.nkang.kxmoment.util.SolrUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;

import com.nkang.kxmoment.baseobject.OrganizationSearch4Solr;
import com.nkang.kxmoment.util.StringUtils;

public class SolrClientUtils{
	private static final Logger logger = Logger.getLogger(SolrClientUtils.class);
	private static HttpSolrServer solrServer;
	static {
		initSolrConnection();
	}

	public static QueryResponse queryOrgInformations(OrganizationSearch4Solr orgCriteria, int section) {
		QueryResponse queryResponse = null;
        try {
        	int start = section*50000;
        	int end = (section*50000) + 50000;
        	SolrQuery query = getSolrQuery(orgCriteria, start, end);
			queryResponse = solrServer.query(query);
		} catch (SolrServerException e) {
			logger.error(e);
		}
		return queryResponse;
	}
	
	
	private static SolrQuery getSolrQuery(OrganizationSearch4Solr request, int start, int end) {
		//System.out.println("Start From " + start + " End to " + end);
		SolrQuery query = new SolrQuery();
		query.setHighlight(true).setStart(start);
        query.setTermsLimit(500);
        query.setRows(50000);
        Map<String, String> map = new HashMap<String, String>();
        map.put("wt", "json");
        map.put("indent", "true");
        StringBuilder queryPrams = new StringBuilder();
        queryPrams.append("{!q.op=OR}+isOutOfBusiness:false AND ");
        if (!StringUtils.isEmpty(request.getOrgId())) {
        	queryPrams.append("organizationId:" + request.getOrgId() + " AND ");
        }
        List<String> orgIdList = request.getOrgIdList();
        if (orgIdList != null && orgIdList.size() > 0) {
        	String idsString = "+organizationId:(";
        	for (String orgId : orgIdList) {
            	idsString += orgId + " ";
			}
        	idsString = idsString.substring(0, idsString.length()-1) + ")";
        	queryPrams.append(idsString + " AND ");
        }
        if (!StringUtils.isEmpty(request.getTaxID())) {
        	queryPrams.append("taxIds:(\"" + request.getTaxID() + "\") AND ");
        }
        String orgName = request.getOrgName();
        if (!StringUtils.isEmpty(orgName)) {
        	orgName = "("+ orgName +")";
    		// LATIN
    		queryPrams.append("(+(organizationExtendedName:" + orgName + " ");
    		// NOLATIN
    		queryPrams.append("organizationNonLatinExtendedName:" + orgName + " ");
    		// ReportingName
    		queryPrams.append("organizationReportingName:" + orgName + " ");
    		// LegalName
    		queryPrams.append("organizationLegalName:" + orgName + " ");
    		// tradeName
    		queryPrams.append("tradeStyleNames:" + orgName + ")) AND ");
        }
        String cityName = request.getCityName();
        if (!StringUtils.isEmpty(cityName)) {
    		// LATIN
    		queryPrams.append("(+(latinCity:(\"" + cityName + "\") ");
    		// NOLATIN
    		queryPrams.append("nonlatinCity:(\"" + cityName + "\"))) AND ");
        }
        if (!StringUtils.isEmpty(request.getPostalCode())) {
            queryPrams.append("postalCode:(" + request.getPostalCode() + "*) AND ");
        }
        if (!StringUtils.isEmpty(request.getCountryCode())) {
            queryPrams.append("countryCode:(" + request.getCountryCode() + ") AND ");
        }
        String state = request.getStateProvinceName();
        if (!StringUtils.isEmpty(state)) {
        	if(state.matches("\\p{ASCII}+")){
        		// LATIN
                queryPrams.append("state:(\"" + state + "\") AND ");
        	}else{
        		// NOLATIN
                queryPrams.append("state:(\"" + state + "\") AND ");
        	}
        }

        queryPrams.append(" *:* ");
        
        SolrParams params = new MapSolrParams(map);
        
        query.add(params);
        query.setQuery(queryPrams.toString());
		return query;
	}
	
	private static void initSolrConnection() {
		if(solrServer == null){
			logger.info("start initial solr connection...");
			ResourceBundle bundle = ResourceBundle.getBundle("solrconfig");  
			String certificationPath = bundle.getString("certificationPath");
			String solrServerUrl;
		    solrServerUrl = bundle.getString("organizationaccountlucenesearch.url");
	        //String solrServerUrl = "http://c0050304.itcs.hp.com:8080/solr/organization-account";
	        System.setProperty("javax.net.ssl.trustStore", certificationPath);
	        HttpSolrServer server = new HttpSolrServer(solrServerUrl);
	        solrServer = server;
	        logger.info("finish initial solr connection...");
		}else{
			logger.info("solr is connectioned...");
		}
    }
	
	
	public static String getStringValue(SolrDocument doc , String fieldName) {
		String strValue = null ;
		Object value = doc.getFieldValue(fieldName);
		if(value!=null) {
			strValue = value.toString();
		}
		return strValue;
	}

	
}
