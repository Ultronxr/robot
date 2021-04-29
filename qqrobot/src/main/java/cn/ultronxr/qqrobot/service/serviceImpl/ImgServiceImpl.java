package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.hutool.core.img.ImgUtil;
import cn.ultronxr.qqrobot.service.ImgService;
import cn.ultronxr.qqrobot.util.ImgUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author Ultronxr
 * @date 2021/04/29 12:04
 */
@Service
@Slf4j
public class ImgServiceImpl implements ImgService {

    /** PornHub图标 相关属性 */
    public static class PORNHUB_ICON {
        /** 字体、加粗、字号 */
        private static final Font FONT = ImgUtils.FONT_MICROSOFT_YAHEI;

        /** 前缀文字颜色 */
        private static final Color PREFIX_TEXT_COLOR = Color.WHITE;
        /** 后缀文字颜色 */
        private static final Color SUFFIX_TEXT_COLOR = Color.BLACK;

        /** 前缀背景颜色 */
        private static final Color BACKGROUND = Color.BLACK;
        /** 后缀背景颜色 */
        private static final Color SUFFIX_BACKGROUND = new Color(255, 153, 0);

        /** 圆角弧度 */
        private static final int ARC_ROUND_RECT = 20;
    }


    @Override
    public BufferedImage createPornHubIconImg(@NotNull String prefixText, @NotNull String suffixText) {
        BufferedImage
            prefixImg =
                ImgUtils.createImageRoundRect(prefixText, PORNHUB_ICON.FONT, PORNHUB_ICON.BACKGROUND,
                        PORNHUB_ICON.PREFIX_TEXT_COLOR, BufferedImage.TYPE_INT_ARGB, PORNHUB_ICON.ARC_ROUND_RECT),
            suffixImg =
                    ImgUtils.createImageRoundRect(suffixText, PORNHUB_ICON.FONT, PORNHUB_ICON.SUFFIX_BACKGROUND,
                            PORNHUB_ICON.SUFFIX_TEXT_COLOR, BufferedImage.TYPE_INT_ARGB, PORNHUB_ICON.ARC_ROUND_RECT);

        final int fullWidth = prefixImg.getWidth() + suffixImg.getWidth() + 20;
        final int fullHeight = Math.max(prefixImg.getHeight(), suffixImg.getHeight()) + 10;

        BufferedImage fullImg = new BufferedImage(fullWidth, fullHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D fullGraphics = fullImg.createGraphics();
        fullGraphics.setColor(PORNHUB_ICON.BACKGROUND);
        fullGraphics.fillRect(0, 0, fullWidth, fullHeight);
        fullGraphics.drawImage(prefixImg, 5, 5, null);
        fullGraphics.drawImage(suffixImg, prefixImg.getWidth()+15, 5, null);
        fullGraphics.dispose();
        prefixImg = suffixImg = null;

        log.info("[function] 创建PornHub图标样式图片：prefix suffix - {} {}", prefixText, suffixText);
        return fullImg;
    }

    @Override
    public InputStream createPornHubIconImgInputStream(@NotNull String prefixText, @NotNull String suffixText) {
        return ImgUtil.toStream(createPornHubIconImg(prefixText, suffixText), "png");
    }
}
