package com.hibernate.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "discografica")
public class Discografica {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codDiscografica")
	private int cod;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "pais")
	private String pais;
	
	@Column(name = "fundacion")
	private LocalDate fundacion;
	
	@ManyToMany
	@JoinTable(
			name = "artista_discografica",
			joinColumns = @JoinColumn(name = "cod"),
            inverseJoinColumns = @JoinColumn(name = "cod")
			  )
	private List<Artista> artistas = new ArrayList<Artista>();

	public Discografica() {
		super();
	}

	public Discografica(String nombre, String pais, LocalDate fundacion) {
		super();
		this.nombre = nombre;
		this.pais = pais;
		this.fundacion = fundacion;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public LocalDate getFundacion() {
		return fundacion;
	}

	public void setFundacion(LocalDate fundacion) {
		this.fundacion = fundacion;
	}

	public List<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(List<Artista> artistas) {
		this.artistas = artistas;
	}
	
	public void anyadirArtista(Artista a) {
		this.artistas.add(a);
		a.getDiscograficas().add(this);
	}
	
	public void quitarPersona(Artista a) {
		this.artistas.remove(a);
		a.getDiscograficas().remove(this);
	}
	
	
	
	
}
