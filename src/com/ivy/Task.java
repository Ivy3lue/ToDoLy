package com.ivy;

import com.sun.istack.internal.NotNull;

import java.util.Date;

/**
 * Data class for a task.
 */
public class Task {
    public String name;
    public Date dueDate;
    public boolean isComplete;

    public Task(@NotNull String name, Date dueDate, boolean isComplete) {
        this.name = name;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
    }
}