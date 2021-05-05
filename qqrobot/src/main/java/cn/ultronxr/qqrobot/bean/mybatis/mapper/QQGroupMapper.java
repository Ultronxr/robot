package cn.ultronxr.qqrobot.bean.mybatis.mapper;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroup;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QQGroupMapper {
    long countByExample(QQGroupExample example);

    int deleteByExample(QQGroupExample example);

    int deleteByPrimaryKey(String groupId);

    int insert(QQGroup record);

    int insertSelective(QQGroup record);

    List<QQGroup> selectByExample(QQGroupExample example);

    QQGroup selectByPrimaryKey(String groupId);

    int updateByExampleSelective(@Param("record") QQGroup record, @Param("example") QQGroupExample example);

    int updateByExample(@Param("record") QQGroup record, @Param("example") QQGroupExample example);

    int updateByPrimaryKeySelective(QQGroup record);

    int updateByPrimaryKey(QQGroup record);
}