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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "artista")
/**
 * Clase Artista
 */
public class Artista {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codArtista")
	/**
	 * Codigo autoincrementable del artista
	 */
	private int codArt;

	@Column(name = "nombre")
	/**
	 * Nombre del artista
	 */
	private String nombre;

	@Column(name = "fechaNac")
	/**
	 * Fecha de nacimiento del artista
	 */
	private LocalDate fechaNac;

	@Column(name = "imagen", columnDefinition = "mediumblob")
	/**
	 * Imagen del artista
	 */
	private Blob imagen;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "artista_discografica", joinColumns = @JoinColumn(name = "codArt"), inverseJoinColumns = @JoinColumn(name = "codDis"))
	/**
	 * Lista de discograficas a las que pertenece/ha pertenecido un artista
	 */
	private List<Discografica> discograficas = new ArrayList<Discografica>();

	/*
	 * Constructor por defecto
	 */
	public Artista() {
		super();
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param nombre   Nombre del artista
	 * @param fechaNac Fecha de nacimiento del artista
	 * @param imagen   Imagen del artista
	 */
	public Artista(String nombre, LocalDate fechaNac, Blob imagen) {
		super();
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.imagen = imagen;
	}

	/**
	 * Metodo get del codigo
	 * 
	 * @return Codigo del artista
	 */
	public int getCodArt() {
		return codArt;
	}

	/**
	 * Metodo set del codigo
	 * 
	 * @param codArt Nuevo codigo del artista
	 */
	public void setCodArt(int codArt) {
		this.codArt = codArt;
	}

	/**
	 * Metodo get del nombre
	 * 
	 * @return Nombre del artista
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo set del nombre
	 * 
	 * @param nombre Nuevo nombre del artista
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo get de la fecha de nacimiento
	 * 
	 * @return Fecha de nacimiento del artista
	 */
	public LocalDate getFechaNac() {
		return fechaNac;
	}

	/**
	 * Metodo set de la fecha de nacimiento
	 * 
	 * @param fechaNac Nueva fecha de nacimiento del artista
	 */
	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	/**
	 * Metodo get de la imagen
	 * 
	 * @return Imagen del artista
	 */
	public Blob getImagen() {
		return imagen;
	}

	/**
	 * Metodo set de la imagen
	 * 
	 * @param imagen Nueva imagen del artista
	 */
	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}

	/**
	 * Metodo get de la lista de discograficas
	 * 
	 * @return Lista de discograficas del artista
	 */
	public List<Discografica> getDiscograficas() {
		return discograficas;
	}

	/**
	 * Metodo set de la lista de discograficas
	 * 
	 * @param discograficas Nueva lista de discograficas del artista
	 */
	public void setDiscograficas(List<Discografica> discograficas) {
		this.discograficas = discograficas;
	}

	/**
	 * Metodo que anyade una discografica al artista y a su vez anyade el artista a
	 * dicha discografica
	 * 
	 * @param d Discografica la cual se va anyadir a la lista y a la que se le va a
	 *          anyadir el artista
	 */
	public void anyadirDiscografica(Discografica d) {
		this.discograficas.add(d);
		d.getArtistas().add(this);
	}

	/**
	 * Metodo que quita una discografica al artista y a su vez quita el artista a
	 * dicha discografica
	 * 
	 * @param d Discografica la cual va a ser quitada del artista y a la que se le
	 *          va a quitar el artista
	 */
	public void quitarDiscografica(Discografica d) {
		this.discograficas.remove(d);
		d.getArtistas().remove(this);
	}

}
