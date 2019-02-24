package com.sphtech.application.common;

import android.app.Activity;
import android.content.Context;

import com.sphtech.application.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;


public class BaseFlyContextTest {
    @Mock
    BaseFlyContext SUT;

    @Mock
    MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        SUT = Mockito.spy(SUT);
        mainActivity = Mockito.spy(mainActivity);
        SUT.getInstant().setActivity(mainActivity);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getInstant() {

    }

    @Test
    public void getApplicationContext() {

        Context context = SUT.getApplicationContext();


    }

    @Test
    public void getActivity() {

        SUT.setActivity(mainActivity);
        Activity activity = SUT.getActivity();
    }

    @Test
    public void setActivity() {
    }

    @Test
    public void getResources() {
    }


}