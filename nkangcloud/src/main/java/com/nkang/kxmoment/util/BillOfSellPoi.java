package com.nkang.kxmoment.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.nkang.kxmoment.baseobject.BillOfSell;
import com.nkang.kxmoment.baseobject.Inventory;
import com.nkang.kxmoment.baseobject.OnDelivery;
import com.nkang.kxmoment.baseobject.OnlineQuotation;
import com.nkang.kxmoment.baseobject.OrderNopay;

public class BillOfSellPoi {
	private static List<String> Jeffrey;
	private static List<String> Antonio;
	private static List<String> Nils;
	private static List<String> China;
	public List<OnlineQuotation> readXlsOfQuotations() throws FileNotFoundException{
		List<OnlineQuotation> quotationList = new ArrayList<OnlineQuotation>();
		 InputStream is = new FileInputStream("C:/Users/pengcha/Desktop/yongjiahe/price.XLS");

	        HSSFWorkbook hssfWorkbook;
			try {
				hssfWorkbook = new HSSFWorkbook(is);
			
				OnlineQuotation xlsDto = null;

				// 循环工作表Sheet

				    for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			
			            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			            HSSFRow hssfRowHead = hssfSheet.getRow(0);
			            HSSFCell location1 = hssfRowHead.getCell(5);
			            HSSFCell location2 = hssfRowHead.getCell(6);
			            if (hssfSheet == null) {
		
			                continue;
		
			            }
		
			            // 循环行Row
		
				            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			
				                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			
				                if (hssfRow == null) {
			
				                    continue;
			
				                }
				                xlsDto = new OnlineQuotation();
				                // 循环列Cell
					         //   for (int cellNum = 0; cellNum <=9; cellNum++) {
					            	
					                HSSFCell category = hssfRow.getCell(0);
					                xlsDto.setCategory(category+"");
				
					                HSSFCell categoryGrade = hssfRow.getCell(1);
					                xlsDto.setCategoryGrade(categoryGrade+"");
				
					                HSSFCell item = hssfRow.getCell(2);
					                xlsDto.setItem(item+"");
					                
					                HSSFCell quotationPrice = hssfRow.getCell(3);
					                xlsDto.setQuotationPrice(quotationPrice+"");
				
					                HSSFCell comments = hssfRow.getCell(4);
					                xlsDto.setComments(comments+"");
				
					                HSSFCell locationAmounts1 = hssfRow.getCell(5);
					                HSSFCell locationAmounts2 = hssfRow.getCell(6);
					                xlsDto.setLocationAmounts(location1+" : "+locationAmounts1+" | "+location2+" : "+locationAmounts2+"");
				
					                HSSFCell avaliableInventory = hssfRow.getCell(7);
					                xlsDto.setAvaliableInventory(avaliableInventory+"");
				
					                HSSFCell onDelivery = hssfRow.getCell(8);
					                xlsDto.setOnDelivery(onDelivery+"");
				
					                HSSFCell soldOutOfPay = hssfRow.getCell(9);
					                xlsDto.setSoldOutOfPay(soldOutOfPay+"");
					       //     }
					            quotationList.add(xlsDto);
				            }
			        }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return quotationList;
	} 

	 public List<BillOfSell> readXls() throws IOException  {
	
		 InputStream is = new FileInputStream("C:/Users/pengcha/Desktop/HP/MDL/AAA.xls");

	        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

	        BillOfSell xlsDto = null;

	        List<BillOfSell> list = new ArrayList<BillOfSell>();

	        // 循环工作表Sheet

	        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {

	            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);

	            if (hssfSheet == null) {

	                continue;

	            }

	            // 循环行Row

		            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
	
		                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
	
		                if (hssfRow == null) {
	
		                    continue;
	
		                }
		                xlsDto = new BillOfSell();
	
		                // 循环列Cell
	
	
			            for (int cellNum = 2; cellNum <=38; cellNum++) {
			            	
			                HSSFCell businessType = hssfRow.getCell(2);
		
			                xlsDto.setBusinessType(businessType+"");
		
			                HSSFCell sellType = hssfRow.getCell(3);
		
		
			                xlsDto.setSellType(sellType+"");
		
			                HSSFCell orderNumber = hssfRow.getCell(4);
		
			                xlsDto.setOrderNumber(orderNumber+"");
		
			                HSSFCell orderTime = hssfRow.getCell(5);
			                String ordert =TransitionDate(orderTime+"");
		
			                xlsDto.setOrderTime(ordert);
		
			                HSSFCell customerName = hssfRow.getCell(6);
		
			                xlsDto.setCustomerName(customerName+"");
			                
			                HSSFCell currency = hssfRow.getCell(7);
			                xlsDto.setCurrency(currency+"");
			                HSSFCell exchange = hssfRow.getCell(8);
			                xlsDto.setExchange(exchange+"");
			                HSSFCell salesDepartments = hssfRow.getCell(9);
			            	
			                xlsDto.setSalesDepartments(salesDepartments+"");
			                HSSFCell salesman = hssfRow.getCell(10);
			            	
			                xlsDto.setSalesman(salesman+"");
			                HSSFCell InventoryCoding = hssfRow.getCell(11);
			            	
			                xlsDto.setInventoryCoding(InventoryCoding+"");
			                HSSFCell inventoryCode = hssfRow.getCell(12);
			                xlsDto.setInventoryCode(inventoryCode+"");
			                HSSFCell inventoryName = hssfRow.getCell(13);
			                xlsDto.setInventoryName(inventoryName+"");
			                
			                HSSFCell specificationsModels = hssfRow.getCell(14);
			               
			                xlsDto.setSpecificationsModels(specificationsModels+"");
			                
			                HSSFCell unit = hssfRow.getCell(15);
			                xlsDto.setUnit(unit+"");
			                HSSFCell amount = hssfRow.getCell(16);
			                xlsDto.setAmount(amount+"");
			                HSSFCell unitPriceIncludTax = hssfRow.getCell(17);
			                xlsDto.setUnitPriceIncludTax(unitPriceIncludTax+"");
			                HSSFCell priceExcludingTax = hssfRow.getCell(18);
			                xlsDto.setPriceExcludingTax(priceExcludingTax+"");
			                HSSFCell noTaxAmount = hssfRow.getCell(19);
			                xlsDto.setNoTaxAmount(noTaxAmount+"");
			                HSSFCell tax = hssfRow.getCell(20);
			            	
			                xlsDto.setTax(tax+"");
			                HSSFCell totalPriceWithTax = hssfRow.getCell(21);
			            	
			                xlsDto.setTotalPriceWithTax(totalPriceWithTax+"");
			                HSSFCell taxRateString = hssfRow.getCell(22);
			            	
			                xlsDto.setTaxRateString(taxRateString+"");
			                HSSFCell deductible = hssfRow.getCell(23);
			            	
			                xlsDto.setDeductible(deductible+"");
			                
			                HSSFCell deductible2 = hssfRow.getCell(24);
			            	
			                xlsDto.setDeductible2(deductible2+"");
			                
			                HSSFCell advanceShipmentDate = hssfRow.getCell(25);
			            	
			                xlsDto.setAdvanceShipmentDate(TransitionDate(advanceShipmentDate+""));
			                
			                HSSFCell ordersForChildTableID = hssfRow.getCell(26);
			                xlsDto.setOrdersForChildTableID(ordersForChildTableID+"");
			                
			                HSSFCell unfilledOrderCount = hssfRow.getCell(27);
			            	
			                xlsDto.setUnfilledOrderCount(unfilledOrderCount+"");
			                
			                HSSFCell noInvoiceCount = hssfRow.getCell(28);
			            	
			                xlsDto.setNoInvoiceCount(noInvoiceCount+"");
			                
			                HSSFCell reservedNum = hssfRow.getCell(29);
			            	
			                xlsDto.setReservedNum(reservedNum+"");
			                
			                HSSFCell notDeliverNum = hssfRow.getCell(30);
			            	
			                xlsDto.setNotDeliverNum(notDeliverNum+"");
			                
			                HSSFCell notDeliverAmount = hssfRow.getCell(31);
			                xlsDto.setNotDeliverAmount(notDeliverAmount+"");
			                HSSFCell noInvoiceCounts = hssfRow.getCell(32);
			            	
			                xlsDto.setNoInvoiceCounts(noInvoiceCounts+"");
			                HSSFCell noInvoiceAmount = hssfRow.getCell(33);
			                xlsDto.setNoInvoiceAmount(noInvoiceAmount+"");
			                HSSFCell amountPurchased = hssfRow.getCell(34);
			                xlsDto.setAmountPurchased(amountPurchased+"");
			                HSSFCell noamountPurchased = hssfRow.getCell(35);
			                xlsDto.setNoamountPurchased(noamountPurchased+"");
			                HSSFCell noProduction = hssfRow.getCell(36);
			                xlsDto.setNoProduction(noProduction+"");
			                HSSFCell noOutsourcing = hssfRow.getCell(37);
			            	
			                xlsDto.setNoOutsourcing(noOutsourcing+"");
			                
			                HSSFCell noImportVolume = hssfRow.getCell(38);
			            	
			                xlsDto.setNoImportVolume(noImportVolume+"");
			                
			            }
		                list.add(xlsDto);
		        }
		 
	        }
			return list;
	 }

	 public String TransitionDate(String date){
		 String str = "";
		 if(date!=null){
			 String[] strarray=date.split("-");
			 if(strarray!=null && strarray.length>1){
				 if("一月".equals(strarray[1])){
					 str = "01";
				 }else if("二月".equals(strarray[1])){
					 str = "01";
				 }else if("三月".equals(strarray[1])){
					 str = "01";
				 }else if("四月".equals(strarray[1])){
					 str = "01";
				 }else if("五月".equals(strarray[1])){
					 str = "01";
				 }else if("六月".equals(strarray[1])){
					 str = "01";
				 }else if("七月".equals(strarray[1])){
					 str = "01";
				 }else if("八月".equals(strarray[1])){
					 str = "01";
				 }else if("九月".equals(strarray[1])){
					 str = "01";
				 }else if("十月".equals(strarray[1])){
					 str = "01";
				 }else if("十一月".equals(strarray[1])){
					 str = "01";
				 }else if("十二月".equals(strarray[1])){
					 str = "01";
				 }else{
					 str = " ";
				 }
				/* switch (strarray[1]) {
					case "一月": 
						str = "01";
						break;
					case "二月": 
						str = "02";
						break;
					case "三月": 
						str = "03";
						break;
					case "四月": 
						str = "04";
						break;
					case "五月": 
						str = "05";
						break;
					case "六月": 
						str = "06";
						break;
					case "七月": 
						str = "07";
						break;
					case "八月": 
						str = "08";
						break;
					case "九月": 
						str = "09";
						break;
					case "十月": 
						str = "10";
						break;
					case "十一月": 
						str = "11";
						break;
					case "十二月": 
						str = "12";
						break;
					default:
						str = " ";
						break;
					}*/
					date = strarray[0]+"-"+str+"-"+strarray[2];
			 }
		 }
		 
		 return date;
	 }
	 
	 public List<Inventory> readXlsOfInventory(InputStream is) throws FileNotFoundException{
			List<Inventory> inventoryList = new ArrayList<Inventory>();
			// InputStream is = new FileInputStream("C:/Users/pengcha/Desktop/HP/MDL/Inventory.XLS");
			//InputStream is = new FileInputStream(url);
		        HSSFWorkbook hssfWorkbook;
				try {
					hssfWorkbook = new HSSFWorkbook(is);
				
					Inventory xlsDto = null;

					// 循环工作表Sheet
					    for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
					            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					                if (hssfRow == null) {
					                    continue;
					                }
					                xlsDto = new Inventory();
//						            for (int cellNum = 0; cellNum <=6; cellNum++) {
//						            	
						                HSSFCell repositoryName = hssfRow.getCell(0);
						                xlsDto.setRepositoryName(repositoryName+"");
						                
						                HSSFCell plasticItem = hssfRow.getCell(1);
						                xlsDto.setPlasticItem(plasticItem+"");
						                
						                HSSFCell unit = hssfRow.getCell(2);
						                xlsDto.setUnit(unit+"");
						                
						                if(hssfRow.getCell(3)!=null){
						                	 Double inventoryAmount = hssfRow.getCell(3).getNumericCellValue(); 
								                xlsDto.setInventoryAmount(inventoryAmount);
						                }
						               
						                if(hssfRow.getCell(4)!=null){
							                Double waitDeliverAmount = hssfRow.getCell(4).getNumericCellValue();
							                xlsDto.setWaitDeliverAmount(waitDeliverAmount);
						                }
						                
						                if(hssfRow.getCell(5)!=null){
							                Double reserveDeliverAmount = hssfRow.getCell(5).getNumericCellValue();
							                xlsDto.setReserveDeliverAmount(reserveDeliverAmount);
						                }
						                
						                if(hssfRow.getCell(6)!=null){
							                Double availableAmount = hssfRow.getCell(6).getNumericCellValue();
							                xlsDto.setAvailableAmount(availableAmount);
						                }
						                inventoryList.add(xlsDto);
//						            }
					            }
					    }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				
				return inventoryList;
	 }
	 
	 public List<OnDelivery> readXlsOfOnDelivery(InputStream is) throws FileNotFoundException{
			List<OnDelivery> OnDeliveryList = new ArrayList<OnDelivery>();
			// InputStream is = new FileInputStream("C:/Users/pengcha/Desktop/HP/MDL/OnDelivery.XLS");
//			while (url==null) {
//				return null;
//			}
//			InputStream is = new FileInputStream(url);
		        HSSFWorkbook hssfWorkbook;
				try {
					hssfWorkbook = new HSSFWorkbook(is);
					OnDelivery xlsDto = null;
					// 循环工作表Sheet
					    for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				            // 循环行Row
					            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					                if (hssfRow == null) {
					                    continue;
					                }
					                xlsDto = new OnDelivery();
//					                // 循环列Cell
						                HSSFCell billID = hssfRow.getCell(2);
						                xlsDto.setBillID(billID+"");
						                HSSFCell date = hssfRow.getCell(3);
						                xlsDto.setDate(TransitionDate(date+""));
						                HSSFCell provider = hssfRow.getCell(4);
						                xlsDto.setProvider(provider+"");
						                HSSFCell plasticItem = hssfRow.getCell(5);
						                xlsDto.setPlasticItem(plasticItem+"");
						                
						                if(hssfRow.getCell(6)!=null){
							                Double amount=hssfRow.getCell(6).getNumericCellValue();
							                xlsDto.setAmount(amount);
						                }
						                
						                if(hssfRow.getCell(7)!=null){
							                Double originalPrice = hssfRow.getCell(7).getNumericCellValue();;
							                xlsDto.setOriginalPrice(originalPrice);
						                }
						                
						                if(hssfRow.getCell(8)!=null){
							                Double taxRate = hssfRow.getCell(8).getNumericCellValue();;
							                xlsDto.setTaxRate(taxRate);
						                }
						                
						                HSSFCell billType = hssfRow.getCell(9);
						                xlsDto.setBillType(billType+"");
						                
						                if(hssfRow.getCell(10)!=null){
							                Double notInInRepository = hssfRow.getCell(10).getNumericCellValue();;
							                xlsDto.setNotInInRepository(notInInRepository);
						                }
						                OnDeliveryList.add(xlsDto);
					            }
					    }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				return OnDeliveryList;
	 }
	 
	 public List<OrderNopay> readXlsOfOrderNopay(InputStream is) throws FileNotFoundException{
		// String urlString = "C:/Users/pengcha/Desktop/HP/MDL/OrderNopay.XLS";
			List<OrderNopay> OrderNopayList = new ArrayList<OrderNopay>();
			// InputStream is = new FileInputStream("C:/Users/pengcha/Desktop/HP/MDL/OrderNopay.XLS");
			//InputStream is = new FileInputStream(url);
		        HSSFWorkbook hssfWorkbook;
				try {
					hssfWorkbook = new HSSFWorkbook(is);
					OrderNopay xlsDto = null;
					// 循环工作表Sheet
					    for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				            // 循环行Row
					            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					                if (hssfRow == null) {
					                    continue;
					                }
					                xlsDto = new OrderNopay();
						                HSSFCell customerNameString = hssfRow.getCell(0);
						                xlsDto.setCustomerName(customerNameString+"");
						                
						                HSSFCell salesman = hssfRow.getCell(1);
						                xlsDto.setSalesman(salesman+"");
						                
						                HSSFCell billID = hssfRow.getCell(2);
						                xlsDto.setBillID(billID+"");
						                
						                HSSFCell billDate = hssfRow.getCell(3);
						                xlsDto.setBillDate(billDate+"");
						                
						                HSSFCell plasticItem = hssfRow.getCell(4);
						                xlsDto.setPlasticItem(plasticItem+"");
						                
						                Double unfilledOrderAmount = hssfRow.getCell(5).getNumericCellValue();
						                xlsDto.setUnfilledOrderAmount(unfilledOrderAmount);
						                
						                if(hssfRow.getCell(6)!=null){
						                	 Double filledOrderAmount = hssfRow.getCell(6).getNumericCellValue();
								                xlsDto.setFilledOrderAmount(filledOrderAmount);
						                }
						               
						                
						                Double noInvoiceAmount = hssfRow.getCell(7).getNumericCellValue();
						                xlsDto.setNoInvoiceAmount(noInvoiceAmount);
						                
						                OrderNopayList.add(xlsDto);
					            }
					    }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				return OrderNopayList;
	 }
	 
	 public Map<String,List> readAllXls(InputStream is) throws FileNotFoundException{
		 Map<String,List> map = new HashMap<String, List>();
			List<Inventory> inventoryList = new ArrayList<Inventory>();
			List<OnDelivery> OnDeliveryList = new ArrayList<OnDelivery>();
			List<OrderNopay> OrderNopayList = new ArrayList<OrderNopay>();
			//InputStream is = new FileInputStream(url);
		        HSSFWorkbook hssfWorkbook;
				try {
					hssfWorkbook = new HSSFWorkbook(is);
					
					// 循环工作表Sheet
					    for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				           
				            if (hssfSheet == null) {
				                continue;
				            }else{
				            	 //if("Inventory".equals(hssfSheet.getSheetName())){
				            	
				            	if("库存".equals(hssfSheet.getSheetName().trim())){
				            		   for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
								            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
								            if (hssfRow == null) {
		 					                    continue;
		 					                }
								            Inventory xlsDto = null;
						            		xlsDto = new Inventory();
							                HSSFCell repositoryName = hssfRow.getCell(0);
							                xlsDto.setRepositoryName(repositoryName+"");
							                
							                HSSFCell plasticItem = hssfRow.getCell(1);
							                xlsDto.setPlasticItem(plasticItem+"");
							                
							                HSSFCell unit = hssfRow.getCell(2);
							                xlsDto.setUnit(unit+"");
							                
							                if(hssfRow.getCell(3)!=null){
							                	 Double inventoryAmount = hssfRow.getCell(3).getNumericCellValue(); 
									                xlsDto.setInventoryAmount(inventoryAmount);
							                }
							               
							                if(hssfRow.getCell(4)!=null){
								                Double waitDeliverAmount = hssfRow.getCell(4).getNumericCellValue();
								                xlsDto.setWaitDeliverAmount(waitDeliverAmount);
							                }
							                
							                if(hssfRow.getCell(5)!=null){
								                Double reserveDeliverAmount = hssfRow.getCell(5).getNumericCellValue();
								                xlsDto.setReserveDeliverAmount(reserveDeliverAmount);
							                }
							                
							                if(hssfRow.getCell(6)!=null){
								                Double availableAmount = hssfRow.getCell(6).getNumericCellValue();
								                xlsDto.setAvailableAmount(availableAmount);
							                }
							                inventoryList.add(xlsDto);	
				            		   }
				            		   map.put("Inventory", inventoryList); 
				            	 }else if("到货单列表".equals(hssfSheet.getSheetName().trim())){
				            		 OnDelivery xlsDto = null;
				            		 for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
		 					                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
		 					                if (hssfRow == null) {
		 					                    continue;
		 					                }
		 					                xlsDto = new OnDelivery();
		 						                HSSFCell billID = hssfRow.getCell(2);
		 						                xlsDto.setBillID(billID+"");
		 						                HSSFCell date = hssfRow.getCell(3);
		 						                xlsDto.setDate(TransitionDate(date+""));
		 						                HSSFCell provider = hssfRow.getCell(4);
		 						                xlsDto.setProvider(provider+"");
		 						                HSSFCell plasticItem = hssfRow.getCell(5);
		 						                xlsDto.setPlasticItem(plasticItem+"");
		 						                
		 						                if(hssfRow.getCell(6)!=null){
		 							                Double amount=hssfRow.getCell(6).getNumericCellValue();
		 							                xlsDto.setAmount(amount);
		 						                }
		 						                
		 						                if(hssfRow.getCell(7)!=null){
		 							                Double originalPrice = hssfRow.getCell(7).getNumericCellValue();;
		 							                xlsDto.setOriginalPrice(originalPrice);
		 						                }
		 						                
		 						                if(hssfRow.getCell(8)!=null){
		 							                Double taxRate = hssfRow.getCell(8).getNumericCellValue();;
		 							                xlsDto.setTaxRate(taxRate);
		 						                }
		 						                
		 						                HSSFCell billType = hssfRow.getCell(9);
		 						                xlsDto.setBillType(billType+"");
		 						                
		 						                if(hssfRow.getCell(10)!=null){
		 							                Double notInInRepository = hssfRow.getCell(10).getNumericCellValue();;
		 							                xlsDto.setNotInInRepository(notInInRepository);
		 						                }
		 						                OnDeliveryList.add(xlsDto);  
				            		 }
				            		 map.put("OnDelivery", OnDeliveryList);           
								}else if("订单".equals(hssfSheet.getSheetName().trim())){
									OrderNopay xlsDto = null;
				            		 for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
		 					                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
		 					                if (hssfRow == null) {
		 					                    continue;
		 					                }
		 					               xlsDto = new OrderNopay();
							                HSSFCell customerNameString = hssfRow.getCell(0);
							                xlsDto.setCustomerName(customerNameString+"");
							                
							                HSSFCell salesman = hssfRow.getCell(1);
							                xlsDto.setSalesman(salesman+"");
							                
							                HSSFCell billID = hssfRow.getCell(2);
							                xlsDto.setBillID(billID+"");
							                
							                HSSFCell billDate = hssfRow.getCell(3);
							                xlsDto.setBillDate(billDate+"");
							                
							                HSSFCell plasticItem = hssfRow.getCell(4);
							                xlsDto.setPlasticItem(plasticItem+"");
							                
							                Double unfilledOrderAmount = hssfRow.getCell(5).getNumericCellValue();
							                xlsDto.setUnfilledOrderAmount(unfilledOrderAmount);
							                
							                if(hssfRow.getCell(6)!=null){
							                	 Double filledOrderAmount = hssfRow.getCell(6).getNumericCellValue();
									                xlsDto.setFilledOrderAmount(filledOrderAmount);
							                }
							                
							                Double noInvoiceAmount = hssfRow.getCell(7).getNumericCellValue();
							                xlsDto.setNoInvoiceAmount(noInvoiceAmount);
							                
							                OrderNopayList.add(xlsDto);
				            		 }
				            		 map.put("OrderNopay", OrderNopayList);     
								}
				            }
				          //  System.out.println("name-----"+hssfSheet.getSheetName());
					    }
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return map;
	 }
	 
				
}

