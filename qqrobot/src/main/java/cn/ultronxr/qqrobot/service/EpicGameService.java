package cn.ultronxr.qqrobot.service;

/**
 * @author Ultronxr
 * @date 2022/05/13 11:52
 *
 * 获取 Epic 商城游戏信息 Service
 */
public interface EpicGameService {

    /**
     * 获取每周免费游戏信息（原付费，每周限时免费领取）
     *
     * @return
     */
    String freeGamesPromotionsWeekly();

    /**
     * 请求免费游戏接口
     *
     * @return
     */
    String httpRequest();

    /**
     * 解析免费游戏内容json
     *
     * @param response 请求接口获得的json
     * @return
     */
    String parseJson(String response);

}
