package com.yzg.oemassistant.constant;

/**
 * @ProjectName: Intelligent analysis terminal
 * @Package: com.adasplus.intelligentanalysisterminal.constant
 * @ClassName: ApiConstant
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/23 11:30 AM
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/23 11:30 AM
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ApiConstant {

    /*
     * 命名规则：获取数据以REQUEST_XXX;配置数据CONFIG_XXX
     * */
    public static final String ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";

    public static final String MEDIA_TYPE = "application/json;charset=GBK";

    //IMEI
    public final static String IMEI_CODE = "imei";
    //产品编码
    public final static String PRODUCT_CODE = "product";
    //日期编码
    public final static String DATE_CODE = "date";

    //    public final static String BASE_URL = "http://192.168.1.1:8000";
    public final static String USB_BASE_URL = "//192.168.42.254:8000";
    public final static String WIFI_BASE_URL = "//192.168.1.1:8000";
    //后台服务验证
    public final static String DEVICE_UNIQUE_CODE = "/device_info_report_insert";
    //获取mac
    public static final String REQUEST_PROJECT_INFO = "/factoryProdectKindRequest";
    //配置
    public static final String CONFIG_TERMINAL = "/factoryTerminalInfoConfig";
    //获取生成二维码
    public static final String REQUEST_TERMINAL = "/factoryTerminalInfoRequest";
    //速度信息获取
    public static final String REQUEST_SPEEDS_INFO = "/speedRequest";
    //速度信息配置
    public static final String CONFIG_SPEEDS_DATA = "/speedConfig";


}
