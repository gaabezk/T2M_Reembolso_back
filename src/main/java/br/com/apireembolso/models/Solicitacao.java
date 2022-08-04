package br.com.apireembolso.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Solicitacao {  
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "data")
	private LocalDate data;
	@Column(name = "status")
	private String status = "Pendente";
	
	@Column(name = "gestor")
	private Long gestor;
	
	@Column(name = "aguardando")
	private Boolean aguardando = true;

	@Column(name = "total")
	private Double total;
	
	@OneToMany(mappedBy = "solicitacao", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Despesa> despesas;
	
	@ManyToOne()
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;
	
	

	public Solicitacao() {
		super();
	}

	public Solicitacao(Long id, LocalDate data, String status, List<Despesa> despesas) {
		super();
		this.id = id;
		this.data = data;
		this.status = status;
		this.despesas = despesas;
	}


}
