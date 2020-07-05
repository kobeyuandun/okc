package com.free.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import static android.app.ActivityManager.RunningAppProcessInfo;
import static android.app.ActivityManager.RunningServiceInfo;

public class AndroidUtils {

    private static final String TAG = AndroidUtils.class.getSimpleName();

    private static Context mContext;

    // must be first initialize on Application onCreate Method
    public static void init(@NonNull Context context) {
        mContext = context;
    }

    public static Context getContext() {
        if (AndroidUtils.mContext == null) {
            throw new NullPointerException("Call AndroidUtils.initialize(context) within your Application onCreate() method.");
        }

        return AndroidUtils.mContext.getApplicationContext();
    }

    public static Resources getResources() {
        return AndroidUtils.getContext().getResources();
    }

    public static Resources.Theme getTheme() {
        return AndroidUtils.getContext().getTheme();
    }

    public static AssetManager getAssets() {
        return AndroidUtils.getContext().getAssets();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return AndroidUtils.getResources().getDisplayMetrics();
    }

    public static int id(Context context, String resourceName, TYPE type) {
        Resources resources = context.getResources();
        return resources.getIdentifier(resourceName, type.getString(), context.getPackageName());
    }

    public enum TYPE {
        ATTR("attr"),
        ARRAY("array"),
        ANIM("anim"),
        BOOL("bool"),
        COLOR("color"),
        DIMEN("dimen"),
        DRAWABLE("drawable"),
        ID("id"),
        INTEGER("integer"),
        LAYOUT("layout"),
        MENU("menu"),
        MIPMAP("mipmap"),
        RAW("raw"),
        STRING("string"),
        STYLE("style"),
        STYLEABLE("styleable");

        private String string;

        TYPE(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }
    }

    public static int getAppVersionCode() {
        if (mContext != null) {
            PackageManager pm = mContext.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionCode;
                    }
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public static String getAppVersionName() {
        if (mContext != null) {
            PackageManager pm = mContext.getPackageManager();
            if (pm != null) {
                PackageInfo pi;
                try {
                    pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionName;
                    }
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void installApk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + "." + "fileprovider", apkFile);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apkFile);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    public static void uninstallApk(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        mContext.startActivity(intent);
    }

    public static boolean isAppInstalled(final String packageName) {
        try {
            final PackageManager pm = mContext.getPackageManager();
            final PackageInfo info = pm.getPackageInfo(packageName, 0);
            return info != null;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        // Detecting Application Class Running on Main Process on a Multiprocess app
        // The Current Running Process
        String mainProcessName = mContext.getPackageName();
        int myPid = android.os.Process.myPid();
        for (RunningAppProcessInfo info : processes) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isServiceRunning(Class<?> cls) {
        final ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        final List<RunningServiceInfo> services = am.getRunningServices(Integer.MAX_VALUE);
        final String className = cls.getName();
        for (RunningServiceInfo service : services) {
            if (className.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAppRunning(final String packageName) {
        final ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        final List<RunningAppProcessInfo> apps = am.getRunningAppProcesses();
        if (apps == null || apps.isEmpty()) {
            return false;
        }
        for (RunningAppProcessInfo app : apps) {
            if (packageName.equals(app.processName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAppDebug() {
        return isAppDebug(mContext.getPackageName());
    }

    public static boolean isAppDebug(final String packageName) {
//        if (StringUtils.isEmpty(packageName)) {
//            return false;
//        }
        try {
            PackageManager pm = mContext.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Signature[] getAppSignature() {
        return getAppSignature(mContext.getPackageName());
    }

    public static Signature[] getAppSignature(final String packageName) {
//        if (StringUtils.isEmpty(packageName)) {
//            return null;
//        }
        try {
            PackageManager pm = mContext.getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAppSignatureMd5Hex() {
        return getAppSignatureMd5Hex(mContext.getPackageName());
    }

    public static String getAppSignatureMd5Hex(final String packageName) {
//        Signature[] signature = getAppSignature(packageName);
//        if (signature == null) {
//            return null;
//        }
//        return DigestUtils.md5Hex(signature[0].toByteArray());
        return null;
    }

    public static void restartActivity(final Activity activity) {
        Intent intent = activity.getIntent();
        activity.overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static boolean isMobile() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    public static void mediaScan(Uri uri) {
        // Add new file to sysdb
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
    }

    public static void addToMediaStore(File file) {
        // Add new file to sysdb
        String[] path = new String[]{file.getPath()};
        MediaScannerConnection.scanFile(mContext, path, null, null);
    }

    /**
     * Get File Mime Type
     * @param uri File uri
     * @return Mime type string
     */
    public static String getMimeType(Uri uri) {
        String mimeType = null;
        try {
            if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                ContentResolver cr = mContext.getContentResolver();
                mimeType = cr.getType(uri);
            } else {
                String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
                mimeType = MimeTypeMap.getSingleton()
                        .getMimeTypeFromExtension(fileExtension.toLowerCase());
            }
        } catch (Exception e) {
            Log.e(TAG, "getMimeType Exception", e);
        }
        return mimeType;
    }

    /**
     * Return Intent to open any files
     * @param openFile file object to be opened
     */
    public static Intent getFileIntent(File openFile) {
        Intent fileIntent = null;
        if (mContext != null && openFile.exists()) {
            try {
                fileIntent = new Intent(Intent.ACTION_VIEW);
                fileIntent.setDataAndType(Uri.fromFile(openFile), getMimeType(Uri.fromFile(openFile)));
            } catch (Exception e) {
                Log.e(TAG, "getFileIntent Exception", e);
            }
        }
        return fileIntent;
    }

    /**
     * Open File using intent
     * @param openFile file object to be opened
     */
    public static void openFileIntent(File openFile) {
        if (mContext != null && openFile.exists()) {
            try {
                Intent intent = getFileIntent(openFile);
                mContext.startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, "openFileIntent Exception", e);
            }
        }
    }

    @SuppressLint("MissingPermission")
    public static String getDeviceId() {
        String uniqueDeviceId = "";
        try {
            //IMEI:(International Mobile Equipment Identity)是国际移动设备身份码的缩写
            //MEID:(Mobile Equipment IDentifier)是全球唯一的56bitCDMA制式移动终端标识号
            uniqueDeviceId = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE))
                    .getDeviceId();
        } catch (Exception e) {
            //1.非手机设备无
            //2.权限问题未授权无
            // notAuth premission
        }

        if (TextUtils.isEmpty(uniqueDeviceId)) {
            uniqueDeviceId = "";
        }

        //硬件序列,没有电话功能的设备会提供,某些手机可能进行提供
        String SimSerialNumber = "";
        try {
            SimSerialNumber = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE))
                    .getSimSerialNumber();
        } catch (Exception e) {
            //权限问题未授权无
            // notAuth premission
        }

        if (TextUtils.isEmpty(SimSerialNumber)) {
            SimSerialNumber = "";
        }

        //ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置
        //在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
        String androidId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidId) || "9774d56d682e549c".equals(androidId)) {
            androidId = "";
        }

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) uniqueDeviceId.hashCode() << 32) | SimSerialNumber
                .hashCode());
        return deviceUuid.toString().replace("-", "");
    }
}
