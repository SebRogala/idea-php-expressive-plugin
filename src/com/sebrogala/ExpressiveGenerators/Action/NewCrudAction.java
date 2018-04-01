package com.sebrogala.ExpressiveGenerators.Action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.sebrogala.ExpressiveGenerators.Exception.ModuleStructureNotFoundException;
import com.sebrogala.ExpressiveGenerators.Form.SingleTextInput;
import com.sebrogala.ExpressiveGenerators.Helper.*;
import com.sebrogala.ExpressiveGenerators.Resource.HelperMessages;

import java.util.HashMap;
import java.util.Map;

public class NewCrudAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String resourceName = SingleTextInput.run("Provide new Resource name for CRUD");
        if ("".equals(resourceName)) {
            return;
        }

        ExpressiveModuleStructure xsvHelper = new ExpressiveModuleStructure(e);
        String moduleRootDir;
        try {
            moduleRootDir = xsvHelper.currentModuleRootDir();
        } catch (ModuleStructureNotFoundException exc) {
            Messages.showInfoMessage(HelperMessages.EXPRESSIVE_STRUCTURE_NOT_FOUND, "Wrong Directory");
            return;
        }

        ExpressiveModuleConfig moduleConfig = new ExpressiveModuleConfig(moduleRootDir);
        String moduleName = xsvHelper.getModuleName();

        String[][] filesToCreate = {
            {"CreateAction","Create" + resourceName + "Action", moduleRootDir + "/src/Action"},
            {"ReadAction","Read" + resourceName + "Action", moduleRootDir + "/src/Action"},
            {"UpdateAction","Update" + resourceName + "Action", moduleRootDir + "/src/Action"},
            {"DeleteAction","Delete" + resourceName + "Action", moduleRootDir + "/src/Action"},

            {"CreateInputFilter","Create" + resourceName + "InputFilter", moduleRootDir + "/src/InputFilter"},
            {"UpdateInputFilter","Update" + resourceName + "InputFilter", moduleRootDir + "/src/InputFilter"},

            {"CreateDoctrineService","Create" + resourceName + "DoctrineService", moduleRootDir + "/src/Service"},
            {"ReadDoctrineService","Read" + resourceName + "DoctrineService", moduleRootDir + "/src/Service"},
            {"UpdateDoctrineService","Update" + resourceName + "DoctrineService", moduleRootDir + "/src/Service"},
            {"DeleteDoctrineService","Delete" + resourceName + "DoctrineService", moduleRootDir + "/src/Service"}
        };

        //no need for factories settings as there are Abstract Factories

        createFiles(filesToCreate, moduleName, resourceName);

        moduleConfig.addRoute(
                StringUtility.toSnakeCase(resourceName) + ".create",
                "/" + StringUtility.fromCamelCase(resourceName, "-"),
                moduleName + "\\\\Action\\\\Create" + resourceName + "Action::class",
                new String[] {"POST"}
                );
        moduleConfig.addRoute(
                StringUtility.toSnakeCase(resourceName) + ".read",
                "/" + StringUtility.fromCamelCase(resourceName, "-") + "[/:id]",
                moduleName + "\\\\Action\\\\Read" + resourceName + "Action::class",
                new String[] {"GET"}
        );
        moduleConfig.addRoute(
                StringUtility.toSnakeCase(resourceName) + ".update",
                "/" + StringUtility.fromCamelCase(resourceName, "-") + "/:id",
                moduleName + "\\\\Action\\\\Update" + resourceName + "Action::class",
                new String[] {"PUT"}
        );
        moduleConfig.addRoute(
                StringUtility.toSnakeCase(resourceName) + ".delete",
                "/" + StringUtility.fromCamelCase(resourceName, "-") + "/:id",
                moduleName + "\\\\Action\\\\Delete" + resourceName + "Action::class",
                new String[] {"DELETE"}
        );


        FileHandler.refresh();
    }

    private void createFiles(String[][] filesToCreate, String moduleName, String resourceName) {
        Map<String, String> vars = new HashMap<>();
        vars.put("ResourceName", resourceName);
        vars.put("ModuleName", moduleName);

        for (String[] fileInfo: filesToCreate) {
            TemplateRenderer renderer = new TemplateRenderer(fileInfo[0] + ".php", vars);

            renderer.render()
                    .saveRenderedFile(fileInfo[2] + "/" + fileInfo[1] + ".php");
        }
    }
}
