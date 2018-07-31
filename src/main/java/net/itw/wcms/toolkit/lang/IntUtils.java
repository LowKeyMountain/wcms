package net.itw.wcms.toolkit.lang;

public class IntUtils {
	public static boolean intValueEquals(Integer i1, Integer i2) {
		if (i1 == null && i2 == null) {
			return true;
		}
		if (i1 == i2) {
			return true;
		}
		if (i1 != null && i2 != null && i1.intValue() == i2.intValue()) {
			return true;
		}
		return false;
	}
}
