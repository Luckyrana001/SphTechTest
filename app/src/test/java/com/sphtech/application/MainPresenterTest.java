package com.sphtech.application;

import com.sphtech.application.Presenter.MobileUsageViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class MainPresenterTest {



    @Mock
     MobileUsageViewModel viewModel;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void fetchValidDataShouldLoadIntoView() {

        viewModel.getDataFromAPiOrCacheOrFromDb();

    }



}
