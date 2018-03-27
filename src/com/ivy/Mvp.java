package com.ivy;

import java.util.List;

/**
 * Provides contract for implementing {@link View} and {@link Presenter}.
 */
public interface Mvp {

    interface View extends BaseView {

        void showWelcomeMenu(int completedTasks, int tasksToComplete, int overdueTasks);

        void showMenu();

        String getUserInput();

        void showTasks(List<String> tasks);

        void print(String message);
    }

    interface Presenter {

    }
}
