package com.example.cloud;

public class ParserEPGlive {

	//http://openbbox.flex.bouyguesbox.fr:81/V0/Media/EPG/Live
	public String input;
	
	public String id;
	public String nom;
	public String logo;
	public String ListeProgrammes;
	public String Programme;
	public String description;
	public String debut;
	public String fin;
	
	public boolean create_success;
	
	public ParserEPGlive(){
	}
	// message error : Cannot GET /V0/Media/EPG/    ("{\"Response\":\"False\",\"Error\":\"Incorrect IMDb ID\"}"))
	public ParserEPGlive(String input){
	/*	if(input.contains("Cannot GET \"/\"VO\"/\"Media\"/\"EPG\"/"))
		{
			create_success = false;
			
		}*/
	if (input.contains("[\"{\"")){
		input = input.replace("[\"{\"", "");
		input = input.replace("]\"}\"", "");
	}
	}
}
