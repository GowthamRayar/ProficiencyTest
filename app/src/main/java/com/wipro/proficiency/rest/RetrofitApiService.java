package com.wipro.proficiency.rest;

import com.wipro.proficiency.mvp.model.response.NewsFeedResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface RetrofitApiService {

    @GET("2iodh4vg0eortkl/facts.json")
    Observable<NewsFeedResponse> getFeedResponse();
}
