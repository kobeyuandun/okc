package com.sky.javademo.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author yuandunbin
 * @date 2020/5/5
 */
public class InputStreamReaderTest {
    public static void main(String[] args) throws Exception {
        testISRDefaultEncoder(new FileInputStream(new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/OutPutStreamWrite.txt")));
        testISRGBKEncoder(new FileInputStream(new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/OutPutStreamWrite.txt")));
        testISRUTFEncoder(new FileInputStream(new File("/Users/yuandunbin/study_work/github_app/SQLiteTest/app/src/testtxt/OutPutStreamWrite.txt")));
    }

    private static void testISRUTFEncoder(InputStream is) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(is,"UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        while ((str = bufferedReader.readLine()) !=null){
            System.out.println(str);
            System.out.println("code: "+inputStreamReader.getEncoding());
        }
        bufferedReader.close();
    }

    private static void testISRGBKEncoder(InputStream is) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(is,"GBK");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        while ((str = bufferedReader.readLine()) !=null){
            System.out.println(str);
            System.out.println("code: "+inputStreamReader.getEncoding());
        }
        bufferedReader.close();
    }

    private static void testISRDefaultEncoder(InputStream is) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        while ((str = bufferedReader.readLine()) !=null){
              System.out.println(str);
              System.out.println("code: "+inputStreamReader.getEncoding());
        }
        bufferedReader.close();
    }
}
