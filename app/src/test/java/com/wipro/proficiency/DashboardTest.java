package com.wipro.proficiency;

import com.wipro.proficiency.interactions.DashboardInteraction;
import com.wipro.proficiency.mvp.model.response.NewsFeedResponse;
import com.wipro.proficiency.mvp.presenter.DashboardPresenter;
import com.wipro.proficiency.mvp.view.DashboardView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DashboardTest {

    DashboardView mockView;
    DashboardInteraction mockInteraction;
    DashboardPresenter mockPresenter;

    @Before
    public void setup()
    {
        mockView = mock(DashboardView.class);
        mockInteraction = mock(DashboardInteraction.class);

        mockPresenter = new DashboardPresenter(mockInteraction);
        mockPresenter.attachView(mockView);
    }

    /*Simple UI Test*/
    @Test
    public void adapterItemCountValidation()  {
        assertFalse(mockPresenter.isAdapterEmpty());
    }

    /*Test that indicates a successful response from server*/
    @Test
    public void testGetNewsFeedSuccess(){
        when(mockInteraction.execute()).thenReturn(getObservableNewsFeed());
        TestSubscriber<NewsFeedResponse> subscriber = TestSubscriber.create();
        mockInteraction.execute().subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        Assert.assertTrue((subscriber.getOnNextEvents().get(0).getTitle()).equalsIgnoreCase("About Canada"));
    }


    /*Test that indicates a NULL response from server*/
    @Test
    public void testGetNewsFeedNull(){
        when(mockInteraction.execute()).thenReturn(Observable.just(null));
        TestSubscriber<NewsFeedResponse> subscriber = TestSubscriber.create();
        mockInteraction.execute().subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        Assert.assertTrue((subscriber.getOnNextEvents().get(0) == null));
    }

    private Observable<NewsFeedResponse> getObservableNewsFeed(){
        return Observable.create(subscriber -> {
            if(!subscriber.isUnsubscribed()){
                NewsFeedResponse newsFeedResponse = new NewsFeedResponse();
                newsFeedResponse.setTitle("About Canada");
                subscriber.onNext(newsFeedResponse);
                subscriber.onCompleted();
            }
        });
    }

    @After
    public void tearDown()
    {

    }
}
