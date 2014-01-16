package com.example.cloud;

import java.util.ArrayList;
import java.util.Date;

/*
 * 
 * 
 * Création des attributs qui caractérise une chaîne ainsi que ses programmes
 * 
 */

public class EPGChaine {

	// tout passer en public ?
	public String id; //private avant maj BDD
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
		
		private String id;
		private String nom, description;
		private String debut,fin;
		
		public Programme()
		{
		  
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
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
		public String getDebut() {
			return debut;
		}
		public void setDebut(String debut) {
			this.debut = debut;
		}
		public String getFin() {
			return fin;
		}
		public void setFin(String fin) {
			this.fin = fin;
		}
		
		
	}
	
	
	}
}
	

