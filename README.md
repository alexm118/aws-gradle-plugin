# AWS Gradle Plugin

Extendable library to deliver AWS functionality as gradle tasks. 

## Setting up your AWS Credentials

The Gradle AWS Plugin utilizes the standard AWS Client builder.

For more detailed instructions about how to configure your local AWS Credentials,
please see the [AWS Documentation](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html)

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
    classpath "gradle.plugin.com.alexmartin.plugins.AwsPlugin:aws-gradle-plugin:0.1.0"
  }
}
```

Apply the plugin

```groovy
apply plugin: "com.alexmartin.plugins.AwsPlugin"
```

## Supported Features

  * [CloudFormation](./cloudformation.md)

### Support or Contact

Having trouble with AWS Gradle Plugin? Please leave an issue!
