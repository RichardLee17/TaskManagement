package com.bau.taskportal.util.task;

import com.bau.taskportal.bean.task.TaskDetails;
import com.bau.taskportal.entity.Task;
import com.bau.taskportal.repository.TaskRepository;
import com.bau.taskportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskUtilService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private final String taskNotAssignedStrPrefix = " task not assigned for ";
    private final String taskAlrAssignStrPrefix = " task already assigned for ";
    private final String taskCreatedStrPrefix = " task creating for ";

    private final String taskChangedStrPrefix = " task changed from ";

    private final String taskUpdatedStrPrefix = " task updated for ";

    private final String taskDeletedStrPrefix = " task deleted for ";

    private Task task;

    public TaskDetails createNewTask(TaskDetails taskDetails, Integer projectId) {
        if (null == taskRepository.findByProjectIdAndTaskDescriptionAndTaskCategoryAndAssignedToAndActiveInd(projectId, taskDetails.getTaskDescription(), taskDetails.getTaskCategory(), findUserId(taskDetails.getAssignedTo()), "Active")) {
            task = new Task();
            task.setTaskCategory(taskDetails.getTaskCategory());
            task.setTaskDescription(taskDetails.getTaskDescription());
            task.setActiveInd("Active");
            task.setAssignedBy(findUserId(taskDetails.getAssignedBy()));
            task.setAssignedTo(findUserId(taskDetails.getAssignedTo()));
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
            System.out.println(taskDetails.getTaskDescription() + " " + taskCreatedStrPrefix + " " + taskDetails.getAssignedTo());
            return getResultedDetails(taskRepository.save(task));
        }
        System.out.println(taskDetails.getTaskDescription() + " " + taskAlrAssignStrPrefix + " " + taskDetails.getAssignedTo());
        return null;
    }

    public TaskDetails assignTask(TaskDetails taskDetails, Integer projectId) {
        task = taskRepository.findByProjectIdAndTaskDescriptionAndTaskCategoryAndAssignedToAndAssignedByAndActiveInd(projectId, taskDetails.getTaskDescription(), taskDetails.getTaskCategory(), findUserId(taskDetails.getAssignedToOld()), findUserId(taskDetails.getAssignedBy()), "Active");
        if (null != task) {
            if (null == taskRepository.findByProjectIdAndTaskDescriptionAndTaskCategoryAndAssignedToAndActiveInd(projectId, taskDetails.getTaskDescription(), taskDetails.getTaskCategory(), findUserId(taskDetails.getAssignedToNew()), "Active")) {
                task.setAssignedTo(findUserId(taskDetails.getAssignedToNew()));
                task.setUpdatedTimestamp(new Timestamp(new Date().getTime()));
                System.out.println(taskDetails.getTaskDescription() + taskChangedStrPrefix + taskDetails.getAssignedToOld() + " to " + taskDetails.getAssignedToNew());
                return getResultedDetails(taskRepository.save(task));
            }
            System.out.println(taskDetails.getTaskDescription() + taskAlrAssignStrPrefix + taskDetails.getAssignedToNew());
            return null;
        }
        System.out.println(taskDetails.getTaskDescription() + taskNotAssignedStrPrefix + taskDetails.getAssignedToOld());
        return null;
    }

    public TaskDetails updateTask(TaskDetails taskDetails, Integer projectId) {
        task = taskRepository.findByProjectIdAndTaskDescriptionAndTaskCategoryAndAssignedToAndAssignedBy(projectId, taskDetails.getTaskDescription(), taskDetails.getTaskCategory(), findUserId(taskDetails.getAssignedTo()), findUserId(taskDetails.getAssignedBy()));
        if (null != task) {
            task.setTaskCategory((String) getValue(task.getTaskCategory(), taskDetails.getTaskCategory()));
            task.setTaskDescription((String) getValue(task.getTaskDescription(), taskDetails.getTaskDescription()));
            task.setPriority((String) getValue(task.getPriority(), taskDetails.getPriority()));
            task.setActiveInd((String) getValue(task.getActiveInd(), taskDetails.getActiveInd()));
            task.setFrequency((String) getValue(task.getFrequency(), taskDetails.getFrequency()));
            task.setRemarks((String) getValue(task.getRemarks(), taskDetails.getRemarks()));
            task.setStatus((String) getValue(task.getStatus(), taskDetails.getStatus()));
            task.setUpdatedTimestamp(new Timestamp(new Date().getTime()));
            task.setDueDate((Date) getValue(task.getDueDate(), taskDetails.getDueDate()));
            System.out.println(taskDetails.getTaskDescription() + taskUpdatedStrPrefix + taskDetails.getAssignedTo());
            return getResultedDetails(taskRepository.save(task));
        }
        System.out.println(taskDetails.getTaskDescription() + taskNotAssignedStrPrefix + taskDetails.getAssignedTo());
        return null;
    }

    public TaskDetails deleteTask(TaskDetails taskDetails, Integer projectId) {
        task = taskRepository.findByProjectIdAndTaskDescriptionAndTaskCategoryAndAssignedToAndAssignedBy(projectId, taskDetails.getTaskDescription(), taskDetails.getTaskCategory(), findUserId(taskDetails.getAssignedTo()), findUserId(taskDetails.getAssignedBy()));
        if (null != task) {
            taskRepository.delete(task);
            System.out.println(taskDetails.getTaskDescription() + taskDeletedStrPrefix + taskDetails.getAssignedTo());
            return getResultedDetails(task);
        }
        System.out.println(taskDetails.getTaskDescription() + taskNotAssignedStrPrefix + taskDetails.getAssignedTo());
        return null;
    }

    public List<TaskDetails> viewActiveTask(Integer projectId) {
        return getResultedList(taskRepository.findAllByProjectIdAndActiveInd(projectId, "Active"));
    }

    public List<TaskDetails> viewAllTask(Integer projectId) {
        return getResultedList(taskRepository.findAllByProjectId(projectId));
    }

    private Object getValue(Object oldValue, Object newValue) {
        return null != newValue ? newValue : oldValue;
    }

    private List<TaskDetails> getResultedList(List<Task> taskList) {
        List<TaskDetails> taskResultList = new ArrayList<>();
        for (Task task : taskList) {
            taskResultList.add(getResultedDetails(task));
        }
        return taskResultList;
    }

    private TaskDetails getResultedDetails(Task task) {
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setTaskDescription(task.getTaskDescription());
        taskDetails.setTaskCategory(task.getTaskCategory());
        taskDetails.setAssignedTo(findUserName(task.getAssignedTo()));
        taskDetails.setAssignedBy(findUserName(task.getAssignedBy()));
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

    private int findUserId(String username) {
        return userRepository.findByUserName(username).getUserId();
    }

    private String findUserName(int userId) {
        return userRepository.findByUserId(userId).getUserName();
    }
}
