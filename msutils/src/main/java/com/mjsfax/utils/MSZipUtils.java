package com.mjsfax.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MSZipUtils {
    public static void inflate(String filePath, String zipPath) {
        try {
            File file = new File(filePath);
            File zipFile = new File(zipPath);
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            if (file.isDirectory()) {
                zipFolder(file, zos);
            } else {
                zipFile(file.getParentFile(), file, zos);
            }
            zos.close();
        } catch (IOException e) {

        }
    }

    private static void zipFolder(File folder, ZipOutputStream zos) throws IOException {
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; ++i) {
            File file = files[i];
            if (file.isDirectory()) {
                zipFolder(file, zos);
            } else {
                zipFile(folder, file, zos);
            }
        }
    }

    private static void zipFile(File parent, File file, ZipOutputStream zos) throws IOException {
        InputStream is = new FileInputStream(file);;
        zos.putNextEntry(new ZipEntry(parent.getName() + File.separator + file.getName()));
        byte[] buffer = new byte[1024];
        int readLen = is.read(buffer);
        while (readLen != -1) {
            zos.write(buffer, 0, readLen);
            readLen = is.read(buffer);
        }
        is.close();
    }
}
