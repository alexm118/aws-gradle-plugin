package com.alexmartin.extensions;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

public class CreateStackExtension {
    final Property<String> stackName;
    final Property<String> filePath;

    public CreateStackExtension(Project project) {
        stackName = project.getObjects().property(String.class);
        stackName.set("Example");
        filePath = project.getObjects().property(String.class);
        filePath.set("template.yaml");

    }
}
