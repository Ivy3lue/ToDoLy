package com.ivy;

import java.util.Date;

/**
 * Data class for a task.
 */
public class Task {
    public final String name;
    public final Date dueDate;
    public final boolean isComplete;

    public Task(String name, Date dueDate, boolean isComplete) {
        this.name = name;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
    }

    @Override
    public String toString() {
        return name + " , due " + dueDate;
    }
}
