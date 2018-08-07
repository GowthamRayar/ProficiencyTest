package com.wipro.proficiency;

import com.wipro.proficiency.interactions.DashboardInteraction;
import com.wipro.proficiency.mvp.presenter.DashboardPresenter;
import com.wipro.proficiency.mvp.view.DashboardView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

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

    @Test
    public void adapterItemCountValidation()  {
        assertFalse(mockPresenter.isAdapterEmpty());
    }

    @After
    public void tearDown()
    {

    }
}
