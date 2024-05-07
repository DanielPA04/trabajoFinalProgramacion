package com.hibernate.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private int codDis;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "pais")
	private String pais;
	
	@Column(name = "fundacion")
	private LocalDate fundacion;
	
	@Column(name = "imagen")
	private Blob imagen;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "artista_discografica",
			joinColumns = @JoinColumn(name = "codDis"),
            inverseJoinColumns = @JoinColumn(name = "codArt")
			  )
	private List<Artista> artistas = new ArrayList<Artista>();

	public Discografica() {
		super();
	}

	public Discografica(String nombre, String pais, LocalDate fundacion, Blob imagen) {
		super();
		this.nombre = nombre;
		this.pais = pais;
		this.fundacion = fundacion;
		this.imagen = imagen;
	}

	public int getCodDis() {
		return codDis;
	}

	public void setCodDis(int codDis) {
		this.codDis = codDis;
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
	
	
	public Blob getImagen() {
		return imagen;
	}

	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}

	public void anyadirArtista(Artista a) {
		this.artistas.add(a);
		a.getDiscograficas().add(this);
	}
	
	public void quitarArtista(Artista a) {
		this.artistas.remove(a);
		a.getDiscograficas().remove(this);
	}
	
	
	
	
}
