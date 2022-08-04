package br.com.apireembolso.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class CategoriaDespesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
	@Column(name = "nome")
    private String nome;

    @JsonIgnore
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Despesa> despesas;
    
    public CategoriaDespesa() {
    }

	public CategoriaDespesa(Long id, @NotNull String nome, List<Despesa> despesas) {
		super();
		this.id = id;
		this.nome = nome;
		this.despesas = despesas;
	}

	

    
}
