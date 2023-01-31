package com.bau.taskportal.controller;

import com.bau.taskportal.bean.task.RegionClass;
import com.bau.taskportal.bean.task.TaskDetails;
import com.bau.taskportal.constant.Constants;
import com.bau.taskportal.util.project.ProjectUtilService;
import com.bau.taskportal.util.task.TaskUtilService;
import com.bau.taskportal.util.user.UserUtilService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class TaskController {

    @Autowired
    TaskUtilService taskUtilService;

    @Autowired
    ProjectUtilService projectUtilService;

    @Autowired
    UserUtilService userUtilService;
    private List<TaskDetails> taskResultList;
    private Integer projectId;
    private Integer userId;
    Logger logger = Logger.getLogger(TaskController.class.getName());


    @PostMapping("/create/task")
    public List<TaskDetails> createTask(@RequestBody RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        projectId = null;
        try {
            projectId = projectUtilService.findProject(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.TASK_WARNING_MSG2 + e.getMessage());
        }
        if (null != projectId) {
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                try {
                    taskResultList.add(taskUtilService.createNewTask(taskDetails, projectId));
                } catch (Exception e) {
                    logger.warning(Constants.TASK_WARNING_MSG1 + e.getMessage());
                }
            }
        }
        return taskResultList;
    }

    @PostMapping("/assign/task")
    public List<TaskDetails> assignTask(@RequestBody @NotNull RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        projectId = null;
        try {
            projectId = projectUtilService.findProject(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.TASK_WARNING_MSG2 + e.getMessage());
        }
        if (null != projectId) {
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                try {
                    taskResultList.add(taskUtilService.assignTask(taskDetails, projectId));
                } catch (Exception e) {
                    logger.warning(Constants.TASK_WARNING_MSG1 + e.getMessage());
                }
            }
        }
        return taskResultList;
    }

    @PostMapping("/update/task")
    public List<TaskDetails> updateTask(@RequestBody @NotNull RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        projectId = null;
        try {
            projectId = projectUtilService.findProject(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.TASK_WARNING_MSG2 + e.getMessage());
        }
        if (null != projectId) {
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                try {
                    taskResultList.add(taskUtilService.updateTask(taskDetails, projectId));
                } catch (Exception e) {
                    logger.warning(Constants.TASK_WARNING_MSG1 + e.getMessage());
                }
            }
        }
        return taskResultList;
    }

    @PostMapping("/delete/task")
    public List<TaskDetails> deleteTask(@RequestBody @NotNull RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        projectId = null;
        try {
            projectId = projectUtilService.findProject(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.TASK_WARNING_MSG2 + e.getMessage());
        }
        if (null != projectId) {
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                try {
                    taskResultList.add(taskUtilService.deleteTask(taskDetails, projectId));
                } catch (Exception e) {
                    logger.warning(Constants.TASK_WARNING_MSG1 + e.getMessage());
                }
            }
        }
        return taskResultList;
    }

    @PostMapping("/view/active/task")
    public List<TaskDetails> viewActiveTask(@RequestBody @NotNull RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        projectId = null;
        try {
            projectId = projectUtilService.findProject(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.TASK_WARNING_MSG2 + e.getMessage());
        }
        if (null != projectId) {
            try {
                taskResultList = taskUtilService.viewActiveTask(projectId);
            } catch (Exception e) {
                logger.warning(Constants.TASK_WARNING_MSG1 + e.getMessage());
            }
        }
        return taskResultList;
    }

    @PostMapping("/view/user/active/task")
    public List<TaskDetails> viewUserActiveTask(@RequestBody @NotNull RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        projectId = null;
        userId = null;
        try {
            projectId = projectUtilService.findProject(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.TASK_WARNING_MSG2 + e.getMessage());
        }
        try {
            userId = userUtilService.findUser(regionClass.getUserName()).getUserId();
        } catch (NullPointerException e) {
            logger.warning(Constants.TASK_WARNING_MSG3 + e.getMessage());
        }
        if (null != projectId && null != userId) {
            try {
                taskResultList = taskUtilService.viewUserActiveTask(projectId, userId);
            } catch (Exception e) {
                logger.warning(Constants.TASK_WARNING_MSG1 + e.getMessage());
            }
        }
        return taskResultList;
    }

    @PostMapping("/view/all/task")
    public List<TaskDetails> viewAllTask(@RequestBody @NotNull RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        projectId = null;
        try {
            projectId = projectUtilService.findProject(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.TASK_WARNING_MSG2 + e.getMessage());
        }
        if (null != projectId) {
            try {
                taskResultList = taskUtilService.viewAllTask(projectId);
            } catch (Exception e) {
                logger.warning(Constants.TASK_WARNING_MSG1 + e.getMessage());
            }
        }
        return taskResultList;
    }

    @PostMapping("/view/user/all/task")
    public List<TaskDetails> viewUserAllTask(@RequestBody @NotNull RegionClass regionClass) {
        taskResultList = new ArrayList<>();
        projectId = null;
        userId = null;
        try {
            projectId = projectUtilService.findProject(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.TASK_WARNING_MSG2 + e.getMessage());
        }
        try {
            userId = userUtilService.findUser(regionClass.getUserName()).getUserId();
        } catch (NullPointerException e) {
            logger.warning(Constants.TASK_WARNING_MSG3 + e.getMessage());
        }
        if (null != projectId && null != userId) {
            try {
                taskResultList = taskUtilService.viewUserAllTask(projectId, userId);
            } catch (Exception e) {
                logger.warning(Constants.TASK_WARNING_MSG1 + e.getMessage());
            }
        }
        return taskResultList;
    }
}