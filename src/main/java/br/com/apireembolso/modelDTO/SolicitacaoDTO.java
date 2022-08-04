package br.com.apireembolso.modelDTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitacaoDTO {
	
	private Long id;
	private LocalDate data;
	private String nome;
	private String status;

}
