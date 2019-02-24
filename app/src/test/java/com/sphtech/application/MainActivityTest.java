package com.sphtech.application;

import com.sphtech.application.Presenter.MobileUsageViewModel;
import com.sphtech.application.Presenter.MobileUsageViewModelProvider;
import com.sphtech.application.services.IRemoteServices;
import com.sphtech.application.services.RemoteServices;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MainActivityTest {


    @Mock
    MainActivity mainActivity;

    @Mock
    IRemoteServices iRemoteServices;

    @Mock
    MobileUsageViewModelProvider mobileUsageViewModelProvider;


    @Mock MobileUsageViewModel mobileUsageViewModel;
    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        mainActivity = Mockito.spy(mainActivity);
        iRemoteServices= Mockito.spy(iRemoteServices);
        mobileUsageViewModelProvider = Mockito.spy(mobileUsageViewModelProvider);
    }




    @Test
    public void setupViewModel() {

        iRemoteServices   = new RemoteServices(mainActivity);
        mobileUsageViewModelProvider = new MobileUsageViewModelProvider(iRemoteServices);
        mobileUsageViewModel = mainActivity.setupViewModel(mobileUsageViewModelProvider);
       // mobileUsageViewModel.getDataFromAPiOrCacheOrFromDb();


    }




    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void showToast() {
    }

    @Test
    public void imageClicked() {
    }
}