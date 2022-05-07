package com.hongzhe.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Hongzhe
 * @since 2022-04-20
 */
@Data
public class SysRoleMenu {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;


}
