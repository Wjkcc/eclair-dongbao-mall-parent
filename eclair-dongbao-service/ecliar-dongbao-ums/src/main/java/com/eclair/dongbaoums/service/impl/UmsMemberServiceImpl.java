package com.eclair.dongbaoums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eclair.dongbaoums.dto.UmsMemberRegisterDTO;
import com.eclair.dongbaoums.entity.UmsMember;
import com.eclair.dongbaoums.mapper.UmsMemberMapper;
import com.eclair.dongbaoums.service.UmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author wjk
 * @since 2021-01-17
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Resource
    UmsMemberMapper umsMemberMapper;
    @Override
    public String register(UmsMemberRegisterDTO umsMemberRegisterDTO) {
        // 先判断用户名是否存在，为防止并发，数据库的username设置唯一索引
        QueryWrapper<UmsMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",umsMemberRegisterDTO.getUserName());
        Integer integer = umsMemberMapper.selectCount(queryWrapper);
        if (integer > 0) {
            return "username is exit";
        }
        UmsMember umsMember = new UmsMember();
        // dto拥有的属性转换到umsMember
        BeanUtils.copyProperties(umsMemberRegisterDTO, umsMember);
        try {
            int insert = umsMemberMapper.insert(umsMember);
            if (insert < 1) {
                return "insert error";
            }
        }catch (Exception e) {
            return e.getMessage();
        }
        return "ok";
    }
}
