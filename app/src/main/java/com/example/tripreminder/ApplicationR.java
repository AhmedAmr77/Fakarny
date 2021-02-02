package com.example.tripreminder;

import android.app.Application;

public class ApplicationR {
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    public static void setApplication(Application application) {
        ApplicationR.application = application;
    }
}
