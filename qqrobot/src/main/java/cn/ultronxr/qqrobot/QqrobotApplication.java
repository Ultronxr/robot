package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.bean.BotEntity;
import cn.ultronxr.qqrobot.bean.GlobalData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class QqrobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QqrobotApplication.class, args);
        BotEntity.BOT_ENTITY.login();
    }

}
