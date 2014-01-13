package com.example.cloud;

import java.util.ArrayList;
import java.util.Date;

import android.text.method.DateTimeKeyListener;

/*
 * 
 * 
 * Création des attributs qui caractérise une chaîne ainsi que ses programmes
 * 
 */

public class EPGChaine {

	// tout passer en public ?
	private String id;
	private String nom;
	private String logo;
	private ListeProgramme ListeProgrammes;
	
	public EPGChaine()
	{
	  
	}

	public void setId(String id){
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public ListeProgramme getListeProgrammes() {
		return ListeProgrammes;
	}


	public void setListeProgrammes(ListeProgramme listeProgrammes) {
		this.ListeProgrammes = listeProgrammes;
	}

	public String getId() {
		return id;
	}

	public class ListeProgramme{
		
		private Programme Programme;
		
		
		public ListeProgramme(){
		 
		}

		public Programme getProgrammes() {
			return Programme;
		}

		public void setProgrammes(Programme programmes) {
			this.Programme = programmes;
		}
		
	public class Programme{
		
		private int id;
		private String nom, description;
		private Date debut,fin;
		
		public Programme()
		{
		  
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Date getDebut() {
			return debut;
		}
		public void setDebut(Date debut) {
			this.debut = debut;
		}
		public Date getFin() {
			return fin;
		}
		public void setFin(Date fin) {
			this.fin = fin;
		}
		
		
	}
	
	
	}
}
	

