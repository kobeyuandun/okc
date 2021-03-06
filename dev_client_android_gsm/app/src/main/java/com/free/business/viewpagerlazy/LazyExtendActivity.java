package com.free.business.viewpagerlazy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.free.base.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * @author yuandunbin
 * @date 2020/7/8
 */
public class LazyExtendActivity extends AppCompatActivity {
    private static final String TAG = "LazyExtendActivity";
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.fragment_1:
                    viewPager.setCurrentItem(0, true);
                    return true;
                case R.id.fragment_2:
                    viewPager.setCurrentItem(1, true);
                    return true;
                case R.id.fragment_3:
                    viewPager.setCurrentItem(2, true);
                    return true;
                case R.id.fragment_4:
                    viewPager.setCurrentItem(3, true);
                    return true;
                case R.id.fragment_5:
                    viewPager.setCurrentItem(4, true);
                    return true;
            }
            return false;
        });

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(FragmentExtendLazy.newInstance(1));
        fragmentList.add(FragmentExtendLazy.newInstance(2));
        fragmentList.add(FragmentExtendLazy.newInstance(3));
        fragmentList.add(FragmentExtendLazy.newInstance(4));
        fragmentList.add(FragmentExtendLazy.newInstance(5));

        LazyFragmentPagerAdapter lazyFragmentPagerAdapter = new LazyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(lazyFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                int itemId = R.id.fragment_1;
                switch (position) {
                    case 0:
                        itemId = R.id.fragment_1;
                        break;
                    case 1:
                        itemId = R.id.fragment_2;
                        break;
                    case 2:
                        itemId = R.id.fragment_3;
                        break;
                    case 3:
                        itemId = R.id.fragment_4;
                        break;
                    case 4:
                        itemId = R.id.fragment_5;
                        break;
                }
                bottomNavigationView.setSelectedItemId(itemId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "curr3000: "+System.currentTimeMillis());
                Log.d(TAG, "run: "+3000);
            }
        },3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "curr2000: "+System.currentTimeMillis());
                Log.d(TAG, "run: "+2000);
            }
        },2000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "curr1000: "+System.currentTimeMillis());
                Log.d(TAG, "run: "+1000);
            }
        },1000);

    }
}
