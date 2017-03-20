package com.jsecode.library.utils;

/**
 * 拼音工具类
 *
 * @author lsf
 */
public class PingYinUtils {
//    /**
//     * 将字符串中的中文转化为拼音,其他字符不变
//     *
//     * @param inputString 中文字符串
//     * @return 拼音
//     */
//    public static String getPingYin(String inputString) {
//        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        format.setVCharType(HanyuPinyinVCharType.WITH_V);
//
//        char[] input = inputString.trim().toCharArray();
//        String output = "";
//
//        try {
//            for (char anInput : input) {
//                if (Character.toString(anInput).matches("[\\u4E00-\\u9FA5]+")) {
//                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(anInput, format);
//                    output += temp[0];
//                } else
//                    output += Character.toString(anInput);
//            }
//        } catch (BadHanyuPinyinOutputFormatCombination e) {
//            e.printStackTrace();
//        }
//        return output;
//    }
//
//    /**
//     * 获取汉字串拼音首字母，英文字符不变
//     *
//     * @param chinese 汉字串
//     * @return 汉语拼音首字母
//     */
//    public static String getFirstSpell(String chinese) {
//        StringBuilder pyBuilder = new StringBuilder();
//        char[] arr = chinese.toCharArray();
//        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
//        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        for (char anArr : arr) {
//            if (anArr > 128) {
//                try {
//                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(anArr, defaultFormat);
//                    if (temp != null) {
//                        pyBuilder.append(temp[0].charAt(0));
//                    }
//                } catch (BadHanyuPinyinOutputFormatCombination e) {
//                    e.printStackTrace();
//                }
//            } else {
//                pyBuilder.append(anArr);
//            }
//        }
//        return pyBuilder.toString().replaceAll("\\W", "").trim();
//    }
//
//    /**
//     * 获取汉字串拼音，英文字符不变
//     *
//     * @param chinese 汉字串
//     * @return 汉语拼音
//     */
//    public static String getFullSpell(String chinese) {
//        StringBuilder pyBuilder = new StringBuilder();
//        char[] arr = chinese.toCharArray();
//        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
//        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        for (char anArr : arr) {
//            if (anArr > 128) {
//                try {
//                    String[] s = PinyinHelper.toHanyuPinyinStringArray(anArr, defaultFormat);
//                    pyBuilder.append(s == null ? anArr : s[0]);
//                } catch (BadHanyuPinyinOutputFormatCombination e) {
//                    e.printStackTrace();
//                }
//            } else {
//                pyBuilder.append(anArr);
//            }
//        }
//        return pyBuilder.toString();
//    }

}
