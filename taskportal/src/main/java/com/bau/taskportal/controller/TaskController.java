package com.bau.taskportal.controller;

import com.bau.taskportal.bean.task.RegionClass;
import com.bau.taskportal.bean.task.TaskDetails;
import com.bau.taskportal.repository.ProjectRepository;
import com.bau.taskportal.repository.TaskRepository;
import com.bau.taskportal.repository.UserRepository;
import com.bau.taskportal.util.task.TaskUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class TaskController {

    @Autowired
    private TaskUtilService taskUtilService;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private List<TaskDetails> taskResultList;

    @PostMapping("/create/task")
    public List<TaskDetails> createTask(@RequestBody RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        Integer projectId = projectRepository.findByProjectName(regionClass.getProjectName()).getProjectId();
        if (null != projectId) {
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                taskResultList.add(taskUtilService.createNewTask(taskDetails, projectId));
            }
        }
        return taskResultList;
    }

    @PostMapping("/assign/task")
    public List<TaskDetails> assignTask(@RequestBody RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        Integer projectId = projectRepository.findByProjectName(regionClass.getProjectName()).getProjectId();
        if (null != projectId) {
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                taskResultList.add(taskUtilService.assignTask(taskDetails, projectId));
            }
        }
        return taskResultList;
    }

    @PostMapping("/update/task")
    public List<TaskDetails> updateTask(@RequestBody RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        Integer projectId = projectRepository.findByProjectName(regionClass.getProjectName()).getProjectId();
        if (null != projectId) {
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                taskResultList.add(taskUtilService.updateTask(taskDetails, projectId));
            }
        }
        return taskResultList;
    }

    @PostMapping("/delete/task")
    public List<TaskDetails> deleteTask(@RequestBody RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        Integer projectId = projectRepository.findByProjectName(regionClass.getProjectName()).getProjectId();
        if (null != projectId) {
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                taskResultList.add(taskUtilService.deleteTask(taskDetails, projectId));
            }
        }
        return taskResultList;
    }

    @PostMapping("/view/active/task")
    public List<TaskDetails> viewActiveTask(@RequestBody RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        Integer projectId = projectRepository.findByProjectName(regionClass.getProjectName()).getProjectId();
        if (null != projectId) {
            taskResultList = taskUtilService.viewActiveTask(projectId);
        }
        return taskResultList;
    }

    @PostMapping("/view/all/task")
    public List<TaskDetails> viewAllTask(@RequestBody RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        Integer projectId = projectRepository.findByProjectName(regionClass.getProjectName()).getProjectId();
        if (null != projectId) {
            taskResultList = taskUtilService.viewAllTask(projectId);
        }
        return taskResultList;
    }
}
