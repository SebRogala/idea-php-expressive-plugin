package com.sebrogala.ExpressiveGenerators.Helper;

public class StringUtility {

    public static String toSnakeCase(String input) {
        return fromCamelCase(input, "_");
    }

    public static String fromCamelCase(String input, String connector) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1" + connector + "$2";

        return input.replaceAll(regex, replacement)
                .toLowerCase();
    }

    public static String join(String[] strings, String glue) {
        String newString = "";

        for (String str: strings) {
            newString += str + glue;
        }
        return newString.substring(0, newString.length() - glue.length());
    }
}
