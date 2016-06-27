package com.nkang.test;


import java.net.URLEncoder;
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
		MongoDBBasic mongoDBBasic =new MongoDBBasic();
		mongoDBBasic.mongoDBInsert(null);
/*		List<OrgOtherPartySiteInstance> opsiList = new ArrayList<OrgOtherPartySiteInstance>();
		opsiList = SolrClientUtils.getOPSIList(0);
		for (OrgOtherPartySiteInstance opsi : opsiList) {
			RestUtils.callMasterDataUpsert(opsi);
		}*/
	}

}
