package com.free.business.viewpagerlazy;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

/**
 * @author yuandunbin
 * @date 2020/7/14
 */
public class BottomNavigationViewHelper {
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field mShiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            mShiftingMode.setAccessible(true);
            mShiftingMode.setBoolean(menuView,false);
            mShiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
//                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
//                //noinspection RestrictedApi
//                item.setShiftingMode(false);
//                // set once again checked value, so view will be updated
//                //noinspection RestrictedApi
//                item.setChecked(item.getItemData().isChecked());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
