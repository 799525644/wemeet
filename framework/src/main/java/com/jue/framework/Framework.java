package com.jue.framework;

import android.content.Context;

import com.jue.framework.bmob.BmobManager;
import com.jue.framework.cloud.CloudManager;
import com.jue.framework.helper.NotificationHelper;
import com.jue.framework.helper.WindowHelper;
import com.jue.framework.manager.KeyWordManager;
import com.jue.framework.manager.MapManager;
import com.jue.framework.utils.LogUtils;
import com.jue.framework.utils.SpUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.litepal.LitePal;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * FileName: Framework
 * Founder: Jue
 * Profile: Framework 入口
 */
public class Framework {

    private volatile static Framework mFramework;

    private String BUGLY_KEY = "d51bdd38bd";

    private Framework() {

    }

    public static Framework getFramework() {
        if (mFramework == null) {
            synchronized (Framework.class) {
                if (mFramework == null) {
                    mFramework = new Framework();
                }
            }
        }
        return mFramework;
    }

    /**
     * 初始化框架 Model
     *
     * @param mContext
     */
    public void initFramework(Context mContext) {
        LogUtils.i("initFramework");
        SpUtils.getInstance().initSp(mContext);
        BmobManager.getInstance().initBmob(mContext);
        CloudManager.getInstance().initCloud(mContext);
        LitePal.initialize(mContext);
        MapManager.getInstance().initMap(mContext);
        WindowHelper.getInstance().initWindow(mContext);
        CrashReport.initCrashReport(mContext, BUGLY_KEY, BuildConfig.LOG_DEBUG);
        ZXingLibrary.initDisplayOpinion(mContext);
        NotificationHelper.getInstance().createChannel(mContext);
        KeyWordManager.getInstance().initManager(mContext);

        //全局捕获RxJava异常
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.e("RxJava：" + throwable.toString());
            }
        });
    }
}
