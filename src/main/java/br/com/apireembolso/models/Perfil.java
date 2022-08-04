package br.com.apireembolso.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_perfil")
	private Long id;

	@NotNull
	@Column(name = "nome")
	private String nome;
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.perfil", orphanRemoval = true)
	private Set<UsuarioPerfil> usuarioPerfis = new HashSet<>();

	public Perfil() {

	}

	public Perfil(Long id, @NotNull String nome, Set<UsuarioPerfil> usuarioPerfis) {
		super();
		this.id = id;
		this.nome = nome;
		this.usuarioPerfis = usuarioPerfis;
	}

}
