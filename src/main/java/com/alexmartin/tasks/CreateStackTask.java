package com.alexmartin.tasks;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class CreateStackTask extends DefaultTask {

    private AmazonCloudFormation client = AmazonCloudFormationClientBuilder.standard().build();

    @TaskAction
    public void createStack() {

    }

}
