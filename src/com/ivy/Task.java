package com.ivy;

import java.util.Date;

/**
 * Data class for a task.
 */
public class Task {
    public String name;
    public Date dueDate;
    public boolean isComplete;

    public Task(String name, Date dueDate, boolean isComplete) {
        this.name = name;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
    }
}