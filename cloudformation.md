# CloudFormation

AWS Gradle Plugin currently supports a creating, deleting and listing CloudFormation Stacks.
For more information about AWS CloudFormation please see the [AWS CloudFormation Documentation](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/Welcome.html)

## Defining Cloudformation Tasks

AWS Gradle Plugin is designed to provide a base for you to extend and define tasks on your own.
To begin you will need to import the desired tasks. Currently supported tasks include:

  * CreateStackTask
  * DeleteStackTask
  
We have defined a gradle task `listStacks` that will output all stacks to the console.

In your `build.gradle` import:

```groovy
import com.alexmartin.tasks.CreateStackTask
import com.alexmartin.tasks.DeleteStackTask
```

Now in your `build.gradle` define a new task:

```groovy
tasks.register("createS3Bucket", CreateStackTask) {
    stackName = "alex-demo"
    filePath = "s3.yaml"
    params = [BucketName: "alex-demo-gradle-task"]
}
```

```groovy
tasks.register("deleteS3Bucket", DeleteStackTask) {
    stackName = "alex-demo"
}
```

Please note that `filePath` is the string path to the file for the CloudFormation template.

After defining our task we can run:

```sh
gradle createS3Bucket
```

`CreateStackTask` will treat the following statuses as a successful creation:
* CREATE_COMPLETE
* UPDATE_COMPLETE

It will treat the following statuses as a failed stack creation:
* CREATE_FAILED
* ROLLBACK_FAILED
* UPDATE_ROLLBACK_COMPLETE
* UPDATE_ROLLBACK_FAILED

The current behavior is to wait until the stack reaches either a failure state or a successful state.

Now when we want to clean up we can run:

```sh
gradle deleteS3Bucket
```

## Looking up Stack Outputs

More often than not, you will need to reference the output from a CloudFormation stack as a 
parameter for another CloudFormation stack. If you need to reference an output value you can utilize
a static helper method:

```groovy
import static com.alexmartin.utility.GetStackOutput.getStackOutput
```

Now we can use `getStackOutput(String stackName, String key)` in our `build.gradle`

```groovy
tasks.register("createEC2", CreateStackTask) {
    stackName = "Example-EC2-Task"
    filePath = "ec2.yaml"
    params = [LoadBalancerUrl: getStackOutput("LoadBalancerURL"), ...]
}
```