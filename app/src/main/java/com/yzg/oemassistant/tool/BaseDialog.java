package com.yzg.oemassistant.tool;

import android.app.Dialog;
import android.content.Context;

import com.yzg.oemassistant.R;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.tool
 * @ClassName: BaseDialog
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/29 6:36 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/29 6:36 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BaseDialog  extends Dialog {
    private static BaseDialog mBaseDialog;

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);

    }
    //显示dialog的方法
    public static BaseDialog showDialog(Context context){
        mBaseDialog = new BaseDialog(context, R.style.MyDialog);//dialog样式
        mBaseDialog.setContentView(R.layout.dialog_load_progress);//dialog布局文件
        mBaseDialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog
        mBaseDialog.setCancelable(false);
        return mBaseDialog;
    }
}
