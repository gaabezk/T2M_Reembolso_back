package br.com.apireembolso.respositories;

import br.com.apireembolso.models.Anexo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnexoRepo extends JpaRepository<Anexo,Long> {

    Optional<Anexo> findByDespesaId(Long id);

}
