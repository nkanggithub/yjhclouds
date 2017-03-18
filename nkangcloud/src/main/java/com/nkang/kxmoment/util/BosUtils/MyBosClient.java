package com.nkang.kxmoment.util.BosUtils;

import java.util.ArrayList;
import java.util.List;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BucketSummary;
import com.baidubce.services.bos.model.CannedAccessControlList;
import com.baidubce.services.bos.model.Grant;
import com.baidubce.services.bos.model.Grantee;
import com.nkang.kxmoment.util.Constants;


public class MyBosClient {
	public static BosClient initialBosClient(){
		BosClientConfiguration config = new BosClientConfiguration();
		config.setCredentials(new DefaultBceCredentials("134da44f67a64cd4997d9d87361384ee", "5174599e7daf41f293319531d45925f5"));
		config.setMaxConnections(10);
		config.setConnectionTimeoutInMillis(5000);
		config.setSocketTimeoutInMillis(2000);
/*		config.setProxyHost("http.proxyHost"+Constants.proxyInfo);
		config.setProxyPort(8080);*/
		BosClient client = new BosClient();
		return client;
	}
	
	public static void createBucket (BosClient client, String bucketName) {
	    client.createBucket(bucketName);
	}
	
	public List<BucketSummary> listBuckets (BosClient client) {
	    List<BucketSummary> buckets = client.listBuckets().getBuckets();
	    for (BucketSummary bucket : buckets) {
	        System.out.println(bucket.getName());
	    }
	    return buckets;
	}
	
	public boolean doesBucketExist (BosClient client, String bucketName) {
	    boolean exists = client.doesBucketExist(bucketName);
	    return exists;
	}
	
	public void deleteBucket (BosClient client, String bucketName) {
	    client.deleteBucket(bucketName);
	}
	
	public void setBucketPrivate (BosClient client, String bucketName) {
	    client.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
	}

}
