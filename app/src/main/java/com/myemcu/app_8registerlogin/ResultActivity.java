package com.myemcu.app_8registerlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView mResultname;
    private TextView mResultpsd;
    private TextView mResultsexbie;
    private TextView mResultcity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findViews(); // 获取控件对象。

        Intent intent = getIntent();

        mResultname.setText(intent.getStringExtra("name"));
        mResultpsd.setText(intent.getStringExtra("psd"));
        mResultsexbie.setText(intent.getStringExtra("sexbie"));
        mResultcity.setText(intent.getStringExtra("city"));
    }

    private void findViews() {
        mResultname = (TextView) findViewById(R.id.resultName);
        mResultpsd = (TextView) findViewById(R.id.resultPsd);
        mResultsexbie = (TextView) findViewById(R.id.resultSexbie);
        mResultcity = (TextView) findViewById(R.id.resultCity);
    }
}
