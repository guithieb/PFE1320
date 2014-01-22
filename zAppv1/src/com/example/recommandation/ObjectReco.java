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
      String lastName;
      
      public Artist()
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
      
      
    }
  
  
  
}