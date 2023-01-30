package com.bau.taskportal.constant;

public class TaskConstants {
    private TaskConstants() {
        throw new IllegalStateException("Constant class");
    }

    public static final String TASK_IS_ACTIVE="Active";
    public static final String TASK_IS_INACTIVE="InActive";
    public static final String TASK_NOT_ASSIGNED_FOR = " task not assigned for ";
    public static final String TASK_ALREADY_ASSIGNED_FOR = " task already assigned for ";
    public static final String TASK_CREATING_FOR = " task creating for ";
    public static final String TASK_CHANGED_FROM = " task changed from ";
    public static final String TASK_UPDATED_FOR = " task updated for ";
    public static final String TASK_DELETED_FOR = " task deleted for ";
}
