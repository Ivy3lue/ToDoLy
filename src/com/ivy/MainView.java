package com.ivy;

import java.util.List;
import java.util.Scanner;

/**
 * Reads and writes to console.
 */
public class MainView implements Mvp.View {

    private MainPresenter presenter;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Creates a {@link MainPresenter} object and provides <code>this view</code> as an argument.
     */
    void start() {
        presenter = new MainPresenter();
        presenter.onAttach(this);
    }

    @Override
    public void showWelcomeMenu(int completedTasks, int tasksToComplete) {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~ Welcome to ToDoLy ~~~~~~~~~~~~~");
        System.out.println("Great job! You have completed " + completedTasks + " tasks!");
        System.out.println("You have " + tasksToComplete + " tasks left to complete! ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    @Override
    public void showMenu() {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Please select one of the following options: ");
        System.out.println("(1) to show ToDoLy");
        System.out.println("(2) to add new task");
        System.out.println("(3) to view, edit or remove task");
        System.out.println("(4) to remove finished tasks");
        System.out.println("(5) to see all tasks");
        System.out.println();
        System.out.println("(9) to exit");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }

    @Override
    public void showTasks(List<Task> tasks) {
        tasks.forEach(task -> presenter.printTask(task));
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }

    @Override
    public void showError() {
        System.out.println("Invalid input. Please try again... ");
    }

    @Override
    public void exit() {
        presenter.onDetach();
    }
}