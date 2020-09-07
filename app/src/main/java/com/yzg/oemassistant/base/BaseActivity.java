package com.yzg.oemassistant.base;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.base
 * @ClassName: BaseActivity
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/20 2:09 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/20 2:09 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutResID());
        initViews();
    }

    public abstract int getLayoutResID();

    public abstract void initViews();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
