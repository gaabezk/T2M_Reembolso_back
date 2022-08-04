package br.com.apireembolso.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Anexo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_anexo")
	private Long id;
	@Lob
	private byte[] dados;
	@Column(name = "tipo")
	private String tipo;
	@Column(name = "nome")
	private String nome;
	@OneToOne
	@JoinColumn(name = "despesa_id")
	private Despesa despesa;

	public Anexo() {
	}

	public Anexo(Long id, byte[] dados, String tipo, String nome, Despesa despesa) {
		super();
		this.id = id;
		this.dados = dados;
		this.tipo = tipo;
		this.nome = nome;
		this.despesa = despesa;
	}
	
}