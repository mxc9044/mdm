package com.mdm.auth.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mdm.auth.mapper.SysRoleMapper;
import com.mdm.auth.mapper.SysUserMapper;
import com.mdm.auth.mapper.SysUserRoleMapper;
import com.mdm.auth.model.dto.LoginDTO;
import com.mdm.auth.model.entity.SysRole;
import com.mdm.auth.model.entity.SysUser;
import com.mdm.auth.model.entity.SysUserRole;
import com.mdm.auth.model.vo.LoginVO;
import com.mdm.common.exception.BizException;
import com.mdm.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SysUserMapper userMapper;
    private final SysRoleMapper roleMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginVO login(LoginDTO dto) {
        SysUser user = userMapper.selectOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, dto.getUsername()));
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BizException(ErrorCode.UNAUTHORIZED, "用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BizException(ErrorCode.FORBIDDEN, "账号已被停用");
        }

        StpUtil.login(user.getId());

        LoginVO vo = new LoginVO();
        vo.setToken(StpUtil.getTokenValue());
        vo.setUsername(user.getUsername());
        vo.setRealName(user.getRealName());
        vo.setRoles(getUserRoles(user.getId()));
        return vo;
    }

    public void logout() {
        StpUtil.logout();
    }

    public List<String> getUserRoles(Long userId) {
        List<SysUserRole> userRoles = userRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        if (userRoles.isEmpty()) {
            return List.of();
        }
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        List<SysRole> roles = roleMapper.selectBatchIds(roleIds);
        return roles.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
    }

    public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
