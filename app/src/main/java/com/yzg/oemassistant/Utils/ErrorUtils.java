package com.yzg.oemassistant.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.yzg.oemassistant.R;
import com.yzg.oemassistant.constant.ApiConstant;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.Utils
 * @ClassName: ErrorUtils
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/31 4:47 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/31 4:47 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ErrorUtils {

    private static final String TERMINAL_SERVICE_EXCEPTION = "Failed to connect to /" + ApiConstant.USB_BASE_URL;

    public static String onErrorNetData(int errorCode){
        String str;
        switch (errorCode){
            case -2222:
                str = "业务接口执行失败";
                break;
            case -3333:
                str = "对应的业务接口未注册";
                break;
            case -4444:
                str = "命令类型错误";
                break;
            case -5555:
                str = "格式解析错误";
                break;
            case 1:
                str = "设备唯一码已存在";
                break;
            case -6:
                str = "请检查当前的网络否可用";
                break;
            default:
                str = "由于网络波动，请重新再试一次～";
                break;
        }
        return str;
    }

    public static void exceptionHandling(Context context
            , Throwable e) {
        if (e instanceof HttpException) {
            // http 请求异常，服务器宕机的时候会进行报这个异常
            Log.i("TAG", "HttpException:" + e.toString());
            Toast.makeText(context, R.string.please_check_network_is_available, Toast.LENGTH_SHORT).show();
        } else if (e instanceof UnknownHostException
                || e instanceof SocketException) {
            Log.i("TAG", "HttpException:" + e.toString());
            String message = e.getMessage();
            if (TextUtils.isEmpty(message)) {
                //联网异常 或 不是正确的主机名
                Toast.makeText(context, R.string.please_check_network_is_available, Toast.LENGTH_SHORT).show();
            } else {
                if (message.contains(TERMINAL_SERVICE_EXCEPTION)) {
                    //联网异常 或 不是正确的主机名
                    Toast.makeText(context, R.string.please_check_network_is_available, Toast.LENGTH_SHORT).show();
                } else if (message.contains("http://172.16.0.188")) {
                    //联网异常 或 不是正确的主机名
                    Toast.makeText(context, R.string.please_check_network_is_available, Toast.LENGTH_SHORT).show();
                } else {
                    //联网异常 或 不是正确的主机名
                    Toast.makeText(context, R.string.please_check_network_is_available, Toast.LENGTH_SHORT).show();
                }
            }

        } else if (e instanceof InterruptedIOException) {
            Log.i("TAG", "HttpException:" + e.toString());
            //网络请求超时
            Toast.makeText(context, R.string.please_check_network_is_available, Toast.LENGTH_SHORT).show();
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            // json 数据的格式解析异常
            Log.i("TAG", "JSONException:" + e.toString());
            Toast.makeText(context, R.string.please_check_network_is_available, Toast.LENGTH_SHORT).show();
        }
    }

}
