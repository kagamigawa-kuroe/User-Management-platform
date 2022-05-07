package com.hongzhe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongzhe.entity.SysRole;
import com.hongzhe.mapper.SysRoleMapper;
import com.hongzhe.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  service implement class
 * </p>
 *
 * @author Hongzhe
 * @since 2022-04-20
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> listRolesByUserId(Long id) {
        List<SysRole> sysRoles = this.list(new QueryWrapper<SysRole>()
                .inSql("id", "select role_id from sys_user_role where user_id = " + id));

        return sysRoles;
    }
}
