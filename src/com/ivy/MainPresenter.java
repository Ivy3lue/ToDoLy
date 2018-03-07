package com.ivy;

/**
 * Contains the implementation of the ToDoLy program in {@link #startProgram()}
 */
public class MainPresenter extends AbsBasePresenter<Mvp.View> implements Mvp.Presenter {
    @Override
    public void onAttach(Mvp.View view) {
        super.onAttach(view);
        startProgram();
    }

    private void startProgram() {
        if (view == null) {
            return;
        }
        view.exit();
    }
}
