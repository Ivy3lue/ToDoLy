package com.ivy;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Contains logic for managing the To Do list.
 */
class TaskManager {

    private List<Task> tasks;

    //temp used by Test class
    public TaskManager() {
    }

    public TaskManager(List<Task> tasks) {
        this.tasks = tasks;
    }

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
       List<Task> completedTasks = tasks.stream()
                .filter(task -> task.isComplete)
                .collect(Collectors.toList());
       if (completedTasks.isEmpty()) {
           return completedTasks;
       } else
           return sortByDate(completedTasks);
    }

    /**
     * Returns sorted (by date) list of uncompleted tasks
     *
     * @return a list of uncompleted tasks
     */
    @NotNull
    public List<Task> getUncompletedTasks() {
        List<Task> uncompletedTasks = tasks.stream()
                .filter(task -> !task.isComplete)
                .collect(Collectors.toList());
        if (uncompletedTasks.isEmpty()) {
            return uncompletedTasks;
        } else
            return sortByDate(uncompletedTasks);
    }

    /**
     * Returns sorted (by date) list of overdue tasks
     *
     * @return a list of overdue tasks
     */
    @NotNull
    public List<Task> getOverdueTasks() {
        Date currentDate = new Date();
        return sortByDate(getUncompletedTasks().stream()
                .filter(task -> task.dueDate != null && task.dueDate.before(currentDate))
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
     *
     * @param nameOfSearchedTask the name of the task searched
     * @return if found returns the task searched, otherwise returns null
     */
    @Nullable
    public Task findTask(@NotNull String nameOfSearchedTask) {
        Optional<Task> optionalTask = tasks.stream()
                .filter(task -> nameOfSearchedTask.equals(task.name))
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

    /**
     * Removes all tasks
     */
    public void removeAll() {
        tasks.clear();
    }

    /**
     * Removes multiple tasks
     *
     * @param taskPredicate the predicate to filter tasks for removing
     */
    public void removeAll(Predicate<Task> taskPredicate) {
        List<Task> tasksToRemove = tasks
                .stream()
                .filter(taskPredicate)
                .collect(Collectors.toList());
        tasks.removeAll(tasksToRemove);
    }

    /**
     * Returns a list of current projects
     *
     * @return list of project names
     */
    public List<String> listProjects() {
        return tasks.stream().map(task -> task.project)
                .distinct()
                .collect(Collectors.toList());
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