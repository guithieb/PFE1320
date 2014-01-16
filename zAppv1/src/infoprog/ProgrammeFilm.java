package infoprog;

import java.util.ArrayList;

public class ProgrammeFilm extends BaseProgramme{
	
	private ListeArtiste ListeArtistes;
	
	public ProgrammeFilm()
	{
		
	}
	
	
	
	public ListeArtiste getListeArtistes() {
		return ListeArtistes;
	}



	public void setListeArtistes(ListeArtiste listeArtistes) {
		ListeArtistes = listeArtistes;
	}



	public class ListeArtiste{
		
		private ArrayList <Artiste> Artiste;
		
		public ListeArtiste()
		{
			
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
			
			public Artiste()
			{
				
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

}
