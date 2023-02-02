package com.bau.taskportal.controller;

import com.bau.taskportal.bean.task.RegionClass;
import com.bau.taskportal.bean.task.TaskDetails;
import com.bau.taskportal.constant.Constants;
import com.bau.taskportal.entity.User;
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
    private User user;
    Logger logger = Logger.getLogger(TaskController.class.getName());


    @PostMapping("/create/task")
    public List<TaskDetails> createTask(@RequestBody RegionClass regionClass) {
        projectId = null;
        try {
            projectId = projectUtilService.findProjectByName(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.PROJECT_NOT_FOUND_MSG + e.getMessage());
        }
        if (null != projectId) {
            logger.info(Constants.PROJECT_FOUND_MSG);
            taskResultList = new ArrayList<>();
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                try {
                    taskResultList.add(taskUtilService.createNewTask(taskDetails, projectId));
                } catch (Exception e) {
                    logger.warning(Constants.TASK_NOT_FOUND_MSG + e.getMessage());
                }
            }
        }
        if (null == taskResultList) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return taskResultList;
    }

    @PostMapping("/assign/task")
    public List<TaskDetails> assignTask(@RequestBody @NotNull RegionClass regionClass) {
        projectId = null;
        try {
            projectId = projectUtilService.findProjectByName(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.PROJECT_NOT_FOUND_MSG + e.getMessage());
        }
        if (null != projectId) {
            logger.info(Constants.PROJECT_FOUND_MSG);
            taskResultList = new ArrayList<>();
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                try {
                    taskResultList.add(taskUtilService.assignTask(taskDetails, projectId));
                } catch (Exception e) {
                    logger.warning(Constants.TASK_NOT_FOUND_MSG + e.getMessage());
                }
            }
        }
        if (null == taskResultList) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return taskResultList;
    }

    @PostMapping("/update/task")
    public List<TaskDetails> updateTask(@RequestBody @NotNull RegionClass regionClass) {
        projectId = null;
        try {
            projectId = projectUtilService.findProjectByName(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.PROJECT_NOT_FOUND_MSG + e.getMessage());
        }
        if (null != projectId) {
            logger.info(Constants.PROJECT_FOUND_MSG);
            taskResultList = new ArrayList<>();
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                try {
                    taskResultList.add(taskUtilService.updateTask(taskDetails, projectId));
                } catch (Exception e) {
                    logger.warning(Constants.TASK_NOT_FOUND_MSG + e.getMessage());
                }
            }
        }

        if (null == taskResultList) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return taskResultList;
    }

    @PostMapping("/update/user/task")
    public List<TaskDetails> updateUserTask(@RequestBody @NotNull RegionClass regionClass) {
        projectId = null;
        try {
            projectId = userUtilService.findUser(regionClass.getUserName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.USER_NOT_FOUND_MSG + e.getMessage());
        }
        if (null != projectId) {
            logger.info(Constants.USER_FOUND_PROJECT_MSG);
            taskResultList = new ArrayList<>();
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                try {
                    taskResultList.add(taskUtilService.updateTask(taskDetails, projectId));
                } catch (Exception e) {
                    logger.warning(Constants.TASK_NOT_FOUND_MSG + e.getMessage());
                }
            }
        }
        if (null == taskResultList) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return taskResultList;
    }

    @PostMapping("/delete/task")
    public List<TaskDetails> deleteTask(@RequestBody @NotNull RegionClass regionClass) {
        projectId = null;
        try {
            projectId = projectUtilService.findProjectByName(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.PROJECT_NOT_FOUND_MSG + e.getMessage());
        }
        if (null != projectId) {
            logger.info(Constants.PROJECT_FOUND_MSG);
            taskResultList = new ArrayList<>();
            for (TaskDetails taskDetails : regionClass.getTaskList()) {
                try {
                    taskResultList.add(taskUtilService.deleteTask(taskDetails, projectId));
                } catch (Exception e) {
                    logger.warning(Constants.TASK_NOT_FOUND_MSG + e.getMessage());
                }
            }
        }
        if (null == taskResultList) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return taskResultList;
    }

    @PostMapping("/view/active/task")
    public List<TaskDetails> viewActiveTask(@RequestBody @NotNull RegionClass regionClass) {
        projectId = null;
        try {
            projectId = projectUtilService.findProjectByName(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.PROJECT_NOT_FOUND_MSG + e.getMessage());
        }
        if (null != projectId) {
            logger.info(Constants.PROJECT_FOUND_MSG);
            taskResultList = new ArrayList<>();
            try {
                taskResultList = taskUtilService.viewActiveTask(projectId);
            } catch (Exception e) {
                logger.warning(Constants.TASK_NOT_FOUND_MSG + e.getMessage());
            }
        }
        if (null == taskResultList) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return taskResultList;
    }

    @PostMapping("/view/user/active/task")
    public List<TaskDetails> viewUserActiveTask(@RequestBody @NotNull RegionClass regionClass) {
        projectId = null;
        userId = null;
        user = null;
        try {
            user = userUtilService.findUser(regionClass.getUserName());
            userId = user.getUserId();
            projectId = user.getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.USER_NOT_FOUND_MSG + e.getMessage());
        }
        if (null != projectId && null != userId) {
            logger.info(Constants.USER_FOUND_PROJECT_MSG);
            taskResultList = new ArrayList<>();
            try {
                taskResultList = taskUtilService.viewUserActiveTask(projectId, userId);
            } catch (Exception e) {
                logger.warning(Constants.TASK_NOT_FOUND_MSG + e.getMessage());
            }
        }
        if (null == taskResultList) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return taskResultList;
    }

    @PostMapping("/view/all/task")
    public List<TaskDetails> viewAllTask(@RequestBody @NotNull RegionClass regionClass) {
        projectId = null;
        try {
            projectId = projectUtilService.findProjectByName(regionClass.getProjectName()).getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.PROJECT_NOT_FOUND_MSG + e.getMessage());
        }
        if (null != projectId) {
            logger.info(Constants.PROJECT_FOUND_MSG);
            taskResultList = new ArrayList<>();
            try {
                taskResultList = taskUtilService.viewAllTask(projectId);
            } catch (Exception e) {
                logger.warning(Constants.TASK_NOT_FOUND_MSG + e.getMessage());
            }
        }
        if (null == taskResultList) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return taskResultList;
    }

    @PostMapping("/view/user/all/task")
    public List<TaskDetails> viewUserAllTask(@RequestBody @NotNull RegionClass regionClass) {
        projectId = null;
        userId = null;
        user = null;
        try {
            user = userUtilService.findUser(regionClass.getUserName());
            userId = user.getUserId();
            projectId = user.getProjectId();
        } catch (NullPointerException e) {
            logger.warning(Constants.USER_NOT_FOUND_MSG + e.getMessage());
        }
        if (null != projectId && null != userId) {
            logger.info(Constants.USER_FOUND_PROJECT_MSG);
            taskResultList = new ArrayList<>();
            try {
                taskResultList = taskUtilService.viewUserAllTask(projectId, userId);
            } catch (Exception e) {
                logger.warning(Constants.TASK_NOT_FOUND_MSG + e.getMessage());
            }
        }
        if (null == taskResultList) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return taskResultList;
    }
}