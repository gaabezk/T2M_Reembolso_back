package br.com.apireembolso.controllers;

import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.models.Cliente;
import br.com.apireembolso.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@GetMapping("/get")
	public ResponseEntity<List<Cliente>> listarTodos() {
		return ResponseEntity.ok(clienteService.listarTodos());
	}
	
	@GetMapping("/getAtivo")
	public ResponseEntity<List<Cliente>> listarAtivo() {
		return ResponseEntity.ok(clienteService.listarPorStatus());
	}
	
	@GetMapping("/get/{nome}")
	public ResponseEntity<Cliente> buscarPorNome(@PathVariable String nome) throws ErrorException {
		return ResponseEntity.ok(clienteService.buscarPorNome(nome));
	}

	@PreAuthorize("hasRole('ROLE_GESTOR')")
	@PostMapping("/post")
	public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) throws ErrorException {
		return ResponseEntity.ok(clienteService.adicionar(cliente));
	}
	
	@PreAuthorize("hasRole('ROLE_GESTOR')")
	@PutMapping("/alterarStatus/{id}")
	public ResponseEntity<String> alterarStatus(@PathVariable Long id) throws ErrorException {
		return ResponseEntity.ok(clienteService.alterarStatus(id));
	}


}
