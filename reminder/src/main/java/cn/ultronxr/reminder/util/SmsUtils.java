package cn.ultronxr.reminder.util;

import cn.ultronxr.reminder.bean.GlobalData;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


@Slf4j
public class SmsUtils extends GlobalData {

    /**
     * 腾讯云发送SMS短信服务
     *
     * @param smsSign
     *          短信签名，一个字符串，标识发送短信的个人/组织，向腾讯云短信服务申请，且需要审核通过才能使用
     *          如果传入的字符串为null或为空，则读取配置文件中的默认短信签名
     *
     * @param smsTemplateId
     *          短信模板ID，一个数字字符串，从腾讯云短信服务申请分配，且需要审核通过才能使用
     *          如果传入的字符串为null或为空，则直接return一个空的SendSmsResponse对象，停止发送短信
     *
     * @param phoneNumberSet
     *          接收短信的手机号字符串数组
     *          如果传入的数组为null或为空，则读取配置文件中的默认手机号
     *          请注意手机号格式（需要携带加号和区号，再接手机号），例：["+8612345678901", "+8612345678902"]
     *
     * @return
     *          {@code SendSmsResponse}腾讯云短信服务的发送短信通用类，记录这次短信发送过程的各种信息
     *          可以使用{@code SendSmsResponse.toJsonString()}方法输出完整内容
     *          如果传参smsTemplateId为null或为空，将会return一个空的{@code SendSmsResponse}对象
     *
     * @throws TencentCloudSDKException
     *          腾讯云SDK执行异常
     */
    public static SendSmsResponse tencentSms(String smsSign, String smsTemplateId, String[] phoneNumberSet)
            throws TencentCloudSDKException {
        if(StringUtils.isEmpty(smsSign)){
            smsSign = ResBundle.TENCENT_CLOUD.getString("sms.sign");
        }
        if(StringUtils.isEmpty(smsTemplateId)){
            return new SendSmsResponse();
        }
        if(Objects.isNull(phoneNumberSet) || phoneNumberSet.length == 0){
            phoneNumberSet = ResBundle.getStrArray(ResBundle.WATER_POWER_REMINDER, "reminder.phoneNumber", " ");
        }

        Credential tencentApiCredential = new Credential(
                ResBundle.TENCENT_CLOUD.getString("secret.id"),
                ResBundle.TENCENT_CLOUD.getString("secret.key")
        );

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.ap-shanghai.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        SmsClient smsClient = new SmsClient(tencentApiCredential, "", clientProfile);
        SendSmsRequest req = new SendSmsRequest();
        req.setPhoneNumberSet(phoneNumberSet);
        req.setSign(smsSign);
        req.setTemplateID(smsTemplateId);
        req.setSmsSdkAppid(ResBundle.TENCENT_CLOUD.getString("app.id"));

        return smsClient.SendSms(req);
    }

    /**
     * 对tencentSms()方法的Reminder封装
     * 只需要传入一个短信模板ID即可，其他参数省略不填
     *
     * @param smsTemplateId
     *          短信模板ID，一个数字字符串，从腾讯云短信服务申请分配，且需要审核通过才能使用
     *
     * @return
     *          发送短信后返回的{@code SendSmsResponse}对象转成JsonString的结果字符串
     *          如果传入的短信模板为null或为空，return的字符串为“{}”
     *
     * @throws TencentCloudSDKException
     *          腾讯云SDK执行异常
     */
    public static String tencentSmsReminder(String smsTemplateId) throws TencentCloudSDKException {
        return SendSmsResponse.toJsonString(
                tencentSms(
                        ResBundle.TENCENT_CLOUD.getString("sms.sign"),
                        smsTemplateId,
                        ResBundle.WATER_POWER_REMINDER.getString("reminder.phoneNumber").split(" ")
                ));
    }

}
