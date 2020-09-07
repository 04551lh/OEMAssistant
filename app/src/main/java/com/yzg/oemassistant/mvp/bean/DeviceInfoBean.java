package com.yzg.oemassistant.mvp.bean;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.mvp.bean
 * @ClassName: DeviceInfoBean
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/8/13 2:52 PM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/13 2:52 PM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DeviceInfoBean {


    /**
     * terminal_model : KY-BJX
     * oper : KY800
     * soft : V3.9.24
     */

    private String terminal_model;
    private String oper;
    private String soft;

    public String getTerminal_model() {
        return terminal_model;
    }

    public void setTerminal_model(String terminal_model) {
        this.terminal_model = terminal_model;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getSoft() {
        return soft;
    }

    public void setSoft(String soft) {
        this.soft = soft;
    }
}
