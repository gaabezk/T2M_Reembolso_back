package br.com.apireembolso.controllers;

import br.com.apireembolso.exceptions.PerfilExistenteException;
import br.com.apireembolso.exceptions.CategoriaExistenteException;
import br.com.apireembolso.exceptions.ErrorException;
import br.com.apireembolso.exceptions.IdNotFoundException;

import br.com.apireembolso.exceptions.UsuarioExistenteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> idNotFoundException(IdNotFoundException e) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(e.m1(), e.m2());
		return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UsuarioExistenteException.class)
	public ResponseEntity<?> usuarioExistenteException(UsuarioExistenteException e) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(e.m1(), e.m2());
		return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PerfilExistenteException.class)
	public ResponseEntity<?> cargoExistenteException(PerfilExistenteException e) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(e.m1(), e.m2());
		return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CategoriaExistenteException.class)
	public ResponseEntity<?> despesaExistenteException(CategoriaExistenteException e) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(e.m1(), e.m2());
		return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ErrorException.class)
	public ResponseEntity<?> GeneralException(ErrorException exception) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Error", exception.getMessage());
		return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
	}
}