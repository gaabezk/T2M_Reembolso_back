package br.com.apireembolso.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome")
	private String nome;

	@CPF
	@NotNull
	@Column(name = "cpf", unique = true)
	private String cpf;

	@Email
	@NotNull
	@Column(name = "email", unique = true)
	private String email;

	@NotNull
	@Column(name = "senha")
	private String password;
	
	@Column(name = "status")
	private String status = "Ativo";

	@Column(name = "codigo")
	private String codigo = null;
	@JsonIgnore
	@NotNull
	@OneToMany(mappedBy = "id.usuario", fetch = FetchType.EAGER)
	private Set<UsuarioPerfil> usuarioPerfis = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Despesa> despesa;
	
	@JsonIgnore 
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Solicitacao> solicitacao;


	public Usuario() {
		super();
	}


	public Usuario(Long id, @NotNull String nome, @CPF @NotNull String cpf, @NotNull String email,
			@NotNull String password, @NotNull Set<UsuarioPerfil> usuarioPerfis, List<Despesa> despesa,
			List<Solicitacao> solicitacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.password = password;
		this.usuarioPerfis = usuarioPerfis;
		this.despesa = despesa;
		this.solicitacao = solicitacao;
	}




	

}
