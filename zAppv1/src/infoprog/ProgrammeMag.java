package infoprog;


public class ProgrammeMag {

private ListeArtiste ListeArtistes;
	
	public ProgrammeMag()
	{
		
	}
	
	
	
	public ListeArtiste getListeArtistes() {
		return ListeArtistes;
	}



	public void setListeArtistes(ListeArtiste listeArtistes) {
		ListeArtistes = listeArtistes;
	}



	public class ListeArtiste{
		
		private Artiste Artiste;
		
		public ListeArtiste()
		{
			
		}
		
		
		
		public Artiste getArtiste() {
			return Artiste;
		}



		public void setArtiste(Artiste artiste) {
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
