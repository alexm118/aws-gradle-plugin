package com.alexmartin.plugins;

import com.alexmartin.tasks.ListStacksTask
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AwsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.tasks.create("listStacks", ListStacksTask)
    }
}
