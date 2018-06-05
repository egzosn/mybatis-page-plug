package com.egzosn.mybatis.page.utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字段工具类，带缓存
 * @author egan
 *         email egzosn@gmail.com
 *         date 2018/5/16.18:48
 */
public final class FieldUtils {

    private static final Map<String,  Field> strongFieldCache = new ConcurrentHashMap<String,  Field>();

    /**
     * 查找类对应的字段
     * @param clazz 类
     * @param fieldName 字段名
     * @return 字段
     */
    public static final Field findField(Class clazz, String fieldName){
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查找类对应的字段
     * @param clazz 类
     * @param fieldName 字段名

     * @return 字段
     */
    public static final Field findFieldCaches(Class clazz, String fieldName){
        String key = clazz.getName() + "#" + fieldName;
        Field field = strongFieldCache.get(key);
        if (null == field){
            field = findField(clazz, fieldName);
            strongFieldCache.put(key, field);
        }
        return field;
    }

    /**
     * 获取字段值
     * @param field 字段
     * @param obj 对象

     */
    public static final Object getField(Field field, Object obj){
        field.setAccessible(true);
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            field.setAccessible(false);
        }
        return null;
    }
    /**
     * 设置字段值
     * @param field 类
     * @param obj 对象
     * @param value 值
     */
    public static final void setField(Field field, Object obj ,Object value){
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            field.setAccessible(false);
        }

    }


}
