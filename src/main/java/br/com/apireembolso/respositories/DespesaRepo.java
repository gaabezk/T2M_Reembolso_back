package br.com.apireembolso.respositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.apireembolso.models.Despesa;
import br.com.apireembolso.models.Solicitacao;
import br.com.apireembolso.models.Usuario;

public interface DespesaRepo extends JpaRepository <Despesa,Long> {

	Optional<Despesa> findByData(LocalDate data);
	List<Despesa> findAllByUsuarioAndPendente(Usuario usuario, Boolean pendente);
	
	@Query("SELECT d FROM Despesa d WHERE d.solicitacao.id =:solicitacao_id")
    public List<Despesa> findBySolicitacaoId(long solicitacao_id);

//	Optional<Despesa> findByIdAndUsuario();
	

}