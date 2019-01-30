package com.alexmartin.tasks;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.*;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateStackTask extends DefaultTask {

    private AmazonCloudFormation client = AmazonCloudFormationClientBuilder.standard().build();

    String stackName;
    String filePath;

    @Input
    public String getStackName() {
        return stackName;
    }

    public void setStackName(String stackName) {
        this.stackName = stackName;
    }

    @Input
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Input
    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    Map<String, Object> params;

    @TaskAction
    public void createStack() {
        CreateStackRequest stackRequest = new CreateStackRequest();
        try {
            System.out.println("Stack Name: " + stackName);
            System.out.println("Stack File: " + filePath);
            stackRequest.withStackName(stackName);
            stackRequest.withTemplateBody(new String(Files.readAllBytes(Paths.get(filePath))));
            stackRequest.withParameters(convertMapToParams());
            client.createStack(stackRequest);
            System.out.println(String.format("Creating Stack %s", stackName));
            waitForStackCreationToComplete();
        } catch (Exception e) {
            System.out.println("Error creating stack: " + e.getMessage());
        }
    }

    private List<Parameter> convertMapToParams() {
        List<Parameter> cloudformationParameters = new ArrayList<>();
        for(Map.Entry entry : params.entrySet()) {
            Parameter temp = new Parameter()
                    .withParameterKey(entry.getKey().toString())
                    .withParameterValue(entry.getValue().toString());
            cloudformationParameters.add(temp);
        }
        return cloudformationParameters;
    }

    private void waitForStackCreationToComplete() throws InterruptedException {
        DescribeStacksRequest check = new DescribeStacksRequest().withStackName(getStackName());
        boolean completed = false;
        while(!completed) {
            List<Stack> results = client.describeStacks(check).getStacks();
            if(results.isEmpty()){
                throw new RuntimeException("Stack Does Not Exist.");
            } else {
                if(results.stream().anyMatch(this::checkIfStackCreationFailed)) {
                    throw new RuntimeException("Stack Creation or Update Failed");
                } else if(results.stream().anyMatch(this::checkIfStackCreationSucceeded)) {
                    System.out.println("Stack Creation Complete");
                    completed = true;
                }
            }
            if(!completed) Thread.sleep(5000);
        }
    }

    private boolean checkIfStackCreationSucceeded(Stack stack) {
        StackStatus status = StackStatus.fromValue(stack.getStackStatus());
        return status.equals(StackStatus.CREATE_COMPLETE) ||
                status.equals(StackStatus.UPDATE_COMPLETE);
    }

    private boolean checkIfStackCreationFailed(Stack stack) {
        StackStatus status = StackStatus.fromValue(stack.getStackStatus());
        return status.equals(StackStatus.CREATE_FAILED) ||
                status.equals( StackStatus.ROLLBACK_FAILED) ||
                status.equals(StackStatus.UPDATE_ROLLBACK_COMPLETE) ||
                status.equals(StackStatus.UPDATE_ROLLBACK_FAILED);
    }
}
