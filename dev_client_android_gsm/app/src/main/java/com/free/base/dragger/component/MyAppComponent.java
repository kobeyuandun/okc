package com.free.base.dragger.component;

import com.free.base.config.MyApi;
import com.free.base.dragger.module.MyAppModule;
import com.free.base.dragger.scope.MyAppScope;

import dagger.Component;

@MyAppScope
@Component(dependencies = AppComponent.class, modules = MyAppModule.class)
public interface MyAppComponent {
    MyApi retrofitMyApiHelper();
}
