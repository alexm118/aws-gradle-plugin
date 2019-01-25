package com.alexmartin.extensions

import org.gradle.api.Project
import org.gradle.api.provider.Property


public class GetStackOutputExtension {
    Property<String> stackName
    Property<String> outputKey
    public GetStackOutputExtension(Project project) {
        stackName = project.objects.property(String)
        stackName.set("StackName")
        outputKey = project.objects.property(String)
        outputKey.set("OutputKey")
    }
}
