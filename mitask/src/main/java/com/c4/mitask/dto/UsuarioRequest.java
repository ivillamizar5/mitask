package com.c4.mitask.dto;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String correoElectronico;
    private String password;
    private Integer rolId;

}
