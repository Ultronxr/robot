package cn.ultronxr.qqrobot.service;

import java.util.ArrayList;

/**
 * @author Ultronxr
 * @date 2021/04/30 15:54
 *
 * 磁力链/种子搜索Service
 */
public interface MagnetService {

    /**
     * 使用 ØMagnet无极磁链搜索 进行磁力搜索
     * {@link "https://6mag.net"}
     *
     * @param searchParam 搜索内容
     * @param length      限制返回结果列表长度
     * @return {@code ArrayList<ArrayList<String>>}
     *          外面一层ArrayList是所有的磁力搜索结果列表，包含每一个磁力信息
     *          里面一层ArrayList是一个磁力信息，包含：标题、文件大小、详情网页链接、磁力链、磁力链发布日期、文件信息、演员
     *        注：如果搜索结果为空，那么将返回一个长度为零的ArrayList（非null）
     */
    ArrayList<ArrayList<String>> SIXMAGNETSearch(String searchParam, int length);

}
