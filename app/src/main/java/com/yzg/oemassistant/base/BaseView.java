package com.yzg.oemassistant.base;

import autodispose2.AutoDisposeConverter;
import io.reactivex.rxjava3.annotations.NonNull;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.base
 * @ClassName: BaseView
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/25 11:34 AM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/25 11:34 AM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface BaseView {

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 数据获取失败
     * @param errMessage 错误信息
     */
    void onError(String errMessage);


    void onError(@NonNull Throwable e);
    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T> 返回类型
     * @return 绑定结果
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();

}

