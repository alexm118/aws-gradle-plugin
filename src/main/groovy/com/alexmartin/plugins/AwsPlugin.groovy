package com.alexmartin.plugins;

import com.alexmartin.extensions.CreateStackExtension
import com.alexmartin.extensions.DeleteStackExtension
import com.alexmartin.extensions.GetStackOutputExtension
import com.alexmartin.tasks.CreateStackTask
import com.alexmartin.tasks.DeleteStackTask
import com.alexmartin.tasks.GetStackOutputTask
import com.alexmartin.tasks.ListStacksTask
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AwsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        def createStackExtension = project.extensions.create("createStack", CreateStackExtension, project)
        def deleteStackExtension = project.extensions.create("deleteStack", DeleteStackExtension, project)
        def getStackOutputExtension = project.extensions.create("getStackOutput", GetStackOutputExtension, project)
        project.tasks.create("createStack", CreateStackTask) {
            stackName = createStackExtension.stackName
            filePath = createStackExtension.filePath
            params = createStackExtension.params
        }
        project.tasks.create("deleteStack", DeleteStackTask) {
            stackName = deleteStackExtension.stackName
        }
        project.tasks.create("getStackOutput", GetStackOutputTask) {
            stackName = getStackOutputExtension.stackName
            outputKey = getStackOutputExtension.outputKey
        }
        project.tasks.create("listStacks", ListStacksTask)
    }
}
