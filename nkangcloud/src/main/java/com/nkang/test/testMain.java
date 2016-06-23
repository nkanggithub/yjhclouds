package com.nkang.test;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
import com.nkang.kxmoment.baseobject.OrganizationSearch4Solr;
import com.nkang.kxmoment.util.CsvFileWriter;
import com.nkang.kxmoment.util.MongoDBBasic;
import com.nkang.kxmoment.util.RestUtils;
import com.nkang.kxmoment.util.StopWatch;
import com.nkang.kxmoment.util.StringUtils;
import com.nkang.kxmoment.util.SolrUtils.SolrClientUtils;

public class testMain{
	private static Logger log=Logger.getLogger(testMain.class);
	private static Timer timer= new Timer();
	public static void main(String[] args) throws Exception {
	//public static void a() throws Exception {

		List<OrgOtherPartySiteInstance> opsiList = new ArrayList<OrgOtherPartySiteInstance>();
		opsiList = getOPSIList(0);
		for(OrgOtherPartySiteInstance opsi : opsiList){
			RestUtils.callMasterDataUpsert(opsi);
		}
		
/*		StopWatch sw = new StopWatch();
		for(int section = 0; section < 1 ; section ++){
			//List<ProcurementOrgSite> listOrgSite = imp.quickSearchProcurementOrganizationByOrgId(critiera, section);
			List<OrgOtherPartySiteInstance> opsiList = getOPSIList(section);
			String FileName = "MDMExtract_" + section + "_" + section*50000 + "-"+ (section*50000+50000) + ".properties";
			CsvFileWriter.writeCsvFile(FileName, opsiList);
			System.out.println("MDM Extraction Section " + section + " Completed in total Takes: " + sw);
		}*/
}
	/**
	 * @return
	 */
	private static List<OrgOtherPartySiteInstance> getOPSIList(int section) {
		List<OrgOtherPartySiteInstance> opsiList  = new ArrayList<OrgOtherPartySiteInstance>();
		OrgOtherPartySiteInstance opsi;
		OrganizationSearch4Solr solrSearchParams = new OrganizationSearch4Solr();
		solrSearchParams.setCountryCode("CN");
		solrSearchParams.setCharScriptCode("HANS");
		//solrSearchParams.setOrgId("12345");
		solrSearchParams.setStateProvinceName("重庆");
		QueryResponse queryResponse = SolrClientUtils.queryOrgInformations(solrSearchParams, section);
		
		SolrDocumentList docs = queryResponse.getResults();
		
		Iterator<SolrDocument> orgInfosIterator = docs.iterator();
		while (orgInfosIterator.hasNext()) {
			opsi = new OrgOtherPartySiteInstance();
			SolrDocument resultDoc = (SolrDocument) orgInfosIterator.next();
			opsi.setAmid2(SolrClientUtils.getStringValue(resultDoc,"amid2"));
			opsi.setBranchIndicator(SolrClientUtils.getStringValue(resultDoc,"branchIndicator"));
			opsi.setCharScriptCode(SolrClientUtils.getStringValue(resultDoc,"charScriptCode"));
			opsi.setCityRegion(SolrClientUtils.getStringValue(resultDoc,"cityRegion"));
			opsi.setCountOfEmployee(SolrClientUtils.getStringValue(resultDoc,"countOfEmployee"));
			opsi.setCountryCode(SolrClientUtils.getStringValue(resultDoc,"countryCode"));
			opsi.setCountryName(SolrClientUtils.getStringValue(resultDoc,"countryName"));
			opsi.setCountryRegionCode(SolrClientUtils.getStringValue(resultDoc,"countryRegionCode"));
			opsi.setCountryRegionName(SolrClientUtils.getStringValue(resultDoc,"countryRegionName"));
			opsi.setDeletionIndicator(SolrClientUtils.getStringValue(resultDoc,"deletionIndicator"));
			opsi.setDomesticDuns(SolrClientUtils.getStringValue(resultDoc,"domesticDuns"));
			opsi.setDuns(SolrClientUtils.getStringValue(resultDoc,"duns"));
			opsi.setFocusAccountIndicator(SolrClientUtils.getStringValue(resultDoc,"focusAccountIndicator"));
			opsi.setGlobalAccountIndicator(SolrClientUtils.getStringValue(resultDoc,"globalAccountIndicator"));
			opsi.setGlobalDuns(SolrClientUtils.getStringValue(resultDoc,"globalDuns"));
			opsi.setGlobalDunsName(SolrClientUtils.getStringValue(resultDoc,"globalDunsName"));
			opsi.setHeadDuns(SolrClientUtils.getStringValue(resultDoc,"headDuns"));
			opsi.setHeadDunsName(SolrClientUtils.getStringValue(resultDoc,"headDunsName"));
			opsi.setHyperscaleAccountIndicator(SolrClientUtils.getStringValue(resultDoc,"hyperscaleAccountIndicator"));
			opsi.setIncludePartnerOrgIndicator(SolrClientUtils.getStringValue(resultDoc,"includePartnerOrgIndicator"));
			opsi.setIndicatorBags(SolrClientUtils.getStringValue(resultDoc,"indicatorBags"));
			opsi.setIndustrySegmentNames(SolrClientUtils.getStringValue(resultDoc,"industrySegmentNames"));
			opsi.setIndustryVerticalNames(SolrClientUtils.getStringValue(resultDoc,"industryVerticalNames"));
			opsi.setIsCompetitor(SolrClientUtils.getStringValue(resultDoc,"isCompetitor"));
			opsi.setIsGlobalAccount(SolrClientUtils.getStringValue(resultDoc,"isGlobalAccount"));
			opsi.setIsOutOfBusiness(SolrClientUtils.getStringValue(resultDoc,"isOutOfBusiness"));
			opsi.setLanguageCode(SolrClientUtils.getStringValue(resultDoc,"languageCode"));
			opsi.setLatinCity(SolrClientUtils.getStringValue(resultDoc,"latinCity"));
			opsi.setLatinStreet1LongName(SolrClientUtils.getStringValue(resultDoc,"latinStreet1LongName"));
			opsi.setMailingSiteDuns(SolrClientUtils.getStringValue(resultDoc,"mailingSiteDuns"));
			opsi.setNamedAccountIndicator(SolrClientUtils.getStringValue(resultDoc,"namedAccountIndicator"));
			opsi.setNonlatinCity(SolrClientUtils.getStringValue(resultDoc,"nonlatinCity"));
			opsi.setNonlatinStreet1LongName(SolrClientUtils.getStringValue(resultDoc,"nonlatinStreet1LongName"));
			opsi.setOnlyPresaleCustomer(SolrClientUtils.getStringValue(resultDoc,"onlyPresaleCustomer"));
			opsi.setOrganizationExtendedName(SolrClientUtils.getStringValue(resultDoc,"organizationExtendedName"));
			opsi.setOrganizationId(SolrClientUtils.getStringValue(resultDoc,"organizationId"));
			opsi.setOrganizationLegalName(SolrClientUtils.getStringValue(resultDoc,"organizationLegalName"));
			opsi.setOrganizationNonLatinExtendedName(SolrClientUtils.getStringValue(resultDoc,"organizationNonLatinExtendedName"));
			opsi.setOrganizationNonLatinLegalName(SolrClientUtils.getStringValue(resultDoc,"organizationNonLatinLegalName"));
			opsi.setOrganizationNonLatinReportingName(SolrClientUtils.getStringValue(resultDoc,"organizationNonLatinReportingName"));
			opsi.setOrganizationReportingName(SolrClientUtils.getStringValue(resultDoc,"organizationReportingName"));
			opsi.setOrgCountryCode(SolrClientUtils.getStringValue(resultDoc,"orgCountryCode"));
			opsi.setOrgCountryName(SolrClientUtils.getStringValue(resultDoc,"orgCountryName"));
			opsi.setParentCountryCode(SolrClientUtils.getStringValue(resultDoc,"parentCountryCode"));
			opsi.setParentOrganizationId(SolrClientUtils.getStringValue(resultDoc,"parentOrganizationId"));
			opsi.setParentOrganizationName(SolrClientUtils.getStringValue(resultDoc,"parentOrganizationName"));
			opsi.setPostalCode(SolrClientUtils.getStringValue(resultDoc,"postalCode"));
			opsi.setPostalCode2(SolrClientUtils.getStringValue(resultDoc,"postalCode2"));
			opsi.setPresalesId(SolrClientUtils.getStringValue(resultDoc,"presalesId"));
			opsi.setRad(SolrClientUtils.getStringValue(resultDoc,"rad"));
			opsi.setRadBags(SolrClientUtils.getStringValue(resultDoc,"radBags"));
			opsi.setReturnPartnerFlag(SolrClientUtils.getStringValue(resultDoc,"radBagsreturnPartnerFlag"));
			opsi.setRplStatusCode(SolrClientUtils.getStringValue(resultDoc,"rplStatusCode"));
			opsi.setRplStatusTime(SolrClientUtils.getStringValue(resultDoc,"rplStatusTime"));
			opsi.setSalesCoverageSegments(SolrClientUtils.getStringValue(resultDoc,"salesCoverageSegments"));
			opsi.setSiteDuns(SolrClientUtils.getStringValue(resultDoc,"siteDuns"));
			opsi.setSiteId(SolrClientUtils.getStringValue(resultDoc,"siteId"));
			opsi.setSiteInstanceId(SolrClientUtils.getStringValue(resultDoc,"siteInstanceId"));
			opsi.setSiteName(SolrClientUtils.getStringValue(resultDoc,"siteName"));
			opsi.setSlsCrgSegmtNameBags(SolrClientUtils.getStringValue(resultDoc,"slsCrgSegmtNameBags"));
			opsi.setState(SolrClientUtils.getStringValue(resultDoc,"state"));
			opsi.setStreetAddress1(SolrClientUtils.getStringValue(resultDoc,"streetAddress1"));
			opsi.setStreetAddress2(SolrClientUtils.getStringValue(resultDoc,"streetAddress2"));
			opsi.setStreetAddress3(SolrClientUtils.getStringValue(resultDoc,"streetAddress3"));
			opsi.setTargetSegmentNames(SolrClientUtils.getStringValue(resultDoc,"targetSegmentNames"));
			opsi.setTargetSubSegmentNames(SolrClientUtils.getStringValue(resultDoc,"targetSubSegmentNames"));
			opsi.setTaxIds(SolrClientUtils.getStringValue(resultDoc,"taxIds"));
			opsi.setTgtSegmtNameBags(SolrClientUtils.getStringValue(resultDoc,"tgtSegmtNameBags"));
			opsi.setTopAccountIndicator(SolrClientUtils.getStringValue(resultDoc,"topAccountIndicator"));
			opsi.setTopParentOrganizationId(SolrClientUtils.getStringValue(resultDoc,"topParentOrganizationId"));
			opsi.setTopParentOrganizationName(SolrClientUtils.getStringValue(resultDoc,"topParentOrganizationName"));
			opsi.setWorldRegion(SolrClientUtils.getStringValue(resultDoc,"worldRegion"));
			opsi.setWorldRegionPath(SolrClientUtils.getStringValue(resultDoc,"worldRegionPath"));			
			opsiList.add(opsi);
		}
		return opsiList;
	}
}
