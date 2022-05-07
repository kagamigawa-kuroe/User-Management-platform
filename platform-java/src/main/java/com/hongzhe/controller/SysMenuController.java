
package com.hongzhe.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongzhe.common.dto.SysMenuDto;
import com.hongzhe.common.lang.Const;
import com.hongzhe.common.lang.Result;
import com.hongzhe.entity.SysMenu;
import com.hongzhe.entity.SysRoleMenu;
import com.hongzhe.entity.SysUser;
import org.apache.ibatis.executor.ResultExtractor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * <p>
 *  Front end controller
 * </p>
 *
 * @author Hongzhe
 * @since 2022-04-20
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

    @GetMapping("/nav")
    public Result nav (Principal principal){
        SysUser sysUser = sysUserService.getByUsername(principal.getName());

        //permission
        String authorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getId());
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");
        //The navigation bar
        List<SysMenuDto> navs =  sysMenuService.getCurrentUserNav();

        return Result.succ(MapUtil.builder()
                .put("authoritys",authorityInfoArray)
                .put("nav",navs)
                .map()
        );
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result list() {

        List<SysMenu> menus = sysMenuService.tree();
        return Result.succ(menus);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result save(@Validated @RequestBody SysMenu sysMenu) {

        sysMenu.setCreated(LocalDateTime.now());

        sysMenuService.save(sysMenu);
        return Result.succ(sysMenu);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result update(@Validated @RequestBody SysMenu sysMenu) {

        sysMenu.setUpdated(LocalDateTime.now());

        sysMenuService.updateById(sysMenu);

        // Clear all permissions caches associated with this menu
        sysUserService.clearUserAuthorityInfoByMenuId(sysMenu.getId());
        return Result.succ(sysMenu);
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result info(@PathVariable(name = "id") Long id) {
        return Result.succ(sysMenuService.getById(id));
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result delete(@PathVariable("id") Long id) {
        int count = sysMenuService.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) {
            return Result.fail("Delete the submenu first");
        }

        // Clear all permissions caches associated with this menu
        sysUserService.clearUserAuthorityInfoByMenuId(id);

        sysMenuService.removeById(id);

        // Delete intermediate associated tables synchronously
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
        return Result.succ("");
    }
}
