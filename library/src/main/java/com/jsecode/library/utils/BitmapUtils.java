package com.jsecode.library.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {
    /**
     * 从给定路径加载图片
     */
    public static Bitmap loadBitmap(String imgPath, Options options) {
        return BitmapFactory.decodeFile(imgPath, options);
    }

    /**
     * 从给定的路径加载图片，并指定是否自动旋转方向
     */
    public static Bitmap loadBitmap(String imgPath, boolean adjustOrientation, Options options) {
        if (!adjustOrientation) {
            return loadBitmap(imgPath, options);
        } else {
            Bitmap bm = loadBitmap(imgPath, options);
            int degree = 0;
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imgPath);
            } catch (IOException e) {
                e.printStackTrace();
                exif = null;
            }
            if (exif != null) {
                // 读取图片中相机方向信息
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                // 计算旋转角度
                switch (ori) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                    default:
                        degree = 0;
                        break;
                }
            }
            if (degree != 0) {
                // 旋转图片
                Matrix m = new Matrix();
                m.postRotate(degree);
                try {
                    Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                    bm.recycle();
                    bm = bm1;
                } catch (OutOfMemoryError e) {
                    Log.e("Utils", "设备内存不足无法进行图片旋转操作");
                }
            }
            return bm;
        }
    }

    /**
     * 系统图库图片Uri 转 图片实际存储路径
     *
     * @param mContext context
     * @param uri      uri
     * @return path
     */
    public static String getRealImagePath(Context mContext, Uri uri) {
        String fileName = null;
        if (uri != null) {
            if ("content".equals(uri.getScheme())) // content://开头的uri
            {
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    fileName = cursor.getString(column_index); // 取出文件路径
                    if (!fileName.startsWith("/mnt")) {
                        // 检查是否有”/mnt“前缀
                        fileName = "/mnt" + fileName;
                    }
                    cursor.close();
                }
            } else if ("file".equals(uri.getScheme())) // file:///开头的uri
            {
                fileName = uri.getPath();
            }
        }
        return fileName;
    }

    /**
     * Bitmap 保存为图片
     *
     * @param bmp  bitmap
     * @param path path
     * @return success or failed
     */
    public static boolean saveBitmapAsJpeg2File(Bitmap bmp, String path) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Log.i("WriteFile", "SD card is not avaiable/writeable right now.");
            return false;
        }
        FileOutputStream b = null;
        File file = new File(path);
        if (file.getParentFile().mkdirs()) {
            try {
                b = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (b != null) {
                        b.flush();
                        b.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 生成缩略图(释放原图)
     *
     * @param source 原图
     * @param width  目标宽度
     * @param height 目标高度
     * @return 缩略图
     */
    public static Bitmap extractMiniThumb(Bitmap source, int width, int height) {
        return extractMiniThumb(source, width, height, true);
    }

    /**
     * 生成缩略图
     *
     * @param source  原图
     * @param width   目标宽度
     * @param height  目标高度
     * @param recycle 是否释放原图
     * @return 缩略图
     */
    public static Bitmap extractMiniThumb(Bitmap source, int width, int height, boolean recycle) {
        if (source == null) {
            return null;
        }

        float scale;
        if (source.getWidth() < source.getHeight()) {
            scale = width / (float) source.getWidth();
        } else {
            scale = height / (float) source.getHeight();
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap miniThumbnail = transform(matrix, source, width, height, false);

        if (recycle && miniThumbnail != source) {
            source.recycle();
        }
        return miniThumbnail;
    }

    private static Bitmap transform(Matrix matrix, Bitmap source, int targetWidth, int targetHeight, boolean scaleUp) {
        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;
        if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
            /*
             * In this case the bitmap is smaller, at least in one dimension,
			 * than the target. Transform it by placing as much of the image as
			 * possible into the target and leaving the top/bottom or left/right
			 * (or both) black.
			 */
            Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b2);

            int deltaXHalf = Math.max(0, deltaX / 2);
            int deltaYHalf = Math.max(0, deltaY / 2);
            Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf + Math.min(targetWidth, source.getWidth()),
                    deltaYHalf + Math.min(targetHeight, source.getHeight()));
            int dstX = (targetWidth - src.width()) / 2;
            int dstY = (targetHeight - src.height()) / 2;
            Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight - dstY);
            c.drawBitmap(source, src, dst, null);
            return b2;
        }
        float bitmapWidthF = source.getWidth();
        float bitmapHeightF = source.getHeight();

        float bitmapAspect = bitmapWidthF / bitmapHeightF;
        float viewAspect = (float) targetWidth / targetHeight;

        if (bitmapAspect > viewAspect) {
            float scale = targetHeight / bitmapHeightF;
            if (scale < .9F || scale > 1F) {
                matrix.setScale(scale, scale);
            } else {
                matrix = null;
            }
        } else {
            float scale = targetWidth / bitmapWidthF;
            if (scale < .9F || scale > 1F) {
                matrix.setScale(scale, scale);
            } else {
                matrix = null;
            }
        }

        Bitmap b1;
        if (matrix != null) {
            // this is used for minithumb and crop, so we want to filter here.
            b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        } else {
            b1 = source;
        }

        int dx1 = Math.max(0, b1.getWidth() - targetWidth);
        int dy1 = Math.max(0, b1.getHeight() - targetHeight);

        Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth, targetHeight);

        if (b1 != source) {
            b1.recycle();
        }

        return b2;
    }

    /**
     * 转byte[]
     *
     * @param bitmap 源
     * @return byte[]
     */
    public static byte[] toByteArray(Bitmap bitmap) {
        byte[] result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                baos.flush();
                baos.close();

                result = baos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * from byte array
     *
     * @param bytes array
     * @return bitmap
     */
    public static Bitmap fromByteArray(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /**
     * Bitmap转为Base64字符串
     *
     * @param bitmap bitmap
     * @return Base64 String
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        byte[] bytes = toByteArray(bitmap);
        if (bytes != null) {
            try {
                result = Base64.encodeBytes(bytes, Base64.GZIP | Base64.URL_SAFE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * Base64字符串转为Bitmap
     *
     * @param base64Data 字符串
     * @return bitmap
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        return base64ToBitmap(base64Data, Base64.GZIP | Base64.URL_SAFE);
    }

    public static Bitmap base64ToBitmap(String base64Data, int option) {
        try {
            byte[] bytes = Base64.decode(base64Data, option);
            return fromByteArray(bytes);
        } catch (IOException e) {
            Logger.w(BitmapUtils.class, e);
        }
        return null;
    }

    /**
     * 缩小Bitmap至最大宽高 (若原图小于目标大小则返回原图,否则缩小后释放原图返回新图)
     *
     * @param bm        原图
     * @param maxWidth  目标宽度
     * @param maxHeight 目标高度
     * @return 拉伸后的bitmap
     */
    public static Bitmap constrainScaleBitmapToMaxSize(Bitmap bm, int maxWidth, int maxHeight) {
        float width = bm.getWidth();
        float height = bm.getHeight();

        if (width <= maxWidth && height <= maxHeight) {
            return bm;
        }

        float newWidth = 0;
        float newHeight = 0;
        if (width / maxWidth > height / maxHeight) {
            newWidth = maxWidth;
            newHeight = newWidth * height / width;
        } else {
            newHeight = maxHeight;
            newWidth = newHeight * width / height;
        }

        // 设置想要的大小
        // 计算缩放比例
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBmp = Bitmap.createBitmap(bm, 0, 0, (int) width, (int) height, matrix, true);
        bm.recycle();
        return newBmp;
    }
}
