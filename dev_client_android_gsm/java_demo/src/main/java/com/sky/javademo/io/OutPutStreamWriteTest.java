package com.sky.javademo.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @author yuandunbin
 * @date 2020/5/5
 */
public class OutPutStreamWriteTest {
    private static final String STRING = "I LIKE AV";
    public static void main(String[] args) throws Exception {
        File file = new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/OutPutStreamWrite.txt");
        //删除已有文件
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(STRING);
        bufferedWriter.newLine();
        bufferedWriter.flush();
//        bufferedWriter.close();
        System.out.println("outputStreamWriter encoding: " + outputStreamWriter.getEncoding());

        OutputStreamWriter gbk = new OutputStreamWriter(fileOutputStream, "GBK");
        BufferedWriter bufferedWriterGBK = new BufferedWriter(gbk);
        bufferedWriterGBK.write(STRING+"GBK");
        bufferedWriterGBK.newLine();
        bufferedWriterGBK.flush();
//        bufferedWriterGBK.close();
        System.out.println("gbk encoding: " + gbk.getEncoding());

        OutputStreamWriter utf = new OutputStreamWriter(fileOutputStream, "UTF-8");
        BufferedWriter bufferedWriterUTF = new BufferedWriter(utf);
        bufferedWriterUTF.write(STRING+"UTF-8");
        bufferedWriterUTF.newLine();
        bufferedWriterUTF.flush();
        System.out.println("utf encoding: " + utf.getEncoding());

        bufferedWriter.close();
        bufferedWriterGBK.close();
        bufferedWriterUTF.close();
    }
}
