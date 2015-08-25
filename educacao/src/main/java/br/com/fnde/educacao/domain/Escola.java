package br.com.fnde.educacao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity 
@Table(name="escola")
public class Escola implements Domain {

	@Id @Generated(GenerationTime.INSERT) 
	@Column(name="idEscola", nullable=false) 
	private Long idEscola;
	
	@NotNull
	@Column(name="noEscola", nullable=false, length=100)
	private String noEscola;
	
	@NotNull
	@Column(name="txEndereco", nullable=false, length=100)
	private String txEndereco;
	
	@NotNull
	@Column(name="txImagem", nullable=false, length=100)
	private String txImagem;

	public Long getIdEscola() {
		return idEscola;
	}

	public void setIdEscola(Long idEscola) {
		this.idEscola = idEscola;
	}

	public String getNoEscola() {
		return noEscola;
	}

	public void setNoEscola(String noEscola) {
		this.noEscola = noEscola;
	}

	public String getTxEndereco() {
		return txEndereco;
	}

	public void setTxEndereco(String txEndereco) {
		this.txEndereco = txEndereco;
	}

	public String getTxImagem() {
		return txImagem;
	}

	public void setTxImagem(String txImagem) {
		this.txImagem = txImagem;
	}
	
	
	
}
