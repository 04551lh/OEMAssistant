package com.yzg.oemassistant.base;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.base
 * @ClassName: BasePresenter
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/25 11:33 AM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/25 11:33 AM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BasePresenter<V extends BaseView> {

    protected V mView;


    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    public void attachView(V view) {
        this.mView = view;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */

    void detachView() {
        this.mView = null;
    }

    /**
     * View是否绑定
     *
     * @return 绑定结果
     */
    protected boolean isViewAttached() {
        return mView == null;
    }


}

