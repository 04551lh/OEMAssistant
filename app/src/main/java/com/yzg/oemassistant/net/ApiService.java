package com.yzg.oemassistant.net;


import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.constant.ApiConstant;
import com.yzg.oemassistant.mvp.mdel.InformationModel;
import com.yzg.oemassistant.mvp.mdel.NullModel;
import com.yzg.oemassistant.mvp.mdel.SettingsModel;
import com.yzg.oemassistant.mvp.mdel.TerminalInfoModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.mvp
 * @ClassName: ApiService
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/20 2:36 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/20 2:36 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface ApiService {

    @POST(ApiConstant.REQUEST_TERMINAL)
    Observable<BaseResponse<TerminalInfoModel>> getTerminalInfo();

    @POST(ApiConstant.REQUEST_PROJECT_INFO)
    Observable<BaseResponse<InformationModel>> getProjectInfo();

    @POST(ApiConstant.CONFIG_TERMINAL)
    Observable<BaseResponse<NullModel>> configInfo(@Body RequestBody requestBody);

    @POST(ApiConstant.REQUEST_SPEEDS_INFO)
    Observable<BaseResponse<SettingsModel>> getSettingsInfo();

    @POST(ApiConstant.CONFIG_SPEEDS_DATA)
    Observable<BaseResponse<NullModel>> configSettings(@Body RequestBody requestBody);
}
