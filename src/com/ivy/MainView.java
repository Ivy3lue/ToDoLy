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
        System.out.println("##############################");
        System.out.println();
        System.out.println("***Welcome to ToDoLy***");
        System.out.println("Great job! You have " + completedTasks + " completed tasks!");
        System.out.println("You have " + tasksToComplete + " tasks to complete");
        System.out.println("##############################");
        System.out.println();
    }

    @Override
    public void showMenu() {
        System.out.println("##############################");
        System.out.println();
        System.out.println("Please select one of the following options: ");
        System.out.println("(1) to show tasks");
        System.out.println("(2) to add new task");
        System.out.println("(3) to edit task");
        System.out.println("(4) to remove task");
        System.out.println();
        System.out.println("(9) to exit ToDoLy");

        System.out.println("##############################");
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }

    @Override
    public void showTasks(List<Task> tasks) {
        tasks.forEach(this::printTask);
    }

    @Override
    public void printTask(Task task) {
        System.out.println("Task: " + task.toString());
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    //TODO fix entering multiple word input
    @Override
    public String getInput() {
        return scanner.next();
    }

    @Override
    public void showError() {
        System.out.println("Please enter valid input: ");
    }

    @Override
    public void exit() {
        presenter.onDetach();
    }
}