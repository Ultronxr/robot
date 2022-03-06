package cn.ultronxr.qqrobot.bean.mybatis.mapper;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzJob;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzJobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuartzJobMapper {
    long countByExample(QuartzJobExample example);

    int deleteByExample(QuartzJobExample example);

    int deleteByPrimaryKey(Integer jobId);

    int insert(QuartzJob record);

    int insertSelective(QuartzJob record);

    List<QuartzJob> selectByExample(QuartzJobExample example);

    QuartzJob selectByPrimaryKey(Integer jobId);

    int updateByExampleSelective(@Param("record") QuartzJob record, @Param("example") QuartzJobExample example);

    int updateByExample(@Param("record") QuartzJob record, @Param("example") QuartzJobExample example);

    int updateByPrimaryKeySelective(QuartzJob record);

    int updateByPrimaryKey(QuartzJob record);
}