package com.dovsnier.receiptsbox.application;

import android.app.Application;
import android.content.Intent;

import com.dvsnier.crashmonitor.server.MoniterService;

/**
 * Created by DovSnier on 2016/8/1.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializedServerConfig();
    }

    /**
     * the initialized server config
     */
    protected void initializedServerConfig() {
        Intent intent = new Intent(this, MoniterService.class);
        startService(intent);
    }

    /**
     * to closed server moniter
     */
    protected void stopServer() {
        Intent intent = new Intent(this, MoniterService.class);
        stopService(intent);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopServer();
    }
}
