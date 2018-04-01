package com.sebrogala.ExpressiveGenerators.Helper;

import com.sebrogala.ExpressiveGenerators.Exception.ConfigFileNotFoundException;

import java.io.File;

public class ExpressiveModuleConfig {

    private String moduleDir;

    public ExpressiveModuleConfig(String moduleDir) {
        if(!new File(moduleDir + "/src/ConfigProvider.php").exists()) {
            throw new ConfigFileNotFoundException();
        }

        this.moduleDir = moduleDir;
    }

    public ExpressiveModuleConfig addFactory(String key, String value) {
        PregReplaceInFile.run(
                moduleDir + "/src/ConfigProvider.php",
                "('factories'\\s*?=>\\s*?\\[[\\s\\w:=>,]*)\\n\\s*],",
                "$1\n\t\t\t\t" + key + " => " + value + "\n\t\t\t],"
        );

        return this;
    }

    public ExpressiveModuleConfig addUse(String namespace) {
        return addUse(namespace, "/ConfigProvider.php");
    }

    public ExpressiveModuleConfig addUse(String namespace, String whichFile) {
        String file = moduleDir + "/src" + whichFile;
        if(!PregReplaceInFile.foundInFile(file, namespace)) {
            PregReplaceInFile.run(
                    file,
                    "(\\s*class ConfigProvider)",
                    "\n" + namespace + "$1"
            );
        }

        return this;
    }

    public ExpressiveModuleConfig addRoute(String name, String path, String middleware, String[] methods) {
        String replacement = "[\n" +
                "                'name' => '" + name + "',\n" +
                "                'path' => '" + path + "',\n" +
                "                'middleware' => " + middleware + ",\n" +
                "                'allowed_methods' => ['" + StringUtility.join(methods, "', '") + "'],\n" +
                "            ],";

        PregReplaceInFile.run(
                moduleDir + "/src/ConfigProvider.php",
                "(getRoutes\\(\\)\\s*\\{\\s*return\\s*[\\[\\s'\\w=>\\.,\\/\\\\:\\]]*)(\\];)",
                "$1\t" + replacement + "\n\t\t$2"
        );

        return this;
    }
}
