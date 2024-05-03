package com.hibernate.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "artista")
public class Artista {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codArtista")
	private int cod;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "fechaNac")
	private LocalDate fechaNac;

	@Column(name = "imagen", columnDefinition = "mediumblob")
	private Blob imagen;

	@ManyToMany 
	@JoinTable(
			name = "artista_discografica",
			joinColumns = @JoinColumn(name = "cod"),
            inverseJoinColumns = @JoinColumn(name = "cod")
			  )
	private List<Discografica> discograficas = new ArrayList<Discografica>();
	
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

	public List<Discografica> getDiscograficas() {
		return discograficas;
	}

	public void setDiscograficas(List<Discografica> discograficas) {
		this.discograficas = discograficas;
	}
	
	
	public void anyadirPersona(Discografica d) {
		this.discograficas.add(d);
		d.getArtistas().add(this);
	}
	
	public void quitarPersona(Discografica d) {
		this.discograficas.remove(d);
		d.getArtistas().remove(this);
	}
	

}
