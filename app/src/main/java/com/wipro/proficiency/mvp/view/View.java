package com.wipro.proficiency.mvp.view;


public interface View {
    void showProgressDialog();
    void dismissProgressDialog();
    void showError(String message);
}
