package cn.ultronxr.qqrobot.util;

import cn.hutool.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

import static cn.ultronxr.qqrobot.bean.GlobalData.USER_AGENT;

/**
 * @author Ultronxr
 * @date 2021/04/30 15:51
 *
 * JSoup Java HTML Parser Utils
 */
public class JSoupUtils {

    /**
     * GET请求获取网页返回结果
     *
     * @param url      请求网页URL
     * @param paramMap 请求参数
     * @return {@code String} 网页返回内容
     */
    public static String get(String url, Map<String, Object> paramMap) {
        return HttpRequest.get(url)
                .header("user-agent", USER_AGENT)
                .form(paramMap)
                .execute()
                .body();
    }

    /**
     * 先请求URL获取网页返回内容，再根据传入的cssQuery解析html文本，返回解析之后的所有元素
     *
     * @param url      请求网页URL
     * @param paramMap 请求参数
     * @param cssQuery 查询参数
     * @return {@code Elements} 解析之后的JSoup元素列表
     */
    public static Elements getAndParse(String url, Map<String, Object> paramMap, String... cssQuery) {
        Elements elements = Jsoup.parse(get(url, paramMap)).getAllElements();
        for(String query : cssQuery){
            elements = elements.select(query);
        }
        return elements;
    }

    /**
     * 根据传入的cssQuery解析html文本，返回解析之后的所有元素
     *
     * @param html     原html内容
     * @param cssQuery 查询参数
     * @return {@code Elements} 解析之后的JSoup元素列表
     */
    public static Elements parse(String html, String... cssQuery) {
        Elements elements = Jsoup.parse(html).getAllElements();
        for(String query : cssQuery){
            elements = elements.select(query);
        }
        return elements;
    }

    /**
     * 根据传入的cssQuery解析Elements，返回解析之后的所有元素
     *
     * @param elements 原JSoup元素对象列表
     * @param cssQuery 查询参数
     * @return {@code Elements} 解析之后的JSoup元素列表
     */
    public static Elements parse(Elements elements, String... cssQuery) {
        for(String query : cssQuery){
            elements = elements.select(query);
        }
        return elements;
    }

    /**
     * 根据传入的cssQuery解析Element，返回解析之后的所有元素
     *
     * @param element  原JSoup元素
     * @param cssQuery 查询参数
     * @return {@code Elements} 解析之后的JSoup元素列表
     */
    public static Elements parse(Element element, String... cssQuery) {
        Elements elements = element.getAllElements();
        for(String query : cssQuery){
            elements = elements.select(query);
        }
        return elements;
    }

}
