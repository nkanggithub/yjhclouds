package com.nkang.kxmoment.service;

import java.util.ArrayList;
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
	 * 修改价格信息
	 * @param itemNo 编号
	 * @param price 价格
	 * @param opType 操作价格类型 1:edit	2：approve
	 * @return 更新成功返回true， 否则false
	 */
	public static boolean updatePriceInfo(String itemNo, Float price, Integer opType) {
		boolean flag = false;
		if(itemNo==null || opType==null){
			return flag;
		}
		PlasticItem item = getDetailByNo(itemNo);
		if(item==null){
			return flag;
		}
		if(price != null){
			float prePrice = item.getPrice();
			if(prePrice >= 0){
				// 计算差价
				float diffPrice = price - prePrice;
				item.setDiffPrice(diffPrice);
			}
			// 更新价格
			item.setPrice(price);	
		}
		// 价格状态，0:initial; 1:edit; 2:approve
		Integer priceStatus;
		switch (opType) {
		case 1:
			priceStatus = 1;
			break;
		case 2:
			priceStatus = 2;
			break;
		default:
			priceStatus = 0;
			break;
		}
		item.setPriceStatus(priceStatus);
		// 保存
		MongoClient.save(item);
		flag = true;
		return flag;
	}
	
	/**
	 * 判断并插入, 如果不存在就插入对象
	 * @return 插入返回true， 否则false， 参数不对返回null 
	 */
	public static Boolean judgeAndInsert(Inventory inventory) {
		if(inventory==null || inventory.getPlasticItem()==null){
			return null;
		}
		PlasticItem item = getDetailByNo(inventory.getPlasticItem());
		if(item != null){
			return false;
		}
		item = new PlasticItem();
		item.setItemNo(inventory.getPlasticItem());
		item.setPriceStatus(0);
		// 保存
		MongoClient.save(item);
		return true;
	}
	
	/**
	 * 判断并插入, 如果不存在就插入对象
	 * @return 插入返回true， 否则false， 参数不对返回null 
	 */
	public static Boolean judgeAndInsert(OnDelivery onDelivery) {
		if(onDelivery==null || onDelivery.getPlasticItem()==null){
			return null;
		}
		PlasticItem item = getDetailByNo(onDelivery.getPlasticItem());
		if(item != null){
			return false;
		}
		item = new PlasticItem();
		item.setItemNo(onDelivery.getPlasticItem());
		item.setPriceStatus(0);
		// 保存
		MongoClient.save(item);
		return true;
	}
	
	/**
	 * 判断并插入, 如果不存在就插入对象
	 * @return 插入返回true， 否则false， 参数不对返回null 
	 */
	public static Boolean judgeAndInsert(OrderNopay orderNopay) {
		if(orderNopay==null || orderNopay.getPlasticItem()==null){
			return null;
		}
		PlasticItem item = getDetailByNo(orderNopay.getPlasticItem());
		if(item != null){
			return false;
		}
		item = new PlasticItem();
		item.setItemNo(orderNopay.getPlasticItem());
		item.setPriceStatus(0);
		// 保存
		MongoClient.save(item);
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> priceList(String itemNo) {
		Map<String, Object> dataSource = null;
		if(itemNo == null || itemNo.length() == 0){
			return null;
		}
		dataSource = new HashMap<String, Object>();
		// chart initial
		Map<String, Object> chart = new HashMap<String, Object>();
		chart.put("theme", "fint");
		chart.put("xaxisname", itemNo);
		chart.put("yaxisname", "");
		chart.put("numberSuffix","");
		int subIndex = itemNo.lastIndexOf("-");
		// 设置主副标题
		String caption = itemNo.substring(0, subIndex>0?subIndex:itemNo.length());
		String subcaption = itemNo.substring(subIndex>0?subIndex:0, itemNo.length());
		chart.put("caption", caption);
		chart.put("subcaption", "("+ subcaption +")");
		chart.put("showvalues", "0");
		chart.put("plottooltext", "$seriesname); $value");
		//Error bar configuration
		chart.put("halferrorbar", "0");
		chart.put("errorBarColor", "#990000");
		chart.put("errorBarAlpha", "50");
		chart.put("errorBarThickness", "4");
		chart.put("errorBarWidth", "8");
		dataSource.put("chart", chart);
		// categories
		Map[] categories = new Map[1];
		Map<String, Object> category = new HashMap<String, Object>(); 
		categories[0] = category;
		List<Map<String, Object>> categoryList = new ArrayList<Map<String,Object>>();
		Map<String, Object> ctgMap = new HashMap<String, Object>();
		ctgMap.put("label", "Jan");
		categoryList.add(ctgMap);
		ctgMap = new HashMap<String, Object>();
		ctgMap.put("label", "Feb");
		categoryList.add(ctgMap);
		ctgMap = new HashMap<String, Object>();
		ctgMap.put("label", "Mar");
		categoryList.add(ctgMap);
		ctgMap = new HashMap<String, Object>();
		ctgMap.put("label", "Apl");
		categoryList.add(ctgMap);
		ctgMap = new HashMap<String, Object>();
		ctgMap.put("label", "May");
		categoryList.add(ctgMap);
		ctgMap = new HashMap<String, Object>();
		ctgMap.put("label", "Jun");
		categoryList.add(ctgMap);
		category.put("category", categoryList);
		dataSource.put("categories", categories);
		// dataset
		List<Map> dataset = new ArrayList<Map>();
		Map<String, Object> dataMap;
		List<Map> dataList;
		// 定价
		dataMap = new HashMap<String, Object>();
		dataMap.put("seriesname", "定价");
		dataList =  new ArrayList<Map>();
		for(int i=0; i<11; i++){
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("value", 16700+i*100);
			data.put("errorvalue", "");
			dataList.add(data);
		}
		dataMap.put("data", dataList);
		dataset.add(dataMap);
		// 
		dataMap = new HashMap<String, Object>();
		dataMap.put("seriesname", "发布价");
		dataList =  new ArrayList<Map>();
		for(int i=0; i<11; i++){
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("value", 15700+i*100);
			data.put("errorvalue", "");
			dataList.add(data);
		}
		dataMap.put("data", dataList);
		dataset.add(dataMap);
		dataSource.put("dataset", dataset);
		return dataSource;
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
	
	/**
	 * 根据编号获取对象
	 * @param itemNo
	 * @return
	 */
	public static PlasticItem getDetailByNo(String itemNo) {
		PlasticItem item = null;
		if(itemNo==null || itemNo.length()==0){
			return item;
		}
		try {
			DBObject query = new BasicDBObject();
			query.put("itemNo", itemNo);
			item = MongoClient.getOne(query, PlasticItem.class);
		} catch (Exception e) {
			logger.error("Get Detail fail.", e);
			item = null;
		}
		return item;
	}
}
