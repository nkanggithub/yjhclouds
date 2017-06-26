package com.nkang.test;



import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.nkang.kxmoment.util.Constants;
import com.nkang.kxmoment.util.BosUtils.MyBosClient;



public class testMain{

	public static void mains(String[] args) throws Exception {

	    File file = new File("C:\\USVISANK.jpg");
	    // 获取数据流
	    InputStream inputStream = new FileInputStream("C:\\USVISANK.jpg");
	    String bk = MyBosClient.client.listBuckets().getBuckets().get(0).getName();
	    System.out.println(bk);
	    // 以文件形式上传Object
	    //PutObjectResponse putObjectFromFileResponse = client.putObject(bk, objectKey, file);
	    PutObjectResponse putObjectResponseFromInputStream = MyBosClient.client.putObject(bk, "myperspon.jpg", inputStream);
/*	    // 以数据流形式上传Object
	    PutObjectResponse putObjectResponseFromInputStream = client.putObject(bucketName, objectKey, inputStream);
	    // 以二进制串上传Object
	    PutObjectResponse putObjectResponseFromByte = client.putObject(bucketName, objectKey, byte1);
	    // 以字符串上传Object
	    PutObjectResponse putObjectResponseFromString = client.putObject(bucketName, objectKey, string1);*/

	    // 打印ETag
	    System.out.println(putObjectResponseFromInputStream.getETag());
		

	}
}

