package application;

import android.app.Application;

import server.RequestManager;

/**
 * Created by Prateek on 29/08/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.getInstance(getApplicationContext());
    }
}
