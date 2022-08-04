package br.com.apireembolso.respositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apireembolso.models.Email;


public interface EmailRepo extends JpaRepository<Email, UUID> {

}
