package com.alexlearn.unittestingpractice.di;

import androidx.lifecycle.ViewModelProvider;

import com.alexlearn.unittestingpractice.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
}
