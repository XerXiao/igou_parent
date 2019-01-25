package com.xer.igou.util;

import java.lang.reflect.Field;
import java.util.*;

public class XObjectUtils {


    /**
     * 对象集合转为map list集合
     * @param obj
     * @return
     */
    public static List<Map<String,Object>> object2MapList(List obj,Class c) {
        List<Map<String,Object>> result = result = new ArrayList<>();
        try {
            Class<?> clazz = Class.forName(c.getName());
            Field[] fields = clazz.getDeclaredFields();
            List<Field> list = Arrays.asList(fields);
            for (Object o : obj) {
                if(o != null) {
                    Map<String, Object> map = new HashMap<>();
                    for (Field field : list) {
                        field.setAccessible(true);
                        map.put(field.getName(),field.get(o));
                    }
                    result.add(map);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
