package com.bau.taskportal.util.task;

import com.bau.taskportal.bean.task.TaskDetails;
import com.bau.taskportal.constant.Constants;
import com.bau.taskportal.entity.Task;
import com.bau.taskportal.repository.TaskRepository;
import com.bau.taskportal.util.project.ProjectUtilService;
import com.bau.taskportal.util.user.UserUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TaskUtilService {

    @Autowired
    UserUtilService userUtilService;
    @Autowired
    ProjectUtilService projectUtilService;
    @Autowired
    TaskRepository taskRepository;
    private Task task = null;

    Logger logger = Logger.getLogger(TaskUtilService.class.getName());

    public TaskDetails createNewTask(TaskDetails taskDetails, Integer projectId) throws NullPointerException {
        if (null == taskRepository.findByProjectIdAndTaskDescriptionAndTaskCategoryAndAssignedToAndActiveInd(projectId, taskDetails.getTaskDescription(), taskDetails.getTaskCategory(), userUtilService.findUserId(taskDetails.getAssignedTo()), Constants.TASK_IS_ACTIVE)) {
            task = new Task();
            task.setTaskCategory(taskDetails.getTaskCategory());
            task.setTaskDescription(taskDetails.getTaskDescription());
            task.setActiveInd(Constants.TASK_IS_ACTIVE);
            task.setAssignedBy(userUtilService.findUserId(taskDetails.getAssignedBy()));
            task.setAssignedTo(userUtilService.findUserId(taskDetails.getAssignedTo()));
            task.setDueDate(taskDetails.getDueDate());
            task.setFilePath(taskDetails.getFilePath());
            task.setFrequency(taskDetails.getFrequency());
            task.setProjectId(projectId);
            task.setRemarks(taskDetails.getRemarks());
            task.setLoggedDate(taskDetails.getLoggedDate());
            task.setStatus(taskDetails.getStatus());
            task.setPriority(taskDetails.getPriority());
            task.setCreateTimestamp(new Timestamp(new Date().getTime()));
            task.setUpdatedTimestamp(new Timestamp(new Date().getTime()));
            logger.info(taskDetails.getTaskDescription() + " " + Constants.TASK_CREATING_FOR + " " + taskDetails.getAssignedTo());
            return setTaskDetails(taskRepository.save(task));
        }
        logger.info(taskDetails.getTaskDescription() + " " + Constants.TASK_ALREADY_ASSIGNED_FOR + " " + taskDetails.getAssignedTo());
        return null;
    }

    public TaskDetails assignTask(TaskDetails taskDetails, Integer projectId) throws NullPointerException {
        task = taskRepository.findByProjectIdAndTaskDescriptionAndTaskCategoryAndAssignedToAndAssignedByAndActiveInd(projectId, taskDetails.getTaskDescription(), taskDetails.getTaskCategory(), userUtilService.findUserId(taskDetails.getAssignedToOld()), userUtilService.findUserId(taskDetails.getAssignedBy()), Constants.TASK_IS_ACTIVE);
        if (null != task) {
            if (null == taskRepository.findByProjectIdAndTaskDescriptionAndTaskCategoryAndAssignedToAndActiveInd(projectId, taskDetails.getTaskDescription(), taskDetails.getTaskCategory(), userUtilService.findUserId(taskDetails.getAssignedToNew()), Constants.TASK_IS_ACTIVE)) {
                task.setAssignedTo(userUtilService.findUserId(taskDetails.getAssignedToNew()));
                task.setUpdatedTimestamp(new Timestamp(new Date().getTime()));
                logger.info(taskDetails.getTaskDescription() + Constants.TASK_CHANGED_FROM + taskDetails.getAssignedToOld() + " to " + taskDetails.getAssignedToNew());
                return setTaskDetails(taskRepository.save(task));
            }
            logger.info(taskDetails.getTaskDescription() + Constants.TASK_ALREADY_ASSIGNED_FOR + taskDetails.getAssignedToNew());
            return null;
        }
        logger.info(taskDetails.getTaskDescription() + Constants.TASK_NOT_ASSIGNED_FOR + taskDetails.getAssignedToOld());
        return null;
    }

    public TaskDetails updateTask(TaskDetails taskDetails, Integer projectId) throws NullPointerException {
        if (null != projectUtilService.findProjectById(projectId)) {
            task = taskRepository.findByProjectIdAndTaskDescriptionAndTaskCategoryAndAssignedToAndAssignedBy(projectId, taskDetails.getTaskDescription(), taskDetails.getTaskCategory(), userUtilService.findUserId(taskDetails.getAssignedTo()), userUtilService.findUserId(taskDetails.getAssignedBy()));
            if (null != task) {
                task.setTaskCategory((String) getValue(task.getTaskCategory(), taskDetails.getTaskCategory()));
                task.setTaskDescription((String) getValue(task.getTaskDescription(), taskDetails.getTaskDescription()));
                task.setPriority((String) getValue(task.getPriority(), taskDetails.getPriority()));
                task.setActiveInd((String) getValue(task.getActiveInd(), taskDetails.getActiveInd()));
                task.setFrequency((String) getValue(task.getFrequency(), taskDetails.getFrequency()));
                task.setRemarks((String) getValue(task.getRemarks(), taskDetails.getRemarks()));
                task.setFilePath((String) getValue(task.getFilePath(), taskDetails.getFilePath()));
                task.setStatus((String) getValue(task.getStatus(), taskDetails.getStatus()));
                task.setUpdatedTimestamp(new Timestamp(new Date().getTime()));
                task.setDueDate((Date) getValue(task.getDueDate(), taskDetails.getDueDate()));
                logger.info(taskDetails.getTaskDescription() + Constants.TASK_UPDATED_FOR + taskDetails.getAssignedTo());
                return setTaskDetails(taskRepository.save(task));
            }
        }
        logger.info(taskDetails.getTaskDescription() + Constants.TASK_NOT_ASSIGNED_FOR + taskDetails.getAssignedTo());
        return null;
    }

    public TaskDetails deleteTask(TaskDetails taskDetails, Integer projectId) throws NullPointerException {
        task = taskRepository.findByProjectIdAndTaskDescriptionAndTaskCategoryAndAssignedToAndAssignedBy(projectId, taskDetails.getTaskDescription(), taskDetails.getTaskCategory(), userUtilService.findUserId(taskDetails.getAssignedTo()), userUtilService.findUserId(taskDetails.getAssignedBy()));
        if (null != task) {
            taskRepository.delete(task);
            logger.info(taskDetails.getTaskDescription() + Constants.TASK_DELETED_FOR + taskDetails.getAssignedTo());
            return setTaskDetails(task);
        }
        logger.info(taskDetails.getTaskDescription() + Constants.TASK_NOT_ASSIGNED_FOR + taskDetails.getAssignedTo());
        return null;
    }

    public List<TaskDetails> viewActiveTask(Integer projectId) throws NullPointerException {
        return getTaskDetailsList(taskRepository.findAllByProjectIdAndActiveInd(projectId, Constants.TASK_IS_ACTIVE));
    }

    public List<TaskDetails> viewUserActiveTask(Integer projectId, Integer userId) throws NullPointerException {
        return null != projectUtilService.findProjectById(projectId) ? getTaskDetailsList(taskRepository.findAllByProjectIdAndAssignedToAndActiveInd(projectId, userId, Constants.TASK_IS_ACTIVE)) : null;
    }

    public List<TaskDetails> viewAllTask(Integer projectId) throws NullPointerException {
        return getTaskDetailsList(taskRepository.findAllByProjectId(projectId));
    }

    public List<TaskDetails> viewUserAllTask(Integer projectId, Integer userId) throws NullPointerException {
        return null != projectUtilService.findProjectById(projectId) ? getTaskDetailsList(taskRepository.findAllByProjectIdAndAssignedTo(projectId, userId)) : null;
    }

    private Object getValue(Object oldValue, Object newValue) {
        return null != newValue ? newValue : oldValue;
    }

    private List<TaskDetails> getTaskDetailsList(List<Task> taskList) {
        List<TaskDetails> taskResultList = new ArrayList<>();
        for (Task task1 : taskList) {
            taskResultList.add(setTaskDetails(task1));
        }
        return taskResultList;
    }

    private TaskDetails setTaskDetails(Task task) {
        if (null != task) {
            TaskDetails taskDetails = new TaskDetails();
            taskDetails.setTaskDescription(task.getTaskDescription());
            taskDetails.setTaskCategory(task.getTaskCategory());
            taskDetails.setAssignedTo(userUtilService.findUserName(task.getAssignedTo()));
            taskDetails.setAssignedBy(userUtilService.findUserName(task.getAssignedBy()));
            taskDetails.setStatus(task.getStatus());
            taskDetails.setActiveInd(task.getActiveInd());
            taskDetails.setFilePath(task.getFilePath());
            taskDetails.setPriority(task.getPriority());
            taskDetails.setFrequency(task.getFrequency());
            taskDetails.setRemarks(task.getRemarks());
            taskDetails.setLoggedDate(task.getLoggedDate());
            taskDetails.setDueDate(task.getDueDate());
            return taskDetails;
        }
        return null;
    }

}
