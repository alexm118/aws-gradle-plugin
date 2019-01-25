package com.alexmartin.tasks;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.DescribeStacksResult;
import com.amazonaws.services.cloudformation.model.Output;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;

import java.util.List;

public class GetStackOutputTask extends DefaultTask {

    private AmazonCloudFormation client = AmazonCloudFormationClientBuilder.standard().build();

    Property<String> stackName = getProject().getObjects().property(String.class);
    Property<String> outputKey = getProject().getObjects().property(String.class);

    @TaskAction
    public void getStackOutput(){
        DescribeStacksRequest describeStacksRequest = new DescribeStacksRequest();
        describeStacksRequest.withStackName(stackName.get());
        DescribeStacksResult describeStacksResult = client.describeStacks(describeStacksRequest);
        List<Output> outputs = describeStacksResult.getStacks().get(0).getOutputs();
        outputs.stream()
                .filter(index -> index.getOutputKey().equals(outputKey.get()))
                .findFirst()
                .ifPresent(output -> System.out.println(output.getOutputValue()));
    }
}
