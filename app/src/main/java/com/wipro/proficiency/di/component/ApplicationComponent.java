package com.wipro.proficiency.di.component;

import android.app.Application;

import com.wipro.proficiency.di.module.ApplicationModule;
import com.wipro.proficiency.di.module.NetworkModule;
import com.wipro.proficiency.di.scope.PerApplication;
import com.wipro.proficiency.rest.Repository;

import dagger.Component;


@PerApplication
@Component(modules = {ApplicationModule.class, NetworkModule.class
}
)
public interface ApplicationComponent {
    Application application();
    Repository repository();
}
