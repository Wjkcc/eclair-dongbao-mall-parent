package com.eclair.dongbaoums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eclair.base.Exception.BusinessException;
import com.eclair.base.config.ValueConfig;
import com.eclair.base.result.ResultWrapper;
import com.eclair.dongbaoums.dto.UmsMemberChangePwdDTO;
import com.eclair.dongbaoums.dto.UmsMemberLoginDTO;
import com.eclair.dongbaoums.dto.UmsMemberRegisterDTO;
import com.eclair.dongbaoums.dto.UmsMemberUpdateDTO;
import com.eclair.dongbaoums.entity.UmsMember;
import com.eclair.dongbaoums.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eclair.dongbaoums.service.UmsMemberService;
import com.eclair.dongbaoums.vo.UmsMemberLoginVO;
import com.eclair.dongbaoums.vo.UmsMemberVO;
import com.eclair.message.user.UserUtil;
import com.eclair.token.JWTUtils;
import com.eclair.token.Third.AbstractTokenSave;
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
    @Resource
    AbstractTokenSave abstractTokenSave;
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
        if (null != umsMember && umsMember.getStatus() != 0) {
            String pwd = umsMember.getPassword();
            // 输入密码 和 数据库加密密码
            boolean matches = bCryptPasswordEncoder.matches(umsMemberLoginDTO.getPassword(), pwd);
            if (matches) {
                boolean b  = ValueConfig.exp;
                // 获取token 返回
                String token = JWTUtils.generateToken(umsMember.getUsername(), umsMemberLoginDTO.getIp(), b);
                // token加入
                if (!b) {
                    // 防止多次登录多个token
                    abstractTokenSave.deleteToken(umsMember.getUsername());
                    abstractTokenSave.addKey(token,umsMember.getUsername());
                }
                UmsMemberLoginVO umsMemberLoginVO = new UmsMemberLoginVO();
                BeanUtils.copyProperties(umsMember,umsMemberLoginVO);
//                umsMemberLoginVO.setIcon(umsMember.getIcon());
//                umsMemberLoginVO.setNickName(umsMember.getNickName());
//                umsMemberLoginVO.setUsername(umsMember.getUsername());
//                umsMemberLoginVO.setId(umsMember.getId());
                umsMemberLoginVO.setToken(token);
                return ResultWrapper.success().data(umsMemberLoginVO).build();
            }
            return ResultWrapper.fail().msg("密码错误").data("").build();
        }
        // 数据库密码和输入密码进行匹配
        return ResultWrapper.fail().msg("用户不存在").data("").build();
    }

    /**
     * 修改密码
     * @param umsMemberChangePwdDTO
     * @return
     */
    @Override
    public ResultWrapper changePassword(UmsMemberChangePwdDTO umsMemberChangePwdDTO) {
        // 校验是否非当前用户
        if (!checkUser(umsMemberChangePwdDTO.getUsername())) {
            throw new RuntimeException("用户校验失败");
        }
        // 暂时未添加此校验
        UmsMember umsMember = umsMemberMapper.selectById(umsMemberChangePwdDTO.getId());
        if (null != umsMember && umsMember.getStatus() == 1) {
            // 校验是否非当前用户
            if (!checkUser(umsMember.getUsername())) {
                throw new RuntimeException("用户校验失败");
            }
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
                // 修改密码后重新登录
                abstractTokenSave.deleteToken(umsMember.getUsername());
                return ResultWrapper.success().build();
            }
        }
        return ResultWrapper.fail().msg("user is not exit or pwd is wrong").build();
    }
    // 校验是否为当前用户
    private boolean checkUser(String username) {
        String user = UserUtil.getUser();
      if (user.equals(username)) {
          return true;
      }
      return false;
    }

    /**
     * 修改用户信息
     * @param umsMemberUpdateDTO
     * @return
     */
    @Override
    public ResultWrapper updateUser(UmsMemberUpdateDTO umsMemberUpdateDTO) {
        UmsMember umsMember1 = umsMemberMapper.selectById(umsMemberUpdateDTO.getId());
        if (!umsMember1.getUsername().equals(UserUtil.getUser())) {
            throw new RuntimeException("用户校验失败");
        }
        if (umsMemberUpdateDTO.getUsername() != null && umsMemberUpdateDTO.getUsername().trim().length() != 0) {
            // 先查通过用户名查询用户是否存在
          if(checkUsername(umsMemberUpdateDTO.getUsername())) {
              return ResultWrapper.fail().msg("username is exit").build() ;
          }
        }
        UmsMember umsMember = new UmsMember();
        BeanUtils.copyProperties(umsMemberUpdateDTO,umsMember);
        int i = umsMemberMapper.updateById(umsMember);
        if (i < 1) {
            throw new BusinessException("update error");
        }
        return ResultWrapper.success().build() ;
    }

    @Override
    public ResultWrapper getUser() {
        String user = UserUtil.getUser();
        UmsMember umsMember = umsMemberMapper.selectUmsByUsername(user);
        if (umsMember == null || umsMember.getStatus() == 0) {
            abstractTokenSave.deleteToken(umsMember.getUsername());
            return ResultWrapper.fail().msg("用户不存在或者失效").build();
        }
        UmsMemberVO umsMemberVO = new UmsMemberVO();
        BeanUtils.copyProperties(umsMember, umsMemberVO);
        return ResultWrapper.success().data(umsMemberVO).build();
    }

    @Override
    public ResultWrapper loginOut() {
        String user = UserUtil.getUser();
        abstractTokenSave.deleteToken(user);
        return ResultWrapper.success().build();
    }
}
