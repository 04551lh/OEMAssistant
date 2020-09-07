package com.yzg.oemassistant.mvp.mdel;

import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.mvp.contract.ScanResultContract;
import com.yzg.oemassistant.net.ApiRetrofit;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.mvp.mdel
 * @ClassName: InformationModel
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/30 2:27 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/30 2:27 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class InformationModel implements ScanResultContract.Model {

    /**
     * PrpdectKindCode : 640
     * mac : 82554511245
     */

    private String ProdectKindCode;
    private String mac;
    private String soft;

    @Override
    public Observable<BaseResponse<InformationModel>> getProjectInfo() {
        return ApiRetrofit.getInstance().getApi().getProjectInfo();
    }

    @Override
    public Observable<BaseResponse<NullModel>> configInfo(@Body RequestBody requestBody) {
        return ApiRetrofit.getInstance().getApi().configInfo(requestBody);
    }

    @Override
    public Observable<DeviceUniqueCodeModel> configDeviceUniqueCode(@Body RequestBody requestBody) {
        return ApiRetrofit.getInstance().getDeviceApi().configDeviceUniqueCode(requestBody);
    }

    public String getPrpdectKindCode() {
        return ProdectKindCode;
    }

    public void setProdectKindCode(String ProdectKindCode) {
        this.ProdectKindCode = ProdectKindCode;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSoft() {
        return soft;
    }

    public void setSoft(String soft) {
        this.soft = soft;
    }
}
