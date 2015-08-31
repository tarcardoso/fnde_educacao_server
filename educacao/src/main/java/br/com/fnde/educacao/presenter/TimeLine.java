package br.com.fnde.educacao.presenter;

import java.io.Serializable;

import javax.ws.rs.QueryParam;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeLine implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String latitude;
	private String longitude;
	private Long page;
	private Long start;
	private Long limits;
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Long getPage() {
		return page;
	}
	public void setPage(Long page) {
		this.page = page;
	}
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public Long getLimits() {
		return limits;
	}
	public void setLimits(Long limits) {
		this.limits = limits;
	}
	
	public String toString(){
		return "latitude: "+latitude+" longitude: "+ longitude+" page: "+page;
	}
}
