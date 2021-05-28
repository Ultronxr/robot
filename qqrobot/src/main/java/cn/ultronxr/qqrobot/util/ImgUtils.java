package cn.ultronxr.qqrobot.util;

import cn.hutool.core.img.FontUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static cn.hutool.core.img.ImgUtil.getRectangle;


/**
 * @author Ultronxr
 * @date 2021/04/29 13:13
 *
 * 图像处理 公用方法类库
 */
@Slf4j
public class ImgUtils {

    public static Font FONT_DEFAULT = new Font(Font.SANS_SERIF, Font.BOLD, 45);

    public static Font FONT_MICROSOFT_YAHEI = FontUtil.createFont("Microsoft YaHei", 45);

    static {
        InputStream inputStream = ImgUtils.class.getResourceAsStream("/fonts/Microsoft YaHei.ttc");
        try {
            FONT_MICROSOFT_YAHEI = FontUtil.createFont(inputStream).deriveFont(Font.BOLD, 60);
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.warn("[function] 创建Microsoft YaHei字体失败！");
        }
    }


    /**
     * 根据文字创建图片<br/>
     * 从hutool类库中复制过来，修改了以下内容：<br/>
     *   图片的四个角是圆角、添加文字抗锯齿。<br/>
     * {@link cn.hutool.core.img.ImgUtil#createImage(String, Font, Color, Color, int)}
     *
     * @param str             文字
     * @param font            字体{@link Font}
     * @param backgroundColor 背景颜色，默认透明
     * @param fontColor       字体颜色，默认黑色
     * @param imageType       图片类型，见：{@link BufferedImage}
     * @param arcRoundRect    四个角的圆角弧度，见：{@link Graphics#fillRoundRect}
     *                        带有透明度属性的图片类型（如TYPE_INT_ARGB）才能让整个画布的四个角变成圆角；
     *                        否则整个画布还是直角矩形的，圆角的后面依旧会显现出黑色背景。
     * @return 图片
     */
    public static BufferedImage createImageRoundRect(String str, Font font, Color backgroundColor, Color fontColor, int imageType, int arcRoundRect) {
        // 获取font的样式应用在str上的整个矩形
        final Rectangle2D r = getRectangle(str, font);
        // 获取单个字符的高度
        int unitHeight = (int) Math.floor(r.getHeight());
        // 获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
        int width = (int) Math.round(r.getWidth()) + 1;
        // 把单个字符的高度+3保证高度绝对能容纳字符串作为图片的高度
        int height = unitHeight + 3;
        // 四个角的圆角弧度
        arcRoundRect = (arcRoundRect < 0 ? 10 : arcRoundRect);

        // 创建图片
        final BufferedImage image = new BufferedImage(width, height, imageType);
        final Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        if (null != backgroundColor) {
            // 先用背景色填充整张图片,也就是背景
            g.setColor(backgroundColor);
            g.fillRoundRect(0, 0, width, height, arcRoundRect, arcRoundRect);
        }
        g.setColor(ObjectUtil.defaultIfNull(fontColor, Color.BLACK));
        g.setFont(font);// 设置画笔字体
        g.drawString(str, 0, font.getSize());// 画出字符串
        g.dispose();

        return image;
    }


}
