package com.free.business.hook;

import android.os.Bundle;

import com.free.base.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author yuandunbin
 * @date 2020/5/7
 */
public class StubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stub);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
