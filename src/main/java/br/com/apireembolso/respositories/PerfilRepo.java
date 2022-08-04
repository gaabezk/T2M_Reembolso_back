package br.com.apireembolso.respositories;

import br.com.apireembolso.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepo extends JpaRepository<Perfil,Long>{

    public Optional<Perfil> findByNome(String nome);


}
