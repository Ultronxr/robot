package cn.ultronxr.reminder;

import cn.ultronxr.reminder.crawler.WaterAndPowerNewsCrawler;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class MainMethodTest {

    public static void main(String[] args) {
        //String str = "<div style=\"text-align: center\"><a href=\"/repos\"><img src=\"/assets/images/ef5169e8ad14e18dda46b95d019149a0-growth.png\" width=\"170\" height=\"120\"><p>test</p></a></div>";
        //log.info(str.replaceAll("<[.[^>]]*>",""));

        //log.info("second timestamp = " + String.valueOf(new Date().getTime() / 1000));
        //log.info("second timestamp = " + String.valueOf(System.currentTimeMillis() / 1000));

        //try {
        //    log.info(WaterAndPowerNewsCrawler.waterReminder().toString());
        //} catch (JsonProcessingException e) {
        //    e.printStackTrace();
        //}
    }

}
