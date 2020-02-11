package com.hkex.soma.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.basic.MasterActivity;

public class ErrorReportActivity extends MasterActivity {
    TextView error;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.errorreport);
        this.error = (TextView) findViewById(R.id.error);
        this.error.setText(getIntent().getStringExtra("error"));
    }
}
