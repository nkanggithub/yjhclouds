package com.nkang.kxmoment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
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
import com.nkang.kxmoment.controller.MasterDataRestController;


public class FileOperateUtil {
	static String ny="Y";
	public static String DBOperateOrderNopay(String url){
		
	 	String message="";
		int total=0;
		int success=0;
		int fail=0;
		String failnum=null;
		BillOfSellPoi bos = new BillOfSellPoi();
  		RestUtils.deleteDB("OrderNopay");
  		List<OrderNopay> OrderNopays;
  	
  		try {
  			OrderNopays = bos.readXlsOfOrderNopay(url);
  			for(OrderNopay ol : OrderNopays){
  				total++;
  				String ret;
  				if("Y".equals(ny)){
  					MongoDBBasic.DeleteDB("OrderNopay");
  					ret = MongoDBBasic.saveOrderNopay(ol);
  				}else{
  					ret = RestUtils.callsaveOrderNopay(ol);
  				}
  				
  				if("failed".equals(ret)||"Read timed out".equals(ret)){
  					ret=RestUtils.callsaveOrderNopay(ol);
  				}
  				if("failed".equals(ret)){
  					failnum = failnum + total+",";
  					fail++;
  				}else{
  					success++;
  				}
  				
  				 System.out.println(total+"----"+ret);
  				 //System.out.println(oq.info());
  			}
  			
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			System.out.println(e.getMessage());
  		}finally{
  			File file = new File(url); 
  			if (file.exists()) { 
  			    file.delete(); 
  			}
  		}
  		message = "成功导入:"+success+"条; "+" 导入失败"+fail+"条; "+ "总共"+total+"条; "+" 失败具体条数:"+failnum;
		return message;
	}
	
public static String DBOperateInventory(String url){
		
	 	String message="";
		int total=0;
		int success=0;
		int fail=0;
		String failnum=null;
		BillOfSellPoi bos = new BillOfSellPoi();
  		RestUtils.deleteDB("Inventory");
  		List<Inventory> Inventorys;
  	
  		try {
  			Inventorys = bos.readXlsOfInventory(url);
  			for(Inventory ol : Inventorys){
  				total++;
  				String ret = RestUtils.callsaveInventory(ol);
  				if("failed".equals(ret)||"Read timed out".equals(ret)){
  					ret=RestUtils.callsaveInventory(ol);
  				}
  				if("failed".equals(ret)){
  					failnum = failnum + total+",";
  					fail++;
  				}else{
  					success++;
  				}
  				
  				 System.out.println(total+"----"+ret);
  				 //System.out.println(oq.info());
  			}
  			
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  			System.out.println(e.getMessage());
  		}finally{
  			File file = new File(url); 
  			if (file.exists()) { 
  			    file.delete(); 
  			}
  		}
  		message = "成功导入:"+success+"条; "+" 导入失败"+fail+"条; "+ "总共"+total+"条; "+" 失败具体条数:"+failnum;
		return message;
	}
	

public static String DBOperateOnDelivery(String url){
	
 	String message="";
	int total=0;
	int success=0;
	int fail=0;
	String failnum=null;
	BillOfSellPoi bos = new BillOfSellPoi();
		RestUtils.deleteDB("OnDelivery");
		List<OnDelivery> OnDeliverys;
	
		try {
			OnDeliverys = bos.readXlsOfOnDelivery(url);
			for(OnDelivery ol : OnDeliverys){
				total++;
				String ret = RestUtils.callsaveOnDelivery(ol);
				if("failed".equals(ret)||"Read timed out".equals(ret)){
					ret=RestUtils.callsaveOnDelivery(ol);
				}
				if("failed".equals(ret)||"Read timed out".equals(ret)){
					failnum = failnum + total+",";
					fail++;
				}else{
					success++;
				}
				
				 System.out.println(total+"----"+ret);
				 //System.out.println(oq.info());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			File file = new File(url); 
			if (file.exists()) { 
			    file.delete(); 
			}
		}
		message = "成功导入:"+success+"条; "+" 导入失败"+fail+"条; "+ "总共"+total+"条; "+" 失败具体条数:"+failnum;
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
}
