package com.wipro.proficiency;

import android.app.Application;

import com.wipro.proficiency.di.component.ApplicationComponent;
import com.wipro.proficiency.di.component.DaggerApplicationComponent;
import com.wipro.proficiency.di.module.ApplicationModule;
import com.wipro.proficiency.di.module.NetworkModule;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ProficiencyTestApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initialization();
        setupInjector();
    }

    private void initialization() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void setupInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
