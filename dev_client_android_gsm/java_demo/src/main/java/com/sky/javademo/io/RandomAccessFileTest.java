package com.sky.javademo.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author yuandunbin
 * @date 2020/5/1
 */
public class RandomAccessFileTest {
    private final static File file = new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/raf.txt");

    public static void main(String[] args) throws Exception {
//        testWrite();
        testRead();
    }

    /**
     * 从文件中读取内容
     * 这里我们要清楚现在文件中有什么内容、而且还要清楚这些内容起始字节下标、长度
     *
     * @throws IOException
     */
    private static void testRead() throws IOException {
        /*
         * 对文件中内容简单说明：
         * 1、从0到1000	为空
         * 2、从1001到1100是100个1
         * 3、从1101到5000是空
         * 4、从5001到5200是字符'a'
         * 5、从5201到10000是空
         * 6、从10001到10011是字符串"陈华应"
         * 7、从10012到10023是"aabcde"
         */
        RandomAccessFile accessFile = new RandomAccessFile(file, "r");
        //可按照自己想读取的东西所在的位置、长度来读取

        //读取"享学课堂"
        accessFile.seek(10000);
        System.out.println(accessFile.readUTF());

        //读取100个字符'a'
        accessFile.seek(5000);
        byte[] bytes = new byte[200];
        accessFile.read(bytes);
        System.out.println(new String(bytes));

        //读取100个1
        byte[] bytes1 = new byte[100];
        accessFile.seek(5000);
        accessFile.read(bytes1, 0, 100);
        for (byte b : bytes1) {
            System.out.println(b);
        }

        //读取字符'aabcde'
        byte[] bytes2 = new byte[12];
        accessFile.seek(10014);
        accessFile.read(bytes2);
        System.out.println(new String(bytes2));
    }

    /**
     * 向文件写入内容
     * @throws Exception
     */
    private static void testWrite() throws Exception {
        //删除已有文件
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        //不会改变文件大小，但是它会将下一个字符的写入位置标识为10000，也就是说此后写入内容从10001开始
        randomAccessFile.seek(10000);
        printFileLength(randomAccessFile);

        randomAccessFile.setLength(10000);
        System.out.println("oo");
        printFileLength(randomAccessFile);        //result: 0
        System.out.println("xx");

        //每个汉子占3个字节、写入字符串的时候会有一个记录写入字符串长度的两个字节
        randomAccessFile.writeUTF("享学课堂");
        printFileLength(randomAccessFile);        //result: 10014

        //每个字符占两个字节
        randomAccessFile.writeChar('a');
        randomAccessFile.writeChars("abcde");
        printFileLength(randomAccessFile);        //result: 10026

        //再从“文件指针”为5000的地方插一个长度为100、内容全是'a'的字符数组
        //这里file长依然是10026、因为他是从“文件指针”为5000的地方覆盖后面
        //randomAccessFile、下标并没有超过文件长度
        randomAccessFile.seek(5000);
        char[] cbuf = new char[100];
        for (int i = 0; i < cbuf.length; i++) {
            cbuf[i] = 'a';
            randomAccessFile.writeChar(cbuf[i]);
        }
        printFileLength(randomAccessFile);    //result:  10026

        //再从“文件指针”为1000的地方插入一个长度为100、内容全是a的字节数组
        //这里file长依然是10026、因为他是从“文件指针”为5000的地方覆盖后面
        //的200个字节、下标并没有超过文件长度
        byte[] bbuf = new byte[100];
        for (int i = 0; i < bbuf.length; i++) {
            bbuf[i] = 1;
        }
        randomAccessFile.seek(5000);
        randomAccessFile.writeBytes(new String(bbuf));
        printFileLength(randomAccessFile);
    }

    /**
     * 打印文件长度
     *
     * @param rsfWriter 指向文件的随机文件流
     * @throws IOException
     */
    private static void printFileLength(RandomAccessFile rsfWriter)
            throws IOException {
        System.out.println("file length: " + rsfWriter.length() + "  file pointer: " + rsfWriter.getFilePointer());
    }
}
