package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.bean.BotEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class QqrobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QqrobotApplication.class, args);
        BotEntity.BOT_ENTITY.login();
        BotEntity.BOT_ENTITY.join();
    }

}
