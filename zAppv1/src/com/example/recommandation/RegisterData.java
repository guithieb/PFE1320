package com.example.recommandation;

import com.google.gson.annotations.SerializedName;

public class RegisterData {
  
  @SerializedName("appName")
  public String appName;
  
  public RegisterData(String appName)
  {
    this.appName = appName;
  }

}
