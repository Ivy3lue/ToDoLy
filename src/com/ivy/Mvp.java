package com.ivy;

import java.util.List;

/**
 * Provides contract for implementing {@link View} and {@link Presenter}.
 */
public interface Mvp {

    interface View extends BaseView {

        void showWelcomeMenu(int completedTasks, int tasksToComplete);

        void showMenu();

        String getUserInput();

        void showTasks(List<Task> tasks);

        void showError();

        void printTask(Task task);

        String getInput();

        void print(String message);
    }

    interface Presenter {

    }
}
