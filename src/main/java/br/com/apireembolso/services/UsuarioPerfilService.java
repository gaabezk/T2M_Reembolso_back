package br.com.apireembolso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apireembolso.models.UsuarioPerfil;
import br.com.apireembolso.respositories.UsuarioPerfilRepo;

@Service
public class UsuarioPerfilService {
	
	private final UsuarioPerfilRepo repo = null;

	public List<UsuarioPerfil> listarTodos() {
		return repo.findAll();
	}
}
