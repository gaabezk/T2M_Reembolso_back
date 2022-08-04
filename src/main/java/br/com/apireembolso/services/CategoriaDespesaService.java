package br.com.apireembolso.services;

import br.com.apireembolso.exceptions.CategoriaExistenteException;
import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.models.CategoriaDespesa;
import br.com.apireembolso.models.Cliente;
import br.com.apireembolso.respositories.CategoriaDespesaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaDespesaService {

	@Autowired
	CategoriaDespesaRepo categoriaDespesaRepo;

	public List<CategoriaDespesa> listarTodos() {
		return categoriaDespesaRepo.findAll();
	}

	public CategoriaDespesa buscar(String nome) {
		Optional<CategoriaDespesa> cargo = categoriaDespesaRepo.findByNome(nome);
		return cargo.get();
	}

	public CategoriaDespesa buscarID(Long id) throws ErrorException {
		Optional<CategoriaDespesa> optional = categoriaDespesaRepo.findById(id);
		if (optional.isEmpty()) {
			throw new ErrorException("Categoria não existe!");
		}
		return optional.get();
	}
	
	public CategoriaDespesa buscarPorNome(String nome) throws ErrorException {
		Optional<CategoriaDespesa> optional = categoriaDespesaRepo.findByNome(nome);
		if (optional.isEmpty()) {
			throw new ErrorException("Categoria não existe!");
		}
		return optional.get();
	}


	public CategoriaDespesa salvar(CategoriaDespesa categoriaDespesa) throws ErrorException {
		Optional<CategoriaDespesa> opt = categoriaDespesaRepo.findByNome(categoriaDespesa.getNome());
		return categoriaDespesaRepo.save(categoriaDespesa);
	}
	
    public String delete(Long id) throws ErrorException {

        Optional<CategoriaDespesa> optional = categoriaDespesaRepo.findById(id);
        if (optional.isEmpty()) {
            throw new ErrorException("Categoria não existe!");
        }
        categoriaDespesaRepo.deleteById(id);
        return "Categoria Deletada com sucesso";
    }

}
