package com.free.business.main;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * @author yuandunbin
 * @date 2019/8/22
 */
public class MessengerService extends Service {
    private static class MessengerHandle extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
