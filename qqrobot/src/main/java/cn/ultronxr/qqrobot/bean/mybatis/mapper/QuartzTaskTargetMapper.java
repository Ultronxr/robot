package cn.ultronxr.qqrobot.bean.mybatis.mapper;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskTarget;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskTargetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuartzTaskTargetMapper {
    long countByExample(QuartzTaskTargetExample example);

    int deleteByExample(QuartzTaskTargetExample example);

    int insert(QuartzTaskTarget record);

    int insertSelective(QuartzTaskTarget record);

    List<QuartzTaskTarget> selectByExample(QuartzTaskTargetExample example);

    int updateByExampleSelective(@Param("record") QuartzTaskTarget record, @Param("example") QuartzTaskTargetExample example);

    int updateByExample(@Param("record") QuartzTaskTarget record, @Param("example") QuartzTaskTargetExample example);
}