package org.example.servlet.dto;

import org.example.model.PerformerEntity;
import org.example.model.ProjectEntity;

import java.sql.Timestamp;
import java.util.UUID;

public class IncomingTaskDto {
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
}
