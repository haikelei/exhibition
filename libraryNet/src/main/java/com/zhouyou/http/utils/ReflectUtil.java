package com.zhouyou.http.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author: lujialei
 * @date: 2018/9/30
 * @describe:
 */


public class ReflectUtil {

    /**
     * 获取属性名数组
     * */
    public static String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
//            System.out.println(fields[i].getType());
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }

    /* 根据属性名获取属性值
    * */
    public static Object getFieldValueByName(String fieldName, Object o) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        try {
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            try {
                Field field = o.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object v = field.get(o);
                if(v instanceof Integer || v instanceof Short || v instanceof Long){
                    int i = (int) v;
                    if (i==0){
                        return null;
                    }
                }
                return v;
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            return null;
        }
    }


}
