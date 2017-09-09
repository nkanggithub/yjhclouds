package com.nkang.kxmoment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.baseobject.PlasticItem;
import com.nkang.kxmoment.service.PlasticItemService;
import com.nkang.kxmoment.util.MongoClient;
import com.nkang.kxmoment.util.MongoDBBasic;

/**
 * 塑胶库存信息 Controller
 * @author cqdxk
 *
 */
@Controller
@RequestMapping("/PlasticItem")
public class PlasticItemController {
	@RequestMapping(value="/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, HttpServletResponse response, PlasticItem item){
		allowCrossDomain(request, response);
		Map<String, Object> respParms = new HashMap<String, Object>();
		// 保存库
		String id = PlasticItemService.save(item);
		if(id != null){
			respParms.put("code", "200");
			respParms.put("data", id);
		}else{
			respParms.put("code", "500");
			
		}
		return respParms;
	}

	@RequestMapping("/findList")
	@ResponseBody
	public Map<String, Object> findList(HttpServletRequest request, HttpServletResponse response, Integer page, Integer count){
		allowCrossDomain(request, response);
		Map<String, Object> respParms = new HashMap<String, Object>();
		List<PlasticItem> list = PlasticItemService.findList(page, count);
		respParms.put("code", "200");
		respParms.put("data", list);
		return respParms;
	}

	@RequestMapping("/findList4DiffPrice")
	@ResponseBody
	public Map<String, Object> findList4DiffPrice(HttpServletRequest request, HttpServletResponse response, Integer page, Integer count){
		allowCrossDomain(request, response);
		Map<String, Object> respParms = new HashMap<String, Object>();
		List<PlasticItem> list = PlasticItemService.findList4DiffPrice(page, count);
		respParms.put("code", "200");
		respParms.put("data", list);
		return respParms;
	}

	/**
	 * 价格列表
	 * @param request
	 * @param response
	 * @param itemNo
	 * @param type
	 * @return
	 */
	@RequestMapping("/priceList")
	@ResponseBody
	public Map<String, Object> priceList(HttpServletRequest request, HttpServletResponse response, String itemNo, String type){
		allowCrossDomain(request, response);
		Map<String, Object> dataSource = PlasticItemService.priceList(itemNo, type);
		return dataSource;
	}
	
	@RequestMapping("/getDetail")
	@ResponseBody
	public Map<String, Object> getDetail(HttpServletRequest request, HttpServletResponse response, String id){
		allowCrossDomain(request, response);
		Map<String, Object> respParms = new HashMap<String, Object>();
		PlasticItem item = MongoClient.getOneById(id, PlasticItem.class);
		respParms.put("code", "200");
		respParms.put("data", item);
		return respParms;
	}
	
	@RequestMapping("/getDetailByNo")
	@ResponseBody
	public Map<String, Object> getDetailByNo(HttpServletRequest request, HttpServletResponse response, String itemNo){
		allowCrossDomain(request, response);
		Map<String, Object> respParms = new HashMap<String, Object>();
		PlasticItem item = PlasticItemService.getDetailByNo(itemNo);
		respParms.put("code", "200");
		respParms.put("data", item);
		return respParms;
	}
	@RequestMapping("/updatePlasticItemType")
	@ResponseBody
	public boolean updatePlasticItemType(HttpServletRequest request, HttpServletResponse response, String itemNo,String type){
		boolean result = MongoDBBasic.updatePlasticItemType(type,itemNo);
		return result;
	}
	@RequestMapping("/getPlasticItemTypeByNo")
	@ResponseBody
	public String getPlasticItemTypeByNo(HttpServletRequest request, HttpServletResponse response, String itemNo){
		String result = MongoDBBasic.getPlasticItemTypeByNo(itemNo);
		return result;
	}
	@RequestMapping("/saveUserAllKMByItemType")
	@ResponseBody
	public String saveUserAllKMByItemType(HttpServletRequest request, HttpServletResponse response, String openid,String type){
		String result = MongoDBBasic.saveUserAllKMByItemType(openid,type);
		return result;
	}
	private void allowCrossDomain(HttpServletRequest request, HttpServletResponse response) {
		// 跨域访问设置
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
	}
}
