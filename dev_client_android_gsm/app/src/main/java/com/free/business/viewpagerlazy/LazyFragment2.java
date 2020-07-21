package com.free.business.viewpagerlazy;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author yuandunbin
 * @date 2020/7/13
 * 针对viewpager嵌套viewpager的fragment封装
 */
public abstract class LazyFragment2 extends Fragment {
    private static final String TAG = "LazyFragment";

    /**
     * fragment生命周期
     * onAttach ---> onCreate ---> onCreateView ---> onActivityCreated ---> onStart ---> onResume ---> onPause --->onStop ---> onDestroyView ---> onDestory ---> onDetach
     * 对应ViewPager + Fragment 的实现需要关注的几个生命周期有:
     * onCreateView + onActivityCreated + onResume + onPause + onDestroyView
     */

    protected View rootView = null;

    /**
     * view是否创建
     */
    private boolean isViewCreated = false;

    /**
     * 是否第一次创建的标志位
     */
    private boolean isFirstVisible = true;

    /**
     * 为了获取Fragment的不可见状态，和再次回到可见状态的判断，还需要增加一个currentVisibleState标志位，改标志位在onResume 和onPause中结合 getUserVisibleHint 来决定是否应该回调可见和不可见状态函数
     */
    private boolean currentVisibleState = false;

    FragmentDelegater  mFragmentDelegater;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }
        //init用于添加默认的界面
        initView(rootView);
        //将view创建完成的标志位设置为true
        isViewCreated = true;
        Log.d(TAG, "onCreateView: ");
        //本次分发主要是用于分发默认tab的可见状态，这种情况的生命周期: 1、fragment  setUserVisibleHint:true -->  onAttach ---> onCreate ---> onCreateView --->  onResume
        //默认tab getUserVisibleHint() = true !isHidden() = true
        // 对于非默认 tab 或者非默认显示的 Fragment 在该生命周期中只做了 isViewCreated 标志位设置 可见状态将不会在这里分发
        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
        return rootView;
    }


    protected abstract void initView(View rootView);

    protected abstract int getLayoutRes();
    /**
     * 修改fragment的可见性
     * setUserVisibleHint被调用的两种情况：
     * （1）在切换tab的时候会先于fragment所有其他的生命周期，先调用这个函数，可以看log，对应默认tab 和 间隔checked tab 需要等到isViewCreated = true 后才可以通过此通知用户可见
     * （2）对于之前已经调用过setUserVisibleHint方法的fragment 后，让fragment从可见到不可见状态之间的变化。
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: ");
        //对应情况（1）不处理，对于 isViewCreated，如果为false，说明view没有被创建
        if (isViewCreated) {
            //对于情况(2）要分情况考虑：(2.1）如果是不可见->可见是下面的情况 ；(2.2）如果是可见->不可见是下面的情况
            //对于(2.1）我们需要如何判断呢？首先必须是可见的（isVisibleToUser 为true）
            // 而且只有当可见状态进行改变的时候才需要切换（此时就添加了currentVisibleState来辅助判断），否则会出现反复调用的情况
            //从而导致事件分发带来的多次更新
            //对于(2.2）如果是可见->不可见，判断条件恰好和 (2.1）相反
            if (isVisibleToUser && !currentVisibleState) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && currentVisibleState) {
                dispatchUserVisibleHint(false);
            }
        }
    }
    /**
     * 统一处理用户可见信息
     * 分第一次可见、可见、不可见分发
     *
     * @param isVisible
     */
    private void dispatchUserVisibleHint(boolean isVisible) {
        Log.d(TAG, "dispatchUserVisibleHint: ");
        //事实上作为父Fragment 的 BottomTabFragment2 并没有分发可见事件，他通过 getUserVisibleHint() 得到的结果为false,首先我想到的是能在负责分发事件的方法中判断一下当前父fragment是否可见，如果父fragment 不可见就不进行可见事件的分发
        if (isVisible && isParentInVisible()){
            return;
        }
        if (currentVisibleState == isVisible) {
            return;
        }
        currentVisibleState = isVisible;
        if (isVisible) {
            if (isFirstVisible) {
                isFirstVisible = false;
                onFragmentFirstVisible();
            }
            onFragmentResume();
            //在双重ViewPager嵌套的情况下，第一次滑到Frgment 嵌套ViewPager(fragment)的场景的时候
            //此时只会加载外层Fragment的数据，而不会加载内嵌viewPager中的fragment的数据，因此，我们
            //需要在此增加一个当外层Fragment可见的时候，分发可见事件给自己内嵌的所有Fragment显示
            dispatchChildVisibleState(true);
        } else {
            onFragmentPause();
            dispatchChildVisibleState(false);
        }
    }


    private boolean isParentInVisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof LazyFragment2){
            LazyFragment2 lazyFragment2 = (LazyFragment2) parentFragment;
            return !lazyFragment2.isSupportVisible();
        }
        return false;
    }

    public boolean isSupportVisible(){
        return currentVisibleState;
    }

    private void dispatchChildVisibleState(boolean b) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (fragments!=null){
            for (Fragment fragment:fragments){
                if (fragment instanceof LazyFragment2 && !fragment.isHidden() && fragment.getUserVisibleHint()){
                    ((LazyFragment2)fragment).dispatchUserVisibleHint(b);
                }
            }
        }
    }

    /**
     * 用FragmentTransaction来控制fragment show和hide时，会调用此方法。
     * 每当你对某个Fragment使用hide或者是show的时候，那么这个Fragment就会自动调用这个方法。
     * https://blog.csdn.net/u013278099/article/details/72869175
     *
     * 你会发现使用hide和show这时fragment的生命周期不再执行，
     * 不走任何的生命周期，
     * 这样在有的情况下，数据将无法通过生命周期方法进行刷新，
     * 所以你可以使用onHiddenChanged方法来解决这问题。
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged: ");
        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }

    protected void onFragmentPause() {
    }

    protected void onFragmentResume() {
    }

    protected abstract void onFragmentFirstVisible();

    public void setFragmentDelegater(FragmentDelegater fragmentDelegater) {
        mFragmentDelegater = fragmentDelegater;
    }
    @Override
    public void onResume() {
        super.onResume();
        logD( "onResume: ");
        Log.d(TAG, "onResume: ");
        //在滑动或者跳转的过程中，第一次创建fragment的时候均会调用onResume方法，类似于在tab1 滑到tab2，此时tab3会缓存，这个时候会调用tab3 fragment的
        //onResume，所以，此时是不需要去调用 dispatchUserVisibleHint(true)的，因而出现了下面的if
        if (!isFirstVisible) {
            //由于Activit1 中如果有多个fragment，然后从Activity1 跳转到Activity2，此时会有多个fragment会在activity1缓存，此时，如果再从activity2跳转回
            //activity1，这个时候会将所有的缓存的fragment进行onResume生命周期的重复，这个时候我们无需对所有缓存的fragnment 调用dispatchUserVisibleHint(true)
            //我们只需要对可见的fragment进行加载，因此就有下面的if
            if (!isHidden() && !currentVisibleState && getUserVisibleHint()) {
                dispatchUserVisibleHint(true);
            }
        }
    }

    /**
     * 只有当当前页面由可见状态转变到不可见状态时才需要调用 dispatchUserVisibleHint
     * currentVisibleState && getUserVisibleHint() 能够限定是当前可见的 Fragment
     */
    @Override
    public void onPause() {
        super.onPause();
        logD( "onPause: ");
        Log.d(TAG, "onPause: ");
        if (currentVisibleState && getUserVisibleHint()){
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logD("onDestroyView");
        isViewCreated = false;
        isFirstVisible = false;
    }

    private void logD(String infor) {
        if (mFragmentDelegater != null) {
            mFragmentDelegater.dumpLifeCycle(infor);
        }
    }
}
