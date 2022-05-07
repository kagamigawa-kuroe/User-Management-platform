package com.hongzhe.service;

import com.hongzhe.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  service class
 * </p>
 *
 * @author Hongzhe
 * @since 2022-04-20
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> listRolesByUserId(Long id);
}
