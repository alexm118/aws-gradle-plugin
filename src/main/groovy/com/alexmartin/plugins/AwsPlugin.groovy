package com.alexmartin.plugins;

import com.alexmartin.extensions.CreateStackExtension
import com.alexmartin.tasks.CreateStackTask;
import com.alexmartin.tasks.ListStacksTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;

public class AwsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        def extension = project.extensions.create("createStack", CreateStackExtension, project)
        project.tasks.create("createStack", CreateStackTask) {
            stackName = extension.stackName
            filePath = extension.filePath
        }
        project.tasks.create("listStacks", ListStacksTask)
    }
}
