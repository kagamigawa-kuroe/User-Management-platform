package com.hongzhe.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Hongzhe
 * @since 2022-04-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "super menu can't be null")
    private Long parentId;

    @NotNull(message = "menu name can't be null")
    private String name;

    private String path;

    @NotBlank(message = "permission can't be null")
    private String perms;

    private String component;

    @NotNull(message = "type can't be null")
    private Integer type;

    private String icon;

    @TableField("orderNum")
    private Integer orderNum;

    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
}
