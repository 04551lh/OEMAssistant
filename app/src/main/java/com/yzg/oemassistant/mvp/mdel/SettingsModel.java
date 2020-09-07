package com.yzg.oemassistant.mvp.mdel;

import com.yzg.oemassistant.base.BaseResponse;
import com.yzg.oemassistant.mvp.contract.SettingsContract;
import com.yzg.oemassistant.net.ApiRetrofit;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;

/**
 * @ProjectName: OEMAssistant
 * @Package: com.yzg.oemassistant.mvp.mdel
 * @ClassName: SettingsModel
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/9/7 12:04 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/9/7 12:04 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class SettingsModel implements SettingsContract.Model {

    private PulseSpeedBean pulseSpeed;
    private SimulateSpeedBean simulateSpeed;
    private WithGPSSpeedEnableBean withGPSSpeedEnable;

    public PulseSpeedBean getPulseSpeed() {
        return pulseSpeed;
    }

    public void setPulseSpeed(PulseSpeedBean pulseSpeed) {
        this.pulseSpeed = pulseSpeed;
    }

    public SimulateSpeedBean getSimulateSpeed() {
        return simulateSpeed;
    }

    public void setSimulateSpeed(SimulateSpeedBean simulateSpeed) {
        this.simulateSpeed = simulateSpeed;
    }

    public WithGPSSpeedEnableBean getWithGPSSpeedEnable() {
        return withGPSSpeedEnable;
    }

    public void setWithGPSSpeedEnable(WithGPSSpeedEnableBean withGPSSpeedEnable) {
        this.withGPSSpeedEnable = withGPSSpeedEnable;
    }

    @Override
    public Observable<BaseResponse<SettingsModel>> getSettingsInfo() {
        return ApiRetrofit.getInstance().getApi().getSettingsInfo();
    }

    @Override
    public Observable<BaseResponse<NullModel>> configSettings(RequestBody requestBody) {
        return ApiRetrofit.getInstance().getApi().configSettings(requestBody);
    }

    public static class PulseSpeedBean {
        /**
         * enable : 1
         * pulseCoefficient : 1000
         * autoCalibration : 0
         * allowErrorValue : 0
         */

        private int enable;
        private int pulseCoefficient;
        private int autoCalibration;
        private int allowErrorValue;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getPulseCoefficient() {
            return pulseCoefficient;
        }

        public void setPulseCoefficient(int pulseCoefficient) {
            this.pulseCoefficient = pulseCoefficient;
        }

        public int getAutoCalibration() {
            return autoCalibration;
        }

        public void setAutoCalibration(int autoCalibration) {
            this.autoCalibration = autoCalibration;
        }

        public int getAllowErrorValue() {
            return allowErrorValue;
        }

        public void setAllowErrorValue(int allowErrorValue) {
            this.allowErrorValue = allowErrorValue;
        }
    }

    public static class SimulateSpeedBean {
        /**
         * enable : 0
         * value : 0
         */

        private int enable;
        private int value;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class WithGPSSpeedEnableBean {
        /**
         * enable : 0
         */

        private int enable;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }
    }
}
