package com.nkang.kxmoment.service;

import java.util.List;

import com.nkang.kxmoment.service.ProcurementOrgSite;
import com.nkang.kxmoment.service.ProcurementOrganizationSearch;
import com.nkang.kxmoment.service.Organization;




public interface ProcurementOrganizationService {
	Organization callProcurementOrganizationByBrId(String brId) throws Exception;
	
	List<ProcurementOrgSite> quickSearchProcurementOrganizationByOrgId(String orgId) ;
	
	List<ProcurementOrgSite> quickSearchProcurementOrganizationByOrgId(ProcurementOrganizationSearch orgCritera, int section) ;
	
	
	
	
}