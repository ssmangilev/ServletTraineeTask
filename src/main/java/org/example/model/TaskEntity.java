package org.example.model;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * {@link TaskEntity} represents database table.
 * It is entity class.
 */
public class TaskEntity {
    private UUID taskId;
    private String taskName;
    private String taskDescription;
    private String taskPriority;
    private String taskStatus;
    private Timestamp taskDeadline;
    private PerformerEntity taskPerformer;
    private ProjectEntity taskProject;

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Timestamp getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(Timestamp taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public PerformerEntity getTaskPerformer() {
        return taskPerformer;
    }

    public void setTaskPerformer(PerformerEntity taskPerformer) {
        this.taskPerformer = taskPerformer;
    }

    public ProjectEntity getTaskProject() {
        return taskProject;
    }

    public void setTaskProject(ProjectEntity taskProject) {
        this.taskProject = taskProject;
    }

    public TaskEntity(UUID taskId) {
        this.taskId = taskId;
    }

    public TaskEntity(String taskName, String taskDescription, String taskPriority, String taskStatus,
                      Timestamp taskDeadline, PerformerEntity taskPerformer, ProjectEntity taskProject) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
        this.taskDeadline = taskDeadline;
        this.taskPerformer = taskPerformer;
        this.taskProject = taskProject;
    }

    public TaskEntity() {
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskPriority=" + taskPriority +
                ", taskStatus=" + taskStatus +
                ", taskDeadline=" + taskDeadline +
                ", taskPerformer=" + taskPerformer +
                ", taskProject=" + taskProject +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskEntity)) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(taskId, that.taskId) && Objects.equals(taskName, that.taskName) && Objects.equals(taskDescription, that.taskDescription) && Objects.equals(taskPriority, that.taskPriority) && Objects.equals(taskStatus, that.taskStatus) && Objects.equals(taskDeadline, that.taskDeadline) && Objects.equals(taskPerformer, that.taskPerformer) && Objects.equals(taskProject, that.taskProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName, taskDescription, taskPriority, taskStatus, taskDeadline, taskPerformer, taskProject);
    }
}
