package com.ivy;

/**
 * Reads and writes to console.
 */
public class MainView implements Mvp.View {

    private MainPresenter presenter;

    /**
     * Creates a {@link MainPresenter} object and provides <code>this view</code> as an argument.
     */
    public void start() {
        presenter = new MainPresenter();
        presenter.onAttach(this);
    }

    @Override
    public void exit() {
        presenter.onDetach();
    }
}