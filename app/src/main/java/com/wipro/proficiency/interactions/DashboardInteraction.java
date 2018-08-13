package com.wipro.proficiency.interactions;

import com.wipro.proficiency.mvp.model.response.NewsFeedResponse;
import com.wipro.proficiency.rest.Repository;

import rx.Observable;

public class DashboardInteraction implements Interaction<NewsFeedResponse> {
    private Repository helper;

    public DashboardInteraction(Repository repository) {
        this.helper = repository;
    }

    @Override
    public Observable<NewsFeedResponse> execute() {
        return helper.getFeedResponse();
    }

}
