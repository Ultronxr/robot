package cn.ultronxr.qqrobot.util;

import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Ultronxr
 * @date 2022/02/17 15:43
 *
 * 有关二维码解析和生成的工具类库
 */
@Slf4j
public class QRCodeUtils {

    /** 二维码解析参数 */
    private static final Map<DecodeHintType, Object> HINTS_DECODER = new HashMap<>();
    /** 二维码解析参数（TRY_HARDER） */
    private static final Map<DecodeHintType, Object> HINTS_DECODER_TRY_HARDER = new HashMap<>();
    /** 二维码生成参数 */
    public static final Map<EncodeHintType, Object> HINTS_ENCODER = new HashMap<>();

    static {
        HINTS_DECODER.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        HINTS_DECODER_TRY_HARDER.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        HINTS_DECODER_TRY_HARDER.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        HINTS_ENCODER.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        HINTS_ENCODER.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        HINTS_ENCODER.put(EncodeHintType.MARGIN, 1);
    }


    /**
     * 解析路径指定的二维码
     * @param filePath 文件路径
     * @return {@code String} 解析结果
     * @throws IOException 文件处理出错
     * @throws NotFoundException 图像中未发现/无法识别二维码
     */
    public static String decoderByFilePath(String filePath) throws IOException, NotFoundException{
        return decoderByImageFile(new File(filePath));
    }

    /**
     * 解析文件指定的二维码
     * @param file 文件
     * @return {@code String} 解析结果
     * @throws IOException 文件处理出错
     * @throws NotFoundException 图像中未发现/无法识别二维码
     */
    public static String decoderByImageFile(File file) throws IOException, NotFoundException {
        BufferedImage image = ImageIO.read(file);
        return decoderByBufferedImage(image);
    }

    /**
     * 解析二维码图像
     * @param bufferedImage 二维码图像
     * @return {@code String} 解析结果
     * @throws NotFoundException 图像中未发现/无法识别二维码
     */
    public static String decoderByBufferedImage(BufferedImage bufferedImage) throws NotFoundException {
        try {
            // 第一次尝试解析
            return decoderByBufferedImage(bufferedImage, HINTS_DECODER);
        } catch (NotFoundException ex) {
            // 第二次尝试解析
            return decoderByBufferedImage(bufferedImage, HINTS_DECODER_TRY_HARDER);
        }
    }

    /**
     * 实际进行解析二维码的方法
     * @param bufferedImage 二维码图像
     * @param hints 解析二维码的相关参数
     *          {@link cn.ultronxr.qqrobot.util.QRCodeUtils#HINTS_DECODER}
     *          {@link cn.ultronxr.qqrobot.util.QRCodeUtils#HINTS_DECODER_TRY_HARDER}
     * @return {@code String} 解析结果
     * @throws NotFoundException 图像中未发现/无法识别二维码
     */
    public static String decoderByBufferedImage(BufferedImage bufferedImage, Map<DecodeHintType, Object> hints) throws NotFoundException {
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap bitmap = new BinaryBitmap(binarizer);
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }




    /**
     * 生成二维码到输出流
     * @param content 待生成二维码的文本内容
     * @param format   二维码图片文件格式（png/jpg等）
     * @param width    {@code Integer} 二维码图片宽度，单位 像素 px
     * @param height   {@code Integer} 二维码图片高度，单位 像素 px
     * @return {@code ByteArrayOutputStream} 输出流 <br/>
     *         可以使用 {@code ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());} 来转换成输入流。
     * @throws WriterException 写点阵数据异常
     * @throws IOException 输出流异常
     */
    public static ByteArrayOutputStream encoderToStream(String content, String format, Integer width, Integer height) throws WriterException, IOException {
        BitMatrix bitMatrix = encoder(content, width, height, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, format, byteArrayOutputStream);
        return byteArrayOutputStream;
    }

    /**
     * 生成二维码到指定路径文件
     * @param content  待生成二维码的文本内容
     * @param filePath 二维码图片文件路径
     * @param format   二维码图片文件格式（png/jpg等）
     * @param width    {@code Integer} 二维码图片宽度，单位 像素 px
     * @param height   {@code Integer} 二维码图片高度，单位 像素 px
     * @throws WriterException 写点阵数据异常
     * @throws IOException 写文件异常
     */
    public static void encoderToFile(String content, String filePath, String format, Integer width, Integer height) throws WriterException, IOException {
        BitMatrix bitMatrix = encoder(content, width, height, null);
        MatrixToImageWriter.writeToPath(bitMatrix, format, new File(filePath).toPath());
    }

    /**
     * 生成二维码点阵
     * @param content 待生成二维码的文本内容
     * @param width   {@code Integer} 宽度
     * @param height  {@code Integer} 高度
     * @param hints   生成二维码的相关参数，如果传入null，则默认使用 {@link cn.ultronxr.qqrobot.util.QRCodeUtils#HINTS_ENCODER}
     * @return {@code BitMatrix} 生成的二维码点阵
     * @throws WriterException 写点阵数据异常
     */
    private static BitMatrix encoder(String content, Integer width, Integer height, Map<EncodeHintType, Object> hints) throws WriterException {
        if(hints == null || hints.isEmpty()) {
            hints = HINTS_ENCODER;
        }
        return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
    }

}
