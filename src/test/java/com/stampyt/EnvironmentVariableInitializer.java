package com.stampyt;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentVariableInitializer {

    public static void initEnvironmentVariables() throws Exception {
        Map<String, String> environmentVariable = new HashMap<>();
        environmentVariable.put("LOCAL", "true");
        Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
        Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
        theEnvironmentField.setAccessible(true);
        Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
        env.clear();
        env.putAll(environmentVariable);
        Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
        theCaseInsensitiveEnvironmentField.setAccessible(true);
        Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
        cienv.clear();
        cienv.putAll(environmentVariable);
    }

}
