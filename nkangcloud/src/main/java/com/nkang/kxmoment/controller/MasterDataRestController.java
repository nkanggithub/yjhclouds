package com.nkang.kxmoment.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import com.nkang.kxmoment.baseobject.ClientInformation;
import com.nkang.kxmoment.baseobject.GeoLocation;
import com.nkang.kxmoment.baseobject.MdmDataQualityView;
import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
import com.nkang.kxmoment.baseobject.Teamer;
import com.nkang.kxmoment.baseobject.WeChatMDLUser;
import com.nkang.kxmoment.baseobject.WeChatUser;
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
	
	@RequestMapping("/CallRegisterUser")
	public static boolean CallRegisterUser(
			@RequestParam(value="openid", required=false) String openid,
			@RequestParam(value="suppovisor", required=false) String suppovisor,
			@RequestParam(value="registerDate", required=false) String registerDate,
			@RequestParam(value="role", required=false) String role,
			@RequestParam(value="selfIntro", required=false) String selfIntro,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="phone", required=false) String phone,
			@RequestParam(value="group", required=false) String groupid,
			@RequestParam(value="name", required=false) String name
			){
		boolean ret = false;
		Teamer teamer = new Teamer();
		teamer.setOpenid(openid);
		teamer.setSelfIntro(selfIntro);
		teamer.setSuppovisor(suppovisor);
		teamer.setRegisterDate(registerDate);
		teamer.setRole(role);
		teamer.setEmail(email);
		teamer.setPhone(phone);
		teamer.setGroupid(groupid);
		teamer.setRealName(name);
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
	 */
	@RequestMapping("/getCountrycount")
	public @ResponseBody List<Object[]> getCountrycount(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "country") String country)
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
}