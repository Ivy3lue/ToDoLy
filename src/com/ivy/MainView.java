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
    }

    @Override
    public void showMenu() {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Please select one of the following options: ");
        System.out.println("(1) to show your ToDoLy");
        System.out.println("(2) to add a new task");
        System.out.println("(3) to view task");
        //todo remove multiple tasks menu
        System.out.println("(4) to remove all finished tasks");
        System.out.println("(5) to see all tasks");
        System.out.println();
        //todo add saving option that writes to file
        System.out.println("(9) to exit");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }

    @Override
    public void showTasks(List<String> tasks) {
        tasks.forEach(task -> print(task));
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void exit() {
        presenter.onDetach();
    }
}