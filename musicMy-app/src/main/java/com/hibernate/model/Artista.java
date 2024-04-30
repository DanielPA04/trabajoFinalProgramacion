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
@Table(name = "artista")
public class Artista {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codArtista")
	int cod;

	@Column(name = "nombre")
	String nombre;

	@Column(name = "fechaNac")
	LocalDate fechaNac;

	@Column(name = "imagen", columnDefinition = "mediumblob")
	Blob imagen;

	// int puntuacion;

	public Artista() {
		super();
	}

	public Artista(String nombre, LocalDate fechaNac, Blob imagen) {
		super();
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.imagen = imagen;
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

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Blob getImagen() {
		return imagen;
	}

	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}

}
