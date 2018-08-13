package com.wipro.proficiency.mvp.presenter;


import com.wipro.proficiency.mvp.view.View;

public interface Presenter<T extends View> {
    void onCreate();

    void onStart();

    void onStop();

    void onPause();

    void attachView(T view);

}
