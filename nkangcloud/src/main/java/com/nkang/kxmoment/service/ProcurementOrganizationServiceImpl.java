package com.nkang.kxmoment.service;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nkang.kxmoment.service.OrganizationQuickLookupBo;
import com.nkang.kxmoment.service.ProcurementOrgSite;
import com.nkang.kxmoment.service.ProcurementOrganizationSearch;
import com.nkang.kxmoment.service.ProcurementOrganizationService;
import com.nkang.kxmoment.service.Constants;
import com.nkang.kxmoment.service.HttpSolrServer;
import com.nkang.kxmoment.service.StringUtils;
import com.nkang.kxmoment.service.WebServiceLoaderUtils;
//import com.hp.it.mdm.core.ws.procurementOrganization.BrOrganization;
//import com.hp.it.mdm.core.ws.procurementOrganization.BusinessRelationship;
//import com.hp.it.mdm.core.ws.procurementOrganization.MessageContext;
import com.nkang.kxmoment.service.Organization;
/*import com.hp.it.mdm.core.ws.procurementOrganization.PartnerLookup;
import com.hp.it.mdm.core.ws.procurementOrganization.PartnerLookupService;*/

//import com.nkang.kxmoment.service.SecurityUtil;


@Service("ProcurementOrganizationServiceImpl")
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class ProcurementOrganizationServiceImpl implements ProcurementOrganizationService{
	
	private final static Logger log = Logger.getLogger(ProcurementOrganizationServiceImpl.class);
	private static HttpSolrServer solrServer;
	private List<ProcurementOrgSite> responseList ;
	static {
        //getSolrConnection(Constants.ORGANIZATION_ACCOUNT_LUCENE_SEARCH_URL);
        getSolrConnection("http://c0050304.itcs.hp.com:8080/solr/organization-account");
    }
	

	public Organization callProcurementOrganizationByBrId(String brId) throws Exception {
		return null;	
	}

	//@Override
	public List<ProcurementOrgSite> quickSearchProcurementOrganizationByOrgId(ProcurementOrganizationSearch orgCritera, int section) {
		responseList = new ArrayList<ProcurementOrgSite>();
        try {
        	int start = section*50000;
        	int end = (section*50000) + 50000;
        	SolrQuery query = getSolrQuery(orgCritera, start, end);
			QueryResponse queryResponse = solrServer.query(query);
			SolrDocumentList docs = queryResponse.getResults();
			System.out.println("Number of Found: " + docs.getNumFound());
            Iterator<SolrDocument> iterator = docs.iterator();
            while (iterator.hasNext()) {
                SolrDocument resultDoc = iterator.next();
                ProcurementOrgSite tmpOrgSite = new ProcurementOrgSite();
                tmpOrgSite.setOrgId(getStringValue(resultDoc,"organizationId"));
                tmpOrgSite.setDunsID(getStringValue(resultDoc,"duns"));
                tmpOrgSite.setOtherPartySiteInstanceRowId(getStringValue(resultDoc,"siteInstanceId"));
                tmpOrgSite.setAddressL1(getStringValue(resultDoc,"streetAddress1"));
                tmpOrgSite.setCityName(getStringValue(resultDoc,"cityRegion"));
                tmpOrgSite.setCountryName(getStringValue(resultDoc,"countryName"));
                tmpOrgSite.setCountryName(getStringValue(resultDoc,"countryCode"));
                tmpOrgSite.setOrgExtName(getStringValue(resultDoc,"organizationExtendedName"));
                tmpOrgSite.setPostalCode(getStringValue(resultDoc,"postalCode"));
                tmpOrgSite.setRecordStatus(getStringValue(resultDoc,"deletionIndicator"));
                tmpOrgSite.setStateProvinceName(getStringValue(resultDoc,"state"));
                tmpOrgSite.setSearchLevel(getStringValue(resultDoc,"Site"));
                tmpOrgSite.setOrganizationNonLatinExtendedName(getStringValue(resultDoc,"organizationNonLatinExtendedName"));
                tmpOrgSite.setNumberOfEmployee(getStringValue(resultDoc,"countOfEmployee"));
                tmpOrgSite.setNonlatinCity(getStringValue(resultDoc,"nonlatinCity"));
                tmpOrgSite.setCustomerIndicator(getStringValue(resultDoc,"onlyPresaleCustomer"));
                tmpOrgSite.setPartnerIndicator(getStringValue(resultDoc,"returnPartnerFlag"));
                tmpOrgSite.setIsCompetitor(getStringValue(resultDoc,"isCompetitor"));
        		Object value = resultDoc.getFieldValue("industrySegmentNames");
        		List<String> starList = new ArrayList();
        		if(value!=null) {
        			starList.add(value.toString());
        			//tmpOrgSite.setIndustrySegmentNames(starList);
        			tmpOrgSite.setIndustrySegmentName(value.toString());
        		}
                responseList.add(tmpOrgSite);
            }
		} catch (SolrServerException e) {
			log.error(e);
		}
		return responseList;
	}
	
	
	public SolrQuery getSolrQuery(ProcurementOrganizationSearch request, int start, int end) {
		System.out.println("Start From " + start + " End to " + end);
		SolrQuery query = new SolrQuery();
		query.setHighlight(true).setStart(start);
        query.setTermsLimit(500);
        query.setRows(50000);
        Map<String, String> map = new HashMap<String, String>();
        map.put("wt", "json");
        map.put("indent", "true");
        StringBuilder queryPrams = new StringBuilder();
        if (!StringUtils.isEmpty(request.getOrgId())) {
        	queryPrams.append("organizationId:" + request.getOrgId() + " AND ");
        }
        if (!StringUtils.isEmpty(request.getOrgDUNsID())) {
        	queryPrams.append("duns:" + request.getOrgDUNsID() + " AND ");
        }
        if (!StringUtils.isEmpty(request.getAddress())) {
            queryPrams.append("streetAddress1:" + request.getAddress() + " AND "); 
        }
        if (!StringUtils.isEmpty(request.getOrgName())) {
            queryPrams.append("organizationExtendedName:" + request.getOrgName() + " AND ");
        }
        if (!StringUtils.isEmpty(request.getPostalCode())) {
            queryPrams.append("postalCode:" + request.getPostalCode() + " AND ");
        }
        if (!StringUtils.isEmpty(request.getStateProvinceCode())) {
            queryPrams.append("state:" + request.getStateProvinceCode() + " AND ");
        }
        if (!StringUtils.isEmpty(request.getCountryCode())) {
            queryPrams.append("countryCode:\"" + request.getCountryCode() + "\" AND ");
        }
        if (!StringUtils.isEmpty(request.getCharScriptCode())) {
            queryPrams.append("charScriptCode:\"" + request.getCharScriptCode() + "\" AND ");
        }
        queryPrams.append(" *:* ");
        
        SolrParams params = new MapSolrParams(map);
        
        query.add(params);
        query.setQuery(queryPrams.toString());
		return query;
	}
	

	//@Override
	public List<ProcurementOrgSite> quickSearchProcurementOrganizationByOrgId(String orgId)  {
		responseList = new ArrayList<ProcurementOrgSite>();
		return responseList;
	}
	

	public String getStringValue(SolrDocument doc , String fieldName) {
		String strValue = null ;
		Object value = doc.getFieldValue(fieldName);
		if(value!=null) {
			strValue = value.toString();
		}
		return strValue;
	}
	

	private static void getSolrConnection(String url) {
		System.out.println("start initial solr connection...");
        String solrServerUrl = "http://c0050304.itcs.hp.com:8080/solr/organization-account";
        System.setProperty("javax.net.ssl.trustStore", "C:/jssecacerts");
        HttpSolrServer server = new HttpSolrServer(solrServerUrl);
        solrServer = server;
        System.out.println("finish initial solr connection...");
    }

}