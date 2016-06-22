package com.nkang.kxmoment.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.nkang.kxmoment.baseobject.Opportunity;
import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
import com.nkang.kxmoment.baseobject.OrganizationSearch4Solr;
import com.nkang.kxmoment.service.ProcurementOrgSite;
import com.nkang.kxmoment.service.ProcurementOrganizationSearch;
import com.nkang.kxmoment.service.ProcurementOrganizationServiceImpl;
import com.nkang.kxmoment.util.SolrUtils.SolrClientUtils;

public class ExtractionCronJob extends TimerTask{
	private static Logger log=Logger.getLogger(ExtractionCronJob.class);
	private int section;
	public ExtractionCronJob(int i) {
		// TODO Auto-generated constructor stub
		this.section = i;
	}
	
	public void run(){
		ArrayList opsiList  = new ArrayList<OrgOtherPartySiteInstance>();
		OrgOtherPartySiteInstance opsi = new OrgOtherPartySiteInstance();
		OrganizationSearch4Solr solrSearchParams = new OrganizationSearch4Solr();
		solrSearchParams.setCountryCode("CN");
		solrSearchParams.setCharScriptCode("HANS");
		solrSearchParams.setOrgId("12345");
		QueryResponse queryResponse = SolrClientUtils.queryOrgInformations(solrSearchParams, 1);
		
		SolrDocumentList docs = queryResponse.getResults();
		
		Iterator<SolrDocument> orgInfosIterator = docs.iterator();
		while (orgInfosIterator.hasNext()) {
			//opsi = null;
			SolrDocument resultDoc = (SolrDocument) orgInfosIterator.next();
			opsi.setWorldRegionPath(SolrClientUtils.getStringValue(resultDoc,"worldRegionPath"));
			opsi.setNonlatinStreet1LongName(SolrClientUtils.getStringValue(resultDoc,"nonlatinStreet1LongName"));
			opsiList.add(opsi);
			//System.out.println(opsiList.size()+"------" + opsi.getWorldRegionPath() + "----" + opsi.getNonlatinStreet1LongName());
		}
		
		
/*		Date date = new Date();  
		StopWatch sw = new StopWatch();
		String ds = String.format("%tF",date);  
		ProcurementOrganizationServiceImpl imp = new ProcurementOrganizationServiceImpl();
		ProcurementOrganizationSearch critiera = new ProcurementOrganizationSearch();
		critiera.setCountryCode("CN");
		critiera.setCharScriptCode("HANS");
		//int block = 1; //259
	
		List<ProcurementOrgSite> listOrgSite = imp.quickSearchProcurementOrganizationByOrgId(critiera, section);*/	
		StopWatch sw = new StopWatch();
		for(int section = 0; section < 1 ; section ++){
			//List<ProcurementOrgSite> listOrgSite = imp.quickSearchProcurementOrganizationByOrgId(critiera, section);
			String FileName = "MDMExtract_" + section + "_" + section*50000 + "-"+ (section*50000+50000) + ".properties";
			try {
				CsvFileWriter.writeCsvFile(FileName, opsiList);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("MDM Extraction Section " + section + " Completed in total Takes: " + sw);
		}
	}

}
