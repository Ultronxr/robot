package cn.ultronxr.qqrobot.data;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.utils.BotConfiguration;

import java.util.ResourceBundle;


public class GlobalData {

    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("configData");

    public static final String BOT_QQ = RESOURCE_BUNDLE.getString("bot.qq");

    private static final String BOT_PWD = RESOURCE_BUNDLE.getString("bot.pwd");

    public static final String BOT_NICK = RESOURCE_BUNDLE.getString("bot.nick");

    private static final String DEVICE_INFO_FILE_PATH = "src\\main\\resources\\deviceInfo.json";

    public static final Bot BOT = BotFactoryJvm.newBot(
            Long.parseLong(GlobalData.BOT_QQ), GlobalData.BOT_PWD,
            new BotConfiguration(){{fileBasedDeviceInfo(GlobalData.DEVICE_INFO_FILE_PATH);}}
    );


    private static final String ALI_WEATHER_APP_KEY = RESOURCE_BUNDLE.getString("ali.weather.app.key");

    private static final String ALI_WEATHER_APP_SECRET = RESOURCE_BUNDLE.getString("ali.weather.app.secret");

    private static final String ALI_WEATHER_APP_CODE = RESOURCE_BUNDLE.getString("ali.weather.app.code");

    private static final String ALI_WEATHER_API_PRE_URL = "http://ali-weather.showapi.com/";

    public enum ALI_WEATHER_API {
        //地名查询天气
        areaToWeather(ALI_WEATHER_API_PRE_URL + "area-to-weather"),
        //经纬度查询天气
        gpsToWeather(ALI_WEATHER_API_PRE_URL + "gps-to-weather"),
        //景点名称查询天气
        spotToWeather(ALI_WEATHER_API_PRE_URL + "spot-to-weather"),
        //区号邮编查询天气
        phonePostCodeToWeather(ALI_WEATHER_API_PRE_URL + "phone-post-code-weeather"),
        //IP查询天气
        ipToWeather(ALI_WEATHER_API_PRE_URL + "ip-to-weather"),
        //查询24小时预报
        hour24(ALI_WEATHER_API_PRE_URL + "hour24"),
        //未来7天指定日期天气
        areaToWeatherDate(ALI_WEATHER_API_PRE_URL + "area-to-weather-date"),
        //未来15天预报
        day15(ALI_WEATHER_API_PRE_URL + "day15"),
        //地名查询id
        areaToId(ALI_WEATHER_API_PRE_URL + "area-to-id"),
        //历史天气查询
        weatherHistory(ALI_WEATHER_API_PRE_URL + "weatherhistory"),
        //请求接口的身份验证信息（添加在请求头，以“Authorization”为键）
        authCode("APPCODE " + ALI_WEATHER_APP_CODE)
        ;

        private final String url;

        ALI_WEATHER_API(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }


    public static final String IP_REGEX = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    public static final String DOMAIN_REGEX = "^[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";

    public static final String URL_REGEX = "^https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";


}
