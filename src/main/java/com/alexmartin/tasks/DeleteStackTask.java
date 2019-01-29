package com.alexmartin.tasks;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.DeleteStackRequest;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public class DeleteStackTask extends DefaultTask {

    private AmazonCloudFormation client = AmazonCloudFormationClientBuilder.standard().build();

    String stackName;

    @Input
    public String getStackName() {
        return stackName;
    }

    public void setStackName(String stackName) {
        this.stackName = stackName;
    }

    @TaskAction
    public void deleteStack() {
        DeleteStackRequest deleteStackRequest = new DeleteStackRequest();
        deleteStackRequest.withStackName(stackName);
        client.deleteStack(deleteStackRequest);
    }
}
