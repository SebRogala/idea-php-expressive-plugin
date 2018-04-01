package com.sebrogala.ExpressiveGenerators.Helper;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.sebrogala.ExpressiveGenerators.Exception.ModuleStructureNotFoundException;

import java.io.File;

public class ExpressiveModuleStructure {

    private VirtualFile clickedMenuItem;

    private String modulePath = "";

    private String moduleName = "";

    public ExpressiveModuleStructure(AnActionEvent e) {
        clickedMenuItem = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
    }

    public String clickedMenuItemPath() {
        return clickedMenuItem.getCanonicalPath();
    }

    public String clickedMenuItemParentPath() {
        return clickedMenuItem.getParent().getCanonicalPath();
    }

    public boolean siblingFileExists(String name) {
        return new File(clickedMenuItemParentPath() + "/" + name).exists();
    }

    public boolean childFileExists(String name) {
        return new File(clickedMenuItemPath() + "/" + name).exists();
    }

    public boolean parentFileExists(String name) {
        return new File(clickedMenuItem.getParent().getParent().getCanonicalPath() + "/" + name).exists();
    }

    public String currentModuleRootDir() throws ModuleStructureNotFoundException {
        //String modulePath = "";

        if (childFileExists("ConfigProvider.php")) {
            modulePath = clickedMenuItemParentPath();
        } else if (childFileExists("src/ConfigProvider.php")) {
            modulePath = clickedMenuItemPath();
        } else if (siblingFileExists("ConfigProvider.php")) {
            modulePath = clickedMenuItem.getParent().getParent().getCanonicalPath();
        } else if (parentFileExists("ConfigProvider.php")) {
            modulePath = clickedMenuItem.getParent().getParent().getParent().getCanonicalPath();
        }

        if ("".equals(modulePath)) {
            throw new ModuleStructureNotFoundException();
        }

        moduleName = modulePath.replaceAll(".+\\/(\\w+$)", "$1");

        return modulePath;
    }

    public String getModuleName() {
        return moduleName;
    }
}
