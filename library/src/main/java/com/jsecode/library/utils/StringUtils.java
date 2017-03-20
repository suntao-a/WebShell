package com.jsecode.library.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class StringUtils {

    /**
     * String转Int
     *
     * @param s string
     * @return int
     */
    public static int intValue(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Empty String
     *
     * @param s string
     * @return isEmpty
     */
    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    /**
     * Empty or Blank
     *
     * @param s string
     * @return isEmptyOrBlank
     */
    public static boolean isEmptyOrBlank(String s) {
        return isEmpty(s) || s.trim().length() <= 0;
    }

    /**
     * MD5 加密
     *
     * @param str 字符串
     * @return 加密串
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            Logger.e(StringUtils.class, "NoSuchAlgorithmException caught!");
            return "NoSuchAlgorithmException";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        StringBuilder md5StrBuff = new StringBuilder();

        for (byte aByteArray : byteArray) {
            if (Integer.toHexString(0xFF & aByteArray).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & aByteArray));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
        }

        return md5StrBuff.toString();
    }


    /**
     * 字符串 拼接二进制和
     *
     * @param str 源字符串
     * @return 源字符串+二进制和
     */
    public static String strAppendSum(String str) {
        if (str == null) {
            return null;
        }

        long sum = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            sum += c;
        }
        String sumStr = Long.toHexString(sum).toUpperCase(Locale.getDefault());
        if (sumStr.length() < 2) {
            sumStr = "0" + sumStr;
        } else if (sumStr.length() > 2) {
            sumStr = sumStr.substring(sumStr.length() - 2);
        }

        return str + sumStr;
    }

    /**
     * 格式化文件大小，显示KB/MB/GB
     *
     * @param size long size
     * @return String
     */

    @SuppressLint("DefaultLocale")
    public static String formatFileSize(long size) {
        long SIZE_KB = 1024;
        long SIZE_MB = SIZE_KB * 1024;
        long SIZE_GB = SIZE_MB * 1024;

        if (size < SIZE_KB) {
            return String.format("%d B", (int) size);
        } else if (size < SIZE_MB) {
            return String.format("%.2f KB", (float) size / SIZE_KB);
        } else if (size < SIZE_GB) {
            return String.format("%.2f MB", (float) size / SIZE_MB);
        } else {
            return String.format("%.2f GB", (float) size / SIZE_GB);
        }
    }

}
