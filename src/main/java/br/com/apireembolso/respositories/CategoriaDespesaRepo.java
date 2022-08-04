package br.com.apireembolso.respositories;

import br.com.apireembolso.models.CategoriaDespesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaDespesaRepo extends JpaRepository<CategoriaDespesa, Long> {
	
    Optional<CategoriaDespesa> findByNome(String nome);
    
	Optional<CategoriaDespesa> findById(CategoriaDespesa categoriaDespesa);
}
