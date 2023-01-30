package com.bau.taskportal.bean.task;

import java.util.List;

public class RegionClass {

    private String projectName;
    private List<TaskDetails> taskList;

    public List<TaskDetails> getTaskList() {
        return taskList;
    }

    public String getProjectName() {
        return projectName;
    }
}
