package com.example.recommandation;

import java.util.ArrayList;

public class ObjectReco {

  Long userId;
  ArrayList <Artist> artists;
  
  public ObjectReco()
  {
    
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public ArrayList<Artist> getArtists() {
    return artists;
  }

  public void setArtists(ArrayList<Artist> artists) {
    this.artists = artists;
  }
  
    public class Artist{
    
      String firstName;
      String familyName;
      
      public Artist()
      {
        
      }

      public String getFirstName() {
        return firstName;
      }

      public void setFirstName(String firstName) {
        this.firstName = firstName;
      }

      public String getFamilyName() {
        return familyName;
      }

      public void setFamilyName(String familyName) {
        this.familyName = familyName;
      }
      
      
    }
  
    // recup la chaine JSON format depuis la reco
    // recup tous les acteurs reco
    // matchs de ces noms avec le d�tail prog de l'EPG live
    // si oui, afficher le programme dans la vue recommendations
  
 
    
  
}
