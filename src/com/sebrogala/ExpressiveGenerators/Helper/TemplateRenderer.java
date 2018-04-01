package com.sebrogala.ExpressiveGenerators.Helper;

import java.util.Map;

public class TemplateRenderer {

    private String templateName;
    private Map<String, String> vars;

    private String renderedTemplate;
    private boolean isRendered = false;

    public TemplateRenderer(String templateName, Map<String, String> vars) {
        this.templateName = templateName;
        this.vars = vars;
    }


    public TemplateRenderer render() {
        String content = FileHandler.getContents(
                this
                        .getClass()
                        .getClassLoader()
                        .getResource("/com/sebrogala/ExpressiveGenerators/Resource/Template/" + templateName)
                        .getFile());

        for (Map.Entry<String, String> entry : vars.entrySet()) {
            String regex = "\\{\\{\\s*?" + entry.getKey() + "\\s*?}}";
            content = content.replaceAll(regex, entry.getValue());
        }

        renderedTemplate = content;
        isRendered = true;

        return this;
    }

    public String getRenderedTemplate() {
        if(!isRendered){
            throw new RuntimeException();
        }

        return renderedTemplate;
    }

    public void saveRenderedFile(String path) {
        FileHandler.putContents(path, renderedTemplate);
    }
}
