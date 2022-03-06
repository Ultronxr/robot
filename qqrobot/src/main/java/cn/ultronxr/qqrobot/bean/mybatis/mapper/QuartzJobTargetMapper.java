package cn.ultronxr.qqrobot.bean.mybatis.mapper;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzJobTarget;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzJobTargetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuartzJobTargetMapper {
    long countByExample(QuartzJobTargetExample example);

    int deleteByExample(QuartzJobTargetExample example);

    int insert(QuartzJobTarget record);

    int insertSelective(QuartzJobTarget record);

    List<QuartzJobTarget> selectByExample(QuartzJobTargetExample example);

    int updateByExampleSelective(@Param("record") QuartzJobTarget record, @Param("example") QuartzJobTargetExample example);

    int updateByExample(@Param("record") QuartzJobTarget record, @Param("example") QuartzJobTargetExample example);
}