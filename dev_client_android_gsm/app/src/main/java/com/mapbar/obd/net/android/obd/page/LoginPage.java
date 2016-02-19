package com.mapbar.obd.net.android.obd.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mapbar.obd.Manager;
import com.mapbar.obd.UserCenter;
import com.mapbar.obd.net.android.R;
import com.mapbar.obd.net.android.framework.Configs;
import com.mapbar.obd.net.android.framework.common.StringUtil;
import com.mapbar.obd.net.android.framework.control.SDKManager;
import com.mapbar.obd.net.android.framework.inject.annotation.ViewInject;
import com.mapbar.obd.net.android.framework.log.Log;
import com.mapbar.obd.net.android.framework.log.LogTag;
import com.mapbar.obd.net.android.framework.model.AppPage;

import static com.mapbar.obd.net.android.framework.control.PageManager.ManagerHolder.pageManager;

/**
 * Created by liuyy on 2016/1/18.
 */
public class LoginPage extends AppPage implements View.OnClickListener {

    @ViewInject(R.id.btn_login)
    private Button btnLogin;

    @ViewInject(R.id.btn_retrieve_password)
    private TextView btn_retrieve_password;

    @ViewInject(R.id.tv_register)
    private TextView tv_register;

    @ViewInject(R.id.et_phone)
    private EditText et_phone;

    @ViewInject(R.id.et_password)
    private EditText et_password;

    private SDKManager.SDKListener loginSdkListener;


    public LoginPage(final Context context, View rootview) {
        super(context, rootview);
        setListener();
    }

    public LoginPage(Context context, View rootview, AppPage parent) {
        super(context, rootview, parent);
    }


    private void setListener() {
        btnLogin.setOnClickListener(this);
        btn_retrieve_password.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        loginSdkListener = new SDKManager.SDKListener() {
            @Override
            public void onEvent(int event, Object o) {

                switch (event) {
                    case Manager.Event.loginSucc:
                        // 日志
                        if (Log.isLoggable(LogTag.TEMP, Log.DEBUG)) {
                            Log.d(LogTag.TEMP, "loginSucc -->> ");
                        }
                        pageManager.goPage(Configs.VIEW_POSITION_MAIN);
                        StringUtil.toastStringShort(R.string.login_succ);
                        break;
                    case Manager.Event.loginFailed:
                        // 日志
                        if (Log.isLoggable(LogTag.TEMP, Log.DEBUG)) {
                            Log.d(LogTag.TEMP, "loginFailed -->>login_failed ");
                        }
                        StringUtil.toastStringShort(R.string.login_phone_is_null);
                        StringUtil.toastStringShort(R.string.login_succ);
                        break;
                }
            }
        };
        SDKManager.getInstance().setSdkListener(loginSdkListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
//                userLogin();
                pageManager.goPage(Configs.VIEW_POSITION_MAIN);
                break;
            case R.id.btn_retrieve_password:
                pageManager.goPage(Configs.VIEW_POSITION_RETRIEVE_PASSWORD);
                break;
            case R.id.tv_register:
                pageManager.goPage(Configs.VIEW_POSITION_REGISTER);
                break;
        }
    }

    /**
     * 用户登录
     */
    private void userLogin() {
        String phone = et_phone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            //手机号为空
            StringUtil.toastStringShort(R.string.login_phone_is_null);
            return;
        }
        if (!StringUtil.isMobileNum(phone)) {
            //手机号不合法
            StringUtil.toastStringShort(R.string.login_phone_invalid);
            return;
        }

        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            //密码为空
            StringUtil.toastStringShort(R.string.login_password_is_null);
            return;
        }
        UserCenter.getInstance().login(phone, password);
        // 日志
        if (Log.isLoggable(LogTag.TEMP, Log.DEBUG)) {
            Log.d(LogTag.TEMP, "login -->> ");
        }
    }
}
