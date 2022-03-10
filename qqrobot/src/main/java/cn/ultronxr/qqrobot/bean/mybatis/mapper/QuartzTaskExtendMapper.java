package cn.ultronxr.qqrobot.bean.mybatis.mapper;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskExtend;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskExtendExample;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QuartzTaskExtendKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuartzTaskExtendMapper {
    long countByExample(QuartzTaskExtendExample example);

    int deleteByExample(QuartzTaskExtendExample example);

    int deleteByPrimaryKey(QuartzTaskExtendKey key);

    int insert(QuartzTaskExtend record);

    int insertSelective(QuartzTaskExtend record);

    List<QuartzTaskExtend> selectByExample(QuartzTaskExtendExample example);

    QuartzTaskExtend selectByPrimaryKey(QuartzTaskExtendKey key);

    int updateByExampleSelective(@Param("record") QuartzTaskExtend record, @Param("example") QuartzTaskExtendExample example);

    int updateByExample(@Param("record") QuartzTaskExtend record, @Param("example") QuartzTaskExtendExample example);

    int updateByPrimaryKeySelective(QuartzTaskExtend record);

    int updateByPrimaryKey(QuartzTaskExtend record);
}