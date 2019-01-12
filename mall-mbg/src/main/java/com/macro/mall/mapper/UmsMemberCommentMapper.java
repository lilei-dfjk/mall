package com.macro.mall.mapper;

import com.macro.mall.model.UmsMemberComment;
import com.macro.mall.model.UmsMemberCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsMemberCommentMapper {
    int countByExample(UmsMemberCommentExample example);

    int deleteByExample(UmsMemberCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsMemberComment record);

    int insertSelective(UmsMemberComment record);

    List<UmsMemberComment> selectByExample(UmsMemberCommentExample example);

    UmsMemberComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsMemberComment record, @Param("example") UmsMemberCommentExample example);

    int updateByExample(@Param("record") UmsMemberComment record, @Param("example") UmsMemberCommentExample example);

    int updateByPrimaryKeySelective(UmsMemberComment record);

    int updateByPrimaryKey(UmsMemberComment record);
}