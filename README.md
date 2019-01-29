# AWS Gradle Plugin

Extendable library to deliver AWS functionality as gradle tasks. 

## Getting Started
Add the gradle plugins library as a maven repository and the AWS Plugin as a dependency.

```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.alexmartin.plugins.AwsPlugin:aws-gradle-plugin:0.0.19"
  }
}
```

Apply the plugin

```groovy
apply plugin: "com.alexmartin.plugins.AwsPlugin"
```

Import the desired Tasks and utility functions

```groovy
import static com.alexmartin.utility.GetStackOutput.getStackOutput
import com.alexmartin.tasks.*
```

Register your desired tasks
eg:

```groovy
tasks.register("createDemoBucket", CreateStackTask) {
  stackName = "alex-demo-bucket"
  filePath = "s3.yaml"
  params = [BucketName: "alex-demo-gradle-task"]
}
```

### Support or Contact

Having trouble with AWS Gradle Plugin? Please leave an issue!
