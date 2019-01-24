package com.alexmartin.plugins;

import com.alexmartin.extensions.CreateStackExtension
import com.alexmartin.extensions.DeleteStackExtension
import com.alexmartin.tasks.CreateStackTask
import com.alexmartin.tasks.DeleteStackTask
import com.alexmartin.tasks.ListStacksTask
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AwsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        def createStackExtension = project.extensions.create("createStack", CreateStackExtension, project)
        def deleteStackExtension = project.extensions.create("deleteStack", DeleteStackExtension, project)
        project.tasks.create("createStack", CreateStackTask) {
            stackName = createStackExtension.stackName
            filePath = createStackExtension.filePath
        }
        project.tasks.create("deleteStack", DeleteStackTask) {
            stackName = deleteStackExtension.stackName
        }
        project.tasks.create("listStacks", ListStacksTask)
    }
}
