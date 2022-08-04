package br.com.apireembolso.controllers;

import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.exceptions.UsuarioExistenteException;
import br.com.apireembolso.modelDTO.RedefinirSenha;
import br.com.apireembolso.modelDTO.UsuarioInserirDTO;
import br.com.apireembolso.modelDTO.UsuarioPerfilDTO;
import br.com.apireembolso.models.Email;
import br.com.apireembolso.models.Usuario;
import br.com.apireembolso.models.UsuarioDTO;
import br.com.apireembolso.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/get")
	public ResponseEntity<List<Usuario>> listarTodos() {
		return ResponseEntity.ok(usuarioService.listarTodos());
	}
	
	@GetMapping("/get/{nome}")
	public ResponseEntity<Usuario> buscarPorNome(@PathVariable String nome) throws ErrorException {
		return ResponseEntity.ok(usuarioService.buscarPorNome(nome));
	}

	@GetMapping("/getPorPerfil/{id}")
	public ResponseEntity<List<UsuarioPerfilDTO>> buscarPorPerfil(@PathVariable Long id) throws ErrorException {
		return ResponseEntity.ok(usuarioService.ListarPorPerfil(id));
	}

	@PreAuthorize("hasRole('ROLE_GESTOR')")
	@PostMapping("/post")
	public ResponseEntity<Usuario> salvar(@RequestBody UsuarioInserirDTO usuarioInserirDTO)
			throws UsuarioExistenteException, ErrorException {
		return ResponseEntity.ok(usuarioService.salvar(usuarioInserirDTO));
	}

	@PostMapping("/valid")
	public ResponseEntity<UsuarioDTO> validarSenha(@RequestParam String email, @RequestParam String senha) throws ErrorException {
		return ResponseEntity.ok(usuarioService.validarSenha(email,senha));
	}
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario, @PathVariable Long id)
			throws UsuarioExistenteException, ErrorException {
		return ResponseEntity.ok(usuarioService.atualizar(usuario, id));
	}
	@PreAuthorize("hasRole('ROLE_GESTOR')")
	@PutMapping("/alterarStatus/{id}")
	public ResponseEntity<String> alterarStatus(@PathVariable Long id) throws ErrorException {
		return ResponseEntity.ok(usuarioService.alterarStatus(id));
	}
	@PostMapping("/esquecisenha")
	public ResponseEntity<String> esqueciMinhaSenha(@RequestBody RedefinirSenha redefinirSenha) throws ErrorException {
		usuarioService.esqueciMinhaSenha(redefinirSenha);
		return new ResponseEntity(HttpStatus.OK);
	}
	@PutMapping("/sendcode")
	public ResponseEntity<String> sendCode(@RequestBody RedefinirSenha redefinirSenha) throws ErrorException {
		usuarioService.sendCode(redefinirSenha.getEmail());
		return new ResponseEntity(HttpStatus.OK);
	}

}
