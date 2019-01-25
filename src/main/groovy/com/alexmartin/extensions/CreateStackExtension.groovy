package com.alexmartin.extensions

import org.gradle.api.Project
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property

public class CreateStackExtension {
    Property<String> stackName
    Property<String> filePath
    MapProperty<String, String> params
    public CreateStackExtension(Project project) {
        stackName = project.objects.property(String)
        stackName.set("Example")
        filePath = project.objects.property(String)
        filePath.set("template.yaml")
        params = project.objects.mapProperty(String, String)
        params.set(new HashMap<String, String>())
    }
}
