package com.arsen.dto;

import lombok.Data;

@Data
public class AuthDTO { // дтошки пишутся не заглавными и по моему этот класс нигде не используется
    private String username;
    private String password;
}
