package com.free.base.dragger.component;

import android.app.Activity;

import com.free.base.dragger.module.FragmentModule;
import com.free.base.dragger.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(dependencies = MyAppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();
}
