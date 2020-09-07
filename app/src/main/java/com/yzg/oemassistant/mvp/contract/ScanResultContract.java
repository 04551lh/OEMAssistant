package com.yzg.oemassistant.mvp.contract;

import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.base.BaseView;
import com.yzg.oemassistant.mvp.mdel.DeviceUniqueCodeModel;
import com.yzg.oemassistant.mvp.mdel.InformationModel;
import com.yzg.oemassistant.mvp.mdel.NullModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.constant
 * @ClassName: DeviceInformationContract
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/25 11:51 AM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/25 11:51 AM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface ScanResultContract {
    interface Model {
        Observable<BaseResponse<InformationModel>> getProjectInfo();

        Observable<BaseResponse<NullModel>> configInfo(@Body RequestBody requestBody);

        Observable<DeviceUniqueCodeModel> configDeviceUniqueCode(@Body RequestBody requestBody);
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(String errMessage);

        void onInfoSuccess(BaseResponse<InformationModel> bean);

        void onNullSuccess(BaseResponse<NullModel> bean);

        void onDeviceUniqueCodeSuccess(DeviceUniqueCodeModel bean);
    }

    interface Presenter {
        /**
         * 获取设备信息
         */
        void getInfo();

        void  configInfo(@Body RequestBody requestBody);

        void configDeviceUniqueCode(@Body RequestBody requestBody);

    }

}
