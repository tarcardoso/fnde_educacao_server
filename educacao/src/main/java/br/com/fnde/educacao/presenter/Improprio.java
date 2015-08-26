package br.com.fnde.educacao.presenter;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Improprio implements Serializable {

	private static final long serialVersionUID = 1919191L;
	
	private String idTimeLine;
	private String idImproprio;
	private String idUsuario;
	
	
	public String getIdTimeLine() {
		return idTimeLine;
	}
	public void setIdTimeLine(String idTimeLine) {
		this.idTimeLine = idTimeLine;
	}
	public String getIdImproprio() {
		return idImproprio;
	}
	public void setIdImproprio(String idImproprio) {
		this.idImproprio = idImproprio;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	 
	public String toString(){
		return "idTimeLine: "+idTimeLine+" idImproprio: "+ idImproprio+" idUsuario: "+idUsuario;
	}
	
}
