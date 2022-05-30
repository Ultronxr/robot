package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.ultronxr.qqrobot.service.EpicGameService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ultronxr
 * @date 2022/05/30 23:22
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        cn.ultronxr.qqrobot.service.EpicGameService.class,
        cn.ultronxr.qqrobot.service.serviceImpl.EpicGameServiceImpl.class,
        cn.ultronxr.qqrobot.util.DateTimeUtils.class,
})
class EpicGameServiceImplTest {

    @Autowired
    private EpicGameService epicGameService;

    @Test
    void freeGamesPromotionsWeekly() {
        String msg = epicGameService.freeGamesPromotionsWeekly();
        System.out.println(msg);
    }

    @Test
    void httpRequest() {
    }

    @Test
    void parseJson() {
    }
}