package com.ivy;

import com.sun.istack.internal.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Contains implementation of the ToDoLy program.
 */
public class MainPresenter extends AbsBasePresenter<Mvp.View> implements Mvp.Presenter {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd", Locale.ENGLISH);
    private TaskManager taskManager = new TaskManager();

    @Override
    public void onAttach(Mvp.View view) {
        super.onAttach(view);

        taskManager.mockData();

        view.showWelcomeMenu(taskManager.getCompletedTaskCount(), taskManager.getUncompletedTaskCount(), taskManager.getOverdueTasksCount());
        view.print("Your ToDoLy: ");
        taskManager.getAgenda().forEach(task -> view.print(taskToString(task)));

        while (true) {
            view.showMenu();
            String userInput = view.getUserInput();

            if ("9".equals(userInput)) {
                view.exit();
                return;
            }

            switch (userInput) {
                case "1":
                    List tasks = new ArrayList();
                    taskManager.getUncompletedTasks().forEach(task -> tasks.add(taskToString(task)));
                    view.showTasks(tasks);
                    break;
                //sets status to unfinished
                case "2":
                    String name = getNameInput();
                    Date date = getDateInput();
                    taskManager.addTask(name, date, false);
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
                    taskManager.removeAllCompleted();
                    view.print(Messages.REMOVED);
                    break;

                case "5":
                    List<Task> sortedTasks = taskManager.getSortedTasks();
                    sortedTasks.forEach(task -> view.print(taskToString(task)));
                    break;

                default:
                    view.print(Messages.ERROR);
            }
        }
    }

    private String getNameInput() {
        view.print(Messages.ENTER_TASK_NAME);
        String taskName = view.getUserInput();
        return taskName;
    }

    private Date getDateInput() {
        view.print(Messages.ENTER_TASK_DUE_DATE);
        Date taskDate = parseDate(view.getUserInput());
        return taskDate;
    }

    private String taskToString(Task task) {
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