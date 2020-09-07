package com.yzg.oemassistant.fragment;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yzg.oemassistant.R;
import com.yzg.oemassistant.base.BaseFragment;
import com.yzg.oemassistant.constant.ApiConstant;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFragment {

    private ImageView mQRCode;
    private String mQRData;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void findViewById(View view) {
        mQRCode = view.findViewById(R.id.iv_qr_product);
        mQRData = Objects.requireNonNull(getArguments()).getString(ApiConstant.PRODUCT_CODE);
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
        return R.layout.fragment_product;
    }

}
