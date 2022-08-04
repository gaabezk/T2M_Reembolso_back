package br.com.apireembolso.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Status;
import javax.transaction.Transactional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.exceptions.UsuarioExistenteException;
import br.com.apireembolso.modelDTO.SolicitacaoDTO;
import br.com.apireembolso.models.Despesa;
import br.com.apireembolso.models.Email;
import br.com.apireembolso.models.Solicitacao;
import br.com.apireembolso.models.Usuario;
import br.com.apireembolso.models.UsuarioDTO;
import br.com.apireembolso.respositories.DespesaRepo;
import br.com.apireembolso.respositories.SolicitacaoRepo;
import br.com.apireembolso.respositories.UsuarioRepo;
import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class SolicitacaoService {

	@Autowired
	SolicitacaoRepo solicitacaoRepo;

	@Autowired
	DespesaRepo despesaRepo;

	@Autowired
	UsuarioRepo usuarioRepo;

	@Autowired
	EmailService emailService;

	public List<Solicitacao> listarTodos() {
		return solicitacaoRepo.findAll();
	}

	public List<Solicitacao> listarTodasPendentes() {
		return (List<Solicitacao>) solicitacaoRepo.findByStatus("Pendente");
	}
	public String alterarStatus(String status, Long id) throws ErrorException {
		Optional<Solicitacao> optional = solicitacaoRepo.findById(id);
		if (optional.isEmpty()) {
			throw new ErrorException("Essa Solicitação não existe!");
		}
		Solicitacao oldSolicitacao = optional.get();
		oldSolicitacao.setAguardando(false);
		oldSolicitacao.setStatus(status);

		solicitacaoRepo.save(oldSolicitacao);
		return "Status atualizado";
	}
	public List<Solicitacao> listarTodasAprovadas() {
		return (List<Solicitacao>) solicitacaoRepo.findByStatus("Aprovada");
	}

	public List<Solicitacao> listarPoridUsuario(Long usuario_id) {
		return (List<Solicitacao>) solicitacaoRepo.findByUsuarioId(usuario_id);
	}
  
	public List<Solicitacao> listarPorid(Long id) {
		return (List<Solicitacao> ) solicitacaoRepo.findAllById(id);
	}
	
	public List<SolicitacaoDTO> listarUsuarioPorid2() {

		List<SolicitacaoDTO> solicitacaoDTO2 = new ArrayList<>();

		for (Solicitacao solicitacao3 : solicitacaoRepo.findAll()) {
			if (solicitacao3.getAguardando() == true) {
				SolicitacaoDTO solicitacao2 = new SolicitacaoDTO();
				solicitacao2.setId(solicitacao3.getId());
				solicitacao2.setData(solicitacao3.getData());
				solicitacao2.setNome(solicitacao3.getUsuario().getNome());
				solicitacao2.setStatus(solicitacao3.getStatus());
				solicitacaoDTO2.add(solicitacao2);
			}
		}
		return (List<SolicitacaoDTO>) solicitacaoDTO2;
	}
	public List<SolicitacaoDTO> listarDoAdministrativo() {
		List<SolicitacaoDTO> solicitacaoDTO2 = new ArrayList<>();

		for (Solicitacao solicitacao3 : solicitacaoRepo.findAll()) {
			if (solicitacao3.getAguardando() == true) {
				SolicitacaoDTO solicitacao2 = new SolicitacaoDTO();
				solicitacao2.setId(solicitacao3.getId());
				solicitacao2.setData(solicitacao3.getData());
				solicitacao2.setNome(solicitacao3.getUsuario().getNome());
				solicitacao2.setStatus(solicitacao3.getStatus());
				solicitacaoDTO2.add(solicitacao2);
			}
		}
		return (List<SolicitacaoDTO>) solicitacaoDTO2;
	}

	public List<Solicitacao> listarPorGestor(Long gestor) {
		return solicitacaoRepo.findByGestorAndStatus(gestor,"Pendente");
	}

	@Transactional
	public Solicitacao add(Long id) throws ErrorException {

		Optional<Usuario> optional2 = usuarioRepo.findById(id);
		System.out.println(optional2.get());
		

		Solicitacao solicitacao2 = new Solicitacao();
		solicitacao2.setData(LocalDate.now());
		solicitacao2.setUsuario(optional2.get());

		List<Despesa> list = new ArrayList<>();

		Double total = 0.0;

		for (Despesa despesa : despesaRepo.findAll()) {
			if (despesa.getUsuario().getId() == id && despesa.getPendente() == true) {
				despesa.setSolicitacao(solicitacao2);
				despesa.setPendente(false);
				list.add(despesa);
				total += despesa.getValor();
			}
		}
		solicitacao2.setTotal(total);
		solicitacao2.setDespesas(list);

		solicitacaoRepo.save(solicitacao2);
		return solicitacao2;
	}

	public String atualizar(Solicitacao solicitacao, long id) throws UsuarioExistenteException, ErrorException {

		Optional<Solicitacao> optional = solicitacaoRepo.findById(id);
		if (optional.isEmpty()) {
			throw new ErrorException("Essa Solicitação não existe!");
		}
		Solicitacao oldSolicitacao = optional.get();
		if (solicitacao.getGestor() != null && !solicitacao.getGestor().equals("")) {
			oldSolicitacao.setGestor(solicitacao.getGestor());
			oldSolicitacao.setAguardando(false);
			;
		}
		solicitacaoRepo.save(oldSolicitacao);
		return "Gestor adicionado com sucesso";
	}

	public String aprovar(long id, Email email) throws ErrorException {

		emailService.sendEmail(email);

		Optional<Solicitacao> optional = solicitacaoRepo.findById(id);
		if (optional.isEmpty()) {
			throw new ErrorException("Essa Solicitação não existe!");
		}
		Solicitacao oldSolicitacao = optional.get();
		oldSolicitacao.setStatus("Aprovada");
		oldSolicitacao.setAguardando(false);

		solicitacaoRepo.save(oldSolicitacao);
		return "Solicitação Aprovada";
	}

	public String reprovar(long id, Email email) throws ErrorException {

		emailService.sendEmail(email);

		Optional<Solicitacao> optional = solicitacaoRepo.findById(id);
		if (optional.isEmpty()) {
			throw new ErrorException("Essa Solicitação não existe!");
		}
		Solicitacao oldSolicitacao = optional.get();
		oldSolicitacao.setStatus("Reprovada");
		oldSolicitacao.setAguardando(false);

		solicitacaoRepo.save(oldSolicitacao);
		return "Solicitação Reprovada";
	}
}