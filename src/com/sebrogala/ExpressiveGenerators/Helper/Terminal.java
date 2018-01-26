package com.sebrogala.ExpressiveGenerators.Helper;

import com.intellij.openapi.ui.Messages;

import java.io.IOException;

public class Terminal {

    public static void run(String[] commands) {
        try {
            for (String cmd: commands) {
                Runtime.getRuntime().exec(cmd);
            }
        } catch (IOException e) {
            Messages.showErrorDialog(e.getMessage(), "Error");
        }
    }
}
