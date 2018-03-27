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
 * Contains all the logic for managing the list of tasks.
 */
class TaskManager {

    private List<Task> tasks = new ArrayList<>();

    List<Task> getUncompletedTasks() {
        return tasks.stream().filter(task -> !task.isComplete).collect(Collectors.toList());
    }

    private List<Task> getCompletedTasks() {
        return tasks
                .stream()
                .filter(task -> task.isComplete)
                .collect(Collectors.toList());
    }

    private List<Task> getOverdueTasks() {
        Date currentDate = new Date();
        return getUncompletedTasks()
                .stream()
                .filter(task -> task.dueDate != null && task.dueDate.after(currentDate))
                .collect(Collectors.toList());
    }

    public List<Task> getAgenda() {
        return sortByDate(getUncompletedTasks());
    }

    public int getCompletedTaskCount() {
        return getCompletedTasks().size();
    }

    public int getUncompletedTaskCount() {
        return getUncompletedTasks().size();
    }

    int getOverdueTasksCount() {
        return getOverdueTasks().size();
    }


    public void addTask(String name, Date date, boolean status) {
        Task task = new Task(name, date, status);
        tasks.add(task);
    }

    @Nullable
    Task findTask(@NotNull String name) {
        Optional<Task> optionalTask = tasks.stream().filter(task -> task.name.equals(name)).findFirst();
        try {
            return optionalTask.get();
        } catch (NoSuchElementException e) {
            return null;
        }
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

    public void remove(Task taskToEdit) {
        tasks.remove(taskToEdit);
    }

    //TODO archive instead?
    public void removeAllCompleted() {
        tasks.removeAll(getCompletedTasks());
    }

    public List<Task> sortByDate(List<Task> tasks) {
        List<Task> sortedTasks = tasks.stream()
                .filter(task -> task.dueDate != null)
                .sorted(Comparator.comparing(currentTask -> currentTask.dueDate))
                .collect(Collectors.toList());
        sortedTasks.addAll(tasks.stream().filter(task -> task.dueDate == null).collect(Collectors.toList()));
        return sortedTasks;
    }

    public List<Task> getSortedTasks() {
        return sortByDate(tasks);
    }

}