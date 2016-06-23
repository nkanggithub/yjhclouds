package com.nkang.kxmoment.controller;

/*import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;*/

import org.springframework.web.bind.annotation.*;

import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
import com.nkang.kxmoment.util.MongoDBBasic;


@RestController
public class MasterDataRestController {
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
								@RequestParam(value="returnPartnerFlag", required=false) String returnPartnerFlag)
	{
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
			

			MongoDBBasic.mongoDBInsert(opsi);
		}
		catch(Exception e){
			return "failed";
		}

		return "success";
	}
}