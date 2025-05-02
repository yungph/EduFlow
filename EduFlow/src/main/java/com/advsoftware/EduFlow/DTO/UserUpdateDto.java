package com.advsoftware.Recruitment_site.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    private String id;
    private String username;
    private String password;
    private String email;
    private String dtype;
}
