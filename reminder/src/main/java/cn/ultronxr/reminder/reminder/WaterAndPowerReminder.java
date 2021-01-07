package cn.ultronxr.reminder.reminder;

import cn.ultronxr.reminder.bean.GlobalData;
import cn.ultronxr.reminder.bean.RemindCode;
import cn.ultronxr.reminder.crawler.WaterAndPowerNewsCrawler;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableScheduling
@Slf4j
public class WaterAndPowerReminder {

    /**
     * 上午七点、下午七点各执行一次
     */
    @Scheduled(cron = "0 0 7,19 * * ? ")
    //@Scheduled(cron = "0/10 * * * * ? ")
    public void scheduledReminder(){
        reminderHandler();
    }

    //public static void main(String[] args) {
    //    new WaterAndPowerReminder().reminderHandler();
    //}

    public void reminderHandler(){
        try {
            Map<String, String> waterResMap = WaterAndPowerNewsCrawler.waterReminder(),
                    powerResMap = WaterAndPowerNewsCrawler.powerReminder();

            log.info(waterResMap.toString());
            log.info(powerResMap.toString());

            if(waterResMap.get("remindCode").equals(RemindCode.DoRemind.getStrCode())){
                log.info("waterRemind发起提醒！");
                log.info(waterResMap.get("url"));
            }
            if(powerResMap.get("remindCode").equals(RemindCode.DoRemind.getStrCode())){
                log.info("powerRemind发起提醒！");
                log.info(powerResMap.get("url"));
            }

        } catch (JsonProcessingException ex){
            log.warn("JsonProcessingException");
            ex.printStackTrace();
        }
    }

}
