package com.yzg.oemassistant.activity;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.yzg.oemassistant.R;
import com.yzg.oemassistant.Utils.ErrorUtils;
import com.yzg.oemassistant.base.BaseMvpActivity;
import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.constant.ApiConstant;
import com.yzg.oemassistant.mvp.contract.SettingsContract;
import com.yzg.oemassistant.mvp.mdel.NullModel;
import com.yzg.oemassistant.mvp.mdel.SettingsModel;
import com.yzg.oemassistant.mvp.presenter.SettingsPresenter;
import com.yzg.oemassistant.tool.BaseDialog;

import io.reactivex.rxjava3.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SettingsActivity extends BaseMvpActivity<SettingsPresenter> implements SettingsContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private ImageView mIvSettingsBack;
    private ImageView mIvPlusSpeed;
    private ImageView mIvSimulationSpeed;
    private TextView mTvSave;
    private SwipeRefreshLayout mSRLSettings;
    private int mSwitchClose;
    private int mSwitchOpen;
    private int mSpeedEnable;
    private int mSimulationEnable;
    private InputMethodManager mInputMethodManager;
    private BaseDialog mBaseDialog;
    private SettingsModel mSettingsModel;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_settings;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void initViews() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mIvSettingsBack = findViewById(R.id.iv_settings_back);
        mIvPlusSpeed = findViewById(R.id.iv_plus_switch);
        mIvSimulationSpeed = findViewById(R.id.iv_simulation_switch);
        mTvSave = findViewById(R.id.tv_save);
        mSRLSettings = findViewById(R.id.srl_settings);
        mBaseDialog = BaseDialog.showDialog(SettingsActivity.this);
        initData();
        mPresenter = new SettingsPresenter();
        mPresenter.attachView(this);
        mPresenter.getSettingsInfo();
        setListener();
    }

    private void setListener() {
        mSRLSettings.setOnRefreshListener(this);
        mIvSettingsBack.setOnClickListener(this);
        mIvPlusSpeed.setOnClickListener(this);
        mIvSimulationSpeed.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //键盘处理
        switch (v.getId()) {
            case R.id.iv_settings_back:
                if (mInputMethodManager.isActive()) {
                    mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);  //强制隐藏
                }
                finish();
                break;
            case R.id.iv_plus_switch:
                mSpeedEnable = 1;
                mIvPlusSpeed.setImageResource(mSwitchOpen);
                mSimulationEnable = 0;
                mIvSimulationSpeed.setImageResource(mSwitchClose);
                break;
            case R.id.iv_simulation_switch:
                mSpeedEnable = 0;
                mIvPlusSpeed.setImageResource(mSwitchClose);
                mSimulationEnable = 1;
                mIvSimulationSpeed.setImageResource(mSwitchOpen);
                break;
            case R.id.tv_save:
                if (mSettingsModel == null) {
                    mSettingsModel = new SettingsModel();
                }
                if (mSettingsModel.getPulseSpeed() == null) {
                    mSettingsModel.setPulseSpeed(new SettingsModel.PulseSpeedBean());
                }
                mSettingsModel.getPulseSpeed().setEnable(mSpeedEnable);
                mSettingsModel.getPulseSpeed().setPulseCoefficient(3600);
                mSettingsModel.getSimulateSpeed().setValue(60);
                mSettingsModel.getSimulateSpeed().setEnable(mSimulationEnable);
                onSave();
                break;
        }
    }

    private void initData() {
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mSRLSettings.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSRLSettings.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwitchClose = R.drawable.switch_close_icon;
        mSwitchOpen = R.drawable.switch_open_icon;
    }

    private void onSave() {
        String json = new Gson().toJson(mSettingsModel);
        RequestBody requestBody = RequestBody.create(MediaType.parse(ApiConstant.MEDIA_TYPE), json.toString());
        mPresenter.configSettings(requestBody);
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        mPresenter.getSettingsInfo();
    }

    @Override
    public void onSettingsInfoSuccess(BaseResponse<SettingsModel> bean) {
        mSettingsModel = bean.getResult();
        if (mSettingsModel.getPulseSpeed().getEnable() == 1) {
            mIvPlusSpeed.setImageResource(mSwitchOpen);
            mIvSimulationSpeed.setImageResource(mSwitchClose);
        }
        if (mSettingsModel.getSimulateSpeed().getEnable() == 1) {
            mIvPlusSpeed.setImageResource(mSwitchClose);
            mIvSimulationSpeed.setImageResource(mSwitchOpen);
        }
        mSettingsModel.getPulseSpeed().setEnable(mSpeedEnable);
        mSettingsModel.getPulseSpeed().setAutoCalibration(0);
        mSettingsModel.getSimulateSpeed().setEnable(mSimulationEnable);
        mSettingsModel.getWithGPSSpeedEnable().setEnable(0);
    }

    @Override
    public void onNullModelSuccess(BaseResponse<NullModel> bean) {
        if (bean.getStatusCode() == 0) {
            Toast.makeText(SettingsActivity.this, R.string.device_info_success, Toast.LENGTH_SHORT).show();
            finish();
        }
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
        Toast.makeText(SettingsActivity.this, errMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(@NonNull Throwable e) {
        ErrorUtils.exceptionHandling(SettingsActivity.this, e);
    }
}
