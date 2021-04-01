package cn.ultronxr.qqrobot.util;

import cn.ultronxr.qqrobot.bean.GlobalData;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;


@Slf4j
public class AliOSSUtils extends GlobalData {

    /** Bucket所在地域对应的Endpoint */
    public static final String ENDPOINT = GlobalData.ResBundle.ALI_CLOUD.getString("ali.oss.endPoint");

    /** OSS用户的AccessKeyId（推荐使用RAM用户） */
    public static final String ACCESS_KEY_ID = GlobalData.ResBundle.ALI_CLOUD.getString("ali.subUser.accessKey.id");

    /** OSS用户的AccessKeyId（推荐使用RAM用户） */
    public static final String ACCESS_KEY_SECRET = GlobalData.ResBundle.ALI_CLOUD.getString("ali.subUser.accessKey.secret");

    /** OSS的Bucket名称 */
    public static final String BUCKET_NAME = GlobalData.ResBundle.ALI_CLOUD.getString("ali.oss.bucketName");

    /** OSS的存取对象路径（不能携带 Bucket名称 和 位于首位的"/"斜杠字符） */
    public static final String FOLDER_KEY = GlobalData.ResBundle.ALI_CLOUD.getString("ali.oss.folderKey");


    /**
     * 向阿里云OSS中存放文件对象
     * {@link #putFileObject(String, String, String)} 方法的重载，使用默认 {@link #FOLDER_KEY}
     *
     * @param sourceFile   本地的原文件路径和文件名
     * @param ossFileKey   存入的OSS对象文件名
     *
     * @return {@code Boolean} 文件对象的存放结果：true-成功，false-失败
     */
    public static Boolean putFileObject(String sourceFile, String ossFileKey){
        return putFileObject(sourceFile, null, ossFileKey);
    }

    /**
     * 向阿里云OSS中存放文件对象
     *
     * sourceFile              = 本地待存入的原文件
     * ossFolderKey+ossFileKey = 存入的OSS对象文件
     *
     * @param sourceFile   本地的原文件路径和文件名
     * @param ossFolderKey 存入的OSS对象路径，如果这个参数留空则会套用默认 {@link #FOLDER_KEY}
     * @param ossFileKey   存入的OSS对象文件名
     *
     * @return {@code Boolean} 文件对象的存放结果：true-成功，false-失败
     */
    public static Boolean putFileObject(String sourceFile, String ossFolderKey, String ossFileKey) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    BUCKET_NAME,
                    (null == ossFolderKey ? FOLDER_KEY : ossFolderKey) + ossFileKey,
                    new File(sourceFile)
            );

            // 设置权限
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            putObjectRequest.setMetadata(metadata);

            ossClient.putObject(putObjectRequest);
        } catch (Exception ex) {
            log.warn("[function] 阿里云OSS存放文件对象抛出异常！");
            ex.printStackTrace();
            ossClient.shutdown();
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;
    }


    /**
     * 从阿里云OSS获取文件对象
     * {@link #getFileObject(String, String, String)} 方法的重载，使用默认 {@link #FOLDER_KEY}
     *
     * @param ossFileKey   待获取的OSS对象文件名
     * @param targetFile   获取到本地的文件路径和文件名
     *
     * @return {@code Boolean} 文件对象的获取结果：true-成功，false-失败
     */
    public static Boolean getFileObject(String ossFileKey, String targetFile){
        return getFileObject(null, ossFileKey, targetFile);
    }

    /**
     * 从阿里云OSS获取文件对象
     *
     * ossFolderKey+ossFileKey = 待获取的OSS对象文件
     * targetFile              = 获取到本地的文件
     *
     * @param ossFolderKey 待获取的OSS对象路径，如果这个参数留空则会套用默认 {@link #FOLDER_KEY}
     * @param ossFileKey   待获取的OSS对象文件名
     * @param targetFile   获取到本地的文件路径和文件名
     *
     * @return {@code Boolean} 文件对象的获取结果：true-成功，false-失败
     */
    public static Boolean getFileObject(String ossFolderKey, String ossFileKey, String targetFile) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            ossClient.getObject(
                    new GetObjectRequest(
                            BUCKET_NAME,
                            (null == ossFolderKey ? FOLDER_KEY : ossFolderKey) + ossFileKey
                    ),
                    new File(targetFile)
            );
            ossClient.shutdown();
        } catch (Exception ex){
            log.warn("[function] 阿里云OSS获取文件对象抛出异常！");
            ex.printStackTrace();
            ossClient.shutdown();
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;
    }

}
