package com.sebrogala.ExpressiveGenerators.Form;

import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.*;

public class TextInput extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField userInput;

    public TextInput() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if("".equals(userInput.getText())) {
                    Messages.showInfoMessage("Input can't be empty!", "Empty Input");
                } else {
                    dispose();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {
        dispose();
    }

    public static String run(String title) {
        TextInput dialog = new TextInput();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setTitle(title);
        dialog.setVisible(true);

        return dialog.userInput.getText();
    }
}
