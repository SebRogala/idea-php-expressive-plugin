package com.sebrogala.ExpressiveGenerators.Helper;

import com.intellij.openapi.vfs.VirtualFileManager;

import java.io.*;
import java.util.Scanner;

public class FileHandler {

    public static void putContents(String filePath, String content) {

        File f = new File(filePath.replaceAll("(.+)\\/[\\w\\.]+$", "$1"));
        f.mkdirs();

        try {
            Writer wr = new FileWriter(filePath);
            wr.write(content);
            wr.flush();
            wr.close();
        } catch (IOException e) {
        }
    }

    public static String getContents(String filePath) {

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
        }
        Scanner fileScanned = new Scanner(fileReader);

        String content = "";
        while (fileScanned.hasNext()) {
            String line = fileScanned.nextLine();
            content += line + "\n";
        }
        fileScanned.close();

        return content;
    }

    public static void refresh() {
        VirtualFileManager.getInstance().syncRefresh();
    }
}
