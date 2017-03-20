package com.jsecode.library.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtils {

    /**
     * Java文件操作 获取文件扩展名
     *
     * @param filename 文件名
     * @return 扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     *
     * @param filename 文件名
     * @return 不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 获取文件内容字节数组
     *
     * @param filePath path
     * @return byte[]
     */
    public static byte[] file2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            Logger.e(FileUtils.class, e);
        }
        return buffer;
    }


    /**
     * 文件拷贝
     *
     * @param sourceFile 源地址
     * @param targetFile 目标地址
     * @throws IOException IO异常
     */
    public static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }


    /**
     * 文件夹(包括其中文件)拷贝
     *
     * @param sourceDir 源地址
     * @param targetDir 目标地址
     * @return success or failed
     * @throws IOException IO异常
     */
    public static boolean copyDirectiory(String sourceDir, String targetDir)
            throws IOException {
        // 新建目标目录
        if ((new File(targetDir)).mkdirs()) {
            // 获取源文件夹当前下的文件或目录
            File[] file = (new File(sourceDir)).listFiles();
            for (File aFile : file) {
                if (aFile.isFile()) {
                    // 目标文件
                    File targetFile = new File(
                            new File(targetDir).getAbsolutePath() + File.separator
                                    + aFile.getName());
                    copyFile(aFile, targetFile);
                }
                if (aFile.isDirectory()) {
                    // 准备复制的源文件夹
                    String dir1 = sourceDir + "/" + aFile.getName();
                    // 准备复制的目标文件夹
                    String dir2 = targetDir + "/" + aFile.getName();
                    copyDirectiory(dir1, dir2);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 文件编码转换
     *
     * @param srcFileName  源文件
     * @param destFileName 目标文件
     * @param srcCoding    源编码
     * @param destCoding   目标编码
     * @throws IOException IO异常
     */
    public static void copyFile(File srcFileName, File destFileName,
                                String srcCoding, String destCoding) throws IOException {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    srcFileName), srcCoding));
            bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(destFileName), destCoding));
            char[] cBuf = new char[1024 * 5];
            int len = cBuf.length;
            int off = 0;
            int ret;
            while ((ret = br.read(cBuf, off, len)) > 0) {
                off += ret;
                len -= ret;
            }
            bw.write(cBuf, 0, off);
            bw.flush();
        } finally {
            if (br != null)
                br.close();
            if (bw != null)
                bw.close();
        }
    }

    /**
     * 删除文件/文件夹
     *
     * @param filepath 文件或文件夹地址
     * @return success
     * @throws IOException IO异常
     */
    public static boolean del(String filepath) throws IOException {
        File f = new File(filepath);// 定义文件路径
        if (f.exists()) {
            if (f.isDirectory()) {// 判断是文件还是目录
                boolean success = true;
                if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
                    success = f.delete();
                } else {// 若有则把文件放进数组，并判断是否有下级目录
                    File delFile[] = f.listFiles();
                    int i = f.listFiles().length;
                    for (int j = 0; j < i; j++) {
                        if (delFile[j].isDirectory()) {
                            success &= del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                        }
                        success &= delFile[j].delete();// 删除文件
                    }
                }
                return success;
            } else {
                return f.delete();
            }
        } else {
            return false;
        }
    }

    /**
     * 通过Uri获取文件实际地址
     *
     * @param context context
     * @param uri     uri
     * @return path
     */
    public static String getFilePathByContentResolver(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        String filePath = null;
        if (null == c) {
            throw new IllegalArgumentException(
                    "Query on " + uri + " returns null result.");
        }
        try {
            if ((c.getCount() != 1) || !c.moveToFirst()) {
            } else {
                filePath = c.getString(
                        c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            }
        } finally {
            c.close();
        }
        return filePath;
    }

    /**
     * 关闭IO
     *
     * @param closeables
     */
    public static void closeIO(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
