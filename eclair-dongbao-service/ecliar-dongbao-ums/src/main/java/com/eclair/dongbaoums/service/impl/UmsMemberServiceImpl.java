package com.eclair.dongbaoums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eclair.Exception.BusinessException;
import com.eclair.base.result.ResultWrapper;
import com.eclair.config.ValueConfig;
import com.eclair.dongbaoums.dto.UmsMemberChangePwdDTO;
import com.eclair.dongbaoums.dto.UmsMemberLoginDTO;
import com.eclair.dongbaoums.dto.UmsMemberRegisterDTO;
import com.eclair.dongbaoums.dto.UmsMemberUpdateDTO;
import com.eclair.dongbaoums.entity.UmsMember;
import com.eclair.dongbaoums.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eclair.dongbaoums.service.UmsMemberService;
import com.eclair.dongbaoums.vo.UmsMemberLoginVO;
import com.eclair.token.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author wjk
 * @since 2021-01-25
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Resource
    UmsMemberMapper umsMemberMapper;
    @Resource
    BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 用户注册功能
     * @param umsMemberRegisterDTO
     * @return
     */
    @Override
    @Transactional
    public String register(UmsMemberRegisterDTO umsMemberRegisterDTO) {
        // 查询用户名是否存在
        if (checkUsername(umsMemberRegisterDTO.getUsername())) {
            return "username is exit";
        }
        // 添加用户
        try {
            UmsMember umsMember = new UmsMember();
            BeanUtils.copyProperties(umsMemberRegisterDTO, umsMember);
            // 密码进行加密
            umsMember.setPassword(bCryptPasswordEncoder.encode(umsMember.getPassword()));
            int insert = umsMemberMapper.insert(umsMember);
            if (insert < 1) {
                return "insert error";
            }
        }catch (Exception e) {
            return e.getMessage();
        }
        return "ok";
    }

    /**
     * 校验用户名是否已经存在
     * @param username
     * @return
     */
    private Boolean checkUsername(String username) {
        // 查询用户名是否存在
        QueryWrapper<UmsMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Integer integer = umsMemberMapper.selectCount(queryWrapper);
        return integer > 0;
    }

    /**
     * 登录
     * @param umsMemberLoginDTO
     * @return
     */
    @Override
    public ResultWrapper login(UmsMemberLoginDTO umsMemberLoginDTO) {
        // 先查通过用户名查询用户是否存在
        UmsMember umsMember = umsMemberMapper.selectUmsByUsername(umsMemberLoginDTO.getUsername());
        if (null != umsMember) {
            String pwd = umsMember.getPassword();
            // 输入密码 和 数据库加密密码
            boolean matches = bCryptPasswordEncoder.matches(umsMemberLoginDTO.getPassword(), pwd);
            if (matches) {
                boolean b  = ValueConfig.exp;
                // 获取token 返回
                String token = JWTUtils.generateToken(umsMember.getUsername(), umsMemberLoginDTO.getIp(), b);
                UmsMemberLoginVO umsMemberLoginVO = new UmsMemberLoginVO();
                umsMemberLoginVO.setIcon(umsMember.getIcon());
                umsMemberLoginVO.setNickName(umsMember.getNickName());
                umsMemberLoginVO.setUsername(umsMember.getUsername());
                umsMemberLoginVO.setToken(token);
                umsMemberLoginVO.setId(umsMember.getId());
                return ResultWrapper.success().data(umsMemberLoginVO).build();
            }
            return ResultWrapper.success().data("").build();
        }
        // 数据库密码和输入密码进行匹配
        return ResultWrapper.success().data("").build();
    }

    /**
     * 修改密码
     * @param umsMemberChangePwdDTO
     * @return
     */
    @Override
    public String changePassword(UmsMemberChangePwdDTO umsMemberChangePwdDTO) {
        // 校验是否非为前用户
        UmsMember umsMember = umsMemberMapper.selectById(umsMemberChangePwdDTO.getId());
        if (null != umsMember && umsMember.getStatus() == 0) {
            // 校验旧密码
            boolean matches = bCryptPasswordEncoder.matches(umsMemberChangePwdDTO.getOldPwd(), umsMember.getPassword());
            if (matches) {
                UmsMember update = new UmsMember();
                update.setId(umsMemberChangePwdDTO.getId());
                // 加密
                String pwd = bCryptPasswordEncoder.encode(umsMemberChangePwdDTO.getNewPwd());
                umsMember.setPassword(pwd);
                int i = umsMemberMapper.updateById(umsMember);
                if (i < 1) {
                    throw new BusinessException("update error");
                }
                return "update success";
            }
        }
        return "user is not exit";
    }

    /**
     * 修改用户信息
     * @param umsMemberUpdateDTO
     * @return
     */
    @Override
    public String updateUser(UmsMemberUpdateDTO umsMemberUpdateDTO) {
        if (umsMemberUpdateDTO.getUsername() != null && umsMemberUpdateDTO.getUsername().trim().length() != 0) {
            // 先查通过用户名查询用户是否存在
          if(checkUsername(umsMemberUpdateDTO.getUsername())) {
              return "username is exit";
          }
        }
        // 校验用户信息

        UmsMember umsMember = new UmsMember();
        BeanUtils.copyProperties(umsMemberUpdateDTO,umsMember);
        int i = umsMemberMapper.updateById(umsMember);
        if (i < 1) {
            throw new BusinessException("update error");
        }
        return "update success";
    }
}
