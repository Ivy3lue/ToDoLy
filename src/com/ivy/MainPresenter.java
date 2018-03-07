package com.ivy;

import java.util.Date;
import java.util.List;

/**
 * Contains the implementation of the ToDoLy program in {@link #startProgram()}
 */
public class MainPresenter extends AbsBasePresenter<Mvp.View> implements Mvp.Presenter {

    private List<Task> tasks;

    @Override
    public void onAttach(Mvp.View view) {
        super.onAttach(view);
        mockData();
        startProgram();
    }

    private void startProgram() {
        if (view == null) {
            return;
        }
        view.exit();
    }

    /**
     * Adds mock data for developing and testing purposes.
     */
    private void mockData() {
        tasks.add(new Task("first mock task", new Date(), false));
        tasks.add(new Task("second mock task", new Date(), true));
        tasks.add(new Task("third mock task", null, false));
        tasks.add(new Task("fourth mock task", new Date(1528236000000L), false));
        tasks.add(new Task("fifth mock task", new Date(), true));
        tasks.add(new Task("sixth mock task", new Date(), true));
    }
}
