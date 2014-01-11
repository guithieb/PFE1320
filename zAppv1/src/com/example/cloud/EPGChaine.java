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

	private int id;
	private String nom;
	private String logo;
	private ArrayList<ListeProgramme> listeProgrammes;
	

	public void setId(int id){
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

	public ArrayList<ListeProgramme> getListeProgrammes() {
		return listeProgrammes;
	}

	public void setListeProgrammes(ArrayList<ListeProgramme> listeProgrammes) {
		this.listeProgrammes = listeProgrammes;
	}

	public int getId() {
		return id;
	}

	public class ListeProgramme{
		
		private ArrayList<Programme> programmes;

		public ArrayList<Programme> getProgrammes() {
			return programmes;
		}

		public void setProgrammes(ArrayList<Programme> programmes) {
			this.programmes = programmes;
		}
		
		
		
	}
	
	public  class Programme{
		
		private int id;
		private String nom, description;
		private Date debut,fin;
		
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
