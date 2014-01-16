package infoprog;

import java.util.ArrayList;

public class ProgrammeSerie extends BaseProgramme {
	
	private ListeArtiste ListeArtistes;
	private serie serie;
	
	public ProgrammeSerie(){
		
	}
	

	public ListeArtiste getListeArtistes() {
		return ListeArtistes;
	}

	public void setListeArtistes(ListeArtiste listeArtistes) {
		ListeArtistes = listeArtistes;
	}

	public serie getSerie() {
		return serie;
	}

	public void setSerie(serie serie) {
		this.serie = serie;
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
	
	public class serie{
		private String information;
		private String saison;
		private String episode;
		private String isLast;
		
		public serie(){
			
		}

		public String getInformation() {
			return information;
		}

		public void setInformation(String information) {
			this.information = information;
		}

		public String getSaison() {
			return saison;
		}

		public void setSaison(String saison) {
			this.saison = saison;
		}

		public String getEpisode() {
			return episode;
		}

		public void setEpisode(String episode) {
			this.episode = episode;
		}

		public String getIsLast() {
			return isLast;
		}

		public void setIsLast(String isLast) {
			this.isLast = isLast;
		}
		
		
	}
}
