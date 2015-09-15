package br.com.fnde.educacao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="curtirtimeline")
public class CurtirTimeLine implements Domain {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idCurtirTimeLine", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idImproprioTimeLine;
	
	@Column(name="idTimeLine", nullable=false) 
	private Long idTimeLine;
	
	@Column(name="idUsuario", nullable=false, length=100) 
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

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	
}
