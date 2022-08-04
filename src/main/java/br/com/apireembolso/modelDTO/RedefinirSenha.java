package br.com.apireembolso.modelDTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedefinirSenha {

    private String email;
    private String senha;
    private String codigo;

    public RedefinirSenha() {
    }
}
