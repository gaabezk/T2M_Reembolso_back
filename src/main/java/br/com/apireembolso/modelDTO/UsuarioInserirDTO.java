package br.com.apireembolso.modelDTO;

import br.com.apireembolso.models.UsuarioPerfil;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UsuarioInserirDTO {

	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private String password;
	private Set<UsuarioPerfil> usuarioPerfis = new HashSet<>();

	public UsuarioInserirDTO() {
		super();
	}

	public UsuarioInserirDTO(Long id, String nome, String cpf, String email, String password) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.password = password;
	}
}
