package com.sebrogala.ExpressiveGenerators.Helper;

public class PregReplaceInFile {

    public static void run(String filePath, String regex, String replacement) {

        String content = FileHandler.getContents(filePath);
        String response = content.replaceAll(regex, replacement);
        FileHandler.putContents(filePath, response);
    }
}
