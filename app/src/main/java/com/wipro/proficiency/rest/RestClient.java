package com.wipro.proficiency.rest;

import com.wipro.proficiency.mvp.model.response.NewsFeedResponse;

import retrofit2.Retrofit;
import rx.Observable;

public class RestClient implements Repository {
    private RetrofitApiService apiService;

    public RestClient(Retrofit retrofit) {
        apiService = retrofit.create(RetrofitApiService.class);
    }

    @Override
    public Observable<NewsFeedResponse> getFeedResponse() {
        return apiService.getFeedResponse();
    }
}
