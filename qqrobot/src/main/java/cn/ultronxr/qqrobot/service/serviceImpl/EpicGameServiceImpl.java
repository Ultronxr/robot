package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.hutool.Hutool;
import cn.hutool.http.HttpRequest;
import cn.ultronxr.qqrobot.service.EpicGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2022/05/13 11:53
 */
@Slf4j
@Service
public class EpicGameServiceImpl implements EpicGameService {

    /** 获取免费游戏信息的请求接口URL */
    public static final String FREE_GAMES_PROMOTIONS_URL = "https://store-site-backend-static.ak.epicgames.com/freeGamesPromotions?locale=zh-CN&country=CN&allowCountries=CN";


    @Override
    public String freeGamesPromotionsWeekly() {
        HttpRequest.get(FREE_GAMES_PROMOTIONS_URL);
        return null;
    }

    @Override
    public String freeGamesPromotions() {
        return null;
    }

}
