package com.hongzhe.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
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
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "name can't be null")
    private String name;

    @NotBlank(message = "code can't be null")
    private String code;

    private String remark;

    @TableField(exist = false)
    private List<Long> menuIds = new ArrayList<>();

}
