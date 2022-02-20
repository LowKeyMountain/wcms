package net.itw.wcms.x27.utils;

import java.util.HashMap;
import java.util.Map;



public class JedisUtils {

    private static Map<String, Map<String, Object>> jedis = new HashMap<>();

    /**
     * 以map形式存放对象.
     *
     * @param key
     * @param field
     * @param obj
     *
     */
    public static void setObject(String key, String field, Object obj) {
        try {
            if (!jedis.containsKey(key)) {
                jedis.put(key, new HashMap<String, Object>());
            }
            jedis.get(key).put(field, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象.
     *
     * @param key
     * @param field
     * @return
     */
    public static Object getObject(String key, String field) {
        Object obj = null;
        try {
            if (!jedis.containsKey(key)) {
                return null;
            }
            obj = jedis.get(key).get(field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    public static void main(String[] args) {
        setObject("test","test123","");
        System.out.println(getObject("test","test123"));

    }

}
