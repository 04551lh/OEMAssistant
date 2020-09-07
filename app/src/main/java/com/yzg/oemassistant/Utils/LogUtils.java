package com.yzg.oemassistant.Utils;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.Utils
 * @ClassName: LogUtils
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/20 2:37 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/20 2:37 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
import android.util.Log;

import com.yzg.oemassistant.constant.BuildConfig;


/**
 *
 * 日志封装
 */

public class LogUtils {
    public static String getClassName() {
        return className;
    }

    public static void setClassName(String className) {
        LogUtils.className = className;
    }

    static String className;//类名

    /**
     * 判断是否可以调试
     * @return
     */
    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }


    public static void e(String message){
        if (!isDebuggable())
            return;
        Log.e(className, message);
    }

    public static void i(String message){
        if (!isDebuggable())
            return;
        Log.i(className, message);
    }

    public static void d(String message){
        if (!isDebuggable())
            return;
        Log.d(className, message);
    }

    public static void v(String message){
        if (!isDebuggable())
            return;
        Log.v(className, message);
    }

    public static void w(String message){
        if (!isDebuggable())
            return;
        Log.w(className, message);
    }
}
