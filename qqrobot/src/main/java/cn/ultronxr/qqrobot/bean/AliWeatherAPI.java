package cn.ultronxr.qqrobot.bean;

/**
 * @author Ultronxr
 * @date 2021/01/25 21:08
 *
 * 阿里云天气查询接口bean
 * 相关文档请参看 {@link "https://market.aliyun.com/products/57096001/cmapi010812.html"}
 */
public abstract class AliWeatherAPI extends GlobalData {

    //private static final String APP_KEY = ResBundle.ALI_CLOUD.getString("ali.weatherAPI.app.key");

    //private static final String APP_SECRET = ResBundle.ALI_CLOUD.getString("ali.weatherAPI.app.secret");

    private static final String APP_CODE = ResBundle.ALI_CLOUD.getString("ali.weatherAPI.app.code");

    /** 请求接口的身份验证信息（添加在请求头，以“Authorization”为键） */
    public static final String AUTH_CODE = "APPCODE " + APP_CODE;

    /** API请求的URL公共部分 */
    private static final String PRE_URL = "http://ali-weather.showapi.com/";

    /**
     * 不同用途、查询不同信息的API的请求地址
     */
    public enum API {
        //地名查询天气
        areaToWeather(PRE_URL + "area-to-weather"),
        //经纬度查询天气
        gpsToWeather(PRE_URL + "gps-to-weather"),
        //景点名称查询天气
        spotToWeather(PRE_URL + "spot-to-weather"),
        //区号邮编查询天气
        phonePostCodeToWeather(PRE_URL + "phone-post-code-weeather"),
        //IP查询天气
        ipToWeather(PRE_URL + "ip-to-weather"),
        //查询24小时预报
        hour24(PRE_URL + "hour24"),
        //未来7天指定日期天气
        areaToWeatherDate(PRE_URL + "area-to-weather-date"),
        //未来15天预报
        day15(PRE_URL + "day15"),
        //地名查询id
        areaToId(PRE_URL + "area-to-id"),
        //历史天气查询
        weatherHistory(PRE_URL + "weatherhistory");

        private final String url;

        API(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

}
