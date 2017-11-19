package net.itw.wcms.toolkit.lang;

import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.tomcat.util.MutableInteger;

@SuppressWarnings("deprecation")
public class Int32 {
	public static boolean tryParse(String value, MutableInt oInt) {
		try {
			int i = Integer.valueOf(value).intValue();

			oInt.setValue(i);

			return true;
		} catch (Throwable localThrowable1) {
			oInt.setValue(0);
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean tryParse(String value, MutableInteger oInt) {
		try {
			int i = Integer.valueOf(value).intValue();

			oInt.set(i);

			return true;
		} catch (Throwable localThrowable1) {
			oInt.set(0);
		}

		return false;
	}
}
