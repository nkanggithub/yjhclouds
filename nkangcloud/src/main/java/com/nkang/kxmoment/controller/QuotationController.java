package com.nkang.kxmoment.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkang.kxmoment.baseobject.Inventory;
import com.nkang.kxmoment.baseobject.Location;
import com.nkang.kxmoment.baseobject.Market;
import com.nkang.kxmoment.baseobject.OnlineQuotation;
import com.nkang.kxmoment.baseobject.PlasticItem;
import com.nkang.kxmoment.baseobject.QuotationList;
import com.nkang.kxmoment.baseobject.Rate;
import com.nkang.kxmoment.baseobject.Visited;
import com.nkang.kxmoment.baseobject.Visitedreturn;
import com.nkang.kxmoment.baseobject.WeChatMDLUser;
import com.nkang.kxmoment.service.PlasticItemService;
import com.nkang.kxmoment.util.Constants;
import com.nkang.kxmoment.util.MongoDBBasic;
import com.nkang.kxmoment.util.RestUtils;

@Controller
public class QuotationController {


	@RequestMapping("/saveQuotationList")
	public @ResponseBody String saveQuotationList(@RequestParam(value="plasticItem", required=true) String plasticItem,
			@RequestParam(value="status", required=false) String status,
			@RequestParam(value="approveBy", required=false) String approveBy,
			@RequestParam(value="editBy", required=false) String editBy,
			@RequestParam(value="dateTime", required=false) String dateTime,
			@RequestParam(value="suggestPrice", required=false) Double suggestPrice,
			@RequestParam(value="type", required=false) int type
			){
		QuotationList quotation = new QuotationList();
		quotation.setApproveBy(approveBy);
		quotation.setDateTime(dateTime);
		quotation.setEditBy(editBy);
		quotation.setPlasticItem(plasticItem);
		quotation.setSuggestPrice(suggestPrice);
		quotation.setStatus(status);
		quotation.setType(type);
		
		String ret = MongoDBBasic.insertQuotationList(quotation);
		
		return ret;
	}
	
	
	@RequestMapping("/pushPlasticItem")
	public @ResponseBody List<PlasticItem> getPushPlasticItem(@RequestParam(value="openid", required=true) String openid){
		 List<PlasticItem>  plasticItemlist = new ArrayList<PlasticItem>();
		List<String> itemsList = new ArrayList<String>();
		WeChatMDLUser user=MongoDBBasic.queryUserKM(openid); 
		itemsList=user.getKmLists();
		for(String str : itemsList){
			plasticItemlist.add(PlasticItemService.getDetailByNo(str));
		}
		return plasticItemlist;
		
	}
	

	@RequestMapping("/insertVisited")
	public @ResponseBody String insertVisited(@RequestParam(value="openid") String openid,@RequestParam(value="pageName") String pageName,@RequestParam(value="imgUrl") String imgUrl,@RequestParam(value="nickName") String nickName){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		String str = MongoDBBasic.updateVisited(openid,date,pageName,imgUrl,nickName); 
		return str;
	}
	
	@RequestMapping("/getVisitedByOpenid")
	public @ResponseBody List<Visited> getVisitedByOpenid(@RequestParam(value="openid") String openid,@RequestParam(value="date")String date ){
		
		return MongoDBBasic.getVisited(openid,date); 
	}
	@RequestMapping("/getVisitedDetail")
	public @ResponseBody List<Visited> getVisitedByDate(@RequestParam(value="dateIndex")String dateIndex,@RequestParam(value="pageName")String pageName){
		String pn="";
		ArrayList<Map> visitedPageList=MongoDBBasic.QueryVisitPage();
		for(int i=0;i<visitedPageList.size();i++){
			if(visitedPageList.get(i).get("descName").toString().equals(pageName)){
				pn=visitedPageList.get(i).get("realName").toString().trim();
			}
		}
		int index=Integer.parseInt(dateIndex);
		String date=MongoDBBasic.getLastestDate(-6).get(index);
		return MongoDBBasic.getVisitedDetail(date,pn); 
	}
	@RequestMapping("/getSharedCustomer")
	public @ResponseBody List<Visited> getSharedCustomer(@RequestParam(value="dateIndex")String dateIndex,@RequestParam(value="uid")String uid,@RequestParam(value="nickName")String nickName){

		int index=Integer.parseInt(dateIndex);
		String date=MongoDBBasic.getLastestDate(-6).get(index);
		System.out.println("nickName========"+nickName);
		return MongoDBBasic.getSharedCustomer(uid,date,nickName); 
	}
	@RequestMapping("/getVisitedCustomerByXSDB")
	public @ResponseBody List<Visited> getVisitedCustomerByXSDB(@RequestParam(value="dateIndex")String dateIndex,@RequestParam(value="uid")String uid,@RequestParam(value="nickName")String nickName){

		int index=Integer.parseInt(dateIndex);
		String date=MongoDBBasic.getLastestDate(-6).get(index);
		System.out.println("nickName========"+nickName);
		return MongoDBBasic.getVisitedCustomerByXSDB(uid,date,nickName); 
	}
	
	@RequestMapping("/getSharedCustomerChart")
	public @ResponseBody List<Map> getSharedCustomerChart(@RequestParam(value="uid")String uid,@RequestParam(value="nickName")String nickName){

		List<String> dates=MongoDBBasic.getLastestDate(-6);
		List<Map> dataList = new ArrayList<Map>();
		Map<String, Object> data;
		for(int i=0;i<dates.size();i++){
		data = new HashMap<String, Object>();
		data.put("value", MongoDBBasic.getVisitedCustomerByXSDB(uid,dates.get(i),nickName).size());
		data.put("errorvalue", "");
		dataList.add(data);
		}
		return dataList;
	}
	public List<Map> getSharedCustomerChart(String uid,String nickName,List<String> dates){

		List<Map> dataList = new ArrayList<Map>();
		Map<String, Object> data;
		for(int i=0;i<dates.size();i++){
		data = new HashMap<String, Object>();
		data.put("value", MongoDBBasic.getVisitedCustomerByXSDB(uid,dates.get(i),nickName).size());
		data.put("errorvalue", "");
		dataList.add(data);
		}
		return dataList;
	}
	@RequestMapping("/getAllSharedCustomerChart")
	public @ResponseBody List<List<Map>> getAllSharedCustomerChart(@RequestParam(value="dateNum")int dateNum){
		System.out.println("start to implement...");
		List<String> dates=MongoDBBasic.getLastestDate(dateNum);
		List<List<Map>> finalData=new ArrayList<List<Map>>();
		 List<Map> dataList = getSharedCustomerChart("oij7nt2wV7C_dYVLxJvFJgOG9GpQ","王素萍",dates);
		  List<Map> dataList1 = getSharedCustomerChart("oij7nt60inaYfekRpCpSIVnhjwVU","邓立铭",dates);
		  List<Map> dataList2 = getSharedCustomerChart("oij7nt0gk8vOYth0_0gzoLwg2YyU","胡贵花",dates);
		  List<Map> dataList3 = getSharedCustomerChart("oij7nt82eNsDS6cYo_362sJIBtFs","罗成洪",dates);
		  List<Map> dataList4 = getSharedCustomerChart("oij7nt-E02pgCKU2EfHGIAxOF5cA","罗浩",dates);
		  List<Map> dataList5 = getSharedCustomerChart("oij7nt2EjSi-GPboZaaODgBfgNT8","陈博",dates);
		  List<Map> dataList6 = getSharedCustomerChart("oij7ntyMv00uo_vwhNN5UM2b2uHY","段阳",dates);
		  List<Map> dataList7 = getSharedCustomerChart("oij7nt_g_9Nk0AEfRm_pRiKbP1c4","郑仁利",dates);
		  List<Map> dataList8 = getSharedCustomerChart("oij7nt9jFLpGhyvkk5BSBM9QThE4","罗斯威",dates);
		  List<Map> dataList9 = getSharedCustomerChart("oij7ntyGSa-1ZH8qvv5ykfA5BwKA","温小兵",dates);
		  List<Map> dataList10 = getSharedCustomerChart("oij7nt8-8xoKGXWQXoaOnIhT7fis","马家勇",dates);
		  List<Map> dataList11 = getSharedCustomerChart("oij7ntxdF2qaQ8pirWJjVL9fI854","郝海涛",dates);
		 /* List<Map> dataList12 = getSharedCustomerChart("oij7nt5GgpKftiaoMSKD68MTLXpc","康宁",dates);
		  List<Map> dataList13 = getSharedCustomerChart("oij7ntzKTXhhx5vSGE45X_Q2KT_A","天小婷",dates);*/
		  finalData.add(dataList);
		  finalData.add(dataList1);
		  finalData.add(dataList2);
		  finalData.add(dataList3);
		  finalData.add(dataList4);
		  finalData.add(dataList5);
		  finalData.add(dataList6);
		  finalData.add(dataList7);
		  finalData.add(dataList8);
		  finalData.add(dataList9);
		  finalData.add(dataList10);
		  finalData.add(dataList11);
/*		  finalData.add(dataList12);
		  finalData.add(dataList13);*/
		  for(int i=0;i<dates.size();i++){
			  System.out.println("date.size"+dates.get(i));
		  }
		  for(int i=0;i<finalData.size();i++){
			  System.out.println("finalData.size"+finalData.get(i).size());
		  }
		  System.out.println("end to implement...");
		return finalData;
	}
	@RequestMapping("/getVisitedAllDate")
	public @ResponseBody List<String> getVisitedAllDate(){
		
		return MongoDBBasic.getVisitedAllDate(); 
	}
	
	@RequestMapping("/getVisitedbTotalNumByDate")
	public @ResponseBody  Map<String,String> getVisitedbTotalNumByDate(){
		List<String> dates = MongoDBBasic.getVisitedAllDate();
		
		return MongoDBBasic.getVisitedbTotalNumByDate(dates); 
	}
	
	@RequestMapping("/getInventoryDetailByItem")
	public @ResponseBody List<Inventory> getInventoryDetailByItem(@RequestParam(value="item")String item){
		
		return MongoDBBasic.getInventoryDetailByItem(item); 
	}

	@RequestMapping("/getRate")
	public @ResponseBody Double getRate(@RequestParam(value="name")String name){
		Double rate = 0.0;
		try {
			List<Rate> lt = RestUtils.callGetRate();
			Map<String, String> map = new HashMap<String, String>();
			for(Rate rt : lt){
				map.put(rt.getName(), rt.getRate());
			}
			rate = Double.valueOf(map.get(name));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rate;
	}
	@RequestMapping("/getNoVisitedDetail")
	public @ResponseBody List<WeChatMDLUser> getNoVisitedDetail(@RequestParam(value="dateIndex")String dateIndex,@RequestParam(value="pageName")String pageName){
		System.out.println("start to implement getNoVisitedDetail method");
		List<WeChatMDLUser> allUser = MongoDBBasic.getAllUserByIsInternalSeniorMgt();
		if(allUser.isEmpty()){
			System.out.println("no data in allUser Obj");
		}else
		{
			System.out.println("fisrt obj:-----"+allUser.get(1).getNickname());
		}
		int index=Integer.parseInt(dateIndex);
		String date=MongoDBBasic.getLastestDate(-6).get(index);
		List<Visited> visited= MongoDBBasic.getVisitedDetail(date,pageName); 
		for(int i=0;i<visited.size();i++){
			
			for(int j=0;j<allUser.size();j++){
			//	System.out.println("visited.get(j).nickName:----"+visited.get(j).getOpenid()+"--"+visited.get(j).getNickName());
				if(allUser.get(j).getOpenid().equals(visited.get(i).getOpenid())){
					System.out.println("allUser.get(i).nickName:----"+allUser.get(j).getOpenid()+"--"+allUser.get(j).getNickname());
					allUser.remove(j);
					}
				}
			}
		return allUser;
		}
/*	@RequestMapping("/getVisitedbTotalNumPage")
	public @ResponseBody Map<String,Visitedreturn> getVisitedbTotalNumPage(){
		Map<String,Visitedreturn> mapret = new HashMap<String,Visitedreturn>();
		List<String> dates = MongoDBBasic.getVisitedAllDate();
		//Map<String,List<Visited>> maps = MongoDBBasic.getVisitedByDate(dates); 
		int page1Num=0;
		int page2Num=0;
		for(String date : dates){
			Visitedreturn vrtn = new Visitedreturn();
			List<Visited> visiteds = MongoDBBasic.getVisitedDetail(date);
			for(Visited vis : visiteds){
				if(vis.getPageName().equals("page1")){
					page1Num = page1Num+vis.getVisitedNum();
				}else if(vis.getPageName().equals("page2")){
					page2Num = page2Num+vis.getVisitedNum();
				}
			}
			vrtn.setPage1Num(page1Num);
			vrtn.setPage2Num(page2Num);
			mapret.put(date, vrtn);
			page1Num=0;
			page2Num=0;
			visiteds.clear();
		}
		return mapret;
	}*/
	@RequestMapping("/sendQuotationMessage")
	public @ResponseBody String sendQuotationMessage(@RequestParam(value="openid", required=true) String openid,@RequestParam(value="title", required=false) String title,@RequestParam(value="img", required=true) String img,@RequestParam(value="imgType", required=true) String imgType){
		// List<PlasticItem>  plasticItemlist = new ArrayList<PlasticItem>();
		 if("".equals(title)||title==null){
				title="永佳和塑胶报价更新啦~";
			}
			if("1".equals(imgType)){
				img="http://wonderfulcq.bj.bcebos.com/"+img;
			}
		    List<WeChatMDLUser> allUser = MongoDBBasic.getAllUserByIsRegistered();
			List<String> itemsList = new ArrayList<String>();
			String url="";
             for(int i=0;i<allUser.size();i++){
            		WeChatMDLUser user=MongoDBBasic.queryUserKM(allUser.get(i).getOpenid());
            		itemsList=user.getKmLists();
     			String[] aStrings=new Market().getMarket(allUser.get(i).getSelfIntro());
     			String content="永佳和【"+aStrings[1]+"-"+aStrings[0]+"-"+aStrings[2]+"】邀您查看您所关注的牌号\n";
     			System.out.println("starting to get the itemsList....");
     			if(itemsList!=null&&itemsList.size()!=0){
     			for(String str : itemsList){
     				System.out.println("str-----------------------"+str);
     				if(PlasticItemService.getDetailByNo(str)!=null){
     			//	plasticItemlist.add();
     				content+="["+PlasticItemService.getDetailByNo(str).getItemNo()+"-￥"+PlasticItemService.getDetailByNo(str).getPrice()+"]\n";
     				}
     			}
     			}
     			
     			url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constants.APP_ID+"&redirect_uri=http%3A%2F%2F"+Constants.baehost+"%2Fmdm%2FquoteDetailExternal.jsp?UID="+allUser.get(i).getOpenid()+"&response_type=code&scope=snsapi_userinfo&state="+allUser.get(i).getOpenid()+"#wechat_redirect&UID=";
            	 RestUtils.sendQuotationToUser(allUser.get(i),content,img,"【"+allUser.get(i).getNickname()+"】"+title,url);
            	 content="";
            }
             
		
		return allUser.size()+"";
		
	}
	
	@RequestMapping("/sendReminderForQuotation")
	public @ResponseBody String sendReminderForQuotation()
	{
		String url="http://"+Constants.baehost+"/mdm/quoteDetail.jsp?UID=";
		int itemNeedApprove = PlasticItemService.findListWithOutApprove();
		String title=itemNeedApprove+"个牌号需要您审批";
		String content="";
		List<WeChatMDLUser> allUser = MongoDBBasic.getAllUserByIsInternalSeniorMgt();
		 for(int i=0;i<allUser.size();i++){
			 RestUtils.sendQuotationToUser(allUser.get(i),content," https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000Do9zj&oid=00D90000000pkXM","【"+allUser.get(i).getNickname()+"】"+title,url);
		 }
		 return "ok";
	}
}
