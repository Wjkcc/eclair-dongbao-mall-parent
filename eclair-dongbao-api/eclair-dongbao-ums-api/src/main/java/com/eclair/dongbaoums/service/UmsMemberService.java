package com.eclair.dongbaoums.service;

import com.eclair.dongbaoums.dto.UmsMemberLoginDTO;
import com.eclair.dongbaoums.dto.UmsMemberRegisterDTO;
import com.eclair.dongbaoums.entity.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author wjk
 * @since 2021-01-17
 */
public interface UmsMemberService extends IService<UmsMember> {

    String register(UmsMemberRegisterDTO umsMemberRegisterDTO);

    String login(UmsMemberLoginDTO umsMemberLoginDTO);

}
