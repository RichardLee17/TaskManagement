package com.bau.taskportal.bean.task;

import java.util.List;
public class RegionClass {

    private String projectName;

    private String userName;

    private List<TaskDetails> taskList;

    public List<TaskDetails> getTaskList() {
        return taskList;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getUserName() {
        return userName;
    }
}
