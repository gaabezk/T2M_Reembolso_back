package br.com.apireembolso.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class UsuarioPerfil {

	@EmbeddedId
	private UsuarioPerfilPk id = new UsuarioPerfilPk();

	@Column(name = "data_criacao")
	private LocalDate dataCriacao;

	public UsuarioPerfil() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioPerfil(Usuario usuario, Perfil perfil, LocalDate dataCriacao) {
		id.setUsuario(usuario);
		id.setPerfil(perfil);
		this.dataCriacao = dataCriacao;
	}

	public void setUsuario(Usuario usuario) {
		id.setUsuario(usuario);
	}

	public Usuario getUsuario() {
		return id.getUsuario();
	}

	public void setPerfil(Perfil perfil) {
		id.setPerfil(perfil);
	}

	public Perfil getPerfil() {
		return id.getPerfil();
	}
}
