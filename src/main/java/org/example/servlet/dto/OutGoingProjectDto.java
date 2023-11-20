package org.example.servlet.dto;

import org.example.model.PerformerEntity;
import org.example.model.TaskEntity;

import java.util.List;
import java.util.Objects;

public class OutGoingProjectDto {
    private String projectId;
    private String projectName;
    private String projectStartDate;
    private String projectDeadlineDate;
    private List<PerformerEntity> projectPerformers;
    private List<TaskEntity> projectTasks;

    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectStartDate() {
        return projectStartDate;
    }

    public String getProjectDeadlineDate() {
        return projectDeadlineDate;
    }

    public List<PerformerEntity> getProjectPerformers() {
        return projectPerformers;
    }

    public List<TaskEntity> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public void setProjectDeadlineDate(String projectDeadlineDate) {
        this.projectDeadlineDate = projectDeadlineDate;
    }

    public void setProjectPerformers(List<PerformerEntity> projectPerformers) {
        this.projectPerformers = projectPerformers;
    }

    public void setProjectTasks(List<TaskEntity> projectTasks) {
        this.projectTasks = projectTasks;
    }

    @Override
    public String toString() {
        return "OutGoingProjectDto{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectStartDate='" + projectStartDate + '\'' +
                ", projectDeadlineDate='" + projectDeadlineDate + '\'' +
                ", projectPerformers=" + projectPerformers +
                ", projectTasks=" + projectTasks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutGoingProjectDto)) return false;
        OutGoingProjectDto that = (OutGoingProjectDto) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(projectName, that.projectName) && Objects.equals(projectStartDate, that.projectStartDate) && Objects.equals(projectDeadlineDate, that.projectDeadlineDate) && Objects.equals(projectPerformers, that.projectPerformers) && Objects.equals(projectTasks, that.projectTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, projectStartDate, projectDeadlineDate, projectPerformers, projectTasks);
    }
}
