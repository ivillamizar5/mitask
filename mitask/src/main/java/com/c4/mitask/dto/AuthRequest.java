package com.c4.mitask.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String correoElectronico;
    private String password;
}
