package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.service.EpicGameService;
import cn.ultronxr.qqrobot.util.DateTimeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper jsonObjMapper = new ObjectMapper();


    @Override
    public String freeGamesPromotionsWeekly() {
        return parseJson(httpRequest());
    }

    @Override
    public String httpRequest() {
        return HttpRequest
                .get(FREE_GAMES_PROMOTIONS_URL)
                .header("User-Agent", GlobalData.USER_AGENT)
                .header("content-type", "application/json; charset=utf-8")
                .execute()
                .body();
    }

    @Override
    public String parseJson(String response) {
        final StringBuilder sb = new StringBuilder("Epic游戏商城-白嫖游戏\n");
        try {
            JsonNode elementsNode = jsonObjMapper.readTree(response)
                    .path("data").path("Catalog").path("searchStore").path("elements");

            // 遍历每一个游戏，筛选符合条件的结果
            elementsNode.elements().forEachRemaining(oneGameElementNode -> {
                String promotionCalendarPeriod = checkPromotionCalendarPeriod(oneGameElementNode),
                        offerTypeAndOriginalPrice = checkPrice(oneGameElementNode);
                if(null != promotionCalendarPeriod && null != offerTypeAndOriginalPrice) {
                    buildMsg(sb, oneGameElementNode, promotionCalendarPeriod, offerTypeAndOriginalPrice);
                }
            });
        } catch (Exception ex) {
            //sb.delete(0, sb.length());
            sb.append("Epic游戏商城-白嫖游戏：json数据解析失败！");
            log.warn(sb.toString());
            ex.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 检查当前时间是否在优惠时间段内（当前时间该优惠是否有效）
     *
     * @param oneGameElementNode 遍历中的一个游戏json内容节点
     * @return 是（在优惠时间段内） - 返回字符串：优惠起始时间 - 优惠结束时间<br/>
     *         否（不在优惠时间段内） - 返回：null
     */
    private String checkPromotionCalendarPeriod(JsonNode oneGameElementNode) {
        // 获取优惠时间
        JsonNode promotionalOffers = oneGameElementNode.path("promotions").path("promotionalOffers"),
                upcomingPromotionalOffers = oneGameElementNode.path("promotions").path("upcomingPromotionalOffers");
        if(!promotionalOffers.path(0).isEmpty()) {
            // 获取的时间是UTC格式，需要转换为北京时间
            String startDate = DateTimeUtils.utcToUtc8WithStandardFormat(promotionalOffers.path(0).path("promotionalOffers").path(0).path("startDate").asText()),
                    endDate = DateTimeUtils.utcToUtc8WithStandardFormat(promotionalOffers.path(0).path("promotionalOffers").path(0).path("endDate").asText());

            // 检查当前时间是否在优惠时间段内（当前时间该优惠是否有效）
            if(DateTimeUtils.isNowInCalendarPeriod(
                    DateTimeUtils.parseStringToCalendar(startDate, null),
                    DateTimeUtils.parseStringToCalendar(endDate, null)
            )) {
                return startDate + " - " + endDate;
            }
        }
        return null;
    }

    /**
     * 检查游戏价格是否符合条件：原价不为0，且折扣价为0
     *
     * @param oneGameElementNode 遍历中的一个游戏json内容节点
     * @return 是（符合条件） - 返回字符串：优惠类别 - 游戏原价<br/>
     *         否（不符合条件） - 返回：null
     */
    private String checkPrice(JsonNode oneGameElementNode) {
        JsonNode fmtPrice = oneGameElementNode.path("price").path("totalPrice").path("fmtPrice");
        String offerType = oneGameElementNode.path("offerType").asText();
        String discountPrice = fmtPrice.path("discountPrice").asText(),
                originalPrice = fmtPrice.path("originalPrice").asText();

        // 白嫖游戏：
        // 1. offerType = BASE_GAME；打折价格 = 0 ≠ 原价
        // 2. offerType = OTHERS/BUNDLE/...；打折价格 = 0 = 原价
        if("BASE_GAME".equals(offerType)) {
            if("0".equals(discountPrice) && !originalPrice.equals(discountPrice)) {
                return offerType + " - " + originalPrice;
            }
        } else if("0".equals(discountPrice) && "0".equals(originalPrice)) {
            return offerType + " - " + originalPrice;
        }
        return null;
    }

    /**
     * 组装白嫖游戏消息
     *
     * @param sb                 组装消息内容的StringBuilder对象
     * @param oneGameElementNode 遍历中的一个游戏json内容节点
     * @param promotionCalendarPeriod 游戏优惠时期
     * @param offerTypeAndPrice       优惠类别和原价
     * @return
     */
    private StringBuilder buildMsg(StringBuilder sb, JsonNode oneGameElementNode, String promotionCalendarPeriod, String offerTypeAndPrice) {
        log.info("Epic游戏商城-白嫖游戏：{}，原价：{}", oneGameElementNode.path("title").asText(), offerTypeAndPrice);
        sb.append("○游戏名：").append(oneGameElementNode.path("title").asText()).append("\n")
                .append("○优惠类别-原价：").append(offerTypeAndPrice).append("\n")
                .append("○优惠起止时间：").append(promotionCalendarPeriod).append("\n")
                .append("○简介：").append(oneGameElementNode.path("description").asText()).append("\n");

        // 找到合适的封面URL
        oneGameElementNode.path("keyImages").elements().forEachRemaining(e -> {
            if(e.path("type").asText().equals("OfferImageWide")) {
                log.info("封面：{}", e.path("url").asText());
                sb.append("○封面：").append(e.path("url").asText()).append("\n");
            }
        });
        sb.append("\n");
        return sb;
    }

}
