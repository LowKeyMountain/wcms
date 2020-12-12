package net.itw.wcms.toolkit.lang;

import java.text.DecimalFormat;

public class FloatUtils {

	public static final DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
	
	public static String format(Float float1){
		if (float1 == null || Float.valueOf("0").compareTo(float1) == 0) {
			return "0.00";
		}
		return decimalFormat.format(float1);
	}
}
