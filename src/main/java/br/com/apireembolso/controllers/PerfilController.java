package br.com.apireembolso.controllers;

import br.com.apireembolso.exceptions.PerfilExistenteException;
import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.models.Perfil;
import br.com.apireembolso.services.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gestor/perfil")
public class PerfilController {

	@Autowired
	PerfilService perfilService;

	@GetMapping("/get")
	public ResponseEntity<List<Perfil>> listarTodos() {
		return ResponseEntity.ok(perfilService.listarTodos());
	}
	
	@GetMapping("/get/{nome}")
	public ResponseEntity<Perfil> buscarPorNome(@PathVariable String nome) throws ErrorException {
		return ResponseEntity.ok(perfilService.buscarPorNome(nome));
	}
	

	@PreAuthorize("hasRole('ROLE_GESTOR')")
	@PostMapping("/post/{nome}")
	public ResponseEntity<Perfil> salvar(@PathVariable String nome) throws PerfilExistenteException {
		return ResponseEntity.ok(perfilService.salvar(nome));
	}
	
	@PreAuthorize("hasRole('ROLE_GESTOR')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws ErrorException {
		return ResponseEntity.ok(perfilService.delete(id));
	}
	
}
