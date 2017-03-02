package com.nkang.kxmoment.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 
 * @author cqdxk
 *
 */
public class MongoClient {
	private static Logger logger = Logger.getLogger(MongoClient.class);
	private static DB db = MongoDBBasic.getMongoDB();

	/**
	 * 获取集合
	 * @param clazz
	 * @return
	 */
	public static DBCollection getCollection(Class<?> clazz) {
		if(clazz == null){
			return null;
		}
		return db.getCollection(clazz.getSimpleName());
	}
	
	/**
	 * 获取集合
	 * @param clazz
	 * @return
	 */
	public static DBCollection getCollection(String collName) {
		if(collName == null){
			return null;
		}
		return db.getCollection(collName);
	}
	/**
	 * 保存对象
	 * @param collName 集合名词
	 * @param obj 对象
	 * @return
	 */
	public static String save(Object obj) {
		if(obj == null){
			return null;
		}
		// 集合名词
		String collName = obj.getClass().getSimpleName();
		return save(collName, obj);
	}
	
	/**
	 * 批量保存对象
	 * @param list 对象集合
	 * @return
	 */
	public static int batchSave(List<?> list) {
		return batchSave(null, list);
	}
	
	/**
	 * 批量保存对象
	 * @param collName 集合名词
	 * @param list 对象集合
	 * @return
	 */
	public static int batchSave(String collName, List<?> list) {
		int count = -1;
		try {
			List<DBObject> dbList = null;
			if(list != null && list.size() > 0){
				if(collName == null || collName.length() <= 0){
					collName = list.get(0).getClass().getSimpleName();
				}
				// 集合
				DBCollection collection = db.getCollection(collName);
				dbList = new ArrayList<DBObject>(list.size());
				for(Object obj : list){
					// 转换
					DBObject dbObj = BasicDBObject.parse(JSON.toJSONString(obj));
					String id = getId(obj);
					if(id != null && id.length() > 0){
						dbObj.put("_id", new ObjectId(id));
						dbObj.removeField("id");
					}
					dbList.add(dbObj);
				}
				collection.insert(dbList);
				count = dbList.size();
			}
		} catch (Exception e) {
			logger.error("batchSave error.", e);
			count = -1;
		}
		return count;
	}
	
	/**
	 * 批量保存对象
	 * @param list 对象集合
	 * @return
	 */
	public static int batchSave(Object[] list) {
		return batchSave(null, list);
	}
	/**
	 * 批量保存对象
	 * @param collName 集合名词
	 * @param list 对象集合
	 * @return
	 */
	public static int batchSave(String collName, Object[] list) {
		int count = -1;
		try {
			List<DBObject> dbList = null;
			if(list != null && list.length > 0){
				if(collName == null || collName.length() <= 0){
					collName = list[0].getClass().getSimpleName();
				}
				// 集合
				DBCollection collection = db.getCollection(collName);
				dbList = new ArrayList<DBObject>(list.length);
				for(Object obj : list){
					// 转换
					DBObject dbObj = BasicDBObject.parse(JSON.toJSONString(obj));
					String id = getId(obj);
					if(id != null && id.length() > 0){
						dbObj.put("_id", new ObjectId(id));
						dbObj.removeField("id");
					}
					dbList.add(dbObj);
				}
				collection.insert(dbList);
				count = dbList.size();
			}
		} catch (Exception e) {
			logger.error("batchSave error.", e);
			count = -1;
		}
		return count;
	}
	
	/**
	 * 保存对象
	 * @param collName 集合名词
	 * @param obj 对象
	 * @return  成功返回Id, 否则null
	 */
	public static String save(String collName, Object obj) {
		String id = null;
		try {
			// 集合
			DBCollection collection = db.getCollection(collName);
			// 转换
			DBObject dbObj = BasicDBObject.parse(JSON.toJSONString(obj));
			// 判断ID是否存在
			String idtmp = getId(obj);
			if(idtmp != null && idtmp.length() > 0){
				dbObj.put("_id", new ObjectId(idtmp));
				dbObj.removeField("id");
			}
			collection.save(dbObj);
			id = dbObj.get("_id").toString();;
		} catch (Exception e) {
			logger.error("save error.", e);
			id = null;
		}
		return id;
	}

	/**
	 * 
	 * @param clazz 转换类型
	 * @return
	 */
	public static List<DBObject> findListNoCast(Integer page, Integer count, Class<?> clazz){
		if(clazz == null){
			return null;
		}
		String collName = clazz.getSimpleName();
		return findListNoCast(collName, null, page, count, null);
	}
	
	/**
	 * 
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> List<T> findList(Integer page, Integer count, Class clazz){
		return findList(null, null, null, page, count, null, clazz);
	}
	
	/**
	 * 
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> List<T> findList(Class clazz){
		return findList(null, null, null, null, null, null, clazz);
	}
	
	/**
	 * 
	 * @param collName 集合名
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> List<T> findList(String collName, Class clazz){
		return findList(collName, null, null, null, null, null, clazz);
	}
	
	/**
	 * 
	 * @param collName 集合名
	 * @param propertyName 字段名
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> List<T> findList(String collName, String propertyName, Class clazz){
		return findList(collName, propertyName, null, null, null, null, clazz);
	}
	
	/**
	 * 
	 * @param collName 集合名
	 * @param query 条件
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> List<T> findList(String collName, DBObject query, Class clazz){
		return findList(collName, null, query, null, null, null, clazz);
	}

	
	
	/**
	 * 
	 * @param collName 集合名
	 * @param propertyName 字段名
	 * @param orderBy 排序
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> List<T> findList(String collName, String propertyName, DBObject orderBy, Class clazz){
		return findList(collName, propertyName, null, null, null, null, clazz);
	}
	
	/**
	 * 
	 * @param collName 集合名
	 * @param query 条件
	 * @param orderBy 排序
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> List<T> findList(DBObject query, Class clazz){
		return findList(null, null, query, null, null, null, clazz);
	}
	
	/**
	 * 
	 * @param collName 集合名
	 * @param query 条件
	 * @param orderBy 排序
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> List<T> findList(DBObject query, DBObject orderBy, Class clazz){
		return findList(null, null, query, null, null, null, clazz);
	}
	
	/**
	 * 
	 * @param collName 集合名
	 * @param query 条件
	 * @param orderBy 排序
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> List<T> findList(String collName, DBObject query, DBObject orderBy, Class clazz){
		return findList(collName, null, query, null, null, null, clazz);
	}
	
	/**
	 * 
	 * @param collName 集合名
	 * @param query 条件
	 * @param orderBy 排序
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> List<T> findList(String collName, String propertyName, DBObject query, DBObject orderBy, Class clazz){
		return findList(collName, propertyName, query, null, null, orderBy, clazz);
	}
	
	/**
	 * 查询列表
	 * @param query 查询条件
	 * @param page 页码
	 * @param count 每页条数
	 * @param orderBy 排序
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "hiding" })
	public static <T> List<T> findList(Integer page, Integer count, DBObject orderBy, Class clazz) {
		// 集合名
		return findList(null, null, null, page, count, orderBy, clazz);
	}
	
	/**
	 * 查询列表
	 * @param query 查询条件
	 * @param page 页码
	 * @param count 每页条数
	 * @param orderBy 排序
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "hiding" })
	public static <T> List<T> findList(DBObject query, 
			Integer page, Integer count, DBObject orderBy, Class clazz) {
		// 集合名
		return findList(null, null, query, page, count, orderBy, clazz);
	}
	
	/**
	 * 查询列表
	 * @param propertyName 字段名
	 * @param query 查询条件
	 * @param page 页码
	 * @param count 每页条数
	 * @param orderBy 排序
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "hiding" })
	public static <T> List<T> findList(String propertyName, DBObject query, 
			Integer page, Integer count, DBObject orderBy, Class clazz) {
		// 集合名
		return findList(null, propertyName, query, page, count, orderBy, clazz);
	}
	
	/**
	 * 查询
	 * @param collName 集合名
	 * @param propertyName 字段名
	 * @param query 查询条件
	 * @param page 页码
	 * @param count 每页条数
	 * @param orderBy 排序
	 * @param clazz 转换类型
	 * @return
	 */
	@SuppressWarnings({ "hiding", "unchecked", "rawtypes" })
	public static <T> List<T> findList(String collName, String propertyName, DBObject query, 
			Integer page, Integer count, DBObject orderBy, Class clazz) {
		List<T> list = null;
		if(collName == null){
			if(clazz == null){
				return null;
			}else{
				// 集合名
				collName = clazz.getSimpleName();
			}
		
		}
		// 集合
		DBCollection collection = db.getCollection(collName);
		// 初始化查询条件
//		query = query!=null ? query : new BasicDBObject();
		count = count != null && count > 0 ? count : 10;
		page = page != null && page > 0 ? (page-1)*count : 0;
		// 查询
		DBCursor cursor = collection.find(query).skip(page).limit(count).sort(orderBy);
		if(cursor != null && cursor.length() > 0){
			list = new ArrayList<T>(cursor.length());
			// 迭代数据
			Iterator<DBObject> iterator = cursor.iterator();
			while (iterator.hasNext()) {
				Object valueObject = null;
				DBObject dbObject = iterator.next();
				if(propertyName != null){
					valueObject = dbObject.get(propertyName);	
				}else {
					valueObject = dbObject;
				}
				T t2;
				if(valueObject instanceof BasicDBList){
					if(clazz != null){
						t2 = (T) JSON.parseArray(valueObject.toString(), clazz);
					}else{
						t2 = (T) JSON.parseArray(valueObject.toString());
					}
					list.add(t2);
					// 设置ID
					setId(dbObject, t2);
				}else{
					if(clazz != null){
						t2 = (T) JSON.parseObject(valueObject.toString(), clazz);
					}else {
						t2 = (T) JSON.parseObject(valueObject.toString());
					}
					// 设置ID
					setId(dbObject, t2);
					list.add(t2);
				}
			}
		}
		return list;
	}
	
	/**
	 * 查询
	 * @param collName 集合名
	 * @param propertyName 字段名
	 * @param query 查询条件
	 * @param page 页码
	 * @param count 每页条数
	 * @param orderBy 排序
	 * @param clazz 转换类型
	 * @return
	 */
	public static  List<DBObject> findListNoCast(String collName, DBObject query, 
			Integer page, Integer count, DBObject orderBy) {
		List<DBObject> list = null;
		if(collName == null){
			return null;
		}
		// 集合
		DBCollection collection = db.getCollection(collName);
		// 初始化查询条件
//		query = query!=null ? query : new BasicDBObject();
		page = page != null && page > 0 ? page-1 : 0;
		count = count != null && count > 0 ? count : 10;
		// 查询
		DBCursor cursor = collection.find(query).skip(page).limit(count).sort(orderBy);
		if(cursor != null && cursor.length() > 0){
			list = new ArrayList<DBObject>(cursor.length());
			// 迭代数据
			Iterator<DBObject> iterator = cursor.iterator();
			while (iterator.hasNext()) {
				DBObject dbObject = iterator.next();
				list.add(dbObject);
			}
		}
		return list;
	}
	
	/**
	 * 分组统计
	 * @param key 要统计字段
	 * @param cond 统计条件
	 * @param initial 统计结果初始值
	 * @param reduce 统计的JS脚本
	 * @param clazz 集合类
	 * @return
	 */
	public static DBObject groupBy(final DBObject key, final DBObject cond, final DBObject initial, 
			final String reduce, Class<?> clazz) {
		if(key==null || cond==null || initial==null || reduce==null){
			return null;
		}
		DBCollection col = getCollection(clazz);
		return col.group(key, cond, initial, reduce);
	}
	
	/**
	 * 分组统计
	 * @param key 要统计字段
	 * @param cond 统计条件
	 * @param initial 统计结果初始值
	 * @param reduce 统计的JS脚本
	 * @param finalize 返回结果前操作的JS脚步
	 * @param clazz 集合类
	 * @return
	 */
	public static DBObject groupBy(final DBObject key, final DBObject cond, final DBObject initial, final String reduce,
            final String finalize, Class<?> clazz) {
		if(key==null || cond==null || initial==null || reduce==null){
			return null;
		}
		DBCollection col = getCollection(clazz);
		return col.group(key, cond, initial, reduce, finalize);
	}
	
	/**
	 * 分组统计
	 * @param key 要统计字段
	 * @param cond 统计条件
	 * @param initial 统计结果初始值
	 * @param reduce 统计的JS脚本
	 * @param collName 集合名
	 * @return
	 */
	public static DBObject groupBy(final DBObject key, final DBObject cond, final DBObject initial, 
			final String reduce, String collName) {
		if(key==null || cond==null || initial==null || reduce==null){
			return null;
		}
		DBCollection col = getCollection(collName);
		return col.group(key, cond, initial, reduce);
	}
	
	/**
	 * 分组统计
	 * @param key 要统计字段
	 * @param cond 统计条件
	 * @param initial 统计结果初始值
	 * @param reduce 统计的JS脚本
	 * @param finalize 返回结果前操作的JS脚步
	 * @param collName 集合名
	 * @return
	 */
	public static DBObject groupBy(final DBObject key, final DBObject cond, final DBObject initial, final String reduce,
            final String finalize, String collName) {
		if(key==null || cond==null || initial==null || reduce==null){
			return null;
		}
		DBCollection col = getCollection(collName);
		return col.group(key, cond, initial, reduce, finalize);
	}
	
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> T getOneById(String id, Class clazz) {
		DBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		return getOne(query, clazz);
	}
	
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> T getOneById(String collName, String id, Class clazz) {
		DBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		return getOne(collName, null, query, clazz);
	}
	
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> T getOne(DBObject query, Class clazz) {
		// 集合名
		String collName = clazz.getSimpleName();
		return getOne(collName, null, query, clazz);
	}
	
	/**
	 * 获取单条记录
	 * @param collName
	 * @param query
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "hiding", "rawtypes" })
	public static <T> T getOne(String collName, DBObject query, Class clazz) {
		return getOne(collName, null, query, clazz);
	}
	
	/**
	 * 获取单条记录
	 * @param collName 集合名
	 * @param propertyName 字段名
	 * @param query 查询条件
	 * @return
	 */
	@SuppressWarnings({ "hiding", "unchecked", "rawtypes" })
	public static <T> T getOne(String collName, String propertyName, DBObject query, Class clazz) {
		T t;
		// 集合
		DBCollection collection = db.getCollection(collName);
		// 初始化查询条件
		query = query!=null ? query : new BasicDBObject();
		DBObject dbObject = collection.findOne(query);
		Object valueObject;
		if(propertyName != null){
			valueObject = dbObject.get(propertyName);	
		}else {
			valueObject = dbObject;
		}
		if(valueObject instanceof BasicDBList){
			if(clazz != null){
				t = (T) JSON.parseArray(valueObject.toString(), clazz);
			}else{
				t = (T) JSON.parseArray(valueObject.toString());
			}
		}else{
			if(clazz != null){
				t = (T) JSON.parseObject(valueObject.toString(), clazz);
			}else {
				t = (T) JSON.parseObject(valueObject.toString());
			}
		}
		// 设置ID
		setId(dbObject, t);
		return t;
	}
	
	/**
	 * 设置ID
	 * @param dbObject
	 * @param t
	 */
	@SuppressWarnings("hiding")
	private static <T> void setId(DBObject dbObject, T t) {
		try {
			// 设置ID
			Method setterMethod = t.getClass().getDeclaredMethod("setId", String.class);
			setterMethod.invoke(t, dbObject.get("_id").toString());
		} catch (Exception e) {
			// logger.error("setId Error", e);
		}
	}
	
	/**
	 * 获取ID
	 * @param obj
	 * @return
	 */
	private static String getId(Object obj) {
		String id = null;
		try {
			// 设置ID
			Method setterMethod = obj.getClass().getDeclaredMethod("getId");
			id = (String)setterMethod.invoke(obj);
		} catch (Exception e) {
			// logger.error("getId Error", e);
		}
		return id;
	}
}
