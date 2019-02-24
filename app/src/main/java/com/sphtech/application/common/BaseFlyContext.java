package com.sphtech.application.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;


public class BaseFlyContext {

    private static BaseFlyContext flyContext;
    private Context context;
    private Activity currentActivity;
    public static String responce;
    private Resources resources;

    private BaseFlyContext() {
    }

    ;

    public static BaseFlyContext getInstant() {

        if (flyContext == null) {
            flyContext = new BaseFlyContext();
        }
        return flyContext;
    }

    /**
     * return current application context
     *
     * @return context
     */
    public Context getApplicationContext() {
        return context;
    }

    /**
     * return current Activity instant
     *
     * @return currentActivity
     */
    public Activity getActivity() {

        return currentActivity;
    }

    /**
     * return resources object
     *
     * @return resources
     */
    public Resources getResources() {
        return this.resources;
    }

    /**
     * set resources object
     *
     * @param resources
     */
    public void setResources(Resources resources) {
        this.resources = resources;
    }

    /**
     * set current Activity instant
     *
     * @param a
     */
    public void setActivity(Activity a) {

        currentActivity = a;
        setResources(a.getResources());
        setApplicationContext(a.getApplicationContext());
    }

    /**
     * set ApplicationContext object
     *
     * @param c
     */
    public void setApplicationContext(Context c) {
        context = c;
    }
}
