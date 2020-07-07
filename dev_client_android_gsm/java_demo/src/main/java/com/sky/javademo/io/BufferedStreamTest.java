package com.sky.javademo.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author yuandunbin
 * @date 2020/4/28
 */
public class BufferedStreamTest {
    private static final byte[] byteArray = {
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
    };


    public static void main(String[] args) {
//        bufferedOutPutStream();
        bufferedInputStream();
    }

    private static void bufferedInputStream() {
        File file = new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/BufferedStreamTest.txt");
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            for (int i = 0; i < 10; i++) {
                if (bufferedInputStream.available() > 0) {
                    System.out.println(byteToString((byte) bufferedInputStream.read()));
                }
            }
            bufferedInputStream.mark(1);
            bufferedInputStream.skip(1);

            byte[] bytes = new byte[1024];
            int read = bufferedInputStream.read(bytes, 0, bytes.length);
            System.out.println("剩余的有效字节数 ： " +read + "");
            printByteValue(bytes);
            
            bufferedInputStream.reset();
            int read1 = bufferedInputStream.read(bytes, 0, bytes.length);
            System.out.println("剩余的有效字节数 ： " +read1 + "");
            printByteValue(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printByteValue(byte[] bytes) {
        for (byte b : bytes) {
            if (b != 0) {
                System.out.println(byteToString(b));
            }
        }
    }

    private static String byteToString(byte b) {
        byte[] bytes = {b};
        return new String(bytes);
    }

    private static void bufferedOutPutStream() {
        try {
            File file = new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/BufferedStreamTest.txt");

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(byteArray[0]);
            bos.write(byteArray, 1, byteArray.length - 1);
            bos.flush();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
