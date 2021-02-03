package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.bean.BotEntity;
import cn.ultronxr.qqrobot.bean.GlobalData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class QqrobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QqrobotApplication.class, args);
        try {
            BotEntity.BOT_ENTITY.login();
            log.info("[system] QQ机器人登录成功。");
        } catch (Exception ex){
            log.warn("[system] QQ机器人登录失败！");
            ex.printStackTrace();
        }
    }

}
