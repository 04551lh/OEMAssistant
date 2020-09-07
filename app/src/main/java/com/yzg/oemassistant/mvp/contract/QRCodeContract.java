package com.yzg.oemassistant.mvp.contract;

import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.base.BaseView;
import com.yzg.oemassistant.mvp.mdel.TerminalInfoModel;

import io.reactivex.rxjava3.core.Observable;

/**
 * @ProjectName: OEMAssistant
 * @Package: com.yzg.oemassistant.mvp.contract
 * @ClassName: QRCodeContract
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/9/7 10:51 AM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/9/7 10:51 AM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface QRCodeContract {

    interface  Model{
        Observable<BaseResponse<TerminalInfoModel>> getTerminalInfo();
    }

    interface View extends BaseView{
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(String errMessage);

        void onTerminalInfoSuccess(BaseResponse<TerminalInfoModel> bean);
    }

    interface Presenter{
        void getTerminalInfo();
    }
}
