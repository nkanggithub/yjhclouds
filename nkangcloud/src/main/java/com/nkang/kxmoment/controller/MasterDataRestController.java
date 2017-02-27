package com.nkang.kxmoment.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import com.nkang.kxmoment.baseobject.BillOfSell;
import com.nkang.kxmoment.baseobject.ClientInformation;
import com.nkang.kxmoment.baseobject.ClientMeta;
import com.nkang.kxmoment.baseobject.GeoLocation;
import com.nkang.kxmoment.baseobject.Inventory;
import com.nkang.kxmoment.baseobject.Location;
import com.nkang.kxmoment.baseobject.MdmDataQualityView;
import com.nkang.kxmoment.baseobject.OnDelivery;
import com.nkang.kxmoment.baseobject.OnlineQuotation;
import com.nkang.kxmoment.baseobject.OrderNopay;
import com.nkang.kxmoment.baseobject.OrgCountryCode;
import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
import com.nkang.kxmoment.baseobject.QuotationList;
import com.nkang.kxmoment.baseobject.Teamer;
import com.nkang.kxmoment.baseobject.WeChatMDLUser;
import com.nkang.kxmoment.baseobject.WeChatUser;
import com.nkang.kxmoment.util.BillOfSellPoi;
import com.nkang.kxmoment.util.DBUtils;
import com.nkang.kxmoment.util.MongoDBBasic;
import com.nkang.kxmoment.util.RestUtils;


@RestController
public class MasterDataRestController {
	private static Logger log=Logger.getLogger(MasterDataRestController.class);
	
	@RequestMapping("/masterdataupsert")
	public String getMasterData(@RequestParam(value="siteInstanceId", required=false) String siteInstanceId,
								@RequestParam(value="organizationId", required=false) String organizationId,
								@RequestParam(value="OrganizationNonLatinName", required=false) String OrganizationNonLatinName,
								@RequestParam(value="onlyPresaleCustomer", required=false) String onlyPresaleCustomer,
								@RequestParam(value="siteId", required=false) String siteId,
								@RequestParam(value="isOutOfBusiness", required=false) String isOutOfBusiness,
								@RequestParam(value="siteDuns", required=false) String siteDuns,
								@RequestParam(value="mailingSiteDuns", required=false) String mailingSiteDuns,
								@RequestParam(value="countOfEmployee", required=false) String countOfEmployee,
								@RequestParam(value="organizationExtendedName", required=false) String organizationExtendedName,
								@RequestParam(value="organizationReportingName", required=false) String organizationReportingName,
								@RequestParam(value="organizationLegalName", required=false) String organizationLegalName,
								@RequestParam(value="deletionIndicator", required=false) String deletionIndicator,
								@RequestParam(value="duns", required=false) String duns,
								@RequestParam(value="organizationNonLatinExtendedName", required=false) String organizationNonLatinExtendedName,
								@RequestParam(value="organizationNonLatinReportingName", required=false) String organizationNonLatinReportingName,
								@RequestParam(value="organizationNonLatinLegalName", required=false) String organizationNonLatinLegalName,
								@RequestParam(value="taxIds", required=false) String taxIds,
								@RequestParam(value="globalDuns", required=false) String globalDuns,
								@RequestParam(value="domesticDuns", required=false) String domesticDuns,
								@RequestParam(value="amid2", required=false) String amid2,
								@RequestParam(value="presalesId", required=false) String presalesId,
								@RequestParam(value="latinStreet1LongName", required=false) String latinStreet1LongName,
								@RequestParam(value="nonlatinStreet1LongName", required=false) String nonlatinStreet1LongName,
								@RequestParam(value="latinCity", required=false) String latinCity,
								@RequestParam(value="nonlatinCity", required=false) String nonlatinCity,
								@RequestParam(value="postalCode", required=false) String postalCode,
								@RequestParam(value="postalCode2", required=false) String postalCode2,
								@RequestParam(value="charScriptCode", required=false) String charScriptCode,
								@RequestParam(value="languageCode", required=false) String languageCode,
								@RequestParam(value="cityRegion", required=false) String cityRegion,
								@RequestParam(value="state", required=false) String state,
								@RequestParam(value="countryName", required=false) String countryName,
								@RequestParam(value="countryCode", required=false) String countryCode,
								@RequestParam(value="orgCountryName", required=false) String orgCountryName,
								@RequestParam(value="orgCountryCode", required=false) String orgCountryCode,
								@RequestParam(value="worldRegion", required=false) String worldRegion,
								@RequestParam(value="rplStatusCode", required=false) String rplStatusCode,
								@RequestParam(value="rplStatusTime", required=false) String rplStatusTime,
								@RequestParam(value="isCompetitor", required=false) String isCompetitor,
								@RequestParam(value="isGlobalAccount", required=false) String isGlobalAccount,
								@RequestParam(value="industrySegmentNames", required=false) String industrySegmentNames,
								@RequestParam(value="industryVerticalNames", required=false) String industryVerticalNames,
								@RequestParam(value="worldRegionPath", required=false) String worldRegionPath,
								@RequestParam(value="countryRegionCode", required=false) String countryRegionCode,
								@RequestParam(value="countryRegionName", required=false) String countryRegionName,
								@RequestParam(value="parentOrganizationId", required=false) String parentOrganizationId,
								@RequestParam(value="parentOrganizationName", required=false) String parentOrganizationName,
								@RequestParam(value="parentCountryCode", required=false) String parentCountryCode,
								@RequestParam(value="topParentOrganizationId", required=false) String topParentOrganizationId,
								@RequestParam(value="topParentOrganizationName", required=false) String topParentOrganizationName,
								@RequestParam(value="headDuns", required=false) String headDuns,
								@RequestParam(value="headDunsName", required=false) String headDunsName,
								@RequestParam(value="globalDunsName", required=false) String globalDunsName,
								@RequestParam(value="siteName", required=false) String siteName,
								@RequestParam(value="targetSegmentNames", required=false) String targetSegmentNames,
								@RequestParam(value="targetSubSegmentNames", required=false) String targetSubSegmentNames,
								@RequestParam(value="focusAccountIndicator", required=false) String focusAccountIndicator,
								@RequestParam(value="globalAccountIndicator", required=false) String globalAccountIndicator,
								@RequestParam(value="namedAccountIndicator", required=false) String namedAccountIndicator,
								@RequestParam(value="topAccountIndicator", required=false) String topAccountIndicator,
								@RequestParam(value="hyperscaleAccountIndicator", required=false) String hyperscaleAccountIndicator,
								@RequestParam(value="branchIndicator", required=false) String branchIndicator,
								@RequestParam(value="rad", required=false) String rad,
								@RequestParam(value="radBags", required=false) String radBags,
								@RequestParam(value="salesCoverageSegments", required=false) String salesCoverageSegments,
								@RequestParam(value="includePartnerOrgIndicator", required=false) String includePartnerOrgIndicator,
								@RequestParam(value="indicatorBags", required=false) String indicatorBags,
								@RequestParam(value="tgtSegmtNameBags", required=false) String tgtSegmtNameBags,
								@RequestParam(value="slsCrgSegmtNameBags", required=false) String slsCrgSegmtNameBags,
								@RequestParam(value="streetAddress1", required=false) String streetAddress1,
								@RequestParam(value="streetAddress2", required=false) String streetAddress2,
								@RequestParam(value="streetAddress3", required=false) String streetAddress3,
								@RequestParam(value="addressType", required=false) String addressType,
								@RequestParam(value="lat", required=false) String lat,
								@RequestParam(value="lng", required=false) String lng,
								@RequestParam(value="qualityGrade", required=false) String qualityGrade,
								@RequestParam(value="returnPartnerFlag", required=false) String returnPartnerFlag)
	{
		String ret = "error";
		try{
			OrgOtherPartySiteInstance opsi = new OrgOtherPartySiteInstance();
			opsi.setAmid2(amid2);
			opsi.setBranchIndicator(branchIndicator);
			opsi.setCharScriptCode(charScriptCode);
			opsi.setCityRegion(cityRegion);
			opsi.setCountOfEmployee(countOfEmployee);
			opsi.setCountryCode(countryCode);
			opsi.setCountryName(countryName);
			opsi.setCountryRegionCode(countryRegionCode);
			opsi.setCountryRegionName(countryRegionName);
			opsi.setDeletionIndicator(deletionIndicator);
			opsi.setDomesticDuns(domesticDuns);
			opsi.setDuns(duns);
			opsi.setFocusAccountIndicator(focusAccountIndicator);
			opsi.setGlobalAccountIndicator(globalAccountIndicator);
			opsi.setGlobalDuns(globalDuns);
			opsi.setGlobalDunsName(globalDunsName);
			opsi.setHeadDuns(headDuns);
			opsi.setHeadDunsName(headDunsName);
			opsi.setHyperscaleAccountIndicator(hyperscaleAccountIndicator);
			opsi.setIncludePartnerOrgIndicator(includePartnerOrgIndicator);
			opsi.setIndicatorBags(indicatorBags);
			opsi.setIndustrySegmentNames(industrySegmentNames);
			opsi.setIndustryVerticalNames(industryVerticalNames);
			opsi.setIsCompetitor(isCompetitor);
			opsi.setIsGlobalAccount(isGlobalAccount);
			opsi.setIsOutOfBusiness(isOutOfBusiness);
			opsi.setLanguageCode(languageCode);
			opsi.setLatinCity(latinCity);
			opsi.setLatinStreet1LongName(latinStreet1LongName);
			opsi.setMailingSiteDuns(mailingSiteDuns);
			opsi.setNamedAccountIndicator(namedAccountIndicator);
			opsi.setNonlatinCity(nonlatinCity);
			opsi.setNonlatinStreet1LongName(nonlatinStreet1LongName);
			opsi.setOnlyPresaleCustomer(onlyPresaleCustomer);
			opsi.setOrganizationExtendedName(organizationExtendedName);
			opsi.setOrganizationId(organizationId);
			opsi.setOrganizationLegalName(organizationLegalName);
			opsi.setOrganizationNonLatinExtendedName(organizationNonLatinExtendedName);
			opsi.setOrganizationNonLatinLegalName(organizationNonLatinLegalName);
			opsi.setOrganizationNonLatinReportingName(organizationNonLatinReportingName);
			opsi.setOrganizationReportingName(organizationReportingName);
			opsi.setOrgCountryCode(orgCountryCode);
			opsi.setOrgCountryName(orgCountryName);
			opsi.setParentCountryCode(parentCountryCode);
			opsi.setParentOrganizationId(parentOrganizationId);
			opsi.setParentOrganizationName(parentOrganizationName);
			opsi.setPostalCode(postalCode);
			opsi.setPostalCode2(postalCode2);
			opsi.setPresalesId(presalesId);
			opsi.setRad(rad);
			opsi.setRadBags(radBags);
			//opsi.setReturnPartnerFlag(radBagsreturnPartnerFlag);
			opsi.setRplStatusCode(rplStatusCode);
			opsi.setRplStatusTime(rplStatusTime);
			opsi.setSalesCoverageSegments(salesCoverageSegments);
			opsi.setSiteDuns(siteDuns);
			opsi.setSiteId(siteId);
			opsi.setSiteInstanceId(siteInstanceId);
			opsi.setSiteName(siteName);
			opsi.setSlsCrgSegmtNameBags(slsCrgSegmtNameBags);
			opsi.setState(state);
			opsi.setStreetAddress1(streetAddress1);
			opsi.setStreetAddress2(streetAddress2);
			opsi.setStreetAddress3(streetAddress3);
			opsi.setTargetSegmentNames(targetSegmentNames);
			opsi.setTargetSubSegmentNames(targetSubSegmentNames);
			opsi.setTaxIds(taxIds);
			opsi.setTgtSegmtNameBags(tgtSegmtNameBags);
			opsi.setTopAccountIndicator(topAccountIndicator);
			opsi.setTopParentOrganizationId(topParentOrganizationId);
			opsi.setTopParentOrganizationName(topParentOrganizationName);
			opsi.setWorldRegion(worldRegion);
			opsi.setWorldRegionPath(worldRegionPath);	
			opsi.setLat(lat);
			opsi.setLng(lng);
			opsi.setQualityGrade(qualityGrade);

			ret = MongoDBBasic.mongoDBInsert(opsi);
		}
		catch(Exception e){
			ret = "failed";
		}

		return ret;
	}
	
	@RequestMapping("/getValidAccessKey")
	public String getValidAccessKey(@RequestParam(value="accessKey", required=false) String siteInstanceId){
		String AK="";
		try{
			AK = MongoDBBasic.getValidAccessKey();
		}
		catch(Exception e){
			AK = "failed";
		}
		return AK;
	}

	@RequestMapping("/updateWechatUser")
	public String updateWechatUser(	@RequestParam(value="OpenID", required=false) String OpenID,
									@RequestParam(value="Lat", required=false) String Lat,
									@RequestParam(value="Lng", required=false) String Lng,
									@RequestParam(value="AccessKey", required=false) String AccessKey)
	{
		boolean result=false;
		try{
			WeChatUser wcu = RestUtils.getWeChatUserInfo(AccessKey, OpenID);
			result = MongoDBBasic.updateUser(OpenID, Lat, Lng, wcu);
		}
		catch(Exception e){
			result = false;
		}
		return String.valueOf(result);
	}
	@RequestMapping("/callUpdateClientMeta")
	public String callUpdateClientMeta(	@RequestParam(value="ClientCode", required=true) String ClientCode,
									@RequestParam(value="ClientLogo", required=false) String ClientLogo,
									@RequestParam(value="ClientName", required=false) String ClientName,
									@RequestParam(value="ClientSubName", required=false) String ClientSubName,
									@RequestParam(value="ClientCopyRight", required=false) String ClientCopyRight,
									@RequestParam(value="ClientThemeColor", required=false) String ClientThemeColor,
									@RequestParam(value="Slide1", required=false) String Slide1,
									@RequestParam(value="Slide2", required=false) String Slide2,
									@RequestParam(value="Slide3", required=false) String Slide3)
	{
		boolean result=false;
		ClientMeta cm=new ClientMeta();
		cm.setClientStockCode(ClientCode);
		cm.setClientName(ClientName);
		cm.setClientLogo(ClientLogo);
		cm.setClientSubName(ClientSubName);
		cm.setClientCopyRight(ClientCopyRight);
		cm.setClientThemeColor(ClientThemeColor);
		ArrayList<HashMap<String, String>> slideList=new ArrayList<HashMap<String, String>>();
		HashMap<String, String> a=new HashMap<String, String>();
		a.put("src", Slide1);
		slideList.add(a);
		a=new HashMap<String, String>();
		a.put("src", Slide2);
		slideList.add(a);
		a=new HashMap<String, String>();
		a.put("src", Slide3);
		slideList.add(a);
		cm.setSlide(slideList);
		try{
			result = MongoDBBasic.updateClientMeta(cm);
		}
		catch(Exception e){
			result = false;
		}
		return String.valueOf(result);
	}
	@RequestMapping("/ActivaeClientMeta")
	public String ActivaeClientMeta(	@RequestParam(value="clientCode", required=true) String clientCode)
	{
		boolean result=false;
		try{
			MongoDBBasic.ActivaeClientMeta(clientCode);
			result=true;
		}
		catch(Exception e){
			result = false;
		}
		return String.valueOf(result);
	}
	
	@RequestMapping("/getDBUserGeoInfo")
	public static GeoLocation callGetDBUserGeoInfo(@RequestParam(value="OpenID", required=false) String OpenID){
		GeoLocation geoLocation = new GeoLocation();
		try{
			geoLocation = MongoDBBasic.getDBUserGeoInfo(OpenID);
		}		
		catch(Exception e){
			geoLocation = null;
		}
		return geoLocation;
	}
	
	@RequestMapping("/getDataQualityReport")
	public static MdmDataQualityView callGetDataQualityReport(){
		MdmDataQualityView mdmDataQualityView = new MdmDataQualityView();
		try{
			mdmDataQualityView = DBUtils.getDataQualityReport();
		}		
		catch(Exception e){
			mdmDataQualityView = null;
		}
		return mdmDataQualityView;
	}
	
	@RequestMapping("/getDataQualityReportByParameter")
	public static MdmDataQualityView callGetDataQualityReportByParameter(   @RequestParam(value="stateProvince", required=false) String stateProvince,
																			@RequestParam(value="nonlatinCity", required=false) String nonlatinCity,
																			@RequestParam(value="cityRegion", required=false) String cityRegion){
		MdmDataQualityView mdmDataQualityView = new MdmDataQualityView();
		try{
			mdmDataQualityView = MongoDBBasic.getDataQualityReport(stateProvince, nonlatinCity, cityRegion);
		}		
		catch(Exception e){
			mdmDataQualityView = null;
		}
		return mdmDataQualityView;
	}
	/*
	 * author chang-zheng
	 */
	@RequestMapping("/getDataQualityReportByParameterV2")
	public static Map<String, MdmDataQualityView> callGetDataQualityReportByParameter(   @RequestParam(value="stateProvince", required=false) String stateProvince,
																			@RequestParam(value="nonlatinCity", required=false) List<String> nonlatinCity,
																			@RequestParam(value="cityRegion", required=false) String cityRegion){
	//	MdmDataQualityView mdmDataQualityView = new MdmDataQualityView();
		Map<String, MdmDataQualityView> map = new HashMap<String, MdmDataQualityView>();
		try{
			//mdmDataQualityView = MongoDBBasic.getDataQualityReport(stateProvince, nonlatinCity, cityRegion);
			map = MongoDBBasic.getDataQualityReport(stateProvince, nonlatinCity, cityRegion);
		}		
		catch(Exception e){
			//mdmDataQualityView = null;
			map = null;
		}
		return map;
	}
	
	@RequestMapping("/getFilterSegmentArea")
	public static  List<String> callGetFilterSegmentArea(){
		List<String> listOfSegmentArea = new ArrayList<String>();
		try{
			listOfSegmentArea = DBUtils.getFilterSegmentArea();
		}		
		catch(Exception e){
			listOfSegmentArea = null;
		}
		return listOfSegmentArea;
	}
	
	@RequestMapping("/getFilterSegmentAreaFromMongo")
	public static  List<String> callGetFilterSegmentAreaFromMongo(@RequestParam(value="state", required=false) String state){
		List<String> segmentArea = new ArrayList<String>();
		try{
			segmentArea = MongoDBBasic.getFilterSegmentArea(state);
		}		
		catch(Exception e){
			segmentArea.add("--2--" + e.getMessage().toString());
		}
		return segmentArea;
	}
	
	@RequestMapping("/insertCommentsFromVisitor")
	public static  boolean callInsertCommentsFromVisitor(@RequestParam(value="OpenID", required=false) String OpenID,
														 @RequestParam(value="comments", required=false) String comments){
		boolean ret = false;
		try{
			ret = MongoDBBasic.InsertCommentsFromVisitor(OpenID, comments);
		}		
		catch(Exception e){
			ret = false;
		}
		return ret;
	}
	
	@RequestMapping("/getFilterRegionFromMongo")
	public static  List<String> callGetFilterRegionFromMongo(@RequestParam(value="state", required=false) String state){
		List<String> regions = new ArrayList<String>();
		try{
			regions = MongoDBBasic.getFilterRegionFromMongo(state);
		}		
		catch(Exception e){
			regions.add("--2--" + e.getMessage().toString());
		}
		return regions;
	}
	
	@RequestMapping("/getFilterNonLatinCityFromMongo")
	public static  List<String> callGetFilterNonLatinCityFromMongo(@RequestParam(value="state", required=false) String state){
		List<String> nonLatiCities = new ArrayList<String>();
		try{
			nonLatiCities = MongoDBBasic.getFilterNonLatinCitiesFromMongo(state);
		}		
		catch(Exception e){
			nonLatiCities.add("--2--" + e.getMessage().toString());
		}
		return nonLatiCities;
	}
	
	@RequestMapping("/getFilterStateFromMongo")
	public static  List<String> callGetFilterStateFromMongo(){
		List<String> states = new ArrayList<String>();
		try{
			states = MongoDBBasic.getFilterStateFromMongo();
		}		
		catch(Exception e){
			states.add("--2--" + e.getMessage().toString());
		}
		return states;
	}
	
	@RequestMapping("/getFilterCountOnCriteriaFromMongo")
	public static  String CallgetFilterCountOnCriteriaFromMongo(@RequestParam(value="industrySegmentNames", required=false) String industrySegmentNames,
																@RequestParam(value="nonlatinCity", required=false) String nonlatinCity,
																@RequestParam(value="state", required=false) String state,
																@RequestParam(value="cityRegion", required=false) String cityRegion
			){
		String ret = "error";
		try{
			ret = String.valueOf(MongoDBBasic.getFilterCountOnCriteriaFromMongo(industrySegmentNames,nonlatinCity,state,cityRegion)) ;
		}		
		catch(Exception e){
			ret = e.getMessage().toString();
		}
		return ret;
	}
	
	/*
	 * by chang-zheng
	 */
	@RequestMapping("/CallgetFilterCountOnCriteriaFromMongoBylistOfSegmentArea")
	public static   Map<String,String> CallgetFilterCountOnCriteriaFromMongoBylistOfSegmentArea(@RequestParam(value="industrySegmentNames", required=false) List<String> industrySegmentNames,
																@RequestParam(value="nonlatinCity", required=false) String nonlatinCity,
																@RequestParam(value="state", required=false) String state,
																@RequestParam(value="cityRegion", required=false) String cityRegion
			){
		Map<String,String> ret = null;
		try{
			ret = MongoDBBasic.CallgetFilterCountOnCriteriaFromMongoBylistOfSegmentArea(industrySegmentNames,nonlatinCity,state,cityRegion) ;
		}		
		catch(Exception e){
			ret = null;
		}
		return ret;
	}
	
	@RequestMapping("/getFilterTotalOPSIFromMongo")
	public static  String CallgetFilterTotalOPSIFromMongo(
																@RequestParam(value="state", required=false) String state,
																@RequestParam(value="nonlatinCity", required=false) String nonlatinCity,
																@RequestParam(value="cityRegion", required=false) String cityRegion){
		String ret = "error";
		try{
			ret = MongoDBBasic.getFilterTotalOPSIFromMongo(state, nonlatinCity, cityRegion);
		}		
		catch(Exception e){
			ret = e.getMessage().toString();
		}
		return ret;
	}
	
	@RequestMapping("/getFilterOnIndustryByAggregateFromMongo")
	public static  List<String> CallgetFilterOnIndustryByAggregateFromMongo(){
		List<String> ret = new ArrayList<String>();
		ret.add("--in rest service--");
		try{
			ret = MongoDBBasic.getFilterOnIndustryByAggregateFromMongo();
		}		
		catch(Exception e){
			ret.add("----" + e.getMessage());
		}
		return ret;
	}
	
	@RequestMapping("/setLocationtoMongoDB")
	public static  String CallsetLocationtoMongoDB(@RequestParam(value="state", required=false) String state){
		String ret;

		try{
			ret = MongoDBBasic.setLocationtoMongoDB(state);
		}		
		catch(Exception e){
			ret = "error occurs...";
		}
		return ret;
	}
	
	
	@RequestMapping("/LoadClientIntoMongoDB")
	public static String CallLoadClientIntoMongoDB(@RequestParam(value="ClientID", required=false) String ClientID,
			@RequestParam(value="ClientIdentifier", required=false) String ClientIdentifier,
			@RequestParam(value="ClientDesc", required=false) String ClientDesc,
			@RequestParam(value="WebService", required=false) String WebService){
		String ret="Completed";
		try{
			ret = MongoDBBasic.CallLoadClientIntoMongoDB(ClientID,ClientIdentifier,ClientDesc, WebService);
		}		
		catch(Exception e){
			ret = "error occurs...";
		}
		return ret;
	}
	
/*	@RequestMapping(value="/LoadClientIntoMongoDB", method = RequestMethod.POST)
	public static String CallLoadClientIntoMongoDB(HttpServletResponse response, HttpServletRequest request){
		String ret="Completed";
		try{
			request.setCharacterEncoding("UTF-8");
			log.info("--request---" + request.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String jsonStr = null;
			StringBuilder result = new StringBuilder();
			try {
				while ((jsonStr = reader.readLine()) != null) {
					result.append(jsonStr);
				}
				log.info("--ret---" + result.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info("--2222111---");
			reader.close();
			JSONObject jsonObject = new JSONObject(result.toString());
			log.info("--22222---");
			String ClientID = jsonObject.getString("ClientID");
			String ClientIdentifier = jsonObject.getString("ClientIdentifier");
			String ClientDesc = jsonObject.getString("ClientDesc");
			String WebService = jsonObject.getString("WebService");
			log.info("--4444---");
			ret = MongoDBBasic.CallLoadClientIntoMongoDB(ClientID,ClientIdentifier,ClientDesc, WebService);
			log.info("--55555---");
		}		
		catch(Exception e){
			ret = ret + "error occurs..." + e.getMessage();
		}
		return ret;
	}
	*/
	@RequestMapping("/CallGetClientFromMongoDB")
	public static List<ClientInformation> CallGetClientFromMongoDB(){
		List<ClientInformation> ret = new ArrayList<ClientInformation>();
		try{
			ret = MongoDBBasic.CallGetClientFromMongoDB();
		}		
		catch(Exception e){
			//ret.add(e.getMessage());
		}
		return ret;
	}
	
	@RequestMapping("/CallGetOPSIWithOutLatLngFromMongoDB")
	public static List<OrgOtherPartySiteInstance> CallgetOPSIWithOutLatLngFromMongoDB(){
		List<OrgOtherPartySiteInstance> ret = new ArrayList<OrgOtherPartySiteInstance>();
		try{
			ret = MongoDBBasic.getOPSIWithOutLatLngFromMongoDB();
		}		
		catch(Exception e){
			//ret.add(e.getMessage());
		}
		return ret;
	}
	
	@RequestMapping("/CallGetWeChatUserFromMongoDB")
	public static List<WeChatMDLUser> CallGetWeChatUserFromMongoDB(
			@RequestParam(value="openid", required=false) String openid
			){
		List<WeChatMDLUser> ret = new ArrayList<WeChatMDLUser>();;
		try{
			ret = MongoDBBasic.getWeChatUserFromMongoDB(openid);
		}		
		catch(Exception e){
			//ret.add(e.getMessage());
		}
		return ret;
	}
	@RequestMapping(value = "/queryUserKM")
	public static List<String> queryUserKM(@RequestParam(value="openid", required=true) String openid) {
		List<String> res=new ArrayList<String>();
		res=MongoDBBasic.queryUserKM(openid);
		return res;
	}
	@RequestMapping(value = "/saveUserKM")
	public static boolean saveUserKM(@RequestParam(value="openid", required=true) String openid,
			@RequestParam(value="kmItem", required=true) String kmItem,
			@RequestParam(value="flag", required=true) String flag) {
		boolean res=false;
		res=MongoDBBasic.saveUserKM(openid, kmItem,flag);
		return res;
	}
	@RequestMapping("/CallRegisterUser")
	public static boolean CallRegisterUser(
			@RequestParam(value="openid", required=false) String openid,
			@RequestParam(value="registerDate", required=false) String registerDate,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="phone", required=false) String phone,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="companyname", required=false) String companyname
			//@RequestParam(value="role", required=false) String role,
			//@RequestParam(value="selfIntro", required=false) String selfIntro,
			//@RequestParam(value="group", required=false) String groupid,
			//@RequestParam(value="suppovisor", required=false) String suppovisor,
			//@RequestParam(value="skill", required=false) String skill
			){
		boolean ret = false;
		Teamer teamer = new Teamer();
		teamer.setOpenid(openid);
		teamer.setRegisterDate(registerDate);
		teamer.setEmail(email);
		teamer.setPhone(phone);
		teamer.setCompanyName(companyname);
		teamer.setRealName(name);
		//teamer.setRole(role);
		//teamer.setGroupid(groupid);
		//teamer.setSelfIntro(selfIntro);
		//teamer.setSuppovisor(suppovisor);
		/*ArrayList taglist=new ArrayList();
		String[] tagArr = skill.split(",");
		for(int i=0;i<tagArr.length;i++){
			String[] tag=tagArr[i].split(":");
			if(tag.length==2){
				HashMap<String, String> temp=new HashMap<String, String>();
				temp.put("key", tag[0]);
				temp.put("value", tag[1]);
				taglist.add(temp);
			}
		}
		teamer.setTag(taglist);*/
		try{
			ret = MongoDBBasic.registerUser(teamer);
		}		
		catch(Exception e){
			ret = false;
		}
		return ret;
		
	}
	
	@RequestMapping("/CallUpdateOpptLatLngIntoMongoDB")
	public static String CallUpdateOpptLatLngIntoMongoDB( 
			@RequestParam(value="state", required=false) String state
			){
		String ret = "";
		try{
			ret = MongoDBBasic.updateOpptLatLngIntoMongoDB(state);
		}		
		catch(Exception e){
			ret = e.getMessage().toString();
		}
		return ret;
	}
	
	/*
	 * author  chang-zheng
	 * get opsi by country
	 */
	@RequestMapping("/getCountrycount")
	public @ResponseBody List<Object[]> getDataQualityDetailReport(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "country") String country
			)
	{
		List<MdmDataQualityView> listOfCountry = MongoDBBasic.getDataQualityReportOSfCountry(country);
		List<Object[]> finalString=new ArrayList<Object[]>();
		Object[] a = new Object[2];
		a[0]="Customer";
		Object[] b = new Object[2];
		b[0]="Partner";
		Object[] c = new Object[2];
		c[0]="Competitor";
		Object[] d = new Object[2];
		d[0]="Lead";
		
		MdmDataQualityView dqv = listOfCountry.get(0);
		a[1]=dqv.getNumberOfCustomer();
		b[1]=dqv.getNumberOfPartner();
		c[1]=dqv.getNumberOfCompetitor();
		d[1]=dqv.getNumberOfLeads();
		finalString.add(a);
    	finalString.add(b);
    	finalString.add(c);
    	finalString.add(d);
		return finalString;
	}
	
/*
 * 
 * chang-zheng
 */
	
	@RequestMapping("/getCitydetailxx")
	public @ResponseBody List<List<OrgOtherPartySiteInstance>> getDataQualityReportbynonatinCityxx(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "userState") String state,@RequestParam(value = "nonlatinCity") String City,@RequestParam(value = "cityRegion",required=false) String cityRegion)
	{
		return  MongoDBBasic.getDataQualityReportbynonatinCity(state, City, cityRegion);
	}
	/*
	 * 
	 * chang-zheng
	 */
	/*@RequestMapping("/InsertOrgCountryCode")
	public @ResponseBody boolean InsertOrgCountryCode(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "OrgCountryCode") OrgCountryCode octc)
	{
		return  MongoDBBasic.createcountryCodes(octc);
		
	}*/
	/*
	 * chang-zheng
	 * 
	 */
	@RequestMapping("/ReadOrgCountryCode")
	public @ResponseBody Map<String,OrgCountryCode> ReadOrgCountryCode(){
		URL xmlpath = MasterDataRestController.class.getClassLoader().getResource("sumOrgCountCountryCode.json"); 
		String path = xmlpath.toString();
		path=path.substring(5);  
		System.out.println(xmlpath);
		String url = path;
		Map<String,OrgCountryCode> map = RestUtils.ReadCountryCode(url);
		return map;
	}
	
	/*
	 * chang-zheng
	 * 
	 */
	/*@RequestMapping("/ReadCountryCodeByCountryCode")
	public @ResponseBody Map<String,String> ReadCountryCodeByCountryCode(@RequestParam(value = "countryCode") String countryCode){
		OrgCountryCode orgcode = new OrgCountryCode();
		Map<String,String> codeMap = new HashMap<String,String>();
		URL xmlpath = MasterDataRestController.class.getClassLoader().getResource("sumOrgCountCountryCode.json"); 
		String path = xmlpath.toString();
		path=path.substring(5);  
		System.out.println(xmlpath);
		String url = path;
		orgcode = RestUtils.ReadCountryCodeByCountryCode(url,countryCode);
		String orgcountrycode = "机遇:"+orgcode.getTotalCount()+"<br/>客户："+orgcode.getCustomerCount()+"<br/>伙伴:"+orgcode.getPartnerCount()+"<br/>竞争:"+orgcode.getCompetitorCount();
				"countryCode:"+orgcode.getCountryCode()+",countryName:"+orgcode.getCountryName()+",totalCount:"+orgcode.getTotalCount()+",customerCount:"+orgcode.getCustomerCount()+",partnerCount:"+orgcode.getPartnerCount()+",competitorCount:"+orgcode.getCompetitorCount();
		//" "机遇:600514<br/>客户:16856<br/>伙伴:6045<br/>竞争:119")
		codeMap.put(countryCode, orgcountrycode);
		return codeMap;
	}*/

	@RequestMapping("/CallCommandOPSIIntoMongoDB")
	public static Boolean CallUpdateOpptLatLngIntoMongoDB( 
			@RequestParam(value="field", required=false) String field,
			@RequestParam(value="src", required=false) String src,
			@RequestParam(value="tgt", required=false) String tgt,
			@RequestParam(value="cmd", required=false) String cmd
			){
		Boolean ret = false;
		try{
			ret = MongoDBBasic.modifyOrgSiteInstance(field,src,tgt,cmd);
		}		
		catch(Exception e){
			ret = false;
		}
		return ret;
	}
	
	@RequestMapping("/QueryClientMeta")
	public static ClientMeta CallQueryClientMeta(){
		ClientMeta cm = new ClientMeta();
		try{
			cm = MongoDBBasic.QueryClientMeta();
		}		
		catch(Exception e){
			cm = null;
		}
		return cm;
	}
	@RequestMapping("/QueryClientMetaList")
	public static ArrayList<ClientMeta> QueryClientMetaList(){
		ArrayList<ClientMeta> cm = new ArrayList<ClientMeta>();
		try{
			cm = MongoDBBasic.QueryClientMetaList();
		}		
		catch(Exception e){
			cm = null;
		}
		return cm;
	}
	
	@RequestMapping("/CallUpdateUserWithSignature")
	public static boolean CallUpdateUserWithSignature(
			@RequestParam(value="openid", required=false) String openid,
			@RequestParam(value="svg", required=false) String svg
			){
		boolean ret = false;
		try{
			ret = MongoDBBasic.updateUserWithSignature(openid, svg);
		}		
		catch(Exception e){
			ret = false;
		}
		return ret;
	}
	
	@RequestMapping("/CallGetUserWithSignature")
	public static String CallGetUserWithSignature(@RequestParam(value="openid", required=false) String openid){
		String ret = "{";
		try{
			ret = ret + MongoDBBasic.getUserWithSignature(openid);
			ret =  ret + "}";
		}		
		catch(Exception e){
			ret = e.getMessage();
		}
		return ret;
	}
	
	@RequestMapping("/CallGetUserWithESignature")
	public static String CallGetUserWithESignature(@RequestParam(value="openid", required=false) String openid){
		String ret = "";
		try{
			ret = MongoDBBasic.getUserWithSignature(openid);

		}		
		catch(Exception e){
			ret = e.getMessage();
		}
		return ret;
	}
	
	@RequestMapping("/CallGetUserWithFaceUrl")
	public static String CallGetUserWithFaceUrl(@RequestParam(value="openid", required=false) String openid){
		String ret = "";
		try{
			ret = MongoDBBasic.getUserWithFaceUrl(openid);

		}		
		catch(Exception e){
			ret = e.getMessage();
		}
		return ret;
	}
	
	/*
	 * chang-zheng
	 * FOR billOfSell
	 */
	@RequestMapping("/saveBill")
	public static String saveBill(@RequestParam(value="businessType", required=false) String businessType,
			@RequestParam(value="sellType", required=false) String sellType,
			@RequestParam(value="orderNumber", required=false) String orderNumber,
			@RequestParam(value="orderTime", required=false) String orderTime,
			@RequestParam(value="customerName", required=false) String customerName,
			@RequestParam(value="currency", required=false) String currency,
			@RequestParam(value="exchange", required=false) String exchange,
			@RequestParam(value="salesDepartments", required=false) String salesDepartments,
			@RequestParam(value="salesman", required=false) String salesman,
			@RequestParam(value="inventoryCoding", required=false) String inventoryCoding,
			
			@RequestParam(value="inventoryName", required=false) String inventoryName,
			@RequestParam(value="specificationsModels", required=false) String specificationsModels,
			@RequestParam(value="unit", required=false) String unit,
			@RequestParam(value="amount", required=false) String amount,
			@RequestParam(value="unitPriceIncludTax", required=false) String unitPriceIncludTax,
			@RequestParam(value="priceExcludingTax", required=false) String priceExcludingTax,
			@RequestParam(value="noTaxAmount", required=false) String noTaxAmount,
			@RequestParam(value="tax", required=false) String tax,
			@RequestParam(value="totalPriceWithTax", required=false) String totalPriceWithTax,
			@RequestParam(value="taxRateString", required=false) String taxRateString,
			
			@RequestParam(value="deductible", required=false) String deductible,
			@RequestParam(value="deductible2", required=false) String deductible2,
			@RequestParam(value="advanceShipmentDate", required=false) String advanceShipmentDate,
			@RequestParam(value="ordersForChildTableID", required=false) String ordersForChildTableID,
			@RequestParam(value="unfilledOrderCount", required=false) String unfilledOrderCount,
			@RequestParam(value="noInvoiceCount", required=false) String noInvoiceCount,
			@RequestParam(value="reservedNum", required=false) String reservedNum,
			@RequestParam(value="notDeliverNum", required=false) String notDeliverNum,
			@RequestParam(value="notDeliverAmount", required=false) String notDeliverAmount,
			@RequestParam(value="noInvoiceCounts", required=false) String noInvoiceCounts,
			
			@RequestParam(value="noInvoiceAmount", required=false) String noInvoiceAmount,
			@RequestParam(value="amountPurchased", required=false) String amountPurchased,
			@RequestParam(value="noamountPurchased", required=false) String noamountPurchased,
			@RequestParam(value="noProduction", required=false) String noProduction,
			@RequestParam(value="noOutsourcing", required=false) String noOutsourcing,
			@RequestParam(value="noImportVolume", required=false) String noImportVolume){
		
		BillOfSell bos = new BillOfSell();
		bos.setBusinessType(businessType);
		bos.setSellType(sellType);
		bos.setOrderNumber(orderNumber);
		bos.setOrderTime(orderTime);
		bos.setCustomerName(customerName);
		bos.setCurrency(currency);
		bos.setExchange(exchange);
		bos.setSalesDepartments(salesDepartments);
		bos.setSalesman(salesman);
		bos.setInventoryCoding(inventoryCoding);
		
		bos.setInventoryName(inventoryName);
		bos.setSpecificationsModels(specificationsModels);
		bos.setUnit(unit);
		bos.setAmount(amount);
		bos.setUnitPriceIncludTax(unitPriceIncludTax);
		bos.setPriceExcludingTax(priceExcludingTax);
		bos.setNoTaxAmount(noTaxAmount);
		bos.setTax(tax);
		bos.setTotalPriceWithTax(totalPriceWithTax);
		bos.setTaxRateString(taxRateString);
		
		bos.setDeductible(deductible);
		bos.setDeductible2(deductible2);
		bos.setAdvanceShipmentDate(advanceShipmentDate);
		bos.setOrdersForChildTableID(ordersForChildTableID);
		bos.setUnfilledOrderCount(unfilledOrderCount);
		bos.setNoInvoiceCount(noInvoiceCount);
		bos.setReservedNum(reservedNum);
		bos.setNotDeliverNum(notDeliverNum);
		bos.setNotDeliverAmount(notDeliverAmount);
		bos.setNoInvoiceCounts(noInvoiceCounts);
		
		bos.setNoInvoiceAmount(noInvoiceAmount);
		bos.setAmountPurchased(amountPurchased);
		bos.setNoamountPurchased(noamountPurchased);
		bos.setNoProduction(noProduction);
		bos.setNoOutsourcing(noOutsourcing);
		bos.setNoImportVolume(noImportVolume);
		
		String ret="";
		ret=MongoDBBasic.saveBillOfSell(bos);
		return ret;
	}
	
	/*
	 * chang-zheng
	 * FOR OnlineQuotation
	 */
	@RequestMapping("/saveQuotation")
	public static String saveQuotation(@RequestParam(value="category", required=false) String category,
			@RequestParam(value="categoryGrade", required=false) String categoryGrade,
			@RequestParam(value="item", required=false) String item,
			@RequestParam(value="quotationPrice", required=false) String quotationPrice,
			@RequestParam(value="comments", required=false) String comments,
			@RequestParam(value="locationAmounts", required=false) String locationAmounts,
			@RequestParam(value="avaliableInventory", required=false) String avaliableInventory,
			@RequestParam(value="onDelivery", required=false) String onDelivery,
			@RequestParam(value="soldOutOfPay", required=false) String soldOutOfPay,
			@RequestParam(value="originalProducer", required=false) String originalProducer
			){
		java.sql.Timestamp cursqlTS = new java.sql.Timestamp(new java.util.Date().getTime()); 
		
		OnlineQuotation quotation = new OnlineQuotation();
		quotation.setCategory(category);
		quotation.setCategoryGrade(categoryGrade);
		quotation.setItem(item);
		quotation.setComments(comments);
		quotation.setQuotationPrice(quotationPrice);
		quotation.setLocationAmounts(locationAmounts);
		quotation.setAvaliableInventory(avaliableInventory);
		quotation.setOnDelivery(onDelivery);
		quotation.setSoldOutOfPay(soldOutOfPay);
		quotation.setOriginalProducer(originalProducer);
		quotation.setLastUpdate(cursqlTS.toString());
		
		String ret = MongoDBBasic.saveOnlineQuotation(quotation);
		return ret;
		
	}
	
	/*
	 * chang-zheng
	 * FOR OnlineQuotation
	 */
	/*@RequestMapping("/saveLocation")
	public static String saveLocation(@RequestParam(value="item", required=false) String item,
			@RequestParam(value="chongQing", required=false) String chongQing,
			@RequestParam(value="chengDu", required=false) String chengDu
			){
		Location location = new Location();
		location.setChengDu(chengDu);
		location.setChongQing(chongQing);
		String ret="";
		ret=MongoDBBasic.saveLocation(item,location);
		
		return ret;
	}
		*/
	
	/*
	 * chang-zheng
	 * FOR Inventory
	 */
	@RequestMapping("/saveInventory")
	public static String saveInventory(@RequestParam(value="repositoryName", required=false) String repositoryName,
			@RequestParam(value="plasticItem", required=false) String plasticItem,
			@RequestParam(value="unit", required=false) String unit,
			@RequestParam(value="inventoryAmount", required=false) Double inventoryAmount,
			@RequestParam(value="waitDeliverAmount", required=false) Double waitDeliverAmount,
			@RequestParam(value="reserveDeliverAmount", required=false) Double reserveDeliverAmount,
			@RequestParam(value="availableAmount", required=false) Double availableAmount
			){
		
		Inventory it = new Inventory();
		it.setAvailableAmount(availableAmount);
		it.setInventoryAmount(inventoryAmount);
		it.setPlasticItem(plasticItem);
		it.setRepositoryName(repositoryName);
		it.setReserveDeliverAmount(reserveDeliverAmount);
		it.setUnit(unit);
		it.setWaitDeliverAmount(waitDeliverAmount);
		
		String ret = MongoDBBasic.saveInventory(it);
		return ret;
	}
	
	/*
	 * chang-zheng
	 * FOR saveOnDelivery
	 */
	@RequestMapping("/saveOnDelivery")
	public static String saveOnDelivery(@RequestParam(value="billID", required=false) String billID,
			@RequestParam(value="date", required=false) String date,
			@RequestParam(value="provider", required=false) String provider,
			@RequestParam(value="plasticItem", required=false) String plasticItem,
			@RequestParam(value="amount", required=false) Double amount,
			@RequestParam(value="originalPrice", required=false) Double originalPrice,
			@RequestParam(value="taxRate", required=false) Double taxRate,
			@RequestParam(value="billType", required=false) String billType,
			@RequestParam(value="notInInRepository", required=false) Double notInInRepository
			){
		
		OnDelivery onDelivery = new OnDelivery();
		onDelivery.setAmount(amount);
		onDelivery.setBillID(billID);
		onDelivery.setBillType(billType);
		onDelivery.setDate(date);
		onDelivery.setNotInInRepository(notInInRepository);
		onDelivery.setOriginalPrice(originalPrice);
		onDelivery.setPlasticItem(plasticItem);
		onDelivery.setProvider(provider);
		onDelivery.setTaxRate(taxRate);
		String ret = MongoDBBasic.saveOnDelivery(onDelivery);
		return ret;
	}
	
	/*
	 * chang-zheng
	 * FOR saveOrderNopay
	 */
	@RequestMapping("/saveOrderNopay")
	public static String saveOrderNopay(@RequestParam(value="customerName", required=false) String customerName,
			@RequestParam(value="salesman", required=false) String salesman,
			@RequestParam(value="billID", required=false) String billID,
			@RequestParam(value="billDate", required=false) String billDate,
			@RequestParam(value="plasticItem", required=false) String plasticItem,
			@RequestParam(value="unfilledOrderAmount", required=false) Double unfilledOrderAmount,
			@RequestParam(value="filledOrderAmount", required=false) Double filledOrderAmount,
			@RequestParam(value="noInvoiceAmount", required=false) Double noInvoiceAmount
			){
		
		OrderNopay orderNopay = new OrderNopay();
		orderNopay.setBillDate(billDate);
		orderNopay.setBillID(billID);
		orderNopay.setCustomerName(customerName);
		orderNopay.setNoInvoiceAmount(noInvoiceAmount);
		orderNopay.setPlasticItem(plasticItem);
		orderNopay.setSalesman(salesman);
		orderNopay.setUnfilledOrderAmount(unfilledOrderAmount);
		orderNopay.setUnfilledOrderAmount(filledOrderAmount);
		String ret = MongoDBBasic.saveOrderNopay(orderNopay);
		return ret;
	}
	
	/*
	 * chang-zheng
	 * FOR saveOrderNopay
	 */
	@RequestMapping("/updateQuotationList")
	public static String updateQuotationList(@RequestParam(value="plasticItem", required=false) String plasticItem,
			@RequestParam(value="status", required=false) String status,
			@RequestParam(value="approveBy", required=false) String approveBy,
			@RequestParam(value="editBy", required=false) String editBy,
			@RequestParam(value="dateTime", required=false) String dateTime,
			@RequestParam(value="suggestPrice", required=false) Double suggestPrice,
			@RequestParam(value="type", required=false) int type
			){
		
		QuotationList quotation = new QuotationList();
		quotation.setApproveBy(approveBy);
		quotation.setDateTime(dateTime);
		quotation.setEditBy(editBy);
		quotation.setPlasticItem(plasticItem);
		quotation.setStatus(status);
		quotation.setSuggestPrice(suggestPrice);
		quotation.setType(type);
		String ret = MongoDBBasic.UpdateQuotationList(quotation);
		return ret;
	}
	
	/*
	 * chang-zheng
	 * FOR delete
	 */
	@RequestMapping("/deleteDB")
	public static String deleteDB(@RequestParam(value="dbName", required=true) String dbName){
		String wr = MongoDBBasic.DeleteDB(dbName)+"";
		return wr;
	}
}
		
