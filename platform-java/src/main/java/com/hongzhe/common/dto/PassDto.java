package com.hongzhe.common.dto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class PassDto implements Serializable {

    @NotBlank(message = "old passwd can't be null")
    private String password;

    @NotBlank(message = "new passwd can't be null")
    private String currentPass;
}
