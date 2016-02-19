package com.mapbar.obd.net.android.framework.control.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.mapbar.obd.net.android.framework.model.ActivityInterface;
import com.mapbar.obd.net.android.framework.model.FilterObj;
import com.mapbar.obd.net.android.framework.model.MAnimation;
import com.mapbar.obd.net.android.framework.model.PageObject;
import com.mapbar.obd.net.android.framework.model.PageRestoreData;
import com.mapbar.obd.net.android.framework.widget.MViewAnimator;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class BaseActivity extends Activity implements ActivityInterface {
    private MViewAnimator mViewAnimator;
    private PageObject mOutPage;

    private ArrayList<PageObject> mPageHistorys = new ArrayList<PageObject>();
    private Hashtable<Integer, PageRestoreData> mHt_MapRestores = new Hashtable<Integer, PageRestoreData>();

    private int mCurrentPageIndex = 0;
    private int mScreenWidth, mScreenHeight;
    private boolean isShowingPage = false;
    private MViewAnimator.OnAnimatorHelperListener animationListener = new MViewAnimator.OnAnimatorHelperListener() {
        @Override
        public void onAnimationEnd(Animation animation, int flag, int fromFlag) {
            onAttachFinished(animation, fromFlag);
            if (flag == 1) {
                int size = mPageHistorys.size();
                for (int i = size - 1; i > mCurrentPageIndex; i--) {
                    PageObject outPage = mPageHistorys.get(i);
                    outPage.destroy();
                    mPageHistorys.remove(i);
                    outPage = null;
                }
            }
            isShowingPage = false;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == MAnimation.fade_out_map)
                getOutPage().getPage().onAnimationEnd(animation, -1);
            else if (animation == MAnimation.fade_out_map1) {
                MViewAnimator myAnimator = getAnimator();
                // myAnimator.setInAnimation(MAnimation.push_none);
                // myAnimator.setOutAnimation(MAnimation.push_none);
                myAnimator.setInAnimation(null);
                myAnimator.setOutAnimation(null);
                int size = mPageHistorys.size();
                if (size - 3 < 0)
                    throw new IllegalArgumentException("is not enough Page to return.");
                PageObject inPage = mPageHistorys.get(size - 3);
                for (int i = size - 1; i > mCurrentPageIndex; i--) {
                    PageObject outPage = mPageHistorys.get(i);
                    outPage.destroy();
                    mPageHistorys.remove(i);
                    outPage = null;
                }
                myAnimator.setDisplayedChild(inPage.getView(), false, 0, -1);
                myAnimator.setVisibility(View.GONE);
            } else if (animation == MAnimation.fade_in_map) {
                onAttachFinished(animation, getOutPosition());
                int size = mPageHistorys.size();
                if (size > 0) {
                    PageObject outPage = mPageHistorys.get(size - 1);
                    if (outPage.getPage().getMyViewPosition() == getOutPosition()) {
                        // getOutPage().getView().setVisibility(View.GONE);
                        outPage.getPage().onBackrun();
                        mPageHistorys.remove(size - 1);
                        isShowingPage = false;
                        return;
                    }
                }
                throw new IllegalArgumentException("the last Page is not MapPage.");
            } else if (animation == MAnimation.fade_in_map1) {
                // getOutPage().getView().setVisibility(View.GONE);
                onAttachFinished(animation, getOutPosition());
                getOutPage().getPage().onBackrun();
            }
            isShowingPage = false;
        }
    };
    private boolean isFinishInit = false;
    private ProgressDialog mProgressDialog;

    public abstract PageObject createPage(int index);

    public abstract int getAnimatorResId();

    public abstract void onFinishedInit();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // long time = System.currentTimeMillis();

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public int getScreenWidth() {
        return this.mScreenWidth;
    }

    @Override
    public int getScreenHeight() {
        return this.mScreenHeight;
    }

    private MViewAnimator getAnimator() {
        if (mViewAnimator == null) {
            mViewAnimator = (MViewAnimator) findViewById(getAnimatorResId());
            if (mViewAnimator == null)
                throw new IllegalArgumentException(
                        "the ViewAnimator is null, the layout must contain com.homelink.android.widget.MViewAnimator.");
            mViewAnimator.setDoMySelf(true);
            mViewAnimator.setOnAnimatorHelperListener(animationListener);
        }
        return mViewAnimator;
    }

    @Override
    public void setShowingPage(boolean isShowingPage) {
        this.isShowingPage = isShowingPage;
    }

    @Override
    public void showPage(int flag, int index, int dataType, FilterObj filter, int position, Animation in, Animation out) {
        boolean isOutFront = false;
        if (in == MAnimation.push_none)
            isOutFront = true;
        showPage(flag, index, dataType, filter, position, in, out, isOutFront, null, null);
    }

    @Override
    public void onPageActivity() {
    }

    @Override
    public void showPage(int flag, int index, int dataType, FilterObj filter, int position, Animation in,
                         Animation out, boolean outFront, Point outOffset, Point inOffset) {
        if (isShowingPage)
            return;
        isShowingPage = true;
        onPageActivity();
        PageObject newPage = null;
        if (index == getOutPosition())
            newPage = getOutPage();
        else
            newPage = createPage(index);

        newPage.getPage().setFilterObj(flag, filter);
        newPage.getPage().setDataType(dataType);

        int size = this.mPageHistorys.size();
        PageObject curPage = null;
        if (size != 0) {
            curPage = this.mPageHistorys.get(size - 1);
            curPage.getPage().onDetachedFromWindow(getViewNoneFlag());
            curPage.getPage().onDetachedForUmeng(getViewNoneFlag());
        }

        newPage.setIndex(size);
        this.mPageHistorys.add(newPage);

        boolean r2l = true;
        int type = 0;
        if (curPage != null) {
            r2l = newPage.getIndex() > curPage.getIndex();
            type = curPage.getPage().getTitleAnimType();
        }
        this.onTitleChanged(newPage.getPage().getMyViewPosition(), r2l, type);
        mCurrentPageIndex = this.mPageHistorys.size() - 1;
        newPage.getPage().onAttachedToWindow(flag, position);
        newPage.getPage().onAttachedForUmeng(getViewNoneFlag(), getViewNoneFlag());

        MViewAnimator myAnimator = getAnimator();

        myAnimator.setInAnimation(in);
        myAnimator.setOutAnimation(out);

        if (index == getOutPosition()) {
            // getOutPage().getView().setVisibility(View.VISIBLE);
            MAnimation.fade_out_map.setAnimationListener(animationListener);
            myAnimator.startAnimation(MAnimation.fade_out_map);
            myAnimator.setVisibility(View.GONE);
        } else {
            // boolean isOutFront = outFront;
            boolean isOutFront = false;
            if (in == MAnimation.push_none)
                isOutFront = true;
            if (curPage != null && curPage.getPage().getMyViewPosition() == getOutPosition()) {
                isOutFront = false;
                myAnimator.setVisibility(View.VISIBLE);
                MAnimation.fade_in_map1.setAnimationListener(animationListener);
                myAnimator.startAnimation(MAnimation.fade_in_map1);
                // myAnimator.setInAnimation(MAnimation.push_none);
                // myAnimator.setOutAnimation(MAnimation.push_none);
                myAnimator.setInAnimation(null);
                myAnimator.setOutAnimation(null);
                this.mHt_MapRestores.put(size - 1, curPage.getPage().getRestoreData());
            }
            myAnimator.setDisplayedChild(newPage.getView(), isOutFront, 0, outOffset, inOffset, flag);
        }
        collapseSoftInputMethod((newPage == null) ? null : newPage.getView());
    }

    @Override
    public void showJumpPage(int flag, int fromPos, int toPos, int dataType, FilterObj filter, int position,
                             Animation in, Animation out) {
        int size = this.mPageHistorys.size();
        for (int i = size - 2; i >= 0; i--) {
            PageObject page = this.mPageHistorys.get(i);
            if (page.getPage().getMyViewPosition() != fromPos) {
                page.getPage().onDetachedFromWindow(getViewNoneFlag());
                page.destroy();
                mPageHistorys.remove(i);
                page = null;
            } else {
                break;
            }
        }
        size = this.mPageHistorys.size();
        if (size > 1) {
            PageObject newPage = createPage(flag);
            newPage.getPage().setFilterObj(flag, filter);
            newPage.getPage().setDataType(dataType);

            newPage.setIndex(size - 1);
            this.mPageHistorys.add(size - 1, newPage);
            newPage.getPage().onAttachedToWindow(fromPos, position);
        }
        showPrevious(flag, fromPos, in, out);
    }

    @Override
    public void showPrevious(int index, Animation in, Animation out) {
        // showPrevious(getNonePositioin(), index, null, in, out);
        showPrevious(getNonePositioin(), index, null, in, out, null, null);
    }

    @Override
    public void showPrevious(int index, Animation in, Animation out, Point outOffset, Point inOffset) {
        showPrevious(getNonePositioin(), index, null, in, out, outOffset, inOffset);
    }

    @Override
    public void showPrevious(int index, FilterObj filter, Animation in, Animation out) {
        showPrevious(index, index, filter, in, out);
    }

    @Override
    public void showPrevious(int flag, int index, Animation in, Animation out) {
        showPrevious(flag, index, null, in, out);
    }

    private void showPrevious(int flag, int index, FilterObj filter, Animation in, Animation out) {
        showPrevious(flag, index, filter, in, out, null, null);
    }

    private void showPrevious(int flag, int index, FilterObj filter, Animation in, Animation out, Point outOffset,
                              Point inOffset) {
        if (isShowingPage)
            return;
        isShowingPage = true;
        onPageActivity();
        int size = this.mPageHistorys.size();
        if (size < 2) {
            isShowingPage = false;
            this.goHome();
            return;
        }
        mCurrentPageIndex = size - 2;
        PageObject inPage = this.mPageHistorys.get(size - 2);
        inPage.getPage().setFilterObj(flag, filter);
        inPage.getPage().onRestart(flag);
        PageObject outPage = this.mPageHistorys.get(size - 1);
        outPage.getPage().onDetachedFromWindow(getViewNoneFlag());
        outPage.getPage().onDetachedForUmeng(getViewNoneFlag());
        inPage.getPage().onAttachedForUmeng(getViewNoneFlag(), getViewNoneFlag());

        boolean r2l = outPage.getIndex() < inPage.getIndex();

        int type = 0;
        if (outPage != null) {
            r2l = outPage.getIndex() > outPage.getIndex();
            type = outPage.getPage().getTitleAnimType();
        }

        this.onTitleChanged(inPage.getPage().getMyViewPosition(), r2l, type);

        MViewAnimator myAnimator = getAnimator();

        myAnimator.setInAnimation(in);
        myAnimator.setOutAnimation(out);
        if (outPage.getPage().getMyViewPosition() == getOutPosition()) {
            myAnimator.setVisibility(View.VISIBLE);
            MAnimation.fade_in_map.setAnimationListener(animationListener);
            myAnimator.startAnimation(MAnimation.fade_in_map);
            this.mHt_MapRestores.remove(size - 1);
        } else {
            boolean isOutFront = false;
            if (in == MAnimation.push_none)
                isOutFront = true;
            if (inPage != null && inPage.getPage().getMyViewPosition() == getOutPosition()) {
                // getOutPage().getView().setVisibility(View.VISIBLE);
                PageRestoreData prd = this.mHt_MapRestores.get(size - 2);
                inPage.getPage().onRestoreData(prd);
                isOutFront = false;
                MAnimation.fade_out_map1.setAnimationListener(animationListener);
                myAnimator.startAnimation(MAnimation.fade_out_map1);
                return;
            }
            myAnimator.setDisplayedChild(inPage.getView(), isOutFront, 1, outOffset, inOffset, flag);
        }
        //add by liuyy
        mPageHistorys.remove(mPageHistorys.size() - 1);
        collapseSoftInputMethod((inPage == null) ? null : inPage.getView());
    }

    @Override
    public void showJumpPrevious(int flag, int index, Animation in, Animation out) {
        showJumpPrevious(flag, index, null, in, out, 0);
    }

    @Override
    public void showJumpPrevious(int flag, int index, FilterObj filter, Animation in, Animation out) {
        showJumpPrevious(flag, index, null, in, out, 0);
    }

    @Override
    public void showJumpPrevious(int flag, int index, FilterObj filter, Animation in, Animation out, int titleAminType) {
        if (isShowingPage)
            return;
        isShowingPage = true;
        onPageActivity();
        PageObject jumpToPage = null;
        int jumpIndex = 0;
        int size = this.mPageHistorys.size();
        for (int i = size - 1; i >= 0; i--) {
            PageObject page = this.mPageHistorys.get(i);
            if (i == this.mCurrentPageIndex) {
                page.getPage().onDetachedFromWindow(getViewNoneFlag());
                page.getPage().onDetachedForUmeng(getViewNoneFlag());
                continue;
            }
            if (page.getPosition() == index) {
                jumpToPage = page;
                jumpToPage.getPage().setFilterObj(flag, filter);
                jumpToPage.getPage().onRestart(flag);
                jumpIndex = i;
                break;
            }
            page.getPage().onDetachedFromWindow(getViewNoneFlag());
            page.destroy();
            mPageHistorys.remove(i);
            this.mHt_MapRestores.remove(i);
            page = null;
        }
        if (jumpToPage == null) {
            if (index == getOutPosition())
                jumpToPage = getOutPage();
            else
                jumpToPage = createPage(index);
            jumpToPage.setIndex(0);
            this.mPageHistorys.add(0, jumpToPage);
        }

        jumpToPage.getPage().setFilterObj(flag, filter);
        jumpToPage.getPage().onRestart(flag);

        mCurrentPageIndex = jumpIndex;
        jumpToPage.getPage().onAttachedToWindow(getNonePositioin(), getNonePositioin());
        jumpToPage.getPage().onAttachedForUmeng(getViewNoneFlag(), getViewNoneFlag());

        boolean r2l = false;
        PageObject curPage = this.getCurrentPageObj();
        if (curPage != null)
            r2l = curPage.getIndex() < jumpToPage.getIndex();
        this.onTitleChanged(jumpToPage.getPage().getMyViewPosition(), r2l, titleAminType);

        MViewAnimator myAnimator = getAnimator();

        if (index == getOutPosition()) {
            // getOutPage().getView().setVisibility(View.VISIBLE);
            MAnimation.fade_out_map.setAnimationListener(animationListener);
            myAnimator.startAnimation(MAnimation.fade_out_map);
            myAnimator.setVisibility(View.GONE);
        } else {
            if (titleAminType == 2) {
                myAnimator.setInAnimation(MAnimation.push_left_in);
                myAnimator.setOutAnimation(MAnimation.push_left_out);
            } else {
                myAnimator.setInAnimation(MAnimation.push_right_in);
                myAnimator.setOutAnimation(MAnimation.push_right_out);
            }
            myAnimator.setDisplayedChild(jumpToPage.getView(), false, 1, flag);
        }
        collapseSoftInputMethod((jumpToPage == null) ? null : jumpToPage.getView());
    }

    @Override
    public void recycle() {
    }

    @Override
    public void hideSoftInput(EditText et) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    @Override
    public void showSoftInput(EditText et) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, 0);
    }

    @Override
    public void goHome() {
        goHome(getViewNoneFlag(), getNonePositioin());
    }

    @Override
    public void goHome(int flag, int position) {
        if (isShowingPage)
            return;
        isShowingPage = true;
        onPageActivity();
        int mainPosition = getMainPosition();
        PageObject newPage = null;
        PageObject curPage = null;
        int size = this.mPageHistorys.size();
        for (int i = size - 1; i >= 0; i--) {
            PageObject page = this.mPageHistorys.get(i);
            if (i == this.mCurrentPageIndex) {
                curPage = page;
                if (curPage.getPosition() != mainPosition) {
                    page.getPage().onDetachedFromWindow(flag);
                    page.getPage().onDetachedForUmeng(getViewNoneFlag());
                }
                continue;
            }
            if (page.getPosition() == mainPosition) {
                newPage = page;
                continue;
            }
            mPageHistorys.remove(i);
            page.getPage().onDetachedFromWindow(flag);
            if (page.getPage().getMyViewPosition() == getOutPosition()) {
                page.getPage().onDetachedFromWindow(flag);
                page.getPage().onBackrun();
            } else {
                page.destroy();
                page = null;
            }
        }
        if (newPage == null) {
            if (curPage != null && curPage.getPosition() == mainPosition) {
                mCurrentPageIndex = 0;
                curPage.getPage().onAttachedToWindow(flag, position);
                curPage.getPage().onAttachedForUmeng(getViewNoneFlag(), getViewNoneFlag());
                return;
            }
            newPage = createPage(mainPosition);
            newPage.setIndex(0);
            this.mPageHistorys.add(0, newPage);
        }

        mCurrentPageIndex = 0;
        newPage.getPage().onAttachedToWindow(flag, position);
        newPage.getPage().onAttachedForUmeng(getViewNoneFlag(), getViewNoneFlag());

        int fromFlag = -1;

        boolean r2l = false;
        if (curPage != null) {
            fromFlag = curPage.getPage().getMyViewPosition();
            r2l = curPage.getIndex() < newPage.getIndex();
        }
        this.onTitleChanged(newPage.getPage().getMyViewPosition(), r2l, 0);

        MViewAnimator myAnimator = getAnimator();
        if (curPage != null && curPage.getPage().getMyViewPosition() == getOutPosition()) {
            myAnimator.setVisibility(View.VISIBLE);
            MAnimation.fade_in_map.setAnimationListener(animationListener);
            myAnimator.startAnimation(MAnimation.fade_in_map);
            myAnimator.setInAnimation(null);
            myAnimator.setOutAnimation(null);
            this.mHt_MapRestores.remove(size - 1);
            // myAnimator.setDisplayedChild(newPage.getView(), false, 0, null,
            // null, flag);
        } else {
            myAnimator.setInAnimation(MAnimation.push_right_in);
            myAnimator.setOutAnimation(MAnimation.push_right_out);
        }
        myAnimator.setDisplayedChild(newPage.getView(), false, 1, fromFlag);
    }

    @Override
    public void setLoginOut() {
    }

    @Override
    public int getCurrentPageIndex() {
        return this.mCurrentPageIndex;
    }

    @Override
    public PageObject getCurrentPageObj() {
        if (mCurrentPageIndex < this.mPageHistorys.size())
            return this.mPageHistorys.get(mCurrentPageIndex);
        return null;
    }

    @Override
    public PageObject getPageObjByPos(int position) {
        int size = this.mPageHistorys.size();
        for (int i = size - 1; i >= 0; i--) {
            PageObject page = this.mPageHistorys.get(i);
            if (page.getPage().getMyViewPosition() == position)
                return page;
        }
        return null;
    }

    private void onAttachFinished(Animation animation, int fromFlag) {
        int size = mPageHistorys.size();
        if (mCurrentPageIndex < size) {
            PageObject thePage = mPageHistorys.get(mCurrentPageIndex);
            if (thePage.getPage().getMyViewPosition() != getOutPosition()) {
                thePage.getPage().onAnimationEnd(animation, fromFlag);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        int size = mPageHistorys.size();
//        for (int i = 0; i < size; i++) {
//            PageObject thePage = mPageHistorys.get(i);
//            thePage.getPage().onResume();
//        }

        if (isFinishInit)
            return;
        isFinishInit = true;

        this.onFinishedInit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        int size = mPageHistorys.size();
        for (int i = 0; i < size; i++) {
            PageObject thePage = mPageHistorys.get(i);
            thePage.getPage().onWindowFocusChanged(hasFocus);
        }
    }

    private PageObject getOutPage() {
        if (this.mOutPage == null)
            this.mOutPage = this.createPage(this.getOutPosition());
        return this.mOutPage;
    }

    @Override
    protected void onPause() {
        super.onPause();
        int size = mPageHistorys.size();
        for (int i = 0; i < size; i++) {
            PageObject thePage = mPageHistorys.get(i);
            thePage.getPage().onPause(-1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        int size = mPageHistorys.size();
        for (int i = 0; i < size; i++) {
            PageObject thePage = mPageHistorys.get(i);
            thePage.getPage().onDestroy();
        }
        isFinishInit = false;
    }

    public boolean canExit() {

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            PageObject curPage = this.mPageHistorys.get(mCurrentPageIndex);
            if (curPage.getPage().onKeyDown(keyCode, event)) {
                return true;
            }
            // if (!canExit())
            // return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showAlert(int resId) {
        String word = this.getResources().getString(resId);
        showAlert(word);
    }

    @Override
    public void showAlert(String word) {
        Toast.makeText(this, word, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(int resIdTitle, String strText, int icon) {
        showDialog(this.getResources().getString(resIdTitle), strText, icon);
    }

    @Override
    public void showDialog(String strTitle, String strText, int icon) {
        AlertDialog.Builder tDialog = new AlertDialog.Builder(this);
        tDialog.setIcon(icon);
        tDialog.setTitle(strTitle);
        tDialog.setMessage(strText);
        tDialog.setPositiveButton("确定", null);
        tDialog.show();
    }

    public int getMainPosition() {
        return CConfigs.VIEW_POSITION_MAIN;
    }

    public int getOutPosition() {
        return -2;
    }

    public int getNonePositioin() {
        return CConfigs.VIEW_POSITION_NONE;
    }

    public int getViewNoneFlag() {
        return CConfigs.VIEW_FLAG_NONE;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int size = mPageHistorys.size();
        if (mCurrentPageIndex < size) {
            PageObject thePage = mPageHistorys.get(mCurrentPageIndex);
            thePage.getPage().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showProgressDialog(int msgId) {
        showProgressDialog(msgId, false);
    }

    @Override
    public void showProgressDialog(int msgId, boolean cancelable) {
        String msg = this.getResources().getString(msgId);
        showProgressDialog(null, msg, cancelable);
    }

    @Override
    public void showProgressDialog(String title, String msg) {
        showProgressDialog(title, msg, false);
    }

    private void showProgressDialog(String title, String msg, boolean cancelable) {
        try {
            if (mProgressDialog == null)
                mProgressDialog = new ProgressDialog(this);

            if (msg == null)
                return;

            if (title != null && !"".equals(title.trim()))
                mProgressDialog.setTitle(title);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(cancelable);
            // mProgressDialog.setCancelable(cancelable);
            mProgressDialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent evnet) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return false;
                    } else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                        return true;
                    }
                    return false;
                }
            });
            mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isShowDialog() {
        if (null != mProgressDialog) {
            return mProgressDialog.isShowing();
        }
        return false;
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public View getTitleView(int viewPos) {
        return null;
    }

    @Override
    public void onTitleChanged(int viewPos, boolean rightToleft, int titleAminType) {
    }

    @Override
    public void onTitleChanged(int viewPos) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int size = mPageHistorys.size();
        if (mCurrentPageIndex < size) {
            PageObject thePage = mPageHistorys.get(mCurrentPageIndex);
            thePage.getPage().onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int size = mPageHistorys.size();
        if (mCurrentPageIndex < size) {
            PageObject thePage = mPageHistorys.get(mCurrentPageIndex);
            thePage.getPage().onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void setWindowSoftInputMode(boolean resize) {
        if (resize) {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                            | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        } else {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                            | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    @Override
    public View getTitleRootView() {
        return null;
    }


    @Override
    public void onTitleBarAnimationEnd() {
        int size = mPageHistorys.size();
        if (mCurrentPageIndex < size) {
            PageObject thePage = mPageHistorys.get(mCurrentPageIndex);
            thePage.getPage().onTitleBarAnimationEnd();
        }
    }

    @Override
    public void exit() {
        finish();
    }

    private void collapseSoftInputMethod(View v) {
        if (v == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int size = mPageHistorys.size();
        if (mCurrentPageIndex < size) {
            PageObject thePage = mPageHistorys.get(mCurrentPageIndex);
            thePage.getPage().onConfigurationChanged(newConfig);
        }
    }

    protected String getAdditionalUserAgentString() {
        return null;
    }

    public ArrayList<PageObject> getmPageHistorys() {
        return mPageHistorys;
    }
}
