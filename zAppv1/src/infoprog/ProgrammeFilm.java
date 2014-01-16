package infoprog;


import java.util.ArrayList;

public class ProgrammeFilm  {

	private Programme Programme;

	public ProgrammeFilm(){

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
		private String description;
		private ListeGenre ListeGenres;
		private String codeParental;
		private Diffusion Diffusion;
		private String imagette;
		private String productionDate;
		private ListeArtiste ListeArtistes;

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


		public String getProductionDate() {
			return productionDate;
		}

		public void setProductionDate(String productionDate) {
			this.productionDate = productionDate;
		}


		public ListeArtiste getListeArtistes() {
			return ListeArtistes;
		}

		public void setListeArtistes(ListeArtiste listeArtistes) {
			ListeArtistes = listeArtistes;
		}


		public class ListeArtiste{
			private ArrayList<Artiste> Artiste;

			public ListeArtiste(){

			}

			public ArrayList<Artiste> getArtiste() {
				return Artiste;
			}

			public void setArtiste(ArrayList<Artiste> artiste) {
				Artiste = artiste;
			}

			public class Artiste{
				private String firstName;
				private String lastName;
				private String role;

				public Artiste(){

				}

				public String getFirstName() {
					return firstName;
				}

				public void setFirstName(String firstName) {
					this.firstName = firstName;
				}

				public String getLastName() {
					return lastName;
				}

				public void setLastName(String lastName) {
					this.lastName = lastName;
				}

				public String getRole() {
					return role;
				}

				public void setRole(String role) {
					this.role = role;
				}

			}
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
