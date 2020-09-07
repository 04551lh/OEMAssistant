package com.yzg.oemassistant.mvp.presenter;

import com.yzg.oemassistant.base.BasePresenter;
import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.mvp.contract.SettingsContract;
import com.yzg.oemassistant.mvp.mdel.NullModel;
import com.yzg.oemassistant.mvp.mdel.SettingsModel;
import com.yzg.oemassistant.net.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @ProjectName: OEMAssistant
 * @Package: com.yzg.oemassistant.mvp.presenter
 * @ClassName: QRCodePresenter
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/9/7 10:56 AM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/9/7 10:56 AM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class SettingsPresenter extends BasePresenter<SettingsContract.View> implements SettingsContract.Presenter {

    private SettingsContract.Model mModel;

    public SettingsPresenter(){
        mModel = new SettingsModel();
    }

    @Override
    public void getSettingsInfo() {
        if(isViewAttached()){
            return;
        }
        mModel.getSettingsInfo().compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseResponse<SettingsModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseResponse<SettingsModel> settingsModelBaseResponse) {
                        mView.onSettingsInfoSuccess(settingsModelBaseResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onError(e);
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void configSettings(RequestBody requestBody) {
        if(isViewAttached()){
            return;
        }
        mModel.configSettings(requestBody).compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseResponse<NullModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseResponse<NullModel> nullModelBaseResponse) {
                        mView.onNullModelSuccess(nullModelBaseResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onError(e);
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }
}
