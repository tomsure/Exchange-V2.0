package com.ruizton.main.Enum;

public class SubTypeEnum {
	public static final int A1 = 1;//
	public static final int A2 = 2;//
	public static final int A3 = 3;//
	public static final int A4 = 4;//
	public static final int A5 = 5;//
	public static final int A6 = 6;//
	public static final int A7 = 7;//
	public static final int A8 = 8;//
	public static final int A9 = 9;//
	public static final int A10 = 10;//
        
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case A1:
			name = "科技";
			break;
		case A2:
			name = "公益";
			break;
		case A3:
			name = "农业";
			break;
		case A4:
			name = "出版";
			break;
		case A5:
			name = "娱乐";
			break;
		case A6:
			name = "艺术";
			break;
		case A7:
			name = "商铺";
			break;
		case A8:
			name = "房产";
			break;
		case A9:
			name = "虚拟数字货币";
			break;	
		case A10:
			name = "其他";
			break;	
		}
		return name;
	}
    
}
