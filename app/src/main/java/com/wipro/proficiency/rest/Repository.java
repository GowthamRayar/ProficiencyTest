package com.wipro.proficiency.rest;

import com.wipro.proficiency.mvp.model.response.NewsFeedResponse;

import rx.Observable;

public interface Repository {
    Observable<NewsFeedResponse> getFeedResponse();
}
