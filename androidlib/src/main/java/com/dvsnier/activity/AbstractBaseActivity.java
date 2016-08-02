package com.dvsnier.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.dvsnier.network.OnNetworkListener;
import com.dvsnier.utils.LogUtil;
import com.dvsnier.utils.NetWorkUtils;

/**
 * Created by lizw on 16/7/26.
 */
public class AbstractBaseActivity extends AppCompatActivity implements OnNetworkListener {

    public static final int ACTIVITY_NETWORK_AVAILABEL_CODE = 600;
    public static final int ACTIVITY_NETWORK_UNAVAILABEL_CODE = 601;
    protected OnNetworkListener onNetworkListener;
    protected NetworkReceiver networkReceiver;
    protected LayoutInflater inflater;
    protected String TAG = this.getClass().getSimpleName();
    /**
     * the main thread default provide handle object
     */
    protected Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            AbstractBaseActivity.this.handleMessage(msg);
        }
    };

    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case ACTIVITY_NETWORK_AVAILABEL_CODE:
                if (null != onNetworkListener) {
                    onNetworkListener.onAvailable();
                }
                break;
            case ACTIVITY_NETWORK_UNAVAILABEL_CODE:
                if (null != onNetworkListener) {
                    onNetworkListener.onUnavailable();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(this);
        networkReceiver = new NetworkReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, intentFilter);
        setOnNetworkListener(this);
        inspectionNetworkState();
    }

    protected void inspectionNetworkState() {
        if (!NetWorkUtils.isNetworkAvailable(this)) {
            handler.sendEmptyMessage(ACTIVITY_NETWORK_UNAVAILABEL_CODE);
        } else {
            handler.sendEmptyMessage(ACTIVITY_NETWORK_AVAILABEL_CODE);
        }
    }

    public OnNetworkListener getOnNetworkListener() {
        return onNetworkListener;
    }

    public void setOnNetworkListener(OnNetworkListener onNetworkListener) {
        this.onNetworkListener = onNetworkListener;
    }

    @Override
    public void onAvailable() {
    }

    @Override
    public void onUnavailable() {
    }

    /**
     * to listener network changed
     */
    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
            LogUtil.d("NetworkReceiver", intent.getAction());
            if (null == activeInfo) {
                handler.sendEmptyMessage(ACTIVITY_NETWORK_UNAVAILABEL_CODE);
            } else {
                handler.sendEmptyMessage(ACTIVITY_NETWORK_AVAILABEL_CODE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkReceiver);
        super.onDestroy();
    }
}
