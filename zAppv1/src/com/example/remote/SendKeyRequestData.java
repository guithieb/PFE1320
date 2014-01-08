package com.example.remote;

import com.google.gson.annotations.SerializedName;


/**
 * Class représentant le body de la requête /UserInterface/RemoteController/Key
 * Les champs de cette class sont transformés en json par la librairie GSON
 * Ex:  {"key":{"keyName":"P+","keyType":"keypressed"}}
 */

public class SendKeyRequestData {
  
  @SerializedName("key")
  public Key key;

  public SendKeyRequestData(String keyName, String keyType){
    key = new Key();
    key.keyName = keyName;
    key.keyType = keyType;
  }

  public class Key {
    @SerializedName ("keyName")
    public String keyName;

    @SerializedName ("keyType")
    public String keyType;

  }

}
