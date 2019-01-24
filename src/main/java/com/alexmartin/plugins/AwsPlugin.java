package com.alexmartin.plugins;

import com.alexmartin.extensions.CreateStackExtension;
import com.alexmartin.tasks.ListStacksTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class AwsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create("createStack", CreateStackExtension.class, project);
        project.getTasks().create("listStacks", ListStacksTask.class);
    }
}
