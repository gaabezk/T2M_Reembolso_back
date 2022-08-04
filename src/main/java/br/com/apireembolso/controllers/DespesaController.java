package br.com.apireembolso.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.exceptions.IdNotFoundException;
import br.com.apireembolso.modelDTO.DespesaDTO;
import br.com.apireembolso.models.Anexo;
import br.com.apireembolso.models.Despesa;
import br.com.apireembolso.services.AnexoService;
import br.com.apireembolso.services.DespesaService;

@RestController
@RequestMapping("/api/despesa")
public class DespesaController {

	@Autowired
	DespesaService despesaService;
	@Autowired                       
	AnexoService anexoService;
	
//	@GetMapping("/get")
//	public ResponseEntity<List<Despesa>> listarTodos() { 
//		return ResponseEntity.ok(despesaService.listarTodos());
//	}                                                                                
	
	@GetMapping("/get")
	public List<Despesa> listar () {
		return despesaService.listar();
	}
	
	@GetMapping("/get/{data}")
	public ResponseEntity<Despesa> buscarPorNome(@PathVariable LocalDate data) throws ErrorException {
		return ResponseEntity.ok(despesaService.buscarPorData(data));
	}
	@GetMapping("/get/t/{id}")
	public ResponseEntity<List<Despesa>> buscarPorUsuario(@PathVariable Long id) throws ErrorException {
		return ResponseEntity.ok(despesaService.buscarPorUsuario(id));
	}
	
	@GetMapping("/getPorSolicitacao/{id}")
	public ResponseEntity<List<DespesaDTO>> buscarPoridSolicitacao(@PathVariable Long id) throws ErrorException {
		return ResponseEntity.ok(despesaService.listarPoridSolicitacao(id));
	}
	
	@GetMapping("/{id}/imagem")
	public ResponseEntity<byte[]> getImagem(@PathVariable Long id) throws IdNotFoundException {
		Anexo anexo = anexoService.buscar(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", anexo.getTipo());
		headers.add("content-lenght", String.valueOf(anexo.getDados().length));
		return new ResponseEntity<>(anexo.getDados(), headers, HttpStatus.OK);
	}
	
//	@GetMapping("/{id}/foto")
//	public ResponseEntity<byte[]> buscarPorFoto(@PathVariable Long id) {
//		Foto foto = fotoService.buscar(id);
//		System.out.println("FOIIIIIIIIIIIIIIIIIII");
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("content-type", foto.getTipo());
//		headers.add("content-length", String.valueOf(foto.getDados().length));
//		System.out.println("FOIIIIIIIIIIIIIIIIIII2");
//		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
//	}
	
	@PostMapping("/post")
	public ResponseEntity<Despesa> salvar(@RequestPart DespesaDTO despesa, @RequestParam MultipartFile file) throws ErrorException, IOException {
		return ResponseEntity.ok(despesaService.add(despesa,file));
	}
	
	@PutMapping("/deletarDaSolicitacao/{id}")
	public ResponseEntity<String> deletarDaSolicitacao(@PathVariable Long id) throws ErrorException {
		return ResponseEntity.ok(despesaService.deletarDaSolicitacao(id));
	}
	
	@PreAuthorize("hasRole('ROLE_GESTOR')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws ErrorException {
		return ResponseEntity.ok(despesaService.delete(id));
	}
}
