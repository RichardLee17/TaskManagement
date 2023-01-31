package com.bau.taskportal.constant;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Constant class");
    }

    public static final String TASK_IS_ACTIVE="Active";
    public static final String TASK_IS_NEW = "New";
    public static final String TASK_WARNING_MSG ="Internal Error!!! ";

    public static final String TASK_WARNING_MSG1 ="Task not found!!! ";
    public static final String TASK_WARNING_MSG2 ="Project Not found!!! ";
    public static final String TASK_WARNING_MSG3 ="User Not found!!! ";

    public static final String TASK_NOT_ASSIGNED_FOR = " task not assigned for ";
    public static final String TASK_ALREADY_ASSIGNED_FOR = " task already assigned for ";
    public static final String TASK_CREATING_FOR = " task creating for ";
    public static final String TASK_CHANGED_FROM = " task changed from ";
    public static final String TASK_UPDATED_FOR = " task updated for ";
    public static final String TASK_DELETED_FOR = " task deleted for ";
}
