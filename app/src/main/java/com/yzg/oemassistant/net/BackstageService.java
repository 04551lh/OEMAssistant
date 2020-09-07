package com.yzg.oemassistant.net;

import com.yzg.oemassistant.constant.ApiConstant;
import com.yzg.oemassistant.mvp.mdel.DeviceUniqueCodeModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.net
 * @ClassName: BackstageService
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/31 5:38 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/31 5:38 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface BackstageService {

    @POST(ApiConstant.DEVICE_UNIQUE_CODE)
    Observable<DeviceUniqueCodeModel> configDeviceUniqueCode(@Body RequestBody requestBody);

}
