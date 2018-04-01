package com.ivy.base;

/**
 * @param <T> generic type for any subclass of {@link BaseView}.
 */
public interface BasePresenter<T extends BaseView> {
    /**
     * @param view the view to attach to the presenter.
     */
    void onAttach(T view);

    void onDetach();
}
