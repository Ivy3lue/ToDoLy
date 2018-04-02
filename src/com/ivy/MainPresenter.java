package com.ivy;

import com.ivy.base.AbsBasePresenter;
import com.ivy.manager.PersistenceManager;
import com.ivy.manager.TaskManager;
import com.ivy.model.Task;
import com.ivy.util.Messages;
import com.sun.istack.internal.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Contains implementation of the ToDoLy program.
 */
public class MainPresenter extends AbsBasePresenter<Mvp.View> implements Mvp.Presenter {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd", Locale.ENGLISH);

    private TaskManager taskManager;

    @Override
    public void onAttach(Mvp.View view) {
        super.onAttach(view);

        PersistenceManager persistenceManager = new PersistenceManager("./tasks.json");
        taskManager = new TaskManager(persistenceManager.readFromFile());

        view.showWelcomeMenu(taskManager.getCompletedTasks().size(), taskManager.getUncompletedTasks().size(), taskManager.getOverdueTasks().size());

        //TODO daily agenda
        //view.print("Today's ToDoLy: ");

        while (true) {
            view.showMainMenu();
            String userInput = view.getUserInput();

            if (userInput.equals("9")) {
                view.exit();
                return;
            }

            switch (userInput) {
                case "1":
                    List<Task> uncompletedTasks = taskManager.getUncompletedTasks();
                    printList(uncompletedTasks);
                    break;

                //sets status to unfinished
                case "2":
                    String name = getNameInput();
                    Date date = getDateInput();
                    String project = getProjectNameInput();
                    Task taskToAdd = new Task(name, date, project, false);
                    taskManager.addTask(taskToAdd);
                    view.print(Messages.SUCCESS);
                    break;

                case "3":
                    view.print(Messages.ENTER_TASK_NAME);
                    Task taskToEdit = taskManager.findTask(view.getUserInput());

                    if (taskToEdit != null) {
                        taskToString(taskToEdit);
                    } else {
                        view.print(Messages.TASK_NOT_FOUND);
                        break;
                    }

                    switchEditTaskMenu(taskToEdit);
                    break;

                case "4":
                    switchEditMultipleMenu();
                    break;

                case "8":
                    persistenceManager.writeToFile(taskManager.getTasks());
                    view.print(Messages.SAVING);
                    break;

                default:
                    view.print(Messages.ERROR);
            }
        }
    }

    private void switchEditTaskMenu(Task task) {

        while (true) {
            view.showEditTaskMenu();
            String editChoice = view.getUserInput();

            if (editChoice.equals("9")) {
                break;
            }

            switch (editChoice) {
                case "1":
                    task.name = getNameInput();
                    view.print(Messages.SUCCESS);
                    break;

                case "2":
                    task.dueDate = getDateInput();
                    if (task.dueDate != null) {
                        view.print(Messages.SUCCESS);
                    } else view.print(Messages.DATE_ERROR);
                    break;

                case "3":
                    task.project = getProjectNameInput();
                    view.print(Messages.SUCCESS);
                    break;

                case "4":
                    task.isComplete = true;
                    view.print(Messages.SUCCESS);
                    break;

                case "5":
                    task.isComplete = false;
                    view.print(Messages.SUCCESS);
                    break;

                case "6":
                    taskManager.remove(task);
                    view.print(Messages.REMOVED);
                    break;

                default:
                    view.print(Messages.ERROR);
            }
        }
    }

    private void switchEditMultipleMenu() {
        while (true) {
            view.showEditMultipleMenu();

            String editMultipleChoice = view.getUserInput();

            if (editMultipleChoice.equals("9")) {
                break;
            }

            switch (editMultipleChoice) {
                case "1":
                    List<Task> taskList = taskManager.getTasks();
                    printList(taskList);
                    break;

                case "2":
                    List<Task> uncompletedTaskList = taskManager.getUncompletedTasks();
                    printList(uncompletedTaskList);
                    break;

                case "3":
                    List<String> projects = taskManager.listProjects();
                    if (!projects.isEmpty()) {
                        projects.forEach(view::print);
                        switchProjectMenu();
                    } else
                        view.print(Messages.LIST_IS_EMPTY);
                    break;

                case "4":
                    if (taskManager.removeAll(task -> task.isComplete)) {
                        view.print(Messages.REMOVED);
                        break;
                    } else
                        view.print(Messages.ERROR_REMOVING);
                    break;

                case "5":
                    if (taskManager.removeAll()) {
                        view.print(Messages.REMOVED);
                        break;
                    } else {
                        view.print(Messages.ERROR_REMOVING);
                        break;
                    }

                default:
                    view.print(Messages.ERROR);
            }
        }
    }

    private void switchProjectMenu() {
        while (true) {
            view.showProjectMenu();

            String editProjectChoice = view.getUserInput();

            if (editProjectChoice.equals("9")) {
                break;
            }

            switch (editProjectChoice) {

                case "1":
                    String projectName = getProjectNameInput();
                    List<Task> tasksInProject = taskManager.getTasks()
                            .stream()
                            .filter(task -> projectName.equalsIgnoreCase(task.project))
                            .collect(Collectors.toList());
                    printList(tasksInProject);
                    break;

                case "2":
                    String projectToDelete = getProjectNameInput();
                    Predicate<Task> taskPredicate = task -> projectToDelete.equalsIgnoreCase(task.project);
                    taskManager.removeAll(taskPredicate);
                    break;
            }
        }
    }

    private String getNameInput() {
        view.print(Messages.ENTER_TASK_NAME);
        return view.getUserInput();
    }

    private String getProjectNameInput() {
        view.print(Messages.ENTER_PROJECT_NAME);
        return view.getUserInput();
    }

    private Date getDateInput() {
        view.print(Messages.ENTER_TASK_DUE_DATE);
        return parseDate(view.getUserInput());
    }

    private String taskToString(Task task) {
        String taskToString;
        String project;
        String status;
        project = (task.project == null) ? ("not assigned") : task.project;
        status = (task.isComplete) ? ("finished") : ("unfinished");
        taskToString = (task.dueDate == null) ? ("Project: " + project + ", Task: " + task.name + ", " + status) :
                ("Project: " + project + ", Task: " + task.name + ", due " + dateFormat.format(task.dueDate) + ", " + status);
        return taskToString;
    }

    private void printList(List<Task> taskList) {
        if (taskList.isEmpty()) {
            view.print(Messages.LIST_IS_EMPTY);
        } else {
            taskList.stream()
                    .map(this::taskToString)
                    .forEach(view::print);
        }
    }

    @Nullable
    private Date parseDate(String dateInput) {
        try {
            return dateFormat.parse(dateInput);
        } catch (ParseException e) {
            view.print(Messages.DATE_ERROR);
            return null;
        }
    }
}