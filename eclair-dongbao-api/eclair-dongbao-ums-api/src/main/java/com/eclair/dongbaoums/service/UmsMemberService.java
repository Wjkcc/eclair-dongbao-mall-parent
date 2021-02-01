package com.eclair.dongbaoums.service;

import com.eclair.base.result.ResultWrapper;
import com.eclair.dongbaoums.dto.UmsMemberChangePwdDTO;
import com.eclair.dongbaoums.dto.UmsMemberLoginDTO;
import com.eclair.dongbaoums.dto.UmsMemberRegisterDTO;
import com.eclair.dongbaoums.dto.UmsMemberUpdateDTO;
import com.eclair.dongbaoums.entity.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eclair.dongbaoums.vo.UmsMemberLoginVO;
import com.eclair.dongbaoums.vo.UmsMemberVO;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;

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

    ResultWrapper<UmsMemberLoginVO> login(UmsMemberLoginDTO umsMemberLoginDTO);

    String changePassword(UmsMemberChangePwdDTO umsMemberChangePwdDTO);

    String updateUser(UmsMemberUpdateDTO umsMemberUpdateDTO);

    ResultWrapper<UmsMemberVO> getUser();

    ResultWrapper loginOut();

}
