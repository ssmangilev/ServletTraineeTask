package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * {@link PerformerEntity} represents database table.
 * It is entity class.
 */
public class PerformerEntity {
    private UUID performerId;
    private String name;
    private String email;
    private String role;
    private List<ProjectEntity> performerProjects;
    private List<TaskEntity> performerTasks;

    public UUID getPerformerId() {
        return performerId;
    }

    public void setPerformerId(UUID performerId) {
        this.performerId = performerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ProjectEntity> getPerformerProjects() {
        return performerProjects;
    }

    public void setPerformerProjects(List<ProjectEntity> performerProjects) {
        this.performerProjects = performerProjects;
    }

    public List<TaskEntity> getPerformerTasks() {
        return performerTasks;
    }

    public void setPerformerTasks(List<TaskEntity> performerTasks) {
        this.performerTasks = performerTasks;
    }

    public PerformerEntity(UUID uuid) {
        this.performerId = uuid;
    }

    public PerformerEntity() {
        this.performerProjects = new ArrayList<>();
        this.performerTasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "PerformerEntity{" +
                "performerId=" + performerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", performerProjects=" + performerProjects +
                ", performerTasks=" + performerTasks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PerformerEntity)) return false;
        PerformerEntity performer = (PerformerEntity) o;
        return Objects.equals(performerId, performer.performerId) && Objects.equals(name, performer.name) && Objects.equals(email, performer.email) && Objects.equals(role, performer.role) && Objects.equals(performerProjects, performer.performerProjects) && Objects.equals(performerTasks, performer.performerTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(performerId, name, email, role, performerProjects, performerTasks);
    }
}
