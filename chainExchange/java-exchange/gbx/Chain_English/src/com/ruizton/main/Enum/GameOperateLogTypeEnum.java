package com.ruizton.main.Enum;

public class GameOperateLogTypeEnum {
	public static final int ZHONG = 1;//
	public static final int SHOU_OWNER = 2;//
	public static final int BUY = 3;//
	public static final int OPEN = 4;//
	public static final int SHOU_INTROL = 5;//
	public static final int WATER = 6;//
	public static final int KILL = 7;//
	public static final int BUYKILL = 8;//
	
	public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case ZHONG:
			name = "种豆";
			break;
		case SHOU_OWNER:
			name = "收豆_本人种植";
			break;
		case BUY:
			name = "租用土地";
			break;
		case OPEN:
			name = "翻新土地";
			break;	
		case SHOU_INTROL:
			name = "收豆_推荐奖励";
			break;
		case WATER:
			name = "浇水";
			break;
		case KILL:
			name = "除虫";
			break;
		case BUYKILL:
			name = "购买杀虫剂";
			break;		
		}
		return name;
	}
}
