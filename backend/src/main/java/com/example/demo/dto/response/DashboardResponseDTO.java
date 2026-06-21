package com.example.demo.dto.response;

public class DashboardResponseDTO {

    private long totalUsers;
    private long totalProjects;
    private long totalTasks;
    private long completedTasks;
    private long totalPublications;
    private long totalFundingRecords;

    public DashboardResponseDTO() {
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(long totalProjects) {
        this.totalProjects = totalProjects;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(long completedTasks) {
        this.completedTasks = completedTasks;
    }

    public long getTotalPublications() {
        return totalPublications;
    }

    public void setTotalPublications(long totalPublications) {
        this.totalPublications = totalPublications;
    }

    public long getTotalFundingRecords() {
        return totalFundingRecords;
    }

    public void setTotalFundingRecords(long totalFundingRecords) {
        this.totalFundingRecords = totalFundingRecords;
    }
}