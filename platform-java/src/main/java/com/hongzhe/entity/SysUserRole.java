package com.hongzhe.entity;

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
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;


}
