package br.com.apireembolso.data;

import br.com.apireembolso.models.Usuario;
import br.com.apireembolso.models.UsuarioPerfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class DetalheUsuarioData implements UserDetails {

	private final Optional<Usuario> usuario;

	public DetalheUsuarioData(Optional<Usuario> usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (UsuarioPerfil usuarioPerfil : usuario.get().getUsuarioPerfis()){
			grantedAuthorities.add(new SimpleGrantedAuthority(usuarioPerfil.getPerfil().getNome()));
		}
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return usuario.orElse(new Usuario()).getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.orElse(new Usuario()).getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
