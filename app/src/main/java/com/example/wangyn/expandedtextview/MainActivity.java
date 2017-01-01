package com.example.wangyn.expandedtextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpandTextView expandTextView = (ExpandTextView)findViewById(R.id.expand_container);
        String str = getResources().getString(R.string.test_string);
        expandTextView.setText(str);
    }
}
