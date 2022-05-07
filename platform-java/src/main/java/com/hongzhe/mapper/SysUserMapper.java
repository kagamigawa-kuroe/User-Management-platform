package com.hongzhe.mapper;

import com.hongzhe.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper interface
 * </p>
 *
 * @author Hongzhe
 * @since 2022-04-20
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<Long> getNavMenuIds(long userId);

    List<SysUser> listByMenuId(Long menuId);
}
