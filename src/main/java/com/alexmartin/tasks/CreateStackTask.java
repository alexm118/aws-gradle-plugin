package com.alexmartin.tasks;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;
import com.amazonaws.services.cloudformation.model.Parameter;
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
            System.out.println(stackRequest.toString());
            client.createStack(stackRequest);
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
}
