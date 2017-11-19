package net.itw.wcms.toolkit.lang;

import org.apache.commons.lang3.mutable.MutableBoolean;

public class BooleanUtils {
	public static boolean tryBooleanParse(String value, MutableBoolean oBool) {
		try {
			boolean i = Boolean.valueOf(value).booleanValue();

			oBool.setValue(i);

			return true;
		} catch (Throwable localThrowable1) {
		}
		return false;
	}
}
