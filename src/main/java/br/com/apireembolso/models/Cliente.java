package br.com.apireembolso.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

@Getter
@Setter
@ToString
@Entity(name = "Cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome")
	private String nome;

	@NotNull
	@Column(name = "cidade")
	private String cidade;

	@NotNull
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "status")
	private String status = "Ativo";

	@JsonIgnore
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Despesa> despesa;

	public Cliente() {
		super();
	}

	public Cliente(Long id, @NotNull String nome, @NotNull String cidade, @NotNull String estado, String status,
			List<Despesa> despesa) {
		super();
		this.id = id;
		this.nome = nome;
		this.cidade = cidade;
		this.estado = estado;
		this.status = status;
		this.despesa = despesa;
	}

	

}
