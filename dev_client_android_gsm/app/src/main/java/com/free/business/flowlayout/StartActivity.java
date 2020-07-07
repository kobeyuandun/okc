package com.free.business.flowlayout;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.free.base.R;

import java.util.Random;

/**
 * @author yuandunbin
 * @date 2020/3/17
 */
public class StartActivity extends Activity {

    private FlowLayout mFlowLayout;
    private String[] data = {
            "Java ", "Google 将就将就或过多军军军军军军军军军军军军军军军军军军军",
            "Docker ", "Android ",
            "Spring ", " 轻量级 ", "Redis",
            "Python", "JavaScript",
            "Linux", "Python ", "Python ", "SpringBoot",
            "HTML5", "Hibernate",
            "Java ", "Google ",
            "Docker ", "Android ",
            "Spring ", " 轻量级 ", "Redis",
            "Python", "JavaScript",
            "Linux", "Python ", "Python ", "SpringBoot",
            "HTML5", "Hibernate",
            "Java ", "Google ",
            "Docker ", "Android ",
            "Spring ", " 轻量级 ", "Redis",
            "Python", "JavaScript",
            "Linux", "Python ", "Python ", "SpringBoot",
            "HTML5", "Hibernate",
            "Java ", "Google ",
            "Docker ", "Android ",
            "Spring ", " 轻量级 ", "Redis",
            "Python", "JavaScript",
            "Linux", "Python ", "Python ", "SpringBoot",
            "HTML5", "Hibernate",
            "Java ", "Google ",
            "Docker ", "Android ",
            "Spring ", " 轻量级 ", "Redis",
            "Python", "JavaScript",
            "Linux", "Python ", "Python ", "SpringBoot",
            "HTML5", "Hibernate",
            "Java ", "Google ",
            "Docker ", "Android ",
            "Spring ", " 轻量级 ", "Redis",
            "Python", "JavaScript",
            "Linux", "Python ", "Python ", "SpringBoot",
            "HTML5", "Hibernate",
            "Java ", "Google ",
            "Docker ", "Android ",
            "Spring ", " 轻量级 ", "Redis",
            "Python", "JavaScript",
            "Linux", "Python ", "Python ", "SpringBoot",
            "HTML5", "Hibernate",
            "Java ", "Google ",
            "Docker ", "Android ",
            "Spring ", " 轻量级 ", "Redis",
            "Python", "JavaScript",
            "Linux", "Python ", "Python ", "SpringBoot",
            "HTML5", "Hibernate",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        mFlowLayout = (FlowLayout) findViewById(R.id.activity_main);
        mFlowLayout.setPadding(10, 10, 10, 10);
        for (int i = 0; i < data.length; i++) {

            String keyborad = data[i];

            TextView view = new TextView(this);
            Random random = new Random();
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);

            GradientDrawable drawable = getGradientDrawable(Color.rgb(r, g, b), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
            view.setBackground(drawable);
            view.setTextColor(Color.BLACK);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            view.setPadding(5, 5, 5, 5);
            view.setGravity(Gravity.CENTER);
            view.setText(keyborad);
            mFlowLayout.addView(view);
        }

    }

    /**
     * 生成圆角矩形作为 TextView 的背景
     */
    public GradientDrawable getGradientDrawable(int color, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(radius);
        shape.setColor(color);
        return shape;
    }

}