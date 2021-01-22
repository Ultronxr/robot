package cn.ultronxr.reminder.reminder;

import cn.ultronxr.reminder.bean.GlobalData;
import cn.ultronxr.reminder.bean.RemindCode;
import cn.ultronxr.reminder.crawler.WaterPowerNewsCrawler;
import cn.ultronxr.reminder.util.SmsUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableScheduling
@Slf4j
public class WaterPowerReminder extends GlobalData {

    /**
     * 上午七点、下午七点各执行一次
     */
    @Scheduled(cron = "0 0 7,19 * * ? ")
    //@Scheduled(cron = "0/10 * * * * ? ")
    public void scheduledReminder(){
        reminderHandler();
    }

    public static void main(String[] args) {
        new WaterPowerReminder().reminderHandler();
    }

    public void reminderHandler(){
        try {
            Map<String, String> waterResMap = WaterPowerNewsCrawler.waterReminder(),
                    powerResMap = WaterPowerNewsCrawler.powerReminder();

            log.info(waterResMap.toString());
            log.info(powerResMap.toString());

            if(waterResMap.get("remindCode").equals(RemindCode.DoRemind.getStrCode())){
                log.info("waterRemind发起提醒！");
                log.info(waterResMap.get("url"));
                WaterPowerNewsUrl.WATER_NEWS_URL = waterResMap.get("url");
                SmsUtils.tencentSmsReminder(ResBundle.TENCENT_CLOUD.getString("sms.template.id.waterReminder"));
            }
            if(powerResMap.get("remindCode").equals(RemindCode.DoRemind.getStrCode())){
                log.info("powerRemind发起提醒！");
                log.info(powerResMap.get("url"));
                WaterPowerNewsUrl.POWER_NEWS_URL = waterResMap.get("url");
                SmsUtils.tencentSmsReminder(ResBundle.TENCENT_CLOUD.getString("sms.template.id.powerReminder"));
            }

        } catch (JsonProcessingException ex){
            log.error("JsonProcessingException");
            ex.printStackTrace();
        } catch (TencentCloudSDKException ex){
            log.error("TencentCloudSDKException");
            ex.printStackTrace();
        }
    }

}
