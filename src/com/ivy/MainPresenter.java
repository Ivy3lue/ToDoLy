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
        view.showWelcomeMenu(getCompletedTaskCount(), getUncompletedTaskCount(), getOverdueTasksCount());

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

                case "3":
                    view.print(Messages.ENTER_TASK_NAME);
                    Task taskToEdit = findTask(view.getUserInput());
                    if (taskToEdit != null) {
                        printTask(taskToEdit);
                    } else {
                        view.print(Messages.TASK_NOT_FOUND);
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

                        String editChoice = view.getUserInput();

                        if (editChoice.equals("9")) {
                            break;
                        }

                        switch (editChoice) {
                            case "1":
                                view.print(Messages.ENTER_TASK_NAME);
                                taskToEdit.name = view.getUserInput();
                                view.print(Messages.SUCCESS);
                                break;

                            case "2":
                                view.print(Messages.ENTER_TASK_DUE_DATE);
                                taskToEdit.dueDate = parseDate(view.getUserInput());
                                if (taskToEdit.dueDate != null) {
                                    view.print(Messages.SUCCESS);
                                }
                                break;

                            case "3":
                                taskToEdit.isComplete = true;
                                view.print(Messages.SUCCESS);
                                break;

                            case "4":
                                taskToEdit.isComplete = false;
                                view.print(Messages.SUCCESS);
                                break;

                            case "5":
                                tasks.remove(taskToEdit);
                                break;

                            default:
                                view.print(Messages.ERROR);
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
                    view.print(Messages.ERROR);
            }
        }
    }

    private List<Task> getUncompletedTasks() {
        return tasks.stream().filter(task -> !task.isComplete).collect(Collectors.toList());
    }

    private List<Task> getCompletedTasks() {
        return tasks.stream().filter(task -> task.isComplete).collect(Collectors.toList());
    }

    private List<Task> getOverdueTasks() {
        Date currentDate = new Date();
        return getUncompletedTasks().stream().filter(task -> task.dueDate != null && task.dueDate.after(currentDate)).collect(Collectors.toList());
    }

    //TODO optimize following two methods without creating new lists
    private int getCompletedTaskCount() {
        return getUncompletedTasks().size();
    }

    private int getUncompletedTaskCount() {
        return getCompletedTasks().size();
    }

    private int getOverdueTasksCount() {
        return getOverdueTasks().size();
    }

    //status is directly set to unfinished
    private void addNewTask() {
        view.print(Messages.ENTER_TASK_NAME);
        String taskName = view.getUserInput();
        view.print(Messages.ENTER_TASK_DUE_DATE);
        Date taskDate = parseDate(view.getUserInput());
        Task addedTask = new Task(taskName, taskDate, false);
        tasks.add(addedTask);
        view.print(Messages.SUCCESS);
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
            view.print(Messages.ERROR);
            return null;
        }
    }

    /**
     * Formats date and prints task in a String format. Calls <code>view.print()</code>}
     *
     * @param task The task to be printed
     */
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
        tasks.add(new Task("first mock task", new Date(1524002400000L), false));
        tasks.add(new Task("second mock task", new Date(1537999200000L), true));
        tasks.add(new Task("third mock task", null, false));
        tasks.add(new Task("fourth mock task", new Date(1528236000000L), false));
        tasks.add(new Task("fifth mock task", null, true));
        tasks.add(new Task("sixth mock task", new Date(1523484000000L), false));
    }
}