package com.yzg.oemassistant.mvp.contract;

import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.base.BaseView;
import com.yzg.oemassistant.mvp.mdel.NullModel;
import com.yzg.oemassistant.mvp.mdel.SettingsModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;

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
public interface SettingsContract {

    interface Model {
        Observable<BaseResponse<SettingsModel>> getSettingsInfo();

        Observable<BaseResponse<NullModel>> configSettings(@Body RequestBody requestBody);
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(String errMessage);

        void onSettingsInfoSuccess(BaseResponse<SettingsModel> bean);

        void onNullModelSuccess(BaseResponse<NullModel> bean);
    }

    interface Presenter {
        void getSettingsInfo();

        void configSettings(@Body RequestBody requestBody);
    }
}
