package com.yzg.oemassistant.mvp.presenter;

import com.yzg.oemassistant.base.BasePresenter;
import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.mvp.contract.QRCodeContract;
import com.yzg.oemassistant.mvp.mdel.TerminalInfoModel;
import com.yzg.oemassistant.net.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

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
public class QRCodePresenter extends BasePresenter<QRCodeContract.View> implements QRCodeContract.Presenter {

    private QRCodeContract.Model mModel;

    public QRCodePresenter(){
        mModel = new TerminalInfoModel();
    }

    @Override
    public void getTerminalInfo() {
        if(isViewAttached()){
            return;
        }
        mModel.getTerminalInfo().compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseResponse<TerminalInfoModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseResponse<TerminalInfoModel> terminalInfoModelBaseResponse) {
                        mView.onTerminalInfoSuccess(terminalInfoModelBaseResponse);
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
