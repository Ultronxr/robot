package cn.ultronxr.qqrobot;

import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;


@Slf4j
public class MainMethodTest {

    public static void main(String[] args) throws IOException {

        //Path path = ResourceUtils.getFile("classpath:deviceInfo.json").toPath();
        //System.out.println(path.toString());
        //
        //System.out.println(System.getProperty("user.dir"));

        //System.out.println(new ClassPathResource("deviceInfo.json").getURL().toString());
        //System.out.println(new ClassPathResource("deviceInfo.json").getURI().toString());

        //String regex = "^(\\[mirai:.*])";
        //System.out.println("[mirai:at:123][mirai:at:123] ZZZ".replaceAll(regex, ""));

        String remindRegex = "^定时<(\\d{1,2}:\\d{1,2}(:\\d{1,2})?)|([\u4E00-\u9FFF\\d]+)>(<重复>)?\\[(提醒)]\\(([\\d,]{5,})*\\)\\{(.*)}$?",
                execRegex = "^定时<(\\d{1,2}:\\d{1,2}(:\\d{1,2})?)|([\u4E00-\u9FFF\\d]+)>(<重复>)?\\[(执行)]\\(([\\d])\\)\\{(.*)}$?";
        String remindTarget = "定时<周一上午9点><重复>[提醒](1341896100,1208482374){看书}",
                execTarget = "定时<周一上午9点><重复>[执行](1){baidu.com}";

        System.out.println(ReUtil.getAllGroups(Pattern.compile(remindRegex), remindTarget));
        System.out.println(ReUtil.getAllGroups(Pattern.compile(execRegex), execTarget));
    }

}
