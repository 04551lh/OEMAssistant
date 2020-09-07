package com.yzg.oemassistant.mvp.bean;

/**
 * Created by dell on 2019/12/7 12:18
 * Description:
 * Emain: 1187278976@qq.com
 */
public class ConfigBean {

    /**
     * threeCCode : 1632210221
     * terminalModel : 1632210221
     * producerID : 1632210221
     * terminalId : 1632210221
     */

    private String terminalModel;
    private String producerID;
    private String terminalID;
    private String manufactureDate;

    public String getTerminalModel() {
        return terminalModel;
    }

    public void setTerminalModel(String terminalModel) {
        this.terminalModel = terminalModel;
    }

    public String getProducerID() {
        return producerID;
    }

    public void setProducerID(String producerID) {
        this.producerID = producerID;
    }

    public String getTerminalId() {
        return terminalID;
    }

    public void setTerminalId(String terminalId) {
        this.terminalID = terminalId;
    }

    @Override
    public String toString() {
        return "ConfigBean{" +
                ", terminalModel='" + terminalModel + '\'' +
                ", producerID='" + producerID + '\'' +
                ", terminalID='" + terminalID + '\'' +
                '}';
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }
}
