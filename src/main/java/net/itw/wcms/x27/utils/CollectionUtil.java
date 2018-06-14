package net.itw.wcms.x27.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CollectionUtil {

	public static List toList(Set set) {
		if (set == null) {
			return null;
		}
		if (set.size() == 0) {
			return new ArrayList();
		}
		List list = new ArrayList();
		Iterator itor = set.iterator();
		while (itor.hasNext()) {
			Object obj = itor.next();
			list.add(obj);
		}
		return list;
	}

	public static boolean isEmpty(Collection collection) {
		if (null == collection || collection.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
