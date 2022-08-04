package br.com.apireembolso.services;

import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.models.Cliente;
import br.com.apireembolso.models.Despesa;
import br.com.apireembolso.models.Solicitacao;
import br.com.apireembolso.respositories.ClienteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

	@Autowired
	ClienteRepo repo;

	public List<Cliente> listarTodos() {
		return repo.findAll();
	}
	
	public List<Cliente> listarPorStatus() {
		return repo.findByStatus("Ativo");
	}

	public Cliente buscarPorNome(String nome) throws ErrorException {
		Optional<Cliente> optional = repo.findByNome(nome);
		if (optional.isEmpty()) {
			throw new ErrorException("Cliente não existe!");
		}
		return optional.get();
	}

	public Cliente adicionar(Cliente cliente) throws ErrorException {

		Optional<Cliente> optional = repo.findByNome(cliente.getNome());
		if (optional.isPresent()) {
			throw new ErrorException("Cliente já existe!");
		}

		if (cliente.getNome() == null || cliente.getNome().equals("")) {
			throw new ErrorException("Nome não pode ser vazio");
		}

		if (cliente.getCidade() == null || cliente.getCidade().equals("")) {
			throw new ErrorException("Cidade não pode ser vazia");
		}

		if (cliente.getEstado() == null || cliente.getEstado().equals("")) {
			throw new ErrorException("Estado não pode ser vazio");
		}
		
		if (cliente.getNome() == null || cliente.getNome().equals("")) {
			throw new ErrorException("Nome não pode ser vazio");
		}

		return repo.save(cliente);
	}
	
    public String alterarStatus(Long id) throws ErrorException {

        Optional<Cliente> optional = repo.findById(id);
      
        if(optional.get().getStatus().equals("Ativo") ) {
        	optional.get().setStatus("Inativo");
        	
        } else {
        	optional.get().setStatus("Ativo");
        }
        
        repo.save(optional.get());
        return "Status Alterado com sucesso";
    }

}
