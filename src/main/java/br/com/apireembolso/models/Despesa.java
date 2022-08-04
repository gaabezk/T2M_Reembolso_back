package br.com.apireembolso.models;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Despesa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Past
	@NotNull
	@Column(name = "data")
	private LocalDate data;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@Column(name = "valor")
	private Double valor;

	@NotNull
	@ManyToOne()
	@JoinColumn(name = "categoria_despesa_id", referencedColumnName = "id")
	private CategoriaDespesa categoriaDespesa;

	@NotNull
	@ManyToOne()
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private Cliente cliente;
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;
	
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "solicitacao_id", referencedColumnName = "id")
	private Solicitacao solicitacao;

	@Column(name = "url")
	private String url;
	
	@Column(name = "pendente")
	private Boolean pendente = true;

//	@OneToOne(cascade = CascadeType.ALL, mappedBy = "despesa")
//	private Anexo anexo;

	public Despesa() {
	}

	public Despesa(Long id, @Past LocalDate data, @NotNull String descricao, @NotNull Double valor,
			@NotNull CategoriaDespesa categoriaDespesa, @NotNull Cliente cliente, String url, Anexo anexo) {
		super();
		this.id = id;
		this.data = data;
		this.descricao = descricao;
		this.valor = valor;
		this.categoriaDespesa = categoriaDespesa;
		this.cliente = cliente;
		this.url = url;
	}


}
