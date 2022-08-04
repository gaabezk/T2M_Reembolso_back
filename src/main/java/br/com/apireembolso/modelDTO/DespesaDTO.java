package br.com.apireembolso.modelDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DespesaDTO {

    private LocalDate data;
    private String descricao;
    private Double valor;
    private Long idCategoriaDespesa;
    private String nomeCategoriaDespesa;
    private Long idCliente;
    private String nomeCliente;
    private Long idUsuario;
    private String nomeUsuario;
    private String emailUsuario;
    private String url;

}
