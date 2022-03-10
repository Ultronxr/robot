package cn.ultronxr.qqrobot.bean.mybatis.mapper;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTask;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskExample;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuartzTaskMapper {
    long countByExample(QuartzTaskExample example);

    int deleteByExample(QuartzTaskExample example);

    int deleteByPrimaryKey(QuartzTaskKey key);

    int insert(QuartzTask record);

    int insertSelective(QuartzTask record);

    List<QuartzTask> selectByExample(QuartzTaskExample example);

    QuartzTask selectByPrimaryKey(QuartzTaskKey key);

    int updateByExampleSelective(@Param("record") QuartzTask record, @Param("example") QuartzTaskExample example);

    int updateByExample(@Param("record") QuartzTask record, @Param("example") QuartzTaskExample example);

    int updateByPrimaryKeySelective(QuartzTask record);

    int updateByPrimaryKey(QuartzTask record);
}