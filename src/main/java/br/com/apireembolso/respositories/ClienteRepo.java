package br.com.apireembolso.respositories;

import br.com.apireembolso.models.Cliente;
import br.com.apireembolso.models.Despesa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepo extends JpaRepository<Cliente,Long>{

	public Optional<Cliente> findByNome(String nome);

	public Optional<Cliente> findById(Cliente cliente);
	
	public List<Cliente> findByStatus(String status);
}