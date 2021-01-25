package com.eclair.dongbaoums.mapper;

import com.eclair.dongbaoums.entity.UmsMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author wjk
 * @since 2021-01-25
 */
public interface UmsMemberMapper extends BaseMapper<UmsMember> {

    UmsMember selectUmsByUsername(@Param("username") String username);

}
