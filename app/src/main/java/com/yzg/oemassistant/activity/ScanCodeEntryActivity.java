package com.yzg.oemassistant.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.yzg.oemassistant.R;
import com.yzg.oemassistant.Utils.LogUtils;
import com.yzg.oemassistant.Utils.SharedPreferencesHelper;
import com.yzg.oemassistant.base.BaseActivity;
import com.yzg.oemassistant.tool.CommonMethod;
import com.yzq.zxinglibrary.common.Constant;

public class ScanCodeEntryActivity extends BaseActivity implements View.OnClickListener {

    public final static String VENDOR_ID = "vendorId";
    public final static String _3C_AUTHENTICATION_CODE = "3CAuthenticationCode";
    public final static String DEVICE_MODEL = "deviceModel";
    public final static String DATE_CODE = "dataCode";
    public final static String TERMINAL_ID = "terminalId";
    //厂商Id
    private EditText mEtVendorId;
    //保存按钮
    private TextView mTvScanCodeSave;
    //存储厂商ID
    private SharedPreferencesHelper mSharedPreferencesHelper;
    //不可点击颜色
    private int mNoClickColor;
    //不可点击颜色
    private int mClickColor;
    //不可点击背景图
    private Drawable mNoClickDrawable;
    //可点击背景图
    private Drawable mClickDrawable;
    //是否保存
    private boolean mIsSave;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_scan_code_entry;
    }

    @Override
    public void initViews() {
        LogUtils.setClassName("ScanCodeEntry");
        ImageView ivScanCodeBack = findViewById(R.id.iv_scan_code_back);
        mEtVendorId = findViewById(R.id.et_vendor_id);
        //编辑按钮
        TextView tvScanCodeEdit = findViewById(R.id.tv_scan_code_edit);
        mTvScanCodeSave = findViewById(R.id.tv_scan_code_save);
        //扫码按钮
        TextView mTvScanCode = findViewById(R.id.tv_scan_code);
        ivScanCodeBack.setOnClickListener(this);
        tvScanCodeEdit.setOnClickListener(this);
        mTvScanCodeSave.setOnClickListener(this);
        mTvScanCode.setOnClickListener(this);
        mNoClickColor = getResources().getColor(R.color.colorCameraFont);
        mClickColor = Color.WHITE;
        mNoClickDrawable = getResources().getDrawable(R.drawable.hollow_circle);
        mClickDrawable = getResources().getDrawable(R.drawable.solid_round);
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        String productId = (String) mSharedPreferencesHelper.get(VENDOR_ID, null);
        String product = productId == null ? "75208" : productId;
        mEtVendorId.setText(product);
        mEtVendorId.setSelection(product.length());
        mIsSave = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan_code_back:
                finish();
                break;
            case R.id.tv_scan_code_edit:
                mEtVendorId.setEnabled(true);
                mTvScanCodeSave.setBackground(mClickDrawable);
                mTvScanCodeSave.setTextColor(mClickColor);
                mIsSave = false;
                break;
            case R.id.tv_scan_code_save:
                if (TextUtils.isEmpty(mEtVendorId.getText())) {
                    Toast.makeText(ScanCodeEntryActivity.this, R.string.please_entry_vendor_id, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtVendorId.getText().toString().trim().length() != 5) {
                    Toast.makeText(ScanCodeEntryActivity.this, R.string.please_corrent_vendor_id, Toast.LENGTH_SHORT).show();
                    return;
                }
                mEtVendorId.setEnabled(false);
                mTvScanCodeSave.setBackground(mNoClickDrawable);
                mTvScanCodeSave.setTextColor(mNoClickColor);
                mIsSave = true;
                break;
            case R.id.tv_scan_code:
                if (!mIsSave) {
                    Toast.makeText(ScanCodeEntryActivity.this, R.string.please_save, Toast.LENGTH_SHORT).show();
                    return;
                }
                mSharedPreferencesHelper.put(VENDOR_ID, mEtVendorId.getText());
                onScanCode();
                break;
        }
    }

    private void onScanCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ScanCodeEntryActivity.this, R.string.need_to_dynamically_acquire_permissions, Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(ScanCodeEntryActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
            } else {
                CommonMethod.StartActivityForResultCapture(ScanCodeEntryActivity.this);
            }
        } else {
            CommonMethod.StartActivityForResultCapture(ScanCodeEntryActivity.this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0 && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    CommonMethod.StartActivityForResultCapture(ScanCodeEntryActivity.this);
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
                LogUtils.i("content:" + content);
                if (content == null) return;
                if (content.length() < 14) return;
                int length = content.length();
                String terminalId = content.substring(length - 8);
                String dateCode = content.substring(length - 11, length - 3);
                String vendorId = mEtVendorId.getText().toString().trim();
                String deviceModel = content.substring(7, 13);
                Intent intent = new Intent(ScanCodeEntryActivity.this, ScanResultActivity.class);

                intent.putExtra(_3C_AUTHENTICATION_CODE, content.substring(0, 7));
                intent.putExtra(DEVICE_MODEL, deviceModel);
                intent.putExtra(TERMINAL_ID, terminalId);
                intent.putExtra(DATE_CODE, dateCode);
                intent.putExtra(VENDOR_ID, vendorId);
                startActivity(intent);
            }
        }
    }
}
