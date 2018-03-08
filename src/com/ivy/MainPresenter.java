package com.ivy;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Contains implementation of the ToDoLy program.
 */
public class MainPresenter extends AbsBasePresenter<Mvp.View> implements Mvp.Presenter {

    private List<Task> tasks = new ArrayList<>();
    private List<Task> archivedTasks = new ArrayList<>();

    private SimpleDateFormat format = new SimpleDateFormat("YYYY MM dd");

    @Override
    public void onAttach(Mvp.View view) {
        super.onAttach(view);

        mockData();
        view.showWelcomeMenu(getCompletedTaskCount(), getUncompletedTaskCount());
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
                archiveTask();
                addNewTask();
                break;

            case "4":
                archiveTask();
                break;

            default:
                view.showError();
        }

    }

    //TODO optimize following 2/4 methods
    private int getCompletedTaskCount() {
        return getUncompletedTasks().size();
    }

    private int getUncompletedTaskCount() {
        return getCompletedTasks().size();
    }

    private List<Task> getUncompletedTasks() {
        return tasks.stream().filter(task -> !task.isComplete).collect(Collectors.toList());
    }

    private List<Task> getCompletedTasks() {
        return tasks.stream().filter(task -> !task.isComplete).collect(Collectors.toList());
    }

    private void addNewTask() {
        view.print("Please enter task name: ");
        String taskName = view.getInput();
        view.print("Please enter date in the following format: YYYY MM DD");
        Date taskDate = parseDate(view.getInput());
        //TODO validate date
        Task addedTask = new Task(taskName, taskDate, false);
        tasks.add(addedTask);
        view.print("Task added: ");
        view.printTask(addedTask);
    }

    private void archiveTask() {
        view.print("Please enter name of task to edit: ");
        String nameOfTaskToArchive = view.getInput();
        //TODO validate input
        Task taskToArchive = findTask(nameOfTaskToArchive);
        if (taskToArchive == null) {
            //TODO inform for error
            return;
        }
        archiveTask(taskToArchive);
        //TODO infrorm for sucess
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

    private void archiveTask(@NotNull Task task) {
        archivedTasks.add(task);
        tasks.remove(task);
    }

    @Nullable
    private Date parseDate(String dateInput) {
        try {
            return format.parse(dateInput);
        } catch (ParseException e) {
            return null;
        }
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
        tasks.add(new Task("nospace", null, false));
    }
}