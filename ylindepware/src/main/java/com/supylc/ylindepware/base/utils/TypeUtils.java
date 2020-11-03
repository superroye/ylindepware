package com.supylc.ylindepware.base.utils;

/**
 * Created by Supylc on 2020/11/3.
 */
public class TypeUtils {

    public static Class getJavaObjectClass(Class clazz) {
        if (clazz == int.class) {
            return Integer.class;
        } else if (clazz == boolean.class) {
            return Boolean.class;
        } else if (clazz == float.class) {
            return Float.class;
        } else if (clazz == double.class) {
            return Double.class;
        } else if (clazz == short.class) {
            return Short.class;
        } else if (clazz == byte.class) {
            return Byte.class;
        } else if (clazz == long.class) {
            return Long.class;
        }
        return clazz;
    }
}
