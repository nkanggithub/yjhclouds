package com.nkang.kxmoment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nkang.kxmoment.baseobject.Inventory;
import com.nkang.kxmoment.baseobject.OnDelivery;
import com.nkang.kxmoment.baseobject.OrderNopay;
import com.nkang.kxmoment.baseobject.PlatforRelated;

public class FileOperateUtil {
	static String ny="Y";
	public static String DBOperateOrderNopay(InputStream is){
		
	 	String message="";
		int total=0;
		int success=0;
		int fail=0;
		String failnum=null;
		BillOfSellPoi bos = new BillOfSellPoi();
  		
  		List<OrderNopay> OrderNopays=null;
  		if("Y".equals(ny)){
  			MongoDBBasic.DeleteDB("OrderNopay");
  		}else {
  			RestUtils.deleteDB("OrderNopay");
		}
  		try {
  			OrderNopays = bos.readXlsOfOrderNopay(is);
  			for(OrderNopay ol : OrderNopays){
  				total++;
  				String ret;
  				if("Y".equals(ny)){
  					
  					ret = MongoDBBasic.saveOrderNopay(ol);
  					if("failed".equals(ret)||"Read timed out".equals(ret)){
  						ret = MongoDBBasic.saveOrderNopay(ol);
  	  				}
  	  				if("failed".equals(ret)){
  	  					failnum = failnum + total+",";
  	  					fail++;
  	  				}else{
  	  					success++;
  	  				}
  	  				
  				}else{
  					
  					ret = RestUtils.callsaveOrderNopay(ol);
  					if("failed".equals(ret)||"Read timed out".equals(ret)){
  	  					ret=RestUtils.callsaveOrderNopay(ol);
  	  				}
  	  				if("failed".equals(ret)){
  	  					failnum = failnum + total+",";
  	  					fail++;
  	  				}else{
  	  					success++;
  	  				}
  	  				
  				}
  				
  				// System.out.println(total+"----"+ret);
  				 //System.out.println(oq.info());
  			}
  			
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			message = "OrderNopays =="+OrderNopays.size();
  			//System.out.println(e.getMessage());
  		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
//  		finally{
//  			if(is != null){
//				is.close();
//			}
////  			File file = new File(url); 
////  			if (file.exists()) { 
////  			    file.delete(); 
////  			}
//  		}
  		message = "成功导入:"+success+"条; "+" 导入失败"+fail+"条; "+ "总共"+total+"条; "+" 失败具体条数:"+failnum;
		return message;
	}
	
public static String DBOperateInventory(InputStream is){
		
	 	String message="";
		int total=0;
		int success=0;
		int fail=0;
		String failnum=null;
		String ret;
		BillOfSellPoi bos = new BillOfSellPoi();
  		//RestUtils.deleteDB("Inventory");
  		List<Inventory> Inventorys;
  		if("Y".equals(ny)){
  			MongoDBBasic.DeleteDB("Inventory");
  		}else {
  			RestUtils.deleteDB("Inventory");
		}
  		try {
  			Inventorys = bos.readXlsOfInventory(is);
  			for(Inventory ol : Inventorys){
  				total++;
  				
  				if("Y".equals(ny)){
  					ret = MongoDBBasic.saveInventory(ol);
  					if("failed".equals(ret)||"Read timed out".equals(ret)){
  	  					ret=MongoDBBasic.saveInventory(ol);
  	  				}
  	  				if("failed".equals(ret)){
  	  					failnum = failnum + total+",";
  	  					fail++;
  	  				}else{
  	  					success++;
  	  				}
  				}else{
  					
  					ret = RestUtils.callsaveInventory(ol);
  					if("failed".equals(ret)||"Read timed out".equals(ret)){
  	  					ret=RestUtils.callsaveInventory(ol);
  	  				}
  	  				if("failed".equals(ret)){
  	  					failnum = failnum + total+",";
  	  					fail++;
  	  				}else{
  	  					success++;
  	  				}
  				}
  				
  				// System.out.println(total+"----"+ret);
  				 //System.out.println(oq.info());
  			}
  			
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			System.out.println(e.getMessage());
  		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
  		message = "成功导入:"+success+"条; "+" 导入失败"+fail+"条; "+ "总共"+total+"条; "+" 失败具体条数:"+failnum;
		return message;
	}
	

public static String DBOperateOnDelivery(InputStream is){
	
 	String message="";
	int total=0;
	int success=0;
	int fail=0;
	String failnum=null;
	String ret ;
	BillOfSellPoi bos = new BillOfSellPoi();
		//RestUtils.deleteDB("OnDelivery");
		List<OnDelivery> OnDeliverys;
		if("Y".equals(ny)){
  			MongoDBBasic.DeleteDB("OnDelivery");
  		}else {
  			RestUtils.deleteDB("OnDelivery");
		}
		try {
			OnDeliverys = bos.readXlsOfOnDelivery(is);
			for(OnDelivery ol : OnDeliverys){
				total++;
				
  				if("Y".equals(ny)){
  					ret = MongoDBBasic.saveOnDelivery(ol);
  					if("failed".equals(ret)||"Read timed out".equals(ret)){
  						ret=MongoDBBasic.saveOnDelivery(ol);
  					}
  					if("failed".equals(ret)||"Read timed out".equals(ret)){
  						failnum = failnum + total+",";
  						fail++;
  					}else{
  						success++;
  					}
  					
  				}else{
  					ret = RestUtils.callsaveOnDelivery(ol);
  					if("failed".equals(ret)||"Read timed out".equals(ret)){
  						ret=RestUtils.callsaveOnDelivery(ol);
  					}
  					if("failed".equals(ret)||"Read timed out".equals(ret)){
  						failnum = failnum + total+",";
  						fail++;
  					}else{
  						success++;
  					}
  					
  				}
				
				// System.out.println(total+"----"+ret);
				 //System.out.println(oq.info());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		message = "成功导入:"+success+"条; "+" 导入失败"+fail+"条; "+ "总共"+total+"条; "+" 失败具体条数:"+failnum;
	return message;
}

	
	
	

public static String DBOperateOnExcl(InputStream is){
	
 	String message="";
	int OrderNopay_total=0;
	int OrderNopay_success=0;
	int OrderNopay_fail=0;
	String OrderNopay_failnum=null;
	int OnDelivery_total=0;
	int OnDelivery_success=0;
	int OnDelivery_fail=0;
	String OnDelivery_failnum=null;
	int Inventory_total=0;
	int Inventory_success=0;
	int Inventory_fail=0;
	String Inventory_failnum=null;
	String ret ;
	BillOfSellPoi bos = new BillOfSellPoi();
		//RestUtils.deleteDB("OnDelivery");
		Map<String, List> map;
		try {
			map = bos.readAllXls(is);
			if(map.isEmpty()){
				return message="Sheet 名称不符";
			}
			 for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
				 String dBname=(String)iter.next();
				 //System.out.println("name-----"+dBname);
				 if("Y".equals(ny)){
						MongoDBBasic.DeleteDB(dBname);
					}else{
						 RestUtils.deleteDB(dBname);
					}
			 }
			
			 if(map.get("OrderNopay")!=null){
				 for(int i=0;i<map.get("OrderNopay").size();i++){
					 OrderNopay_total++;
					 OrderNopay on = (OrderNopay)map.get("OrderNopay").get(i);
					 if("Y".equals(ny)){
						 ret = MongoDBBasic.saveOrderNopay(on);
		  					if("failed".equals(ret)||"Read timed out".equals(ret)){
		  						ret = MongoDBBasic.saveOrderNopay(on);
		  	  				}
		  	  				if("failed".equals(ret)){
		  	  					OrderNopay_failnum = OrderNopay_failnum + OrderNopay_total+",";
		  	  					OrderNopay_fail++;
		  	  				}else{
		  	  					OrderNopay_success++;
		  	  				}
					 }else{
						 ret = RestUtils.callsaveOrderNopay(on);
		  					if("failed".equals(ret)||"Read timed out".equals(ret)){
		  	  					ret=RestUtils.callsaveOrderNopay(on);
		  	  				}
		  	  				if("failed".equals(ret)){
		  	  					OrderNopay_failnum = OrderNopay_failnum + OrderNopay_total+",";
		  	  					OrderNopay_fail++;
		  	  				}else{
		  	  					OrderNopay_success++;
		  	  				}
					 }
					 
				 }
				 //" 失败具体条数:"+OrderNopay_failnum+
				 message = message+ "（已售未下账）OrderNopay成功导入:"+OrderNopay_success+"条; "+" 导入失败"+OrderNopay_fail+"条; "+ "总共"+OrderNopay_total+"条; "+"<br/>"; 
			 }
			 if(map.get("OnDelivery")!=null){
				 for(int i=0;i<map.get("OnDelivery").size();i++){
					 OnDelivery_total++;
					 OnDelivery ol = (OnDelivery)map.get("OnDelivery").get(i);
					 if("Y".equals(ny)){
						 ret = MongoDBBasic.saveOnDelivery(ol);
		  					if("failed".equals(ret)||"Read timed out".equals(ret)){
		  						 ret = MongoDBBasic.saveOnDelivery(ol);
		  	  				}
		  	  				if("failed".equals(ret)){
		  	  					OnDelivery_failnum = OnDelivery_failnum + OnDelivery_total+",";
		  	  					OnDelivery_fail++;
		  	  				}else{
		  	  					OnDelivery_success++;
		  	  				}
					 }else{
						 ret = RestUtils.callsaveOnDelivery(ol);
		  					if("failed".equals(ret)||"Read timed out".equals(ret)){
		  	  					ret=RestUtils.callsaveOnDelivery(ol);
		  	  				}
		  	  				if("failed".equals(ret)){
		  	  					OnDelivery_failnum = OnDelivery_failnum + OnDelivery_total+",";
		  	  					OnDelivery_fail++;
		  	  				}else{
		  	  					OnDelivery_success++;
		  	  				}
					 }
					
				 }
				 //" 失败具体条数:"+OnDelivery_failnum+
				 message = message+"（订单）OnDelivery成功导入:"+OnDelivery_success+"条; "+" 导入失败"+OnDelivery_fail+"条; "+ "总共"+OnDelivery_total+"条; "+"<br/>"; 
			 }
			 if(map.get("Inventory")!=null){
				 for(int i=0;i<map.get("Inventory").size();i++){
					 Inventory_total++;
					 Inventory on = (Inventory)map.get("Inventory").get(i);
					 if("Y".equals(ny)){
						 ret = MongoDBBasic.saveInventory(on);
		  					if("failed".equals(ret)||"Read timed out".equals(ret)){
		  						ret = MongoDBBasic.saveInventory(on);
		  	  				}
		  	  				if("failed".equals(ret)){
		  	  					Inventory_failnum = Inventory_failnum + Inventory_total+",";
		  	  					Inventory_fail++;
		  	  				}else{
		  	  					Inventory_success++;
		  	  				}
					 }else{
						 ret = RestUtils.callsaveInventory(on);
		  					if("failed".equals(ret)||"Read timed out".equals(ret)){
		  	  					ret=RestUtils.callsaveInventory(on);
		  	  				}
		  	  				if("failed".equals(ret)){
		  	  					Inventory_failnum = Inventory_failnum + Inventory_total+",";
		  	  					Inventory_fail++;
		  	  				}else{
		  	  					Inventory_success++;
		  	  				}
					 }
					 
				 }
				 //" 失败具体条数:"+Inventory_failnum+
				 message = message+ "(库存)Inventory_成功导入:"+Inventory_success+"条; "+" 导入失败"+Inventory_fail+"条; "+ "总共"+Inventory_total+"条; "+"<br/> "; 
			 }
			 
//			 for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
//				// System.out.println("name-----"+iter.next().toString());
//				
//				 
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	return message;
}

	




	public static String FILEDIR = null;
    /**
     * 上传
     * @param request
     * @throws IOException
     */
    public static void upload(HttpServletRequest request) throws IOException{       
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();       
        File file = new File(FILEDIR);
        if (!file.exists()) {
            file.mkdir();
        }
        Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, MultipartFile> entry = it.next();
            MultipartFile mFile = entry.getValue();
            if(mFile.getSize() != 0 && !"".equals(mFile.getName())){
                write(mFile.getInputStream(), new FileOutputStream(initFilePath(mFile.getOriginalFilename())));
            }
        }
    }
    private static String initFilePath(String name) {
        String dir = getFileDir(name) + "";
        File file = new File(FILEDIR + dir);
        if (!file.exists()) {
            file.mkdir();
        }
        Long num = new Date().getTime();
        Double d = Math.random()*num;
        return (file.getPath() + "/" + num + d.longValue() + "_" + name).replaceAll(" ", "-");
    }
    private static int getFileDir(String name) {
        return name.hashCode() & 0xf;
    }
    public static void download(String downloadfFileName, ServletOutputStream out) {
        try {
            FileInputStream in = new FileInputStream(new File(FILEDIR + "/" + downloadfFileName));
            write(in, out);
        } catch (FileNotFoundException e) {
            try {
                FileInputStream in = new FileInputStream(new File(FILEDIR + "/"
                        + new String(downloadfFileName.getBytes("iso-8859-1"),"utf-8")));
                write(in, out);
            } catch (IOException e1) {              
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }       
    }
    /**
     * 写入数据
     * @param in
     * @param out
     * @throws IOException
     */
    public static void write(InputStream in, OutputStream out) throws IOException{
        try{
            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } finally {
            try {
                in.close();
            }
            catch (IOException ex) {
            }
            try {
                out.close();
            }
            catch (IOException ex) {
            }
        }
    } 
    
    
    public static Map<String, List> OperateOnPlatforRelated(InputStream is){
    	Map map = new HashMap<String, List>();
     	String message="";
    	BillOfSellPoi bos = new BillOfSellPoi();
    		try {
    			PlatforRelated  platforRelated  = bos.platformRelated(is);
    			List APJlt = new ArrayList<Integer>();
    			APJlt.add(platforRelated.getDone_APJ());
    			APJlt.add(platforRelated.getInProgress_APJ());
    			APJlt.add(platforRelated.getInPlanning_APJ());
    			List USAlt = new ArrayList<Integer>();
    			USAlt.add(platforRelated.getDone_USA());
    			USAlt.add(platforRelated.getInProgress_USA());
    			USAlt.add(platforRelated.getInPlanning_USA());
    			
    			List MEXICOlt = new ArrayList<Integer>();
    			MEXICOlt.add(platforRelated.getDone_MEXICO());
    			MEXICOlt.add(platforRelated.getInProgress_MEXICO());
    			MEXICOlt.add(platforRelated.getInPlanning_MEXICO());
    			
    			List EMEAlt = new ArrayList<Integer>();
    			EMEAlt.add(platforRelated.getDone_EMEA());
    			EMEAlt.add(platforRelated.getInProgress_EMEA());
    			EMEAlt.add(platforRelated.getInPlanning_EMEA());
    			
    			
    			map.put("APJ", APJlt);
    			map.put("USA", USAlt);
    			map.put("MEXICO", MEXICOlt);
    			map.put("EMEA", EMEAlt);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			System.out.println(e.getMessage());
    		}finally{
    			if(is != null){
    				try {
    					is.close();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    		}
    		//message = "成功导入:"+success+"条; "+" 导入失败"+fail+"条; "+ "总共"+total+"条; "+" 失败具体条数:"+failnum;
    	return map;
    }

}
