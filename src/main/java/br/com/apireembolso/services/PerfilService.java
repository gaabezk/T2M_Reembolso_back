package br.com.apireembolso.services;

import br.com.apireembolso.exceptions.PerfilExistenteException;
import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.models.Perfil;
import br.com.apireembolso.respositories.PerfilRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepo perfilRepo;

	public List<Perfil> listarTodos() {
		return perfilRepo.findAll();
	}

	public Perfil buscar(Long id) throws ErrorException {
		Optional<Perfil> optional = perfilRepo.findById(id);
		if (optional.isEmpty()) {
			throw new ErrorException("Perfil não existe!");
		}
		return optional.get();
	}
	
	public Perfil buscarPorNome(String nome) throws ErrorException {
		Optional<Perfil> optional = perfilRepo.findByNome(nome);
		if (optional.isEmpty()) {
			throw new ErrorException("Perfil não existe!");
		}
		return optional.get();
	}

	public Perfil salvar(String nomeCargo) throws PerfilExistenteException {
		Optional<Perfil> opt = perfilRepo.findByNome(nomeCargo);
		if (opt.isPresent()) {
			throw new PerfilExistenteException();
		}
		Perfil perfil = new Perfil();
		perfil.setNome(nomeCargo);
		return perfilRepo.save(perfil);
	}
	
    public Void delete(Long id) throws ErrorException {

        Optional<Perfil> optional = perfilRepo.findById(id);
        if (optional.isEmpty()) {
            throw new ErrorException("Perfil não existe!");
        }
        perfilRepo.deleteById(id);
        return null;
    }
}



