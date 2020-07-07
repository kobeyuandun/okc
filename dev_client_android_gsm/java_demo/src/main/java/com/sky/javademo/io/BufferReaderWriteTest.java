package com.sky.javademo.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author yuandunbin
 * @date 2020/5/4
 */
public class BufferReaderWriteTest {
    public static void main(String[] args) throws Exception {
        //todo 路径记得修改
        File fileReader = new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/bufferreader.txt");
        File fileWirte = new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/bufferwrite.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileReader));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileWirte));
        char[] chars = new char[102];
        while (bufferedReader.read(chars) != -1) {
            bufferedWriter.write(chars);

        }
        bufferedReader.close();
        bufferedWriter.flush();
        bufferedWriter.close();

    }
}
