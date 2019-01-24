package com.alexmartin.tasks;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CreateStackTask extends DefaultTask {

    private AmazonCloudFormation client = AmazonCloudFormationClientBuilder.standard().build();

    final Property<String> stackName = getProject().getObjects().property(String.class);
    final Property<String> filePath = getProject().getObjects().property(String.class);

    @TaskAction
    public void createStack() {
        CreateStackRequest stackRequest = new CreateStackRequest();
        try {
            stackRequest.withStackName(stackName.get());
            stackRequest.withTemplateBody(
                    convertStreamToString(
                            CreateStackTask.class.getResourceAsStream(filePath.get())));
            client.createStack(stackRequest);
        } catch (Exception e) {
            System.out.println("Error creating stack" + e.getMessage());
        }
    }

    private static String convertStreamToString(InputStream in) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringbuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringbuilder.append(line + "\n");
        }
        in.close();
        return stringbuilder.toString();
    }

}
