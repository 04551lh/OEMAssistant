package com.yzg.oemassistant.mvp.mdel;

import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.mvp.contract.QRCodeContract;
import com.yzg.oemassistant.net.ApiRetrofit;

import io.reactivex.rxjava3.core.Observable;

/**
 * @ProjectName: OEMAssistant
 * @Package: com.yzg.oemassistant.mvp.mdel
 * @ClassName: TerminalInfoModel
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/9/5 11:19 AM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/9/5 11:19 AM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class TerminalInfoModel implements QRCodeContract.Model {
    private String imei;
    private String productCoding;
    private String manufactureDate;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getProductCoding() {
        return productCoding;
    }

    public void setProductCoding(String productCoding) {
        this.productCoding = productCoding;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    @Override
    public Observable<BaseResponse<TerminalInfoModel>> getTerminalInfo() {
        return ApiRetrofit.getInstance().getApi().getTerminalInfo();
    }
}
