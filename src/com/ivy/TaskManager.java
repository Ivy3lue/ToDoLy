package com.ivy;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Contains logic for managing the To Do list.
 */
class TaskManager {

    private List<Task> tasks = new ArrayList<>();

    /**
     * Returns sorted (by date) list of tasks
     *
     * @return the list of tasks
     */
    public List<Task> getTasks() {
        return sortByDate(tasks);
    }

    /**
     * Returns sorted (by date) list of completed tasks
     *
     * @return a list of completed tasks
     */
    public List<Task> getCompletedTasks() {
        return sortByDate(tasks.stream()
                .filter(task -> task.isComplete)
                .collect(Collectors.toList()));
    }

    /**
     * Returns sorted (by date) list of uncompleted tasks
     *
     * @return a list of uncompleted tasks
     */
    public List<Task> getUncompletedTasks() {
        return sortByDate(tasks.stream()
                .filter(task -> !task.isComplete)
                .collect(Collectors.toList()));
    }

    /**
     * Returns sorted (by date) list of overdue tasks
     *
     * @return a list of overdue tasks
     */
    public List<Task> getOverdueTasks() {
        Date currentDate = new Date();
        return sortByDate(getUncompletedTasks().stream()
                .filter(task -> task.dueDate != null && task.dueDate.after(currentDate))
                .collect(Collectors.toList()));
    }

    /**
     * Adds a task
     *
     * @param taskToAdd the task to be added
     */
    public void addTask(Task taskToAdd) {
        tasks.add(taskToAdd);
    }

    /**
     * Searches for a task by its name
     * @param nameOfSearchedTask the name of the task searched
     * @return if found returns the task searched, otherwise returns null
     */
    @Nullable
    public Task findTask(@NotNull String nameOfSearchedTask) {
        Optional<Task> optionalTask = tasks.stream()
                .filter(task -> task.name.equals(nameOfSearchedTask))
                .findFirst();
        try {
            return optionalTask.get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Removes a task
     *
     * @param taskToRemove the task to be removed
     */
    public void remove(Task taskToRemove) {
        tasks.remove(taskToRemove);
    }

    //TODO archive instead?
    public void removeAllCompleted() {
        tasks.removeAll(getCompletedTasks());
    }

    /**
     * Adds mock data for developing and testing purposes.
     */
    public void mockData() {
        tasks.add(new Task("first mock task", new Date(1524002400000L), false));
        tasks.add(new Task("second mock task", new Date(1537999200000L), true));
        tasks.add(new Task("third mock task", null, false));
        tasks.add(new Task("fourth mock task", new Date(1528236000000L), false));
        tasks.add(new Task("fifth mock task", null, true));
        tasks.add(new Task("sixth mock task", new Date(1523484000000L), false));
    }

    private List<Task> sortByDate(List<Task> tasks) {
        List<Task> sortedTasks = tasks.stream()
                .filter(task -> task.dueDate != null)
                .sorted(Comparator.comparing(currentTask -> currentTask.dueDate))
                .collect(Collectors.toList());
        sortedTasks.addAll(tasks.stream()
                .filter(task -> task.dueDate == null)
                .collect(Collectors.toList()));
        return sortedTasks;
    }

}