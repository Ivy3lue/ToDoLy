package com.ivy;

import com.ivy.base.BaseView;

/**
 * Provides contract for implementing {@link View} and {@link Presenter}.
 */
public interface Mvp {

    interface View extends BaseView {

        void showWelcomeMenu(int completedTasks, int tasksToComplete, int overdueTasks);

        void showMainMenu();

        String getUserInput();

        void print(String message);

        void showEditTaskMenu();

        void showEditMultipleMenu();
    }

    interface Presenter {

    }
}
