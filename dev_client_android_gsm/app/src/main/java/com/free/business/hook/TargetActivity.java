package com.free.business.hook;

import android.os.Bundle;
import android.widget.TextView;

import com.free.base.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author yuandunbin
 * @date 2020/5/7
 */
public class TargetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        TextView viewById = (TextView)findViewById(R.id.tv_show);
//        viewById.setText("nihao");
    }


}
