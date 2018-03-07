package com.ivy;

/**
 * Gives all {@link BasePresenter} subclasses a specific implementation of <code>BasePresenter interface</code> methods.
 */
public abstract class AbsBasePresenter<T extends BaseView> implements BasePresenter<T> {

    protected T view;

    @Override
    public void onAttach(T view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
