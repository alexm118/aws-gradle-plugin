package com.alexmartin.extensions;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

class CreateStackExtension {
    final Property<String> stackName;
    final Property<String> filePath;

    CreateStackExtension(Project project) {
        stackName = project.objects().property(String)
        stackName.set("Example")
        filePath = project.objects().property(String)
        filePath.set("template.yaml")

    }
}
