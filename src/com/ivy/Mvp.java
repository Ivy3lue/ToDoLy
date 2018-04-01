package com.ivy;

/**
 * Provides contract for implementing {@link View} and {@link Presenter}.
 */
public interface Mvp {

    interface View extends BaseView {

        void showWelcomeMenu(int completedTasks, int tasksToComplete, int overdueTasks);

        void showMenu();

        String getUserInput();

        void print(String message);
    }

    interface Presenter {

    }
}
