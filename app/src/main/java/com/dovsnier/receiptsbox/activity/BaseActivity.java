package com.dovsnier.receiptsbox.activity;

import android.widget.Toast;

import com.dovsnier.receiptsbox.R;
import com.dvsnier.activity.AbstractBaseActivity;

/**
 * Created by DovSnier on 2016/8/1.
 */
public class BaseActivity extends AbstractBaseActivity {

    @Override
    public void onUnavailable() {
        super.onUnavailable();
        Toast.makeText(BaseActivity.this, getString(R.string.no_network), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        inspectionNetworkState();
    }
}
