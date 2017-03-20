package com.jsecode.library.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhangcheng on 15/6/12.
 */
public class StreamUtils {

    /**
     * 将流另存为文件
     *
     * @param is      输入流
     * @param outfile 输出位置
     */
    public static void streamSaveAsFile(InputStream is, File outfile) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outfile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

        } catch (Exception e) {
            Logger.e(StreamUtils.class, e);
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
                if (fos != null)
                    fos.close();
            } catch (Exception e2) {
                Logger.e(StreamUtils.class, e2);
            }
        }
    }

    /**
     * 读取输入流为String
     *
     * @param in 输入流
     * @return 字符串
     * @throws IOException
     */
    static public String streamToString(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    /**
     * 读取输入流为byte数组
     *
     * @param is 输入流
     * @return byte数组
     * @throws IOException
     */
    public static byte[] stream2Byte(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len;
        byte[] b = new byte[1024];
        while ((len = is.read(b, 0, b.length)) != -1) {
            baos.write(b, 0, len);
        }
        return baos.toByteArray();
    }


    /**
     * byte数组 转为 InputStream
     *
     * @param b 字节数组
     * @return InputStream
     * @throws Exception
     */
    public static InputStream byte2InputStream(byte[] b) throws Exception {
        return new ByteArrayInputStream(b);
    }


}