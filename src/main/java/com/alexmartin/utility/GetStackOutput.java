package com.alexmartin.utility;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.DescribeStacksResult;
import com.amazonaws.services.cloudformation.model.Output;

import java.util.List;
import java.util.Optional;

public class GetStackOutput {

    private static AmazonCloudFormation client = AmazonCloudFormationClientBuilder.standard().build();

    public static String getStackOutput(String stackName, String key){
        DescribeStacksRequest describeStacksRequest = new DescribeStacksRequest();
        describeStacksRequest.withStackName(stackName);
        DescribeStacksResult describeStacksResult = client.describeStacks(describeStacksRequest);
        List<Output> outputs = describeStacksResult.getStacks().get(0).getOutputs();
        Optional<Output> output = outputs.stream()
                .filter(index -> index.getOutputKey().equals(key))
                .findFirst();
        return output.map(Output::getOutputValue)
                .orElseThrow(() -> new RuntimeException("Did not find stack output."));
    }
}
