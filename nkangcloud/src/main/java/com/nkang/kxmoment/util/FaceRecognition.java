package com.nkang.kxmoment.util;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class FaceRecognition {

 public void goface() 
 {
     HttpClient httpclient = HttpClients.createDefault();

     try
     {
         URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/face/v1.0/detect");

         builder.setParameter("returnFaceId", "true");
         builder.setParameter("returnFaceLandmarks", "false");
         builder.setParameter("returnFaceAttributes", "age,gender,headPose,smile,facialHair,glasses");

         URI uri = builder.build();
         HttpPost request = new HttpPost(uri);
         request.setHeader("Content-Type", "application/json");
         request.setHeader("Ocp-Apim-Subscription-Key", "850c8dc0fb914d6791f15d4a3e3ada99");


         // Request body
         StringEntity reqEntity = new StringEntity("{body}");
         request.setEntity(reqEntity);

         HttpResponse response = httpclient.execute(request);
         HttpEntity entity = response.getEntity();

         if (entity != null) 
         {
             System.out.println(EntityUtils.toString(entity));
         }
     }
     catch (Exception e)
     {
         System.out.println(e.getMessage());
     }
 }
}

