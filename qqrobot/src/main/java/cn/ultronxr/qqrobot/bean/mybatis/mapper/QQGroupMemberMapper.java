package cn.ultronxr.qqrobot.bean.mybatis.mapper;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroupMember;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroupMemberExample;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroupMemberKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QQGroupMemberMapper {
    long countByExample(QQGroupMemberExample example);

    int deleteByExample(QQGroupMemberExample example);

    int deleteByPrimaryKey(QQGroupMemberKey key);

    int insert(QQGroupMember record);

    int insertSelective(QQGroupMember record);

    List<QQGroupMember> selectByExample(QQGroupMemberExample example);

    QQGroupMember selectByPrimaryKey(QQGroupMemberKey key);

    int updateByExampleSelective(@Param("record") QQGroupMember record, @Param("example") QQGroupMemberExample example);

    int updateByExample(@Param("record") QQGroupMember record, @Param("example") QQGroupMemberExample example);

    int updateByPrimaryKeySelective(QQGroupMember record);

    int updateByPrimaryKey(QQGroupMember record);
}