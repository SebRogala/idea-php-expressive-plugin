package com.sebrogala.ExpressiveGenerators.Action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.sebrogala.ExpressiveGenerators.Form.TextInput;
import com.sebrogala.ExpressiveGenerators.Helper.FileHandler;
import com.sebrogala.ExpressiveGenerators.Helper.PregReplaceInFile;
import com.sebrogala.ExpressiveGenerators.Helper.Terminal;
import com.sebrogala.ExpressiveGenerators.Resource.HelperMessages;

import java.io.File;

public class NewModuleAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String moduleName = TextInput.run("Provide new Module name");
        if ("".equals(moduleName)) {
            return;
        }


        VirtualFile clickedMenuItem = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        String clickedMenuItemPath = clickedMenuItem.getCanonicalPath();
        String clickedMenuItemParentPath = clickedMenuItem.getParent().getCanonicalPath();
        String expressiveRootPath;

        if (new File(clickedMenuItemPath + "/config/config.php").exists()) {
            expressiveRootPath = clickedMenuItemPath;
        } else if (new File(clickedMenuItemParentPath + "/config/config.php").exists()) {
            expressiveRootPath = clickedMenuItemParentPath;
        } else {
            Messages.showErrorDialog(HelperMessages.EXPRESSIVE_STRUCTURE_NOT_FOUND, "Error");
            return;
        }

        if (!new File(expressiveRootPath + "/composer.json").exists()) {
            Messages.showErrorDialog(HelperMessages.COMPOSER_JSON_NOT_FOUND, "Error");
            return;
        }

        if (!new File(expressiveRootPath + "/src").exists()) {
            Messages.showErrorDialog(HelperMessages.EXPRESSIVE_STRUCTURE_NOT_FOUND, "Error");
            return;
        }

        if (new File(expressiveRootPath + "/src/" + moduleName).exists()) {
            Messages.showErrorDialog(HelperMessages.MODULE_ALREADY_EXISTS, "Error");
            return;
        }

        createModuleFolders(expressiveRootPath, moduleName);
        createModuleFiles(expressiveRootPath, moduleName);

        PregReplaceInFile.run(
                expressiveRootPath + "/config/config.php",
                "\\s+new PhpFileProvider\\(realpath\\(__DIR__\\) \\. '\\/autoload\\/\\{\\{,\\*\\.\\}global,\\{,\\*\\.\\}local\\}\\.php'\\),",
                "\n\t" + moduleName + "\\\\ConfigProvider::class,\n\n\tnew PhpFileProvider(realpath(__DIR__) . '/autoload/{{,*.}global,{,*.}local}.php'),"
        );

        PregReplaceInFile.run(
                expressiveRootPath + "/composer.json",
                "(\\s+\"autoload\":\\s?\\{\\s+?\"psr-4\":\\s+?\\{[\\s.\\w\"\\\\\\\\:\\/,]+)\\n\\s+}",
                "$1,\n" +
                        "            \"" + moduleName + "\\\\\\\\\\\": \"src/" + moduleName + "/src/\"\n" +
                        "        }"
        );

        String[] commands = {"composer dump-autoload -d=" + expressiveRootPath};
        Terminal.run(commands);

        VirtualFileManager.getInstance().asyncRefresh(null);
    }

    private void createModuleFiles(String rootDir, String moduleName) {
        FileHandler.putContents(rootDir + "/src/" + moduleName + "/src/ConfigProvider.php", getConfigProviderContent(moduleName));
    }

    private void createModuleFolders(String rootDir, String moduleName) {
        File moduleDir = new File(rootDir + "/src/" + moduleName + "/src");
        moduleDir.mkdirs();
    }

    private String getConfigProviderContent(String moduleName) {

        return "<?php\n" +
                "\n" +
                "namespace " + moduleName + ";\n" +
                "\n" +
                "class ConfigProvider\n" +
                "{\n" +
                "    public function __invoke()\n" +
                "    {\n" +
                "        return [\n" +
                "            'dependencies' => $this->getDependencies(),\n" +
                "            'routes' => $this->getRoutes(),\n" +
                "        ];\n" +
                "    }\n" +
                "    \n" +
                "    public function getDependencies()\n" +
                "    {\n" +
                "        return [\n" +
                "            'invokables' => [\n" +
                "            ],\n" +
                "            'factories'  => [\n" +
                "            ],\n" +
                "        ];\n" +
                "    }\n" +
                "    \n" +
                "    public function getRoutes()\n" +
                "    {\n" +
                "        return [\n" +
                "            [\n" +
                "                //'name' => 'name',\n" +
                "                //'path' => '/',\n" +
                "                //'middleware' => Full\\NameSpace\\Action::class,\n" +
                "                //'allowed_methods' => ['GET'],\n" +
                "            ],\n" +
                "        ];\n" +
                "    }\n" +
                "}";
    }
}
