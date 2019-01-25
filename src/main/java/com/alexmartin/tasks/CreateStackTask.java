package com.alexmartin.tasks;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;
import com.amazonaws.services.cloudformation.model.Parameter;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateStackTask extends DefaultTask {

    private AmazonCloudFormation client = AmazonCloudFormationClientBuilder.standard().build();

    Property<String> stackName = getProject().getObjects().property(String.class);
    Property<String> filePath = getProject().getObjects().property(String.class);
    MapProperty<String, String> params = getProject().getObjects().mapProperty(String.class, String.class);

    @TaskAction
    public void createStack() {
        CreateStackRequest stackRequest = new CreateStackRequest();
        try {
            System.out.println("Stack Name: " + stackName.get());
            System.out.println("Stack File: " + filePath.get());
            stackRequest.withStackName(stackName.get());
            stackRequest.withTemplateBody(new String(Files.readAllBytes(Paths.get(filePath.get()))));
            stackRequest.withParameters(convertMapToParams());
            System.out.println(stackRequest.toString());
            client.createStack(stackRequest);
        } catch (Exception e) {
            System.out.println("Error creating stack: " + e.getMessage());
        }
    }

    private List<Parameter> convertMapToParams() {
        Map<String, String> parameters = params.get();
        List<Parameter> cloudformationParameters = new ArrayList<>();
        for(Map.Entry entry : parameters.entrySet()) {
            Parameter temp = new Parameter()
                    .withParameterKey(entry.getKey().toString())
                    .withParameterValue(entry.getValue().toString());
            cloudformationParameters.add(temp);
        }
        return cloudformationParameters;
    }
}
