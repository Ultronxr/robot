package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.data.GlobalData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class QqrobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QqrobotApplication.class, args);
        GlobalData.BOT.login();
    }

}
