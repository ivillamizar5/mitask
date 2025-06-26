package com.c4.mitask.dto;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String username;
    private String password;
    private String correoElectronico;
    private Integer rolId;

}
