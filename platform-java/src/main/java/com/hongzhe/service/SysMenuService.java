package com.hongzhe.service;

import com.hongzhe.common.dto.SysMenuDto;
import com.hongzhe.entity.SysMenu;
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
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuDto> getCurrentUserNav();
    public List<SysMenu> tree();
}
