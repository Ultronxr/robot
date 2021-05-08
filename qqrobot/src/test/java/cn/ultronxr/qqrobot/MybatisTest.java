package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.bean.mybatis.bean.ChatSum;
import cn.ultronxr.qqrobot.bean.mybatis.bean.GroupIdAndChatSum;
import cn.ultronxr.qqrobot.bean.mybatis.bean.MemberIdAndChatSum;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroup;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QQGroupMapper;
import cn.ultronxr.qqrobot.service.GroupChatStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/05 17:02
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {

    @Autowired
    private QQGroupMapper qqGroupMapper;

    @Autowired
    private GroupChatStatisticsService statisticsService;

    @Test
    public void test() {
        //qqGroupMapper.insert(new QQGroup("test", 1));
        //List<QQGroup> list = qqGroupMapper.selectByExample(null);
        //log.info("{}", list);
    }

    @Test
    public void serviceTest(){
        String startTime = "2021-05-06 00", endTime = "2021-05-06 24";
        String groupId1 = "111", groupId2 = "222";
        String memberId1 = "111111", memberId2 = "222222", memberId3 = "333333";

        // 某段时间内 <b>某个群<b/> 的活跃程度
        List<ChatSum> s1 = statisticsService.statisticsGroup(groupId1, startTime, endTime);
        List<ChatSum> s2 = statisticsService.statisticsGroup(groupId2, startTime, endTime);

        // 某段时间内 <b>所有群<b/> 的活跃程度/排名
        List<GroupIdAndChatSum> s3 = statisticsService.statisticsAllGroup(startTime, endTime);//

        // 某段时间内 某个群的<b>某位群成员<b/> 的活跃程度
        List<ChatSum> s4 = statisticsService.statisticsGroupMember(groupId1, memberId1, startTime, endTime);
        List<ChatSum> s5 = statisticsService.statisticsGroupMember(groupId1, memberId2, startTime, endTime);
        List<ChatSum> s6 = statisticsService.statisticsGroupMember(groupId1, memberId3, startTime, endTime);

        // 某段时间内 某个群的<b>所有群成员<b/> 的活跃程度/排名
        List<MemberIdAndChatSum> s7 = statisticsService.statisticsGroupAllMember(groupId1, startTime, endTime);//
        List<MemberIdAndChatSum> s8 = statisticsService.statisticsGroupAllMember(groupId2, startTime, endTime);//

        log.info("s1 = {}", s1);
        log.info("s2 = {}", s2);
        log.info("s3 = {}", s3);
        log.info("s4 = {}", s4);
        log.info("s5 = {}", s5);
        log.info("s6 = {}", s6);
        log.info("s7 = {}", s7);
        log.info("s8 = {}", s8);
    }

}
