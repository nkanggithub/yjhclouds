package com.nkang.kxmoment.service;

import org.apache.log4j.Logger;

/**
 * User: $Author$
 * Id: $Id$
 */
public class Constants {
	private static final String svnInfo = "$Id$";
	
	private static Logger log = Logger.getLogger(Constants.class);

	static {
        log.info(" SVN KEYWORDS :�� " + svnInfo);
	}
	
    public static final String SIPERIAN_FIELD_ROWID_OBJECT = "ROWID_OBJECT";

    public static final String SIPERIAN_FILTER_VIA_ROWID = "ROWID_OBJECT = ?";
    public static final String FUTURE_DT = "10/31/2099";

    public static final String APP_PROPERTY_SIPCLIENT_PROPERTIES_FILENAME = "siperian-client.properties";
    public static final String APP_PROPERTY_SIPCLIENT_POOL_PROPERTIES_FILENAME = "pool.properties";

    public static final String JSONVIEW = "jsonView";
    
    public static final String EXTENDED_PROTILE_ITEM_ASSIGNMENT_URL = "extendedprofileItemAssignment.url";
    public static final String ORGANIZATION_PARTNER_URL = "organizationpartner.url";
    public static final String REACTIVATE_BUSINESS_RELATIONSHIP_URL = "reactivate.business.relationship.url";
    public static final String INACTIVATE_BUSINESS_RELATIONSHIP_URL = "inactivate.business.relationship.url";
    public static final String REACTIVATE_BUSINESS_RELATIONSHIP_ITEM_URL = "reactivate.business.relationship.item.url";
    public static final String INACTIVATE_BUSINESS_RELATIONSHIP_ITEM_URL = "inactivate.business.relationship.item.url";
    public static final String MANAGE_CUSTOMER_PARTNER_FLAG_URL = "manage.customer.partner.flag.url";
    public static final String ORGANZIATION_NATIONAL_IDENTIFICATION_URL = "orgnizationnatinalidentification.url";
    public static final String ORGANIZATION_TARGET_SEGMENT_ASSIGNMENT = "organizationtargetsegmentassignment.url";
    public static final String ORGANIZATION_SALES_COVERAGE_SEGMENT_ASSIGNMENT="OrganizationSalesCoverageSegmentAssignment.url";
    public static final String OTHER_PARTY_DYNAMIC_PROFILE_ALL_ASSIGNMENT = "otherpartydynamicprofileallassignment.url";
    public static final String OTHER_PARTY_DYNAMIC_PROFILE_CUSTOMER_ASSIGNMENT = "otherpartydynamicprofilecustomerassignment.url";
    public static final String OTHER_PARTY_SITE_HEAD_COUNT = "otherpartysiteheadcount.url";
    
    public static final String ORGANIZATION_LUCENE_SEARCH_URL = "organizationlucenesearch.url";
    public static final String ADDRESS_COUNTRY_LANGUAGE_LUCENE_SEARCH_URL = "addresscountrylanguagelucenesearch.url";
   
    public static final String ORGANIZATION_ACCOUNT_LUCENE_SEARCH_URL = "organizationaccountlucenesearch.url";
    
    public static final String SALES_TERRITORY_LUCENE_SEARCH_URL = "salesterritorylucenesearch.url";
    
    public static final String SALES_ORGANIZATION_LUCENE_SEARCH_URL = "salesorganizationlucenesearch.url";

    public static final String ORGANIZATION_SALES_ENGAGEMENT_URL = "organizationsalesengagement.url";
    public static final String OTHER_PARTY_SITE_SALES_VOLUME_URL = "otherpartysitesalesvolume.url";
    public static final String ORGANIZATION_NAME_URL = "organizationname.url";
    public static final String PROCESS_USAGE = "processusage.url";
    public static final String PROCESS_USAGE_INACTIVE = "processusageinactive.url";
    public static final String RAD_ASSIGNMENT = "radassignment.url";

    public static final String GTS_DETAILS_PROVIDER = "gtsdetailsprovider.url";
	public static final String MANAGE_RPL_SCREENING = "managerplscreening.url";
	public static final String SALES_CORE_PROFILE = "setupsalesenabledustomer.url";
	
	public static final String APP_PROPERTY_WEBSERVICE_INFO = "webservice-info.properties";
	public static final String WEBSERVICE_INFO_ENVIRONMENT_HOST_URL = "environment_host.url";
	public static final String SECURITY_CERTIFICATIONPATH = "certificationPath";
	public static final String SECURITY_USERNAME = "wsUsername.url";
	public static final String SECURITY_PASSWORD = "wsPassword.url";

    public static final String CUSTOMER_BATCH_URL = "customerbatch.url";
    
    public static final String BATCH_UPLOAD_URL = "batchUpload.url";
    
    public static final String SALESTERRITORY_RULE = "SalesTerritoryRule.url";
    
    public static final String HTTP_HOST_URL = "http_host.url";
    
    public static final String PROCUREMENT_SEARCH_URL = "procurement.url";
    
    public static final String SALESTERRITORY_ATTRIBUTES_UPDATE = "salesTerritoryAttributeUpdate.url";
    
    public static final String SALESORGANIZATION_ATTRIBUTES_UPDATE = "salesOrganizationAttributeUpdate.url";

    public static final String BUSINESS_RELATIONSHIP_OPERATIONAL_APPOINTED_WORLD_REGION = "business.relationship.operational.appointed.world.region.url";
    
    public static final String BA_GROUP_STATUS_SPLITER = "=";

    public static final String BA_GROUP_STATUS_NOT_MATCH = "NOT_MATCH";
    
    public static final String WEBSERVICE_INFO_ENVIRONMENT_HOST_URL_MDAS = "environment_host.url_mdas";
	
    public static final String MDAS_ALIGNMENT_URL = "mdasAlignment.url";
}