package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.ultronxr.qqrobot.service.MagnetService;
import cn.ultronxr.qqrobot.util.JSoupUtils;
import cn.ultronxr.qqrobot.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Ultronxr
 * @date 2021/04/30 15:54
 */
@Service
@Slf4j
public class MagnetServiceImpl implements MagnetService {

    public static final String SIX_MAG_NET_URL = "https://6mag.net";

    public static final String SIX_MAG_NET_SEARCH_URL = "https://6mag.net/search";


    @Override
    public ArrayList<ArrayList<String>> SIXMAGNETSearch(String searchParam, int length) {
        log.info("[Function] 磁力搜索URL= {} ，搜索内容 = {} ，搜索长度 = {}", SIX_MAG_NET_SEARCH_URL, searchParam, length);

        // 获取 磁力链种子列表 网页内容并解析，获取磁力信息
        Elements elements = JSoupUtils.getAndParse(
                SIX_MAG_NET_SEARCH_URL,
                new HashMap<>(1){{ put("q", searchParam); }},
                "div[class=container]", "table", "tbody", "tr");

        // 搜索无结果，直接返回
        if(1 == elements.size() && elements.text().contains("我们未能找到关于")){
            log.info("[Function] 磁力搜索无结果。");
            return new ArrayList<>(0);
        }

        if(length < 0 || length > elements.size()){
            length = elements.size();
        }
        ArrayList<ArrayList<String>> resArrays = new ArrayList<>(length);

        // 遍历磁力列表
        elements.subList(0, length).parallelStream().forEach(element -> {
            // 标题、文件大小、详情网页链接
            String title = JSoupUtils.parse(element, "td", "a", "p").text(),
                    link = SIX_MAG_NET_URL + JSoupUtils.parse(element, "td", "a").attr("href"),
                    size = JSoupUtils.parse(element, "td[class=td-size]").text();
            ArrayList<String> resArray = new ArrayList<>(8);
            resArray.add(title);
            resArray.add(size);
            resArray.add(link);

            // 对每个种子的详情网页进行访问并解析，获取磁力链具体信息
            Elements details = JSoupUtils.getAndParse(link, null),
                    magnetInfos = JSoupUtils.parse(details, "div[class=row]", "dl", "dd");
            // 磁力链、磁力链发布日期、文件信息、演员
            String magnet = trimMagnet(details.select("input[id=input-magnet]").val()),
                    date = magnetInfos.eq(2).text(),
                    info = StringUtils.cutOffStringOverLength(magnetInfos.eq(3).select("a").text(), 15),
                    actor = StringUtils.cutOffStringOverLength(magnetInfos.eq(4).select("a").text(), 10);
            resArray.add(magnet);
            resArray.add(date);
            resArray.add(info);
            resArray.add(actor);

            resArrays.add(resArray);
        });

        log.info("[Function] 磁力搜索完成。");
        return resArrays;
    }

    /**
     * 修剪磁力链内容，使其长度变短
     *
     * @param magnet 原磁力链
     * @return {@code} 修剪后的磁力链
     */
    private static String trimMagnet(String magnet) {
        int idx = magnet.indexOf("&");
        return magnet.substring(0, idx > 0 ? idx : magnet.length());
    }

}
