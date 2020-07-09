package com.sky.javademo.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author yuandunbin
 * @date 2020/5/4
 */
public class DataStreamTest {
    public static void main(String[] args) throws Exception {
        testDataOutPutStream();
        testDataInputStream();
    }

    private static void testDataOutPutStream() throws Exception {
        File file = new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/tataStreamTest.txt");
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        out.writeBoolean(false);
        out.writeByte((byte) 0x41);
        out.writeChar((char) 0x4243);
        out.writeShort((short) 0x4445);
        out.writeInt(0x12345678);
        out.writeLong(0x987654321L);
//
        out.writeUTF("abcdefghijklmnopqrstuvwxyz严12");
//        out.writeLong(0x023433L);
        out.close();
    }

    private static void testDataInputStream() throws Exception {
        File file = new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/tataStreamTest.txt");
        DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        System.out.println(input.readBoolean());
        System.out.println(byteToHexString(input.readByte()));
        System.out.println(charToHexString(input.readChar()));
        System.out.println(shortToHexString(input.readShort()));
        System.out.println(Integer.toHexString(input.readInt()));
        System.out.println(Long.toHexString(input.readLong()));
        System.out.println(input.readUTF());
        input.close();
    }

    // 打印byte对应的16进制的字符串
    private static String byteToHexString(byte val) {
        return Integer.toHexString(val & 0xff);
    }

    // 打印char对应的16进制的字符串
    private static String charToHexString(char val) {
        return Integer.toHexString(val);
    }

    // 打印short对应的16进制的字符串
    private static String shortToHexString(short val) {
        return Integer.toHexString(val & 0xffff);
    }
}
