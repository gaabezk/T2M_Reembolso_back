package br.com.apireembolso.controllers;

import br.com.apireembolso.exceptions.CategoriaExistenteException;
import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.exceptions.IdNotFoundException;
import br.com.apireembolso.models.Anexo;
import br.com.apireembolso.models.CategoriaDespesa;
import br.com.apireembolso.models.Cliente;
import br.com.apireembolso.services.AnexoService;
import br.com.apireembolso.services.CategoriaDespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gestor/categoria")
public class CategoriaDespesaController {

	@Autowired
	AnexoService anexoService;
	@Autowired
	CategoriaDespesaService categoriaDespesaService;

	@GetMapping("/get")
	public ResponseEntity<List<CategoriaDespesa>> listarTodos() {
		return ResponseEntity.ok(categoriaDespesaService.listarTodos());
	}
	
	@GetMapping("/get/{nome}")
	public ResponseEntity<CategoriaDespesa> buscarPorNome(@PathVariable String nome) throws ErrorException {
		return ResponseEntity.ok(categoriaDespesaService.buscarPorNome(nome));
	}

	@PreAuthorize("hasRole('ROLE_GESTOR')")
	@PostMapping("/post")
	public ResponseEntity<CategoriaDespesa> salvar(@RequestBody CategoriaDespesa categoriaDespesa)
			throws CategoriaExistenteException, ErrorException {
		return ResponseEntity.ok(categoriaDespesaService.salvar(categoriaDespesa));
	}

//	@GetMapping("/{id}/anexo")
//	public ResponseEntity<byte[]> getImagem(@PathVariable Long id) throws IdNotFoundException {
//		Anexo imagem = anexoService.getAnexo(id);
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("content-type", imagem.getTipo());
//		headers.add("content-lenght", String.valueOf(imagem.getDados().length));
//		return new ResponseEntity<>(imagem.getDados(), headers, HttpStatus.OK);
//	}
	
	@PreAuthorize("hasRole('ROLE_GESTOR')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws ErrorException {
		return ResponseEntity.ok(categoriaDespesaService.delete(id));
	}

}
