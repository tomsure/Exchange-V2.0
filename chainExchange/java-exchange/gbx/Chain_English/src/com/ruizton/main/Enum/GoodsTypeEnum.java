package com.ruizton.main.Enum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.model.Fgoodtype;
import com.ruizton.main.service.admin.GoodtypeService;

public class GoodsTypeEnum {
	@Autowired
	private static GoodtypeService goodTypeService;
//    public static final int type4 = 4;
//    public static final int type5 = 5;
//    public static final int type6 = 6;
    
    public static String getEnumString(int value) {
    	
    	String name = "";
    	
    	List<Fgoodtype> list = goodTypeService.findAll();
    	
    	for (Fgoodtype fgoodtype : list) {
			if(fgoodtype.getFid() == value){
				name = fgoodtype.getFname();
			}
		}
    	
		return name;
	}
    
}
