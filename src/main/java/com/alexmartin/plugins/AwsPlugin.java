package com.alexmartin.plugins;

import com.alexmartin.tasks.CloudFormation;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class AwsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().create("listQueues", CloudFormation.class);
    }
}
