package br.com.apireembolso.respositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import br.com.apireembolso.models.Solicitacao;

public interface SolicitacaoRepo extends JpaRepository <Solicitacao, Long> {

	@Query("SELECT s FROM Solicitacao s WHERE s.usuario.id =:usuario_id")
    public List<Solicitacao> findByUsuarioId(long usuario_id);
	public List<Solicitacao> findAllById(long id);
	public List<Solicitacao> findByStatus(String status);
	public List<Solicitacao> findByGestorAndStatus(long gestor, String status);

}
