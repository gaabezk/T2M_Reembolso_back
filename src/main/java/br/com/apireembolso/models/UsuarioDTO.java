package br.com.apireembolso.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String perfil;
    private String status;

    public UsuarioDTO() {
    }
}
