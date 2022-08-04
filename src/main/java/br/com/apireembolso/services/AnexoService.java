package br.com.apireembolso.services;

import br.com.apireembolso.exceptions.IdNotFoundException;
import br.com.apireembolso.models.Anexo;
import br.com.apireembolso.models.Despesa;
import br.com.apireembolso.respositories.AnexoRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Service
public class AnexoService {

	@Autowired
	AnexoRepo anexoRepo;

	@Transactional
	public Anexo create(Despesa despesa, MultipartFile file) throws IOException {
		Anexo anexo = new Anexo();
		anexo.setNome("anexo");
		anexo.setTipo(file.getContentType());
		anexo.setDados(file.getBytes());
		anexo.setDespesa(despesa);
		return anexoRepo.save(anexo);
	}
	
	public Anexo inserir(Despesa despesa, MultipartFile file) throws IOException {
		Anexo foto = new Anexo(null, file.getBytes(), file.getContentType(), file.getName(), despesa);
		return anexoRepo.save(foto);
	}

	public String createUrl(Long id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/despesa/{id}/imagem/").buildAndExpand(id)
				.toUri();
		return uri.toString();

	}

//	@Transactional
//	public Anexo getAnexo(Long id) throws IdNotFoundException {
//		Optional<Anexo> optional = anexoRepo.findByDespesaId(id);
//		if (optional.isEmpty()) {
//			throw new IdNotFoundException();
//		}
//		return optional.get();
//
//	}
	
	public Anexo buscar(Long id) {
		Optional<Anexo> anexo = anexoRepo.findById(id);
		if (!anexo.isPresent()) {
			return null;
		}
		return anexo.get();
	}
}

