package br.com.apireembolso.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import br.com.apireembolso.models.Usuario;
import br.com.apireembolso.models.UsuarioPerfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioDetalhe implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Optional<Usuario> usuario;

	public UsuarioDetalhe(Optional<Usuario> usuario) {
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
		return usuario.get().getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.get().getEmail();
	}
	
	public String getStatus() {
		return usuario.get().getStatus();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
