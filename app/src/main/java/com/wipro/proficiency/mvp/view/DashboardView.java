package com.wipro.proficiency.mvp.view;

import android.content.Context;

import com.wipro.proficiency.mvp.model.response.NewsFeedResponse;

public interface DashboardView extends View {
    Context getContext();
    void updateView(NewsFeedResponse response);
}
