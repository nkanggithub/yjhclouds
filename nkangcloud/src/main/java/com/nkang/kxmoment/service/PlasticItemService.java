package com.nkang.kxmoment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.nkang.kxmoment.baseobject.Inventory;
import com.nkang.kxmoment.baseobject.OnDelivery;
import com.nkang.kxmoment.baseobject.OrderNopay;
import com.nkang.kxmoment.baseobject.PlasticItem;
import com.nkang.kxmoment.util.MongoClient;

/**
 * Knowledge Meta Service Handle
 * @author cqdxk
 *
 */
public class PlasticItemService {
	private static Logger logger = Logger.getLogger(PlasticItemService.class);
	
	/**
	 * Save or Update(if exists id)
	 * @param kmVo
	 * @return
	 */
	public static String save(PlasticItem item) {
		String id = null;
		try {
			id = MongoClient.save(item);
		} catch (Exception e) {
			logger.error("save fail.", e);
		}
		return id;
	}
	
	/**
	 * Get PlasticItem list
	 * @return
	 */
	public static List<PlasticItem> findList(Integer page, Integer count) {
		List<PlasticItem> list = null;
		try {
			list = MongoClient.findList(page, count, PlasticItem.class);
			if(list == null){
				return null;
			}
			Map<String, PlasticItem> itemMap = new HashMap<String, PlasticItem>();
			// 编号列表
			for (PlasticItem plasticItem : list) {
				plasticItem.setInventorys(null);
				plasticItem.setOnDeliverys(null);
				plasticItem.setOrderNopays(null);
				plasticItem.setFollowers(null);
				plasticItem.setQuotations(null);
				String itemNo = plasticItem.getItemNo();
//				itemNoList.add(itemNo);
				itemMap.put(itemNo, plasticItem);
			}
			itemMap.keySet().toArray();
			// Inventory 库存情况统计
			sumInventory(itemMap);
			// 已售未出货情况统计
			sumOrderNopay(itemMap);
			// 在途情况统计
			sumOnDelivery(itemMap);
		} catch (Exception e) {
			logger.error("Find KM List fail.", e);
			list = null;
		}
		return list;
	}

	/**
	 * Inventory 库存情况统计
	 * @param itemMap
	 */
	private static void sumInventory(Map<String, PlasticItem> itemMap) {
		DBObject key = new BasicDBObject();
		key.put("plasticItem", 1);
		DBObject cond = new BasicDBObject();
		cond.put("plasticItem", new BasicDBObject("$in", itemMap.keySet().toArray()));
		DBObject initial = new BasicDBObject();
		initial.put("inventoryAmountSum", 0);
		initial.put("waitDeliverAmountSum", 0);
		initial.put("reserveDeliverAmountSum", 0);
		initial.put("availableAmountSum", 0);
		String reduce = "function( curr, result ) {"
				+ "  result.inventoryAmountSum += curr.inventoryAmount;"
				+ "  result.waitDeliverAmountSum += curr.waitDeliverAmount;"
				+ "  result.reserveDeliverAmountSum += curr.reserveDeliverAmount;"
				+ "  result.availableAmountSum += curr.availableAmount;"
				+ "}";
		// 分组统计
		BasicDBList resultList = (BasicDBList) MongoClient.groupBy(key, cond, initial, reduce, Inventory.class);
		resultList.size();
		for(int i=0; i<resultList.size(); i++){
			BasicDBObject dbObject = (BasicDBObject) resultList.get(i);
			String itemNo = dbObject.getString("plasticItem");
			PlasticItem item = itemMap.get(itemNo);
			if(item != null){
				Double sum = dbObject.getDouble("availableAmountSum");
				sum = !Double.isNaN(sum) ? sum : 0;
				item.setInventorysAvailableAmountSum(sum);
			}
		}
	}
	
	/**
	 * Inventory 已售未出货情况统计
	 * @param itemMap
	 */
	private static void sumOrderNopay(Map<String, PlasticItem> itemMap) {
		DBObject key = new BasicDBObject();
		key.put("plasticItem", 1);
		DBObject cond = new BasicDBObject();
		cond.put("plasticItem", new BasicDBObject("$in", itemMap.keySet().toArray()));
		DBObject initial = new BasicDBObject();
		initial.put("notInInRepositorySum", 0);
		String reduce = "function( curr, result ) {"
				+ "  result.notInInRepositorySum += curr.notInInRepository;"
				+ "}";
		// 分组统计
		BasicDBList resultList = (BasicDBList) MongoClient.groupBy(key, cond, initial, reduce, OrderNopay.class);
		resultList.size();
		for(int i=0; i<resultList.size(); i++){
			BasicDBObject dbObject = (BasicDBObject) resultList.get(i);
			String itemNo = dbObject.getString("plasticItem");
			PlasticItem item = itemMap.get(itemNo);
			if(item != null){
				Double sum = dbObject.getDouble("notInInRepositorySum");
				sum = !Double.isNaN(sum) ? sum : 0;
				item.setOrderNopaynoInvoiceAmountSum(sum);
			}
		}
	}
	
	/**
	 * Inventory 在途情况统计
	 * @param itemMap
	 */
	private static void sumOnDelivery(Map<String, PlasticItem> itemMap) {
		DBObject key = new BasicDBObject();
		key.put("plasticItem", 1);
		DBObject cond = new BasicDBObject();
		cond.put("plasticItem", new BasicDBObject("$in", itemMap.keySet().toArray()));
		DBObject initial = new BasicDBObject();
		initial.put("noInvoiceAmountSum", 0);
		String reduce = "function( curr, result ) {"
				+ "  result.noInvoiceAmountSum += curr.noInvoiceAmount;"
				+ "}";
		// 分组统计
		BasicDBList resultList = (BasicDBList) MongoClient.groupBy(key, cond, initial, reduce, OnDelivery.class);
		resultList.size();
		for(int i=0; i<resultList.size(); i++){
			BasicDBObject dbObject = (BasicDBObject) resultList.get(i);
			String itemNo = dbObject.getString("plasticItem");
			PlasticItem item = itemMap.get(itemNo);
			if(item != null){
				Double sum = dbObject.getDouble("noInvoiceAmountSum");
				sum = !Double.isNaN(sum) ? sum : 0;
				item.setOnDeliveryNotInInRepositorySum(sum);
			}
		}
	}

	/**
	 * get detail
	 * @param id
	 * @return
	 */
	public static PlasticItem getDetail(String id) {
		PlasticItem item = null;
		try {
			item = MongoClient.getOneById(id, PlasticItem.class);
		} catch (Exception e) {
			logger.error("Get Detail fail.", e);
			item = null;
		}
		return item;
	}
}
