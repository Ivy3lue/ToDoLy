package com.ivy;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Contains implementation of the ToDoLy program.
 */
public class MainPresenter extends AbsBasePresenter<Mvp.View> implements Mvp.Presenter {

    private List<Task> tasks = new ArrayList<>();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd", Locale.ENGLISH);

    @Override
    public void onAttach(Mvp.View view) {
        super.onAttach(view);

        mockData();
        view.showWelcomeMenu(getCompletedTaskCount(), getUncompletedTaskCount());

        while (true) {
            view.showMenu();
            String userInput = view.getUserInput();

            if ("9".equals(userInput)) {
                view.exit();
                return;
            }

            switch (userInput) {
                case "1":
                    view.showTasks(getUncompletedTasks());
                    break;

                case "2":
                    addNewTask();
                    break;

                //edits can be done multiple times until "save and exit" is chosen
                case "3":
                    view.print("Please enter name of the task to view");
                    Task taskToEdit = findTask(view.getInput());
                    if (taskToEdit != null) {
                        printTask(taskToEdit);
                    } else {
                        view.print("Task not found. Please try again... ");
                        break;
                    }

                    while (true) {
                        view.print("Press: ");
                        view.print("(1) to edit task name");
                        view.print("(2) to edit date");
                        view.print("(3) to mark task finished");
                        view.print("(4) to mark task unfinished");
                        view.print("(5) to delete task");
                        view.print("(9) to save and exit");

                        String editChoice = view.getInput();

                        if (editChoice.equals("9")) {
                            break;
                        }

                        switch (editChoice) {
                            case "1":
                                view.print("Please enter new task name");
                                taskToEdit.name = view.getInput();
                                view.print("Edited name...");
                                break;

                            case "2":
                                view.print("Please enter due date in the following dateFormat: yyyy mm dd");
                                taskToEdit.dueDate = parseDate(view.getInput());
                                if (taskToEdit.dueDate != null) {
                                    view.print("Edited date..." + taskToEdit.dueDate);
                                }
                                break;

                            case "3":
                                taskToEdit.isComplete = true;
                                view.print("Marked finished...");
                                break;

                            case "4":
                                taskToEdit.isComplete = false;
                                view.print("Marked unfinished...");
                                break;

                            case "5":
                                tasks.remove(taskToEdit);
                                break;

                            default:
                                view.showError();
                        }
                    }
                    break;

                case "4":
                    tasks.removeAll(getCompletedTasks());
                    break;

                case "5":
                    tasks.forEach(this::printTask);
                    break;

                default:
                    view.showError();
            }

        }
    }

    private List<Task> getUncompletedTasks() {
        return tasks.stream().filter(task -> !task.isComplete).collect(Collectors.toList());
    }

    private List<Task> getCompletedTasks() {
        return tasks.stream().filter(task -> task.isComplete).collect(Collectors.toList());
    }

    //TODO optimize following two methods without creating new lists
    private int getCompletedTaskCount() {
        return getUncompletedTasks().size();
    }

    private int getUncompletedTaskCount() {
        return getCompletedTasks().size();
    }

    //status is directly set to unfinished
    private void addNewTask() {
        view.print("Please enter task name: ");
        String taskName = view.getInput();
        view.print("Please enter due date in the following dateFormat: yyyy mm dd");
        Date taskDate = parseDate(view.getInput());
        Task addedTask = new Task(taskName, taskDate, false);
        tasks.add(addedTask);
        view.print("Added:");
        printTask(addedTask);
    }

    @Nullable
    private Task findTask(@NotNull String name) {
        Optional<Task> optionalTask = tasks.stream().filter(task -> task.name.equals(name)).findFirst();
        try {
            return optionalTask.get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Nullable
    private Date parseDate(String dateInput) {
        try {
            return dateFormat.parse(dateInput);
        } catch (ParseException e) {
            view.print("Invalid input. Date not set.");
            return null;
        }
    }

    @Override
    public void printTask(Task task) {
        String status;
        String taskToString;
        if (task.isComplete) {
            status = "finished";
        } else {
            status = "unfinished";
        }
        if (task.dueDate == null) {
            taskToString = "Task: " + task.name + ", " + status;
        } else {
            taskToString = "Task: " + task.name + ", due " + dateFormat.format(task.dueDate) + ", " + status;
        }
        view.print(taskToString);
    }

    /**
     * Adds mock data for developing and testing purposes.
     */
    private void mockData() {

        tasks.add(new Task("first mock task", new Date(), false));
        tasks.add(new Task("second mock task", new Date(), true));
        tasks.add(new Task("third mock task", null, false));
        tasks.add(new Task("fourth mock task", new Date(1528236000000L), false));
        tasks.add(new Task("fifth mock task", new Date(), true));
        tasks.add(new Task("sixth mock task", new Date(), true));
    }
}