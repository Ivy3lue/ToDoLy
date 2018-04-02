package com.ivy;

import java.util.Scanner;

/**
 * Reads and writes to console.
 */
public class MainView implements Mvp.View {

    private MainPresenter presenter;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Creates a {@link MainPresenter} object and provides <code>this view</code> as an argument in the <code>onAttach()</code>.
     */
    void start() {
        presenter = new MainPresenter();
        presenter.onAttach(this);
    }

    @Override
    public void showWelcomeMenu(int completedTasks, int tasksToComplete, int overdueTasks) {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~ Welcome to ToDoLy ~~~~~~~~~~~~~~");
        System.out.println("Great job! You have " + completedTasks + " completed tasks!");
        System.out.println("You have " + tasksToComplete + " tasks left to complete. ");
        if (overdueTasks == 1) {
            System.out.println("OOPS! You have " + overdueTasks + " task overdue!");
        } else if (overdueTasks > 1) {
            System.out.println("OOPS! You have " + overdueTasks + " tasks overdue!");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    /**
     * Prints main menu of the program
     */
    @Override
    public void showMainMenu() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Please select one of the following options: ");
        System.out.println("(1) to show your ToDoLy");
        System.out.println("(2) to add a new task");
        System.out.println("(3) to view/edit task");
        System.out.println("(4) to view/edit multiple tasks");
        System.out.println();
        System.out.println("(8) to save all changes");
        System.out.println("(9) to exit program without saving");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    /**
     * Prints menu for a selected task
     */
    @Override
    public void showEditTaskMenu() {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Please select one of the following options: ");
        System.out.println("(1) to edit task name");
        System.out.println("(2) to edit date");
        System.out.println("(3) to edit project");
        System.out.println("(4) to mark task finished");
        System.out.println("(5) to mark task unfinished");
        System.out.println("(6) to delete task");
        System.out.println();
        System.out.println("(9) to exit this menu");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    /**
     * Prints menu for multiple tasks
     */
    @Override
    public void showEditMultipleMenu() {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Please select one of the following options: ");
        System.out.println("(1) to see all tasks");
        System.out.println("(2) to see unfinished tasks");
        System.out.println("(3) to list projects");
        System.out.println("(4) to delete finished");
        System.out.println("(5) to delete all tasks");
        System.out.println();
        System.out.println("(9) to exit this menu");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    /**
     * Prints projects menu
     */
    @Override
    public void showProjectMenu() {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Please select one of the following options: ");
        System.out.println("(1) to list project");
        System.out.println("(2) to delete project ");
        System.out.println();
        System.out.println("(9) to exit this menu");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * Detaches <code>this view</code> from the {@link MainPresenter}
     */
    @Override
    public void exit() {
        presenter.onDetach();
    }
}