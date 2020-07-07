package com.free.business.glide;

import android.graphics.Bitmap;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author yuandunbin782
 * @ClassName Tool
 * @Description
 * @date 2020/6/8
 */
public class Tool {
    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 加密后的报文
     * @return 最终的效果：唯一 加密的 ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a4668
     */
    public static String getSHA256StrJava(String str) {
        String encodeStr = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] digest) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i= 0;i<digest.length;i++){
            String temp = Integer.toHexString(digest[i] & 0xFF);
            if (temp.length() == 1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static String checkNotEmpty(String str){
        if (TextUtils.isEmpty(str)){
            throw new IllegalArgumentException("Must not be null or empty!");
        }
        return str;
    }

    public static void checkNotEmpty(Bitmap bitmap) {
        if (null == bitmap) {
            throw new IllegalArgumentException("Must not be empty. 传递进来的值bitmap:"+bitmap+"是null");
        }
    }

    public static void checkNotEmpty(ImageView imageView) {
        if (imageView == null){
            throw new IllegalArgumentException("Must not be empty. 传递进来的值imageView:"+imageView+"是null");
        }
    }

    public static void assertMainThread() {
        if (!isOnMainThread()){
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    private static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
