package com.aqulasoft.fireman.mobile.di.base.app;

import android.content.Context;

import com.aqulasoft.fireman.mobile.App;
import com.aqulasoft.fireman.mobile.di.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @AppScope
    public Context getAppContext(){
        return App.getInstance().getApplicationContext();
    }
}
