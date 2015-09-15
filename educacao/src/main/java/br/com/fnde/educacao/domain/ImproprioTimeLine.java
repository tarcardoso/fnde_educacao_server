package br.com.fnde.educacao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity 
@Table(name="improprioTimeLine")
public class ImproprioTimeLine implements Domain {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idImproprioTimeLine", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idImproprioTimeLine;
	
	@Column(name="idTimeLine", nullable=false) 
	private Long idTimeLine;
	
	@Column(name="idImproprio", nullable=false) 
	private Long idImproprio;
	
	@Column(name="idUsuario", nullable=false) 
	private String idUsuario;

	public Long getIdImproprioTimeLine() {
		return idImproprioTimeLine;
	}

	public void setIdImproprioTimeLine(Long idImproprioTimeLine) {
		this.idImproprioTimeLine = idImproprioTimeLine;
	}

	public Long getIdTimeLine() {
		return idTimeLine;
	}

	public void setIdTimeLine(Long idTimeLine) {
		this.idTimeLine = idTimeLine;
	}

	public Long getIdImproprio() {
		return idImproprio;
	}

	public void setIdImproprio(Long idImproprio) {
		this.idImproprio = idImproprio;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	
}
