package cn.ultronxr.qqrobot.controller;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTask;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskKey;
import cn.ultronxr.qqrobot.framework.quartz.QuartzService;
import cn.ultronxr.qqrobot.util.DateTimeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.pagehelper.PageHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/06/01 22:19
 */
@Slf4j
@Controller
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    private final ObjectMapper jsonObjMapper = new ObjectMapper();

    {
        //关闭时间戳功能，防止日期对象转json字符串时变为时间戳
        jsonObjMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        jsonObjMapper.setDateFormat(DateTimeUtils.STANDARD_FORMAT);
    }


    @RequestMapping("add")
    public String add(@RequestBody QuartzTask quartzTask) {
        return null;
    }

    @RequestMapping("delete")
    public String delete(@RequestBody QuartzTaskKey quartzTaskKey) {
        return null;
    }

    @RequestMapping("update")
    public String update(@RequestBody QuartzTask quartzTask) {
        return null;
    }

    @Data
    static class QuartzTaskQueryBody {
        private QuartzTask task;
        private Integer limit;
        private Integer offset;
        private String order;
        private String sort;
        private String filter;

        public QuartzTaskQueryBody(Integer limit, Integer offset, String order, String sort, String filter) {
            this.limit = limit;
            this.offset = offset;
            this.order = order;
            this.sort = sort;
            this.filter = filter;
            if(filter != null) {
                try {
                    this.task = new ObjectMapper().readValue(filter, QuartzTask.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("list")
    @ResponseBody
    public String query(@RequestBody QuartzTaskQueryBody taskBody) {
        int pageNum = 1, pageSize = 10;
        if(taskBody.getLimit() == null) {
            pageSize = 0;
        } else {
            pageNum = taskBody.getOffset()/taskBody.getLimit()+1;
            pageSize = taskBody.getLimit();
        }

        PageHelper.startPage(pageNum, pageSize);
        List<QuartzTask> list = quartzService.queryTask(taskBody.getTask());

        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("rows", list);
        // 分页的条目数量，只管这一页有几条记录
        responseMap.put("totalNotFiltered", list.size());
        // 筛选的条目数量，带有筛选条件的数据库总count记录数量
        responseMap.put("total", quartzService.countTask(taskBody.getTask()));
        try {
            return jsonObjMapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
