package com.alexmartin.tasks;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.DeleteStackRequest;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;

public class DeleteStackTask extends DefaultTask {

    private AmazonCloudFormation client = AmazonCloudFormationClientBuilder.standard().build();

    Property<String> stackName = getProject().getObjects().property(String.class);

    @TaskAction
    public void deleteStack() {
        DeleteStackRequest deleteStackRequest = new DeleteStackRequest();
        deleteStackRequest.withStackName(stackName.get());
        client.deleteStack(deleteStackRequest);
    }
}
