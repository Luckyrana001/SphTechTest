package com.sphtech.application.Presenter;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sphtech.application.services.IRemoteServices;


public class MobileUsageViewModelProvider implements ViewModelProvider.Factory {


    private IRemoteServices mRs;


    public MobileUsageViewModelProvider(IRemoteServices mRs) {

        this.mRs = mRs;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MobileUsageViewModel.class)) {
            return (T) new MobileUsageViewModel(mRs);
        }
        throw new IllegalArgumentException("MobileUsageViewModel expected");
    }
}
