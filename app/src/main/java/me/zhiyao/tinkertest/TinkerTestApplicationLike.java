package me.zhiyao.tinkertest;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.tinker.entry.DefaultApplicationLike;

import java.util.Locale;

/**
 * @author WangZhiYao
 * @date 2020/10/16
 */
public class TinkerTestApplicationLike extends DefaultApplicationLike {

    private static final String TAG = "ApplicationLike";

    private static final String BUGLY_APP_ID = "替换为你的APP_ID";

    public TinkerTestApplicationLike(Application application, int tinkerFlags,
                                     boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                     long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(
                application,
                tinkerFlags,
                tinkerLoadVerifyFlag,
                applicationStartElapsedTime,
                applicationStartMillisTime,
                tinkerResultIntent
        );
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        Beta.installTinker(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {
                Log.d(TAG, "补丁下载地址: " + patchFile);
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Log.d(TAG, String.format(Locale.getDefault(), "%s %d%%",
                        Beta.strNotificationDownloading,
                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
            }

            @Override
            public void onDownloadSuccess(String msg) {
                Log.d(TAG, "补丁下载成功: " + msg);
            }

            @Override
            public void onDownloadFailure(String msg) {
                Log.d(TAG, "补丁下载失败: " + msg);
            }

            @Override
            public void onApplySuccess(String msg) {
                Log.d(TAG, "补丁应用成功: " + msg);
            }

            @Override
            public void onApplyFailure(String msg) {
                Log.e(TAG, "补丁应用失败: " + msg);
            }

            @Override
            public void onPatchRollback() {
                Log.d(TAG, "补丁回滚");
            }
        };

        // 接入 Walle 后可以在这里设置渠道(可选)
        String channel = WalleChannelReader.getChannel(getApplication(), "Dev");
        Bugly.setAppChannel(getApplication(), channel);

        // 是否为开发设备(可选)
        Bugly.setIsDevelopmentDevice(getApplication(), true);

        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(getApplication(), BUGLY_APP_ID, true);

        Beta.init(getApplication(), true);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Beta.unInit();
    }
}
