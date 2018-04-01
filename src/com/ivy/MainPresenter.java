package com.ivy;

import com.sun.istack.internal.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Contains implementation of the ToDoLy program.
 */
public class MainPresenter extends AbsBasePresenter<Mvp.View> implements Mvp.Presenter {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd", Locale.ENGLISH);

    private PersistenceManager persistenceManager;
    private TaskManager taskManager;

    @Override
    public void onAttach(Mvp.View view) {
        super.onAttach(view);

        persistenceManager = new PersistenceManager("./tasks.json");
        taskManager = new TaskManager(persistenceManager.readFromFile());

        view.showWelcomeMenu(taskManager.getCompletedTasks().size(), taskManager.getUncompletedTasks().size(), taskManager.getOverdueTasks().size());
        view.print("Your ToDoLy: ");
        taskManager.getUncompletedTasks().forEach(task -> view.print(taskToString(task)));

        while (true) {
            view.showMenu();
            String userInput = view.getUserInput();

            if ("9".equals(userInput)) {
                view.exit();
                return;
            }

            switch (userInput) {
                case "1":
                    taskManager.getUncompletedTasks().forEach(task -> view.print(taskToString(task)));
                    break;

                //sets status to unfinished
                case "2":
                    String name = getNameInput();
                    Date date = getDateInput();
                    String project = getNameInput();
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
                                taskToEdit.name = getNameInput();
                                view.print(Messages.SUCCESS);
                                break;

                            case "2":
                                taskToEdit.dueDate = getDateInput();
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
                                taskManager.remove(taskToEdit);
                                view.print(Messages.REMOVED);
                                break;

                            default:
                                view.print(Messages.ERROR);
                        }
                    }
                    break;

                case "4":

                    while (true) {
                        view.print("Press: ");
                        view.print("(1) to see all tasks");
                        view.print("(2) to see unfinished tasks");
                        //todo add submenu
                        view.print("(3) to list projects");
                        view.print("(4) to delete finished");
                        view.print("(5) to delete all tasks");
                        view.print("(9) to save and go to first menu");

                        String editMultipleChoice = view.getUserInput();

                        if (editMultipleChoice.equals("9")) {
                            break;
                        }

                        switch (editMultipleChoice) {
                            case "1":
                                taskManager.getTasks()
                                        .stream()
                                        .map(this::taskToString)
                                        .forEach(view::print);
                                break;

                            case "2":
                                taskManager.getUncompletedTasks()
                                        .stream()
                                        .map(this::taskToString)
                                        .forEach(view::print);
                                break;

                            case "3":
                                taskManager.listProjects().forEach(view::print);

                                while (true) {
                                    view.print("(1) to list project");
                                    view.print("(2) to delete project ");
                                    view.print("(9) to save and go to first menu");

                                    String editProjectChoice = view.getUserInput();

                                    if (editProjectChoice.equals("9")) {
                                        break;
                                    }

                                    switch (editProjectChoice) {

                                        case "1":
                                            String projectName = getNameInput();
                                            taskManager.getTasks().stream().filter(task -> task.project == projectName).forEach(task -> view.print(task.toString()));
                                            break;

                                        case "2":
                                            String projectToDelete = getNameInput();
                                            List<Task> tasksToRemove = taskManager.getTasks().stream().filter(task -> task.project == projectToDelete).collect(Collectors.toList());
                                            taskManager.removeAll(tasksToRemove);
                                            break;
                                    }
                                    break;
                                }

                            case "4":
                                taskManager.removeAll(taskManager.getCompletedTasks());
                                view.print(Messages.REMOVED);
                                break;

                            case "5":
                                taskManager.removeAll(taskManager.getTasks());
                                break;

                            default:
                                view.print(Messages.ERROR);
                        }
                    }

                case "8":
                    persistenceManager.writeToFile(taskManager.getTasks());
                    break;

                default:
                    view.print(Messages.ERROR);
            }
        }
    }

    private String getNameInput() {
        view.print(Messages.ENTER_TASK_NAME);
        return view.getUserInput();
    }

    private Date getDateInput() {
        view.print(Messages.ENTER_TASK_DUE_DATE);
        return parseDate(view.getUserInput());
    }

    private String taskToString(Task task) {
        String status;
        String taskToString;
        status = (task.isComplete) ? ("finished") : ("unfinished");
        taskToString = (task.dueDate == null) ? ("Task: " + task.name + ", " + status) :
                ("Task: " + task.name + ", due " + dateFormat.format(task.dueDate) + ", " + status);
        return taskToString;
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