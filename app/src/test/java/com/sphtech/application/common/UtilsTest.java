package com.sphtech.application.common;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UtilsTest {

    @Mock
    Utils utils;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isNetworkAvailable() {

        Boolean networkAvailable = utils.isNetworkAvailable();
        assertThat(networkAvailable,is(false));

    }

    @Test
    public void getYear() {
        String year = utils.getYear("2000-Q2");
        assertEquals(year,"2000");

    }

    @Test
    public void getQuater() {
        String quater = utils.getQuater("2000-Q2");
        assertEquals(quater,"Q2");

    }

    @Test
    public void provideCache() {
    }

    @Test
    public void provideHttpLoggingInterceptor() {
    }

    @Test
    public void provideCacheInterceptor() {
    }

    @Test
    public void provideOfflineCacheInterceptor() {
    }
}