package com.wipro.proficiency.mvp.presenter;

import com.wipro.proficiency.interactions.Interaction;
import com.wipro.proficiency.mvp.model.response.NewsFeedResponse;
import com.wipro.proficiency.mvp.view.DashboardView;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DashboardPresenter implements Presenter<DashboardView> {

    private Subscription dashboardSubscription;
    private DashboardView dashboardView;
    private Interaction<NewsFeedResponse> interaction;


    public DashboardPresenter(Interaction<NewsFeedResponse> interaction) {
        this.interaction = interaction;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (dashboardSubscription != null && !dashboardSubscription.isUnsubscribed()) {
            dashboardSubscription.unsubscribe();
        }
        dashboardView = null;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(DashboardView view) {
        this.dashboardView = view;
    }


    public void getNewsFeed() {

        Observable<NewsFeedResponse> newsFeedResponseObservable = interaction.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        dashboardSubscription = newsFeedResponseObservable.subscribe(new Observer<NewsFeedResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dashboardView.showError(e.getMessage());

            }

            @Override
            public void onNext(NewsFeedResponse feedResponse) {
                dashboardView.updateView(feedResponse);
            }

        });
    }

    public boolean isAdapterEmpty() {
        return dashboardView.isAdapterEmpty();
    }

}
