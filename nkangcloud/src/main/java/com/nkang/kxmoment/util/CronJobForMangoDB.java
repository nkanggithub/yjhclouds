package com.nkang.kxmoment.util;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.nkang.kxmoment.baseobject.ExtendedOpportunity;
import com.nkang.kxmoment.baseobject.Opportunity;

public class CronJobForMangoDB extends TimerTask {
    private static Logger log = Logger.getLogger(CronJob.class);

    @Override
    public void run() {
	StopWatch sw = new StopWatch();
	sw.start();
	log.info(" Batch Process Fetch lng&lat From Mysql to MangoDB. Start time : "
		+ sw.toString());

	// TODO: get record from mongodb,and update lng&lat with the same OPSI
	// record in mangodb
	try {
	    // 1. do get oppts from mongodb where lng&lat is null
	    DBCursor opptsCursor = MongoDBBasic.getOpptListFromMongoDB(2);
	    log.info("opptsCursor Size: " + opptsCursor.size());
	    String status;
	    int i = 0;

		while (opptsCursor.hasNext()) {
		    DBObject opptObject = opptsCursor.next();
		    
		    status = RestUtils.getAndSetLocationDetailsforOpptObject(opptObject);
		    log.info("CronJobForMangoDB process the "+i+"th record is done! ");
		    if (status == "302") {
			log.info("BaiDu API Daily Limited: 302");
			log.info("BaiDu API Daily Limited: 302");
			break;
		    }
		}
	   

	    sw.getElapsedMs();
	    java.sql.Timestamp cursqlTS = new java.sql.Timestamp(
		    new java.util.Date().getTime());
	    log.info(cursqlTS + "Batch Process input in total Takes: " + sw);

	} catch (Exception e) {
	    log.info("Update Failed! Exception description :" + e.getMessage());
	    
	}

	sw.getElapsedMs();
	log.info(" Batch Process Fetch lng&lat From Mysql to MangoDB. End time : "
		+ sw);

	java.sql.Timestamp cursqlTS = new java.sql.Timestamp(
		new java.util.Date().getTime());

	log.info(cursqlTS + "Batch Process UPDATE mongoDB in total Takes: "
		+ sw);
    }

}
