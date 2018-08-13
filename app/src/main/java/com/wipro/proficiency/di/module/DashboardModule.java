package com.wipro.proficiency.di.module;

import com.wipro.proficiency.di.scope.PerActivity;
import com.wipro.proficiency.interactions.DashboardInteraction;
import com.wipro.proficiency.mvp.presenter.DashboardPresenter;
import com.wipro.proficiency.rest.Repository;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class DashboardModule {

    @PerActivity
    @Provides
    public DashboardInteraction provideDashboardInteraction(Repository repository) {
        return new DashboardInteraction(repository);
    }

    @PerActivity
    @Provides
    public DashboardPresenter provideDashboardPresenter(DashboardInteraction usecase) {
        return new DashboardPresenter(usecase);
    }


}
