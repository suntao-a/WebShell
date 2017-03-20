package com.jsecode.library.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangsx on 15/10/14.
 */
public class ValidationUtils {

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
    public static final String REGEX_PHONENUMBER = "^[1][3-8]\\d{9}";
    public static final String REGEX_TELEPHONE = "\\d{3}-\\d{8}|\\d{4}-\\d{7}|\\d{4}-\\d{8}|\\d{11}|\\d{12}";
    public static final String REGEX_PASSWORD = "[a-zA-Z0-9_]{6,30}$";
    public static final String REGEX_URL = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public static final String REGEX_IP = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    public static final String REGEX_IDCARD_15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    public static final String REGEX_IDCARD_18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])(\\d{4}|\\d{3}(\\d|X|x))$";
    public static final String REGEX_DOMAIN = "^[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?";

    /**
     * 正则表达式校验
     *
     * @param str   源字符串
     * @param regex 正则表达式
     * @return valid
     */
    public static boolean validateRegex(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证Email
     *
     * @param str string
     * @return valid
     */
    public static boolean validateEmail(String str) {
        return validateRegex(str, REGEX_EMAIL);
    }

    /**
     * 验证手机号
     *
     * @param str string
     * @return valid
     */
    public static boolean validatePhonenumber(String str) {
        return validateRegex(str, REGEX_PHONENUMBER);
    }

    /**
     * 验证电话号码
     *
     * @param str string
     * @return valid
     */
    public static boolean validateTelephone(String str) {
        return validateRegex(str, REGEX_TELEPHONE);
    }

    /**
     * 验证密码(6-30位数字字母下划线)
     *
     * @param str string
     * @return valid
     */
    public static boolean validatePassword(String str) {
        return validateRegex(str, REGEX_PASSWORD);
    }

    /**
     * 验证URL
     *
     * @param str string
     * @return valid
     */
    public static boolean validateUrl(String str) {
        return validateRegex(str, REGEX_URL);
    }

    /**
     * 验证IP地址
     *
     * @param str string
     * @return valid
     */
    public static boolean validateIpAddress(String str) {
        return validateRegex(str, REGEX_IP);
    }

    public static boolean validateDomain(String str) {
        return validateRegex(str, REGEX_DOMAIN);
    }

    /**
     * 验证身份证号
     *
     * @param str string
     * @return valid
     */
    public static boolean validateIdCardNo(String str) {
        return validateRegex(str, REGEX_IDCARD_15) || validateRegex(str, REGEX_IDCARD_18);
    }

    /**
     * 验证昵称(4-20字节)
     *
     * @param str string
     * @return valid
     */
    public static boolean validateNickname(String str) {
        if (str == null) {
            return false;
        }
        try {
            byte[] bs = str.getBytes("UTF-8");
            return bs.length >= 4 && bs.length <= 20;
        } catch (UnsupportedEncodingException e) {
            Logger.e(ValidationUtils.class, e);
            return false;
        }
    }
}
