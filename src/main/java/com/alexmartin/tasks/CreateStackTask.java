package com.alexmartin.tasks;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateStackTask extends DefaultTask {

    private AmazonCloudFormation client = AmazonCloudFormationClientBuilder.standard().build();

    Property<String> stackName = getProject().getObjects().property(String.class);
    Property<String> filePath = getProject().getObjects().property(String.class);

    @TaskAction
    public void createStack() {
        CreateStackRequest stackRequest = new CreateStackRequest();
        try {
            System.out.println("Stack Name: " + stackName.get());
            System.out.println("Stack File: " + filePath.get());
            stackRequest.withStackName(stackName.get());
            stackRequest.withTemplateBody(new String(Files.readAllBytes(Paths.get(filePath.get()))));
            System.out.println(stackRequest.toString());
            client.createStack(stackRequest);
        } catch (Exception e) {
            System.out.println("Error creating stack: " + e.getMessage());
        }
    }
}
