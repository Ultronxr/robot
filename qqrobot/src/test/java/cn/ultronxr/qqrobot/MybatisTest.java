package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.service.GroupChatStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ultronxr
 * @date 2021/05/05 17:02
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest()
public class MybatisTest {

    @Autowired
    private GroupChatStatisticsService chatStatisticsService;

    @Test
    public void test() {
        chatStatisticsService.statisticsGroup("", "", "");
    }

}
