package com.ivy.model;

import com.sun.istack.internal.NotNull;

import java.util.Date;

/**
 * Data class for a task.
 */
public class Task {
    public String name;
    public Date dueDate;
    public String project;
    public boolean isComplete;

    public Task(@NotNull String name, Date dueDate, String project, boolean isComplete) {
        this.name = name;
        this.dueDate = dueDate;
        this.project = project;
        this.isComplete = isComplete;
    }
}