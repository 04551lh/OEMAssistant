package com.yzg.oemassistant.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yzg.oemassistant.R;
import com.yzg.oemassistant.Utils.CheckNetworkStatus;
import com.yzg.oemassistant.Utils.ErrorUtils;
import com.yzg.oemassistant.Utils.LogUtils;
import com.yzg.oemassistant.base.BaseMvpActivity;
import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.constant.ApiConstant;
import com.yzg.oemassistant.mvp.bean.ConfigBean;
import com.yzg.oemassistant.mvp.bean.DeviceInfoBean;
import com.yzg.oemassistant.mvp.contract.ScanResultContract;
import com.yzg.oemassistant.mvp.mdel.DeviceUniqueCodeModel;
import com.yzg.oemassistant.mvp.mdel.InformationModel;
import com.yzg.oemassistant.mvp.mdel.NullModel;
import com.yzg.oemassistant.mvp.presenter.ScanResultPresenter;
import com.yzg.oemassistant.net.ApiRetrofit;
import com.yzg.oemassistant.tool.BaseDialog;
import com.yzg.oemassistant.tool.CommonMethod;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ScanResultActivity extends BaseMvpActivity<ScanResultPresenter> implements ScanResultContract.View, View.OnClickListener {
    //3c认证码
    private TextView mTv3C;
    //3c认证码的值
    private String m3C;
    //设备型号
    private TextView mTvDeviceModel;
    //设备型号的值
    private String mDeviceModel;
    //设备唯一码
    private TextView mTvDeviceUniqueCode;
    //设备唯一码的值
    private String mDateCode;
    private String mTerminalId;
    //厂商ID的值
    private String mVendorId;
    //录入按钮
    private TextView mTvInput;
    //录入结果
    private LinearLayout mLlScanResultHint;
    private TextView mTvScanResultHint;
    private TextView mTvReScan;
    //成功提示录入成功
    private TextView mTvSuccessTips;
    //网路加载
    private BaseDialog mBaseDialog;
    //mac
    private String mMac;
    private String mDeviceType;
    private String mOper;
    private String mDisplay;
    //USB广播
    private BroadcastReceiver mReceiver;
    private boolean mIsClose;
    private boolean mErrorHint;

    private ApiRetrofit mApiRetrofit;


    @Override
    public int getLayoutResID() {
        return R.layout.activity_scan_result;
    }

    @Override
    public void initViews() {
        initUSB();
        //返回按钮
        ImageView mIvScanResultBack = findViewById(R.id.iv_scan_result_back);
        mTv3C = findViewById(R.id.tv_3c);
        mTvDeviceModel = findViewById(R.id.tv_device_model);
        mTvDeviceUniqueCode = findViewById(R.id.tv_device_result_unique_code);
        //厂商ID
        TextView mTvVendorId = findViewById(R.id.tv_vendor_id);
        mTvInput = findViewById(R.id.tv_input);
        mLlScanResultHint = findViewById(R.id.ll_scan_result_hint);
        mTvScanResultHint = findViewById(R.id.tv_scan_result_hint);
        //失败重新录入，
        mTvReScan = findViewById(R.id.tv_rescan);
        mTvSuccessTips = findViewById(R.id.tv_success_tips);
        Bundle bundle = getIntent().getExtras();
        //获取传过来数据
        if(bundle == null)return;
        m3C = bundle.getString(ScanCodeEntryActivity._3C_AUTHENTICATION_CODE, "");
        mDeviceModel = bundle.getString(ScanCodeEntryActivity.DEVICE_MODEL, "");
        mDateCode = bundle.getString(ScanCodeEntryActivity.DATE_CODE, "");
        mTerminalId = bundle.getString(ScanCodeEntryActivity.TERMINAL_ID, "");
        mVendorId = bundle.getString(ScanCodeEntryActivity.VENDOR_ID, "");
        //显示数据
        mTv3C.setText(m3C);
        mTvDeviceModel.setText(mDeviceModel);
        mTvVendorId.setText(mVendorId);
        mIvScanResultBack.setOnClickListener(this);
        mTvInput.setOnClickListener(this);
        mTvReScan.setOnClickListener(this);

        mBaseDialog = BaseDialog.showDialog(ScanResultActivity.this);
        mApiRetrofit = ApiRetrofit.getInstance();
        mApiRetrofit.setHttpsURL(null);
        mPresenter = new ScanResultPresenter();
        mPresenter.attachView(this);
        //todo
        mPresenter.getInfo();
        mErrorHint = false;
    }

    @Override
    public void showLoading() {
        if (mBaseDialog != null && !mBaseDialog.isShowing()) {
            mBaseDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mBaseDialog != null && mBaseDialog.isShowing()) {
            mBaseDialog.dismiss();
        }
    }

    @Override
    public void onError(String errMessage) {
        Toast.makeText(ScanResultActivity.this,errMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
        ErrorUtils.exceptionHandling(this,e);
    }

    @Override
    public void onInfoSuccess(BaseResponse<InformationModel> bean) {
        mMac = bean.getResult().getMac();
        mDeviceType = bean.getResult().getPrpdectKindCode();
        mOper = bean.getResult().getPrpdectKindCode();
        mDisplay = bean.getResult().getSoft();
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        Log.i("TAG","mDeviceType:"+mDeviceType);
        mDeviceType = decimalFormat.format(Integer.parseInt(mDeviceType.substring(2)));
        mTvDeviceUniqueCode.setText(String.format("%s%s", mDeviceType, mTerminalId));
    }

    @Override
    public void onNullSuccess(BaseResponse<NullModel> bean) {
        if(bean.getStatusCode() == 0){
            mErrorHint = false;
            mTvInput.setVisibility(View.GONE);
            mTvSuccessTips.setVisibility(View.VISIBLE);
            mLlScanResultHint.setVisibility(View.GONE);
            Intent intent = new Intent(ScanResultActivity.this,QRCodeActivity.class);
            intent.putExtra(ScanCodeEntryActivity.VENDOR_ID,mVendorId);
            startActivity(intent);
        }else{
            mErrorHint = true;
            mLlScanResultHint.setVisibility(View.VISIBLE);
            mTvScanResultHint.setText(ErrorUtils.onErrorNetData(bean.getStatusCode()));
        }
    }

    @Override
    public void onDeviceUniqueCodeSuccess(DeviceUniqueCodeModel bean) {
        //0：正常；-1:其他错误；-2：SIGN错误，-4：该设备已报备过，-5设备唯一码不全部是数字，-6设备唯一码位数错误
        if(bean.getRet() == 0){
            String deviceUniqueCode = mTvDeviceUniqueCode.getText().toString().trim();
            ConfigBean configBean = new ConfigBean();
            configBean.setProducerID(mVendorId);
            configBean.setTerminalModel(mDeviceModel);
            configBean.setTerminalId(deviceUniqueCode);
            configBean.setManufactureDate(mDateCode);
            String json = new Gson().toJson(configBean);
            mApiRetrofit.setHttpsURL(null);
            RequestBody requestBody = RequestBody.create(MediaType.parse(ApiConstant.MEDIA_TYPE), json);
            mPresenter.configInfo(requestBody);
        }else{
            mErrorHint = true;
            mLlScanResultHint.setVisibility(View.VISIBLE);
            mTvScanResultHint.setText(bean.getMsg());
        }
    }

    private void initUSB() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (intent.hasExtra(UsbManager.EXTRA_PERMISSION_GRANTED)) {
                    boolean permissionGranted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                }
                if (ApiConstant.ACTION_USB_STATE.equals(action)) {
                    boolean connected = intent.getBooleanExtra("connected", false);
                    boolean configured = intent.getBooleanExtra("configured", false);
                    LogUtils.i("connected :" + connected);
                    LogUtils.i("configured :" + configured);
                    if (!connected && !configured) {
                        mIsClose = true;
                    } else {
                        if (mIsClose) {
                            finish();
                        }
                    }
                }
            }
        };
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ApiConstant.ACTION_USB_STATE);
        registerReceiver(mReceiver, mIntentFilter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan_result_back:
                if(mErrorHint){
                    mLlScanResultHint.setVisibility(View.GONE);
                    mErrorHint = false;
                    onScanCode();
                }else{
                    finish();
                }
                break;
            case R.id.tv_input:
                if(mErrorHint){
                    return;
                }

                if(!CheckNetworkStatus.isNetworkAvailable(ScanResultActivity.this)){
                    mLlScanResultHint.setVisibility(View.VISIBLE);
                    mTvScanResultHint.setText("无法与服务器通讯，请检查手机网络");
                    mTvReScan.setVisibility(View.GONE);
                    return ;
                }

                JSONObject jsonObject = new JSONObject();
                String deviceUniqueCode = mTvDeviceUniqueCode.getText().toString().trim();
                String timestamp = System.currentTimeMillis() / 1000 + "";

                Map<String,String> params = new HashMap<>();
                DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                deviceInfoBean.setSoft(mDisplay);
                deviceInfoBean.setOper(mOper);
                deviceInfoBean.setTerminal_model(mDeviceModel);

                String jsonString=new Gson().toJson(deviceInfoBean);
                LogUtils.i("jsonString:"+jsonString);

                params.put("terminal_id",deviceUniqueCode);
                params.put("mac_info",mMac);
                params.put("timestamp",timestamp);
                params.put("sign", Objects.requireNonNull(CommonMethod.getStrMd5(Objects.requireNonNull(CommonMethod.getStrMd5(deviceUniqueCode)).toLowerCase() + timestamp)).toLowerCase());
                params.put("device_info",jsonString);
                params.put("flag","0");
                String baseUrl = "https://cloud.background.adasplus.com/device_info_report_insert";
//                String baseUrl = "http://172.16.0.188:11110/device_info_report_insert";
//                String baseUrl = "http://36.112.77.195:11110/device_info_report_insert";

                String url = mApiRetrofit.getParamWithString(baseUrl,params);
                mApiRetrofit.setHttpsURL(url);
                RequestBody requestBody = RequestBody.create(MediaType.parse(ApiConstant.MEDIA_TYPE), jsonObject.toString());
                mPresenter.configDeviceUniqueCode(requestBody);

//                String deviceUniqueCode = mTvDeviceUniqueCode.getText().toString().trim();
//                ConfigBean configBean = new ConfigBean();
//                configBean.setProducerID(mVendorId);
//                configBean.setTerminalModel(mDeviceModel);
//                configBean.setTerminalId(deviceUniqueCode);
//                configBean.setManufactureDate(mDateCode);
//                String json = new Gson().toJson(configBean);
//
//                RequestBody requestBody = RequestBody.create(MediaType.parse(ApiConstant.MEDIA_TYPE), json);
//                mPresenter.configInfo(requestBody);
                break;
            case R.id.tv_rescan:
                mLlScanResultHint.setVisibility(View.GONE);
                mErrorHint = false;
                onScanCode();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void onScanCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ScanResultActivity.this, R.string.need_to_dynamically_acquire_permissions, Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(ScanResultActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
            } else {
                CommonMethod.StartActivityForResultCapture(ScanResultActivity.this);
            }
        } else {
            CommonMethod.StartActivityForResultCapture(ScanResultActivity.this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0 && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    CommonMethod.StartActivityForResultCapture(ScanResultActivity.this);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                if (content == null) return;
                if (content.length() < 14) return;
                int length = content.length();
                m3C = content.substring(0, 7);
                mTerminalId = content.substring(length - 8);
                mDateCode = content.substring(length - 11, length - 3);
                mDeviceModel = content.substring(7, 13);

                mTv3C.setText(m3C);
                mTvDeviceModel.setText(mDeviceModel);
                mTvDeviceUniqueCode.setText(String.format("%s%s", mDeviceType, mTerminalId));
            }
        }
    }
}
