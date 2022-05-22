package com.nhnacademy.jdbc.board.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {

    private int userNo;
    @NotBlank
    @Size(min=2)
    private String userId;
    @NotBlank
    @Size(min=2)
    private String userPassword;
    private String userNickname;
    private int userTypeCode;

}
