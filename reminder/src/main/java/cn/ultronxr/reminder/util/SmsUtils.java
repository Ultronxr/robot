package cn.ultronxr.reminder.util;

import cn.ultronxr.reminder.bean.GlobalData;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SmsUtils extends GlobalData {

    private static final Credential TENCENT_API_CREDENTIAL =
            new Credential(ResourceBundles.TENCENT_CLOUD.getString("secret.id"),
                           ResourceBundles.TENCENT_CLOUD.getString("secret.key"));

    private static final String[] PHONE_NUMBER_SET = ResourceBundles.WATER_POWER_REMINDER.getString("reminder.phoneNumber").split(" ");


    public static SendSmsResponse tencentSendSms() throws TencentCloudSDKException {
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.ap-shanghai.tencentcloudapi.com");


        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        SmsClient smsClient = new SmsClient(TENCENT_API_CREDENTIAL, "", clientProfile);
        SendSmsRequest req = new SendSmsRequest();
        req.setPhoneNumberSet(PHONE_NUMBER_SET);
        req.setSign("Ultronxr");
        req.setTemplateID("832470");
        req.setSmsSdkAppid(ResourceBundles.TENCENT_CLOUD.getString("app.id"));

        return smsClient.SendSms(req);
    }

    public static void main(String[] args) {
        try {
            System.out.println(SendSmsResponse.toJsonString(tencentSendSms()));
        } catch (TencentCloudSDKException ex){
            ex.printStackTrace();
        }
    }
}
