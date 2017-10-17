package org.yanzi.constant;

public class Constant {
	//Btn的标识
	public static final int BTN_FLAG_MESSAGE = 0x01;
	public static final int BTN_FLAG_CONTACTS = 0x01 << 1;
	public static final int BTN_FLAG_NEWS = 0x01 << 2;
	public static final int BTN_FLAG_SETTING = 0x01 << 3;

	//Fragment的标识
	public static final String FRAGMENT_FLAG_MESSAGE = "首页";
	public static final String FRAGMENT_FLAG_CONTACTS = "最新动态";
	public static final String FRAGMENT_FLAG_NEWS = "缉查记录";
	public static final String FRAGMENT_FLAG_SETTING = "设置";
	public static final String FRAGMENT_FLAG_PAGE_CAR_CHECK = "车辆缉查";
	public static final String FRAGMENT_FLAG_PAGE_CAR_CHECK_RESULT_INFO = "识别结果";
	public static final String FRAGMENT_FLAG_SIMPLE = "simple";


}
