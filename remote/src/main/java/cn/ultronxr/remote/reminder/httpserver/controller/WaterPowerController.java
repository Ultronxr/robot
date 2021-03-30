package cn.ultronxr.remote.reminder.httpserver.controller;

import cn.ultronxr.remote.bean.GlobalData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class WaterPowerController extends GlobalData {

    static {
        WaterPowerNewsUrl.WATER_NEWS_URL = "http://www.lxnews.cn/8274764.html";
        WaterPowerNewsUrl.WATER_NEWS_MOBILE_URL = "http://兰溪新闻.mp.weixin.qq.com/s?__biz=MzA4NDEyMzMyMg==&mid=2651496720&idx=3&sn=54ecfce77dc1fd121920bd92e8c4c327&chksm=84120ac5b36583d326a03502c6a0cf67560e50a4af2de8b1fc562d4923c31586e7e7a5408733&scene=0&xtrack=1#rd";

        WaterPowerNewsUrl.POWER_NEWS_URL = "http://www.lxnews.cn/8210891.html";
        WaterPowerNewsUrl.POWER_NEWS_MOBILE_URL = "http://兰溪发布.mp.weixin.qq.com/s?__biz=MzA5Nzc3NzMxNw==&mid=2651285261&idx=4&sn=5c03ba2756b2cd2aa2396a4bab6c4752&chksm=8b6864c7bc1fedd1aa47ecfc67661a28bf829075af56ed82bc5bb539001ec5de0b5f5f9b79d6&scene=0&xtrack=1#rd";
    }

    @RequestMapping("/water")
    public String waterController(){
        log.info("访问WaterPowerNewsUrl.WATER_NEWS_URL页面：" + WaterPowerNewsUrl.WATER_NEWS_URL);
        return "redirect:" + WaterPowerNewsUrl.WATER_NEWS_URL;
    }

    @RequestMapping("/power")
    public String powerController(){
        log.info("访问WaterPowerNewsUrl.POWER_NEWS_URL页面：" + WaterPowerNewsUrl.POWER_NEWS_URL);
        return "redirect:" + WaterPowerNewsUrl.POWER_NEWS_URL;
    }

}
