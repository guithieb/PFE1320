package com.example.remote;

import com.google.gson.annotations.SerializedName;

public class SendVolume {
	@SerializedName("volume")	
	public String volume;

	public SendVolume(String volumeToSend) {
		volume = volumeToSend;		  
	}
}
