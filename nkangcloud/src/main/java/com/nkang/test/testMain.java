package com.nkang.test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.nkang.kxmoment.baseobject.MdmDataQualityView;
import com.nkang.kxmoment.baseobject.OrgOtherPartySiteInstance;
import com.nkang.kxmoment.baseobject.OrganizationSearch4Solr;
import com.nkang.kxmoment.baseobject.WeChatUser;
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
		read("C://MDM_10//Nkang WorkSpace//nkangcloudFY16//src//main//resources//clientLoading.csv");

		//loading data from mdm to baidu cloud
		//RestUtils.getUserCityInfoWithLatLng("29.605253","106.361580");
		
		//opsi =  RestUtils.CallGetOPSIWithOutLatLng();
		
		//System.out.println(RestUtils.getlatLngwithQuery(opsi.getSiteName(), opsi.getCityRegion()));
		
/*	StopWatch sw = new StopWatch();
		sw.start();
		System.out.println("starting data load--please wait");
		List<OrgOtherPartySiteInstance> opsiList = new ArrayList<OrgOtherPartySiteInstance>();
		opsiList = SolrClientUtils.getOPSIList(9);  // 1 of 33
		for (OrgOtherPartySiteInstance opsi : opsiList) {
			RestUtils.callMasterDataUpsert(opsi);
		}
		sw.getElapsedMs();
		System.out.println("over all loading time : " + sw);*/

	}
	
	
	public static String read(String readPath) {
		String readTxt = "";
		try {
			File f = new File(readPath);
			if (f.isFile() && f.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(f), "UTF-8");
				BufferedReader reader = new BufferedReader(read);
				String line;
				Map<String, String[]> paramsMap = new HashMap<String, String[]>();
				
				while ((line = reader.readLine()) != null) {
					String [] sp = line.split("\\|");
					if(sp[0].equalsIgnoreCase("hpit:w-mdcp-mdm-prd")){
						continue;
					}
					String[] params = paramsMap.get(sp[0]);
					if(params == null){
						params = new String[4];
						params[0] = sp[0];
						params[1] = sp[2];
						params[2] = sp[3];
						params[3] = sp[1];
						paramsMap.put(sp[0], params);
						continue;
					}
					String webServiceStr = params[3];
					if(webServiceStr.indexOf(sp[1]) <= -1){
						webServiceStr += "," + sp[1];
						params[3] = webServiceStr;
					}
					/*String [] sp = line.split("\\|");
					if(sp.length == 4){
						System.out.println(RestUtils.CallLoadClientIntoMongoDB(sp[0],sp[1],sp[2],sp[3]));
					}*/
				}
				Collection<String[]> paramsCol = paramsMap.values();
				Iterator<String[]> paramsIt = paramsCol.iterator();
				while(paramsIt.hasNext()){
					String[] params = paramsIt.next();
					if(params[3].length() > 600){
						continue;
					}
					RestUtils.CallLoadClientIntoMongoDB(params[0],params[1],params[2],params[3]);
					System.out.println(params[0] +  " --- " + params[3]);
				}
				read.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return readTxt;
	}

}
