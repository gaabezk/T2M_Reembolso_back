package br.com.apireembolso.respositories;

import br.com.apireembolso.models.Solicitacao;
import br.com.apireembolso.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByEmailAndPassword(String email, String password);
    public Optional<Usuario> findByEmail(String email);
    public Optional<Usuario> findByNome(String nome);
    public Optional<Usuario> findByUsuarioPerfis(String nome);
    
	@Query("SELECT s FROM Solicitacao s WHERE s.usuario.id =:usuario_id")
    public List<Usuario> findByUsuarioId(long usuario_id);

}
