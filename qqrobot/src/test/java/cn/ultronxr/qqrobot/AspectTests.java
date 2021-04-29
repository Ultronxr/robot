package cn.ultronxr.qqrobot;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Ultronxr
 * @date 2021/04/24 23:55
 *
 * AOP 测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        cn.ultronxr.qqrobot.aspect.LogTrackAspect.class,
        //cn.ultronxr.qqrobot.service.TestService.class,
        //cn.ultronxr.qqrobot.service.serviceImpl.TestServiceImpl.class,
})
@EnableAspectJAutoProxy()
public class AspectTests {

    @Autowired
    //private TestService testService;


    @Test
    public void test() {
        //testService.doTestSth(1, "strstr");
    }

}
