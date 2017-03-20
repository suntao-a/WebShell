package com.jsecode.library.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReflectUtils {

    private static final String TYPE_NAME_PREFIX = "class ";

    /**
     * 通过Type获取对象class
     *
     * @param type type
     * @return className
     */
    public static String getClassName(Type type) {
        if (type == null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        return className;
    }

    /**
     * 通过Type获取对象class
     *
     * @param type type
     * @return class
     * @throws ClassNotFoundException
     */
    public static Class<?> getClass(Type type) throws ClassNotFoundException {
        String className = getClassName(type);
        if (className == null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredFields
     *
     * @param object : 子类对象
     * @return 父类中的属性对象
     */
    public static List<Field> getDeclaredFields(Object object) {
        List<Field> fields = new ArrayList<Field>();
        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] f = clazz.getDeclaredFields();
                Collections.addAll(fields, f);
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }

        return fields;
    }

    /**
     * 通过Type创建对象
     *
     * @param type type
     * @return instance
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Object newInstance(Type type) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = getClass(type);
        if (clazz == null) {
            return null;
        }
        return clazz.newInstance();
    }

    /**
     * 获取泛型对象的泛型化参数
     *
     * @param object 对象
     * @return Type数组
     */
    public static Type[] getParameterizedTypes(Object object) {
        Type superclassType = object.getClass().getGenericSuperclass();
        if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
            return null;
        }
        return ((ParameterizedType) superclassType).getActualTypeArguments();
    }

    /**
     * 检查对象是否存在默认构造函数
     *
     * @param clazz class
     * @return 存在则true
     */
    public static boolean hasDefaultConstructor(Class<?> clazz) throws SecurityException {
        Class<?>[] empty = {};
        try {
            clazz.getConstructor(empty);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    /**
     * 获取指定类型中的特定field类型
     *
     * @param clazz class
     * @param name  fieldName
     * @return class
     */
    public static Class<?> getFieldClass(Class<?> clazz, String name) {
        if (clazz == null || name == null || name.isEmpty()) {
            return null;
        }
        name = name.toLowerCase();
        Class<?> propertyClass = null;
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals(name)) {
                propertyClass = field.getType();
                break;
            }
        }
        return propertyClass;
    }

    /**
     * 获取指定类型中的特定method返回类型
     *
     * @param clazz class
     * @param name  methodName
     * @return method returnType
     */
    public static Class<?> getMethodReturnType(Class<?> clazz, String name) {
        if (clazz == null || name == null || name.isEmpty()) {
            return null;
        }

        name = name.toLowerCase();
        Class<?> returnType = null;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(name)) {
                returnType = method.getReturnType();
                break;
            }
        }

        return returnType;
    }

    /**
     * 根据字符串标示获取枚举常量
     *
     * @param clazz enum class
     * @param name  enum name
     * @return enum constant
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object getEnumConstant(Class<?> clazz, String name) {
        if (clazz == null || name == null || name.isEmpty()) {
            return null;
        }
        return Enum.valueOf((Class<Enum>) clazz, name);
    }
}
