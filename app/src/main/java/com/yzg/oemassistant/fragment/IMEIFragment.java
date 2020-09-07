package com.yzg.oemassistant.fragment;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yzg.oemassistant.R;
import com.yzg.oemassistant.base.BaseFragment;
import com.yzg.oemassistant.constant.ApiConstant;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.util.Objects;

public class IMEIFragment extends BaseFragment {
    private ImageView mQRCode;
    private String mQRData;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void findViewById(View view) {
        mQRCode = view.findViewById(R.id.iv_qr_imei);
        mQRData = Objects.requireNonNull(getArguments()).getString(ApiConstant.IMEI_CODE);
    }

    @Override
    public void setViewData(View view) {
        if (!mQRData.equals("")) {
            //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            Bitmap bitmap = CodeCreator.createQRCode(mQRData, 400, 400, null);
            mQRCode.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getActivity(), "Text can not be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int setLayoutResId() {
        return R.layout.fragment_i_m_e_i;
    }

}
