package com.nkang.kxmoment.baseobject;

import java.util.ArrayList;

public class Market {
	ArrayList<String[]>  arrayList=new ArrayList<String[]>();
	public Market(){
		arrayList.add(new String[]{"总经理","王素萍","13606007258"});
		arrayList.add(new String[]{"副总经理","邓立铭","13320204222"});
		arrayList.add(new String[]{"重庆销售","郝海涛","15123331522"});
		arrayList.add(new String[]{"重庆销售","胡贵花","13996213531"});
		arrayList.add(new String[]{"重庆销售","罗成洪","13996213513"});
		arrayList.add(new String[]{"重庆销售","温小兵","18160042561"});
		arrayList.add(new String[]{"重庆销售","马家勇","17780647932"});
		arrayList.add(new String[]{"重庆销售","罗浩","13996015042"});
		arrayList.add(new String[]{"助理总经理","陈博","13883228651"});
		arrayList.add(new String[]{"助理总经理","段阳","15308228368"});
		arrayList.add(new String[]{"采购经理","郑仁利","13883990710"});
		arrayList.add(new String[]{"采购经理","罗斯威","13883743587"});
	}
	public String[] getMarket(String name){
		for(String[] market:arrayList){
			if(market[1].equals(name)){
				return market;
			}
		}
		return new String[]{"副总经理","邓立铭","13320204222"};
	}
}
