package com.hibernate.model;

import java.sql.Blob;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "album")
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codAlbum")
	private int codAlb;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "fecha")
	private LocalDate fecha;

	@Column(name = "generos")
	private String generos;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "discografica")
	private String discografica;
	
	@Column(name = "imagen")
	private Blob imagen;

	@Column(name = "Artista")
	private int artista;

	public Album() {
		super();
	}

	public Album(String nombre, LocalDate fecha, String generos, String descripcion, String discografica, Blob imagen, int artista) {
		super();
		this.nombre = nombre;
		this.fecha = fecha;
		this.generos = generos;
		this.descripcion = descripcion;
		this.discografica = discografica;
		this.imagen = imagen;
		this.artista = artista;
	}

	public int getCodAlb() {
		return codAlb;
	}

	public void setCodAlb(int codAlb) {
		this.codAlb = codAlb;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getGeneros() {
		return generos;
	}

	public void setGeneros(String generos) {
		this.generos = generos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDiscografica() {
		return discografica;
	}

	public void setDiscografica(String discografica) {
		this.discografica = discografica;
	}
	
	public Blob getImagen() {
		return imagen;
	}

	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}

	public int getArtista() {
		return artista;
	}

	public void setArtista(int artista) {
		this.artista = artista;
	}

}
