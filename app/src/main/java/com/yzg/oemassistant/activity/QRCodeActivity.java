package com.yzg.oemassistant.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.yzg.oemassistant.R;
import com.yzg.oemassistant.Utils.ErrorUtils;
import com.yzg.oemassistant.base.BaseMvpActivity;
import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.constant.ApiConstant;
import com.yzg.oemassistant.fragment.DateFragment;
import com.yzg.oemassistant.fragment.IMEIFragment;
import com.yzg.oemassistant.fragment.ProductFragment;
import com.yzg.oemassistant.mvp.contract.QRCodeContract;
import com.yzg.oemassistant.mvp.mdel.TerminalInfoModel;
import com.yzg.oemassistant.mvp.presenter.QRCodePresenter;
import com.yzg.oemassistant.tool.BaseDialog;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class QRCodeActivity extends BaseMvpActivity<QRCodePresenter> implements QRCodeContract.View, View.OnClickListener {
    private TextView mTvImeiCode;
    private TextView mTvProductCode;
    private TextView mTvDateCode;
    private TextView mTvHomePage;
    private TextView mTvNext;

    private IMEIFragment mImeiFragment;
    private ProductFragment mProductFragment;
    private DateFragment mDateFragment;

    private String mBackHomePage, mNextPage, mPreviousPage, mReScanning,mVendorId;

    private TerminalInfoModel mTerminalInfoModel;
    private final static int request_code = 0;
    private BaseDialog mBaseDialog;


    @Override
    public int getLayoutResID() {
        return R.layout.activity_q_r_code;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void initViews() {
        mImeiFragment = new IMEIFragment();
        mProductFragment = new ProductFragment();
        mDateFragment = new DateFragment();
        mTvImeiCode = findViewById(R.id.tv_imie_code);
        mTvProductCode = findViewById(R.id.tv_product_code);
        mTvDateCode = findViewById(R.id.tv_date_code);
        mTvHomePage = findViewById(R.id.tv_homepage);
        mTvNext = findViewById(R.id.tv_next);;
        mPresenter = new QRCodePresenter();
        mPresenter.attachView(this);
        mPresenter.getTerminalInfo();
        mBaseDialog = BaseDialog.showDialog(QRCodeActivity.this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initData() {
        mBackHomePage = getResources().getString(R.string.back_homepage);
        mNextPage = getResources().getString(R.string.next_page);
        mPreviousPage = getResources().getString(R.string.previous_page);
        mReScanning = getResources().getString(R.string.scanning);
        mVendorId = Objects.requireNonNull(getIntent().getExtras()).getString(ScanCodeEntryActivity.VENDOR_ID);
        setListener();
    }

    private void setListener() {
        mTvHomePage.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
        mTvImeiCode.setOnClickListener(this);
        mTvProductCode.setOnClickListener(this);
        mTvDateCode.setOnClickListener(this);
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!mImeiFragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putString(ApiConstant.IMEI_CODE, mTerminalInfoModel.getImei());
            mImeiFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fl_contain, mImeiFragment);
        }
        if (!mProductFragment.isAdded()) {
            String productCoding = mTerminalInfoModel.getProductCoding();
            Bundle bundle = new Bundle();
            bundle.putString(ApiConstant.PRODUCT_CODE, productCoding);
            mProductFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fl_contain, mProductFragment);
        }
        if (!mDateFragment.isAdded()) {
            String date = mTerminalInfoModel.getManufactureDate();
            if (!date.contains(".")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                Date date1 = null;
                try {
                    date1 = format.parse(date);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    date = format1.format(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Bundle bundle = new Bundle();
            bundle.putString(ApiConstant.DATE_CODE, date);
            mDateFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fl_contain, mDateFragment);
        }
        hideAllFragment(fragmentTransaction);
        fragmentTransaction.commit();
        clickTab(mImeiFragment);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.hide(mImeiFragment);
        fragmentTransaction.hide(mDateFragment);
        fragmentTransaction.hide(mProductFragment);
    }

    private void clickTab(Fragment fragment) {
        clearSelected();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
        changeTabStyle(fragment);
    }

    private void changeTabStyle(Fragment fragment) {
        if (fragment instanceof IMEIFragment) {
            mTvImeiCode.setTextColor(Color.parseColor("#5677FC"));
        }

        if (fragment instanceof ProductFragment) {
            mTvProductCode.setTextColor(Color.parseColor("#5677FC"));
        }

        if (fragment instanceof DateFragment) {
            mTvDateCode.setTextColor(Color.parseColor("#5677FC"));
        }
    }

    private void clearSelected() {
        if (!mImeiFragment.isHidden()) {
            mTvImeiCode.setTextColor(Color.BLACK);
        }

        if (!mProductFragment.isHidden()) {
            mTvProductCode.setTextColor(Color.BLACK);
        }

        if (!mDateFragment.isHidden()) {
            mTvDateCode.setTextColor(Color.BLACK);
        }
    }

    private Fragment getCurrentFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        Fragment currentFragment = getCurrentFragment();
        switch (v.getId()) {
            case R.id.tv_imie_code:
                clickTab(mImeiFragment);
                mTvHomePage.setText(mBackHomePage);
                mTvNext.setText(mNextPage);
                break;
            case R.id.tv_product_code:
                clickTab(mProductFragment);
                mTvHomePage.setText(mPreviousPage);
                mTvNext.setText(mNextPage);
                break;
            case R.id.tv_date_code:
                clickTab(mDateFragment);
                mTvHomePage.setText(mPreviousPage);
                mTvNext.setText(mReScanning);
                break;
            case R.id.tv_homepage:
                if (mTvHomePage.getText().toString().trim().equals(mBackHomePage))
                    startActivity(new Intent(QRCodeActivity.this, MainActivity.class));
                else {
                    if (currentFragment instanceof ProductFragment) {
                        clickTab(mImeiFragment);
                        mTvHomePage.setText(mBackHomePage);
                    } else if (currentFragment instanceof DateFragment) {
                        clickTab(mProductFragment);
                        mTvNext.setText(mNextPage);
                    }
                }
                break;
            case R.id.tv_next:
                if (currentFragment instanceof IMEIFragment) {
                    clickTab(mProductFragment);
                    mTvHomePage.setText(mPreviousPage);
                } else if (currentFragment instanceof ProductFragment) {
                    clickTab(mDateFragment);
                    mTvNext.setText(mReScanning);
                } else if (currentFragment instanceof DateFragment) {
                    Intent intent = new Intent(QRCodeActivity.this, CaptureActivity.class);
                    ZxingConfig config = new ZxingConfig();
                    config.setPlayBeep(true);//是否播放扫描声音 默认为true
                    config.setShake(true);//是否震动  默认为true
//                    config.setDecodeBarCode(true);//是否扫描条形码 默认为true
//                    config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
                    config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
                    config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
                    config.setFullScreenScan(true);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                    intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                    startActivityForResult(intent, request_code);
                }
                break;
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
                String terminalId = content.substring(length - 8);
                String dateCode = content.substring(length - 11, length - 3);
                String deviceModel = content.substring(7, 13);
                String date = "";
                if (content != null && content.length() > 8) terminalId = content.substring(content.length() - 8);
                if (content != null && content.length() > 11) date = content.substring(content.length() - 11,content.length()-3);
                Intent intent = new Intent(QRCodeActivity.this, ScanResultActivity.class);
                intent.putExtra(ScanCodeEntryActivity._3C_AUTHENTICATION_CODE, content.substring(0, 7));
                intent.putExtra(ScanCodeEntryActivity.DEVICE_MODEL, deviceModel);
                intent.putExtra(ScanCodeEntryActivity.TERMINAL_ID, terminalId);
                intent.putExtra(ScanCodeEntryActivity.DATE_CODE, date);
                intent.putExtra(ScanCodeEntryActivity.VENDOR_ID, mVendorId);
                startActivity(intent);
                finish();
            }
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
        Toast.makeText(QRCodeActivity.this,errMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTerminalInfoSuccess(BaseResponse<TerminalInfoModel> bean) {
        mTerminalInfoModel = bean.getResult();
        initData();
        initFragment();
    }

    @Override
    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
        ErrorUtils.exceptionHandling(this,e);
    }
}
