package br.com.apireembolso.services;

import br.com.apireembolso.enums.StatusEmail;
import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.exceptions.UsuarioExistenteException;
import br.com.apireembolso.modelDTO.RedefinirSenha;
import br.com.apireembolso.modelDTO.SolicitacaoDTO;
import br.com.apireembolso.modelDTO.UsuarioInserirDTO;
import br.com.apireembolso.modelDTO.UsuarioPerfilDTO;
import br.com.apireembolso.models.*;
import br.com.apireembolso.respositories.EmailRepo;
import br.com.apireembolso.respositories.UsuarioPerfilRepo;
import br.com.apireembolso.respositories.UsuarioRepo;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

	@Value("${repita-seu-email}")
	private String email;
	@Autowired
	EmailRepo emailRepository;

	@Autowired
	EmailService emailService;

	private JavaMailSender emailSender;
	@Autowired
	private final UsuarioRepo repo;
	@Autowired
	private final PasswordEncoder encoder;
	@Autowired
	PerfilService perfilService;
	@Autowired
	private UsuarioPerfilRepo usuarioPerfilRepo;

	public UsuarioService(UsuarioRepo repo, PasswordEncoder encoder) {
		this.repo = repo;
		this.encoder = encoder;
	}

	public List<Usuario> listarTodos() {
		return repo.findAll();
	}

	public List<UsuarioPerfilDTO> ListarPorPerfil(Long id) throws ErrorException {

		List<UsuarioPerfilDTO> usuarioPerfilDTO2 = new ArrayList<>();

		for (Usuario usuario : repo.findAll()) {
			for (UsuarioPerfil usuarioPerfil : usuario.getUsuarioPerfis()) {
				if (usuarioPerfil.getPerfil().getId() == id) {
					UsuarioPerfilDTO usuarioPerfil2 = new UsuarioPerfilDTO();
					usuarioPerfil2.setNome(usuario.getNome());
					usuarioPerfil2.setId(usuario.getId());
					usuarioPerfil2.setPerfil(usuarioPerfil.getPerfil().getNome());
					usuarioPerfilDTO2.add(usuarioPerfil2);
				}
			}
		}
		return (List<UsuarioPerfilDTO>) usuarioPerfilDTO2;
	}

	public Usuario buscarPorNome(String nome) throws ErrorException {
		Optional<Usuario> optional = repo.findByNome(nome);
		if (optional.isEmpty()) {
			throw new ErrorException("Usuario não existe!");
		}
		return optional.get();
	}

	public Usuario salvar(UsuarioInserirDTO usuarioInserirDTO) throws UsuarioExistenteException, ErrorException {
		Optional<Usuario> optionaUser = repo.findByEmail(usuarioInserirDTO.getEmail());
		Optional<Usuario> optionalEmail = repo.findByEmail(usuarioInserirDTO.getEmail());
		if (optionaUser.isPresent() || optionalEmail.isPresent()) {
			throw new UsuarioExistenteException();
		}
		Usuario usuario = new Usuario();

		usuario.setNome(usuarioInserirDTO.getNome());
		usuario.setCpf(usuarioInserirDTO.getCpf());
		usuario.setPassword(encoder.encode(usuarioInserirDTO.getPassword()));
		usuario.setEmail(usuarioInserirDTO.getEmail());
		repo.save(usuario);

		for (UsuarioPerfil usuarioPerfil : usuarioInserirDTO.getUsuarioPerfis()) {
			usuarioPerfil.setUsuario(usuario);
			usuarioPerfil.setPerfil(perfilService.buscar(usuarioPerfil.getPerfil().getId()));
			usuarioPerfil.setDataCriacao(LocalDate.now());
		}
		usuarioPerfilRepo.saveAll(usuarioInserirDTO.getUsuarioPerfis());
		return (usuario);
	}

	public UsuarioDTO validarSenha(String email, String password) throws ErrorException {
		Optional<Usuario> optional = repo.findByEmail(email);
		if (optional.isEmpty()) {
			throw new ErrorException("usuario nao encontrado!");
		}
		boolean valid = encoder.matches(password, optional.get().getPassword());
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		if (valid == true) {
			usuarioDTO.setId(optional.get().getId());
			usuarioDTO.setEmail(optional.get().getEmail());
			usuarioDTO.setNome(optional.get().getNome());
			usuarioDTO.setStatus(optional.get().getStatus());

			for (UsuarioPerfil usuarioPerfil : optional.get().getUsuarioPerfis()) {
				usuarioDTO.setPerfil(usuarioPerfil.getPerfil().getNome());
			}

		}
		return usuarioDTO;

	}

	public Usuario atualizar(Usuario usuario, long id) throws UsuarioExistenteException, ErrorException {

			Optional<Usuario> optional = repo.findById(id);
			if (optional.isEmpty()) {
				throw new ErrorException("Esse usuario não existe!");
			}
			Usuario oldUsuario = optional.get();
			
			if (usuario.getNome() != null && !usuario.getNome().equals("")) {
				oldUsuario.setNome(usuario.getNome());
			}
			if (usuario.getEmail() != null && !usuario.getEmail().equals("")) {
				oldUsuario.setEmail(usuario.getEmail());
			}
			if (usuario.getPassword() != null && !usuario.getPassword().equals("")) {
				oldUsuario.setPassword(encoder.encode(usuario.getPassword()));
			}

			repo.save(oldUsuario);
			return null;

		}


	 public String alterarStatus(Long id) throws ErrorException {

	        Optional<Usuario> optional = repo.findById(id);
	      
	        if(optional.get().getStatus().equals("Ativo") ) {
	        	optional.get().setStatus("Inativo");
	        	
	        } else {
	        	optional.get().setStatus("Ativo");
	        }
	        
	        repo.save(optional.get());
	        return "Status Alterado com sucesso";
	    }

	public void esqueciMinhaSenha(RedefinirSenha redefinirSenha) throws ErrorException {

		Optional<Usuario> optional = repo.findByEmail(redefinirSenha.getEmail());
			if (optional.isEmpty()){
				throw new ErrorException("E-mail não cadastrado!");
			}
		if (!redefinirSenha.getCodigo().equals(optional.get().getCodigo())){
			throw new ErrorException("Codigo inválido");
		} else{
			optional.get().setPassword(encoder.encode(redefinirSenha.getSenha()));
			optional.get().setCodigo(null);
			repo.save(optional.get());

		}

	}

	public void sendCode(String emaill) throws ErrorException {
		Optional<Usuario> optional = repo.findByEmail(emaill);
		if (optional.isEmpty()){
			throw new ErrorException("E-mail não cadastrado!");
		}
		String codigoRandom = RandomString.make(6);
		optional.get().setCodigo(codigoRandom);

		Email meuEmail = new Email();
		meuEmail.setSendDateEmail(LocalDateTime.now());
		meuEmail.setEmailTo(emaill);
		meuEmail.setSubject("Redefinir senha");
		meuEmail.setText("Segue o codigo para redefinir sua senha: " + optional.get().getCodigo());

		repo.save(optional.get());
		emailService.sendEmail(meuEmail);
	}




}
