package com.nkang.kxmoment.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.GroupCommand;
import com.nkang.kxmoment.baseobject.Inventory;
import com.nkang.kxmoment.baseobject.OnDelivery;
import com.nkang.kxmoment.baseobject.OrderNopay;
import com.nkang.kxmoment.baseobject.PlasticItem;
import com.nkang.kxmoment.baseobject.QuotationList;
import com.nkang.kxmoment.util.DateUtil;
import com.nkang.kxmoment.util.MongoClient;

/**
 * Knowledge Meta Service Handle
 * @author cqdxk
 *
 */
public class PlasticItemService {
	private static Logger logger = Logger.getLogger(PlasticItemService.class);
	private static DecimalFormat fnum = new DecimalFormat("#####0.0000");

	private static int oneDayTimestamp = 1000*60*60*24; //一天的时间戳
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
			if(prePrice > 0){
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
		item.setUpdateAt(new Date());
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
		item.setCreateAt(new Date());
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> priceList(String itemNo, String type) {
		Map<String, Object> dataSource = new HashMap<String, Object>();
		if(itemNo == null || itemNo.length() == 0){
			return null;
		}
		// 分割多个itemNo
		String[] itemNoArr = itemNo.split(",");
		// 当前时间
		Calendar currDateCal = Calendar.getInstance();
		// 去除十分秒
		currDateCal.set(Calendar.HOUR_OF_DAY, 0);
		currDateCal.set(Calendar.MINUTE, 0);
		currDateCal.set(Calendar.SECOND, 0);
		currDateCal.set(Calendar.MILLISECOND, 0);
		// 开始日期
		Calendar beginDateCal = Calendar.getInstance();
		if(type != null && type.equalsIgnoreCase("M")){
			// 查看过去月变化
			beginDateCal.add(Calendar.DATE, -29);
		}else{
			// 查看过去周变化
			beginDateCal.add(Calendar.DATE, -6);
		}
		// 去除十分秒
		beginDateCal.set(Calendar.HOUR_OF_DAY, 0);
		beginDateCal.set(Calendar.MINUTE, 0);
		beginDateCal.set(Calendar.SECOND, 0);
		beginDateCal.set(Calendar.MILLISECOND, 0);
		Date beginTime = beginDateCal.getTime();
		Date endTime = null;
		// 统计价格趋势
		List<Object[]> resultList = statPriceTrend(itemNoArr, beginTime, endTime);
		if(resultList == null || resultList.size() == 0){
			return dataSource;
		}
		// chart initial
		Map<String, Object> chart = new HashMap<String, Object>();
		chart.put("theme", "fint");
		chart.put("yaxisname", "");
		chart.put("numberSuffix","");
		int subIndex = itemNo.indexOf("-");
		// 设置主副标题
		String caption = "价格变化";
		String subcaption = "";
		if(itemNoArr.length==1){
			chart.put("xaxisname", itemNo);
			caption = itemNo.substring(0, subIndex>0?subIndex:itemNo.length()) + "价格变化";
			subcaption = "("+ itemNo.substring(subIndex>0?subIndex+1:0, itemNo.length()) +")";
		}
		chart.put("caption", caption);
		chart.put("subcaption", subcaption);
		chart.put("showvalues", "0");
		chart.put("plottooltext", "$seriesname, $value");
		//Error bar configuration
		chart.put("halferrorbar", "0");
		chart.put("errorBarColor", "#990000");
		chart.put("errorBarAlpha", "50");
		chart.put("errorBarThickness", "4");
		chart.put("errorBarWidth", "8");
		// categories
		Map[] categories = new Map[1];
		Map<String, Object> category = new HashMap<String, Object>(); 
		categories[0] = category;
		List<Map<String, Object>> categoryList = new ArrayList<Map<String, Object>>();
		category.put("category", categoryList);
		// dataset
		Map<String, Map> dataset = new HashMap<String, Map>();
		for(int i=0; i<resultList.size(); i++){
			Object[] rO = resultList.get(i);
			String day = (String)rO[0];
			// 当条数据时间
			Date currDay = DateUtil.str2Date(day, "yyyy-MM-dd");
			String itemNo2 = (String)rO[1];
			Double price = (Double)rO[2];
			Map<String, Object> dataMap = dataset.get(itemNo2);
			List<Map> dataList = null;
			if(dataMap == null){
				dataMap = new HashMap<String, Object>();
				dataMap.put("seriesname", itemNo2);
				dataList = new ArrayList<Map>();
				dataMap.put("data", dataList);
				dataset.put(itemNo2, dataMap);
			}else{
				dataList = (List<Map>)dataMap.get("data");
				if(dataList.size()>0){
					int lastIdx = dataList.size();
					// 上一条数据时间
					Map<String, Object> lastDateM = categoryList.get(lastIdx-1);
					String lastDayStr = (String)lastDateM.get("label");
					Date lastDay = DateUtil.str2Date(lastDayStr, "yyyy-MM-dd");
					// 如果数据间隔时间大于一天，则补充以前数据时间
					if(currDay.getTime() - lastDay.getTime() > oneDayTimestamp){
						// 填充上一数据前空白数据
						utilFillGapStatPriceData(categoryList, lastIdx, dataList, lastDay, currDay);
					}
				}
			}
			// 横轴标题(日期(天))
			Map<String, Object> ctgMap = new HashMap<String, Object>();
			ctgMap.put("label", day);
			categoryList.add(ctgMap);
			// 纵轴值（每日合计价格）
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("value", price);
			data.put("errorvalue", "");
			dataList.add(data);
			if(resultList.size()>1 && i >= resultList.size()-1
					&& currDateCal.getTimeInMillis() - currDay.getTime() > oneDayTimestamp){
				int lastIdx = dataList.size();
				// 填充当前时间前空白数据
				utilFillGapStatPriceData(categoryList, lastIdx, dataList, currDay, currDateCal.getTime());
			}
		}
		dataSource.put("chart", chart);
		dataSource.put("categories", categories);
		dataSource.put("dataset", dataset.values());
		return dataSource;
	}

	/**
	 * 填充报表中空白没有数据的时间点
	 * @param categoryList 标题列表
	 * @param index 数据所在index
	 * @param dataList 数据列表
	 * @param lastDay 前一天数据时间
	 * @param currDay 当前数据时间
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void utilFillGapStatPriceData(
			List<Map<String, Object>> categoryList, int index,
			List<Map> dataList, Date lastDay, Date currDay) {
		Calendar lastDayCal = Calendar.getInstance();
		lastDayCal.setTime(lastDay);
		//上一时间第二天
		lastDayCal.add(Calendar.DATE, 1);
		String nextLastDayStr = DateUtil.date2Str(lastDayCal.getTime(), "yyyy-MM-dd");
		// 上一条数据值
		Map<String, Object> lastDataM = dataList.get(index-1);
		// 上一条数据价格
		Double lastPrice = (Double) lastDataM.get("value");
		// 横轴标题(日期(天))
		Map<String, Object> ctgMap = new HashMap<String, Object>();
		ctgMap.put("label", nextLastDayStr);
		categoryList.add(index, ctgMap);
		// 纵轴值（每日合计价格）
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("value", lastPrice);
		data.put("errorvalue", "");
		dataList.add(index, data);
		
		lastDay = lastDayCal.getTime();
		// 如果数据间隔时间大于一天，则补充以前数据时间
		if(currDay.getTime() - lastDay.getTime() > oneDayTimestamp){
			// 填充上一数据前空白数据
			utilFillGapStatPriceData(categoryList, index+1, dataList, lastDay, currDay);
		}
	}
	
	/**
	 * Get PlasticItem list
	 * @return
	 */
	public static List<PlasticItem> findList(Integer page, Integer count) {
		List<PlasticItem> list = null;
		try {
			DBObject orderBy = new BasicDBObject();
			orderBy.put("updateAt", -1);
			list = MongoClient.findList(page, count, orderBy, PlasticItem.class);
			if(list == null){
				return null;
			}
			Map<String, PlasticItem> itemMap = new HashMap<String, PlasticItem>();
			// 编号列表
			for (PlasticItem plasticItem : list) {
				// 四舍五入长度，5位小数
				String priceStr = fnum.format(plasticItem.getPrice());
				plasticItem.setPrice(Float.valueOf(priceStr));
				String diffPriceStr = fnum.format(plasticItem.getDiffPrice());
				plasticItem.setDiffPrice(Float.valueOf(diffPriceStr));
				// // 清空不需要的值
				plasticItem.setInventorys(null);
				plasticItem.setOnDeliverys(null);
				plasticItem.setOrderNopays(null);
				plasticItem.setFollowers(null);
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
	 * Get PlasticItem list for show diffrence price
	 * @return
	 */
	public static List<PlasticItem> findList4DiffPrice(Integer page, Integer count) {
		List<PlasticItem> list = null;
		try {
			DBObject query = new BasicDBObject();
			query.put("priceStatus", 2);
			DBObject orderBy = new BasicDBObject();
			orderBy.put("updateAt", -1);
			list = MongoClient.findList(query, page, count, orderBy, PlasticItem.class);
			if(list == null){
				return null;
			}
			for (PlasticItem plasticItem : list) {
				// 四舍五入长度，5位小数
				String priceStr = fnum.format(plasticItem.getPrice());
				plasticItem.setPrice(Float.valueOf(priceStr));
				String diffPriceStr = fnum.format(plasticItem.getDiffPrice());
				plasticItem.setDiffPrice(Float.valueOf(diffPriceStr));
				// // 清空不需要的值
				plasticItem.setInventorys(null);
				plasticItem.setOnDeliverys(null);
				plasticItem.setOrderNopays(null);
				plasticItem.setFollowers(null);
			}
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
		for(int i=0; i<resultList.size(); i++){
			BasicDBObject dbObject = (BasicDBObject) resultList.get(i);
			String itemNo = dbObject.getString("plasticItem");
			PlasticItem item = itemMap.get(itemNo);
			if(item != null){
				Double sum = dbObject.getDouble("availableAmountSum");
				if(!Double.isNaN(sum)){
					String sumStr = fnum.format(sum);
					sum = Double.valueOf(sumStr);
				}else{
					sum = 0d;
				}
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
		for(int i=0; i<resultList.size(); i++){
			BasicDBObject dbObject = (BasicDBObject) resultList.get(i);
			String itemNo = dbObject.getString("plasticItem");
			PlasticItem item = itemMap.get(itemNo);
			if(item != null){
				Double sum = dbObject.getDouble("notInInRepositorySum");
				if(!Double.isNaN(sum)){
					String sumStr = fnum.format(sum);
					sum = Double.valueOf(sumStr);
				}else{
					sum = 0d;
				}
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
		for(int i=0; i<resultList.size(); i++){
			BasicDBObject dbObject = (BasicDBObject) resultList.get(i);
			String itemNo = dbObject.getString("plasticItem");
			PlasticItem item = itemMap.get(itemNo);
			if(item != null){
				Double sum = dbObject.getDouble("noInvoiceAmountSum");
				if(!Double.isNaN(sum)){
					String sumStr = fnum.format(sum);
					sum = Double.valueOf(sumStr);
				}else{
					sum = 0d;
				}
				item.setOnDeliveryNotInInRepositorySum(sum);
			}
		}
	}

	/**
	 * 统计价格趋势
	 * @param itemNo 变化
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	private static List<Object[]> statPriceTrend(String[] itemNoArr, Date beginTime, Date endTime) {
		List<Object[]> result = null;
		DBCollection coll = MongoClient.getCollection(QuotationList.class);
		// 按时间分组
		String keyf = "function(doc){"
				+ "  var date = doc.lastUpdate.substring(0,10);" // 截取时间 yyyy-MM-dd
				+ "  return {'day':date, 'itemNo': doc.plasticItem};"
				+ "}";
		// 条件
		DBObject condition = new BasicDBObject();
		condition.put("plasticItem", new BasicDBObject("$in", itemNoArr));
		if(beginTime != null){
			DBObject condDate = new BasicDBObject();
			String beginTimeStr = DateUtil.date2Str(beginTime, "yyyy-MM-dd HH:mm:ss.S");
			condDate.put("$gte", beginTimeStr);
			condition.put("lastUpdate", condDate);
		}
		if(endTime != null){
			DBObject condDate = new BasicDBObject();
			String endTimeStr = DateUtil.date2Str(endTime, "yyyy-MM-dd HH:mm:ss.S");
			condDate.put("$gte", endTimeStr);
			condition.put("lastUpdate", new BasicDBObject().put("$lt", endTimeStr));
		}
		// 初始
		DBObject initial = new BasicDBObject();
		initial.put("sumPrice", 0);
		String reduce = "function(curr, result) {"
				+ "  if(curr.lastUpdate && curr.suggestPrice){"
				+ "    result.sumPrice += curr.suggestPrice;"
				+ "  }"
				+ "}";
		// 最后处理
		String finalize = null;
		GroupCommand cmd = new GroupCommand(coll, keyf, condition, initial, reduce, finalize);
		BasicDBList resultList = (BasicDBList)coll.group(cmd);
		if(resultList == null || resultList.size()==0){
			return result;
		}
		result = new ArrayList<Object[]>();
		for(int i=0; i<resultList.size(); i++){
			BasicDBObject dbObject = (BasicDBObject) resultList.get(i);
			String day = dbObject.getString("day");
			String itemNo = dbObject.getString("itemNo");
			Double sumPrice = dbObject.getDouble("sumPrice");
			Object[] rObjects = {day, itemNo, sumPrice};
			result.add(rObjects);
		}
		// 按时间排序
		Collections.sort(result, new Comparator<Object[]>() {
			public int compare(Object[] arg0, Object[] arg1) {
				String dayStr0 = (String) arg0[0];
				Date day0 = DateUtil.str2Date(dayStr0, "yyyy-MM-dd");
				String dayStr1 = (String) arg1[0];
				Date day1 = DateUtil.str2Date(dayStr1, "yyyy-MM-dd");
				if (day0.getTime() > day1.getTime()) {
					return 1;
				} else if (day0.getTime() == day1.getTime()) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		/*if(result.size()>5){
			for(Object[] objs : result){
				String dayStr = (String) objs[0];
				objs[0] = dayStr.substring(5, 10);  //截取部分 MM-dd
			}	
		}*/
		return result;
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
