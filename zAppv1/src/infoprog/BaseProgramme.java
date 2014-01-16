package infoprog;

public class BaseProgramme {
	
	private Programme Programme;
	
	public BaseProgramme()
	{
		
	}
	
	public Programme getProgramme() {
		return Programme;
	}

	public void setProgramme(Programme programme) {
		Programme = programme;
	}

	
	
	public class Programme
	{
		private String id;
		private String titre;
		private String sousTitre;
		private String description;
		private ListeGenre ListeGenres;
		private String codeParental;
		private Diffusion Diffusion;
		private String imagette;
		private String productionNationality;
		private String productionDate;
		
		public Programme()
		{
			
		}
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTitre() {
			return titre;
		}

		public void setTitre(String titre) {
			this.titre = titre;
		}

		public String getSousTitre() {
			return sousTitre;
		}

		public void setSousTitre(String sousTitre) {
			this.sousTitre = sousTitre;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public ListeGenre getListeGenres() {
			return ListeGenres;
		}

		public void setListeGenres(ListeGenre listeGenres) {
			ListeGenres = listeGenres;
		}

		public String getCodeParental() {
			return codeParental;
		}

		public void setCodeParental(String codeParental) {
			this.codeParental = codeParental;
		}

		public Diffusion getDiffusion() {
			return Diffusion;
		}

		public void setDiffusion(Diffusion diffusion) {
			Diffusion = diffusion;
		}

		public String getImagette() {
			return imagette;
		}

		public void setImagette(String imagette) {
			this.imagette = imagette;
		}

		public String getProductionNationality() {
			return productionNationality;
		}

		public void setProductionNationality(String productionNationality) {
			this.productionNationality = productionNationality;
		}

		public String getProductionDate() {
			return productionDate;
		}

		public void setProductionDate(String productionDate) {
			this.productionDate = productionDate;
		}

		
		
		
		
		
		public class ListeGenre{
			
			private String genre;
			
			public ListeGenre()
			{
				
			}

			public String getGenre() {
				return genre;
			}

			public void setGenre(String genre) {
				this.genre = genre;
			}
			
			
		}
		
		public class Diffusion{
			
			private String debut;
			private String fin;
			private String duree;
			
			public Diffusion()
			{
				
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

			public String getDuree() {
				return duree;
			}

			public void setDuree(String duree) {
				this.duree = duree;
			}
			
		}
			
	}
	
	
	

}
