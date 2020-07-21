package com.free.business.viewpagerlazy;

import android.os.Bundle;

import com.free.base.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * @author yuandunbin
 * @date 2020/7/14
 */
public class LazyExtend2Activity extends AppCompatActivity {
    private ViewPager viewPager;  //对应的viewPager
    private ArrayList<Fragment> fragmentsList;//view数组

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy2);
        viewPager = findViewById(R.id.viewpager01);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
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

        fragmentsList = new ArrayList<>();
        fragmentsList.add(Fragment1.newIntance());
        fragmentsList.add(Fragment2WithViewPager.newIntance());
        fragmentsList.add(Fragment3.newIntance());
        fragmentsList.add(Fragment4.newIntance());
        fragmentsList.add(Fragment5.newIntance());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int itemId = R.id.fragment_1;
                switch (i) {
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
            public void onPageScrollStateChanged(int i) {

            }
        });
        LazyFragmentPagerAdapter lazyFragmentPagerAdapter = new LazyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList);
        viewPager.setAdapter(lazyFragmentPagerAdapter);
    }
}
