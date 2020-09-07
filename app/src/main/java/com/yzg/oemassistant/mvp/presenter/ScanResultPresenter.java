package com.yzg.oemassistant.mvp.presenter;

import com.yzg.oemassistant.Utils.ErrorUtils;
import com.yzg.oemassistant.base.BasePresenter;
import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.mvp.contract.ScanResultContract;
import com.yzg.oemassistant.mvp.mdel.DeviceUniqueCodeModel;
import com.yzg.oemassistant.mvp.mdel.InformationModel;
import com.yzg.oemassistant.mvp.mdel.NullModel;
import com.yzg.oemassistant.net.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.mvp.presenter
 * @ClassName: ScanResultPresenter
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/30 2:47 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/30 2:47 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ScanResultPresenter extends BasePresenter<ScanResultContract.View> implements ScanResultContract.Presenter {

    private ScanResultContract.Model mModel;

    public ScanResultPresenter(){mModel = new InformationModel();}

    /**
     * 获取设备信息
     */
    @Override
    public void getInfo() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (isViewAttached()) {
            return;
        }

        mModel.getProjectInfo()
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<BaseResponse<InformationModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseResponse<InformationModel> informationModelBaseResponse) {
                        int statusCode = informationModelBaseResponse.getStatusCode();
                        if(statusCode == 0){
                            mView.onInfoSuccess(informationModelBaseResponse);
                        }else{
                            mView.onError(ErrorUtils.onErrorNetData(statusCode));
                        }
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
    public void configInfo(RequestBody requestBody) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (isViewAttached()) {
            return;
        }
        mModel.configInfo(requestBody)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseResponse<NullModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseResponse<NullModel> nullModelBaseResponse) {
                        int statusCode = nullModelBaseResponse.getStatusCode();
                        if(statusCode == 0){
                            mView.onNullSuccess(nullModelBaseResponse);
                        }else{
                            mView.onError(ErrorUtils.onErrorNetData(statusCode));
                        }
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
    public void configDeviceUniqueCode(RequestBody requestBody) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (isViewAttached()) {
            return;
        }
        mModel.configDeviceUniqueCode(requestBody).compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<DeviceUniqueCodeModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull DeviceUniqueCodeModel deviceUniqueCodeModelBaseResponse) {
                       mView.onDeviceUniqueCodeSuccess(deviceUniqueCodeModelBaseResponse);
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
