package br.com.apireembolso.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.exceptions.UsuarioExistenteException;
import br.com.apireembolso.modelDTO.SolicitacaoDTO;
import br.com.apireembolso.models.Email;
import br.com.apireembolso.models.Solicitacao;
import br.com.apireembolso.services.SolicitacaoService;

@RestController
@RequestMapping("/api/solicitacao")
public class SolicitacaoController {
	

	@Autowired
	SolicitacaoService solicitacaoService;
	
	@GetMapping("/get")
	public ResponseEntity<List<Solicitacao>> listarTodos() {
		return ResponseEntity.ok(solicitacaoService.listarTodos());
	}

  	@GetMapping("/getall/{id}")
	public ResponseEntity <List<Solicitacao> > listarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(solicitacaoService.listarPorid(id));
	}
  	@GetMapping("/getPorUsuario/{id}")
	public ResponseEntity <List<Solicitacao> > listarPorUsuarioId(@PathVariable Long id) {
		return ResponseEntity.ok(solicitacaoService.listarPoridUsuario(id));
	}
  	
	@GetMapping("/get2")
	public ResponseEntity <List<SolicitacaoDTO> > listarDoAdministrativo() {
		return ResponseEntity.ok(solicitacaoService.listarDoAdministrativo());
	}
	
	@GetMapping("/getPorGestor/{gestor}")
	public ResponseEntity <List<Solicitacao> > listarUsuarioPorGestor(@PathVariable Long gestor) {
		return ResponseEntity.ok(solicitacaoService.listarPorGestor(gestor));

	}
	@GetMapping("/getallpendentes")
	public ResponseEntity <List<Solicitacao> > listarTodasPendentes() {
		return ResponseEntity.ok(solicitacaoService.listarTodasPendentes());
	}
	@GetMapping("/getallaprovadas")
	public ResponseEntity <List<Solicitacao> > listarTodasAprovadas() {
		return ResponseEntity.ok(solicitacaoService.listarTodasAprovadas());
	}

	@PostMapping("/post/{id}")
	public ResponseEntity<Solicitacao> salvar(@PathVariable Long id) throws ErrorException {
		return ResponseEntity.ok(solicitacaoService.add(id));
	}
	
	@PutMapping("/put/{id}")
	public ResponseEntity<String> atualizar(@RequestBody Solicitacao solicitacao, @PathVariable Long id)
			throws UsuarioExistenteException, ErrorException {
		return ResponseEntity.ok(solicitacaoService.atualizar(solicitacao, id));
	}
	
	@PutMapping("/aprovar/{id}")
	public ResponseEntity<String> aprovar( @PathVariable Long id, @RequestBody Email email)
			throws ErrorException {
		return ResponseEntity.ok(solicitacaoService.aprovar(id, email));
	}
	@PutMapping("/reprovar/{id}")
	public ResponseEntity<String> reprovar( @PathVariable Long id, @RequestBody Email email)
			throws ErrorException {
		return ResponseEntity.ok(solicitacaoService.reprovar(id, email));
	}
	@PutMapping("/alterarstatus/{id}/{status}")
	public ResponseEntity<String> alterarStatus( @PathVariable Long id, @PathVariable String status)
			throws ErrorException {
		return ResponseEntity.ok(solicitacaoService.alterarStatus(status, id));
	}

}
