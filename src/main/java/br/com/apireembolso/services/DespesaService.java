package br.com.apireembolso.services;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import br.com.apireembolso.modelDTO.DespesaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.models.CategoriaDespesa;
import br.com.apireembolso.models.Cliente;
import br.com.apireembolso.models.Despesa;
import br.com.apireembolso.models.Usuario;
import br.com.apireembolso.respositories.CategoriaDespesaRepo;
import br.com.apireembolso.respositories.ClienteRepo;
import br.com.apireembolso.respositories.DespesaRepo;
import br.com.apireembolso.respositories.UsuarioRepo;

@Service
public class DespesaService {

	@Autowired
	DespesaRepo repoDespesa;
	
	@Autowired
	CategoriaDespesaRepo repoCategoria;
	
	@Autowired
	ClienteRepo repoCliente;
	
	@Autowired
	UsuarioRepo repoUsuario;
	
	
	@Autowired
	AnexoService anexoService;
	
	@Transactional
	public List<Despesa> listarTodos() {
		List<Despesa> lista = repoDespesa.findAll();
		for (Despesa prod: lista ){
			prod.setUrl(anexoService.createUrl(prod.getId()));
		}
		return lista;
	}
	
	public List<Despesa> listar() {
		List<Despesa> despesa = repoDespesa.findAll();
		List<Despesa> despesas2 = new ArrayList<>();
		for (Despesa despesas : despesa) {
			despesas2.add(adicionarImagemUri(despesas));
		}
		return despesas2;
	}
	
	public List<DespesaDTO> listarPoridSolicitacao(Long id) {

		List<Despesa> despesas = repoDespesa.findBySolicitacaoId(id);
		List<DespesaDTO> despesasDTO = new ArrayList<>();

		for (Despesa despesa : despesas) {

			DespesaDTO despesaDTO = new DespesaDTO();
			despesaDTO.setData(despesa.getData());
			despesaDTO.setDescricao(despesa.getDescricao());
			despesaDTO.setValor(despesa.getValor());
			despesaDTO.setIdCategoriaDespesa(despesa.getCategoriaDespesa().getId());
			despesaDTO.setNomeCategoriaDespesa(despesa.getCategoriaDespesa().getNome());
			despesaDTO.setIdCliente(despesa.getCliente().getId());
			despesaDTO.setNomeCliente(despesa.getCliente().getNome());
			despesaDTO.setIdUsuario(despesa.getUsuario().getId());
			despesaDTO.setNomeUsuario(despesa.getUsuario().getNome());
			despesaDTO.setEmailUsuario(despesa.getUsuario().getEmail());
			despesaDTO.setUrl(despesa.getUrl());

			despesasDTO.add(despesaDTO);
		}

		return despesasDTO;

	}

	public List<Despesa> buscarPorUsuario(Long id) throws ErrorException {
		Optional<Usuario> optional1 = repoUsuario.findById(id);
		if (optional1.isEmpty()) {
			throw new ErrorException("Não existe esse usuario!");
		}

		List<Despesa> lista =  repoDespesa.findAllByUsuarioAndPendente(optional1.get(),true);

		for (Despesa despesa: lista ){
			despesa.setUrl(anexoService.createUrl(despesa.getId()));
		}

		return lista;
	}

	public Despesa buscarPorData(LocalDate data) throws ErrorException {
		Optional<Despesa> optional = repoDespesa.findByData(data);
		if (optional.isEmpty()) {
			throw new ErrorException("Não existe despesa nesse dia!");
		}
		optional.get().setUrl(anexoService.createUrl(optional.get().getId()));
		return optional.get();
	}
	
	private Despesa adicionarImagemUri(Despesa despesa) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/despesa/{id}/imagem")
				.buildAndExpand(despesa.getId()).toUri();
		despesa.setUrl(uri.toString());
		return despesa;
	}

	@Transactional
	public Despesa add(DespesaDTO despesaDTO, MultipartFile file) throws ErrorException, IOException {

		Optional<CategoriaDespesa> optional = repoCategoria.findById(despesaDTO.getIdCategoriaDespesa());

		if (optional.isEmpty()) {
			throw new ErrorException("Categoria não existe!");
		}

		Optional<Cliente> optional2 = repoCliente.findById(despesaDTO.getIdCliente());

		if (optional2.isEmpty()) {
			throw new ErrorException("Cliente não existe!");
		}
		
		Optional<Usuario> optional3 = repoUsuario.findById(despesaDTO.getIdUsuario());

		if (optional2.isEmpty()) {
			throw new ErrorException("Usuario não existe!");
		}

		if (despesaDTO.getData() == null || despesaDTO.getData().equals("")) {
			throw new ErrorException("Informe a data!");
		}

		if (despesaDTO.getDescricao() == null || despesaDTO.getDescricao().equals("")) {
			throw new ErrorException("Informe a descrição!");
		}

		if (despesaDTO.getValor() == null || despesaDTO.getValor().equals("")) {
			throw new ErrorException("Informe o valor!");
		}

		Despesa despesa = new Despesa();

		despesa.setData(despesaDTO.getData());
		despesa.setDescricao(despesaDTO.getDescricao());
		despesa.setValor(despesaDTO.getValor());
		despesa.setCategoriaDespesa(optional.get());
		despesa.setCliente(optional2.get());
		despesa.setUsuario(optional3.get());

		Despesa despesa1 = repoDespesa.save(despesa);

		anexoService.inserir(despesa1,file);

		return adicionarImagemUri(despesa);
	}
	
	public String deletarDaSolicitacao(Long id) throws ErrorException {

        Optional<Despesa> optional = repoDespesa.findById(id);
        if (optional.isEmpty()) {
            throw new ErrorException("Despesa não existe!");
        }
        optional.get().setPendente(false);
        repoDespesa.save(optional.get());
        return "Despesa Deletada da solicitação com sucesso";
    }

	
    public Void delete(Long id) throws ErrorException {

        Optional<Despesa> optional = repoDespesa.findById(id);
        if (optional.isEmpty()) {
            throw new ErrorException("Despesa não existe!");
        }
        repoDespesa.deleteById(id);
        return null;
    }
}
