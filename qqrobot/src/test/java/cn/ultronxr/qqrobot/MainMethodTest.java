package cn.ultronxr.qqrobot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;


@Slf4j
public class MainMethodTest {

    public static void main(String[] args) throws IOException {

        //Path path = ResourceUtils.getFile("classpath:deviceInfo.json").toPath();
        //System.out.println(path.toString());
        //
        //System.out.println(System.getProperty("user.dir"));

        System.out.println(new ClassPathResource("deviceInfo.json").getURL().toString());
        System.out.println(new ClassPathResource("deviceInfo.json").getURI().toString());
    }

}
