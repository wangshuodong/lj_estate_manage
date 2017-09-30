package com.ym.common.utils;

import java.io.BufferedOutputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 读取zip压缩文件中文本的内容
 * 
 * @author fish
 */
public class ReadZip {

    public static void getZip(String tmpFileName, String filePath, File[] files) {
        // 缓存区
        byte[] buffer = new byte[1024 * 10];
        // 压缩包名称
        String strZipPath = filePath + "/" + tmpFileName;

        try {
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(strZipPath),
                    1024 * files.length * 100));
            for (File file : files) {
                if (file != null && file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    zos.setEncoding("gbk");
                    try {
                        zos.putNextEntry(new ZipEntry(file.getName()));
                        int len;
                        // 读入需要下载的文件的内容，打包到zip文件
                        while ((len = fis.read(buffer)) != -1) {
                            zos.write(buffer, 0, len);
                        }

                        zos.closeEntry();
                        fis.close();
                        file.delete();
                    } catch (Exception e) {
                        // TODO: handle exception
                        zos.closeEntry();
                        fis.close();
                        e.printStackTrace();
                    }

                }

            }
            zos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            String fileName = "D:/workspace/java/src/ReadZip.zip";
            // 构造ZipFile
            ZipFile zf = new ZipFile(new File(fileName));
            // 返回 ZIP file entries的枚举.
            Enumeration<? extends ZipEntry> entries = zf.getEntries();

            while (entries.hasMoreElements()) {
                ZipEntry ze = entries.nextElement();
                System.out.println("name:" + ze.getName());
                long size = ze.getSize();
                if (size > 0) {
                    System.out.println("Length is " + size);
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line.trim());
                    }
                    br.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
