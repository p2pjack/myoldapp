package com.hacker.eaun.cigmanotes;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class MainWatchActivity extends WearableActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mTextView = (TextView) findViewById(R.id.textView);
        String message = getIntent().getStringExtra("message");
        if (message == null || message.equalsIgnoreCase("")) {
                message = "Hello World !!!!";
        }
        mTextView.setText(message);


    }


}
