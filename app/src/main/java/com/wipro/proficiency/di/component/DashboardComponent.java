package com.wipro.proficiency.di.component;

import com.wipro.proficiency.activity.DashboardActivity;
import com.wipro.proficiency.di.module.DashboardModule;
import com.wipro.proficiency.di.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {DashboardModule.class}
)
public interface DashboardComponent {
    void inject(DashboardActivity activity);
}
