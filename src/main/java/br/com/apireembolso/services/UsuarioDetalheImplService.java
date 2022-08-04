package br.com.apireembolso.services;

import br.com.apireembolso.models.Usuario;
import br.com.apireembolso.models.UsuarioPerfil;
import br.com.apireembolso.respositories.UsuarioRepo;
import br.com.apireembolso.security.UsuarioDetalhe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioDetalheImplService implements UserDetailsService {

	@Autowired
	private UsuarioRepo usuarioRepository;

	public UsuarioDetalheImplService(UsuarioRepo usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		if (!usuario.isPresent()) {
			throw new RuntimeException();
		}

//		String perfil = "";
//		for (UsuarioPerfil usuarioPerfil : usuario.get().getUsuarioPerfis()) {
//			perfil = usuarioPerfil.getPerfil().getNome();
//		}

		//return new UsuarioDetalhe(String.format("%s-%s-%s-%s", usuario.get().getNome(), usuario.get().getUsername(), usuario.get().getEmail(), perfil));
		return new UsuarioDetalhe(usuario);
	}

}
