package com.eclair.dongbaoums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eclair.dongbaoums.dto.UmsMemberLoginDTO;
import com.eclair.dongbaoums.dto.UmsMemberRegisterDTO;
import com.eclair.dongbaoums.entity.UmsMember;
import com.eclair.dongbaoums.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eclair.dongbaoums.service.UmsMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public String register(UmsMemberRegisterDTO umsMemberRegisterDTO) {
        // 查询用户名是否存在
        QueryWrapper<UmsMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",umsMemberRegisterDTO.getUsername());
        Integer integer = umsMemberMapper.selectCount(queryWrapper);
        if (integer > 0) {
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

    @Override
    public String login(UmsMemberLoginDTO umsMemberLoginDTO) {
        // 先查通过用户名查询用户是否存在
        UmsMember umsMember = umsMemberMapper.selectUmsByUsername(umsMemberLoginDTO.getUsername());
        if (null != umsMember) {
            String pwd = umsMember.getPassword();
            // 输入密码 和 数据库加密密码
            boolean matches = bCryptPasswordEncoder.matches(umsMemberLoginDTO.getPassword(), pwd);
            if (matches) {
                return "token";
            }
            return "password is error";
        }
        // 数据库密码和输入密码进行匹配
        return "username is not exit";
    }
}
