package com.wipro.proficiency.di.module;

import android.app.Application;

import com.wipro.proficiency.ProficiencyTestApplication;
import com.wipro.proficiency.di.scope.PerApplication;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    private final ProficiencyTestApplication application;

    public ApplicationModule(ProficiencyTestApplication application) {
        this.application = application;
    }


    @Provides
    @PerApplication
    public Application provideApplication() {
        return application;
    }


}
