package com.nkang.kxmoment.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;

import com.nkang.kxmoment.baseobject.BillOfSell;

public class BillOfSellPoi {

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
}


