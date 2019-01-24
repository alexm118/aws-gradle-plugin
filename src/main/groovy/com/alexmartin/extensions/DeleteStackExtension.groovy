package com.alexmartin.extensions

import org.gradle.api.Project
import org.gradle.api.provider.Property

public class DeleteStackExtension {
    Property<String> stackName

    public DeleteStackExtension(Project project) {
        stackName = project.objects.property(String)
        stackName.set("Example")
    }
}
