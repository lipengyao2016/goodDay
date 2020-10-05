package com.tpw.goo.util;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ReflectUtils {
    public static Map<String, Object> getFieldMap(Object object) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<String,Object >();
        //迭代属性
        for(Field field : fields){
            String name = field.getName();
            String methodName = "get" + name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
                    .toUpperCase());
            // 调用getter方法获取属性值
//                 Method getter = object.getClass().getMethod(methodName);
//                 String value =  getter.invoke(object)+"";

            //通过get方法直接获取属性值
            field.setAccessible(true);
            Object value = field.get(object);
            if (value != null){
                map.put(name, value);
            }
//            System.out.println("字段名："+name);
//            System.out.println("字段值："+field.get(object));
//            System.out.println("字段java语言修饰符："+field.getModifiers());
//            System.out.println("字段类型："+field.getType());
//            System.out.println("");
        }

        return map;
    }
}
